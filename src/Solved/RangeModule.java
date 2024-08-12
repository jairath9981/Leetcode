package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
https://leetcode.com/problems/range-module/
See Input From RangeModule_Input.txt
*/

class PairModuleForSegmentTree_RangeModule{

    int start;
    int end;
    Map<Integer, Boolean> segmentTree;
    Map<Integer, Boolean> lazyPropagationTree;

    PairModuleForSegmentTree_RangeModule(int start, int end){
     this.start = start;
     this.end = end;
     this.segmentTree = new HashMap<>();
     this.lazyPropagationTree = new HashMap<>();
    }

    public void addRange(int left, int right) {
        int diff = this.start;
        int maxForCurrTree = this.end - diff;
        updateSegmentTreePlusLazyPropagation(left, right,
                0, maxForCurrTree, 0, true);
    }

    public void removeRange(int left, int right) {
        int diff = this.start;
        int maxForCurrTree = this.end - diff;
        updateSegmentTreePlusLazyPropagation(left, right,
                0, maxForCurrTree, 0, false);
    }

    public boolean queryRange(int left, int right) {
        int diff = this.start;
        int maxForCurrTree = this.end - diff;
        return queryRangeHelper(left, right, 0, maxForCurrTree, 0);
    }

    private boolean queryRangeHelper(int queryLeft, int queryRight,
            int treeLeft, int treeRight, int treeIndex) {

        if(queryLeft<=queryRight){
            updateIfRequired(treeIndex);
            if(isCompleteOverlap(queryLeft, queryRight, treeLeft, treeRight)){
                return this.segmentTree.getOrDefault(treeIndex, false);
            }else if(isNoOverlap(queryLeft, queryRight, treeLeft, treeRight)){
                return true;
            }else{
                int mid = treeLeft + (treeRight-treeLeft)/2;
                int leftChild = getLeftChild(treeIndex);
                int rightChild = getRightChild(treeIndex);

                boolean leftPresent = queryRangeHelper(
                        queryLeft, queryRight, treeLeft, mid, leftChild);
                boolean rightPresent = queryRangeHelper(
                        queryLeft, queryRight, mid+1, treeRight, rightChild);

                boolean bothPresent = leftPresent && rightPresent;
                return bothPresent;
            }
        }
        return false;
    }

    private boolean updateSegmentTreePlusLazyPropagation(int queryLeft, int queryRight,
           int treeLeft, int treeRight, int treeIndex, boolean currVal) {
        if(queryLeft<=queryRight){
            updateIfRequired(treeIndex);
            if(isCompleteOverlap(queryLeft, queryRight, treeLeft, treeRight)){
                this.segmentTree.put(treeIndex, currVal);
                propagateChangesToChild(treeIndex, currVal);
                return this.segmentTree.getOrDefault(treeIndex, false);
            }else if(isNoOverlap(queryLeft, queryRight, treeLeft, treeRight)){
                return this.segmentTree.getOrDefault(treeIndex, false);
            }else{
                int mid = treeLeft + (treeRight-treeLeft)/2;
                int leftChild = getLeftChild(treeIndex);
                int rightChild = getRightChild(treeIndex);

                boolean leftPresent = updateSegmentTreePlusLazyPropagation(
                        queryLeft, queryRight, treeLeft, mid, leftChild, currVal);
                boolean rightPresent = updateSegmentTreePlusLazyPropagation(
                        queryLeft, queryRight, mid+1, treeRight, rightChild, currVal);

                boolean bothPresent = leftPresent && rightPresent;
                this.segmentTree.put(treeIndex, bothPresent);
                return bothPresent;
            }
        }
        return false;
    }


    private void updateIfRequired(int treeIndex) {

        if(this.lazyPropagationTree.containsKey(treeIndex)){

            boolean isValuePresent = this.lazyPropagationTree.get(treeIndex);

            this.segmentTree.put(treeIndex, isValuePresent);

            propagateChangesToChild(treeIndex, isValuePresent);
        }
    }

    private void propagateChangesToChild(int treeIndex, boolean newValue) {
        int leftChild = getLeftChild(treeIndex);
        int rightChild = getRightChild(treeIndex);

        this.lazyPropagationTree.remove(treeIndex);
        this.lazyPropagationTree.put(leftChild, newValue);
        this.lazyPropagationTree.put(rightChild, newValue);
    }

    private int getLeftChild(int treeIndex) {
        return 2*treeIndex+1;
    }

    private int getRightChild(int treeIndex) {
        return 2*treeIndex+2;
    }

    private boolean isCompleteOverlap(int queryLeft, int queryRight,
                                      int treeLeft, int treeRight) {
        return treeLeft >= queryLeft && treeRight <= queryRight;
    }

    private boolean isNoOverlap(int queryLeft, int queryRight,
                                int treeLeft, int treeRight) {
        return treeRight < queryLeft || treeLeft > queryRight;
    }
}

