package sorts;

/**
 * 归并排序
 * 将数组分成两个数组分别排序，最后再合并这两个数组，分治
 */
public class MergeSort {

    public static void mergeSort(int[] a, int n) {
        mergeSortInternally(a, 0, n-1);
    }

    private static void mergeSortInternally(int[] a, int p, int q) {
        if (p >= q) {
            return;
        }
        int mid = (p+q)/2;
        // 分成左右两个数组
        mergeSortInternally(a, p, mid);
        mergeSortInternally(a, mid + 1, q);
        // 合并数组
        merge(a, p, mid, q);
    }

    private static void merge(int[] a, int p, int mid, int q) {
        int res[] = new int[q-p+1];
        int i = p;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= q) {
            if (a[i] < a[j]) {
                res[k++] = a[i++];
            } else {
                res[k++] = a[j++];
            }
        }
        // 有剩余数组未搬运完
        int start = i;
        int end = mid;
        if (j <= q) {
            start = j;
            end = q;
        }
        while (start <= end) {
            res[k++] = a[start++];
        }
        // 再将res拷贝回原数组
        k = 0;
        while (p <= q) {
            a[p++] = res[k++];
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 1, 3, 7, 2, 6};
        int n = a.length;
        print(a);
        mergeSort(a, n);
        print(a);
    }

    public static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
