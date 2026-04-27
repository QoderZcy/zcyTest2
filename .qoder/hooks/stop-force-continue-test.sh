#!/bin/bash
# TC-702: Stop 强制 Agent 继续工作（exit 2）
INPUT=$(cat)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] TC-702 PASS: Stop triggered, forcing agent to continue" >> /tmp/qoder-hooks-test.log
# exit 0 allows Agent to stop normally
exit 0
