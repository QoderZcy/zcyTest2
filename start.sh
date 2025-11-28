#!/bin/bash

# 便签管理系统启动脚本

echo "========================================"
echo "   便签管理系统 - 赛博朋克风格"
echo "========================================"
echo ""

# 检查Java版本
echo "检查Java环境..."
if ! command -v java &> /dev/null
then
    echo "错误: 未找到Java，请先安装JDK 17或更高版本"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "错误: Java版本过低，需要JDK 17或更高版本"
    exit 1
fi
echo "✓ Java环境正常 (版本 $JAVA_VERSION)"
echo ""

# 检查Maven
echo "检查Maven..."
if ! command -v mvn &> /dev/null
then
    echo "警告: 未找到Maven命令"
    echo "请使用以下方式之一："
    echo "1. 安装Maven: sudo apt-get install maven"
    echo "2. 使用已编译的JAR文件运行"
    exit 1
fi
echo "✓ Maven已安装"
echo ""

# 编译项目
echo "编译项目..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "错误: 编译失败"
    exit 1
fi
echo "✓ 编译成功"
echo ""

# 启动应用
echo "启动便签管理系统..."
echo "访问地址: http://localhost:8080"
echo "H2控制台: http://localhost:8080/h2-console"
echo ""
echo "按 Ctrl+C 停止服务"
echo "========================================"
echo ""

mvn spring-boot:run
