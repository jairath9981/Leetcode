package Interview;

import java.util.HashMap;
import java.util.Map;


/*
Sun
					1(0)
				2(1)	 3(-1)
			4(2)  5(0)    6(0)  7(-2)

1,2 4,3,7

ld +1
rd -1

0  1
1  2
2  4
-1 3
-2 7
 */

class Node_VerticalOrderOfBinaryTree{
    Node_VerticalOrderOfBinaryTree right;
    Node_VerticalOrderOfBinaryTree left;
    int val;

    public Node_VerticalOrderOfBinaryTree(int val){
        this.val = val;
    }
}

public class VerticalOrderOfBinaryTree {
    public static void main(String[] args) {
        VerticalOrderOfBinaryTree solution1 = new VerticalOrderOfBinaryTree();
        Node_VerticalOrderOfBinaryTree node1 = new Node_VerticalOrderOfBinaryTree(1);
        Node_VerticalOrderOfBinaryTree node2 = new Node_VerticalOrderOfBinaryTree(2);
        Node_VerticalOrderOfBinaryTree node3 = new Node_VerticalOrderOfBinaryTree(3);
        Node_VerticalOrderOfBinaryTree node4 = new Node_VerticalOrderOfBinaryTree(4);
        Node_VerticalOrderOfBinaryTree node5 = new Node_VerticalOrderOfBinaryTree(5);
        Node_VerticalOrderOfBinaryTree node6 = new Node_VerticalOrderOfBinaryTree(6);
        Node_VerticalOrderOfBinaryTree node7 = new Node_VerticalOrderOfBinaryTree(7);
        node1.left = node2; node1.right = node3;
        node2.left = node4; node2.right = node5;
        node3.left = node6; node3.right = node7;

        Map<Integer, Integer> ans = solution1.sunFallOnNodes(node1);
        System.out.println(ans);
    }

    private Map<Integer, Integer> sunFallOnNodes(Node_VerticalOrderOfBinaryTree root) {
        Map<Integer, Integer> map = new HashMap<>();
        sunFallOnNodesHelper(root, 0, map);
        return map;
    }
    private void sunFallOnNodesHelper(Node_VerticalOrderOfBinaryTree r, int label, Map<Integer, Integer> map) {
        if(r!=null){
            if(!map.containsKey(label)) {
                map.put(label, r.val);
            }
                sunFallOnNodesHelper(r.left, label + 1, map);
                sunFallOnNodesHelper(r.right, label - 1, map);
        }
    }
}
