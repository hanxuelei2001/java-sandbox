# 使用包含JDK和Maven的基础镜像
FROM maven:3.8.6-openjdk-8

# 设置工作目录
WORKDIR /app

# 复制项目文件
COPY . /app

# 创建必要的目录结构
RUN mkdir -p src/main/java/com/icbc/sandbox \
    && mkdir -p src/main/java/com/icbc/jsp/components

# 复制源代码
COPY src/main/java/com/icbc/sandbox/JavaSandbox.java /app/src/main/java/com/icbc/sandbox/
COPY src/main/java/com/icbc/sandbox/CodeExecutionController.java /app/src/main/java/com/icbc/sandbox/
COPY src/main/java/com/icbc/sandbox/Main.java /app/src/main/java/com/icbc/sandbox/
COPY src/main/java/com/icbc/jsp/components/SampleComponent.java /app/src/main/java/com/icbc/jsp/components/

# 安装额外依赖（如果需要）
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 设置环境变量
ENV MAVEN_OPTS="-Xmx512m"

# 创建一个默认的pom.xml（如果不存在）
RUN if [ ! -f pom.xml ]; then \
        echo '<?xml version="1.0" encoding="UTF-8"?>' > pom.xml && \
        echo '<project xmlns="http://maven.apache.org/POM/4.0.0"' >> pom.xml && \
        echo '         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' >> pom.xml && \
        echo '         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">' >> pom.xml && \
        echo '    <modelVersion>4.0.0</modelVersion>' >> pom.xml && \
        echo '    <groupId>com.icbc.jsp</groupId>' >> pom.xml && \
        echo '    <artifactId>java-sandbox</artifactId>' >> pom.xml && \
        echo '    <version>1.0.0</version>' >> pom.xml && \
        echo '    <packaging>jar</packaging>' >> pom.xml && \
        echo '    <properties>' >> pom.xml && \
        echo '        <maven.compiler.source>8</maven.compiler.source>' >> pom.xml && \
        echo '        <maven.compiler.target>8</maven.compiler.target>' >> pom.xml && \
        echo '        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>' >> pom.xml && \
        echo '    </properties>' && \
        echo '</project>' >> pom.xml; \
    fi

# 暴露端口（如果需要提供API服务）
EXPOSE 8080

# 默认命令
CMD ["tail", "-f", "/dev/null"]