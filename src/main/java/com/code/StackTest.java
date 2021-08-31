package com.code;

import java.util.Stack;

/**
 * @author xz
 * @Description 栈的应用
 * @date 2021/8/30 0030 22:09
 **/
public class StackTest {
    public static void main(String[] args) {
        System.out.println(stackTest("[[]][]"));

    }
    /**
     * 判断字符串是否成对出现
     * 例如：'[]' '[[]]' '[][]'
     * @return
     */
    public static boolean stackTest(String s) {
        // 解决方案, 使用栈 出栈的原理, 匹配字符串成对 将其出栈
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (']' == chars[i]) {
                if (stack.isEmpty()) {
                    return false;
                }
                Character pop = stack.pop();
                if (pop != '[') {
                    return false;
                }
            } else {
                stack.push(chars[i]);
            }
        }
        // 栈不为空说明有未匹配的单字符
        return stack.isEmpty();
    }

}
