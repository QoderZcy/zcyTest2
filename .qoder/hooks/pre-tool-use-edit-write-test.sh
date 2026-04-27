#!/bin/bash
# TC-403: PreToolUse 自动批准测试 - permissionDecision: allow
# TC-406: matcher 正则匹配多工具 (Edit|Write)
INPUT=$(cat)
TOOL_NAME=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('tool_name',''))" 2>/dev/null)
FILE_PATH=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('tool_input',{}).get('file_path',''))" 2>/dev/null)

echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-403/406 PASS: PreToolUse(Edit|Write) triggered, tool=$TOOL_NAME, file=$FILE_PATH" >> /tmp/qoder-hooks-test.log

# TC-403: 返回 permissionDecision: allow，自动批准 Edit/Write 操作
echo '{"hookSpecificOutput":{"hookEventName":"PreToolUse","permissionDecision":"allow","permissionDecisionReason":"Auto-approved by TC-403 hook"}}'
exit 0
