package sorts;

/**
 * 找出数组中第K小的元素，时间复杂度 O(n)
 * 利用分区的思想
 * 把数组 A[0..n-1] 的最后一个元素 A[n-1] 当做 pivot，对数组进行原地分区，得到三个部分 A[0..p-1] A[p] A[p+1..n-1]
 * 如果 p+1=k 那 A[p] 就是求解元素，如果 p+1 < k 说明在 A[p+1..n-1] 中，如果 p+1 > k 在 A[0..p-1]中
 * 递归查找
 */
public class KthSmallest {
    public static int kthSmallest(int[] a, int k) {
        if (a == null || a.length < k) {
            return -1;
        }
        int p = partition(a, 0, a.length - 1);
        while (p + 1 != k) {
            if (p + 1 > k) {
                p = partition(a, 0, p -1);
            } else {
                p = partition(a, p + 1, a.length -1);
            }
        }
        return p;
    }

    private static int partition(int[] a, int p, int r) {
        int pivot = a[r];

        int i = p;
        for (int j = p; j < r; j++) {
            // 注意这里要写 <= 否则出现死循环
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, r);
        System.out.println("分区点" + i);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 2};
        System.out.println(kthSmallest(a, 2));
    }
}
