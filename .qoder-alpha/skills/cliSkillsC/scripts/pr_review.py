#!/usr/bin/env python3
"""PR Style Reviewer - Analyzes commit messages and PR style."""

import argparse
import json
import os
import re
import subprocess
import sys
from dataclasses import dataclass, field, asdict
from typing import List, Dict, Tuple

CONVENTIONAL_TYPES = ['feat', 'fix', 'docs', 'style', 'refactor', 'perf', 'test', 'build', 'ci', 'chore', 'revert']

@dataclass
class Issue:
    level: str
    message: str
    line: int = 0

@dataclass
class CommitReview:
    hash: str
    subject: str
    issues: List[Issue] = field(default_factory=list)

    @property
    def score(self) -> float:
        if not self.issues:
            return 100.0
        total = sum(1 for i in self.issues if i.level != 'PASS')
        return max(0, 100 - (total * 15))

@dataclass
class ReviewReport:
    commits: List[CommitReview]
    total_score: float = 0
    summary: Dict = field(default_factory=dict)

    def __post_init__(self):
        if self.commits:
            self.total_score = sum(c.score for c in self.commits) / len(self.commits)
        self.summary = {
            'total_commits': len(self.commits),
            'total_issues': sum(len(c.issues) for c in self.commits),
            'score': round(self.total_score, 1)
        }

def run_git(args: List[str], cwd: str = None) -> str:
    result = subprocess.run(['git'] + args, capture_output=True, text=True, cwd=cwd)
    if result.returncode != 0:
        raise RuntimeError(f"git {' '.join(args)} failed: {result.stderr}")
    return result.stdout.strip()

def get_commits(repo_path: str, n: int) -> List[Dict]:
    log_format = '%H%n%s%n%b%n---COMMIT---'
    output = run_git(['log', f'-{n}', f'--pretty=format:{log_format}'], cwd=repo_path)
    commits = []
    for block in output.split('---COMMIT---\n'):
        parts = block.strip().split('\n', 2)
        if len(parts) >= 2:
            commits.append({
                'hash': parts[0][:8],
                'subject': parts[1],
                'body': parts[2] if len(parts) > 2 else ''
            })
    return commits

def get_diff_stats(repo_path: str, commit_hash: str) -> List[str]:
    return run_git(['diff', '--unified=0', f'{commit_hash}~1', commit_hash], cwd=repo_path).split('\n')

def check_conventional_commit(subject: str) -> Issue:
    pattern = r'^(\w+)(\([^)]+\))?: .+$'
    match = re.match(pattern, subject)
    if match and match.group(1) in CONVENTIONAL_TYPES:
        return Issue('PASS', 'Follows conventional commit format')
    return Issue('FAIL', f'Not conventional commit. Format: type: description (types: {", ".join(CONVENTIONAL_TYPES)})')

def check_subject_length(subject: str) -> Issue:
    if len(subject) <= 72:
        return Issue('PASS', f'Subject length OK ({len(subject)} chars)')
    return Issue('WARN', f'Subject too long ({len(subject)} chars, max 72)')

def check_capitalization(subject: str) -> Issue:
    after_type = re.sub(r'^\w+(\([^)]+\))?:\s*', '', subject)
    if after_type and after_type[0].islower():
        return Issue('WARN', 'Description should start with lowercase')
    return Issue('PASS', 'Capitalization correct')

def check_trailing_period(subject: str) -> Issue:
    if subject.endswith('.'):
        return Issue('WARN', 'Subject should not end with period')
    return Issue('PASS', 'No trailing period')

def check_debug_code(diff_lines: List[str]) -> List[Issue]:
    issues = []
    debug_patterns = [
        (r'console\.log', 'console.log'),
        (r'debugger\s*;', 'debugger statement'),
        (r'print\s*\(', 'print statement'),
        (r'TODO\s*[:\(]', 'TODO comment'),
        (r'FIXME\s*[:\(]', 'FIXME comment'),
    ]
    for i, line in enumerate(diff_lines, 1):
        if line.startswith('+'):
            for pattern, name in debug_patterns:
                if re.search(pattern, line):
                    issues.append(Issue('FAIL', f'Contains {name} (line {i})', line=i))
    return issues

def check_pr_description(body: str) -> Issue:
    if body and len(body.strip()) > 10:
        return Issue('PASS', 'PR description present')
    return Issue('WARN', 'PR description missing or too short')

def review_commit(repo_path: str, commit: Dict) -> CommitReview:
    review = CommitReview(hash=commit['hash'], subject=commit['subject'])

    review.issues.append(check_conventional_commit(commit['subject']))
    review.issues.append(check_subject_length(commit['subject']))
    review.issues.append(check_capitalization(commit['subject']))
    review.issues.append(check_trailing_period(commit['subject']))
    review.issues.append(check_pr_description(commit['body']))

    try:
        diff = get_diff_stats(repo_path, commit['hash'])
        debug_issues = check_debug_code(diff)
        review.issues.extend(debug_issues)
    except RuntimeError:
        pass

    return review

def format_output(report: ReviewReport, details: bool = False) -> str:
    lines = ['PR Style Review Summary', '=' * 50]

    for commit in report.commits:
        lines.append(f'\nCommit: {commit.hash} {commit.subject}')
        lines.append(f'Score: {commit.score:.0f}/100')
        if details:
            for issue in commit.issues:
                prefix = f'  [{issue.level}]'
                lines.append(f'{prefix} {issue.message}')

    lines.append('\n' + '=' * 50)
    lines.append(f'Overall Score: {report.total_score:.1f}/100')
    lines.append(f'Total Commits: {report.summary["total_commits"]}')
    lines.append(f'Total Issues: {report.summary["total_issues"]}')

    return '\n'.join(lines)

def main():
    parser = argparse.ArgumentParser(description='PR Style Reviewer')
    parser.add_argument('--repo', default='.', help='Git repository path')
    parser.add_argument('--commits', type=int, default=10, help='Number of commits to review')
    parser.add_argument('--details', action='store_true', help='Show detailed feedback')
    parser.add_argument('--json', action='store_true', help='Output as JSON')

    args = parser.parse_args()
    repo_path = os.path.abspath(args.repo)

    if not os.path.exists(os.path.join(repo_path, '.git')):
        print(f'Error: Not a git repository: {repo_path}', file=sys.stderr)
        sys.exit(1)

    commits = get_commits(repo_path, args.commits)
    reviews = [review_commit(repo_path, c) for c in commits]
    report = ReviewReport(commits=reviews)

    if args.json:
        print(json.dumps(asdict(report), indent=2))
    else:
        print(format_output(report, args.details))

if __name__ == '__main__':
    main()