package com.code;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xz
 * @ClassName TreeTest
 * @Description
 * @date 2021/9/3 0003 14:20
 **/
public class TreeTest {
    /**
     * 二叉树的三种遍历方式：
     * 例如        1
     *         2      3
     *       4  5   6  7
     * 先序 根节点 [左子节点] [右子节点]  1 2 4 5 3 6 7
     * 中序 [左子节点] 根节点 [右子节点]  4 2 5 1 6 3 7
     * 后序 [左子节点] [右子节点] 根节点  4 5 2 6 7 3 1

     */

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(0);
        TreeNode head1 = new TreeNode(0);
        head.left = new TreeNode(1);
        head.right = new TreeNode(2);
        head.left.left = new TreeNode(3);
        head.left.left.left = new TreeNode(4);

        head1.left = new TreeNode(1);
        head1.right = new TreeNode(2);
        head1.left.left = new TreeNode(3);

        System.out.println(        checkTreeEquals(head, head1));
        System.out.println(treeHeight(head));
    }

    /**
     * 判断两棵树是否相等
     */
    public static boolean checkTreeEquals(TreeNode a, TreeNode b) {
        // 异或符号, 相等为 false, 不等为true, (两个同时为空 or 两个同时不为空)
        if (a == null ^ b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }

        return a.val == b.val && checkTreeEquals(a.left, b.left) && checkTreeEquals(a.right, b.right);
    }

    /**
     * 判断二叉树是否为镜面数
     *    1
     *  2     2
     *3  2   2  3
     * @param a
     * @param b
     * @return
     */
    public static boolean checkTreeEquals1(TreeNode a, TreeNode b) {
        // 异或符号, 相等为 false, 不等为true, (两个同时为空 or 两个同时不为空)
        if (a == null ^ b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }

        return a.val == b.val && checkTreeEquals(a.left, b.right) && checkTreeEquals(a.right, b.left);
    }

    public static int treeHeight(TreeNode a) {
        if (a == null) {
            return 0;
        }
        int [] pre = new int[1];
        if (pre.length == 1) {


        }
        return treeHeight(a.left) + 1;
    }

    /**
     * 当前存在 一个先序数组 和一个 中序数组, 重建这个二叉树
     * 例如 ： 3 9 20 15 7
     *         9 3 15 20 7
     * 根据 两则的顺序
     * 1. 先序可以明确的得知其组成 根节点 [左子节点] [右子节点] (当然注意普通二叉树倾斜问题)
     * 2. 而中序遍历的组成则为 [左子节点] 根节点 [右子节点]
     * 3. 因此需要从先序中拿到根节点, 再从中序中 划分 左右边界进行递归
     *
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 1) {
            return new TreeNode(preorder[0]);
        }

        return buildTree1(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    /**
     * 用于递归的方法
     */
    public TreeNode buildTree1(int[] pre, int[] in,int preL, int preR, int inL, int inR) {
        if (preL > preR) {
            return null;
        }
        // 根节点则是就是 pre 的当前起始节点(递归下还有子根节点)
        TreeNode head = new TreeNode(pre[preL]);
        if (preL == preR) {
            return head;
        }

        // 定位根节点索引, 这里图方便, 最好使用hash表在前置保存 这里只用 o(1)级别的 get即可
        int rootIndex = 0;
        while (in[rootIndex] != pre[preL]) {
            rootIndex ++;
        }
        /*
         * 根节点都是从前序遍历的数组中获取, 深度遍历一直到叶子节点
         * 前序 [根] [左] [右]
         * 中序 [左] [根] [右]
         * preEnd = 当前起始 + (中序中找到的根位置 - 中序当前起始位置 = 左子树有多少个节点)
         * 对于 left 节点
         *  1. preEnd = 左子树的边界
         *  2. 前序遍历 start + 1 则是左子树的起始节点.
         *  3. left 本身在中序遍历起始位置 inStart 则无需变动 [左] [根] [右]
         * 对于 right 节点
         *  1. preEnd + 1 则就是右子树的起始下标
         *  2. 前序的 end 则一定是右子树的边界下标
         *  3. right 则在中序遍历的最右段，根 + 1则是 inStart
         */
        int left = preL + rootIndex - inL;
        head.left = buildTree1(pre, in, preL + 1, left, inL, rootIndex - 1 );
        int right = left + 1;
        head.right = buildTree1(pre, in, right, preR, rootIndex + 1, inR);
        return head;
    }
    private static Map<Integer, Integer> hashMap = new HashMap<>();

    public void buildHashTable(int[] in) {
        for (int i = 0; i < in.length; i ++) {
            hashMap.put(in[i], i);
        }
    }
}
