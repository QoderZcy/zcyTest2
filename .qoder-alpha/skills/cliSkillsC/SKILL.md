---
name: pr-style-reviewer
description: "Automatically review PR/commit style and provide suggestions. Checks commit message conventions (conventional commits, length, format), PR descriptions, code formatting standards, and best practices. Use when reviewing pull requests, checking commit history, evaluating code quality, or when user asks to review PR style, check commits, or analyze code changes."
---

# PR Style Reviewer

## Overview

Review PR/commit style and provide actionable suggestions for improvement.

## Usage

Run the review script against a git repository:

```bash
python scripts/pr_review.py --repo /path/to/repo --commits 5 --details
```

### Parameters
- `--repo <path>`: Git repository path (default: current directory)
- `--commits <n>`: Number of recent commits to review (default: 10)
- `--details`: Show detailed feedback for each issue
- `--json`: Output results as JSON

## Review Checks

The script checks:

1. **Commit Message Format**: Conventional commits (type: description)
2. **Message Length**: Subject line <= 72 characters
3. **Message Structure**: Proper capitalization, no trailing period
4. **PR Description**: Meaningful description present
5. **File Changes**: No debug code, console.log, TODO comments
6. **Best Practices**: Branch naming, commit frequency, scope consistency

## Output Format

```
PR Style Review Summary
======================
Commit: abc1234 feat: add user authentication
  [PASS] Conventional commit format
  [WARN] Subject line too long (78 chars, max 72)
  [FAIL] Contains debug console.log in src/auth.js:45

Score: 8/10 (80%)
```

## Integrating with PR Workflow

For GitHub PR review:

```bash
# Review last 5 commits before merging
python scripts/pr_review.py --repo . --commits 5

# Review specific PR branch
git checkout pr-branch
python scripts/pr_review.py --repo . --details
```

## Resources

### scripts/
- `pr_review.py`: Main review script that analyzes commits and PR style

### references/
- `conventional-commits.md`: Conventional commits specification and type guidelines
