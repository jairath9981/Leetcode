package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
https://leetcode.com/problems/sliding-window-median/

Input:
1 3 -1 -3 5 3 6 7 -999
3
Input meaning nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [1.00000,-1.00000,-1.00000,3.00000,5.00000,6.00000]
Explanation:
Window position                Median
---------------                -----
[1  3  -1] -3  5  3  6  7        1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7        3
 1  3  -1  -3 [5  3  6] 7        5
 1  3  -1  -3  5 [3  6  7]       6

Input:
1 2 3 4 2 3 1 4 2 -999
3
Output: [2.00000,3.00000,3.00000,3.00000,2.00000,3.00000,2.00000]

Input:
1 4 2 3 -999
4
Output: [2.50000]

Input:
5 2 2 7 3 7 9 0 2 3 -999
9
[3.00000,3.00000]
 */

class MinHeap_Sliding_Window_Median{
    List<Integer>minHeap;
    int currArrayIndex;
    int capacity;
    String minHeapMessage = "MinH: ";
    MinHeap_Sliding_Window_Median(int size){
        this.capacity = size;
        this.minHeap = new ArrayList<>(size);
        this.currArrayIndex = -1;
    }

    private int getParent(int index){
        return (index -1)/2;
    }

    private int getLeftChild(int index){
        return (2*index)+1;
    }

    private int getRightChild(int index){
        return (2*index)+2;
    }

    public void insertKey(int givenIndex, int[] nums,
                      Map<Integer, String> givenIndexToHeapIndexMapping){
        if(this.currArrayIndex<this.capacity-1){
            if(this.minHeap.size()<=this.currArrayIndex+1){
                this.currArrayIndex++;
                this.minHeap.add(givenIndex);
            }
            else{
                this.currArrayIndex++;
                this.minHeap.set(this.currArrayIndex, givenIndex);
            }
            addIndexInMap(givenIndex, this.currArrayIndex, givenIndexToHeapIndexMapping);
            maintainBottomToTopHeap(this.currArrayIndex, nums, givenIndexToHeapIndexMapping);
        }
        else{
            System.out.println("Something Went Wrong Min Heap Reach To Its Max " +
                    "Capacity Please Delete Some Value Or Increase Min Heap Size");
        }
    }

    private void maintainBottomToTopHeap(int heapIndex, int[] nums,
                                         Map<Integer, String> givenIndexToHeapIndexMapping) {
        int heapIndexParent = getParent(heapIndex);
        while(heapIndex!=0 && (this.minHeap.get(heapIndex) == Integer.MIN_VALUE ||
                nums[this.minHeap.get(heapIndexParent)]>nums[this.minHeap.get(heapIndex)])){
            int parentPrevValue = this.minHeap.get(heapIndexParent);
            int indexPrevValue = this.minHeap.get(heapIndex);
            swap(heapIndex, heapIndexParent);
            addIndexInMap(indexPrevValue, heapIndexParent, givenIndexToHeapIndexMapping);
            addIndexInMap(parentPrevValue, heapIndex, givenIndexToHeapIndexMapping);
            heapIndex = heapIndexParent;
            heapIndexParent = getParent(heapIndex);
        }
    }

    private void swap(int a, int b) {
        int temp = this.minHeap.get(a);
        this.minHeap.set(a, this.minHeap.get(b));
        this.minHeap.set(b, temp);
    }

    private void addIndexInMap(int key, int value,
                               Map<Integer, String> givenIndexToHeapIndexMapping ){
        givenIndexToHeapIndexMapping.put(key, this.minHeapMessage+value);
    }

    private void removeIndexFromMap(int key,
                               Map<Integer, String> givenIndexToHeapIndexMapping ){
        givenIndexToHeapIndexMapping.remove(key);
    }

    public void delete(int heapIndex, int[] nuns,
                  Map<Integer, String> givenIndexToHeapIndexMapping){
        if(heapIndex<=this.currArrayIndex){
            decreaseKey(heapIndex, Integer.MIN_VALUE, nuns, givenIndexToHeapIndexMapping);
            extractMin(nuns, givenIndexToHeapIndexMapping);
        }
        else{
            System.out.println(heapIndex+" Index Is Not Present in Min Heap");
        }
    }

