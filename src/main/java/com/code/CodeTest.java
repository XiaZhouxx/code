package com.code;

/**
 * 1 0 1
 * 0 1 0
 * 1 0 1
 */

public class CodeTest {
    public static void main(String[] args) {
        String val = "1,0,1,0,0,0,1,0,1";

        String[] arr = val.split(",");
        int length = 1;
        boolean all = false, all1 = false;
        for (int i = 0; i < arr.length / 2;i++) {
            if (arr[i].equals("0")) all = true;
            else all1 = true;
            if (i * i == arr.length) {
                length = i;
            }
        }
        if (all ^ all1) {
            System.out.println("-1");
            return ;
        }
        int[][] iArr = new int[length][length];
        int idx = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                iArr[i][j] = Integer.parseInt(arr[idx++]);
            }
        }
        int res = 0;
        while(true) {
            boolean has = false;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length ; j++) {
                    if (iArr[i][j] == 0) {
                        int top;
                        if (i > 0 && i < length - 1) {
                            top = Math.max(iArr[i - 1][j], iArr[i + 1][j]);
                        } else {
                            top = iArr[i == 0 ? i + 1 : i - 1][j];
                        }
                        if(j > 0 && j < length - 1) {
                            top = Math.max(iArr[i][j - 1], iArr[i][j + 1]);
                        } else {
                            top = Math.max(top, iArr[i][j == 0 ? j + 1 : j - 1]);
                        }
                        if (top == 1) {
                            iArr[i][j] = -1;
                            has = true;
                        }
                    }

                }
            }
            if (has) {
                res ++;
            } else {
                break;
            }
        }

        System.out.println(res);
    }
}
