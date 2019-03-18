package tree;

/**
 * 二叉查找树
 */
public class BinarySearchTree {

    // 二叉树根节点
    private Node tree;

    public Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (p.data < data) {
                p = p.right;
            } else if (p.data > data){
                p = p.left;
            } else {
                return p;
            }
        }
        return null;
    }

    public void insert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return;
        }
        Node p = tree;
        while (p != null) {
            if (data < p.data) {
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                } else {
                    p = p.left;
                }
            } else {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                } else {
                    p = p.right;
                }
            }
        }
    }

    public void delete(int data) {

        Node p = tree;// 要删除的节点
        Node pp = null;// 要删除的节点的父节点
        while (p != null && p.data != data) {
            pp = p;
            if (data > p.data) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        if (p == null) {
            // 没有找到
            return;
        }
        // 要删除的节点有左右子树，找到右子树的最小值
        if (p.left != null && p.right != null) {
            Node minP = p.right;
            Node minPP = p;
            while (minP.left != null) {
                minPP = minP;
                minP = minP.left;
            }
            p.data = minP.data; //将 minP 的数据替换到 p 中
            p = minP; //下面就是删除 minP 节点了
            pp = minPP;
        }

        // 删除节点是叶子节点或者仅有一个子节点
        Node child; // p的子节点
        if (p.left != null) {
            child = p.left;
        } else if (p.right != null) {
            child = p.right;
        } else {
            child = null;
        }
        // 删除根节点
        if (pp == null) {
            tree = child;
        } else if(pp.left == p) {
            p.left = child;
        } else if (pp.right == p) {
            p.right = child;
        }

    }


    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
    }
}
