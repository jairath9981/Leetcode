package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/range-module/
See Input From RangeModule_Input.txt
*/

class Pair_RangeModule2{
    int left;
    int right;

    public Pair_RangeModule2(int left, int right) {
        this.left = left;
        this.right = right;
    }
}

class NodeOfBST_RangeModule2{
    Pair_RangeModule2 val;
    int height;
    NodeOfBST_RangeModule2 left;
    NodeOfBST_RangeModule2 right;

    public NodeOfBST_RangeModule2(int left, int right){
        this.val = new Pair_RangeModule2(left, right);
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

class AVLTree_RangeModule2{
    NodeOfBST_RangeModule2 root;

    public AVLTree_RangeModule2(){
        this.root = null;
    }

    private int height(NodeOfBST_RangeModule2 r1) {
        if(r1 == null)
            return 0;
        else return r1.height;
    }

    private int balanceFactor(NodeOfBST_RangeModule2 r1) {
        if(r1 == null)
            return 0;
        int leftHeight = height(r1.left);
        int rightHeight = height(r1.right);

        return leftHeight - rightHeight;
    }

    private NodeOfBST_RangeModule2 minNode(NodeOfBST_RangeModule2 r1) {
        NodeOfBST_RangeModule2 minNode = r1;
        while(r1!=null){
            minNode = r1;
            r1 = r1.left;
        }
        return minNode;
    }

    private NodeOfBST_RangeModule2 rightRotation(NodeOfBST_RangeModule2 currNode) {

        NodeOfBST_RangeModule2 newRoot = currNode.left;
        NodeOfBST_RangeModule2 prevRightOfNewRoot = newRoot.right;

        // update pointers
        newRoot.right = currNode;
        currNode.left = prevRightOfNewRoot;

        // update heights
        currNode.height = 1 + Math.max(height(currNode.left), height(currNode.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
        return newRoot;
    }

    private NodeOfBST_RangeModule2 leftRotation(NodeOfBST_RangeModule2 currNode) {
        NodeOfBST_RangeModule2 newRoot = currNode.right;
        NodeOfBST_RangeModule2 prevLeftOfNewRoot = newRoot.left;

        // update pointers
        newRoot.left = currNode;
        currNode.right = prevLeftOfNewRoot;

        // update heights
        currNode.height = 1 + Math.max(height(currNode.left), height(currNode.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
        return newRoot;
    }

    public NodeOfBST_RangeModule2 addInAVLTree(int left, int right,
              NodeOfBST_RangeModule2 r1){

        if(r1 == null){
            return new NodeOfBST_RangeModule2(left, right);
        }
        if(r1.val.right<left){
            r1.right = addInAVLTree(left, right, r1.right);
        }
        else if(r1.val.left>right){
            r1.left = addInAVLTree(left, right, r1.left);
        }
        r1.height = 1 + Math.max(height(r1.left), height(r1.right));

        return performInsertionRotation(left, right, r1);
    }

    private NodeOfBST_RangeModule2 performInsertionRotation(int left, int right,
              NodeOfBST_RangeModule2 r1) {
        // 1 [ LL-imbalance/insertion ] -> right_Rotation
        if(balanceFactor(r1) > 1 && r1.left.val.left>right){
            return rightRotation(r1);
        }
        // 2 [ RR-imbalance/insertion ] -> left_Rotation
        if(balanceFactor(r1) < -1 && r1.right.val.right<left){
            return leftRotation(r1);
        }
        // 3 [ LR-imbalance/insertion ] -> left + right Rotation
        if(balanceFactor(r1) > 1 && r1.left.val.right<left){
            r1.left = leftRotation(r1.left);
            return rightRotation(r1);
        }
        // 4 [ RL-imbalance/insertion ] -> right + left Rotation
        if(balanceFactor(r1) < -1 && r1.right.val.left>right){
            r1.right = rightRotation(r1.right);
            return leftRotation(r1);
        }
        // return the unchanged node if it is already balanced
        return r1;
    }

    public NodeOfBST_RangeModule2 deleteFromAVLTree(int left, int right,
              NodeOfBST_RangeModule2 r1){

        if(r1 == null){
            return null;
        }
        if(r1.val.right<left){
            r1.right = deleteFromAVLTree(left, right, r1.right);
        }
        else if(r1.val.left>right){
            r1.left = deleteFromAVLTree(left, right, r1.left);
        }else if(r1.val.left == left && r1.val.right == right){

            // leaf node or one child deletion
            if(r1.left == null || r1.right == null){
                NodeOfBST_RangeModule2 temp = null;
                if(r1.right == null)
                    temp = r1.left;
                if(r1.left == null)
                    temp = r1.right;
                r1 = temp;
            }else{ // both child
                // the smallest successor
                NodeOfBST_RangeModule2 temp = minNode(r1.right);
                //copy data of smallest successor
                r1.val.left = temp.val.left;
                r1.val.right = temp.val.right;
                // delete smallest successor
                r1.right = deleteFromAVLTree(temp.val.left, temp.val.right, r1.right);
            }
        }
        if(r1 == null) // no child deletion
            return r1;

        r1.height = 1 + Math.max(height(r1.left), height(r1.right));
        return performDeletionRotation(r1);
    }

    private NodeOfBST_RangeModule2 performDeletionRotation(NodeOfBST_RangeModule2 r1) {
        // 1 [ LL-imbalance ] -> right_Rotation
        if(balanceFactor(r1) > 1 && balanceFactor(r1.left) >= 0){
            return rightRotation(r1);
        }
        // 2 [ RR-imbalance ] -> left_Rotation
        if(balanceFactor(r1) < -1 && balanceFactor(r1.right) <= 0){
            return leftRotation(r1);
        }
        // 3 [ LR-imbalance ] -> left + right Rotation
        if(balanceFactor(r1) > 1 && balanceFactor(r1.left) < 0){
            r1.left = leftRotation(r1.left);
            return rightRotation(r1);
        }
        // 4 [ RL-imbalance ] -> right + left Rotation
        if(balanceFactor(r1) < -1 && balanceFactor(r1.right) > 0){
            r1.right = rightRotation(r1.right);
            return leftRotation(r1);
        }
        // return the unchanged node if it is already balanced
        return r1;
    }

    public void queryAdd(NodeOfBST_RangeModule2 r1, Pair_RangeModule2 needToAdd,
           List<Pair_RangeModule2> listNeedToRemove){

        if(r1 == null || (needToAdd.left < 0 && needToAdd.right < 0))
            return;
        // merge from right of prev
        if(r1.val.right+1 == needToAdd.left){
            listNeedToRemove.add(r1.val);
            needToAdd.left = r1.val.left;
            queryAdd(r1.right, needToAdd, listNeedToRemove);
        }
        // merge from left of prev
        else if(r1.val.left == needToAdd.right+1){
            listNeedToRemove.add(r1.val);
            needToAdd.right = r1.val.right;
            queryAdd(r1.left, needToAdd, listNeedToRemove);
        }
        // no overlap query is on right side of number line
        else if(r1.val.right<needToAdd.left) {
             queryAdd(r1.right, needToAdd, listNeedToRemove);
        }
        // no overlap query is on left side of number line
        else if (r1.val.left>needToAdd.right){
            queryAdd(r1.left, needToAdd, listNeedToRemove);
        }
        // complete overlap query is inside this root
        else if(r1.val.left<=needToAdd.left && r1.val.right>=needToAdd.right){
            needToAdd.left = -1;
            needToAdd.right = -1;
            return;
        }
        // partial covered 1 -> completely by pass
        else if(r1.val.left>=needToAdd.left && r1.val.right<=needToAdd.right){
            listNeedToRemove.add(r1.val);
            queryAdd(r1.left, needToAdd, listNeedToRemove);
            queryAdd(r1.right, needToAdd, listNeedToRemove);
        }
        // partial covered 2 -> left side by pass
        else if(r1.val.left>=needToAdd.left && comingInside(r1, needToAdd.right)){
            listNeedToRemove.add(r1.val);
            needToAdd.right = Math.max(needToAdd.right, r1.val.right);
            queryAdd(r1.left, needToAdd, listNeedToRemove);
        }
        // partial covered 3 -> ride side by pass
        else if(comingInside(r1, needToAdd.left) && r1.val.right<=needToAdd.right){
            listNeedToRemove.add(r1.val);
            needToAdd.left = Math.min(needToAdd.left, r1.val.left);
            queryAdd(r1.right, needToAdd, listNeedToRemove);
        }
    }

    public void queryRemove(NodeOfBST_RangeModule2 r1, Pair_RangeModule2 needToRemove,
        List<Pair_RangeModule2> listNeedToAdd,
        List<Pair_RangeModule2> listNeedToRemove, int[] flag){

        if(r1 == null || (flag[0] < 0))
            return;
        // no overlap query is on right side of number line
        else if(r1.val.right<needToRemove.left) {
            queryRemove(r1.right, needToRemove, listNeedToAdd, listNeedToRemove,
                    flag);
        }
        // no overlap query is on left side of number line
        else if (r1.val.left>needToRemove.right){
            queryRemove(r1.left, needToRemove, listNeedToAdd, listNeedToRemove,
                    flag);
        }
        // complete overlap query is inside this root
        else if(r1.val.left<=needToRemove.left && r1.val.right>=needToRemove.right){
            listNeedToRemove.add(r1.val);

            int leftToAdd1 = r1.val.left == needToRemove.left?-1:r1.val.left;
            int rightToAdd1 = leftToAdd1 == -1?leftToAdd1:needToRemove.left-1;
            int leftToAdd2 = needToRemove.right+1;
            leftToAdd2 = r1.val.right == needToRemove.right?-1:leftToAdd2;
            int rightToAdd2 =leftToAdd2 == -1?leftToAdd2:r1.val.right;
            if(leftToAdd1!=-1)
                listNeedToAdd.add(new Pair_RangeModule2(leftToAdd1, rightToAdd1));
            if(leftToAdd2!=-1)
            listNeedToAdd.add(new Pair_RangeModule2(leftToAdd2, rightToAdd2));

            flag[0] = -1;
            return;
        }
        // partial covered 1 -> completely by pass
        else if(r1.val.left>=needToRemove.left &&
                r1.val.right<=needToRemove.right){
            listNeedToRemove.add(r1.val);

            queryRemove(r1.left, needToRemove, listNeedToAdd, listNeedToRemove,
                    flag);
            queryRemove(r1.right, needToRemove, listNeedToAdd, listNeedToRemove,
                    flag);
        }
        // partial covered 2 -> left side by pass
        else if(r1.val.left>=needToRemove.left &&
                comingInside(r1, needToRemove.right)){
            listNeedToRemove.add(r1.val);

            int leftToAdd1 = needToRemove.right+1;
            leftToAdd1 = needToRemove.right == r1.val.right?-1:leftToAdd1;
            int rightToAdd1 = leftToAdd1 == -1?leftToAdd1:r1.val.right;

            if(leftToAdd1!=-1)
                listNeedToAdd.add(new Pair_RangeModule2(leftToAdd1, rightToAdd1));

            queryRemove(r1.left, needToRemove, listNeedToAdd, listNeedToRemove,
                    flag);
        }
        // partial covered 3 -> ride side by pass
        else if(comingInside(r1, needToRemove.left) &&
                r1.val.right<=needToRemove.right){
            listNeedToRemove.add(r1.val);

            int leftToAdd1 = needToRemove.left == r1.val.left?-1:r1.val.left;
            int rightToAdd1 = leftToAdd1 == -1?leftToAdd1:needToRemove.left-1;

            if(leftToAdd1!=-1)
                listNeedToAdd.add(new Pair_RangeModule2(leftToAdd1, rightToAdd1));

            queryRemove(r1.right, needToRemove, listNeedToAdd, listNeedToRemove,
                    flag);
        }
    }

    private boolean comingInside(NodeOfBST_RangeModule2 r1, int val){
        if(r1.val.left<=val && r1.val.right>=val){
            return true;
        }
        return false;
    }

    public void queryFind(NodeOfBST_RangeModule2 r1,
          Pair_RangeModule2 needToFind, int []flag){

        /*
        flag[0] == 1 found
        flag[0] == -1 can't find enter in partial overlap zone
        flag[0] == 0 can't say anything rightNow
         */
        if(r1 == null || flag[0] == 1 || flag[0] == -1)
            return;
        // no overlap query is on right side of number line
        else if(r1.val.right<needToFind.left) {
            queryFind(r1.right, needToFind, flag);
        }
        // no overlap query is on left side of number line
        else if (r1.val.left>needToFind.right){
            queryFind(r1.left, needToFind, flag);
        }
        // complete overlap query is inside this root
        else if(r1.val.left<=needToFind.left && r1.val.right>=needToFind.right){
            flag[0] = 1;
            return;
        }else{ // partial overlap
            flag[0] = -1;
            return;
        }
    }

    public void printInorder(NodeOfBST_RangeModule2 r1){
        if(r1!=null){
            printInorder(r1.left);
            System.out.println("["+r1.val.left+"-"+r1.val.right+"]");
            printInorder(r1.right);
        }
    }
}

public class RangeModule2 {

    AVLTree_RangeModule2 avlTree;
    RangeModule2(){
        avlTree = new AVLTree_RangeModule2();
    }

    public void addRange(int left, int right) {
        right--;
        List<Pair_RangeModule2> listNeedToRemove = new ArrayList<>();
        Pair_RangeModule2 needToAdd = new Pair_RangeModule2(left, right);

        avlTree.queryAdd(avlTree.root, needToAdd, listNeedToRemove);
        for(int i = 0; i<listNeedToRemove.size(); i++){
            int leftToDelete = listNeedToRemove.get(i).left;
            int rightToDelete = listNeedToRemove.get(i).right;
            avlTree.root = avlTree.deleteFromAVLTree(leftToDelete,
                    rightToDelete, avlTree.root);
        }
        if(!(needToAdd.left<0 && needToAdd.right<0)){
            int leftToAdd = needToAdd.left;
            int rightToAdd = needToAdd.right;
            avlTree.root = avlTree.addInAVLTree(leftToAdd, rightToAdd,
                    avlTree.root);
        }
//        System.out.println("Print AVL Tree For Add()");
//        avlTree.printInorder(avlTree.root);
    }

    public void removeRange(int left, int right) {
        right--;

        List<Pair_RangeModule2> listNeedToRemove = new ArrayList<>();
        List<Pair_RangeModule2> listNeedToAdd = new ArrayList<>();
        int[] flag = new int[1];
        flag[0] = 1;
        Pair_RangeModule2 needToRemove = new Pair_RangeModule2(left, right);

        avlTree.queryRemove(avlTree.root, needToRemove, listNeedToAdd,
                listNeedToRemove, flag);

        for(int i = 0; i<listNeedToRemove.size(); i++){
            int leftToDelete = listNeedToRemove.get(i).left;
            int rightToDelete = listNeedToRemove.get(i).right;
            avlTree.root = avlTree.deleteFromAVLTree(leftToDelete,
                    rightToDelete, avlTree.root);
        }

        for(int i = 0; i<listNeedToAdd.size(); i++){
            int leftToAdd = listNeedToAdd.get(i).left;
            int rightToAdd = listNeedToAdd.get(i).right;
            avlTree.root = avlTree.addInAVLTree(leftToAdd,
                    rightToAdd, avlTree.root);
        }

//        System.out.println("Print AVL Tree For Remove()");
//        avlTree.printInorder(avlTree.root);
    }

    public boolean queryRange(int left, int right) {
        right--;

        Pair_RangeModule2 needToFind = new Pair_RangeModule2(left, right);
        int[] flag = new int[1];
        flag[0] = 0;
        /*
        flag[0] == 1 found
        flag[0] == -1 can't find enter in partial overlap zone
        flag[0] == 0 can't say anything rightNow
         */
        avlTree.queryFind(avlTree.root, needToFind, flag);
        if(flag[0] == 1)
            return true;
        return false;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while (t > 0) {
            RangeModule2 rangeModule2 = new RangeModule2();

            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if (choice == 1) {

                input.nextLine(); // garbage
                List<String> operationChoice = new ArrayList<>();
                String operation;
                System.out.println("Enter Your OperationChoice Array Valid Strings[addRange, removeRange, " +
                        "queryRange] To Stop Insertion In OperationChoice Array press xxx");
                operation = input.nextLine();
                while(!operation.equalsIgnoreCase("xxx")){
                    operationChoice.add(operation);
                    operation = input.nextLine();
                }

                List<List<Integer>> operationRange = new ArrayList<>(operationChoice.size());
                int left, right;
                for (int i = 0; i<operationChoice.size(); i++){

                    left = input.nextInt();
                    right = input.nextInt();

                    List<Integer> currOperationRange = new ArrayList<>();
                    currOperationRange.add(left);
                    currOperationRange.add(right);

                    operationRange.add(currOperationRange);
                }

                for (int i = 0; i<operationChoice.size(); i++){
                    int currOperationLeft = operationRange.get(i).get(0);
                    int currOperationRight = operationRange.get(i).get(1);
                    int actualRight = currOperationRight - 1;
                    if(operationChoice.get(i).equalsIgnoreCase("addRange")) {
                        //System.out.println("addRange(): [" + currOperationLeft + "-" + actualRight + "] ");
                        rangeModule2.addRange(currOperationLeft, currOperationRight);
                    }
                    else if(operationChoice.get(i).equalsIgnoreCase("removeRange")) {
                        //System.out.println("removeRange: ["+ currOperationLeft+"-"+actualRight+"] ");
                        rangeModule2.removeRange(currOperationLeft, currOperationRight);
                    }
                    else if(operationChoice.get(i).equalsIgnoreCase("queryRange")){
                        boolean result = rangeModule2.queryRange(currOperationLeft, currOperationRight);
                        System.out.println("Is Query Range: ["+ currOperationLeft+"-"+actualRight+"] " +
                                "Exist: "+result);
                    }
                }
            } else {

                //true, false, false, false
                String[] operationChoice = {"RangeModule","addRange","addRange","addRange","queryRange","queryRange","queryRange","removeRange","queryRange"};
                int[][] operationRange = {{},{10,180},{150,200},{250,500},{50,100},{180,300},{600,1000},{50,150},{50,100}};

                for (int i = 1; i<operationChoice.length; i++){
                    int currOperationLeft = operationRange[i][0];
                    int currOperationRight = operationRange[i][1];

                    int actualRight = currOperationRight - 1;
                    if(operationChoice[i].equalsIgnoreCase("addRange")) {
                        //System.out.println("addRange(): [" + currOperationLeft + "-" + actualRight + "] ");
                        rangeModule2.addRange(currOperationLeft, currOperationRight);
                    }
                    else if(operationChoice[i].equalsIgnoreCase("removeRange")) {
                        //System.out.println("removeRange: ["+ currOperationLeft+"-"+actualRight+"] ");
                        rangeModule2.removeRange(currOperationLeft, currOperationRight);
                    }
                    else if(operationChoice[i].equalsIgnoreCase("queryRange")){
                        boolean result = rangeModule2.queryRange(currOperationLeft, currOperationRight);
                        System.out.println("Is Query Range: ["+ currOperationLeft+"-"+actualRight+"] " +
                                "Exist: "+result);
                    }
                }
            }
            t--;
        }
    }
}
