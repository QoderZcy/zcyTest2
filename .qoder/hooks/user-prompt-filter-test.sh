#!/bin/bash
# TC-302: UserPromptSubmit 拒绝包含敏感信息的提示词
INPUT=$(cat)
PROMPT=$(echo "$INPUT" | python3 -c "
import json,sys
data = json.load(sys.stdin)
prompt = data.get('prompt','')
if len(str(prompt)) > 200:
    prompt = str(prompt)[:200] + '...'
print(prompt)
" 2>/dev/null)

echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-302 INFO: UserPromptSubmit checking, prompt=$PROMPT" >> /tmp/qoder-hooks-test.log

# TC-302: 拒绝包含 API Key 或密码等敏感信息的提示词
# 匹配关键词，支持中文、英文各种语境
if echo "$PROMPT" | grep -qiE '(api[_-]?key|password|secret[_-]?key|token|AKIA)'; then
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-302 PASS: Blocked prompt with sensitive info" >> /tmp/qoder-hooks-test.log
  echo "BLOCKED: Prompt contains sensitive information (API key, password, etc.). Please remove it and try again." >&2
  exit 2
fi

exit 0
