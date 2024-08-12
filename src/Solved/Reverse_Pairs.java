package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/reverse-pairs/

Input:
1
3
2
3
1
-999
Input meaning : nums = [1,3,2,3,1]
Output: 2

Input:
2
4
3
5
1
-999
Output: 3
*/

class NodeOfBST
{
    int countInLeftSide;    // including current node itself
    int countInRightSide;   // do not include current node in this
    int duplicateCount;
    int value;
    int height;
    NodeOfBST leftNode;
    NodeOfBST rightNode;

    public NodeOfBST(int value)
    {
        this.value = value;
        this.height = 1;
        this.countInLeftSide = 1; this.countInRightSide = 0;
        this.duplicateCount = 0;
        this.leftNode = null;  this.rightNode = null;
    }
}

class AVLTree
{
    NodeOfBST root;
    public AVLTree()
    {
        this.root = null;
    }

    private int max(int a, int b) // When we need to update the heights
    {
        return (a > b) ? a : b;
    }

    private int height(NodeOfBST node)
    {
        if(node == null)
            return 0;
        else
            return node.height;
    }

    private int balanceFactor(NodeOfBST node)
    {
        if( node == null)
            return 0;
        else
           return ( height(node.leftNode) - height(node.rightNode) ) ;
    }

    private int rightCount(NodeOfBST node)
    {
        if(node == null)
            return 0;
        else
            return node.countInRightSide;
    }

    private int leftCount(NodeOfBST node)
    {
        if(node == null)
            return 0;
        else
            return node.countInLeftSide;
    }

    private NodeOfBST left_Rotation( NodeOfBST currNode )
    {
        // get required values value
        NodeOfBST rightChild = currNode.rightNode;
        NodeOfBST leftTreeOfRightChild = rightChild.leftNode;

        // update nodes
        rightChild.leftNode = currNode;
        currNode.rightNode = leftTreeOfRightChild;

        // update counts
        currNode.countInRightSide = leftCount( currNode.rightNode ) + rightCount( currNode.rightNode );
        rightChild.countInLeftSide = 1 + rightChild.duplicateCount + leftCount( rightChild.leftNode ) + rightCount( rightChild.leftNode );

        //  Update heights
        currNode.height = max( height(  currNode.leftNode  ), height(  currNode.rightNode  ) ) + 1;
        rightChild.height = max( height(  rightChild.leftNode  ), height(  rightChild.rightNode  ) ) + 1;

        return rightChild;
    }

    private NodeOfBST right_Rotation( NodeOfBST currNode )
    {
        // get required values value
        NodeOfBST leftChild = currNode.leftNode;
        NodeOfBST rightTreeOfRightChild = leftChild.rightNode;

        // update nodes
        leftChild.rightNode = currNode;
        currNode.leftNode = rightTreeOfRightChild;

        // update counts
        currNode.countInLeftSide = 1 + currNode.duplicateCount + leftCount( currNode.leftNode ) + rightCount( currNode.leftNode );
        leftChild.countInRightSide = leftCount( leftChild.rightNode ) + rightCount( leftChild.rightNode );

        //  Update heights
        currNode.height = max( height(  currNode.leftNode  ), height(  currNode.rightNode  ) ) + 1;
        leftChild.height = max( height(  leftChild.leftNode  ), height(  leftChild.rightNode  ) ) + 1;

        return leftChild;
    }

