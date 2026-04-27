#!/bin/bash
# TC-201: SessionEnd 会话结束触发测试
INPUT=$(cat)
EVENT=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('hook_event_name',''))" 2>/dev/null)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-201 PASS: SessionEnd triggered, event=$EVENT" >> /tmp/qoder-hooks-test.log
exit 0
