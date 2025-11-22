package com.icbc.sandbox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 代码执行控制器
 * 实现完整的验证和执行流程
 */
public class CodeExecutionController {
    
    private final JavaSandbox sandbox;
    
    public CodeExecutionController(String projectPath) {
        this.sandbox = new JavaSandbox(projectPath);
    }
    
    /**
     * 执行完整的验证和部署流程
     */
    public boolean executeFullWorkflow(String className, String code) {
        System.out.println("=== 开始执行完整的Java代码验证和部署流程 ===");
        
        // 步骤1: 验证Java代码语法
        System.out.println("步骤1: 验证Java代码语法...");
        if (!sandbox.validateJavaCode(className, code)) {
            System.err.println("代码语法验证失败！");
            return false;
        }
        System.out.println("代码语法验证通过！");
        
        // 步骤2: 执行Java代码（在源码级别）
        System.out.println("步骤2: 执行Java代码...");
        if (!sandbox.executeJavaCode(className)) {
            System.err.println("代码执行失败！");
            return false;
        }
        System.out.println("代码执行成功！");
        
        // 步骤3: 创建Maven项目结构
        System.out.println("步骤3: 创建Maven项目结构...");
        if (!sandbox.createMavenProject(className, "1.0.0")) {
            System.err.println("创建Maven项目失败！");
            return false;
        }
        System.out.println("Maven项目创建成功！");
        
        // 步骤4: 执行Maven打包
        System.out.println("步骤4: 执行Maven打包...");
        if (!sandbox.executeMavenPackage()) {
            System.err.println("Maven打包失败！");
            return false;
        }
        System.out.println("Maven打包成功！");
        
        // 步骤5: 验证JAR文件是否存在
        System.out.println("步骤5: 验证JAR文件是否存在...");
        if (!sandbox.verifyJarExists()) {
            System.err.println("JAR文件不存在！");
            return false;
        }
        System.out.println("JAR文件验证通过！");
        
        // 步骤6: 执行打包后的JAR文件
        System.out.println("步骤6: 执行打包后的JAR文件...");
        if (!sandbox.executePackagedJar()) {
            System.err.println("JAR文件执行失败！");
            return false;
        }
        System.out.println("JAR文件执行成功！");
        
        System.out.println("=== 完整的验证和部署流程完成！ ===");
        return true;
    }
    
    /**
     * 将代码写入指定路径的文件
     */
    public boolean writeCodeToFile(String className, String code, String packageName) {
        try {
            String packagePath = packageName.replace(".", "/");
            String filePath = sandbox.getBaseDir() + "/src/main/java/" + packagePath + "/" + className + ".java";
            
            // 确保目录存在
            String dirPath = filePath.substring(0, filePath.lastIndexOf("/"));
            Files.createDirectories(Paths.get(dirPath));
            
            // 写入代码
            Files.write(Paths.get(filePath), code.getBytes());
            System.out.println("代码已写入文件: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("写入代码文件失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 创建Jenkins Job配置XML
     */
    public boolean createJenkinsJobXml(String jobName, String jarPath) {
        try {
            String xmlContent = generateJenkinsJobXml(jobName, jarPath);
            String xmlPath = sandbox.getBaseDir() + "/jenkins-job.xml";
            Files.write(Paths.get(xmlPath), xmlContent.getBytes());
            
            System.out.println("Jenkins Job配置已创建: " + xmlPath);
            return true;
        } catch (IOException e) {
            System.err.println("创建Jenkins Job配置失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 生成Jenkins Job配置XML
     */
    private String generateJenkinsJobXml(String jobName, String jarPath) {
        return "<?xml version='1.1' encoding='UTF-8'?>\n" +
               "<project>\n" +
               "  <description>Generated job for " + jobName + "</description>\n" +
               "  <keepDependencies>false</keepDependencies>\n" +
               "  <properties/>\n" +
               "  <scm class=\"hudson.scm.NullSCM\"/>\n" +
               "  <canRoam>true</canRoam>\n" +
               "  <disabled>false</disabled>\n" +
               "  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
               "  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
               "  <triggers/>\n" +
               "  <concurrentBuild>false</concurrentBuild>\n" +
               "  <builders>\n" +
               "    <hudson.tasks.Shell>\n" +
               "      <command>java -jar " + jarPath + "</command>\n" +
               "    </hudson.tasks.Shell>\n" +
               "  </builders>\n" +
               "  <publishers/>\n" +
               "  <buildWrappers/>\n" +
               "</project>";
    }
    
    public JavaSandbox getSandbox() {
        return sandbox;
    }
}