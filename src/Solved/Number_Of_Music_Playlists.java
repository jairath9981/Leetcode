package Solved;


import java.util.Scanner;

/*
https://leetcode.com/problems/number-of-music-playlists/description/

Example 1: n = 3, goal = 3, k = 1
Input 1:
3
3
1
Output: 6
Explanation: There are 6 possible playlists: [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], and [3, 2, 1].


Input 2
2
3
0
Output: 6
Explanation: There are 6 possible playlists: [1, 1, 2], [1, 2, 1], [2, 1, 1], [2, 2, 1], [2, 1, 2], and [1, 2, 2].

Input 3
2
3
1
Output: 2
Explanation: There are 2 possible playlists: [1, 2, 1] and [2, 1, 2].


Input 4
16
16
4
Output: 789741546


Input 5
2
3
0
Output:
 */


public class Number_Of_Music_Playlists {

    public static void main(String[] args) {
        Number_Of_Music_Playlists numberOfMusicPlaylists = new Number_Of_Music_Playlists();
        Scanner input = new Scanner( System.in );
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while(t>0)
        {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int n, goal, k;
            if(choice == 1) {
                System.out.println("Enter How Many Songs In Music Player. n = ");
                n = input.nextInt();
                System.out.println("Enter Number of Songs Wants to Listen on Trip. Goal = ");
                goal = input.nextInt();
                System.out.println("After How Many Songs same Song can be Repeated. k = ");
                k = input.nextInt();
                System.out.println("Number Of Possible Playlists That can be created.  Ans = " +
                        numberOfMusicPlaylists.numMusicPlaylists(n, goal, k));
            }
            else {
                n = 3;
                goal = 3;
                k = 1;
                System.out.println("Number Of Possible Playlists That can be created. Ans = " +
                        numberOfMusicPlaylists.numMusicPlaylists(n, goal, k));
            }
            t--;
        }
    }

    public static final int MOD = 1000000007;

    public int numMusicPlaylists(int n, int goal, int k) {

//        long ans1 = numMusicPlaylistsRecursionAlgo1(0, 0, n, goal, k);
//        return (int) ans1;

        long [][] dp = new long[n+1][goal+1];
        for(int i = 0; i<n+1; i++){
            for(int j = 0; j<goal+1; j++){
                dp[i][j] = -1;
            }
        }
        long ans2 =  numMusicPlaylistsRecursionDPAlgo2(0, 0, dp, n, goal, k);
        System.out.println("ans = "+ ans2);
        return (int) ans2;

    }

    /*
        Time Complexity : O(2^(n+goal))
        Space Complexity : O(n+goal)  // recursion stack space
     */
    private long numMusicPlaylistsRecursionAlgo1(int currPlayedSong, int uniqueSong, int n, int goal, int k) {

        if(currPlayedSong == goal){
            if(uniqueSong == n){
                return 1;
            }
            return 0;
        }
        long result = 0;
        // NEW SONG
        if(n > uniqueSong) { // is my unique song left
            long uniquesAdd = (n - uniqueSong) * numMusicPlaylistsRecursionAlgo1(currPlayedSong + 1,
                    uniqueSong + 1, n, goal, k);
            result = (result + uniquesAdd % MOD) % MOD;
        }
        // REPEAT SONG
        if(uniqueSong > k){ // can I do repeat acc. to question constraint
            long repeatAdd = (uniqueSong - k) * numMusicPlaylistsRecursionAlgo1(currPlayedSong + 1,
                    uniqueSong, n, goal, k);
            result = (result + repeatAdd % MOD) % MOD;
        }
        return result % MOD;
    }


    /*
        Time Complexity : O(n*goal)
        Space Complexity : O(n*goal)  // dp array space + recursion stack space
     */
    private long numMusicPlaylistsRecursionDPAlgo2(int currPlayedSong, int uniqueSong, long[][]dp,
                                                  int n, int goal, int k) {

        if(dp[uniqueSong][currPlayedSong] != -1){
            return dp[uniqueSong][currPlayedSong];
        }

        if(currPlayedSong == goal){
            if(uniqueSong == n){
                dp[uniqueSong][currPlayedSong] = 1;
                return dp[uniqueSong][currPlayedSong];
            }
            dp[uniqueSong][currPlayedSong] = 0;
            return dp[uniqueSong][currPlayedSong];
        }

        long result = 0;
        // NEW SONG
        if(n > uniqueSong) { // is my unique song left
            long uniquesAdd =  ((n - uniqueSong) * numMusicPlaylistsRecursionDPAlgo2(currPlayedSong + 1,
                    uniqueSong + 1, dp, n, goal, k));
            result = (result + uniquesAdd % MOD) % MOD;
        }
        // REPEAT SONG
        if(uniqueSong > k){ // can I do repeat acc. to question constraint
            long repeatAdd =  ((uniqueSong - k) * numMusicPlaylistsRecursionDPAlgo2(currPlayedSong + 1,
                    uniqueSong, dp, n, goal, k));
            result = (result + repeatAdd % MOD) % MOD;
        }
        dp[uniqueSong][currPlayedSong] = result % MOD;
        return dp[uniqueSong][currPlayedSong];
    }

}
