package tree;

import javax.swing.tree.TreeNode;

/**
 * 红黑树
 * 1.每个节点是黑色或者红色
 * 2.根节点是黑色
 * 3.每个叶子节点（NULL）是黑色
 * 4.如果节点是红色，子节点一定是黑色
 * 5.从任意节点到达叶子节点的每条路径，必须包含相同的黑色节点数目
 */
public class RedBlackTree<T extends Comparable<T>> {

    private RBNode<T> root;

    private static final boolean RED = true;

    private static final boolean BLACK = false;

    static class RBNode<T extends Comparable<T>> {
        T key;
        // 红黑树的颜色 true红色 false黑色
        boolean color;
        RBNode<T> left;
        RBNode<T> right;
        RBNode<T> parent;

        RBNode(T key, boolean color) {
            this.key = key;
            this.color = color;
        }

        RBNode(T key) {
            this.key = key;
            this.color = RED;
        }
    }

    // 父节点
    private RBNode<T> parentOf(RBNode<T> node) {
        return node != null ? node.parent : null;
    }

    // 是否为红的
    private boolean isRed(RBNode<T> node) {
        return node != null && node.color == RED;
    }
    private boolean isBlack(RBNode<T> node) {
        return !isRed(node);
    }

    // 设置颜色
    private void setColor(RBNode<T> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }
    private void setRed(RBNode<T> node) {
        setColor(node, RED);
    }
    private void setBlack(RBNode<T> node) {
        setColor(node, BLACK);
    }

    // 前序遍历
    public void preOrder() {
        preOrder(this.root);
    }

    private void preOrder(RBNode<T> node) {
        if (node != null) {
            System.out.println(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // 中序遍历
    public void inOrder() {
        inOrder(this.root);
    }

    private void inOrder(RBNode<T> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.key + " ");
            inOrder(node.right);
        }
    }

    // 后序遍历
    public void postOrder() {
        postOrder(this.root);
    }

    private void postOrder(RBNode<T> node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.key + " ");
        }
    }

    public void print() {
        printNode(this.root, 0);
        System.out.println("-------------------------------------------");
    }

    public void printNode(RBNode<T> node, int level) {
        if (node == null) {
            padding("\t", level);
            System.out.println("NIL");
        } else {
            printNode(node.right, level + 1);
            padding("\t", level);
            if (node.color == BLACK) {
                System.out.printf("%s\n", node.key);
            } else {
                // 打印红色
                System.out.printf("\033[31m%s\033[0m\n", node.key);
            }
            printNode(node.left, level + 1);
        }
    }

    private void padding(String s, int n) {
        for (int i = 0; i < n; i++) {
            System.out.printf(s);
        }
    }



    /** ----内部方法---- **/

    /**
     * 对节点x左旋
     * 左旋示意图(对节点x进行左旋)：
     *      px                             px
     *     /                              /
     *    x                              y
     *   /  \      --(左旋)->           / \
     *  lx   y                         x  ry
     *     /   \                      /  \
     *    ly   ry                    lx  ly
     *
     * @param x
     */
    private void leftRotate(RBNode<T> x) {
        // 设置x的右孩子为y
        RBNode<T> y = x.right;

        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else {
            if (x.parent.left == x) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        }

        y.left = x;
        x.parent = y;
    }

    /**
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                              py
     *           /                               /
     *          y                               x
     *         /  \      --(右旋)->            /  \
     *        x   ry                         lx   y
     *       / \                                 / \
     *      lx  rx                              rx  ry
     *
     * @param y
     */
    private void rightRotate(RBNode<T> y) {
        RBNode<T> x = y.left;

        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }

        x.parent = y.parent;
        if (y.parent == null) {
            this.root = x;
        } else {
            if (y.parent.left == y) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
        }

        x.right = y;
        y.parent = x;
    }

    /** 插入节点 **/
    public void insert(T key) {
        insert(new RBNode<>(key));
    }

    public void insert(RBNode<T> node) {
        // 插入节点默认是红色
        RBNode<T> current = null; //插入节点node的父节点
        RBNode<T> x = this.root; //向下搜索的节点

        while (x != null) {
            current = x;
            if (node.key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = current;
        // 判断node是在左节点还是右节点
        if (current != null) {
            if (node.key.compareTo(current.key) < 0) {
                current.left = node;
            } else {
                current.right = node;
            }
        } else {
            this.root = node;
        }

        insertFixUp(node);
    }

    // 插入重新调整
    private void insertFixUp(RBNode<T> node) {
        // 父节点和祖父节点
        RBNode<T> parent, gparent;

        // 父节点存在且是红色
        while ((parent = parentOf(node)) != null && parent != this.root && isRed(parent)) {
            // 祖父节点
            gparent = parentOf(parent);
            // 父节点是祖父节点的左孩子
            if (parent == gparent.left) {
                RBNode<T> uncle = gparent.right;

                // CASE 1: 叔叔节点是红色
                // 将父节点和叔叔节点设为黑色，祖父节点设为红色，关注节点变成祖父节点
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // CASE 2: 叔叔节点是黑色，当前节点是右子节点
                // 围绕父节点左旋，将关注节点设为父节点，跳到CASE 3
                if (parent.right == node) {
                    leftRotate(parent);
                    RBNode<T> tmp = node;
                    node = parent;
                    parent = tmp;
                }

                // CASE 3: 叔叔节点是黑色，当前节点是左子节点
                // 父节点设为黑色，祖父节点设为红色，围绕祖父节点右旋，
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {
                // 父节点是祖父节点的右孩子
                RBNode<T> uncle = gparent.left;

                // CASE 1: 叔叔节点是红色
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // CASE 2: 叔叔节点是黑色，当前节点是左子节点
                if (parent.left == node) {
                    rightRotate(parent);
                    RBNode<T> tmp = node;
                    node = parent;
                    parent = tmp;
                }

                // CASE 3: 叔叔节点是黑色，当前节点是右子节点
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        // 根节点设为黑色
        setBlack(this.root);
    }




    public static void main(String[] args) {

        RedBlackTree<Integer> tree = new RedBlackTree<>();

        int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        for (int i = 0; i < a.length; i++) {
            System.out.println("----插入" + a[i] + "-----");
            tree.insert(a[i]);
            tree.print();
        }
    }

}
