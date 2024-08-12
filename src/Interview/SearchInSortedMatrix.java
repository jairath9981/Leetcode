package Interview;

class Position_SearchInSortedMatrix{
    int i;
    int j;
}

public class SearchInSortedMatrix {
    public static void main(String[]args){
        SearchInSortedMatrix searchInSortedMatrix = new SearchInSortedMatrix();
        int [][]mat = { {1, 5, 9, 11},
                {14, 20, 21, 26},
                {30, 34, 43, 50} };
        Position_SearchInSortedMatrix position_searchInSortedMatrix =
                searchInSortedMatrix.findNum(mat, 1);
        if(position_searchInSortedMatrix.i ==-1){
            System.out.println("Target Number is Not In Matrix");
        }else{
            System.out.println("Target Number Index: "+position_searchInSortedMatrix.i+"   "+
                    position_searchInSortedMatrix.j);
        }
    }

    public Position_SearchInSortedMatrix findNum(int[][]mat, int target){
        Position_SearchInSortedMatrix position_searchInSortedMatrix =
                new Position_SearchInSortedMatrix();
        position_searchInSortedMatrix.i = -1;
        position_searchInSortedMatrix.j = -1;
        for(int i = 0; i<mat.length; i++){
            int index = binarySearch(mat[i], target);
            if(index!=-1){
                position_searchInSortedMatrix.i = i;
                position_searchInSortedMatrix.j = index;
                break;
            }
        }
        return position_searchInSortedMatrix;
    }

    private int binarySearch(int []arr, int target){
        int left = 0;
        int right = arr.length-1;
        int index = -1;
        while(left<=right){
            int mid = left + (right-left)/2;
            if(arr[mid]>target){
                right = mid-1;
            }else if(arr[mid]<target){
                left = mid+1;
            }else{
                index = mid;
                break;
            }
        }
        return index;
    }
}