public class RangeModule {

    List<PairModuleForSegmentTree_RangeModule> listOfSegmentTrees;

    public RangeModule() {
        this.listOfSegmentTrees = new ArrayList<>();
    }

    public void addRange(int left, int right) {
        right--;
        int index = binarySearchGreaterThanOrEqualToX(left, right);
        if(index == -1){ // no overlap right side query

            PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                    new PairModuleForSegmentTree_RangeModule(left, right);
            pairModuleForSegmentTree.addRange(0, right-left);
            this.listOfSegmentTrees.add(pairModuleForSegmentTree);

        }else{
            if(this.listOfSegmentTrees.get(index).start<=left &&
                    this.listOfSegmentTrees.get(index).end>=right){ //completely covered

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                       this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                pairModuleForSegmentTree.addRange(left-diff, right-diff);
                return;

            }
            else if(this.listOfSegmentTrees.get(index).start==left){ //partialOverlap1 from start

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                pairModuleForSegmentTree.addRange(start-diff, end-diff);

                addRange(this.listOfSegmentTrees.get(index).end+1, right+1);

            }
           else if(this.listOfSegmentTrees.get(index).start>left  &&
                    this.listOfSegmentTrees.get(index).start<=right){ //partialOverlap2 from before start to after end/before end

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int updateRight = Math.min(right, end);
                int diff = start;
                pairModuleForSegmentTree.addRange(start-diff, updateRight-diff);

                addRange(left, this.listOfSegmentTrees.get(index).start);
               if(this.listOfSegmentTrees.get(index).end<right){
                   addRange(this.listOfSegmentTrees.get(index).end+1, right+1);
               }

           }else if(this.listOfSegmentTrees.get(index).end>=left  &&
                    this.listOfSegmentTrees.get(index).end<=right){ //partialOverlap3 from before end to after end

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                pairModuleForSegmentTree.addRange(left-diff, end-diff);

                addRange(this.listOfSegmentTrees.get(index).end+1, right+1);

            }else if(this.listOfSegmentTrees.get(index).start>right){ //no overlap  query is on left of array

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        new PairModuleForSegmentTree_RangeModule(left, right);
                pairModuleForSegmentTree.addRange(0, right-left);
                this.listOfSegmentTrees.add(index, pairModuleForSegmentTree);

            }
        }
        //printList();
    }

    public void removeRange(int left, int right) {
        right--;
        int index = binarySearchGreaterThanOrEqualToX(left, right);
        if(index == -1){ // no overlap so we do not need to remove anything
            //System.out.println("Fall in No Overlap Zone1");
        }else{
            if(this.listOfSegmentTrees.get(index).start<=left &&
                    this.listOfSegmentTrees.get(index).end>=right){ // complete covered

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                pairModuleForSegmentTree.removeRange(left-diff, right-diff);
                return;

            }
            else if(this.listOfSegmentTrees.get(index).start==left){ //partialOverlap1 from start

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                pairModuleForSegmentTree.removeRange(start-diff, end-diff);

                removeRange(this.listOfSegmentTrees.get(index).end+1, right+1);

            }
            else if(this.listOfSegmentTrees.get(index).start>left  &&
                    this.listOfSegmentTrees.get(index).start<=right){ //partialOverlap2 from before start to after end/before end

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int updateRight = Math.min(right, end);
                int diff = start;
                pairModuleForSegmentTree.removeRange(start-diff, updateRight-diff);

                removeRange(left, this.listOfSegmentTrees.get(index).start);
                if(this.listOfSegmentTrees.get(index).end<right){
                    removeRange(this.listOfSegmentTrees.get(index).end+1, right+1);
                }

            }else if(this.listOfSegmentTrees.get(index).end>=left  &&
                    this.listOfSegmentTrees.get(index).end<=right){ //partialOverlap3 from before end to after end

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                pairModuleForSegmentTree.removeRange(left-diff, end-diff);

                removeRange(this.listOfSegmentTrees.get(index).end+1, right+1);

            }else if(this.listOfSegmentTrees.get(index).start>right){ //noOverlap query is on left of array
                // do not need to remove anything
                //System.out.println("Fall in No Overlap Zone2");
            }
        }
    }

