package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/count-the-repetitions/

Example 1:
Input1: s1 = "acb", n1 = 4, s2 = "ab", n2 = 2
Output: 2

Example 2:
Input2: s1 = "acb", n1 = 1, s2 = "acb", n2 = 1
Output: 1

Input3: s1 = "aaa", n1 = 3, s2 = "aa", n2 = 1
Output: 4

Input4: s1 = "baba", n1 = 11, s2 = "baab", n2 = 1
Output: 7

Input5: s1 = "bacaba", n1 = 3, s2 = "abacab", n2 = 1
Output: 2

Input6: s1 = "musicforever", n1 = 10, s2 = "lovelive", n2 = 100000
Output: 0

Input7: s1 = "phqghumeaylnlfdxfircvscxggbwkfnqduxwfnfozvsrtkjprepggxrpnrvystmwcysyycqpevikeffmznimkkasvwsrenzkycxf",
n1 = 100, s2 = "xtlsgypsfa", n2 = 1
Output: 49

 */

class Node_Count_The_Repetitions2 {
    int val;
    Node_Count_The_Repetitions2 next;

    public Node_Count_The_Repetitions2(int val){
        this.val = val;
        this.next = null;
    }
}

class ListStruct_Count_The_Repetitions2 {

    Node_Count_The_Repetitions2 headOfPresent;
    Node_Count_The_Repetitions2 tailOfPresent;

    Node_Count_The_Repetitions2 headOfDeleted;
    Node_Count_The_Repetitions2 tailOfDeleted;

    public ListStruct_Count_The_Repetitions2(){
        headOfPresent = null;
        tailOfPresent = null;

        headOfDeleted = null;
        tailOfDeleted = null;
    }

    public void pushToPresent(int val){
        Node_Count_The_Repetitions2 listObj = new Node_Count_The_Repetitions2(val);
        if(this.headOfPresent == null) {
            this.headOfPresent = listObj;
        }
        else
            this.tailOfPresent.next = listObj;
        this.tailOfPresent = listObj;
    }

    public void pushToDelete(Node_Count_The_Repetitions2 node){
        node.next = null;
        if(this.headOfDeleted == null) {
            this.headOfDeleted = node;
        }
        else
            this.tailOfDeleted.next = node;
        this.tailOfDeleted = node;
    }

    public void addNodeInDeleteList() {
        if(this.headOfPresent!=null) {
            Node_Count_The_Repetitions2 nextNode = this.headOfPresent.next;
            this.pushToDelete(this.headOfPresent);
            this.headOfPresent = nextNode;
        }
        if(this.headOfPresent==null)
            this.tailOfPresent=null;
    }

    public void moveToPresentListFromDeleteList() {
        if(this.headOfDeleted!=null) {
            Node_Count_The_Repetitions2 currHeadOfPresentList = this.headOfPresent;
            this.headOfPresent = this.headOfDeleted;
            if (this.tailOfPresent == null)
                this.tailOfPresent = this.tailOfDeleted;
            else
                this.tailOfDeleted.next = currHeadOfPresentList;
        }
        this.headOfDeleted = null;
        this.tailOfDeleted = null;
    }
}