    public int extractMin(int[] nums,
                  Map<Integer, String> givenIndexToHeapIndexMapping) {
        if(this.currArrayIndex<0) {
            System.out.println("Min Heap Is Empty");
            return Integer.MAX_VALUE;
        }else{
            if(this.currArrayIndex == 0) {
                int rootValue = this.minHeap.get(0);
                this.currArrayIndex--;
                removeIndexFromMap(rootValue, givenIndexToHeapIndexMapping);
                return rootValue;
            }
            else{
                int rootValue = this.minHeap.get(0);
                // put last element in root
                this.minHeap.set(0, minHeap.get(this.currArrayIndex));
                removeIndexFromMap(rootValue, givenIndexToHeapIndexMapping);
                addIndexInMap(this.minHeap.get(0), 0, givenIndexToHeapIndexMapping);
                // remove last element now
                this.currArrayIndex--;
                /*  now heapify from top to bottom(from root) as we randomly put last index
                    in root(max value in min heap)
                */
                maintainTopToBottomHeap(0, nums, givenIndexToHeapIndexMapping);
                return rootValue;
            }
        }
    }

    private void maintainTopToBottomHeap(int heapIndex, int[] nums,
                  Map<Integer, String> givenIndexToHeapIndexMapping) {
        int left = getLeftChild(heapIndex);
        int right = getRightChild(heapIndex);
        int min = getMin(left, right, nums);
        if(min!=-1 && nums[this.minHeap.get(heapIndex)]>nums[this.minHeap.get(min)]){
            int minPrevValue = this.minHeap.get(min);
            int heapIndexPrevValue = this.minHeap.get(heapIndex);
            swap(min, heapIndex);
            addIndexInMap(minPrevValue, heapIndex, givenIndexToHeapIndexMapping);
            addIndexInMap(heapIndexPrevValue, min, givenIndexToHeapIndexMapping);
            maintainTopToBottomHeap(min, nums, givenIndexToHeapIndexMapping);
        }
    }

    private int getMin(int left, int right, int[] nums) {
        int a = -1;
        int b = -1;
        if(left<=this.currArrayIndex)
            a = this.minHeap.get(left);
        if(right<=this.currArrayIndex)
            b = this.minHeap.get(right);
        if(a == -1 && b == -1)
            return a;
        if(a == -1)
            return right;
        if(b == -1)
            return left;
        if(nums[a]<=nums[b])
            return left;
        else
            return right;
    }

    private void decreaseKey(int heapIndex, int minValue, int[] nums,
                   Map<Integer, String> givenIndexToHeapIndexMapping) {
        int prevIndex = this.minHeap.get(heapIndex);
        this.minHeap.set(heapIndex, minValue);
        removeIndexFromMap(prevIndex, givenIndexToHeapIndexMapping);
        addIndexInMap(this.minHeap.get(heapIndex), heapIndex, givenIndexToHeapIndexMapping);
        maintainBottomToTopHeap(heapIndex, nums, givenIndexToHeapIndexMapping);
    }

    public int size() {
        return this.currArrayIndex+1;
    }

    public int getMin(){
        return this.minHeap.get(0);
    }

    public void printHeapArray() {
        System.out.println("Print Min Heap Array");
        for(int i = 0; i<=this.currArrayIndex; i++)
            System.out.print(this.minHeap.get(i)+", ");
        System.out.println();
    }
}

class MaxHeap_Sliding_Window_Median{
    List<Integer> maxHeap;
    int currArrayIndex;
    int capacity;
    String maxHeapMessage = "MaxH: ";
    MaxHeap_Sliding_Window_Median(int size){
        this.capacity = size;
        this.maxHeap = new ArrayList<>(size);
        this.currArrayIndex = -1;
    }

    private int getParent(int index){
        return (index -1)/2;
    }

    private int getLeftChild(int index){
        return (2*index)+1;
    }

    private int getRightChild(int index){
        return (2*index)+2;
    }

