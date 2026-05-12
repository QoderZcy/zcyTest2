package com.photo.example;

/**
 * HelloWorldExample - Java基本特性示例
 * 演示类、方法、变量、循环、条件判断等基础语法
 */
public class HelloWorldExample {

    // 类变量（静态变量）
    private static int callCount = 0;

    // 实例变量
    private String name;

    /**
     * 构造方法
     * @param name 名称
     */
    public HelloWorldExample(String name) {
        this.name = name;
    }

    /**
     * 问候方法 - 演示方法定义与返回值
     * @return 问候语
     */
    public String greet() {
        callCount++;
        return "你好, " + name + "! 欢迎学习Java基本特性。";
    }

    /**
     * 判断奇偶 - 演示条件判断
     * @param number 待判断的数字
     * @return 奇偶描述
     */
    public static String checkOddEven(int number) {
        if (number % 2 == 0) {
            return number + " 是偶数";
        } else {
            return number + " 是奇数";
        }
    }

    /**
     * 计算阶乘 - 演示循环与递归
     * @param n 阶乘数
     * @return n的阶乘
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("阶乘数不能为负数");
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * 打印九九乘法表 - 演示嵌套循环
     */
    public static void printMultiplicationTable() {
        System.out.println("===== 九九乘法表 =====");
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.printf("%d×%d=%-4d", j, i, i * j);
            }
            System.out.println();
        }
    }

    /**
     * 数组操作 - 演示数组与增强for循环
     * @param numbers 整数数组
     * @return 数组元素之和
     */
    public static int sumArray(int[] numbers) {
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
    }

    /**
     * 主方法 - 程序入口
     */
    public static void main(String[] args) {
        // 1. 创建对象并调用实例方法
        HelloWorldExample example = new HelloWorldExample("开发者");
        System.out.println(example.greet());

        // 2. 条件判断示例
        System.out.println("\n--- 条件判断 ---");
        int[] testNumbers = {3, 8, 15, 22};
        for (int num : testNumbers) {
            System.out.println(checkOddEven(num));
        }

        // 3. 循环与阶乘计算
        System.out.println("\n--- 阶乘计算 ---");
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + "! = " + factorial(i));
        }

        // 4. 九九乘法表
        System.out.println();
        printMultiplicationTable();

        // 5. 数组操作
        System.out.println("\n--- 数组求和 ---");
        int[] data = {10, 20, 30, 40, 50};
        System.out.println("数组元素之和: " + sumArray(data));

        // 6. 字符串操作
        System.out.println("\n--- 字符串操作 ---");
        String message = "Hello World";
        System.out.println("原始字符串: " + message);
        System.out.println("转大写: " + message.toUpperCase());
        System.out.println("字符串长度: " + message.length());
        System.out.println("替换World为 Java: " + message.replace("World", "Java"));

        // 7. 统计方法调用次数
        System.out.println("\n--- 其他信息 ---");
        System.out.println("greet()方法被调用了 " + callCount + " 次");
    }
}
