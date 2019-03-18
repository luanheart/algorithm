package sorts;

/**
 * 计数排序
 * https://time.geekbang.org/column/article/42038
 * 只适用于非负整数
 *
 */
public class CountingSort {

    public static void countSort(int[] a, int n) {
        if (n <= 1) {
            return;
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }

        // 创建长度为max的桶，c[0,max]
        int[] c = new int[max + 1];
        for (int i = 0; i <= max; i++) {
            c[i] = 0;
        }
        // 计算每个元素的个数，放入桶中
        for (int i = 0; i < n; i++) {
            c[a[i]]++;
        }
        // 依次累计
        for (int i = 1; i <= max; i++) {
            c[i] = c[i] + c[i-1];
        }

        // 创建临时数组r
        int[] r = new int[n];

        // c[a[i]] 是元素 a[i] 第几小的，直接放在-1位置上
        for (int i = n -1; i >= 0; i--) {
            r[c[a[i]] - 1] = a[i];
            c[a[i]]--;
        }

        // 拷贝回原数组
        for (int i = 0; i < n; i++) {
            a[i] = r[i];
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 1, 3, 7, 2, 6};
        int n = a.length;
        print(a);
        countSort(a, n);
        print(a);
    }

    public static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
