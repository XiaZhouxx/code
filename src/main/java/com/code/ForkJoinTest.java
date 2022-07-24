package com.code;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        Sum sum = new Sum(ints, 0, ints.length - 1);

        System.out.println(sum.invoke());
    }

    static class Sum extends RecursiveTask<Integer> {
        int[] arr;
        int start;
        int end;

        public Sum(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(start >= end) {
                return arr[start];
            }
            int idx = start + (end - start) / 2;
            Sum sum = new Sum(arr, start, idx);
            Sum sum1 = new Sum(arr, idx + 1, end);
            sum.fork();
            sum1.fork();

            return sum.join() + sum1.join();
        }
    }
}
