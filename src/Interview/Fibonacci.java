package Interview;

/*
1 1 2,3 5
*/

public class Fibonacci {
    static int maxLengthFound = 0;
    static int[] dp = new int[1000];

    public static void main(String[] args) {
        int n = 5;
        createFibonacciSeres(n);
        System.out.println("*************Fibonacci Series*************");
        for (int i = 0; i < n; i++) {
            System.out.println(dp[i]);
        }

        n = 10;
        createFibonacciSeres(n);
        System.out.println("*************Fibonacci Series*************");
        for (int i = 0; i < n; i++) {
            System.out.println(dp[i]);
        }
    }

    private static void createFibonacciSeres(int n) {
        if (maxLengthFound >= n) {
            return;
        } else {
            if (maxLengthFound == 0) {
                    dp[0] = 1;
                    dp[1] = 1;
                    maxLengthFound = 2;
                }
                for (int i = maxLengthFound; i <= n; i++) {
                    int c = dp[i - 1] + dp[i - 2];
                    dp[i] = c;
                }
            maxLengthFound = n;
        }
    }
}
