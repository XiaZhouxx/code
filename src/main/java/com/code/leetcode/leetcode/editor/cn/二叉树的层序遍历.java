import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        int weight = 0;
        List<Integer> integers = new ArrayList<>();
        integers.add(root.val);
        result.add(integers);
        weight++;
        if (root.left != null) {
            helper(root.left, weight);
        }
        if (root.right != null) {
            helper(root.right, weight);
        }
        return result;
    }
    public void helper(TreeNode root, int weight) {
        List<Integer> integers;
        if (result.size() <= weight) {
            integers = new ArrayList<>();
            result.add(integers);
        } else {
            integers = result.get(weight);
        }
        integers.add(root.val);
        weight++;
        if (root.left != null) {
            helper(root.left, weight);
        }
        if (root.right != null) {
            helper(root.right, weight);
        }
        return ;
    }

    }
//leetcode submit region end(Prohibit modification and deletion)
