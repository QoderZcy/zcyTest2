#!/bin/bash
# TC-701: Stop Agent响应完成触发测试
INPUT=$(cat)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-701 PASS: Stop triggered" >> /tmp/qoder-hooks-test.log
exit 0
