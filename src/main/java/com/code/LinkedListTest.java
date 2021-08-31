package com.code;

/**
 * @author xz
 * @Description 链表测试
 * @date 2021/8/31 0031 14:51
 **/
public class LinkedListTest {

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node("55");

        // head = reverseLinkedList(head);
        head = reverseLinkedListOne(head);
        while (head != null) {
            System.out.println(head.value);
            head = head.next;
        }
    }

    /**
     * 反转双向链表
     */
    public static Node reverseDoubleLinkedList(Node node) {
        Node next = null;
        Node prev = null;
        // 1 <-> 2 <-> 3 <-> 4
        // 4 <-> 3 <-> 2 <-> 1
        while (node != null) {
            // 获取当前链表的下一个节点 2
            next = node.next;
            // 将当前Node 的下一个节点设置为它的上一个节点, 反转
            node.next = prev;
            node.prev = next;
            // 当前节点反转完毕后, 保存当前节点为上节点, 供后续节点反转
            prev = node;
            node = next;
        }
        return prev;
    }

    /**
     * 反转单链表
     */
    public static Node reverseLinkedListOne(Node node) {
        Node next = null;
        Node prev = null;
        // 1 -> 2 -> 3 -> 4
        while (node != null) {
            // 获取当前链表的下一个节点 2
            next = node.next;
            // 将当前Node 的下一个节点设置为它的上一个节点, 反转
            node.next = prev;
            // 当前节点反转完毕后, 保存当前节点为前置节点变量
            // 供后续节点反转时使用
            prev = node;
            node = next;
        }
        return prev;
    }

    /**
     * 自己写的版本。。。。
     * @param node
     * @return
     */
    public static Node reverseLinkedList(Node node) {
        Node next = null;
        // 1 -> 2 -> 3 -> 4
        while (node != null) {
            // 2
            Node n = node.next;
            if (n != null) {
                Node tempNode = n.next;
                // 2 -> 1
                n.next = node;
                // 1 -> null
                n.next.next = next;
                // currentNode -> 3
                node = tempNode;
                next = n;

            } else {
                node.next = next;
                return node;
            }

        }
        return next;
    }

    static class Node {
        Object value;
        Node next;
        Node prev;

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
