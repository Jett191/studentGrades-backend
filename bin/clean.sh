#!/usr/bin/env bash

# ─────────────────────────────────────────────────────────────────────────────
# [Info] 清理工程 target 生成目录（相当于 Windows 下的 mvn clean）
# ─────────────────────────────────────────────────────────────────────────────

# 确保脚本所在目录
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
# 回到项目根目录（假设 clean.sh 在 scripts/ 或 bin/ 下）
cd "$SCRIPT_DIR/.." || exit 1

# 调用 Maven clean
mvn clean

# 等待用户确认
read -n1 -r -p "Press any key to continue..." key
echo