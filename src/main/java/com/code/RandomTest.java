package com.code;

import java.util.Arrays;

/**
 * @author xz
 * @Description 随机等概率 算法
 * @date 2021/8/30 0030 18:51
 **/
public class RandomTest {
    /**
     * Java 中的Math.random() 函数是一个等概率的随机函数,
     * 默认 [0, 1) 之间出现的数值等概率
     * 注意, 这个是需要在大数据量使用随机才能测试出的概率。
     */
    public static void main(String[] args) {
        int count = 0;
        int number = 100000;
        for (int i = 0; i < number; i++) {
            if (f() < 3) {
                count ++;
            }
        }
        System.out.println((double) (count) / (double)(number));
        count = 0;
        for (int i = 0; i < number; i++) {
            if (testF() == 0) {
                count ++;
            }
        }
        System.out.println((double) (count) / (double)(number));

        int[] counts = new int[40];
        for (int i = 0; i < number; i++) {
            int i1 = testTwo();
            counts[i1] ++;
        }
        System.out.println(Arrays.toString(counts));
        count = 0;
        for (int i = 0; i < number; i++) {
            if (testF1() == 0) {
                count ++;
            }
        }
        System.out.println((double) (count) / (double)(number));
    }

    /**
     * 问题, 当前存在一个等概率函数 f() , 等概率的返回 1 - 5 之间的整数,
     * 在只能使用 f() 的函数的基础之上, 写出一个函数等概率返回 1 - 7之间的整数
     * @return
     */
    public static int f() {
        return (int)(Math.random() * 5 + 1);
    }

    /**
     * 解决思路:
     *  1. 先将其转换为最简单的等概率数, 0 - 1, 作为2分之1的等概率
     *  2. 然后因为其 0 1 的结构 并且0 1出现的概率都是2分之一,
     *  则可以将其使用二进制来表示, 只需要计算出最大需要表示的数二进制位
     *  1 - 7 那么最大为 7, 所需进制位则是 2的3次方-1 = 111 也就是3个bit位
     *
     *  换位思考如果计算 10 - 33 那么最大为(33 - 10 = 23);
     */
    public static int testF() {

        // 1. 首先1 - 5 意味着 每个数出现的等概率则是 20%
        // 那么我们可以泽中选择一个数抛弃(偶数则不需要), 其概率均分到其他数
        // 选择number/2 左右两边转换概率为 50%
        int ans;
        do {
            ans = f();
        } while (ans == 3);
        return ans < 3 ? 0 : 1;
    }

    public static int testFOne() {
        // 2. 因为其0 1 的结构非常适合二进制计算,
        // 根据 1 - 7 得知最大为 7
        // 所需二进制位则是 2的3次方-1 = 111 也就是3个bit位
        return (testF() << 2) + (testF() << 1) + testF() + 1;
    }

    public static int testTwo() {
        // 假设此时需要计算 10 - 33, 那么最大为(33 - 10 = 23)
        // 最接近的二进制位为 2的5次方-1 =  11111
        int bitNumber = 4;
        int number = 0;
        for (int i = 4; i >= 0; i--) {
            number += testF() << i;
        }
        // 因为其二进制位 最大可达 31 故需要判断是否递归
        if (number > 23) {
            return testTwo();
        }
        // 加上这个偏移 33 - 10
        return number + 10;
    }
    /**
     * 问题：
     * 当前存在一个 f1() 函数(黑盒伪代码, 并不是真实实现), 以固定概率但不是等概率返回 0 , 1
     * 借助这个函数 编写一个函数做等概率返回
     */
    public static int f1() {
        return Math.random() > 0.65 ? 0 : 1;
    }

    /**
     * 因为其固定概率, 且非等概率, 那么一定存在一个数的出现的概率比另一个数出现概率高
     * 此时只需要将出现重复数的概率排除, 得到的则是等概率
     * @return
     */
    public static int testF1() {
        int number;
        do {
            number = f1();
        } while (number == f1());
        return number;
    }
}
