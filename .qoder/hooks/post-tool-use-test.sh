#!/bin/bash
# TC-501: PostToolUse 成功执行后触发测试
INPUT=$(cat)
TOOL_NAME=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('tool_name',''))" 2>/dev/null)
TOOL_RESULT=$(echo "$INPUT" | python3 -c "
import json,sys
data = json.load(sys.stdin)
result = data.get('tool_result','')
# 截断过长结果
if len(str(result)) > 100:
    result = str(result)[:100] + '...'
print(result)
" 2>/dev/null)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-501 PASS: PostToolUse triggered, tool=$TOOL_NAME, result=$TOOL_RESULT" >> /tmp/qoder-hooks-test.log
exit 0
