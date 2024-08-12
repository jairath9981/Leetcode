package Interview;

public class MaxEvenSum {
    public static void main(String[]args) {
        int[] arr = {-1, -2, -3, 8, 7};
        int ans = maxEvenSum(arr);
        System.out.println(ans);
    }
    public static int maxEvenSum(int []arr){
        int allPositiveNumberSum = 0;
        int lowestOddNum = Integer.MAX_VALUE;
        int flag = 0;
        int allSum = 0;
        for(int i = 0; i<arr.length; i++){
            allSum+=arr[i];
            if(arr[i]>0) {
                //System.out.println("number added = "+arr[i]);
                allPositiveNumberSum += arr[i];
            }
            int num = Math.abs(arr[i]);
            if(num%2 == 1 && lowestOddNum>num)
                lowestOddNum = num;
            if(arr[i]>=0){
                flag = 1;
            }
        }
        if(flag == 0){
            int num = Math.abs(allSum);
            if(num%2 == 0)
                return allSum;
            else
                return allSum + lowestOddNum;
        }
        //System.out.println("sum = "+sum);
        double x = Math.random();
        if(allPositiveNumberSum%2==0)
            return allPositiveNumberSum;
        else
            return allPositiveNumberSum - lowestOddNum;


    }
}

