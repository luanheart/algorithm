package binarysearch;

/**
 * 二分查找法
 */
public class BinarySearch {
    // 二分查找
    public static int binarySearch(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            // 如果low和high非常大的时候，可能会溢出
            int mid = low + (low - high) >> 2;
            if (a[mid] == value) {
                return a[mid];
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找的变体
     **/

    // 查找第一个值等于给定值的元素
    public static int binarySearchFirst(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + (low - high) >> 2;
            if (a[mid] == value) {
                if (mid == 0 || a[mid - 1] != value) {
                    return mid;
                }
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // 查找最后一个值等于给定值的元素
    public static int binarySearchLast(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + (low - high) >> 2;
            if (a[mid] == value) {
                if (mid == n - 1 || a[mid + 1] != value) {
                    return mid;
                }
                low = mid + 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // 查找第一个大于等于给定值的元素
    public static int binarySearchFirstGreater(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + (low - high) >> 2;
            if (a[mid] >= value) {
                if (mid == 0 || a[mid - 1] < value) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    // 查找最后一个小于等于给定值的元素
    public static int binarySearchLastLess(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + (low - high) >> 2;
            if (a[mid] <= value) {
                if (mid == n - 1 || a[mid - 1] > value) {
                    return mid;
                }
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7, 9, 17, 22, 36};
        int n = a.length;
        System.out.println(binarySearch(a, 5, n));

    }

    public static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
