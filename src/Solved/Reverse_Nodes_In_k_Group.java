package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/reverse-nodes-in-k-group/

Input 1:
1 2 3 4 5 -999
2
Output: [2,1,4,3,5]

Input 2:
1 2 3 4 5 -999
3
Output: [3,2,1,4,5]
*/

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) {
        this.val = val;
    }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Triplet_Reverse_Nodes_In_k_Group{
    ListNode head;
    ListNode tail;
    ListNode nextGroupStarter;

    public Triplet_Reverse_Nodes_In_k_Group(ListNode head, ListNode tail,
                 ListNode nextGroupStarter) {
        this.head = head;
        this.tail = tail;
        this.nextGroupStarter = nextGroupStarter;
    }
}

public class Reverse_Nodes_In_k_Group {
    public static void main(String[] args) {
        Reverse_Nodes_In_k_Group reverseNodesInKGroup = new Reverse_Nodes_In_k_Group();
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
            ListNode head = null, tail = null, node = null;
            if (choice == 1) {
                int x;
                System.out.println("Enter Your Link List, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    node = new ListNode(x);
                    if(head == null){
                        head = node;
                    }else{
                        tail.next = node;
                    }
                    tail = node;
                    x = input.nextInt();
                }
                System.out.println("Enter K Group Size For Reversal Group: ");
                int k = input.nextInt();;
                ListNode outputHead = reverseNodesInKGroup.reverseKGroup(head, k);
                printList(outputHead);
            } else {
                int[] arr = {1, 2, 3};
                int k = 2;
                for(int i = 0; i<arr.length; i++){
                    node = new ListNode(arr[i]);
                    if(head == null){
                        head = node;
                    }else{
                        tail.next = node;
                    }
                    tail = node;
                }
                ListNode outputHead = reverseNodesInKGroup.reverseKGroup(head, k);
                printList(outputHead);
            }
            t--;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode headOfReversal = null, tailOfPrevGrop = null,
                starter = head;
        while(starter!=null){
            Triplet_Reverse_Nodes_In_k_Group reverseGroupTriplet =
                    reverseKNodes(starter, k);
            if(tailOfPrevGrop!=null){
                tailOfPrevGrop.next = reverseGroupTriplet.head;
            }
            tailOfPrevGrop = reverseGroupTriplet.tail;
            if(headOfReversal == null){
                headOfReversal = reverseGroupTriplet.head;
            }
            starter = reverseGroupTriplet.nextGroupStarter;
        }
        return headOfReversal;
    }

    private Triplet_Reverse_Nodes_In_k_Group reverseKNodes(ListNode node,
                                 int k){
        if(isCountGreaterThenOrEqualToK(node, k)){
            ListNode prev = null, next = null, curr = node;
            int count = 0;
            while(curr!=null && count<k){
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
                count++;
            }
            Triplet_Reverse_Nodes_In_k_Group reverseGroupTriplet =
                    new Triplet_Reverse_Nodes_In_k_Group(prev, node, next);
            return reverseGroupTriplet;
        }
        Triplet_Reverse_Nodes_In_k_Group reverseGroupTriplet =
                new Triplet_Reverse_Nodes_In_k_Group(node, null, null);
        return reverseGroupTriplet;
    }

    private boolean isCountGreaterThenOrEqualToK(ListNode node, int k){
        int count = 0;
        while(node!=null && count<k){
            count++;
            node = node.next;
        }
        if(count == k)
            return true;
        return false;
    }

    private static void printList(ListNode outputHead) {
        while(outputHead!=null){
            System.out.print(outputHead.val+", ");
            outputHead = outputHead.next;
        }
        System.out.println();
    }
}
