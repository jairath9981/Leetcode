package Solved;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/cracking-the-safe/

Input 1:
1
2
Input 1 meaning: n = 1, k = 2
Output : "10"
Explanation: The password is a single digit, so enter each digit. "01" would also unlock the safe.

Input 2:
2
2
Output: "01100"
Explanation: For each possible password:
- "00" is typed in starting from the 4th digit.
- "01" is typed in starting from the 1st digit.
- "10" is typed in starting from the 3rd digit.
- "11" is typed in starting from the 2nd digit.
Thus "01100" will unlock the safe. "01100", "10011", and "11001" would also unlock the safe.
*/


public class CrackingTheSafe {
    public static void main(String[] args) {
        CrackingTheSafe crackingTheSafe = new CrackingTheSafe();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int n, k;
            if (choice == 1) {
                System.out.println("Enter Password Length = ");
                n = input.nextInt();
                System.out.println("Enter Password Digit Range k[0, k-1] = ");
                k = input.nextInt();
                String passwordTryMinLength = crackingTheSafe.crackSafe(n, k);
                System.out.println("Password With Min Length = "+passwordTryMinLength);
            }
            else {
                n = 3;
                k = 3;
                String passwordTryMinLength = crackingTheSafe.crackSafe(n, k);
                System.out.println("Password With Min Length = "+passwordTryMinLength);
            }
            t--;
        }
    }

    public String crackSafe(int n, int k) {
        if(k == 1 && n == 1)
            return "0";
        else{
            StringBuilder result = new StringBuilder();
            Set<String> visited = new HashSet<>();

            String start = "";
            for(int i = 0; i<n-1; i++){
                start+="0";
            }

            dfs(start, result, k, visited);
            result.append(start);
            return result.toString();
        }
    }

    private void dfs(String node, StringBuilder result, int k, Set<String> visited) {
        for(int i = 0; i<k; i++){
            String neighbour = node + i;
            if(!visited.contains(neighbour)){
                visited.add(neighbour);
                dfs(neighbour.substring(1), result, k, visited);
                result.append(i);
            }
        }
    }

}
