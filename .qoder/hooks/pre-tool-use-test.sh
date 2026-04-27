#!/bin/bash
# TC-401/402: PreToolUse 工具执行前触发 + 阻止危险命令测试
INPUT=$(cat)
TOOL_NAME=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('tool_name',''))" 2>/dev/null)
COMMAND=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('tool_input',{}).get('command',''))" 2>/dev/null)

echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-401 PASS: PreToolUse triggered, tool=$TOOL_NAME, command=$COMMAND" >> /tmp/qoder-hooks-test.log

# TC-402: 阻止危险 rm -rf 命令
if echo "$COMMAND" | grep -qE 'rm\s+.*-[a-z]*r[a-z]*f'; then
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-402 PASS: Blocked dangerous rm -rf command: $COMMAND" >> /tmp/qoder-hooks-test.log
  echo "BLOCKED: Dangerous rm -rf command detected: $COMMAND" >&2
  exit 2
fi

exit 0
