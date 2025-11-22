package com.icbc.sandbox;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * Java代码沙箱执行器
 * 提供安全的Java代码执行环境
 */
public class JavaSandbox {
    
    private final String baseDir;
    private final String sourceDir;
    private final String targetDir;
    
    public JavaSandbox(String baseDir) {
        this.baseDir = baseDir;
        this.sourceDir = baseDir + "/src/main/java";
        this.targetDir = baseDir + "/target";
        
        // 创建必要的目录结构
        createDirectories();
    }
    
    private void createDirectories() {
        try {
            Files.createDirectories(Paths.get(sourceDir));
            Files.createDirectories(Paths.get(targetDir));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directories", e);
        }
    }
    
    /**
     * 验证Java代码语法
     */
    public boolean validateJavaCode(String className, String code) {
        String filePath = sourceDir + "/com/icbc/jsp/components/" + className + ".java";
        try {
            // 写入代码到文件
            Files.write(Paths.get(filePath), code.getBytes());
            
            // 编译验证
            ProcessBuilder pb = new ProcessBuilder("javac", filePath);
            pb.directory(new File(baseDir));
            Process process = pb.start();
            
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            System.err.println("Error validating Java code: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 执行Java代码
     */
    public boolean executeJavaCode(String className) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", sourceDir, "com.icbc.jsp.components." + className);
            pb.directory(new File(baseDir));
            Process process = pb.start();
            
            // 设置超时时间
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return false;
            }
            
            return process.exitValue() == 0;
        } catch (Exception e) {
            System.err.println("Error executing Java code: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 创建Maven项目结构
     */
    public boolean createMavenProject(String artifactId, String version) {
        try {
            // 创建Maven目录结构
            Files.createDirectories(Paths.get(baseDir + "/src/main/java/com/icbc/jsp/components"));
            Files.createDirectories(Paths.get(baseDir + "/src/test/java"));
            
            // 创建pom.xml
            String pomContent = generatePomXml(artifactId, version);
            Files.write(Paths.get(baseDir + "/pom.xml"), pomContent.getBytes());
            
            return true;
        } catch (Exception e) {
            System.err.println("Error creating Maven project: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 生成pom.xml内容
     */
    private String generatePomXml(String artifactId, String version) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
               "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
               "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 \n" +
               "         http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
               "    <modelVersion>4.0.0</modelVersion>\n" +
               "\n" +
               "    <groupId>com.icbc.jsp</groupId>\n" +
               "    <artifactId>" + artifactId + "</artifactId>\n" +
               "    <version>" + version + "</version>\n" +
               "    <packaging>jar</packaging>\n" +
               "\n" +
               "    <properties>\n" +
               "        <maven.compiler.source>8</maven.compiler.source>\n" +
               "        <maven.compiler.target>8</maven.compiler.target>\n" +
               "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
               "    </properties>\n" +
               "\n" +
               "    <dependencies>\n" +
               "        <!-- 添加您需要的utils依赖 -->\n" +
               "    </dependencies>\n" +
               "\n" +
               "    <build>\n" +
               "        <plugins>\n" +
               "            <plugin>\n" +
               "                <groupId>org.apache.maven.plugins</groupId>\n" +
               "                <artifactId>maven-compiler-plugin</artifactId>\n" +
               "                <version>3.8.1</version>\n" +
               "                <configuration>\n" +
               "                    <source>8</source>\n" +
               "                    <target>8</target>\n" +
               "                </configuration>\n" +
               "            </plugin>\n" +
               "            <plugin>\n" +
               "                <groupId>org.apache.maven.plugins</groupId>\n" +
               "                <artifactId>maven-jar-plugin</artifactId>\n" +
               "                <version>3.2.0</version>\n" +
               "                <configuration>\n" +
               "                    <archive>\n" +
               "                        <manifest>\n" +
               "                            <mainClass>com.icbc.jsp.components." + artifactId + "</mainClass>\n" +
               "                        </manifest>\n" +
               "                    </archive>\n" +
               "                </configuration>\n" +
               "            </plugin>\n" +
               "        </plugins>\n" +
               "    </build>\n" +
               "</project>";
    }
    
    /**
     * 执行Maven打包
     */
    public boolean executeMavenPackage() {
        try {
            ProcessBuilder pb = new ProcessBuilder("mvn", "clean", "package");
            pb.directory(new File(baseDir));
            pb.environment().put("MAVEN_OPTS", "-Xmx512m");
            
            Process process = pb.start();
            
            // 读取输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            System.err.println("Error executing Maven package: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 验证JAR文件是否存在
     */
    public boolean verifyJarExists() {
        try {
            Path targetPath = Paths.get(targetDir);
            if (!Files.exists(targetPath)) {
                return false;
            }
            
            // 查找JAR文件
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(targetPath, "*.jar")) {
                for (Path path : stream) {
                    if (path.toString().endsWith(".jar") && !path.toString().contains("original-")) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error verifying JAR file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 执行打包后的JAR文件
     */
    public boolean executePackagedJar() {
        try {
            Path targetPath = Paths.get(targetDir);
            
            // 查找JAR文件
            String jarPath = null;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(targetPath, "*.jar")) {
                for (Path path : stream) {
                    if (path.toString().endsWith(".jar") && !path.toString().contains("original-")) {
                        jarPath = path.toString();
                        break;
                    }
                }
            }
            
            if (jarPath == null) {
                System.err.println("No JAR file found in target directory");
                return false;
            }
            
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", jarPath);
            pb.directory(new File(baseDir));
            Process process = pb.start();
            
            // 设置超时时间
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return false;
            }
            
            return process.exitValue() == 0;
        } catch (Exception e) {
            System.err.println("Error executing packaged JAR: " + e.getMessage());
            return false;
        }
    }
    
    public String getBaseDir() {
        return baseDir;
    }
}