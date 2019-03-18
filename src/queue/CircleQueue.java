package queue;

/**
 * 循环队列
 */
public class CircleQueue {
    // 数组：items，数组大小：n
    private String[] items;
    private int n = 0;
    // head 表示队头下标，tail 表示队尾下标
    private int head = 0;
    private int tail = 0;

    public CircleQueue(int n) {
        this.items = new String[n];
        this.n = n;
    }

    // 入队
    public boolean enqueue(String item) {
        // tail 在 head 的前一个表示满了
        if ((tail + 1) % n == head) {
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    // 出队
    public String dequeue() {
        if (head == tail) {
            return null;
        }
        String item = items[head];
        head = (head + 1) % n;
        return item;
    }

    public void printAll() {
        if (n == 0) {
            return;
        }
        for (int i = head; i != tail; i = (i + 1) % n) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        CircleQueue queue = new CircleQueue(4);
        String[] data = {"1", "2", "3", "4"};
        for (int i = 0; i < data.length; i++) {
            queue.enqueue(data[i]);
        }
        queue.printAll();
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println("dequeue: " + queue.dequeue());
        queue.enqueue("a");
        queue.enqueue("b");
        queue.printAll();
    }

}
