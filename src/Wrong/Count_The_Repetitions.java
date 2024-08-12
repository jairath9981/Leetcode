package Wrong;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/count-the-repetitions/

   This code is not working for large inputs eg 7th input. So See Part 2 of Solution which is working for all input

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

class Node_Count_The_Repetitions {
    int val;
    Node_Count_The_Repetitions next;

    public Node_Count_The_Repetitions(int val){
        this.val = val;
        this.next = null;
    }
}

class PairIntNode_Count_The_Repetitions {
    int index;
    Node_Count_The_Repetitions node;
    public PairIntNode_Count_The_Repetitions(int index, Node_Count_The_Repetitions node){
        this.index = index;
        this.node = node;
    }
}

class ListStruct_Count_The_Repetitions {
    Node_Count_The_Repetitions head;
    Node_Count_The_Repetitions tail;
    List<PairIntNode_Count_The_Repetitions> arr;
    int start;
    int end;
    public ListStruct_Count_The_Repetitions(){
        head = null;
        tail = null;

        arr = new ArrayList<>();
        start = 0;
        end = 0;
    }
    public void push(int val){
        Node_Count_The_Repetitions listObj = new Node_Count_The_Repetitions(val);
        if(this.head == null)
            this.head = listObj;
        else
            this.tail.next = listObj;
        this.tail = listObj;

        if(this.arr.size()>end)
            this.arr.set(end, new PairIntNode_Count_The_Repetitions(val, listObj));
        else
            this.arr.add(new PairIntNode_Count_The_Repetitions(val, listObj));
        end++;
    }

    public void push(Node_Count_The_Repetitions node) {
        node.next = null;
        if(this.head == null)
            this.head = node;
        else
            this.tail.next = node;
        this.tail = node;

        if(this.arr.size()>end)
            this.arr.set(end, new PairIntNode_Count_The_Repetitions(node.val, node));
        else
            this.arr.add(new PairIntNode_Count_The_Repetitions(node.val, node));
        end++;
    }

    public void moveHead() {
        if(this.head!=null)
            this.head = head.next;
        if(this.head==null)
            this.tail=null;

        start++;
    }

    public void appendList(ListStruct_Count_The_Repetitions listAppendInBeginning) {
        listAppendInBeginning.tail.next = this.head;

        this.head = listAppendInBeginning.head;
        if(this.tail == null)
            this.tail = listAppendInBeginning.tail;

        List<PairIntNode_Count_The_Repetitions> listToAdd = new ArrayList<>(listAppendInBeginning.arr.subList(
                listAppendInBeginning.start, listAppendInBeginning.end));
        this.arr.addAll(start, listToAdd);
        this.end = this.end + (listAppendInBeginning.end - listAppendInBeginning.start);
    }

    public void removeList() {
        this.head = null;
        this.tail = null;

        this.arr = null;
        start = 0;
        end = 0;
    }

    public boolean emptyList(){
        if(this.head == null)
            return true;
        return false;
    }

    public void copySubListFromIndex(ListStruct_Count_The_Repetitions list, int index) {
        Node_Count_The_Repetitions head = list.arr.get(index).node;
        Node_Count_The_Repetitions tail = list.arr.get(list.end-1).node;

        this.head = head;
        this.tail = tail;

        this.arr = new ArrayList<>(list.arr.subList(index, list.end));
        this.start = 0;
        this.end = list.end - index;
    }

    public void removeSubListAfterIndex(int index) {
        Node_Count_The_Repetitions tail = this.arr.get(index - 1).node;
        tail.next = null;

        this.tail = tail;

        this.end = index;
    }

    public void copySubListUpToIndex(ListStruct_Count_The_Repetitions list, int index) {
        Node_Count_The_Repetitions tail = list.arr.get(index).node;
        tail.next = null;

        if(this.head == null)
            this.head = list.arr.get(list.start).node;
        else
            this.tail.next = list.arr.get(list.start).node;
        this.tail = tail;

        List<PairIntNode_Count_The_Repetitions> listToAdd = new ArrayList<>(list.arr.subList(list.start, index+1));
        this.arr.addAll(listToAdd);
        this.end = this.end + (index - list.start) + 1;
    }

    public void removeSubListBeforeIndex(int index) {
        this.head = this.arr.get(index).node;
        this.start = index;
    }
}

class Pair_Count_The_Repetitions {
    ListStruct_Count_The_Repetitions present;
    ListStruct_Count_The_Repetitions deleted;
    public Pair_Count_The_Repetitions(){
        present = null;
        deleted = null;
    }
}

