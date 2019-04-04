package string;

/**
 * 字符串匹配 BM 算法
 * https://time.geekbang.org/column/article/71525
 */
public class BM {

    // 怀字符
    private static final int SIZE = 256;

    /**
     * 在长度为 n 的主串 a 匹配长度为 b 的模式串
     * @param a 主串
     * @param n
     * @param b 模式串
     * @param m
     * @return
     */
    public int bm(char[] a, int n, char[] b, int m) {
        int[] bc = new int[SIZE]; // 记录模式串中每个字符最后出现的位置
        generateBC(b, m, bc); // 构建坏字符哈希表
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        generateGS(b, m, suffix, prefix);
        int i = 0; // i 表示主串与模式串对齐的第一个字符
        while (i <= n - m) {
            int j;
            for (j = m - 1; j >= 0; j--) {
                if (a[i+j] != b[j]) {
                    // 坏字符对应模式串中的下标是 j
                    break;
                }
            }
            if (j < 0) {
                // 匹配成功，返回主串与模式串第一个匹配的字符的位置
                return i;
            }
            // 这里等同于将模式串往后滑动 j-bc[(int)a[i+j]] 位
            int x = j - bc[(int)a[i+j]]; // 坏字符滑动
            int y = 0;
            if (j < m - 1) { // 如果有好后缀的话
                y = moveByGS(j, m, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    // j 表示坏字符对应的模式串中的字符下标 ; m 表示模式串长度
    // 好字符移动位数
    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j; // 好后缀长度
        if (suffix[k] != -1) return j - suffix[k] +1;
        for (int r = j+2; r <= m-1; ++r) {
            if (prefix[m-r]) {
                return r;
            }
        }
        return m;
    }

    // 将模式串转成一个散列表
    private void generateBC(char[] b, int m, int[] bc) {
        // 初始成-1
        for (int i = 0; i < SIZE; i++) {
            bc[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            int ascii = (int)b[i];
            bc[ascii] = i;
        }
    }

    private void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < m; ++i) { // 初始化
            suffix[i] = -1;
            prefix[i] = false;
        }
        for (int i = 0; i < m - 1; i++) {
            int j = i;
            int k = 0; // 公共后缀子串长度
            while (j >= 0 && b[j] == b[m-1-k]) { // 与 b[0, m-1] 求公共后缀子串
                --j;
                ++k;
                suffix[k] = j + 1; // j+1 表示公共后缀子串在 b[0, i] 中的起始下标
            }
            if (j == -1) {
                // 如果公共后缀子串也是模式串的前缀子串
                prefix[k] = true;
            }
        }

    }

    public static void main(String[] args) {
        String a = "abcdeafa234gsgq36353fg";
        String b = "baaa";
        System.out.println(new BM().bm(a.toCharArray(), a.length(), b.toCharArray(), b.length()));
    }
}
