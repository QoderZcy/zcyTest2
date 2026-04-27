#!/bin/bash
# TC-301: UserPromptSubmit 用户提交提示词前触发测试
INPUT=$(cat)
PROMPT=$(echo "$INPUT" | python3 -c "
import json,sys
data = json.load(sys.stdin)
prompt = data.get('prompt','')
if len(str(prompt)) > 100:
    prompt = str(prompt)[:100] + '...'
print(prompt)
" 2>/dev/null)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-301 PASS: UserPromptSubmit triggered, prompt=$PROMPT" >> /tmp/qoder-hooks-test.log
exit 0
