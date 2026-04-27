#!/bin/bash
# TC-1403: PreToolUse 超时配置测试
# 该脚本会 sleep 10 秒来模拟超时，配合 settings 中的 timeout: 5 使用
INPUT=$(cat)
TOOL_NAME=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('tool_name',''))" 2>/dev/null)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-1403 INFO: Timeout test started, tool=$TOOL_NAME, will sleep 10s" >> /tmp/qoder-hooks-test.log
sleep 10
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-1403 INFO: Timeout test completed (should not see this if timeout=5 works)" >> /tmp/qoder-hooks-test.log
exit 0
