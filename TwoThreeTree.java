import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

class Node {
    ArrayList<Integer> key = new ArrayList<Integer>();
    ArrayList<Node> pointer = new ArrayList<Node>();

    public Node(){
        pointer.add(null);
        pointer.add(null);
    }

    public static Node findPlace(Node root, int val) {
        if (root.key.contains(val))
            return root;

        else if (root.key.size() == 1) {
            if (root.pointer.get(0) == null) {
                return root;
            }

            if (val < root.key.get(0))
                return findPlace(root.pointer.get(0), val);
            else if (val > root.key.get(0))
                return findPlace(root.pointer.get(1), val);
        }

        else if (root.key.size() == 2) {
            if (root.pointer.get(0) == null) {
                return root;
            }

            if (val < root.key.get(0))
                return findPlace(root.pointer.get(0), val);
            else if (val > root.key.get(0) && val < root.key.get(1))
                return findPlace(root.pointer.get(1), val);
            else if (val > root.key.get(1))
                return findPlace(root.pointer.get(2), val);
        }

        return root;
    }

    public static Node findPlaceParent(Node root, int val) {
        if (root.pointer.size() == 2) {
            if (root.pointer.get(0).key.contains(Node.findPlace(root, val).key.get(0)) || root.pointer.get(1).key.contains(Node.findPlace(root, val).key.get(0)))
                return root;
            else {
                if (val < root.key.get(0))
                    return findPlaceParent(root.pointer.get(0), Node.findPlace(root, val).key.get(0));
                else if (val > root.key.get(0))
                    return findPlaceParent(root.pointer.get(1), Node.findPlace(root, val).key.get(0));
            }
        }

        if (root.pointer.size() == 3) {
            if (root.pointer.get(0).key.contains(Node.findPlace(root, val).key.get(0)) || root.pointer.get(1).key.contains(Node.findPlace(root, val).key.get(0)) || root.pointer.get(2).key.contains(Node.findPlace(root, val).key.get(0)))
                return root;
            else {
                if (val < root.key.get(0))
                    return findPlaceParent(root.pointer.get(0), Node.findPlace(root, val).key.get(0));
                else if (val > root.key.get(0) && val < root.key.get(1))
                    return findPlaceParent(root.pointer.get(1), Node.findPlace(root, val).key.get(0));
                else if (val > root.key.get(1))
                    return findPlaceParent(root.pointer.get(2), Node.findPlace(root, val).key.get(0));
            }
        }
        return root;      
    }

    public static void insert(Node node, int val) {
        Node tarNode;
        if(node.key.size() != 0)
            tarNode = Node.findPlace(node, val);
        else
            tarNode = node;
        //Node grandParNode = Node.findPlaceParent(root, parNode.key.get(0));

        if (tarNode.key.size() < 3) {
            tarNode.key.add(val);
            tarNode.pointer.add(null);
            Collections.sort(tarNode.key);

            if(tarNode.key.size() == 3){
                Node leftNode = new Node();
                Node rightNode = new Node();

                leftNode.key.add(tarNode.key.get(0));
                int mid = tarNode.key.get(1);
                rightNode.key.add(tarNode.key.get(2));

                leftNode.pointer.add(tarNode.pointer.get(0));
                leftNode.pointer.add(tarNode.pointer.get(1));
                rightNode.pointer.add(tarNode.pointer.get(2));
                rightNode.pointer.add(tarNode.pointer.get(3));

                tarNode.key.clear();
                tarNode.pointer.clear();

                tarNode.key.add(mid);
                tarNode.pointer.add(leftNode);
                tarNode.pointer.add(rightNode);
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
        Node.insert(root, 21);
        Node.insert(root, 30);
        Node.myorder(root);
        
        Node.insert(root, 1);
        Node.myorder(root);

        Node.insert(root, 2);
        Node.myorder(root);

        Node.insert(root, 3);
        Node.myorder(root);
    }
}
