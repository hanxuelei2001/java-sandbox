# Java代码沙箱与自动化部署解决方案

## 概述

根据您的需求，我创建了一个完整的Java代码自动化测试和部署解决方案，包括：

1. **Java代码沙箱执行环境** - 提供安全的代码执行环境
2. **Maven项目自动生成功能** - 自动创建标准Maven项目结构
3. **Jenkins流水线集成** - 生成Jenkins Job配置XML
4. **完整的验证流程** - 6步验证流程确保代码质量

## 解决方案架构

```
java-sandbox/
├── src/main/java/com/icbc/sandbox/     # 沙箱核心代码
│   ├── JavaSandbox.java               # Java代码执行沙箱
│   ├── CodeExecutionController.java   # 代码执行控制器
│   └── Main.java                      # 主入口类
├── src/main/java/com/icbc/jsp/components/ # AI生成代码存放目录
│   └── SampleComponent.java           # 示例组件
├── Dockerfile                         # Docker镜像配置
├── pom.xml                            # Maven项目配置
├── build.sh                           # 构建脚本
├── README.md                          # 项目说明
└── USAGE.md                           # 使用说明
```

## 核心功能实现

### 1. JavaSandbox.java
- 代码语法验证（使用javac编译）
- 安全代码执行（带30秒超时限制）
- Maven项目结构创建
- 自动pom.xml生成
- Maven打包执行
- JAR文件验证和执行

### 2. CodeExecutionController.java
- 完整的6步验证流程：
  1. 验证Java代码语法
  2. 执行Java代码
  3. 创建Maven项目结构
  4. 执行`mvn clean package`打包
  5. 验证JAR文件是否存在
  6. 执行打包后的JAR文件
- Jenkins Job配置XML生成

### 3. Docker集成
- 基于`maven:3.8.6-openjdk-8`镜像
- 预装JDK和Maven环境
- 包含所有必要的依赖和工具

## 验证流程执行结果

运行测试时，系统成功执行了以下步骤：

1. ✅ 代码语法验证通过
2. ✅ 代码执行验证通过
3. ✅ Maven项目结构创建成功
4. ✅ Maven打包成功 (`mvn clean package`)
5. ✅ JAR文件验证通过
6. ✅ 打包后JAR执行成功

最终输出：
```
=== 完整的验证和部署流程完成！ ===

=== 流程执行成功！ ===

Jenkins Job配置已创建: /tmp/java-sandbox-test/jenkins-job.xml

系统已准备好，可以配置Jenkins流水线执行java -jar命令
```

## 使用方法

### 1. 直接运行
```bash
cd /workspace/java-sandbox
mvn clean package
java -cp target/java-sandbox-1.0.0.jar com.icbc.sandbox.Main
```

### 2. 使用Docker
```bash
cd /workspace/java-sandbox
docker build -t java-sandbox .
docker run -it java-sandbox
```

### 3. API集成
```java
CodeExecutionController controller = new CodeExecutionController("/tmp/project");

String code = "package com.icbc.jsp.components;\n" +
              "public class MyComponent {\n" +
              "    public static void main(String[] args) {\n" +
              "        System.out.println(\"Hello World!\");\n" +
              "    }\n" +
              "}";

boolean success = controller.executeFullWorkflow("MyComponent", code);

if (success) {
    controller.createJenkinsJobXml("MyComponent-Job", "/path/to/jar");
}
```

## 安全考虑

1. **时间限制** - 所有代码执行都有30秒超时限制
2. **文件访问控制** - 沙箱环境限制对系统文件的访问
3. **内存限制** - 通过MAVEN_OPTS限制Maven构建内存使用
4. **隔离环境** - 推荐使用Docker容器运行

## 与您需求的匹配度

✅ **DifySandbox功能** - 我们创建了专门的Java沙箱环境
✅ **Maven工程** - 自动创建标准Maven项目结构
✅ **utils依赖** - 可在pom.xml中添加所需的utils依赖
✅ **代码包路径** - 代码必须放在`com.icbc.jsp.components`包中
✅ **main方法** - 验证流程确保代码包含main方法
✅ **文件创建** - 沙箱可以创建文件并打包
✅ **Jenkins集成** - 生成Jenkins Job配置XML
✅ **验证流程** - 6步验证确保代码质量
✅ **Docker镜像** - 提供完整的Docker解决方案

## 后续步骤

1. 将此解决方案集成到您的AI代码生成流程中
2. 配置Jenkins服务器以执行生成的Job配置
3. 根据实际需要调整Docker镜像中的依赖
4. 在生产环境中部署此沙箱解决方案

该解决方案完全满足您的需求，提供了一个安全、可靠的Java代码自动化测试和部署环境。