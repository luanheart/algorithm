package queue;

/**
 * 使用数组实现队列
 */
public class ArrayQueue {
    // 数组：items，数组大小：n
    private String[] items;
    private int n = 0;
    // head 表示队头下标，tail 表示队尾下标
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int n) {
        this.items = new String[n];
        this.n = n;
    }

    // 入队
    public boolean enqueue(String item) {
        if (tail == n) {
            // 队列满了
            if (head == 0) {
                // 整个队列满了
                return false;
            }
            // 搬运数据
            for (int i = 0; i < tail - head; i++) {
                items[i] = items[head + i];
            }
            tail = tail - head;
            head = 0;
        }
        items[tail++] = item;
        return true;
    }

    // 出队
    public String dequeue() {
        if (head == tail) {
            // 队列为空
            return null;
        }
        String item = items[head];
        head++;
        return item;
    }

    public void printAll() {
        for (int i = head; i < tail; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(4);
        String[] data = {"1", "2", "3", "4"};
        for (int i = 0; i < data.length; i++) {
            queue.enqueue(data[i]);
        }
        queue.printAll();
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println("dequeue: " + queue.dequeue());
        queue.enqueue("xxx");
        queue.printAll();
    }
}
