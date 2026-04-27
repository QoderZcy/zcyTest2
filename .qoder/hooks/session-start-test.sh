#!/bin/bash
# TC-101/102: SessionStart 会话启动触发测试
# 功能：记录会话启动事件到日志文件，区分启动来源
INPUT=$(cat)
EVENT=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('hook_event_name',''))" 2>/dev/null)
SOURCE=$(echo "$INPUT" | python3 -c "import json,sys; print(json.load(sys.stdin).get('source','unknown'))" 2>/dev/null)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-101/102 PASS: SessionStart triggered, event=$EVENT, source=$SOURCE" >> /tmp/qoder-hooks-test.log
exit 0
