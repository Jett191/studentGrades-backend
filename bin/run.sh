#!/usr/bin/env bash

# ─────────────────────────────────────────────────────────────────────────────
# [Info] 运行打好的 jar 包（相当于 Windows 下的 java -jar）
# ─────────────────────────────────────────────────────────────────────────────

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
# 进入管理员后台模块的 target 目录
cd "$SCRIPT_DIR/../ruoyi-admin/target" || exit 1

# JVM 参数
JAVA_OPTS="-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"

echo "[Info] Starting ruoyi-admin.jar with options: $JAVA_OPTS"
java $JAVA_OPTS -jar ruoyi-admin.jar

read -n1 -r -p "Press any key to exit..." key
echo