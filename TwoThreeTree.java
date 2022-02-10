import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.io.*;
import java.util.*;

class Tree {
    Node root;

    public void print(Node node) {
        if(node == null)
            System.out.print("null");
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
            for (int i = 0; i < temp.pointer.size(); i++) {
                if (temp.pointer.get(i) != null)
                    queue.add(temp.pointer.get(i));
            }
        }
        System.out.println();
    }

    public Node find(Node node, int val){
        if (node.key.contains(val))
            return node;

        else if (node.key.size() == 1) {
            if (node.pointer.get(0) == null) {
                return node;
            }

            if (val < node.key.get(0))
                return find(node.pointer.get(0), val);
            else if (val > node.key.get(0))
                return find(node.pointer.get(1), val);
        }

        else if (node.key.size() == 2) {
            if (node.pointer.get(0) == null) {
                return node;
            }

            if (val < node.key.get(0))
                return find(node.pointer.get(0), val);
            else if (val > node.key.get(0) && val < node.key.get(1))
                return find(node.pointer.get(1), val);
            else if (val > node.key.get(1))
                return find(node.pointer.get(2), val);
        }

        return null;
    }

    public void insert(Node node, int val, boolean recursive_up) {
        System.out.println("Inserting " + val + ", recursive_up : " + recursive_up);
        if(node.key.size() == 0) { //시작인 경우
            node.key.add(val);
            node.pointer.add(null);
        }
        else {
            System.out.print("going down to "); //이미 root가 있는 경우
            this.print(node);
            System.out.println();

            if(node.pointer.get(0) == null || recursive_up == true){
                System.out.println("found where to insert " + val);
                this.print(node);
                System.out.println();
                if (node.key.size() <= 3) {
                    node.key.add(val);
                    node.pointer.add(null);
                    Collections.sort(node.key);
    
                    if (node.key.size() == 3) {
                        if (node.parent == null) {
                            System.out.println("I'm the root!");
                            this.print(root);
                            System.out.println();
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
    
                        } else {
                            System.out.println("insert : " + val + ", node.parent.key.size() : " + node.parent.key.size());
                            System.out.print("Node.parent : ");
                            this.print(node.parent);
                            System.out.println();
                            if (node.parent.key.size() == 1) {
                                int mid = node.key.get(1);
                                System.out.println("mid1 : " + mid);
                                System.out.println("node.parent.key.get(0) : " + node.parent.key.get(0));

                                Node child1 = null, child2 = null, child3 = null;
                                if(mid < node.parent.key.get(0)) { //왜 나누는 거?
                                    System.out.println("mid < node.parent.key.get(0)");
                                    child1 = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1));
                                    child2 = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3));
                                    child3 = node.parent.pointer.get(1);
                                }
                                else if(mid == node.parent.key.get(0)) {
                                    System.out.println("Same key inserted");
                                }
                                else if(mid > node.parent.key.get(0)){
                                    System.out.println("mid > node.parent.key.get(0)");
                                    child1 = node.parent.pointer.get(0);
                                    child2 = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1)); //여기 말하는 거
                                    //System.out.println("node.key.get(0) : " + node.key.get(0));
                                    //System.out.println("node.key.get(1) : " + node.key.get(1));
                                    child3 = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3));
                                }
                                
                                node.parent.pointer.set(0, child1);
                                node.parent.pointer.set(1, child2);
                                node.parent.pointer.add(child3);

                                node.parent.pointer.get(0).parent = node.parent;
                                node.parent.pointer.get(1).parent = node.parent;
                                node.parent.pointer.get(2).parent = node.parent;

                                node.key.remove(1);
                                insert(node.parent, mid, true);
                                Collections.sort(node.parent.key);
                            } else if (node.parent.key.size() == 2) {
                                int mid = node.key.get(1);
                                System.out.println("mid2 : " + mid);
                                System.out.println("node.parent.key.get(0) : " + node.parent.key.get(0));
                                System.out.println("node.parent.key.get(1) : " + node.parent.key.get(1));

                                Node child1 = null, child2 = null, child3 = null, child4 = null;
                                if(mid < node.parent.key.get(0)) {
                                    child1 = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1)); //위에 저기처럼 child 구분하고 시작해보자. 20210923 수정부분
                                    child2 = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3));
                                    child3 = node.parent.pointer.get(1);
                                    child4 = node.parent.pointer.get(2);
                                }
                                else if(mid > node.parent.key.get(0) && mid < node.parent.key.get(1)){
                                    child1 = node.parent.pointer.get(0);
                                    child2 = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1));
                                    child3 = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3));
                                    child4 = node.parent.pointer.get(2);
                                }
                                else if(mid > node.parent.key.get(1)){
                                    child1 = node.parent.pointer.get(0);
                                    child2 = node.parent.pointer.get(1);
                                    child3 = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1));
                                    child4 = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3));
                                }
                                else
                                    System.out.println("Same key inserted");

                                node.parent.pointer.set(0, child1);
                                node.parent.pointer.set(1, child2);
                                node.parent.pointer.set(2, child3);
                                node.parent.pointer.set(3, child4);

                                node.parent.pointer.get(0).parent = node.parent;
                                node.parent.pointer.get(1).parent = node.parent;
                                node.parent.pointer.get(2).parent = node.parent;
                                node.parent.pointer.get(3).parent = node.parent;

                                node.key.remove(1);
                                insert(node.parent, mid, true);
                                Collections.sort(node.parent.key);
                            }
                        }
                    }
                }
            }

            else if(node.key.size() == 1){
                if (val < node.key.get(0))
                    insert(node.pointer.get(0), val, false);
                else if (val > node.key.get(0))
                    insert(node.pointer.get(1), val, false);
            }

            else if(node.key.size() == 2){
                if (val < node.key.get(0))
                    insert(node.pointer.get(0), val, false);
                else if (val > node.key.get(0) && val < node.key.get(1))
                    insert(node.pointer.get(1), val, false);
                else if (val > node.key.get(1))
                    insert(node.pointer.get(2), val, false);
            }
        }
    }
    
    public void delete(Node node, int val, boolean recursive_down){
        print(node);
        System.out.println(", size : " + node.key.size());
        System.out.println();
        
        if(node.key.size() == 2){
            if (val < node.key.get(0))
                delete(node.pointer.get(0), val, false);
            else if (val > node.key.get(0) && val < node.key.get(1))
                delete(node.pointer.get(1), val, false);
            else if (val > node.key.get(1))
                delete(node.pointer.get(2), val, false);
            else{
                if(node.pointer.get(0) == null){
                    node.key.remove(Integer.valueOf(val));
                }
                else{ //사실 좌우 노드가 2-node인지는 중요하지 않다. 결국에 내가 바꿀 수 있는 노드가 2-node인지 확인하는 것이 중요.
                    if(find(node, findBiggestSmallNode(node, val, false)).key.size() == 2){
                        print(find(node, findBiggestSmallNode(node, val, false)));
                        int temp = findBiggestSmallNode(node, val, false);
                        System.out.println("temp : " + temp);
                        System.out.println();
                        node.key.remove(Integer.valueOf(val));
                        find(node, temp).key.remove(Integer.valueOf(temp));
                        node.key.add(temp);
                        Collections.sort(node.key);
                    }

                    else if(find(node, findSmallestBigNode(node, val, false)).key.size() == 2){
                        print(find(node, findSmallestBigNode(node, val, false)));
                        int temp = findSmallestBigNode(node, val, false);
                        System.out.println("temp : " + temp);
                        System.out.println();
                        find(node, temp).key.remove(Integer.valueOf(temp));
                        node.key.remove(Integer.valueOf(val));
                        System.out.print("tempNode : ");
                        print(find(node, temp));
                        System.out.println();
                        node.key.add(temp);
                        Collections.sort(node.key);
                    }

                    else{

                    }
                }
            }
        }

        else if(node.key.size() == 1){
            if (val < node.key.get(0))
                delete(node.pointer.get(0), val, false);
            else if (val > node.key.get(0))
                delete(node.pointer.get(1), val, false);
            else{
                if(node.pointer.get(0) == null){
                    node.key.remove(Integer.valueOf(val));
                }
                else{ //사실 좌우 노드가 2-node인지는 중요하지 않다. 결국에 내가 바꿀 수 있는 노드가 2-node인지 확인하는 것이 중요.
                    if(find(node, findBiggestSmallNode(node, val, false)).key.size() == 2){
                        print(find(node, findBiggestSmallNode(node, val, false)));
                        int temp = findBiggestSmallNode(node, val, false);
                        System.out.println("temp : " + temp);
                        System.out.println();
                        node.key.remove(Integer.valueOf(val));
                        find(node, temp).key.remove(Integer.valueOf(temp));
                        node.key.add(temp);
                        Collections.sort(node.key);
                    }

                    else if(find(node, findSmallestBigNode(node, val, false)).key.size() == 2){
                        print(find(node, findSmallestBigNode(node, val, false)));
                        int temp = findSmallestBigNode(node, val, false);
                        System.out.println("temp : " + temp);
                        System.out.println();
                        find(node, temp).key.remove(Integer.valueOf(temp));
                        node.key.remove(Integer.valueOf(val));
                        System.out.print("tempNode : ");
                        //print(find(node, temp));
                        System.out.println();
                        node.key.add(temp);
                        Collections.sort(node.key);
                    }

                    else{
                        
                    }
                }
            }
        }
    }

    public int findBiggestSmallNode(Node node, int val, boolean recursive_down){
        //System.out.println(val);
        if(val == node.key.get(0)){
            //System.out.println("type 1");
            if(recursive_down == false) {
                
                if(node.pointer.get(0) == null)
                    return -1;

                else if(node.pointer.get(0).pointer.get(0) == null)
                    return node.pointer.get(0).key.get(node.pointer.get(0).key.size()-1);

                else
                    return findBiggestSmallNode(node.pointer.get(0), node.pointer.get(0).key.get(node.pointer.get(0).key.size()-1), true);
            }
            else{
                if(node.pointer.get(0) == null)
                    return node.key.get(node.key.size()-1);
                else
                    return findBiggestSmallNode(node.pointer.get(1), node.pointer.get(1).key.get(node.pointer.get(1).key.size()-1), true);
            }
        }
        else{
            //System.out.println("type 2");
            if(recursive_down == false) {
                if(node.pointer.get(1) == null)
                    return -1;

                else if(node.pointer.get(1).pointer.get(0) == null)
                    return node.pointer.get(1).key.get(node.pointer.get(1).key.size()-1);

                else
                    return findBiggestSmallNode(node.pointer.get(1), node.pointer.get(1).key.get(node.pointer.get(1).key.size()-1), true);
            }
            else{
                //System.out.println("type 2-2");
                if(node.pointer.get(0) == null)
                    return node.key.get(node.key.size()-1);
                else{
                    //System.out.println("type 2-2-2");
                    return findBiggestSmallNode(node.pointer.get(2), node.pointer.get(2).key.get(node.pointer.get(2).key.size()-1), true);
                }
            }
        }
    }

    public int findSmallestBigNode(Node node, int val, boolean recursive_down){
        //System.out.println(val);
        if(val == node.key.get(0)){
            if(recursive_down == false) {
                if(node.pointer.get(1) == null)
                    return -1;

                else if(node.pointer.get(1).pointer.get(1) == null)
                    return node.pointer.get(1).key.get(0);

                else
                    return findSmallestBigNode(node.pointer.get(1), node.pointer.get(1).key.get(0), true);
            }
            else{
                if(node.pointer.get(0) == null)
                    return node.key.get(0);
                else
                    return findSmallestBigNode(node.pointer.get(0), node.pointer.get(0).key.get(0), true);
            }
        }
        else{
            if(recursive_down == false) {
                if(node.pointer.get(0) == null)
                    return -1;

                else if(node.pointer.get(2).pointer.get(0) == null)
                    return node.pointer.get(2).key.get(0);

                else
                    return findSmallestBigNode(node.pointer.get(2), node.pointer.get(2).key.get(0), true);
            }
            else{
                if(node.pointer.get(0) == null)
                    return node.key.get(0);
                else
                    return findSmallestBigNode(node.pointer.get(0), node.pointer.get(0).key.get(0), true);
            }
        }
    }
}

