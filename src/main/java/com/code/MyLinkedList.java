package com.code;

/**
 * @author xz
 * @Description 自定义实现链表
 * @date 2021/9/11 0011 23:54
 **/
public class MyLinkedList {
    ListNode head;
    ListNode tail;
    int size;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        this.head = new ListNode();
        this.tail = new ListNode();
        head.next = tail;
        tail.prev = head;
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        ListNode result = head.next;
        while (index > 0) {
            result = result.next;
            index --;
        }
        return result.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        ListNode node = new ListNode(val);
        head.next.prev = node;
        node.next = head.next;
        node.prev = head;
        head.next = node;
        size ++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        ListNode node = new ListNode(val);
        tail.prev.next = node;
        node.prev = tail.prev;
        tail.prev = node;
        node.next = tail;
        size ++;
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) {
            return ;
        }
        ListNode node = new ListNode(val);
        ListNode temp = head.next;
        while (index > 0) {
            temp = temp.next;
            index --;
        }
        node.next = temp;
        node.prev = temp.prev;
        temp.prev.next = node;
        temp.prev = node;
        size ++;
    }
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return ;
        }
        ListNode temp = head.next;
        while (index > 0) {
            temp = temp.next;
            index --;
        }
        temp.next.prev = temp.prev;
        temp.prev.next = temp.next;
        size --;
    }
    static class ListNode {
        int val;
        ListNode next;
        ListNode prev;
        public ListNode() {}

        public ListNode(int val) {
            this.val = val;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
