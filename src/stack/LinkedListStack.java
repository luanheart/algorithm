package stack;

import linkedlist.LinkedListAlgo;

/**
 * 基于链表的栈实现
 */
public class LinkedListStack {

    // 栈顶节点
    public Node top;

    public void push(int data) {
        Node newNode = new Node(data);
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    public int pop() {
        // 栈空
        if (top == null) {
            return -1;
        }
        int data = top.data;
        top = top.next;
        return data;
    }

    public void printAll() {
        Node p = top;
        while (p != null) {
            System.out.print(p.data+" ");
            p = p.next;
        }
        System.out.println();
    }

    public static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }


    public static void main(String[] args) {
        LinkedListStack stack = new LinkedListStack();
        int[] data = {1, 2, 3, 5};
        for (int i = 0; i < data.length; i++) {
            stack.push(data[i]);
        }
        stack.printAll();
        System.out.println("pop: " + stack.pop());
        System.out.println("pop: " + stack.pop());
        stack.printAll();
    }
}
