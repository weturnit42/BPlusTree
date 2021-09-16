import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

class Tree{
    Node root;
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

    public Node(int val, Node left, Node right){
        key = new ArrayList<Integer>(3);
        pointer = new ArrayList<Node>(4);
        this.key.add(val);
        this.pointer.add(left);
        this.pointer.add(right);
        isRoot = false;
    }

    public void insert(int val){
        if()
        if(this.key.size() <= 3) {
            this.key.add(val);
            this.pointer.add(null);
            Collections.sort(this.key);

            if(this.key.size() == 3){
                if(isRoot){
                    for(int i=0;i<this.key.size();i++)
                        System.out.println(this.key.get(i));
                    for(int i=0;i<this.pointer.size();i++)
                        System.out.println(this.pointer.get(i));

                    int mid = this.key.get(1);
                    System.out.println(mid);
                    Node left = new Node(this.key.get(0), this.pointer.get(0), this.pointer.get(1));
                    Node right = new Node(this.key.get(2), this.pointer.get(2), this.pointer.get(3));
                    
                    this.key.clear();
                    this.pointer.clear();
                    
                    this.key.add(mid);
                    this.pointer.add(left);
                    this.pointer.add(right);

                    left.parent = this;
                    right.parent = this;
                    
                }
            }
        }
    }

    public static void print(Node node) {
        for (int i = 0; i < node.key.size(); i++)
            System.out.print(node.key.get(i) + " ");
    }

    public static void preorder(Node node) {
        if (node == null)
            return;
        Node.print(node);
        for (int i = 0; i < node.pointer.size(); i++)
            preorder(node.pointer.get(i));
    }
    public static void postorder(Node node) {
        if (node == null)
            return;
        for (int i = 0; i < node.pointer.size(); i++)
            postorder(node.pointer.get(i));
        Node.print(node);
    }
    public static void myorder(Node node) {
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(node);
        Node temp;
        while (queue.size() != 0) {
            temp = queue.poll();
            Node.print(temp);
            for (int i = 0; i < temp.pointer.size(); i++){
                if(temp.pointer.get(i) != null)
                    queue.add(temp.pointer.get(i));
            }
        }
        System.out.println();
    }
}

public class TwoThreeTree {
    public static void main(String[] args) throws Exception {
        Node root = new Node();
        root.isRoot = true;
        root.insert(21);
        root.insert(30);
        root.insert(1);
        root.insert(2);
        Node.myorder(root);
        
    }
}
