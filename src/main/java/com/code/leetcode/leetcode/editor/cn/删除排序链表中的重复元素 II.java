//存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除链表中所有存在数字重复情况的节点，只保留原始链表中 没有重复出现 的数字。 
//
// 返回同样按升序排列的结果链表。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,3,4,4,5]
//输出：[1,2,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [1,1,1,2,3]
//输出：[2,3]
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
// Related Topics 链表 双指针 👍 743 👎 0


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
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.val = head.val - 1;
        dummy.next = head;
        ListNode last = dummy;
        // 找到最后一个有效的节点，用假节点占位, 因为可能出现 [1,1,1,1,1,2] 形式的链表
        ListNode lastValid = dummy;
        ListNode temp = head;
        while (temp != null) {
            ListNode next = temp.next;
            // 1 2 3, 如果当前节点和前节点不同, 且和后节点不同时, 这个节点才能算有效(因为需要删除重复.)
            if (temp.val != last.val) {
                if (next != null && temp.val != next.val) {
                    lastValid = temp;
                }
            }
            if (temp.val == last.val) {
                while (next != null && next.val == last.val) {
                    ListNode tNext = next.next;
                    next.next = null;
                    next = tNext;
                }
                lastValid.next = next;
            }
            last = temp;
            temp = next;
        }
        return dummy.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
