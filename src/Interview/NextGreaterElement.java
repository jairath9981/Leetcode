package Interview;

import java.util.Stack;

/*
4 5   2  25
5 25  25 -1

8  7  6  5   4
-1 -1 -1 -1 -1

1 2 3  4  5
2 3 4  5 -1
*/

public class NextGreaterElement {

    public static void main(String[] args) {
        int []arr1 = {4,5,2,25};
        int []arr = {13,7,6,12};
        int []arr3 = {8, 7, 6, 5, 4};
        int []arr4 = {1, 2, 3, 4, 5};

        NextGreaterElement solution1 = new NextGreaterElement();
        int []nextGreaterArr = solution1.findNextGreater(arr);

        for (int i = 0; i<nextGreaterArr.length; i++){
            System.out.println(arr[i] + "-->"+ nextGreaterArr[i]);
        }
    }

    public int[] findNextGreater(int[] arr) {
        int []nextGreaterArr = new int[arr.length];
        for (int i = 0; i<arr.length; i++)
            nextGreaterArr[i] = -1;

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i<arr.length; i++){
            if(stack.isEmpty() || arr[i]<=arr[stack.peek()]){
                stack.add(i);
            }else{
                while(!stack.isEmpty() && arr[i]>arr[stack.peek()]){
                    int index = stack.pop();
                    nextGreaterArr[index] = arr[i];
                }
                stack.add(i);
            }
        }
        return nextGreaterArr;
    }
}
