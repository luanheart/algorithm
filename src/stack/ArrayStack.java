package stack;

/**
 * 使用数组实现栈
 */
public class ArrayStack {

    private String[] items;
    private int count; //栈的元素个数
    private int n; //栈大小

    public ArrayStack(int n) {
        this.items = new String[n];
        this.n = n;
        this.count = 0;
    }

    public boolean push(String item) {
        // 栈满
        if (count == n) {
//            return false;
            // 动态扩容
            String[] newItems = new String[n * 2];
            for (int i = 0; i < n; i++) {
                newItems[i] = items[i];
            }
            items = newItems;
            n = n*2;
        }
        items[count++] = item;
        return true;
    }

    public String pop() {
        //栈空
        if (count == 0) {
            return null;
        }
        return items[--count];
    }

    public void printAll() {
        for (int i = 0; i < count; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(1);
        String[] data = {"1", "2", "3", "4"};
        for (int i = 0; i < data.length; i++) {
            stack.push(data[i]);
        }
        stack.printAll();
        System.out.println("pop: " + stack.pop());
        System.out.println("pop: " + stack.pop());
        stack.printAll();
    }
}
