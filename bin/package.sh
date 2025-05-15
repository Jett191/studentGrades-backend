#!/usr/bin/env bash

# ─────────────────────────────────────────────────────────────────────────────
# [Info] 打包 Web 工程，生成 .war/.jar（相当于 Windows 下的 mvn clean package）
# ─────────────────────────────────────────────────────────────────────────────

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR/.." || exit 1

# 跳过测试，生成包
mvn clean package -Dmaven.test.skip=true

read -n1 -r -p "Press any key to continue..." key
echo