package sorts;

/**
 * 快排
 * 选取一个元素q（最后一个）作为分区点，把比q小的放在左边，比q大的放在右边，然后把左边和右边递归操作
 */
public class QuickSort {

    public static void quickSort(int[] a, int n) {
        quickSortInternally(a, 0, n - 1);
    }

    private static void quickSortInternally(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        // 分区
        int q = partition(a, p, r);
        quickSortInternally(a, p, q - 1);
        quickSortInternally(a, q + 1, r);
    }

    /**
     * 将选取的元素 pivot 放在合适的地方，左边都比它小，右边的都比它大
     * 通过游标 i，将 A[p..r] 分成两部分，A[p..i-1] 的元素都比 pivot 小，叫做已处理区间，A[i..r-1]是未处理区间
     * 每次从未处理区间中取一个元素 A[j]，如果小于 pivot 将其放入 a[i]
     */
    private static int partition(int a[], int p, int r) {
        int i = p;
        int pivot = a[r];

        for (int j = p; j < r; j++) {
            if (a[j] < pivot) {
                if (i == j) {
                    i++;
                } else {
                    int tmp = a[i];
                    a[i++] = a[j];
                    a[j] = tmp;
                }
            }
        }

        int tmp = a[i];
        a[i] = a[r];
        a[r] = tmp;

        return i;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 1, 3, 7, 2, 6};
        int n = a.length;
        print(a);
        quickSort(a, n);
        print(a);
    }

    public static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}