    public void insertKey(int givenIndex, int[] nums,
                          Map<Integer, String> givenIndexToHeapIndexMapping){
        if(this.currArrayIndex<this.capacity-1){
            if(this.maxHeap.size()<=this.currArrayIndex+1){
                this.currArrayIndex++;
                this.maxHeap.add(givenIndex);
            }
            else{
                this.currArrayIndex++;
                this.maxHeap.set(this.currArrayIndex, givenIndex);
            }
            addIndexInMap(givenIndex, this.currArrayIndex, givenIndexToHeapIndexMapping);
            maintainBottomToTopHeap(this.currArrayIndex, nums, givenIndexToHeapIndexMapping);
        }
        else{
            System.out.println("Something Went Wrong Max Heap Reach To Its Max " +
                    "Capacity Please Delete Some Value Or Increase Max Heap Size");
        }
    }

    private void maintainBottomToTopHeap(int heapIndex, int[] nums,
                                         Map<Integer, String> givenIndexToHeapIndexMapping) {
        int heapIndexParent = getParent(heapIndex);
        while(heapIndex!=0 && (this.maxHeap.get(heapIndex) == Integer.MAX_VALUE ||
                nums[this.maxHeap.get(heapIndexParent)]<nums[this.maxHeap.get(heapIndex)])){
            int parentPrevValue = this.maxHeap.get(heapIndexParent);
            int indexPrevValue = this.maxHeap.get(heapIndex);
            swap(heapIndex, heapIndexParent);
            addIndexInMap(indexPrevValue, heapIndexParent, givenIndexToHeapIndexMapping);
            addIndexInMap(parentPrevValue, heapIndex, givenIndexToHeapIndexMapping);
            heapIndex = heapIndexParent;
            heapIndexParent = getParent(heapIndex);
        }
    }

    private void swap(int a, int b) {
        int temp = this.maxHeap.get(a);
        this.maxHeap.set(a, this.maxHeap.get(b));
        this.maxHeap.set(b, temp);
    }

    private void addIndexInMap(int key, int value,
                               Map<Integer, String> givenIndexToHeapIndexMapping ){
        givenIndexToHeapIndexMapping.put(key, this.maxHeapMessage +value);
    }

    private void removeIndexFromMap(int key,
                                    Map<Integer, String> givenIndexToHeapIndexMapping ){
        givenIndexToHeapIndexMapping.remove(key);
    }

    public void delete(int heapIndex, int[] nuns,
                       Map<Integer, String> givenIndexToHeapIndexMapping){
        if(heapIndex<=this.currArrayIndex){
            increaseKey(heapIndex, Integer.MAX_VALUE, nuns, givenIndexToHeapIndexMapping);
            extractMax(nuns, givenIndexToHeapIndexMapping);
        }
        else{
            System.out.println(heapIndex+" Index Is Not Present in Max Heap");
        }
    }

    public int extractMax(int[] nums,
                           Map<Integer, String> givenIndexToHeapIndexMapping) {
        if(this.currArrayIndex<0) {
            System.out.println("Max Heap Is Empty");
            return Integer.MAX_VALUE;
        }else{
            if(this.currArrayIndex == 0) {
                int rootValue = this.maxHeap.get(0);
                this.currArrayIndex--;
                removeIndexFromMap(rootValue, givenIndexToHeapIndexMapping);
                return rootValue;
            }
            else{
                int rootValue = this.maxHeap.get(0);
                // put last element in root
                this.maxHeap.set(0, maxHeap.get(this.currArrayIndex));
                removeIndexFromMap(rootValue, givenIndexToHeapIndexMapping);
                addIndexInMap(this.maxHeap.get(0), 0, givenIndexToHeapIndexMapping);
                // remove last element now
                this.currArrayIndex--;
                /*  now heapify from top to bottom(from root) as we randomly put last index
                    in root(min value in max heap)
                */
                maintainTopToBottomHeap(0, nums, givenIndexToHeapIndexMapping);
                return rootValue;
            }
        }
    }

    private void maintainTopToBottomHeap(int heapIndex, int[] nums,
                                         Map<Integer, String> givenIndexToHeapIndexMapping) {
        int left = getLeftChild(heapIndex);
        int right = getRightChild(heapIndex);
        int max = getMax(left, right, nums);
        if(max!=-1 && nums[this.maxHeap.get(heapIndex)]<nums[this.maxHeap.get(max)]){
            int maxPrevValue = this.maxHeap.get(max);
            int heapIndexPrevValue = this.maxHeap.get(heapIndex);
            swap(max, heapIndex);
            addIndexInMap(maxPrevValue, heapIndex, givenIndexToHeapIndexMapping);
            addIndexInMap(heapIndexPrevValue, max, givenIndexToHeapIndexMapping);
            maintainTopToBottomHeap(max, nums, givenIndexToHeapIndexMapping);
        }
    }