class Node {
    ArrayList<Integer> key;
    ArrayList<Node> pointer;
    Node parent;
    boolean isRoot;

    public Node() {
        key = new ArrayList<Integer>(3);
        pointer = new ArrayList<Node>(4);
        pointer.add(null);
        parent = null;
        isRoot = false;
    }

    public Node(int val) {
        key = new ArrayList<Integer>(3);
        pointer = new ArrayList<Node>(4);
        this.key.add(val);
        this.pointer.add(null);
        this.pointer.add(null);
        isRoot = false;
    }

    public Node(int val, Node left, Node right) {
        key = new ArrayList<Integer>(3);
        pointer = new ArrayList<Node>(4);
        this.key.add(val);
        this.pointer.add(left);
        this.pointer.add(right);
        isRoot = false;
        if(left != null)
            left.parent = this;
        if(right != null)
            right.parent = this;
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

        // tree.insert(root, 30, false);
        // tree.insert(root, 40, false);
        // tree.insert(root, 10, false);
        // tree.insert(root, 15, false);
        // tree.insert(root, 35, false);
        // tree.insert(root, 20, false);
        // tree.insert(root, 5, false);
        // tree.insert(root, 25, false);
        // tree.insert(root, 1, false);
        // tree.insert(root, 2, false);
        // tree.insert(root, 3, false);
        // tree.insert(root, 11, false);
        // tree.insert(root, 12, false);
        // tree.insert(root, 4, false);
        // tree.insert(root, 5, false);
        // tree.insert(root, 6, false);
        // tree.insert(root, 7, false);
        // tree.insert(root, 8, false);
        // tree.insert(root, 26, false);
        // tree.insert(root, 27, false);
        // tree.insert(root, 28, false);
        
        // System.out.println("--------------------------------");
        // tree.myorder(root);
        // tree.print(root);
        // System.out.println();

        // tree.delete(root, 30, false);
        // tree.myorder(root);
        // tree.print(root);

        File csv = new File("C:\\Users\\SAMSUNG\\Downloads\\data\\input.csv");
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(csv));
        for(int i=0;i<1000;i++){
            String line = "";
            line = br.readLine();
            String[] lineArr = line.split("\t");
            System.out.println(lineArr[1]);
            tree.insert(root, Integer.parseInt(lineArr[1]), false);
        }
        br.close();
    }
}