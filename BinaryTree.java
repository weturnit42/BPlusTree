import java.util.*;

class Node {
    public int val;
    public Node left, right;

    public Node(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    public static void insert(Node root, int val) {
        if (root.left == null && val < root.val) {
            root.left = new Node(val);
            return;
        } else if (root.right == null && val > root.val) {
            root.right = new Node(val);
            return;
        }

        else if (val < root.val)
            insert(root.left, val);
        else if (val > root.val)
            insert(root.right, val);
    }

    public static void delete(Node node, Node parent, int val) {
        if (val < node.val)
            delete(node.left, node, val);
        else if (val > node.val)
            delete(node.right, node, val);
        else {
            if (node.left == null && node.right == null) {
                if(node.val < parent.val){
                    parent.left = null;
                    node = null;
                }
                else{
                    parent.right = null;
                    node = null;
                }
                return;
            }

            else if (node.left != null && node.right == null) {
                parent.left = node.left;
                return;
            }

            else if (node.left == null && node.right != null) {
                parent.right = node.right;
                return;
            }

            else {
                Node maxNode = node.left;
                Node maxNodeParent = node;
                Node minNode = node.right;
                Node minNodeParent = node;
                while (maxNode.right != null){
                    maxNodeParent = maxNode;
                    maxNode = maxNode.right;
                }
                while (minNode.left != null){
                    minNodeParent = minNode;
                    minNode = minNode.left;
                }
                if (node.val < parent.val) {
                    int temp = maxNode.val;
                    maxNode.val = node.val;
                    node.val = temp;

                    // System.out.println("node : " + node.val);
                    // System.out.println("maxNode : " + maxNode.val);
                    // System.out.println("parent : " + parent.val);
                    // System.out.println("maxNodeParent : " + maxNodeParent.val);
                    if(maxNode.left == null)
                        node.left = null;
                    else
                        maxNodeParent.right = null;
                } else if (node.val > parent.val) {
                    int temp = minNode.val;
                    minNode.val = node.val;
                    node.val = temp;

                    System.out.println("node : " + node.val);
                    System.out.println("minNode : " + minNode.val);
                    System.out.println("parent : " + parent.val);
                    System.out.println("minNodeParent : " + minNodeParent.val);
                    if(minNode.right == null)
                        node.right = null;
                    else
                        minNodeParent.left = null;
                }
            }
        }
    }

    public static void preorder(Node root) {
        if (root == null)
            return;

        System.out.println(root.val);
        preorder(root.left);
        preorder(root.right);
    }

    public static void inorder(Node root) {
        if (root == null)
            return;

        inorder(root.left);
        System.out.println(root.val);
        inorder(root.right);
    }

    public static void postorder(Node root) {
        if (root == null)
            return;

        postorder(root.left);
        postorder(root.right);
        System.out.println(root.val);
    }

    public static void myorder(Node node) {
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(node);
        Node temp;
        while (queue.size() != 0) {
            temp = queue.poll();
            System.out.print(temp.val + " ");
            if(temp.left != null){
                queue.add(temp.left);
            }
            if(temp.right != null){
                queue.add(temp.right);
            }
        }
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        Node root = new Node(30);
        Node.insert(root, 21);
        Node.insert(root, 40);
        Node.insert(root, 17);
        Node.insert(root, 25);
        Node.insert(root, 35);
        Node.insert(root, 47);
        Node.insert(root, 10);
        Node.insert(root, 20);
        Node.insert(root, 23);
        Node.insert(root, 27);
        Node.insert(root, 32);
        Node.insert(root, 37);
        Node.insert(root, 43);
        Node.insert(root, 48);

        Node.delete(root, root, 40);
        Node.myorder(root);
    }
}
