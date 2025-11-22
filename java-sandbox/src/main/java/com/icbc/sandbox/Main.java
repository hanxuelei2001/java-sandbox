package com.icbc.sandbox;

/**
 * 主类 - 演示完整的Java代码验证和部署流程
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Java代码沙箱与自动化部署系统启动");
        
        // 创建一个临时项目目录
        String projectPath = "/tmp/java-sandbox-test";
        
        // 创建控制器
        CodeExecutionController controller = new CodeExecutionController(projectPath);
        
        // 示例Java代码 - 这是AI可能生成的代码
        String sampleCode = "package com.icbc.jsp.components;\n\n" +
                           "/**\n" +
                           " * 示例组件类\n" +
                           " */\n" +
                           "public class TestComponent {\n" +
                           "    public static void main(String[] args) {\n" +
                           "        System.out.println(\"Hello from TestComponent!\");\n" +
                           "        System.out.println(\"Testing business logic...\");\n" +
                           "        \n" +
                           "        // 模拟业务逻辑\n" +
                           "        processData();\n" +
                           "        \n" +
                           "        System.out.println(\"TestComponent executed successfully!\");\n" +
                           "    }\n" +
                           "    \n" +
                           "    public static void processData() {\n" +
                           "        System.out.println(\"Processing data...\");\n" +
                           "        // 实际的业务逻辑代码\n" +
                           "        for (int i = 0; i < 5; i++) {\n" +
                           "            System.out.println(\"Processing item: \" + (i + 1));\n" +
                           "        }\n" +
                           "        System.out.println(\"Data processing completed.\");\n" +
                           "    }\n" +
                           "}";
        
        // 执行完整的验证和部署流程
        boolean success = controller.executeFullWorkflow("TestComponent", sampleCode);
        
        if (success) {
            System.out.println("\\n=== 流程执行成功！===\\n");
            
            // 创建Jenkins Job配置
            String jarPath = projectPath + "/target/TestComponent-1.0.0.jar";
            controller.createJenkinsJobXml("TestComponent-Job", jarPath);
            
            System.out.println("\\n系统已准备好，可以配置Jenkins流水线执行java -jar命令");
        } else {
            System.out.println("\\n=== 流程执行失败！===\\n");
        }
    }
}