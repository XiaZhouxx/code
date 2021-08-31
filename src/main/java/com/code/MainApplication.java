package com.code;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author xz
 * @ClassName MainApplication
 * @Description
 * @date 2021/8/7 0007 18:25
 **/
public class MainApplication {
    public static int i = 0;

    private static long ctl;

    public static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            Field i1 = MainApplication.class.getDeclaredField("i");
            i1.setAccessible(true);
            ctl = unsafe.staticFieldOffset(i1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 优化获取当前数组中的任意两个下标之间的对应累加和
     * 假设数组是静态的,下面求和方法会被调用N次, 如何优化
     *
     * @param beginIdx
     * @param endIdx
     * @return
     */
    static int[] arr = new int[]{1, 2, 4, 5, 6, 6, 8, 10};

    public static int getSum(int beginIdx, int endIdx) {
        int sub = beginIdx > 0 ? helpArr[beginIdx - 1] : 0;
        return helpArr[endIdx] - sub;
    }

    /**
     * 将上方数组优化, 计算出每个下标对应的前缀和
     */
    static int[] helpArr = optimizationArr(arr);

    private static int[] optimizationArr(int[] arr) {
        int[] helpArr = new int[arr.length];
        helpArr[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            helpArr[i] = arr[i] + helpArr[i - 1];
        }
        System.out.println(Arrays.toString(helpArr));
        return helpArr;
    }

    public static void main(String[] args) {
        System.out.println(getSum(0, 0));
        unsafe.compareAndSwapInt(MainApplication.class, ctl, 0, 10);
        System.out.println(i);
//        int a = 1;
//
//        System.out.println(~a);
//        int a1[] = {1,5,6,547,231,1};
//        System.out.println(Arrays.toString(selectSort(a1)));
//        System.out.println(Arrays.toString(bobbleSort(a1)));
//        a1 = new int[]{1,5,6,547,231,1};
//        System.out.println(Arrays.toString(insertSort(a1)));
        System.out.println(binarySearchLeft(new int[]{1, 3, 3, 3, 5, 6, 8}, 4));
        System.out.println(binarySearch(new int[]{8, 7, 6, 5, 4, 3}, 8));
        System.out.println(searchLocalMinimum(new int[]{4, 3, 3, 5, 5, 8, 8,1, 9}));
    }

    /**
     * 二分搜索 在数组明确无序的情况下, 暴力二分
     */
    public static boolean binarySearch(int[] args, int num) {
        int left = 0;
        int right = args.length - 1;
        int binary = (left + right) / 2;
        if (args[binary] == num) {
            return true;
        }

        return binarySearchTemp(args, num, left, binary - 1)
                || binarySearchTemp(args, num, binary + 1, right);
    }

    public static boolean binarySearchTemp(int[] args,int num, int left, int right) {
        if (left >= right) {
            return false;
        }
        int binary = (left + right) / 2;
        if (args[binary] == num) {
            return true;
        }

        return binarySearchTemp(args, num, left, binary - 1)
                || binarySearchTemp(args, num, binary + 1, right);

    }
    /**
     * 问题:
     * 数组局部最小
     * 例如 [1, 3, 4, 2, 5, 8, 7];
     * 1 2 7 则是局部最小数, 他自身小于相邻的数则是局部最小
     */
    public static int searchLocalMinimum(int[] args) {
        // 边界条件...
        int length = args.length;
        if (args[0] < args[1]) {
            return args[0];
        }
        if (args[length - 1] < args[length - 2]) {
            return args[length - 1];
        }

        int left = 0;
        int right = args.length - 1;
        int binary = (left + right) / 2;
        if (args[binary] < args[binary - 1] && args[binary] < args[binary + 1]) {
            return args[binary];
        }
        // 边界条件, 需要注意. 因为 下标0 - 1会导致越界, 并且下标0已在前置条件判断
        while (binary > 1) {
            binary = (binary - 1) / 2;
            if (args[binary] < args[binary - 1] && args[binary] < args[binary + 1]) {
                return args[binary];
            }
        }
        binary = (left + right) / 2;
        while (binary < right) {
            binary = (right + binary + 1) / 2;
            if (args[binary] < args[binary - 1] && args[binary] < args[binary + 1]) {
                return args[binary];
            }
        }

        return -1;
    }
    public static int searchLocalMinimumTemp(int[] args, int left, int right) {
        int length = args.length;
        if (right == 0 || left == length - 1) {
            return -1;
        }

        int binary = (left + right) / 2;
        if (args[binary] < args[binary - 1] && args[binary] < args[binary + 1]) {
            return args[binary];
        }

        return Math.max(searchLocalMinimumTemp(args, left, binary - 1),
                searchLocalMinimumTemp(args, binary + 1, right));
    }

    /**
     * 二分搜索 有序的前提
     */
    public static boolean sortedBinarySearch(int[] args, int num) {
        if (args == null || args.length <= 0) {
            return false;
        }
        int left = 0;
        int right = args.length - 1;
        int index;
        while (left <= right) {
            int binary = (left + right) / 2;
            if (args[binary] == num) {
                index = binary;
                return true;
            }
            // 因为其有序的前提可以明确知道向左向右继续二分
            if (args[binary] < num) {
                left = binary + 1;
            } else {
                right = binary - 1;
            }
        }
        return false;
    }

    /**
     * 二分搜索找到 大于等于 num 的最左位置
     */
    public static int binarySearchLeft(int[] args, int num) {
        if (args == null || args.length <= 0) {
            return -1;
        }

        int left = 0;
        int right = args.length - 1;
        int index = -1;
        while (left <= right) {
            int binary = (left + right) / 2;
            if (args[binary] >= num) {
                index = binary;
                right = index - 1;
            }
            if (args[binary] < num) {
                left = binary + 1;
            } else {
                right = binary - 1;
            }
        }
        return index;
    }

    /**
     * 选择排序
     */
    public static int[] selectSort(int a[]) {
        for (int i = 0; i < a.length; i++) {
            int index = i;
            for (int j = i + 1; j < a.length; j++) {
                index = a[i] > a[j] ? j : i;
            }
            if (index != i) {
                int temp = a[i];
                a[i] = a[index];
                a[index] = temp;
            }
        }
        return a;
    }

    /**
     * 冒泡排序
     */
    public static int[] bobbleSort(int a[]) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                }
            }
        }
        return a;
    }

    /**
     * 插入排序
     */
    public static int[] insertSort(int a[]) {
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                if (a[j] > a[i]) {
                    swap(a, j, i);
                }
            }
        }
        return a;
    }

    public static void swap(int a[], int source, int target) {
        int temp = a[source];
        a[source] = a[target];
        a[target] = temp;
    }
}
