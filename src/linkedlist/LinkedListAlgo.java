package linkedlist;

/**
 * 极客时间 07_linkedlist
 * 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个结点
 * 5) 求链表的中间结点
 */
public class LinkedListAlgo {

    public static void main(String[] args) {

        int[] data = {1,2,3,4,5};
        Node list = createNode(data);

        int[] data1 = {4};
        Node list1 = createNode(data1);

//        printAll(mergeSortedLists(list, list1));

        //删除
//        deleteLast(list, 4);
//        printAll(list);

        printAll(getMiddleNode(list));
    }

    // 单链表反转
    public static Node reserve(Node list) {
        Node pre = null; //前面节点
        Node cur = list; //当前节点
        while (cur != null) {
            Node next = cur.next;

            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // 链表中环的检测
    public static boolean checkCircle(Node list) {
        if (list == null) {
            return false;
        }
        Node fast = list;
        Node slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }

        return false;
    }

    // 有序链表合并
    public static Node mergeSortedLists(Node p, Node q) {
        if (p == null) {
            return q;
        }
        if (q == null) {
            return p;
        }


        Node head;
        if (p.data < q.data) {
            head = p;
            p = p.next;
        } else {
            head = q;
            q = q.next;
        }

        Node cur = head;
        while (p != null && q != null) {
            if (p.data < q.data) {
                cur.next = p;
                cur = p;
                p = p.next;
            } else {
                cur.next = q;
                cur = q;
                q = q.next;
            }
        }
        if (p != null) {
            cur.next = p;
        }
        if (q != null) {
            cur.next = q;
        }

        return head;
    }

    // 删除链表倒数第n个结点
    public static Node deleteLast(Node node, int n) {
        Node fast = node;
        while (fast != null && n > 1) {
            fast = fast.next;
            n--;
        }
        if (fast == null) {
            return node;
        }

        Node slow = node;
        Node pre = null;
        while (fast.next != null) {
            fast = fast.next;
            pre = slow;
            slow = slow.next;
        }
        if (pre == null) {
            node = node.next;
        } else {
            pre.next = pre.next.next;
        }
        return node;
    }

    public static Node getMiddleNode(Node node) {
        if (node == null) {
            return null;
        }
        Node fast = node; //快节点（尾节点），一次走两步
        Node low = node; //中间节点，一次走一步

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            low = low.next;
        }
        return low;
    }



    // 打印
    public static void printAll(Node list) {
        Node p = list;
        while (p != null) {
            System.out.print(p.data);
            p = p.next;
        }
        System.out.println();
    }

    // 构建数组创建Node
    public static Node createNode(int[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        Node list = new Node(data[0]);
        Node cur = list;
        for (int i = 1; i < data.length; i++) {
            Node p = new Node(data[i]);
            cur.next = p;
            cur = p;
        }
        return list;
    }


    public static class Node {
        private int data;
        private Node next;

        public Node() {

        }

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
}
