package Algos_Plus_DataStructures;

import java.util.Scanner;

/*
Input 1:
ABABDABACDABABCABAB
ABABCABAB
Output : 10

ababcabcabababd
ababd
Output : 10
*/

public class KMP_Algo {
    public static void main(String[] args) {
        KMP_Algo kmp_algo = new KMP_Algo();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String str = "", pattern = "";
            if (choice == 1) {
                System.out.println("Enter Text: ");
                input.nextLine();
                str = input.nextLine();
                System.out.println("Enter Pattern Which You Want To Find In Text: ");
                pattern = input.nextLine();
                int index = kmp_algo.KMP_Algo(str, pattern);
                System.out.println("Starting Index In Text That Matched With Pattern: "+index);
            } else {
                str = "ABABDABACDABABCABAB";
                pattern = "ABABCABAB";
                int index = kmp_algo.KMP_Algo(str, pattern);
                System.out.println("Starting Index In Text That Matched With Pattern: "+index);
            }
            t--;
        }
    }

    public int KMP_Algo(String str, String pattern){

        int strIndexMatchWithPattern = -1;

        int[] lps = buildLPS_Table(pattern);
        int strIndex = 0, patternIndex = 0;

        while (strIndex<str.length() && patternIndex<pattern.length()){

            if(pattern.charAt(patternIndex) == str.charAt(strIndex)){
                strIndex++;
                patternIndex++;
            }else{
                if(patternIndex!=0) {
                    patternIndex = lps[patternIndex - 1];
                }else{
                    strIndex++;
                }
            }
        }
        if(patternIndex == pattern.length()){
            strIndexMatchWithPattern = strIndex - pattern.length();
        }
        return strIndexMatchWithPattern;
    }

    /*
        length of suffix equals to prefix
     */
    private int[] buildLPS_Table(String pattern){
        int[] lps = new int[pattern.length()];
        if(pattern.length()>0){
            lps[0] = 0;
            int suffixIndex = 1;
            int prefixIndex = 0;
            while(suffixIndex<pattern.length()){
                if(pattern.charAt(suffixIndex) == pattern.charAt(prefixIndex)){
                    lps[suffixIndex] = prefixIndex + 1; // we are maintaining length that's why + 1
                    prefixIndex++;
                    suffixIndex++;
                }else{
                    if(prefixIndex!=0){
                        /*
                            break down of prefix into prefix suffix
                         */
                        prefixIndex = lps[prefixIndex - 1];
                    }else{
                        lps[suffixIndex] = prefixIndex; //0;
                        suffixIndex++;
                    }
                }
            }
        }
        return lps;
    }
}
