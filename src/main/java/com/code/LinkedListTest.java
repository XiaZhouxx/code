package com.code;

import com.code.entity.ListNode;

/**
 * @author xz
 * @Description 链表测试
 * @date 2021/8/31 0031 14:51
 **/
public class LinkedListTest {

    // [1, 2, 3, 4]
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode result = head.next;
        if (result == null) {
            return head;
        }
        int i = 0;
        // 记录两个节点转换后的最后一个节点
        ListNode last = null;
        // 记录两个节点转换时的第一个节点
        ListNode pre = null;
        while (head != null) {
            i++;
            ListNode next = head.next;
            // 两两替换
            if (i == 2) {
                i = 0;
                // 如果前面存在两两转换后的, 将他的next 指向当前转换后的头节点 [1,2,3,4], last = [2,1] -> currentHead = [4,3]
                if (last != null) {
                    last.next = head;
                }
                last = pre;
                head.next = pre;
            } else {
                // 记录两两分组中的前置节点
                pre = head;
            }
            head = next;
        }
        // 当存在 [1,2,3,4,5] 时, 无法分配(2)一组, 所以需要处理最后一个节点.
        last.next = last == pre ? null : pre;
        return result;
    }

    /**
     * 链表的技巧
     * 在双向链表的实现中，
     * 使用一个伪头部（dummy head）和伪尾部（dummy tail）标记界限，
     * 这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在。
     * 例如 判断 当前节点是head 判断 prev，是tail 判断 next等。
     * @param args
     */
    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        // head.next.next = new Node(3);
        // head.next.next.next = new Node(9);

        Node head1 = new Node(9);
        head1.next = new Node(9);
        head1.next.next = new Node(9);
        head1.next.next.next = new Node(9);
        // head = reverseLinkedList(head);
        queueTest();
        stackTest();

        // head = reverseLinkedListOne(head);
        // head = reverseList(head, 2);

        //head = mergeLinkedList(head, head1);
        // head = mergeLinkedListTwo(head, head1);
        head = twoLinkedListSum(head, head1);
        while (head != null) {
            System.out.println(head.value);
            head = head.next;
        }
    }


    /**
     * 使用单链表构建 队列 、 栈
     *
     * 思路：  根据其数据结构特性
     * 队列 FIFO 栈 FILO
     *
     * 队列需要保留两个链表指针, 一个指向Head 一个指向Tail
     * 队列则采用 尾插, 从Tail指针进行插入
     * 栈则采用 头插 只需要Head指针 插入到Head之前
     * 最终 从Head进行遍历 队列得到的就是 FIFO 栈则就是FILO
     */
    static class Queue<T> {
        private Node head;
        private Node tail;
        private int size;

        /**
         * 暂且用个Integer
         */
        public void offer(Integer t) {
            if (t == null) {
                return ;
            }
            Node node = new Node(t);
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
            size ++;
        }

        public Integer pop() {
            if (size <= 0) {
                return null;
            }
            Integer value = head.value;
            head = head.next;
            size --;
            if (size == 0) {
                tail = null;
            }
            return value;
        }

    }
    public static void queueTest() {
        Queue q = new Queue();
        q.offer(0);
        q.offer(1);
        q.offer(2);
        q.offer(3);
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println("----------------");
    }

    static class Stack {
        private Node head;
        private int size;

        public void offer(Integer val) {
            // 边界判断省略
            Node node = new Node(val);
            if (head != null) {
                node.next = head;
            }
            head = node;
            size ++;
        }

        public Integer pop() {
            if (head == null) {
                return null;
            }
            Integer val = head.value;
            head = head.next;
            return val;
        }
    }

    public static void stackTest() {
        Stack stack = new Stack();
        stack.offer(0);
        stack.offer(1);
        stack.offer(2);
        stack.offer(3);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        System.out.println("----------------");
    }


    /**
     * 使用双向链表构建 一个双端队列
     */

    /**
     * 当前存在一个单链表, 和一个正数 K,
     * 实现 K 个节点内的逆序 操作, 不满足K个节点不作操作
     *
     * 思路：
     */
    public static Node reverseList(Node node, int k) {

        return null;
    }

    /**
     * 求两个链表之和
     * 例如  1 2 3
     *       3 2 1 1
     * 则输出需要为 1121 + 321 = 1442
     *
     * 思路 ： 拆分长短链表 , 注意其边界 和 进位
     *
     */
    public static Node twoLinkedListSum(Node l1, Node l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        // 表示是否进位
        int carry = 0;
        // 这里直接选择一个节点作为返回的头部, 计算的值直接放在l1节点中
        Node head = l1;
        // 追踪最后的l1链表节点，因为可能出现 l1 比 l2 短, 需要跟踪 便于新增节点在之后
        Node lastNode = null;
        /**
         * 之前一直误解 这里的链表指针指向... 忘了Java都是值传递,
         * 变量只是保存的一个指针指向具体的数据。
         */

        // 遍历两个链表 直到某个链表遍历完毕
        while (l1 != null && l2 != null) {
            int sum = l1.value + l2.value + carry;
            l1.value = sum % 10;
            carry = sum / 10;
            lastNode = l1;
            l1 = l1.next;
            l2 = l2.next;
        }

        // 在 l2 遍历完 l1 还没遍历完的处理
        while (l1 != null) {
            int sum = l1.value + carry;
            l1.value = sum % 10;
            carry = sum / 10;
            lastNode = l1;
            l1 = l1.next;
        }
        // 在 l1 遍历完 l2 还没遍历完的处理
        while (l2 != null) {
            int sum = l2.value + carry;
            Node node = new Node(sum % 10);
            lastNode.next = node;
            lastNode = node;
            carry = sum / 10;
            l2 = l2.next;
        }
        // 最后还存在进位 则直接构建一个新节点
        if (carry != 0) {
            lastNode.next = new Node(carry);
        }

        return head;
    }

    /**
     * 合并两个有序链表
     */
    public static Node mergeLinkedList(Node nodeA, Node nodeB) {
        // 边界条件
        if (nodeA == null || nodeB == null) {
            return nodeA == null ? nodeB : nodeA;
        }
        // 获取主链表， 得到起始位置指针,后续无论怎么变化都不会影响起始位置
        // 还有个方法 可以构建一个dummy Node来记录指向
        Node head = nodeA.value > nodeB.value ? nodeB : nodeA;
        Node head1 = head == nodeA ? nodeB : nodeA;
        // 因为主链表已经确认， 所以明确知道从head.next开始
        Node head2 = head.next;
        // 上一次的最后头部节点
        Node prev = head;
        while (head1 != null && head2 != null) {
            if (head1.value >= head2.value) {
                prev.next = head2;
                head2 = head2.next;
            } else {
                prev.next = head1;
                head1 = head1.next;
            }
            prev = prev.next;
        }
        prev.next = head1 == null ? head2 : head1;
        return head;
    }

    public static Node mergeLinkedListTwo(Node l1, Node l2) {
        // 思路则是直接遍历两个链表, 构建一个伪节点来装载当前指针
        Node n = new Node(0);
        Node cur = n;
        while (l1 != null && l2 != null) {
            // 判断顺序
            if (l1.value < l2.value) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            // 移动当前指针
            cur = cur.next;
        }
        // 边界情况, 可能某个链表是长链表情况下, 直接追加在最后
        cur.next = l1 == null ? l2 : l1;
        return n.next;
    }


    private static void reverseTwoLinkedList(Node head, Node tail) {
        Node prev = null;
        Node next;
        while (head != tail) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        head.next = prev;
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
            // 记录当前链表的下一个节点, 避免后续需要操作时短节 2
            next = node.next;
            // 将当前Node 的下一个节点设置为它的上一个节点, 反转
            node.next = prev;
            // 当前Node 的上一个节点设置为它的下一个节点
            node.prev = next;
            // 当前节点反转完毕后, 保存当前节点为上节点, 供后续节点反转
            prev = node;
            // 设置当前Node 为之前记录的原下一节点, 使其能继续遍历
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
            // 将当前Node 的下一个节点设置为它的上一个节点,
            // 也就是反转
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
     *
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
        int value;
        Node next;
        Node prev;

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node(int value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(int value) {
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