public class Count_The_Repetitions2 {
    public static void main(String[] args) {
        Count_The_Repetitions2 count_the_repetitions2 = new Count_The_Repetitions2();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String s1, s2;
            int n1, n2;
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter String s1 = "); s1 = input.nextLine();
                System.out.println("Enter String s1 Repetition = "); n1 = input.nextInt();
                input.nextLine();
                System.out.println("Enter String s2 = "); s2 = input.nextLine();
                System.out.println("Enter String s2 Repetition = "); n2 = input.nextInt();

                System.out.println("So, Max Number Of str2 = [s2+s2+s2+...n2 times] Which Can Be Obtained From " +
                        "str1 = [s1+s1+s1...n1 times] Where Deletion Is Allowed On str1 = "+
                        count_the_repetitions2.getMaxRepetitions(s1, n1, s2, n2));
                System.out.println();
            }
            else {
                s1 = "acb"; n1 = 4;
                s2 = "ab";  n2 = 2;
                System.out.println("So, Max Number Of str2 = [s2+s2+s2+...n2 times] Which Can Be Obtained From " +
                        "str1 = [s1+s1+s1...n1 times] Where Deletion Is Allowed On str1 =  "+
                        count_the_repetitions2.getMaxRepetitions(s1, n1, s2, n2));
                System.out.println();
            }
            t--;
        }
    }

    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        List<Integer> dp = getStopDistance(s1, s2);

        int len1 = s1.length();
        int totalIndexInStr1 = ( n1 * len1 ) - 1;
        int count = 0;
        int startIndex = 0, stopIndex = 0;

        if(dp!=null) {
            printDp(dp);
            stopIndex = getStopIndexFromDp(dp, startIndex, len1);
            while(stopIndex<=totalIndexInStr1){
                count++;
                startIndex = stopIndex + 1;
                stopIndex = getStopIndexFromDp(dp, startIndex, len1);
            }
        }
        else
            return count;
        return count/n2;
    }

    private int getStopIndexFromDp(List<Integer> dp, int startIndex, int len1) {
        int distanceNeedToMove = dp.get((startIndex%len1));
        return startIndex + distanceNeedToMove - 1;
    }

    private List<Integer> getStopDistance(String s1, String s2) {
        List<ListStruct_Count_The_Repetitions2> alphabetListOfS1 = createAlphabetListOfS1(s1);
        System.out.println("getStopDistance1");
        printListOfAlphabetDetails(alphabetListOfS1);

        List<Integer> dp = new ArrayList<>();
        for(int i = 0; i<s1.length(); i++){
            System.out.println("s1 = "+s1);
            if(i >= 1){ // re-initialize lists
                moveAllCharactersFromDeleteListToPresentList(alphabetListOfS1);
                //printListOfAlphabetDetails(alphabetListOfS1);
            }
            int distanceOfS2InStr1 = distanceCoveredInStr1ToGetS2Where_i_isStartIndexInStr1(alphabetListOfS1, i,
                    s1.length(), s2);
            if(distanceOfS2InStr1 == -1)
                return null;
            else
                dp.add(distanceOfS2InStr1 + 1);
        }
        return dp;
    }

    private int distanceCoveredInStr1ToGetS2Where_i_isStartIndexInStr1(
            List<ListStruct_Count_The_Repetitions2> alphabetListOfS1, int startIndex,
            int len1, String s2) {

        int currentIndex = startIndex;
        int s1CurrRound = 1;

        //System.out.println("s2 = "+s2);
        for(int i = 0; i<s2.length(); i++){

            int index = ((int)s2.charAt(i)) - 97;
            ListStruct_Count_The_Repetitions2 listDetails = alphabetListOfS1.get(index);
            Node_Count_The_Repetitions2 headOfPresentList = listDetails.headOfPresent;

            if(headOfPresentList!=null){
                currentIndex = getCharacterIndex(listDetails, currentIndex, s1CurrRound, len1);
                if(headOfPresentList==listDetails.headOfPresent) {
                    s1CurrRound++;
                    System.out.println("(P)For index = "+i+"(char = "+ s2.charAt(i)+")in s2 current Round of string s1 = "
                            +s1CurrRound);
                    moveAllCharactersFromDeleteListToPresentList(alphabetListOfS1);
                    currentIndex = getCharacterIndex(listDetails, currentIndex, s1CurrRound, len1);
                }
            }
            else if(listDetails.headOfDeleted!=null){
                s1CurrRound++;
                System.out.println("(D)For index = "+i+"(char = "+ s2.charAt(i)+")in s2 current Round of string s1 = "
                        +s1CurrRound);
                moveAllCharactersFromDeleteListToPresentList(alphabetListOfS1);
                currentIndex = getCharacterIndex(listDetails, currentIndex, s1CurrRound, len1);
            }
            else{ // alphabet not found in s1
                return -1;
            }
            System.out.println("For index = "+i+"(char = "+ s2.charAt(i)+")in s2 forward matched index in s1 = "
                    +currentIndex+"  Round = "+s1CurrRound);
        }
        System.out.println("currentIndex = "+currentIndex);
        return currentIndex - startIndex;
    }

    private void moveAllCharactersFromDeleteListToPresentList(
            List<ListStruct_Count_The_Repetitions2> alphabetListOfS1) {

        for(int i = 0; i<26; i++){
            ListStruct_Count_The_Repetitions2 listDetails = alphabetListOfS1.get(i);
            listDetails.moveToPresentListFromDeleteList();
        }
    }

    private int getCharacterIndex(ListStruct_Count_The_Repetitions2 listDetails, int currentIndex,
                                  int s1CurrRound, int len1) {
        //System.out.println("*************getCharacterIndex**************");
        int additiveDistance = getAdditiveIndexes(s1CurrRound, len1);
        if(listDetails.headOfPresent.val + additiveDistance >= currentIndex) {
            System.out.println("Get First One");
            currentIndex = listDetails.headOfPresent.val + additiveDistance;
            listDetails.addNodeInDeleteList();
        }
        else if(listDetails.tailOfPresent.val + additiveDistance >= currentIndex){
            System.out.println("Move-Ahead");
            while(listDetails.headOfPresent.val + additiveDistance < currentIndex)
                listDetails.addNodeInDeleteList();
            currentIndex = listDetails.headOfPresent.val + additiveDistance;
            listDetails.addNodeInDeleteList();
        }
        System.out.println("Present List");
        printList(listDetails.headOfPresent, 'x');
        System.out.println("Deleted List");
        printList(listDetails.headOfDeleted, 'x');
        return currentIndex;
    }

    private int getAdditiveIndexes(int s1CurrRound, int len1) {
        if(s1CurrRound == 1)
            return 0;
        else
            return len1*(s1CurrRound-1);
    }

    private List<ListStruct_Count_The_Repetitions2> createAlphabetListOfS1(String s1) {
        List<ListStruct_Count_The_Repetitions2> alphabetListOfS1 = new ArrayList<>(26);

        for(int i = 0; i<26; i++)
            alphabetListOfS1.add(i, new ListStruct_Count_The_Repetitions2());

        for(int i = 0; i<s1.length(); i++){
            int index = ((int)s1.charAt(i)) - 97;
            ListStruct_Count_The_Repetitions2 alphabetDetail = alphabetListOfS1.get(index);
            alphabetDetail.pushToPresent(i);
        }
        return alphabetListOfS1;
    }

    private void printDp(List<Integer> dp) {
        System.out.println("********printDp***********");
        for(int i = 0; i<dp.size(); i++){
            System.out.print(dp.get(i)+ ", ");
        }
        System.out.println();
    }

    private void printListOfAlphabetDetails(List<ListStruct_Count_The_Repetitions2> alphabetListOfS1) {

        System.out.println("********printListOfAlphabetDetails***********");

        System.out.println("******** Print Present List ***********");
        for(int i = 0;i<alphabetListOfS1.size(); i++){

            char c = (char)(i+97);
            ListStruct_Count_The_Repetitions2 alphabetDetail = alphabetListOfS1.get(i);
            Node_Count_The_Repetitions2 alphabetPresentNodeHead = alphabetDetail.headOfPresent;
            printList(alphabetPresentNodeHead, c);
        }

        System.out.println("******** Print Deleted List ***********");
        for(int i = 0;i<alphabetListOfS1.size(); i++) {

            char c = (char) (i + 97);
            ListStruct_Count_The_Repetitions2 alphabetDetail = alphabetListOfS1.get(i);
            Node_Count_The_Repetitions2 alphabetDeletedNodeHead = alphabetDetail.headOfDeleted;
            printList(alphabetDeletedNodeHead, c);
        }
    }

    private void printList(Node_Count_The_Repetitions2 node, char c) {

        if(node!=null) {
            Node_Count_The_Repetitions2 head = node;
            System.out.print("Indexes of = " + c + " -> ");
            while (head != null) {
                System.out.print(head.val + ", ");
                head = head.next;
            }
            System.out.println();
        }
    }
}
