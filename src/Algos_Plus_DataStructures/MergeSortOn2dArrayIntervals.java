package Algos_Plus_DataStructures;

/*

 */
public class MergeSortOn2dArrayIntervals {
    public static void main(String[] args) {
        int [][]arr = {{1, 9}, {1, 18}, {19, 24}, {25, 30}, {56, 60}, {0, 7}};

        MergeSortOn2dArrayIntervals mergeSort = new MergeSortOn2dArrayIntervals();
        mergeSort.mergeSortDivideAndConquer(arr, 0, arr.length-1);

        mergeSort.printArray(arr);
    }

    public void mergeSortDivideAndConquer(int[][]arr, int left, int right){
        if(left<right){
            int mid = left + (right - left)/2;
            int x = mid+1;
//            System.out.println("left-right "+left+"-"+right);
//            System.out.println("left-mid "+left+"-"+mid);
//            System.out.println("mid-right "+x+"-"+right);
            mergeSortDivideAndConquer(arr, left, mid);
            mergeSortDivideAndConquer(arr, mid+1, right);

            sort2SortedArrays(arr, left, mid, right);
        }
    }

    private void sort2SortedArrays(int[][] arr, int left, int mid, int right) {
//        System.out.println("Sort 2 sorted arrays "+left+"  "+mid+"  "+right);
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[][]leftArr = new int[n1][2];
        int[][]rightArr = new int[n2][2];

        for (int i = 0; i<n1; i++) {
            leftArr[i][0] = arr[left+i][0];
            leftArr[i][1] = arr[left+i][1];
        }

        for (int i = 0; i<n2; i++) {
            rightArr[i][0] = arr[mid+1+i][0];
            rightArr[i][1] = arr[mid+1+i][1];
        }

        int i = 0,  j = 0;
        int k = left;
        while(i < n1 && j < n2){
//            System.out.println("left= "+leftArr[i][0]+"   "+leftArr[i][1]);
//            System.out.println("right= "+rightArr[j][0]+"   "+rightArr[j][1]);
            if(leftArr[i][0]<rightArr[j][0]){
                arr[k][0] = leftArr[i][0];
                arr[k][1] = leftArr[i][1];
                i++;
            }
            else if(leftArr[i][0] == rightArr[j][0] && leftArr[i][1]<rightArr[j][1]){
                arr[k][0] = rightArr[j][0];
                arr[k][1] = rightArr[j][1];
                j++;
            }else if(leftArr[i][0]>rightArr[j][0]){
                arr[k][0] = rightArr[j][0];
                arr[k][1] = rightArr[j][1];
                j++;
            }
            else if(leftArr[i][0] == rightArr[j][0] && leftArr[i][1]>rightArr[j][1]){
                arr[k][0] = leftArr[i][0];
                arr[k][1] = leftArr[i][1];
                i++;
            }
            k++;
        }
//        System.out.println("End Of first While Loop");
        while(i < n1){
            arr[k][0] = leftArr[i][0];
            arr[k][1] = leftArr[i][1];
            i++;
            k++;
        }
        while(j < n2){
            arr[k][0] = rightArr[j][0];
            arr[k][1] = rightArr[j][1];
            j++;
            k++;
        }
    }


    private void printArray(int[][] arr) {
        System.out.println("Print Array");
        for(int i = 0; i<arr.length; i++)
            System.out.print(arr[i][0]+" - "+arr[i][1]+",   ");
        System.out.println();
    }
}
