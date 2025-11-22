#!/bin/bash

# Java沙箱项目构建脚本

set -e  # 遇到错误时退出

echo "开始构建Java沙箱项目..."

# 创建项目结构
mkdir -p src/main/java/com/icbc/sandbox
mkdir -p src/main/java/com/icbc/jsp/components

# 编译Java源代码
echo "编译Java源代码..."
javac -d target/classes \
  src/main/java/com/icbc/sandbox/JavaSandbox.java \
  src/main/java/com/icbc/sandbox/CodeExecutionController.java \
  src/main/java/com/icbc/sandbox/Main.java \
  src/main/java/com/icbc/jsp/components/SampleComponent.java

# 创建JAR文件
echo "创建JAR文件..."
jar cfe target/java-sandbox.jar com.icbc.sandbox.Main -C target/classes .

echo "构建完成！"

# 如果提供了参数，运行测试
if [ "$1" = "run" ]; then
    echo "运行测试..."
    java -jar target/java-sandbox.jar
fi