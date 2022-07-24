package com.code;

/**
 * @author xz
 * @ClassName BitMapTest
 * @Description
 * @date 2021/9/1 0001 23:45
 **/
public class BitTest {

    public static void main(String[] args) {
//        System.out.println(1 / 64);
//        System.out.println(bitAdd(3, 98));
//        System.out.println(bitSub(3, 98));
//        System.out.println(bitMulti(3, 98));
//        System.out.println(div(95, 3));
//        System.out.println(3 + (3 << 1) + (3 << 3));
//        bitMapTest();
    }

    /**
     * bit 加法的原理 则是
     * 1. 得到两数的 无进位二进制 x = a ^ b
     * 2. 得到两数的 进位二进制 y = a & b << 1
     * 3. 如果进位 y == 0, 意味着本次加 无进位, 直接返回 x
     * 4. 如果进位 y == 1, 重复 1 - 2步,  a,b 此时 就是 x,y
     */
    public static int bitAdd(int a, int b) {
        int result = 0;
        // 进位不等于 0
        while (b != 0) {
            // 先保存结果集是本次 无进位结果
            result = (b ^ a);
            // 计算进位信息
            b = (b & a) << 1;
            // 覆盖 a, 当有进位信息时 后续循环则还需要计算。
            a = result;
        }

        return result;
    }

    /**
     * 那么减法只需要将  被减数 + (减少数取反 = ~b + 1);
     */
    public static int bitSub(int a, int b) {
        return bitAdd(a, bitAdd(~b, 1));
    }

    public static int bitAdd1(int a, int b) {
        int result = 0;
        while (b != 0) {
            result = (a ^ b);
            b = (a & b) << 1;
            a = result;
        }

        return result;
    }

    /**
     * 不使用 * 的情况下进行乘法
     * 乘法在二进制的逻辑则是
     * 例如 3 * 10
     *  3 = 0000 0011
     *  10 = 0000 1010
     *  任选一个二进制开始, 依次遍历二进制位, 0 不变, 1 则加上 另一个二进制 << 当前进制位
     *  例如 从 4 开始
     *  1. 第0个进制位 0 所以 0000 0000
     *  2. 第1个进制位 1 所以 0000 0011 左移一位 0000 0110 可以理解为这里拆分为 2 * 3
     *  3. 第2个进制位 0 所以 0000 0000
     *  4. 第3个进制位 1 所以 0000 0011 左移三位 0001 1000 这里拆分为了 8 * 3
     *  5. 遍历完毕, 上诉步骤结果集相加的到
     *  0001 1110 = 2 * 3 + 8 * 3 = 30;
     *  0001 1110 = (3 << 1) + (3 << 3)
     */
    public static int bitMulti(int one, int two) {
        int result = 0;
        while (two != 0) {
            if ((two & 1) != 0) {
                result = bitAdd(result, one);
            }
            two = two >>> 1;
            one = one << 1;
        }
        return result;
    }

    /**
     * 而除法 则是通过在乘法的逆向操作, a / b = c 那么 a = b * c;
     *
     * b 在左移 N 个二进制位后 最接近a(<= a)则 N 就是 C的最高二进制位
     * 因为二进制中的乘法计算, 最终就是就是 （2的n次方 - 1）* b + (2的n次方 - 1）* b
     * n 则就是 c中有效的 1进制位
     *
     * a = 15 = 0000 1111
     * b = 5 = 0000 0101
     * << 1 <= a, 那么 c 此时 0000 0010;
     * a 去除 b << 1后 0000 0101
     * << 0 <= a, 那么 c 此时 0000 0011;
     * a 去除 b << 0后 0000 0000
     * 此时 b > a, 运算完毕,
     * c = 0000 0011 = 3;
     * @param a
     * @param b
     * @return
     */
    public static int div(int a, int b) {
        int result = 0;
        // 因为不能预估从哪里开始位移, 因 int 32位 一位符号位 则 31,
        // 整数至少会有一位 所以从30开始
        for (int i = 30; i >= 0 && a >= b; i --) {
            // 因为 int 有符号位,可能溢出,不能采用 b << 1的方式,
            // 通过被除数 a 右移得到同样效果
            if ((a >> i) >= b) {
                result |= 1 << i;
                a = a - (b << i);
            }
        }
        return result;
    }

    /**
     * 给定一个数 n. 计算出该范围内 每个值对应二进制位1的个数
     * @param n
     * @return
     */
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        if (n == 0) {
            return result;
        }
        result[1] = 1;
        for (int i = 2; i <= n; i ++) {
            if (i % 2 == 0) {
                // 为什么偶数可以直接得到 i / 2 后的个数？
                /**
                 * 在二进制中, 偶数最后一位必定为0, 那么 i >> 1 位的1进制位一定是和
                 * 结果对应的进制位相同,
                 * 例如
                 * 12 = 0000 1100
                 * 6 = 0000 0110;
                 * 12 / 2 = 6; 12 >> 1
                 */
                result[i] = result[i >> 1];
            } else {
                /**
                 * 而奇数最后位必定为 1, 那么只需要 >> 1位后 补上被删除的 1 即可
                 */
                result[i] = result[i >> 1] + 1;
            }

        }
        return result;
    }

    static int[] countBits1(int n) {
        int[] result = new int[n + 1];
        for (int i = 1; i <= n; i ++) {
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;
    }

    /**
     * 构建一个BitMap 判断某个数是否存在
     */
    static class BigMap {
        private long[] bitMap;

        /**
         * 根据可存储最大值定义位图大小。
         * @param maxVal
         */
        public BigMap(int maxVal) {
            bitMap = new long[(maxVal >> 6) + 1];
        }

        /**
         * 新增时的思路:
         * 1. 一个long 占 64位, 那么可以以一 bit 标识一个数字, 一个long 则可以表示 0 - 63
         * 2. 由此, 我们可以通过 >> 6 (64) 计算出某个数所在long位置，通过取余 得到该数所在二进制位
         */
        public void add(int num) {
            bitMap[num >> 6] |= (1L << (num & 63));
        }

        /**
         * 而删除, 无非就是将num所在数组下的long 对应的二进制位 置为 0
         */
        public void delete(int num) {
            bitMap[num >> 6] &= (~(1L << (num & 63)));
        }

        /**
         * 判断是否存在则 获取num 所在数组节点下 long的二进制位 是否为1
         */
        public boolean contains(int num) {
            return ((bitMap[num >> 6] >> (num & 63)) & 1) == 1L;
        }
    }

    public static void bitMapTest() {
        BigMap map = new BigMap(1024);
        map.add(1);
        map.add(2);
        map.add(3);
        map.add(4);
        map.delete(2);
        System.out.println(map.contains(1));
        System.out.println(map.contains(2));
        System.out.println(map.contains(4));
        System.out.println(map.contains(5));
        System.out.println(map.contains(3));

    }
}
