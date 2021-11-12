//存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。 
//
// 返回同样按升序排列的结果链表。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,1,2]
//输出：[1,2]
// 
//
// 示例 2： 
//
// 
//输入：head = [1,1,2,3,3]
//输出：[1,2,3]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点数目在范围 [0, 300] 内 
// -100 <= Node.val <= 100 
// 题目数据保证链表已经按升序排列 
// 
// Related Topics 链表 👍 676 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


import com.code.entity.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode last = null;
        ListNode temp = head;
        while (temp != null) {
            ListNode next = temp.next;
            if (last != null && temp.val == last.val) {
                while (next != null && next.val == last.val) {
                    ListNode tNext = next.next;
                    next.next = null;
                    next = tNext;
                }
                last.next = next;
            }
            last = temp;
            temp = next;
        }
        return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
