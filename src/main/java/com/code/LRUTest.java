package com.code;

import java.util.HashMap;

/**
 * @author xz
 * @Description 实现一个k v 结构的 Lru算法实现
 * @date 2021/9/3 0003 0:10
 **/
public class LRUTest {
    public static void lruTest() {
        LRUCache cache = new LRUCache(2);
        cache.put(2, 6);
        System.out.println(        cache.get(1));
        cache.put(1, 5);
        cache.put(1, 2);
        System.out.println(cache.get(2));
        System.out.println("-------------------------");
    }

    /**
     * 改进版本
     */
    static class LRUCache {
        private HashMap<Integer, ListNode> cache;
        private ListNode head;
        private ListNode tail;
        private int capacity;

        public LRUCache(int capacity) {
            cache = new HashMap(16);
            this.capacity = capacity;
            // 伪节点, 避免很多的边界判断。
            head = new ListNode(0, 1);
            tail = new ListNode(0, 1);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            ListNode result = cache.get(key);
            if (result == null) {
                return -1;
            }
            addToHead(result, true);
            return result.val;
        }

        public void put(int key, int value) {
            ListNode node = cache.get(key);
            // 如果当前存在相同的key, 则进行修改 然后加入头部
            if (node != null) {
                node = cache.get(key);
                node.key = key;
                node.val = value;
                addToHead(node, true);
            } else {
                // 新增节点加入到头部
                node = new ListNode(key, value);
                addToHead(node, false);
                // 当前容量已经超过最大容量, 删除尾结点
                if (capacity <= cache.size()) {
                    cache.remove(tail.prev.key);
                    tail.prev.prev.next = tail;
                    tail.prev = tail.prev.prev;
                }
            }
            cache.put(key, node);
        }

        public void addToHead(ListNode node, boolean isMove) {
            if (isMove) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            head.next.prev = node;
            node.next = head.next;
            head.next = node;
            node.prev = head;
        }
        static class ListNode {
            int key;
            int val;
            ListNode next;
            ListNode prev;
            public ListNode(int k,int val) {
                this.key = k;
                this.val = val;
            }
        }
    }

    /**
     * 初版
     */
    static class LRUCache1 {
        private HashMap<Integer, ListNode> cache;
        private ListNode head;
        private ListNode tail;
        private int capacity;

        public LRUCache1(int capacity) {
            cache = new HashMap(16);
            this.capacity = capacity;
        }

        public int get(int key) {
            ListNode result = cache.get(key);
            if (result == null) {
                return -1;
            }
            addToHead(result);

            return result.val;
        }

        public void put(int key, int value) {
            if (capacity <= cache.size() && !cache.containsKey(key)) {
                cache.remove(tail.key);
                if (tail == head) {
                    head = null;
                } else {
                    tail = tail.prev;
                    tail.next = null;
                }
            }
            ListNode node;
            if (cache.containsKey(key)) {
                node = cache.get(key);
                node.key = key;
                node.val = value;
                addToHead(node);
            } else {
                node = new ListNode(key, value);
                if (head == null) {
                    tail = node;
                    head = node;
                } else {
                    addToHead(node);
                }
            }
            cache.put(key, node);
        }

        public void addToHead(ListNode node) {
            if (node == null || head == node) {
                return ;
            }
            if (node == tail) {
                tail = node.prev;
                tail.next = null;
            } else if (node.prev != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.prev = null;
            }
            node.next = head;
            head.prev = node;
            head = node;
        }

        static class ListNode {
            int key;
            int val;
            ListNode next;
            ListNode prev;
            public ListNode(int k,int val) {
                this.key = k;
                this.val = val;
            }
        }
    }
}
