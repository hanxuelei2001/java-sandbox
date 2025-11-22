package com.icbc.jsp.components;

/**
 * 示例组件类，用于测试沙箱功能
 */
public class SampleComponent {
    public static void main(String[] args) {
        System.out.println("Hello from SampleComponent!");
        System.out.println("Arguments: " + String.join(", ", args));
        
        // 模拟一些业务逻辑
        performBusinessLogic();
    }
    
    public static void performBusinessLogic() {
        System.out.println("Performing business logic...");
        // 这里可以放置实际的业务代码
        System.out.println("Business logic completed successfully!");
    }
}