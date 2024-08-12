package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/redundant-connection-ii/
Input 1: multiple Parents
1 2
1 3
2 3
0 0
1--->3
|   ^
|  /
V /
2/
Input meaning: edges = [[1,2],[1,3],[2,3]]
Output: [2,3]

Input 2:
1 2
2 3
3 4
4 1
1 5
0 0
Output: [4,1]

Input 3:   multiple Parents
2 1
3 1
4 2
1 4
0 0

3->1->4->2--
   ^       |
   |_______|
Output: [2,1]

Input 4:
1 2
2 3
3 4
4 1
1 5
0 0
Output: [4,1]

Input 5: multiple Parents
4 2
1 5
5 2
5 3
2 4
0 0
1->5->3
  |
  V
  2->4--
  ^    |
  |____|
Output: [4,2]

Input 6:
5 2
5 1
3 1
3 4
3 5
0 0
Output: [3,1]

Input 7:
1 2
2 1
2 3
3 4
0 0
Output: [2,1]
 */


class ParentChildInfo_Redundant_Connection_II {
    List<Integer> child;
    Set<Integer> childSet;
    List<Integer> parent;
    Set<Integer> parentSet;

    ParentChildInfo_Redundant_Connection_II() {
        child = new ArrayList<>();
        childSet = new HashSet<>();
        parent = new ArrayList<>();
        parentSet = new HashSet<>();
    }

    @Override
    public String toString() {
        return "ParentChildInfo_Redundant_Connection_II{" +
                "child=" + child +
                ", parent=" + parent +
                '}';
    }
}


public class Redundant_Connection_II {