    public NodeOfBST addInAVLTree(NodeOfBST root, int val)
    {
        if(root == null)
        {
            return new NodeOfBST(val);
        }
        else if( root.value > val) // val is smaller insert in left side
        {
            root.countInLeftSide++;
            root.leftNode= addInAVLTree(root.leftNode, val);
        }
        else if( root.value < val) // val is larger insert in right side
        {
            root.countInRightSide++;
            root.rightNode = addInAVLTree(root.rightNode, val);
        }
        else // root.value == val, increase Duplicity count
        {
            root.countInLeftSide++;
            root.duplicateCount++;
            return root;
        }
        // update height
        root.height = 1 + max( height(  root.leftNode  ), height(  root.rightNode  ) );

        // [ LL-imbalance/insertion ] -> right_Rotation
        if( balanceFactor( root ) > 1 && val < root.leftNode.value)
        {
            return right_Rotation( root );
        }
        // [ RR-imbalance/insertion ] -> left_Rotation
        if( balanceFactor( root ) < -1 && val > root.rightNode.value)
        {
            return left_Rotation( root );
        }
        // [ LR-imbalance/insertion ] -> left + right Rotation
        if( balanceFactor( root ) > 1 && val > root.leftNode.value)
        {
            root.leftNode = left_Rotation( root.leftNode);
            return right_Rotation( root );
        }
        // [ RL-imbalance/insertion ] -> right + left Rotation
        if( balanceFactor( root ) < -1 && val < root.rightNode.value)
        {
            root.rightNode = right_Rotation( root.rightNode);
            return left_Rotation( root );
        }
        // return the unchanged node if it is already balanced
        return root;
    }

    public int findNode(NodeOfBST root, int val)
    {
        if( root == null )
            return 0;
        if(root.value == val) // root.value is equal to value to found
            return root.countInLeftSide;
        else if( root.value > val) // val is smaller so try to found on left side
            return findNode(root.leftNode, val);
        else //if( root.value < val) val is larger so try to found on right side
        {
            return root.countInLeftSide + findNode(root.rightNode, val);
        }
    }

}

public class Reverse_Pairs
{
    public static void main(String []args) {
        Reverse_Pairs reverse_pairs = new Reverse_Pairs();
        //System.out.println("val = "+reverse_pairs.findMaxReversePairValueForCurr(-9));
        Scanner input = new Scanner(System.in);
        int t;
        System.out.println("Enter Number Of Test Cases = ");
        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if(choice == 1)
            {
                int x;
                List<Integer> listOfNumbers = new ArrayList<>();
                System.out.println("Enter Your Num Array For Stop Insertion Press -999");
                x = input.nextInt();
                while(x!=-999)
                {
                    listOfNumbers.add(x);
                    x = input.nextInt();
                }
                int[] nums = listOfNumbers.stream().mapToInt(i->i).toArray();
                System.out.println( "Number Of Reverse Pair In Num Array = " + reverse_pairs.reversePairs(  nums  ) );
            }
            else
            {
                int[] nums = {1,3,2,3,1}; // ans = 2
                System.out.println( "Number Of Reverse Pair In Num Array = " + reverse_pairs.reversePairs(  nums  ) );
            }
            t--;
        }
    }

    public int reversePairs(int[] nums)
    {
        if(nums.length >= 2) {  // For pair there should be 2 elements in array
            int countReversePairs = 0;

            AVLTree tree = new AVLTree();
            tree.root = tree.addInAVLTree(tree.root, nums[nums.length - 1]);
//            System.out.println("Root Of BST = "+tree.root.value);
            for( int i = nums.length - 2; i >= 0; i--)
            {
                int valToFound = findMaxReversePairValueForCurr( nums[ i ] );
                int count = tree.findNode( tree.root, valToFound);
                countReversePairs = countReversePairs + count;
//                System.out.println("For index value = "+i+" value = "+nums[ i ] +" value To Found = "+ valToFound +
//                        " reverse Pair Count = " + count);
                tree.root = tree.addInAVLTree( tree.root, nums[ i ] );
            }
            return countReversePairs;
        }
        else
            return 0;
    }

    private int findMaxReversePairValueForCurr(int curr)
    {
        if(curr>=0) {
            if (curr % 2 == 0) {
                //System.out.println("Yes Divisible By 2");
                return ((curr / 2) - 1);
            }
            else
                return ( curr / 2 );
        }
        else {
                return ((curr / 2) - 1);
        }
    }
}