    public boolean queryRange(int left, int right) {
        right--;
        int index = binarySearchGreaterThanOrEqualToX(left, right);
        if(index == -1){ // no overlap so we do not need to remove anything
            return false;
        }else{
            if(this.listOfSegmentTrees.get(index).start<=left &&
                    this.listOfSegmentTrees.get(index).end>=right){ // complete covered

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                return pairModuleForSegmentTree.queryRange(left-diff, right-diff);
            }
            else if(this.listOfSegmentTrees.get(index).start==left){ //partialOverlap1 from start

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                boolean partialResult = pairModuleForSegmentTree.queryRange(start-diff, end-diff);

                if(partialResult==false)
                    return false;
                return partialResult && queryRange(this.listOfSegmentTrees.get(index).end+1, right+1);

            }
            else if(this.listOfSegmentTrees.get(index).start>left  &&
                    this.listOfSegmentTrees.get(index).start<=right){ //partialOverlap2 from before start to after end/before end

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int updateRight = Math.min(right, end);
                int diff = start;
                boolean partialResult1 = pairModuleForSegmentTree.queryRange(start-diff, updateRight-diff);

                if(partialResult1==false)
                    return false;

                boolean partialResult3 = true;
                boolean partialResult2 = partialResult1 && queryRange(left, this.listOfSegmentTrees.get(index).start);
                if(this.listOfSegmentTrees.get(index).end<right){
                    partialResult3 = queryRange(this.listOfSegmentTrees.get(index).end+1, right+1);
                }
                return partialResult1 && partialResult2 && partialResult3;
            }else if(this.listOfSegmentTrees.get(index).end>=left  &&
                    this.listOfSegmentTrees.get(index).end<=right){ //partialOverlap3 from before end to after end

                PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                        this.listOfSegmentTrees.get(index);
                int start = pairModuleForSegmentTree.start;
                int end = pairModuleForSegmentTree.end;
                int diff = start;
                boolean partialResult = pairModuleForSegmentTree.queryRange(left-diff, end-diff);

                if(partialResult==false)
                    return false;

                return partialResult && queryRange(this.listOfSegmentTrees.get(index).end+1, right+1);

            }else if(this.listOfSegmentTrees.get(index).start>right){ //noOverlap query is on left of array
                // do not need to remove anything
                return false;
            }
        }
        return false;
    }

    private int binarySearchGreaterThanOrEqualToX(int queryStart, int queryEnd) {

        int left = 0, right = this.listOfSegmentTrees.size() - 1;
        int index = -1;

        while (left<=right){
            int mid = left + (right-left)/2;
            PairModuleForSegmentTree_RangeModule pairModuleForSegmentTree =
                    this.listOfSegmentTrees.get(mid);

            if(pairModuleForSegmentTree.start<=queryStart &&
                    pairModuleForSegmentTree.end>=queryEnd){ // completely covered
               return mid;
            }else if(pairModuleForSegmentTree.start==queryStart) { // find start of query in array
                //partial Overlap1
                return mid;
            } else if(pairModuleForSegmentTree.start>queryStart &&
                    pairModuleForSegmentTree.start<=queryEnd){
                //partial Overlap2
                return mid;
            }
            else if(pairModuleForSegmentTree.end>=queryStart &&
                    pairModuleForSegmentTree.end<queryEnd){
                //partial Overlap3
                return mid;
            }
            else if(pairModuleForSegmentTree.start>queryEnd){
                // noOverlap query is on left side of array indexes
                // mayBe it was the only greater set
                index = mid;
                right = mid - 1;
            }else if(pairModuleForSegmentTree.end<queryStart){
                // noOverlap query is on right side of array mid
                left = mid + 1;
            }
        }
        return index;
    }

    private void printList() {
        System.out.println("Print Start");
        for(int i = 0; i<this.listOfSegmentTrees.size(); i++){
            System.out.println(this.listOfSegmentTrees.get(i).start + " - "+
                    this.listOfSegmentTrees.get(i).end);
        }
        System.out.println("Print End");
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while (t > 0) {
            RangeModule rangeModule = new RangeModule();

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

                    if(operationChoice.get(i).equalsIgnoreCase("addRange"))
                        rangeModule.addRange(currOperationLeft, currOperationRight);
                    else if(operationChoice.get(i).equalsIgnoreCase("removeRange"))
                        rangeModule.removeRange(currOperationLeft, currOperationRight);
                    else if(operationChoice.get(i).equalsIgnoreCase("queryRange")){
                        boolean result = rangeModule.queryRange(currOperationLeft, currOperationRight);
                        System.out.println("Is Query Range Exist: "+result);
                    }
                }
            } else {

                //true, false, false, false
                String[] operationChoice = {"RangeModule","addRange","addRange","addRange","queryRange","queryRange","queryRange","removeRange","queryRange"};
                int[][] operationRange = {{},{10,180},{150,200},{250,500},{50,100},{180,300},{600,1000},{50,150},{50,100}};

                for (int i = 1; i<operationChoice.length; i++){
                    int left = operationRange[i][0];
                    int right = operationRange[i][1];

                    if(operationChoice[i].equalsIgnoreCase("addRange"))
                        rangeModule.addRange(left, right);
                    else if(operationChoice[i].equalsIgnoreCase("removeRange"))
                        rangeModule.removeRange(left, right);
                    else if(operationChoice[i].equalsIgnoreCase("queryRange")){
                        boolean result = rangeModule.queryRange(left, right);
                        System.out.println("Is Query Range Exist: "+result);
                    }
                }
            }
            t--;
        }
    }
}