    public static void main(String[] args) {
        Redundant_Connection_II rc2 = new Redundant_Connection_II();
        Scanner input = new Scanner(System.in);
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if (choice == 1) {
                List<List<Integer>> listOfEdges = new ArrayList<>();
                int x, y;
                System.out.println("Enter Source Vertex Id And Target Vertex Id. For Stop Insertion Enter 0 0");
                x = input.nextInt();
                y = input.nextInt();
                while (x > 0 && y > 0) {
                    listOfEdges.add(List.of(x, y));
                    x = input.nextInt();
                    y = input.nextInt();
                }
                int[][] edges = listOfEdges.stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);

                System.out.println();
                int[] edgeNeedToRemove = rc2.findRedundantDirectedConnection(edges);
                System.out.println("Edge Need To Remove: " + edgeNeedToRemove[0] + "->" + edgeNeedToRemove[1]);
            } else {
                int[][] edges = {{1, 2}, {1, 3}, {2, 3}};
                System.out.println();
                int[] edgeNeedToRemove = rc2.findRedundantDirectedConnection(edges);
                System.out.println("Edge Need To Remove: " + edgeNeedToRemove[0] + "->" + edgeNeedToRemove[1]);
            }
            t--;
        }
    }

    List<Integer> multipleParentNode_Node;
    List<Integer> multipleParentNode_Parent;
    public int[] findRedundantDirectedConnection(int[][] edges) {

        multipleParentNode_Node = new ArrayList<>();
        multipleParentNode_Parent = new ArrayList<>();

        Map<Integer, ParentChildInfo_Redundant_Connection_II> nodeToParentChild =
                buildNodeCompleteInfo(edges);

        System.out.println("nodeToParentChild: " + nodeToParentChild.toString());
        System.out.println("multipleParentNode: " + multipleParentNode_Parent + " " + multipleParentNode_Node);

        int[] edgeToRemove = new int[2];
        edgeToRemove[0] = -1;
        edgeToRemove[1] = -1;

        if (!multipleParentNode_Node.isEmpty()) {
            int index = pathExistForMultipleParent(nodeToParentChild);
            edgeToRemove[0] = multipleParentNode_Parent.get(index);
            edgeToRemove[1] = multipleParentNode_Node.get(index);
        } else {
            int index = nodeToPickWhoseChildExist(nodeToParentChild, edges);
            edgeToRemove[0] = edges[index][0];
            edgeToRemove[1] = edges[index][1];
        }
        return edgeToRemove;
    }

    private int nodeToPickWhoseChildExist(Map<Integer, ParentChildInfo_Redundant_Connection_II> map,
                                          int[][] edges) {
        // Input 4 Input 7
        for (int i = edges.length - 1; i >= 0; i--) {
            int src = edges[i][0];
            int target = edges[i][1];
            if (isPathExist(map, target, src))
                return i;
        }
        return 0;
    }

    private int pathExistForMultipleParent(Map<Integer, ParentChildInfo_Redundant_Connection_II> map) {
        //Input 3, Inout 5
        for (int i = multipleParentNode_Parent.size() - 1; i >= 0; i--) {
            int src = multipleParentNode_Parent.get(i);
            int target = multipleParentNode_Node.get(i);
            if (isPathExist(map, target, src)) {
                return i;
            }
        }
        return 0;
    }

    private boolean isPathExist(Map<Integer, ParentChildInfo_Redundant_Connection_II> map,
                                int target, int src) {
        // Inout 5
        Set<Integer> visited = new HashSet<>();

        int[] edgeGoingToRemove = new int[2];
        edgeGoingToRemove[0] = src;
        edgeGoingToRemove[1] = target;

        int[] result = new int[1];
        result[0] = 0;

        isPathExistHelper(map, visited, target, src, edgeGoingToRemove, result);
        if(result[0] == 0)
            return false;
        else
            return true;
    }

    private void isPathExistHelper(Map<Integer, ParentChildInfo_Redundant_Connection_II> map,
                       Set<Integer> visited, int target, int src, int[] edgeGoingToRemove, int[] result) {
        if(result[0] == 1)
            return;
        if (!visited.contains(src)) {
            visited.add(src);
            Set<Integer> childNodes = map.get(src).childSet;
            Set<Integer> parentNodes = map.get(src).parentSet;
            if (childNodes.contains(target) && edgeGoingToRemove[0]!=src ||
                    (parentNodes.contains(target))){
                System.out.println("Path Will Exist if we Remove: "+edgeGoingToRemove[0]+"->"+edgeGoingToRemove[1]);
                result[0] = 1;
            }
            else {
                for(int childNode: childNodes){
                    if(edgeGoingToRemove[0] == src && edgeGoingToRemove[1] == childNode)
                        continue;
                    if(result[0] == 1)
                        return;
                    isPathExistHelper(map, visited, target, childNode, edgeGoingToRemove, result);
                }
                for(int parentNode: parentNodes){
                    if(edgeGoingToRemove[0] == parentNode && edgeGoingToRemove[1] == src)
                        continue;
                    if(result[0] == 1)
                        return;
                    isPathExistHelper(map, visited, target, parentNode, edgeGoingToRemove, result);
                }
            }
        }
    }

    private Map<Integer, ParentChildInfo_Redundant_Connection_II> buildNodeCompleteInfo(
            int[][] edges) {

        Map<Integer, ParentChildInfo_Redundant_Connection_II> nodeToParentChild = new HashMap<>();

        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int target = edges[i][1];
            addInMap(nodeToParentChild, src, target);
        }

        return nodeToParentChild;
    }

    private void addInMap(Map<Integer, ParentChildInfo_Redundant_Connection_II> map,
                          int node, int child) {
        addInMapChild(map, node, child);
        addInMapParent(map, child, node);
    }

    private void addInMapChild(Map<Integer, ParentChildInfo_Redundant_Connection_II> map,
                               int node, int child) {
        if (map.containsKey(node)) {
            ParentChildInfo_Redundant_Connection_II parentChildInfo =
                    map.get(node);
            parentChildInfo.child.add(child);
            parentChildInfo.childSet.add(child);
        } else {
            ParentChildInfo_Redundant_Connection_II parentChildInfo =
                    new ParentChildInfo_Redundant_Connection_II();
            parentChildInfo.child.add(child);
            parentChildInfo.childSet.add(child);
            map.put(node, parentChildInfo);
        }
    }

    private void addInMapParent(Map<Integer, ParentChildInfo_Redundant_Connection_II> map,
                                int node, int parent) {
        if (map.containsKey(node)) {
            ParentChildInfo_Redundant_Connection_II parentChildInfo =
                    map.get(node);
            parentChildInfo.parent.add(parent);
            parentChildInfo.parentSet.add(parent);
            if (parentChildInfo.parent.size() >= 2) {
                if (parentChildInfo.parent.size() == 2) {
                    multipleParentNode_Parent.add(parentChildInfo.parent.get(0));
                    multipleParentNode_Node.add(node);
                }
                multipleParentNode_Parent.add(parent);
                multipleParentNode_Node.add(node);
            }
        } else {
            ParentChildInfo_Redundant_Connection_II parentChildInfo =
                    new ParentChildInfo_Redundant_Connection_II();
            parentChildInfo.parent.add(parent);
            parentChildInfo.parentSet.add(parent);
            map.put(node, parentChildInfo);
        }
    }
}