public class Count_The_Repetitions {
    public static void main(String[] args) {
        Count_The_Repetitions count_the_repetitions = new Count_The_Repetitions();
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
                        count_the_repetitions.getMaxRepetitions(s1, n1, s2, n2));
                System.out.println();
            }
            else {
                s1 = "phqghumeaylnlfdxfircvscxggbwkfnqduxwfnfozvsrtkjprepggxrpnrvystmwcysyycqpevikeffmznimkkasvwsrenzkycxf";
                        n1 = 100; s2 = "xtlsgypsfa";  n2 = 1;
                System.out.println("So, Max Number Of str2 = [s2+s2+s2+...n2 times] Which Can Be Obtained From " +
                        "str1 = [s1+s1+s1...n1 times] Where Deletion Is Allowed On str1 =  "+
                        count_the_repetitions.getMaxRepetitions(s1, n1, s2, n2));
                System.out.println();
            }
            t--;
        }
    }

    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        List<Pair_Count_The_Repetitions> alphabetListOfS1 = createAlphabetListOfS1(s1);
//        printListOfPair(alphabetListOfS1);
        int s2CountInStr1 = countOfS1RequireForS2(alphabetListOfS1, n1, s1.length(), s2);
//        System.out.println("s2FromStr1 = "+s2FromStr1);
        if(s2CountInStr1 == -1) {
            System.out.println("s2 can't be Formed From s1 either n1 is too small or s1 do not contain " +
                    "some require characters");
            return 0;
        }
        return s2CountInStr1/n2;
    }

    private int countOfS1RequireForS2(List<Pair_Count_The_Repetitions> alphabetListOfS1, int n1, int len1, String s2) {
        int count = 0;
        int i = 0;
        int indexOnWhichWeSit = 0;
        int stringRoundCovered = 0;

        while(stringRoundCovered<n1){

            int index = ((int)s2.charAt(i)) - 97;
            Pair_Count_The_Repetitions alphabetPair = alphabetListOfS1.get(index);
            int additive = stringRoundCovered * len1;

            if(alphabetPair.present!=null){
                stringRoundCovered = manageListMovementFromPresentList(alphabetPair.present, alphabetPair,
                        stringRoundCovered, additive, len1, indexOnWhichWeSit);
                //alphabetPair.present.head.val + additive >= indexOnWhichWeSit
                additive = stringRoundCovered * len1;
                indexOnWhichWeSit = alphabetPair.present.head.val + additive;
                moveIndexToDeleteList(alphabetPair.present, alphabetPair);

            }else if(alphabetPair.deleted!=null){
                stringRoundCovered = manageListMovementWhenAllIndexesInDeletedList(alphabetPair.deleted, alphabetPair,
                        stringRoundCovered, additive, indexOnWhichWeSit);
                additive = stringRoundCovered * len1;
                indexOnWhichWeSit = alphabetPair.present.head.val + additive;
                moveIndexToDeleteList(alphabetPair.present, alphabetPair);
            }
            else{  // if s1 do not contain character which is present in s2
                return -1;
            }
            if(stringRoundCovered>=n1)
                break;
            System.out.println("stringRoundCovered = "+stringRoundCovered+" indexOnWhichWeSit = "+indexOnWhichWeSit+" char = "+s2.charAt(i));
            if(alphabetPair.present!=null)
                System.out.println("After Doing Operation present list = "+alphabetPair.present.head.val+" - "+alphabetPair.present.tail.val);
            if(alphabetPair.deleted!=null)
            System.out.println("After doing Operation deleted list = "+alphabetPair.deleted.head.val+" - "+alphabetPair.deleted.tail.val);
            if(i<s2.length()-1)
                i++;
            else{
                i = 0;
                count++;
                System.out.println("count = "+count+" indexOnWhichWeSit = "+indexOnWhichWeSit+" stringRoundCovered = "+stringRoundCovered);
                //System.out.println("Start Counting");
                //printListOfPair(alphabetListOfS1);
            }
        }
        if(count<1)
            count = -1;
        //System.out.println("count = "+count+"  string Completely Covered = "+stringRoundCovered);
        return count;
    }

    private int manageListMovementFromPresentList(ListStruct_Count_The_Repetitions present, Pair_Count_The_Repetitions alphabetPair, int stringRoundCovered,
                                                  int additive, int len, int indexOnWhichWeSit) {

        System.out.println("**********manageListMovementFromPresentList***********");
        ListStruct_Count_The_Repetitions deleted = alphabetPair.deleted;

        if(present.head.val+additive<indexOnWhichWeSit  && present.tail.val+additive>indexOnWhichWeSit){
            int index = binarySearch(present.arr, present.start, present.end, indexOnWhichWeSit-additive);

            System.out.println("index = "+index);

            if(index>present.start) {
                System.out.println("DO SOME MOVEMENT IN LISTS");
                if(deleted==null)
                    deleted = new ListStruct_Count_The_Repetitions();
                deleted.copySubListUpToIndex(present, index - 1);
                present.removeSubListBeforeIndex(index);
            }
        }
        else if(present.head.val+additive<indexOnWhichWeSit){
            if(deleted!=null)
                present.appendList(deleted);;
            deleted = null;
            stringRoundCovered++;
        }
        else if(deleted!=null && deleted.head.val+additive>indexOnWhichWeSit){
            present.appendList(deleted);;
            deleted = null;
        }
        alphabetPair.present = present;
        alphabetPair.deleted = deleted;
        return stringRoundCovered;
    }

    private int manageListMovementWhenAllIndexesInDeletedList(ListStruct_Count_The_Repetitions deleted, Pair_Count_The_Repetitions alphabetPair,
                                                              int stringRoundCovered, int additive, int indexOnWhichWeSit) {

        System.out.println("*************manageListMovementWhenAllIndexesInDeletedList***************");
        ListStruct_Count_The_Repetitions present;
        if(deleted.head.val+additive<indexOnWhichWeSit  && deleted.tail.val+additive>indexOnWhichWeSit){ // binary
            int index = binarySearch(deleted.arr, deleted.start, deleted.end, indexOnWhichWeSit-additive+1);
            present = new ListStruct_Count_The_Repetitions();
            System.out.println("index= "+index);
            present.copySubListFromIndex(deleted, index);
            deleted.removeSubListAfterIndex(index);
        }
        else{
            if(deleted.head.val+additive<indexOnWhichWeSit)
                stringRoundCovered++;
            present = deleted;
            deleted = null;
        }

        alphabetPair.present = present;
        alphabetPair.deleted = deleted;
        return stringRoundCovered;
    }

    int binarySearch(List<PairIntNode_Count_The_Repetitions> arr, int left, int right, int smallerThen)
    {
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr.get(mid).index == smallerThen){
                ans = mid;
                break;
            }
            if (arr.get(mid).index < smallerThen)
                left = mid + 1;
            else if(arr.get(mid).index > smallerThen){
                ans = mid;
                right = mid - 1;
            }
        }
        return ans;
    }

    private void moveIndexToDeleteList(ListStruct_Count_The_Repetitions present, Pair_Count_The_Repetitions alphabetPair) {
        ListStruct_Count_The_Repetitions deleted = alphabetPair.deleted;
        if(deleted==null)
            deleted = new ListStruct_Count_The_Repetitions();

        Node_Count_The_Repetitions needToMoveInDeleteList = present.head;

        present.moveHead();
        if(present.emptyList())
            present = null;

        deleted.push(needToMoveInDeleteList);

        alphabetPair.present = present;
        alphabetPair.deleted = deleted;
    }

    private List<Pair_Count_The_Repetitions> createAlphabetListOfS1(String s1) {
        List<Pair_Count_The_Repetitions> alphabetListOfS1 = new ArrayList<>(26);

        for(int i = 0; i<26; i++)
            alphabetListOfS1.add(i, new Pair_Count_The_Repetitions());

        for(int i = 0; i<s1.length(); i++){
            int index = ((int)s1.charAt(i)) - 97;
            //System.out.println("index = "+index);
            Pair_Count_The_Repetitions alphabetPair = alphabetListOfS1.get(index);

            if(alphabetPair.present == null){
                //System.out.println("Initialize Fist Time");
                ListStruct_Count_The_Repetitions alphabetIndex = new ListStruct_Count_The_Repetitions();
                alphabetIndex.push(i);
                alphabetPair.present = alphabetIndex;
            }
            else{
                ListStruct_Count_The_Repetitions alphabetIndex = alphabetPair.present;
                alphabetIndex.push(i);
            }
        }
        return alphabetListOfS1;
    }

    private void printListOfPair(List<Pair_Count_The_Repetitions> alphabetListOfS1) {
        System.out.println("********printListOfPair***********");

        for(int i = 0;i<alphabetListOfS1.size(); i++){

            char c = (char)(i+97);
            Pair_Count_The_Repetitions alphabetPair = alphabetListOfS1.get(i);

            ListStruct_Count_The_Repetitions alphabetPresent = alphabetPair.present;
            ListStruct_Count_The_Repetitions alphabetDeleted = alphabetPair.deleted;

            System.out.println("******** Print Present List ***********");
            printList(alphabetPresent, c);
            System.out.println();
            System.out.println("******** Print Deleted List ***********");
            printList(alphabetDeleted, c);

            System.out.println();
        }
    }

    private void printList(ListStruct_Count_The_Repetitions list, char c) {

        if(list!=null){
            Node_Count_The_Repetitions head = list.head;
            System.out.print("Indexes of = "+c+" -> ");
            while(head!=null){
                System.out.print(head.val + ", ");
                head = head.next;
            }
        }
        else{
            System.out.print(c +" is not present in string");
        }
    }
}
