# Java代码沙箱使用说明

## 概述
本系统提供一个完整的Java代码自动化测试和部署解决方案，包括：
- Java代码沙箱执行环境
- Maven项目自动生成功能
- Jenkins流水线集成
- 完整的验证流程

## 目录结构
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
└── USAGE.md                           # 本使用说明
```

## 功能特性

### 1. Java代码验证
- 语法验证：使用`javac`编译验证代码语法
- 执行验证：运行代码验证逻辑正确性
- 安全执行：在受控环境中执行代码

### 2. Maven项目生成
- 自动创建标准Maven目录结构
- 生成包含正确依赖的pom.xml文件
- 设置主类路径为`com.icbc.jsp.components`

### 3. 完整验证流程
1. 验证Java代码语法
2. 执行Java代码
3. 创建Maven项目结构
4. 执行`mvn clean package`打包
5. 验证JAR文件是否存在
6. 执行打包后的JAR文件

### 4. Jenkins集成
- 自动生成Jenkins Job配置XML
- 为自动化部署做准备

## 使用方法

### 方法1：直接运行（需要已安装JDK和Maven）
```bash
# 构建项目
./build.sh

# 运行测试
./build.sh run
```

### 方法2：使用Maven构建
```bash
# 构建项目
mvn clean install

# 运行主类
java -jar target/java-sandbox.jar
```

### 方法3：使用Docker
```bash
# 构建Docker镜像
docker build -t java-sandbox .

# 运行容器
docker run -it java-sandbox

# 在容器中运行测试
docker run -it java-sandbox java -jar target/java-sandbox.jar
```

## API使用示例

```java
// 创建控制器实例
CodeExecutionController controller = new CodeExecutionController("/tmp/project");

// AI生成的Java代码
String code = "package com.icbc.jsp.components;\n" +
              "public class MyComponent {\n" +
              "    public static void main(String[] args) {\n" +
              "        System.out.println(\"Hello World!\");\n" +
              "    }\n" +
              "}";

// 执行完整验证流程
boolean success = controller.executeFullWorkflow("MyComponent", code);

if (success) {
    // 创建Jenkins Job配置
    controller.createJenkinsJobXml("MyComponent-Job", "/path/to/jar");
}
```

## 安全考虑

1. 代码执行时间限制：所有代码执行都有30秒超时限制
2. 文件系统访问控制：沙箱环境限制对系统文件的访问
3. 网络访问限制：默认情况下禁用网络访问
4. 内存限制：通过MAVEN_OPTS限制Maven构建内存使用

## Jenkins流水线集成

系统会自动生成Jenkins Job配置XML，用于执行`java -jar`命令。生成的配置包含：

- Job描述
- Shell构建步骤执行JAR文件
- 基本的构建配置

## 自定义配置

您可以根据需要修改以下配置：

1. **pom.xml**: 添加项目特定的依赖
2. **Dockerfile**: 修改基础镜像或添加额外工具
3. **JavaSandbox.java**: 调整执行参数和安全限制
4. **CodeExecutionController.java**: 修改验证流程

## 注意事项

1. 所有生成的代码必须放在`com.icbc.jsp.components`包中
2. 生成的代码必须包含`main`方法
3. 系统会自动创建Maven项目结构
4. 验证流程确保代码可以成功编译、执行和打包