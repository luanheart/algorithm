package tree;

import javax.swing.tree.TreeNode;

/**
 * 红黑树
 * 1.每个节点是黑色或者红色
 * 2.根节点是黑色
 * 3.每个叶子节点（NULL）是黑色
 * 4.如果节点是红色，子节点一定是黑色
 * 5.从任意节点到达叶子节点的每条路径，必须包含相同的黑色节点数目
 *
 * 参考
 * http://www.cnblogs.com/skywang12345/p/3624343.html
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

    private void setParent(RBNode<T> node, RBNode<T> parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    private boolean colorOf(RBNode<T> node) {
       return node!=null ? node.color : BLACK;
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

    // 查找key的节点
    public RBNode<T> search(T key) {
        return search(this.root, key);
    }

    public RBNode<T> search(RBNode<T> x, T key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return search(x.left, key);
        } else if (cmp > 0) {
            return search(x.right, key);
        } else {
            return x;
        }
    }

    /**
     * 横向打印红黑树
     */
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

    /** 删除节点 **/
    public void remove(T key) {
        RBNode<T> node = search(key);
        if (node != null) {
            remove(node);
        }
    }

    public void remove(RBNode<T> node) {
        RBNode<T> child, parent;
        boolean color;

        // 被删除节点node的左右节点不为空
        if (node.left != null && node.right != null) {
            // 被删节点的后继节点。(称为"取代节点")
            // 用node的后继节点取代node，再将node去掉
            RBNode<T> replace = node.right;
            while (replace.left != null) {
                replace = replace.left;
            }

            if (parentOf(node) != null) {
                // node不是跟节点（只有根节点不存在父节点）
                if (parentOf(node).left == node) {
                    parentOf(node).left = replace;
                } else {
                    parentOf(node).right = replace;
                }
            } else {
                this.root = replace;
            }

            // child 是取代节点的右节点
            // 取代节点肯定不存在左节点，因为它是一个后继节点
            child = replace.right;
            parent = parentOf(replace);
            color = colorOf(replace);

            if (parent == node) {
                // node 是后继节点的父节点
                parent = replace;
            } else {
                // child 不为空
                if (child != null) {
                    setParent(child, parent);
                }
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK) {
                removeFixUp(child, parent);
            }
            node = null;
            return;
        }

        if (node.left != null) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;
        color = node.color;


        if (child != null) {
            // 只有一个子节点
            child.parent = parent;
        }
        if (parent != null) {
            // node节点不是根节点
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        } else {
            this.root = child;
        }

        if (color == BLACK) {
            removeFixUp(child, parent);
        }
        node = null;
    }

    // 删除重新修正红黑树
    private void removeFixUp(RBNode<T> node, RBNode<T> parent) {
        // 兄弟节点
        RBNode<T> other;

        while ((node == null || isBlack(node)) && node != this.root) {
            if (parent.left == node) {
                // 关注节点node是做子节点
                other = parent.right;
                // CASE 1: 兄弟节点是红色
                // 兄弟节点设为黑色，父节点设为红色，围绕父节点左旋
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left == null || isBlack(other.left))
                        && (other.right == null || isBlack(other.right))) {
                    // CASE 2: 兄弟节点是黑色，兄弟节点的两个子节点都是黑色
                    // 兄弟节点设为红色，关注节点node改成父节点
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (other.right == null || isBlack(other.right)) {
                        // CASE 3: 兄弟节点是黑色，它的左子节点是红色，右子节点是黑色
                        // 左子节点设为黑色，兄弟节点设为红色，并围绕兄弟节点右旋
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }

                    // CASE 4: 兄弟节点是黑色，它的右子节点是红色，左子节点任意颜色
                    // 兄弟节点的颜色设为父节点一样的颜色，
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {
                // 相反的
                other = parent.left;
                if (isRed(other)) {
                    // CASE 1: 兄弟节点是红色
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left == null || isBlack(other.left))
                        && (other.right == null || isBlack(other.right))) {
                    // CASE 2: 兄弟节点是黑色，两个子节点都是黑色
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (other.left == null || isBlack(other.left)) {
                        // CASE 3: 兄弟节点是黑色，左子节点是黑色，右子节点红色
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    // CASE 4: 兄弟节点是黑色，左子节点是红色，右子节点任意颜色
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }

            }
        }

        // 根节点设为黑色
        if (node != null) {
            setBlack(node);
        }
    }


    public static void main(String[] args) {

        RedBlackTree<Integer> tree = new RedBlackTree<>();

        int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        for (int i = 0; i < a.length; i++) {
            System.out.println("----插入" + a[i] + "-----");
            tree.insert(a[i]);
            tree.print();
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println("----删除" + a[i] + "-----");
            tree.remove(a[i]);
            tree.print();
        }
    }

}
