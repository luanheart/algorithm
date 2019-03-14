package linkedlist;

/**
 * 单链表
 */
public class SinglyLinkedList {

    private Node head = null;

    public static void main(String[] args) {
        SinglyLinkedList link = new SinglyLinkedList();
        SinglyLinkedList link1 = new SinglyLinkedList();


        int data[] = {1, 2,3,2,1};

        for (int i = 0; i < data.length; i++) {
            link.insertTail(data[i]);
            link1.insertTail(data[i]);
        }
        link.printAll();
        System.out.println("是否为回文：" + link.palindrome());
        link.printAll();


        // 删除
//        link.deleteByValue(1);
//        link.deleteByValueAll(2);

        // 反转
//        link.inverseLinkList();
//        link.printAll();

    }

    public void insertHead(int value) {
        Node node = new Node(value);
        insertHead(node);
    }

    // 链表头部插入
    public void insertHead(Node node) {
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }

    // 链表尾部插入
    public void insertTail(int value) {
        insertTail(new Node(value));
    }

    public void insertTail(Node node) {
        if (head == null) {
            head = node;
        } else {
            Node p = head;
            while (p.next != null) {
                p = p.next;
            }
            node.next = p.next;
            p.next = node;
        }
    }

    // 删除节点
    public void deleteByNode(Node node) {
        if (head == null || node == null) {
            return;
        }

        Node p = head;
        while (p != null && p.next != node) {
            p = p.next;
        }
        if (p == null) {
            return;
        }
        p.next = p.next.next;
    }

    // 删除第一个值
    public void deleteByValue(int value) {
        if (head == null) {
            return;
        }

        Node p = head;
        Node q = null;
        while (p != null && p.data != value) {
            q = p;
            p = p.next;
        }
        if (p == null) {
            return;
        }
        if (q == null) {
            head = head.next;
        } else {
            q.next = q.next.next;
        }
    }

    // 删除所有的值
    public void deleteByValueAll(int value) {
        Node p = head; //要删除的节点
        Node q = null; //前一节点
        while (p != null) {
            if (p.data == value) {
                if (q == null) {
                    head = head.next;
                } else {
                    q.next = q.next.next;
                }
                p = p.next;
                continue;
            }
            q = p;
            p = p.next;
        }
    }

    // 打印
    public void printAll() {
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    // 是否是回文
    public boolean palindrome() {
        if (head == null) {
            return false;
        }

        Node p = head; //中间节点
        Node q = head; //尾节点
        if (p.next == null) {
            return true;
        }

        while (q.next != null && q.next.next != null) {
            p = p.next;
            q = q.next.next;
        }

        Node leftLink = null;
        Node rightLink = null;
        if (q.next == null) {
            // p为真正的中点
            rightLink = p.next;
            leftLink = inverseLinkList(p).next;
        } else {
            // p q都为中点
            leftLink = inverseLinkList(p);
            rightLink = q;
        }

        return equalNode(leftLink, rightLink);
    }

    // 判断两个节点是否相同
    public boolean equalNode(Node l, Node r) {
        while (l != null && r != null) {
            if (l.data == r.data) {
                l = l.next;
                r = r.next;
            } else {
                break;
            }
        }
        return l == null && r == null;
    }

    // 链表翻转
    public void inverseLinkList(){
        if (head == null) {
            return;
        }
        Node pre = null;
        Node r = head;

        while (r != null) {
            Node next = r.next;

            r.next = pre;
            pre = r;
            r = next;
        }
        head = pre;
    }

    // 从head到指定节点p反转
    public Node inverseLinkList(Node p){
        Node pre = null;
        Node r = head;

        while (r != p) {
            Node next = r.next;

            r.next = pre;
            pre = r;
            r = next;
        }
        r.next = pre;
        return r;
    }

    public SinglyLinkedList() {
    }

    public SinglyLinkedList(Node head) {
        this.head = head;
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
}