    private int getMax(int left, int right, int[] nums) {
        int a = -1;
        int b = -1;
        if(left<=this.currArrayIndex)
            a = this.maxHeap.get(left);
        if(right<=this.currArrayIndex)
            b = this.maxHeap.get(right);
        if(a == -1 && b == -1)
            return a;
        if(a == -1)
            return right;
        if(b == -1)
            return left;
        else{
            if(nums[a]<=nums[b])
                return right;
            else
                return left;
        }
    }

    private void increaseKey(int heapIndex, int maxValue, int[] nums,
                             Map<Integer, String> givenIndexToHeapIndexMapping) {
        int prevIndex = this.maxHeap.get(heapIndex);
        this.maxHeap.set(heapIndex, maxValue);
        removeIndexFromMap(prevIndex, givenIndexToHeapIndexMapping);
        addIndexInMap(this.maxHeap.get(heapIndex), heapIndex, givenIndexToHeapIndexMapping);
        maintainBottomToTopHeap(heapIndex, nums, givenIndexToHeapIndexMapping);
    }

    public int size() {
        return this.currArrayIndex+1;
    }

     public int getMax(){
        return this.maxHeap.get(0);
     }

    public void printHeapArray() {
        System.out.println("Print Max Heap Array");
        for(int i = 0; i<=this.currArrayIndex; i++)
            System.out.print(this.maxHeap.get(i)+", ");
        System.out.println();
    }
}

