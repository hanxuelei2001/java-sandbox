# Java代码沙箱与自动化部署系统

## 概述
本项目提供一个完整的Java代码自动化测试和部署解决方案，包括：
- Java代码沙箱执行环境
- Maven项目自动生成功能
- Jenkins流水线集成
- 完整的验证流程

## 架构流程
1. 接收AI生成的Java代码
2. 验证代码语法和可执行性
3. 创建Maven项目结构
4. 执行`mvn clean package`打包
5. 验证生成的JAR文件
6. 配置Jenkins流水线
7. 执行`java -jar`验证

## 技术栈
- Docker容器化环境
- JDK 8+
- Maven 3.6+
- Jenkins CI/CD