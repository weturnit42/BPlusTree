import java.util.ArrayList;
import java.util.Collections;

class Node {
    ArrayList<Integer> key = new ArrayList<Integer>();
    ArrayList<Node> pointer = new ArrayList<Node>();

    public static void insert(Node root, int val){
        if(root.key.size() < 2){
            root.key.add(val);
            Collections.sort(root.key);
        }
    }

    public static void print(Node node){
        for(int i=0;i<node.key.size();i++)
            System.out.print(node.key.get(i) + " ");
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        Node root = new Node();
        Node.insert(root, 2);
        Node.insert(root, 1);

        Node.print(root);

    }
}