public class Sliding_Window_Median {
    public static void main(String []args) {
        Sliding_Window_Median sliding_window_median = new Sliding_Window_Median();
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
            if(choice == 1)
            {
                int x;
                List<Integer> listOfNumbers = new ArrayList<>();
                System.out.println("Enter Your Integer Array, For Stop Insertion Press -999");
                x = input.nextInt();
                while(x!=-999)
                {
                    listOfNumbers.add(x);
                    x = input.nextInt();
                }
                System.out.println("Enter Window Size");
                int k =  input.nextInt();
                int[] nums = listOfNumbers.stream().mapToInt(i->i).toArray();
                double[] medians = sliding_window_median.medianSlidingWindow(nums, k);
                System.out.println("Respective Medians Of Integer Array for Window Size = "+k);
                printArray(medians);
            }
            else
            {
                int[] nums = {1,3,-1,-3,5,3,6,7};
                int k = 3;
                double[] medians = sliding_window_median.medianSlidingWindow(nums, k);
                System.out.println("Respective Medians Of Integer Array for Window Size = "+k);
                printArray(medians);
            }
            t--;
        }
    }

    private static void printArray(double[] medians) {
        for(int i = 0; i<medians.length; i++)
            System.out.print(medians[i]+", ");
        System.out.println();
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        if(k>0 && k<=nums.length){
            int heapCapacity = calculateHeapCapacityRequire(k)+1;
            MinHeap_Sliding_Window_Median minHeap_sliding_window_median =
                    new MinHeap_Sliding_Window_Median(heapCapacity);
            MaxHeap_Sliding_Window_Median maxHeap_sliding_window_median =
                    new MaxHeap_Sliding_Window_Median(heapCapacity);
            Map<Integer, String> givenIndexToHeapIndexMapping = new HashMap<>();

            double[] median = new double[nums.length - k + 1];

            for(int i = 0; i<k; i++) {
                addInHeap(i, nums, givenIndexToHeapIndexMapping, minHeap_sliding_window_median,
                        maxHeap_sliding_window_median);
            }
            double medianValue = calculateMedian(maxHeap_sliding_window_median,
                    minHeap_sliding_window_median, nums);
            median[0] = medianValue;
            for(int i = k; i<nums.length; i++){
                removeFromHeap(i-k, nums, givenIndexToHeapIndexMapping, minHeap_sliding_window_median,
                        maxHeap_sliding_window_median);
                addInHeap(i, nums, givenIndexToHeapIndexMapping, minHeap_sliding_window_median,
                        maxHeap_sliding_window_median);

                medianValue = calculateMedian(maxHeap_sliding_window_median,
                        minHeap_sliding_window_median, nums);
                int index = i - k + 1;
                median[index] = medianValue;
                //System.out.println(index+" medianValue = "+medianValue);
            }
            return median;
        }
        else{
            System.out.println("Please check Array or k value");
            return null;
        }
    }

    private double calculateMedian(MaxHeap_Sliding_Window_Median maxHeap_sliding_window_median,
                                   MinHeap_Sliding_Window_Median minHeap_sliding_window_median,
                                   int[] nums) {
        if(maxHeap_sliding_window_median.size()>minHeap_sliding_window_median.size())
            return nums[maxHeap_sliding_window_median.getMax()];
        else{   // if(maxHeap_sliding_window_median.size()==minHeap_sliding_window_median.size())
            double a  = nums[maxHeap_sliding_window_median.getMax()];
            double b = nums[minHeap_sliding_window_median.getMin()];
            return (a + b)/2;
        }
    }

    private void addInHeap(int index, int[] nums,
                   Map<Integer, String> givenIndexToHeapIndexMapping,
                   MinHeap_Sliding_Window_Median minHeap_sliding_window_median,
                   MaxHeap_Sliding_Window_Median maxHeap_sliding_window_median) {

        maxHeap_sliding_window_median.insertKey(index, nums, givenIndexToHeapIndexMapping);
        int indexForBalancing = maxHeap_sliding_window_median.extractMax(nums, givenIndexToHeapIndexMapping);
        minHeap_sliding_window_median.insertKey(indexForBalancing, nums, givenIndexToHeapIndexMapping);
        balancing(nums, maxHeap_sliding_window_median, minHeap_sliding_window_median, givenIndexToHeapIndexMapping);
    }

    private void balancing(int[] nums, MaxHeap_Sliding_Window_Median maxHeap_sliding_window_median,
                   MinHeap_Sliding_Window_Median minHeap_sliding_window_median,
                          Map<Integer, String> givenIndexToHeapIndexMapping) {
        if(maxHeap_sliding_window_median.size()==minHeap_sliding_window_median.size()){}
        else if(maxHeap_sliding_window_median.size()>minHeap_sliding_window_median.size()+1){
            int indexForBalancing = maxHeap_sliding_window_median.extractMax(nums, givenIndexToHeapIndexMapping);
            minHeap_sliding_window_median.insertKey(indexForBalancing, nums, givenIndexToHeapIndexMapping);
        }
        else if(maxHeap_sliding_window_median.size()<minHeap_sliding_window_median.size()){

            int indexForBalancing = minHeap_sliding_window_median.extractMin(nums, givenIndexToHeapIndexMapping);
            maxHeap_sliding_window_median.insertKey(indexForBalancing, nums, givenIndexToHeapIndexMapping);
        }
    }

    private void removeFromHeap(int index, int[] nums, Map<Integer, String> givenIndexToHeapIndexMapping,
                   MinHeap_Sliding_Window_Median minHeap_sliding_window_median,
                   MaxHeap_Sliding_Window_Median maxHeap_sliding_window_median) {

        //System.out.println("index: "+index+" givenIndexToHeapIndexMapping = "+givenIndexToHeapIndexMapping);
        String message = givenIndexToHeapIndexMapping.get(index);
        if(message.startsWith(maxHeap_sliding_window_median.maxHeapMessage)){
            String heapIndexStr = message.substring(maxHeap_sliding_window_median.maxHeapMessage.length());
            int heapIndex = Integer.parseInt(heapIndexStr);
            //System.out.println("Max heapIndexStr = "+heapIndexStr);

            maxHeap_sliding_window_median.delete(heapIndex, nums, givenIndexToHeapIndexMapping);
        }else if(message.startsWith(minHeap_sliding_window_median.minHeapMessage)){
            String heapIndexStr = message.substring(minHeap_sliding_window_median.minHeapMessage.length());
            int heapIndex = Integer.parseInt(heapIndexStr);
            //System.out.println("Min heapIndexStr = "+heapIndexStr);

            minHeap_sliding_window_median.delete(heapIndex, nums, givenIndexToHeapIndexMapping);
        }
        balancing(nums, maxHeap_sliding_window_median, minHeap_sliding_window_median, givenIndexToHeapIndexMapping);
    }

    private int calculateHeapCapacityRequire(double k) {
        return (int)Math.ceil(k/2);
    }
}
