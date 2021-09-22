import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

class Tree{
    Node root;

    public void print(Node node) {
        for (int i = 0; i < node.key.size(); i++)
            System.out.print(node.key.get(i) + " ");
    }

    public void preorder(Node node) {
        if (node == null)
            return;
        this.print(node);
        for (int i = 0; i < node.pointer.size(); i++)
            preorder(node.pointer.get(i));
    }
    public void postorder(Node node) {
        if (node == null)
            return;
        for (int i = 0; i < node.pointer.size(); i++)
            postorder(node.pointer.get(i));
        this.print(node);
    }
    public void myorder(Node node) {
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(node);
        Node temp;
        while (queue.size() != 0) {
            temp = queue.poll();
            this.print(temp);
            for (int i = 0; i < temp.pointer.size(); i++){
                if(temp.pointer.get(i) != null)
                    queue.add(temp.pointer.get(i));
            }
        }
        System.out.println();
    }

    public void insert(Node node, int val){
        node = node.findPlace(val);
        if(node.key.size() <= 3) {
            node.key.add(val);
            node.pointer.add(null);
            Collections.sort(node.key);

            if(node.key.size() == 3){

                if(node.isRoot == true){
                    int mid = node.key.get(1);
                    Node left = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1));
                    Node right = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3));
                    
                    node.key.clear();
                    node.pointer.clear();
                    
                    node.key.add(mid);
                    node.pointer.add(left);
                    node.pointer.add(right);

                    left.parent = node;
                    right.parent = node;
                    
                }
                else if(node.parent.key.size() == 1){
                    int mid = node.key.get(1);
                    node.parent.key.add(mid);
                    Collections.sort(node.parent.key);
                    node.key.remove(1);

                    node.parent.pointer.add(node.parent.pointer.get(1));
                    node.parent.pointer.set(0, new Node(node.key.get(0)));
                    node.parent.pointer.set(1, new Node(node.key.get(1)));

                    node.parent.pointer.get(0).parent = node.parent;
                    node.parent.pointer.get(1).parent = node.parent;
                    node.parent.pointer.get(2).parent = node.parent;
                }
                else if(node.parent.key.size() == 2){
                    System.out.println("hey");
                }
            }
        }
    }
}

class Node {
    ArrayList<Integer> key;
    ArrayList<Node> pointer;
    Node parent;
    boolean isRoot;

    public Node(){
        key = new ArrayList<Integer>(3);
        pointer = new ArrayList<Node>(4);
        pointer.add(null);
        parent = null;
        isRoot = false;
    }

    public Node(int val){
        key = new ArrayList<Integer>(3);
        pointer = new ArrayList<Node>(4);
        this.key.add(val);
        this.pointer.add(null);
        this.pointer.add(null);
        isRoot = false;
    }

    public Node(int val, Node left, Node right){
        key = new ArrayList<Integer>(3);
        pointer = new ArrayList<Node>(4);
        this.key.add(val);
        this.pointer.add(left);
        this.pointer.add(right);
        isRoot = false;
    }

    public Node findPlace(int val) {
        if (this.key.contains(val))
            return this;

        else if (this.key.size() == 1) {
            if (this.pointer.get(0) == null) {
                return this;
            }

            if (val < this.key.get(0))
                return this.pointer.get(0).findPlace(val);
            else if (val > this.key.get(0))
                return this.pointer.get(1).findPlace(val);
        }

        else if (this.key.size() == 2) {
            if (this.pointer.get(0) == null) {
                return this;
            }

            if (val < this.key.get(0))
                return this.pointer.get(0).findPlace(val);
            else if (val > this.key.get(0) && val < this.key.get(1))
                return this.pointer.get(1).findPlace(val);
            else if (val > this.key.get(1))
                return this.pointer.get(2).findPlace(val);
        }

        return this;
    }
}

public class TwoThreeTree {
    public static void main(String[] args) throws Exception {
        Tree tree = new Tree();
        Node root = new Node();
        root.isRoot = true;
        tree.root = root;

        tree.insert(root, 30);
        tree.insert(root, 40);
        tree.insert(root, 10);
        tree.insert(root, 15);
        tree.insert(root, 35);
        tree.insert(root, 20);
        tree.insert(root, 5);
        tree.insert(root, 25);

        tree.myorder(root);
        tree.print(root);
        System.out.println();
        tree.print(root.pointer.get(0));
        System.out.println();
        tree.print(root.pointer.get(1));
        System.out.println();
        tree.print(root.pointer.get(2));
        System.out.println();

    }
}