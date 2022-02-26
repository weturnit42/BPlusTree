import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.io.*;
import java.util.*;

class Tree {
    Node root;

    public void print(Node node) {
        if(node == null){
            System.out.print("null");
            return;
        }
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
        //System.out.println("Inserting " + val + ", recursive_up : " + recursive_up);
        if(node.key.size() == 0) { //시작인 경우
            node.key.add(val);
            node.pointer.add(null);
        }
        else {
            //System.out.print("going down to "); //이미 root가 있는 경우
            //this.print(node);
            //System.out.println();

            if(node.pointer.get(0) == null || recursive_up == true){ //leaf에 도달했거나 재귀적으로 반복했을 경우
                //System.out.println("found where to insert " + val);
                //this.print(node);
                //System.out.println();
                if (node.key.size() <= 3) {
                    node.key.add(val);
                    node.pointer.add(null);
                    Collections.sort(node.key);
    
                    if (node.key.size() == 3) { //만약 키가 3개라면 == split이 필요한 경우라면
                        if (node.parent == null) { //만약 parent가 없을 때 == node가 root일 때
                            //System.out.println("I'm the root!");
                            //this.print(root);
                            //System.out.println();
                            int mid = node.key.get(1); //node를 새롭게 만드는 것이 아닌, 원래의 노드를 parent로 활용한다.
                            Node left = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1)); //leftChild 생성
                            Node right = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3)); //rightChild 생성
    
                            node.key.clear(); //node의 key와 pointer 모두 지우고
                            node.pointer.clear();
    
                            node.key.add(mid); //키와 parent-child 관계 재생성
                            node.pointer.add(left);
                            node.pointer.add(right);
    
                            left.parent = node;
                            right.parent = node;
    
                        } else { //node가 root가 아닐 때
                            //System.out.println("insert : " + val + ", node.parent.key.size() : " + node.parent.key.size());
                            //System.out.print("Node.parent : ");
                            //this.print(node.parent);
                            //System.out.println();
                            if (node.parent.key.size() == 1) { //parent size가 1일 때 == 재귀적으로 split이 필요없다.
                                int mid = node.key.get(1);
                                //System.out.println("mid1 : " + mid);
                                //System.out.println("node.parent.key.get(0) : " + node.parent.key.get(0));

                                Node child1 = null, child2 = null, child3 = null;
                                if(mid < node.parent.key.get(0)) { //왜 나누는 거?
                                    //System.out.println("mid < node.parent.key.get(0)");
                                    child1 = new Node(node.key.get(0), node.pointer.get(0), node.pointer.get(1));
                                    child2 = new Node(node.key.get(2), node.pointer.get(2), node.pointer.get(3));
                                    child3 = node.parent.pointer.get(1);
                                }
                                else if(mid == node.parent.key.get(0)) {
                                    System.out.println("Same key inserted");
                                }
                                else if(mid > node.parent.key.get(0)){
                                    //System.out.println("mid > node.parent.key.get(0)");
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
                            } else if (node.parent.key.size() == 2) { //parent size가 2일 때 == 재귀적으로 split이 필요하다.
                                int mid = node.key.get(1);
                                // System.out.println("mid2 : " + mid);
                                // System.out.println("node.parent.key.get(0) : " + node.parent.key.get(0));
                                // System.out.println("node.parent.key.get(1) : " + node.parent.key.get(1));

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
                                insert(node.parent, mid, true); //재귀적으로 돌리기. recursive_up을 true로 준다.
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
    
    public void delete(Node node, int val, boolean recursive_up){
        // System.out.println("Deleting " + val);
        // if(node != null && node.parent != null){
        //     System.out.print("node.parent : ");
        //     print(node.parent);
        //     System.out.println();
        // }

        // if(node != null){
        //     System.out.print("node : ");
        //     print(node);
        //     System.out.println();
        // }
        
        // if(node != null && node.pointer.get(0) != null){
        //     System.out.print("node.pointer.get(0) : "); 
        //     print(node.pointer.get(0));
        //     System.out.println();
        // }
        
        // if(node != null && node.pointer.size() > 1 && node.pointer.get(1) != null){
        //     System.out.print("node.pointer.get(1) : ");
        //     print(node.pointer.get(1));
        //     System.out.println();
        // }

        // if(node != null && node.pointer.size() > 2 && node.pointer.get(2) != null){
        //     System.out.print("node.pointer.get(2) : ");
        //     print(node.pointer.get(2));
        //     System.out.println();

        //     if(node.pointer.get(1) == null)
        //         System.out.println("Something wrong");
        // }
        int idx = 0;
        if(node == null)  // 위험한 코드 20220225
            return;
        if(node.parent == null && (node.key.size() == 0)){
            this.root = node.pointer.get(0);
            this.root.isRoot = true;
        }
        else if(node.key.size() != 0)
            idx = (node.parent == null) ? 0 : node.parent.pointer.indexOf(node);
        else{
            if(node.parent.key.size() == 1){
                if(node.parent.pointer.get(0).key.size() == 0) 
                    idx = 0;
                else
                    idx = 1;
            }

            else if(node.parent.key.size() == 2){
                if(node.parent.pointer.get(0).key.size() == 0) 
                    idx = 0;
                else if(node.parent.pointer.get(1).key.size() == 0)
                    idx = 1;
                else
                    idx = 2;
            }
        }
        //System.out.println("Deleting " + val + ", recursive_up is " + recursive_up + ", idx : " + idx);
        int tempCnt = node.pointer.size(); // 위험한 코드 20220225
        for(int i=node.pointer.size()-1;i>=0;i--){
            if(node.pointer.get(i) == null)
                tempCnt--;
            if(node.pointer.get(i) != null)
                break;
        }
        if(recursive_up == true || node.key.contains(Integer.valueOf(val))){
            //System.out.println("case1");
            if(node.key.size() == 2){
                //System.out.println("case1-1");
                if(node.pointer.get(0) == null){ //node가 2-node면서 leaf인 경우
                    node.key.remove(Integer.valueOf(val));
                }
                else{
                    Node biggestSmallNode = find(node, findBiggestSmallNode(node, val, false));
                    Node smallestBigNode = find(node, findSmallestBigNode(node, val, false));

                    //System.out.println("case1-1-2");
                    if(biggestSmallNode.key.size() == 2){
                        //System.out.println("case1-1-2-1");
                        int temp = findBiggestSmallNode(node, val, false);

                        node.key.remove(Integer.valueOf(val));
                        node.key.add(temp);
                        Collections.sort(node.key);
                        biggestSmallNode.key.remove(Integer.valueOf(temp));
                    }

                    else if(smallestBigNode.key.size() == 2){
                        //System.out.println("case1-1-2-2");
                        int temp = findSmallestBigNode(node, val, false);

                        node.key.remove(Integer.valueOf(val));
                        node.key.add(temp);
                        Collections.sort(node.key);
                        smallestBigNode.key.remove(Integer.valueOf(temp));
                    }

                    else {
                        //System.out.println("case1-1-2-3");
                        int temp = findBiggestSmallNode(node, val, false);

                        node.key.remove(Integer.valueOf(val));
                        node.key.add(temp);
                        Collections.sort(node.key);
                        biggestSmallNode.key.remove(Integer.valueOf(temp));

                        delete(biggestSmallNode, val, true);
                    }
                    
                }
            }

            else if(node.key.size() == 1){
                //System.out.println("case1-2");
                if(node.pointer.get(0) == null){ //1-node면서 leaf
                    //System.out.println("case1-2-1");
                    if(node.isRoot == true) //1-node면서 leaf면서 root
                        node.key.remove(Integer.valueOf(val));
                    else{ //1-node면서 leaf면서 root는 아님 -> parent != null
                        //Node parentNode = node.parent;
                        // print(node.parent);
                        // System.out.println();
                        // System.out.println("node.parent.key.size() : " + node.parent.key.size());
                        if(node.parent.key.size() == 2){
                            if(idx == 0 && node.parent.pointer.get(1).key.size() == 1){ //case1
                                //System.out.println("case1-2-1-1");
                                node.key.remove(Integer.valueOf(val));
                                node.key.add(node.parent.key.get(0));
                                node.key.add(node.parent.pointer.get(1).key.get(0));
                                node.parent.key.remove(0);
                                node.parent.pointer.remove(1);

                                Collections.sort(node.key);
                                Collections.sort(node.parent.key);
                            }
                            else if(idx == 0 && node.parent.pointer.get(1).key.size() == 2){ //case2
                                //System.out.println("case1-2-1-2");
                                node.key.remove(Integer.valueOf(val));
                                node.key.add(node.parent.key.get(0));
                                node.parent.key.remove(0);
                                node.parent.key.add(node.parent.pointer.get(1).key.get(0));
                                node.parent.pointer.get(1).key.remove(0);

                                Collections.sort(node.key);
                                Collections.sort(node.parent.key);
                                Collections.sort(node.parent.pointer.get(1).key);
                            }
                            else if(idx == 1 && node.parent.pointer.get(0).key.size() == 2){ //case3
                                //System.out.println("case1-2-1-3");
                                node.key.remove(Integer.valueOf(val));
                                node.key.add(node.parent.key.get(0));
                                node.parent.key.remove(0);
                                node.parent.key.add(node.parent.pointer.get(0).key.get(1));
                                node.parent.pointer.get(0).key.remove(1);

                                Collections.sort(node.key);
                                Collections.sort(node.parent.key);
                                Collections.sort(node.parent.pointer.get(0).key);
                            }
                            else if(idx == 1 && node.parent.pointer.get(2).key.size() == 2){ //case4
                                //System.out.println("case1-2-1-4");
                                node.key.remove(Integer.valueOf(val));
                                // node.parent.pointer.get(0).key.add(node.parent.key.get(0));
                                // node.parent.key.remove(0);
                                // node.parent.pointer.remove(1);
                                node.key.add(node.parent.key.get(1));
                                node.parent.key.remove(1);
                                node.parent.key.add(node.parent.pointer.get(2).key.get(0));
                                node.parent.pointer.get(2).key.remove(0);

                                Collections.sort(node.parent.key);
                            }
                            else if(idx == 1){ //case5
                                //System.out.println("case1-2-1-5");
                                node.key.remove(Integer.valueOf(val));
                                node.key.add(node.parent.key.get(1));
                                node.key.add(node.parent.pointer.get(2).key.get(0));
                                node.parent.key.remove(1);
                                node.parent.pointer.remove(2);

                                Collections.sort(node.key);
                                Collections.sort(node.parent.key);
                            }
                            else if(idx == 2 && node.parent.pointer.get(1).key.size() == 2){ //case6
                                //System.out.println("case1-2-1-6");
                                node.key.remove(Integer.valueOf(val));
                                node.parent.pointer.get(2).key.add(node.parent.key.get(1));
                                node.parent.key.remove(1);
                                node.parent.key.add(node.parent.pointer.get(1).key.get(1));
                                node.parent.pointer.get(1).key.remove(1);

                                Collections.sort(node.key);
                                Collections.sort(node.parent.key);
                            }
                            else if(idx == 2 && node.parent.pointer.get(1).key.size() == 1){ //case7
                                //System.out.println("case1-2-1-7");
                                node.key.remove(Integer.valueOf(val));
                                node.parent.pointer.get(1).key.add(node.parent.key.get(1));
                                node.parent.key.remove(1);
                                node.parent.pointer.remove(2);

                                Collections.sort(node.key);
                                Collections.sort(node.parent.key);
                            }
                        }
                        else if(node.parent.key.size() == 1){
                            if(idx == 0 && node.parent.pointer.get(1).key.size() == 1){ //재귀 돌려야 함
                                //System.out.println("case1-2-1-8");
                                node.key.remove(Integer.valueOf(val));
                                node.key.add(node.parent.key.get(0));
                                node.key.add(node.parent.pointer.get(1).key.get(0));
                                node.parent.key.remove(0);
                                node.parent.pointer.remove(1);

                                Collections.sort(node.key);

                                delete(node.parent, val, true);
                            }
                            else if(idx == 0 && node.parent.pointer.get(1).key.size() == 2){
                                //System.out.println("case1-2-1-9");
                                node.key.remove(Integer.valueOf(val));
                                node.key.add(node.parent.key.get(0));
                                node.parent.key.remove(0);
                                node.parent.key.add(node.parent.pointer.get(1).key.get(0));
                                node.parent.pointer.get(1).key.remove(0);
                            }
                            else if(idx == 1 && node.parent.pointer.get(0).key.size() == 1){ //재귀 돌려야 함
                                //System.out.println("case1-2-1-10");
                                node.parent.pointer.get(0).key.add(node.parent.key.get(0));
                                node.parent.key.remove(0);
                                node.parent.pointer.remove(1);

                                Collections.sort(node.parent.pointer.get(0).key);

                                delete(node.parent.pointer.get(0).parent, val, true);
                            }
                            else if(idx == 1 && node.parent.pointer.get(0).key.size() == 2){
                                //System.out.println("case1-2-1-11");
                                node.key.remove(Integer.valueOf(val));
                                node.key.add(node.parent.key.get(0));
                                node.parent.key.remove(0);
                                node.parent.key.add(node.parent.pointer.get(0).key.get(1));
                                node.parent.pointer.get(0).key.remove(1);
                            }
                        }
                    }
                }
                else{ //1-node면서 leaf는 아님
                    Node biggestSmallNode = find(node, findBiggestSmallNode(node, val, false));
                    // System.out.print("biggestSmallNode : ");
                    // print(biggestSmallNode);
                    // System.out.println();
                    Node smallestBigNode = find(node, findSmallestBigNode(node, val, false));
                    // System.out.print("smallestBigNode : ");
                    // print(smallestBigNode);
                    // System.out.println();

                    //System.out.println("case1-2-2");
                    if(biggestSmallNode.key.size() == 2){
                        //System.out.println("case1-2-2-1");
                        int temp = findBiggestSmallNode(node, val, false);

                        node.key.remove(Integer.valueOf(val));
                        node.key.add(temp);
                        biggestSmallNode.key.remove(Integer.valueOf(temp));

                        Collections.sort(node.key);
                    }

                    else if(smallestBigNode.key.size() == 2){
                        //System.out.println("case1-2-2-2");
                        int temp = findSmallestBigNode(node, val, false);

                        node.key.remove(Integer.valueOf(val));
                        node.key.add(temp);
                        smallestBigNode.key.remove(Integer.valueOf(temp));
                    }

                    else {
                        //System.out.println("case1-2-2-3");
                        int temp = findBiggestSmallNode(node, val, false);

                        node.key.remove(Integer.valueOf(val));
                        node.key.add(temp);
                        biggestSmallNode.key.remove(Integer.valueOf(temp));

                        Collections.sort(node.key);

                        delete(biggestSmallNode, val, true);
                    }
                }
            }

            else{
                // System.out.println("empty node!");
                // System.out.println("case1-3");
                if(node.isRoot == true){
                    //System.out.println("The root is null!!");
                    this.root = node.pointer.get(0);
                    this.root.isRoot = true;
                }
                else{
                    if(node.parent.key.size() == 1){
                        //System.out.println("case1-3-1");
                        if(idx == 0 && node.parent.pointer.get(1).key.size() == 1){
                            //System.out.println("case1-3-1-1");
                            node.key.add(node.parent.key.get(0));
                            node.key.add(node.parent.pointer.get(1).key.get(0));
                            node.parent.key.remove(0);
                            node.pointer.add(1, node.parent.pointer.get(1).pointer.get(0));
                            if(node.pointer.get(1) != null)
                                node.pointer.get(1).parent = node;
                            if(node.parent.pointer.get(1).pointer.size() != 1) //
                                node.pointer.add(2, node.parent.pointer.get(1).pointer.get(1));
                            else
                                node.pointer.add(2, null);
                            if(node.pointer.get(2) != null)
                                node.pointer.get(2).parent = node;
                            node.parent.pointer.remove(1);

                            Collections.sort(node.key);

                            delete(node.parent, val, true);
                        }

                        else if(idx == 0 && node.parent.pointer.get(1).key.size() == 2){
                            //System.out.println("case1-3-1-2");
                            node.key.add(node.parent.key.get(0));
                            node.parent.key.add(node.parent.pointer.get(1).key.get(0));
                            node.parent.key.remove(0);
                            node.parent.pointer.get(1).key.remove(0);
                            node.pointer.add(1, node.parent.pointer.get(1).pointer.get(0));
                            if(node.pointer.get(1) != null)
                                node.pointer.get(1).parent = node;
                            node.parent.pointer.get(1).pointer.remove(0);

                            Collections.sort(node.key);
                            Collections.sort(node.parent.key);
                        }

                        else if(idx == 1 && node.parent.pointer.get(0).key.size() == 1){
                            //System.out.println("case1-3-1-3");
                            node.parent.pointer.get(0).key.add(node.parent.key.get(0));
                            node.parent.key.remove(0);
                            node.parent.pointer.get(0).pointer.add(2, node.pointer.get(0));
                            if(node.parent.pointer.get(0).pointer.get(2) != null)
                                node.parent.pointer.get(0).pointer.get(2).parent = node.parent.pointer.get(0);
                            node.parent.pointer.remove(1);

                            Collections.sort(node.parent.pointer.get(0).key);

                            delete(node.parent, val, true);
                        }

                        else if(idx == 1 && node.parent.pointer.get(0).key.size() == 2){
                            //System.out.println("case1-3-1-4");
                            node.key.add(node.parent.key.get(0));
                            node.parent.key.add(node.parent.pointer.get(0).key.get(1));
                            node.parent.key.remove(0);
                            node.parent.pointer.get(0).key.remove(1);
                            // System.out.println("node.parent.pointer.get(0).key.size() : " + node.parent.pointer.get(0).key.size());
                            // System.out.println("node.parent.pointer.get(0).pointer.size() : " + node.parent.pointer.get(0).pointer.size());
                            // print(node.parent.pointer.get(0));
                            // System.out.println();
                            // print(node.parent.pointer.get(0).pointer.get(0));
                            // System.out.println();
                            // print(node.parent.pointer.get(0).pointer.get(1));
                            // System.out.println();
                            if(node.parent.pointer.get(0).pointer.size() == 2) {
                                //System.out.println("Dang1");//위험한 코드 20220218
                                node.parent.pointer.get(0).pointer.add(null);
                            
                            }
                            //print(node.parent.pointer.get(0).pointer.get(2));
                            //System.out.println();
                            node.pointer.add(0, node.parent.pointer.get(0).pointer.get(2));
                            if(node.pointer.get(0) != null)
                                node.pointer.get(0).parent = node;
                            node.parent.pointer.get(0).pointer.remove(2);

                            Collections.sort(node.key);
                            Collections.sort(node.parent.key);
                        }
                    }
                    else if(node.parent.key.size() == 2){
                        //System.out.println("case1-3-2");
                        if(idx == 0 && node.parent.pointer.get(1).key.size() == 1){
                            //System.out.println("case1-3-2-1");
                            node.key.add(node.parent.key.get(0));
                            node.key.add(node.parent.pointer.get(1).key.get(0));
                            node.parent.key.remove(0);
                            node.parent.pointer.get(1).key.remove(0);
                            node.pointer.add(1, node.parent.pointer.get(1).pointer.get(0));
                            if(node.pointer.get(1) != null)
                                node.pointer.get(1).parent = node;
                            if(node.parent.pointer.get(1).pointer.size() != 1)
                                node.pointer.add(2, node.parent.pointer.get(1).pointer.get(1));
                            else
                                node.pointer.add(2, null);
                            if(node.pointer.get(2) != null)
                                node.pointer.get(2).parent = node;
                            node.parent.pointer.remove(1);

                            Collections.sort(node.key);
                        }

                        else if(idx == 0 && node.parent.pointer.get(1).key.size() == 2){
                            //System.out.println("case1-3-2-2");
                            node.key.add(node.parent.key.get(0));
                            node.parent.key.remove(0);
                            node.parent.key.add(0, node.parent.pointer.get(1).key.get(0));
                            node.parent.pointer.get(1).key.remove(0);
                            node.pointer.add(1, node.parent.pointer.get(1).pointer.get(0));
                            if(node.pointer.get(1) != null)
                                node.pointer.get(1).parent = node;
                            node.parent.pointer.get(1).pointer.remove(0);

                            Collections.sort(node.parent.key);
                        }

                        else if(idx == 1 && node.parent.pointer.get(0).key.size() == 1){
                            //System.out.println("case1-3-2-3");
                            node.parent.pointer.get(0).key.add(node.parent.key.get(0));
                            node.parent.key.remove(0);
                            node.parent.pointer.get(0).pointer.add(2, node.pointer.get(0));
                            if(node.parent.pointer.get(0).pointer.get(2) != null)
                                node.parent.pointer.get(0).pointer.get(2).parent = node.parent.pointer.get(0);
                            node.parent.pointer.remove(1);
                        }

                        else if(idx == 1 && node.parent.pointer.get(0).key.size() == 2){
                            //System.out.println("case1-3-2-4");
                            node.key.add(node.parent.key.get(0));
                            node.parent.key.remove(0);
                            node.parent.key.add(0, node.parent.pointer.get(0).key.get(1));
                            node.parent.pointer.get(0).key.remove(1);
                            // System.out.println("node.parent.pointer.get(0).key.size() : " + node.parent.pointer.get(0).key.size());
                            // System.out.println("node.parent.pointer.get(0).pointer.size() : " + node.parent.pointer.get(0).pointer.size());
                            // print(node.parent.pointer.get(0));
                            // System.out.println();
                            // print(node.parent.pointer.get(0).pointer.get(0));
                            // System.out.println();
                            // print(node.parent.pointer.get(0).pointer.get(1));
                            // System.out.println();
                            // print(node.parent.pointer.get(0).pointer.get(2));
                            // System.out.println();
                            if(node.parent.pointer.get(0).pointer.size() == 2){ //위험한 코드 20220218
                                //System.out.println("Dang2");
                                node.parent.pointer.get(0).pointer.add(null);
                            }
                            node.pointer.add(0, node.parent.pointer.get(0).pointer.get(2));
                            if(node.pointer.get(0) != null)
                                node.pointer.get(0).parent = node;
                            node.parent.pointer.get(0).pointer.remove(2);
                        }

                        else if(idx == 2 && node.parent.pointer.get(1).key.size() == 1){
                            //System.out.println("case1-3-2-5");
                            node.parent.pointer.get(1).key.add(node.parent.key.get(1));
                            node.parent.key.remove(1);
                            node.parent.pointer.get(1).pointer.add(2, node.pointer.get(0));
                            if(node.parent.pointer.get(1).pointer.get(2) != null)
                                node.parent.pointer.get(1).pointer.get(2).parent = node.parent.pointer.get(1);
                            node.parent.pointer.remove(2);
                        }

                        else if(idx == 2 && node.parent.pointer.get(1).key.size() == 2){
                            //System.out.println("case1-3-2-6");
                            node.key.add(node.parent.key.get(1));
                            node.parent.key.remove(1);
                            node.parent.key.add(node.parent.pointer.get(1).key.get(1));
                            node.parent.pointer.get(1).key.remove(1);
                            if(node.parent.pointer.get(1).pointer.size() == 2){ //위험한 코드 20220218
                                //System.out.println("Dang3");
                                node.parent.pointer.get(1).pointer.add(null);
                            }
                            node.pointer.add(0, node.parent.pointer.get(1).pointer.get(2));
                            if(node.pointer.get(0) != null)
                                node.pointer.get(0).parent = node;
                            node.parent.pointer.get(1).pointer.remove(2);
                        }
                    }
                }
            }
        }

        else if(tempCnt == 3){ // 위험한 코드 20220225
            if(node.key.size() == 2){ // 여기도 위험한 코드 20220225
                if (val < node.key.get(0))
                    delete(node.pointer.get(0), val, false);
                else if (val > node.key.get(0) && val < node.key.get(1))
                    delete(node.pointer.get(1), val, false);
                else if (val > node.key.get(1))
                    delete(node.pointer.get(2), val, false);
            }
            else if(node.key.size() == 1){
                if (val < node.key.get(0))
                    delete(node.pointer.get(0), val, false);
                else if (val > node.key.get(0))
                    delete(node.pointer.get(1), val, false);
            }
        }

        else if(tempCnt == 2){ // 위험한 코드 20220225 이렇게 두 개는 원래 node.key.size() == 2, node.key.size() == 1이었음
            if (val < node.key.get(0))
                delete(node.pointer.get(0), val, false);
            else if (val > node.key.get(0))
                delete(node.pointer.get(1), val, false);
        }
    } 

    public int findBiggestSmallNode(Node node, int val, boolean recursive_down){
        //System.out.println("val : " + val);
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
                else{
                    // System.out.println("node.pointer.size() : " + node.pointer.size());
                    // print(node);
                    // System.out.println();
                    // System.out.println(node.pointer.get(0) == null);
                    // System.out.println(node.pointer.get(node.pointer.size()-1) == null);
                    int dangIdx;
                    if(node.key.size() == 2){ //위험한 코드
                        //System.out.println("Dang4-1");
                        dangIdx = 2;
                    }
                    else{
                        //System.out.println("Dang4-2");
                        dangIdx = 1;
                    }
                    return findBiggestSmallNode(node.pointer.get(dangIdx), node.pointer.get(dangIdx).key.get(node.pointer.get(dangIdx).key.size()-1), true);
                }
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
                    // System.out.println("node.pointer.size() : " + node.pointer.size());
                    // print(node);
                    // System.out.println();
                    // print(node.pointer.get(0));
                    // System.out.println();
                    // print(node.pointer.get(1));
                    // System.out.println();
                    // print(node.pointer.get(2));
                    // System.out.println();
                    // System.out.println(node.pointer.get(0) == null);
                    // System.out.println(node.pointer.get(node.pointer.size()-1) == null);
                    //System.out.println("type 2-2-2");
                    int dangIdx;
                    if(node.key.size() == 2) {
                        //System.out.println("Dang5-1");//위험한 코드
                        dangIdx = 2;
                    }
                    else{
                        //System.out.println("Dang5-2");
                        dangIdx = 1;
                    }
                    return findBiggestSmallNode(node.pointer.get(dangIdx), node.pointer.get(dangIdx).key.get(node.pointer.get(dangIdx).key.size()-1), true);
                }
            }
        }
    }

    public int findSmallestBigNode(Node node, int val, boolean recursive_down){
        //System.out.println(val);
        if(node == null){
            System.out.println("Something went wrong");
            return -2;
        }
        else if(val == node.key.get(0)){
            if(recursive_down == false) {
                if(node.pointer.get(1) == null)
                    return -1;
                
                else if(node.pointer.get(1).pointer.size() == 2 && node.pointer.get(1).pointer.get(1) == null)
                    return node.pointer.get(1).key.get(0); //위험한 코드 20220225

                else if(node.pointer.get(1).pointer.size() == 1 && node.pointer.get(1).pointer.get(0) == null)
                    return node.pointer.get(1).key.get(0); //위험한 코드 20220225

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

                else if(node.pointer.get(2).pointer.get(0) == null){
                    // print(node.pointer.get(2));
                    // System.out.println();
                    return node.pointer.get(2).key.get(0);
                }

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

    public Node(Node node){
        Collections.copy(this.key, node.key);
        Collections.copy(this.pointer, node.pointer);
        this.parent = new Node(node.parent);
        this.isRoot = node.isRoot;
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

class RandomizedNumber {
    int[] number;

    public RandomizedNumber(int n){
        number = new int[n];

        for(int i=0;i<n;i++)
            number[i] = i;
        
        for(int i=0;i<n;i++){
            Random random = new Random();
            int a =  random.nextInt(n);
            int temp;

            temp = number[i];
            number[i] = number[a];
            number[a] = temp;
        }
    }

    public void print(){
        for(int i=0;i<number.length;i++){
            System.out.print(number[i] + " ");
        }
        System.out.println();
    }
}

public class TwoThreeTree {
    public static void main(String[] args) throws Exception {
        // int size = 1000000;
        // int del = 500000;
        // RandomizedNumber rn = new RandomizedNumber(size);

        // Tree tree = new Tree();
        // Node root = new Node();
        // root.isRoot = true;
        // tree.root = root;

        // for(int i=0;i<size;i++)
        //     tree.insert(tree.root, rn.number[i], false);
        // System.out.println("_______________________");
        // System.out.println("rn : ");
        // rn.print();
        // tree.myorder(tree.root);
        // tree.print(tree.root);
        // System.out.println();
    

        // for(int i=0;i<del;i++){
        //     // System.out.print("tree : ");
        //     // tree.myorder(tree.root);
        //     // System.out.println();
        //     tree.delete(tree.root, rn.number[i], false);
        // }
        // System.out.println("_______________________");
        // System.out.println("rn : ");
        // rn.print();
        // System.out.println("---------------------------------------");
        // tree.myorder(tree.root);
        // tree.print(tree.root);
        // System.out.println();

        // Tree tree = new Tree();
        // Node root = new Node();
        // root.isRoot = true;
        // tree.root = root;

        // tree.insert(root, 2045201421, false);
        // tree.insert(root, 1220911591, false);
        // tree.insert(root, 153709483, false);
        // tree.insert(root, 1323517163, false);
        // tree.insert(root, 1451071572, false);
        // tree.insert(root, 1494828198, false);
        // tree.insert(root, 868738912, false);
        // tree.insert(root, 1729539890, false);
        // tree.insert(root, 1853336516, false);
        // tree.insert(root, 1410016230, false);

        // tree.delete(tree.root, 1220911591, false);
        
        // System.out.println("--------------------------------");
        // tree.myorder(root);
        // tree.print(root);
        // System.out.println();

        Tree tree = new Tree();
        Node root = new Node();
        root.isRoot = true;
        tree.root = root;
        String name = "";
    
        while(true){
            int choice;
            Scanner sc = new Scanner(System.in);
            System.out.print("1.insertion 2.deletion. 3.quit : ");
            choice = sc.nextInt();
            int count = 1000000;

            if(choice == 1){
                
                Scanner sc2 = new Scanner(System.in);
                name = sc2.nextLine();

                File csv = new File(name);
                BufferedReader br = null;
                br = new BufferedReader(new FileReader(csv));
                for(int i=0;i<count;i++){
                    String line = "";
                    line = br.readLine();
                    String[] lineArr = line.split("\t");
                    //System.out.println(lineArr[1]);
                    tree.insert(root, Integer.parseInt(lineArr[1]), false);
                }
                br.close();

                br = new BufferedReader(new FileReader(csv));
                File csv2 = new File("output.csv");
                BufferedWriter bw = null; // 출력 스트림 생성
                bw = new BufferedWriter(new FileWriter(csv2));

                for(int i=0;i<count;i++){
                    String line = "";
                    line = br.readLine();
                    String[] lineArr = line.split("\t");
                    //System.out.println(lineArr[1]);
                    int target = Integer.parseInt(lineArr[1]);

                    if(tree.find(root, target) != null){
                        bw.write((i+1) + "\t" + Integer.toString(target));
                        bw.newLine();
                    }
                }
                bw.close();

                BufferedReader br3 = new BufferedReader(new FileReader(csv));
                BufferedReader br4 = new BufferedReader(new FileReader(csv2));
                boolean check = true;
                for(int i=0;i<count;i++){
                    String line1 = "";
                    line1 = br3.readLine();
                    String[] lineArr1 = line1.split("\t");
                    int comp1 = Integer.parseInt(lineArr1[1]);

                    String line2 = "";
                    line2 = br4.readLine();
                    String[] lineArr2 = line2.split("\t");
                    int comp2 = Integer.parseInt(lineArr2[1]);

                    if(comp1 != comp2)
                        check = false;
                }
                System.out.println(check);
            }
            else if(choice == 2){
                String name2;
                Scanner sc2 = new Scanner(System.in);
                name2 = sc2.nextLine();

                File csv = new File(name2);
                BufferedReader br = null;
                br = new BufferedReader(new FileReader(csv));
                for(int i=0;i<count/2;i++){
                    String line = "";
                    line = br.readLine();
                    String[] lineArr = line.split("\t");
                    //System.out.println(lineArr[1]);
                    tree.delete(tree.root, Integer.parseInt(lineArr[1]), false);
                }
                br.close();

                br = new BufferedReader(new FileReader(new File(name)));
                File csv2 = new File("delete_output.csv");
                BufferedWriter bw = null; // 출력 스트림 생성
                bw = new BufferedWriter(new FileWriter(csv2));

                for(int i=0;i<count;i++){
                    String line = "";
                    line = br.readLine();
                    String[] lineArr = line.split("\t");
                    int target = Integer.parseInt(lineArr[1]);

                    if(tree.find(tree.root, target).key.contains(Integer.valueOf(target)) == true){
                        // System.out.print("Found " + target + " at ");
                        // tree.print(tree.find(tree.root, target));
                        // System.out.println();
                        bw.write((i+1) + "\t" + Integer.toString(target));
                        bw.newLine();
                    }
                    else{
                        // System.out.print("Not Found " + target + " at ");
                        // tree.print(tree.find(tree.root, target));
                        // System.out.println();
                        bw.write((i+1) + "\t" +"N/A");
                        bw.newLine();
                    }
                }
                bw.close();

                File csv3 = new File("delete_compare.csv"); 

                BufferedReader br3 = new BufferedReader(new FileReader(csv3));
                BufferedReader br4 = new BufferedReader(new FileReader(csv2));
                boolean check = true;
                for(int i=0;i<count;i++){
                    //System.out.println(i+1);
                    String line1 = "";
                    line1 = br3.readLine();
                    String[] lineArr1 = line1.split("\t");
                    int comp1=0;
                    //System.out.println(lineArr1[1].contains("N/A"));
                    if(lineArr1[1].contains("N/A") == false)
                        comp1 = Integer.parseInt(lineArr1[1]);

                    String line2 = "";
                    line2 = br4.readLine();
                    String[] lineArr2 = line2.split("\t");
                    int comp2=0;
                    //System.out.println(lineArr2[1].contains("N/A"));
                    if(lineArr2[1].contains("N/A") == false)
                        comp2 = Integer.parseInt(lineArr2[1]);
                    
                    if(!lineArr1[1].contains("N/A") && !lineArr2[1].contains("N/A") && comp1 != comp2)
                        check = false;
                }
                System.out.println(check);
            }
            else{
                break;
            }
        }
    }
}