package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/count-different-palindromic-subsequences/

Input 1:
bccb
Input Meaning: s = "bccb"
Output: 6
Explanation: The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
Note that 'bcb' is counted only once, even though it occurs twice.

Input 2:
abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba
Output: 104860361
Explanation: There are 3104860382 different non-empty palindromic subsequences, which is 104860361
modulo 109 + 7.

Input 3: (confuse in int or long and Mod)
dddcabadcbabccdadccbcabcdacdadcbbbcadaabcddccbcadaddbdbdacbcccddabbbcbcdccdaadabadacacbdbbbadcdaaabb
Output: 539524363

Input 4: (confuse in int or long and Mod)
baaddaaabaddccbbbdcbcccbdbdabdabdbadabddbbcbbcabbccdaccdbcbbcdcdbaadbcadacabcaaaadbcaddbbacddcdabaadcacacdcabaadacadcccdcbbcdabdcdacaacdcdbdacbdbcdcbaddaccabaaaabcadacdaddbcccbcdbadbdddaaabbdbdbcbcdab
Output: 431300010

Input 5: (confuse in int or long and Mod)
bcbacbabdcbcbdcbddcaaccdcbbcdbcabbcdddadaadddbdbbbdacbabaabdddcaccccdccdbabcddbdcccabccbbcdbcdbdaada
Output: 117990582
*/

class CharacterInfo{
    List<Integer> indexes;
    CharacterInfo(){
        this.indexes = new ArrayList<>();
    }
}

class BuildAnswerForCurrentLength{
    char character;
    int start;
    int end;
    int length;
    int flagStart;
}

public class Count_Different_Palindromic_Subsequences {
    public static void main(String[] args) {
        Count_Different_Palindromic_Subsequences count_different_palindroms =
                new Count_Different_Palindromic_Subsequences();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String s;
            int n1, n2;
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter String s For Which You Want To Find All Palindromic = ");
                s = input.nextLine();
                int ans = count_different_palindroms.countPalindromicSubsequences(s);

                System.out.println("Max Number Of Palindromic Sub-Sequence: "+ ans);
                System.out.println();
            }
            else {
                s = "bccb";
                int ans = count_different_palindroms.countPalindromicSubsequences(s);

                System.out.println("Max Number Of Palindromic Sub-Sequence: "+ ans);
                System.out.println();
            }
            t--;
        }
    }

    public static final int mod = 1000000007;
    public int countPalindromicSubsequences(String s) {
        if(s.length() <=2)
            return s.length();
        List<CharacterInfo> listOfCharacterInfo = buildCharacterInfo(s);

        long[][]dp = initializeDp(s.length());;
        buildDp(dp, listOfCharacterInfo, s);

        return (int) (dp[0][s.length()-1]%mod);
    }

    private void buildDp(long[][] dp,
                         List<CharacterInfo> listOfCharacterInfo, String str) {

        int maxLen = str.length();
        for (int len = 1; len<=maxLen; len++) {
            int i = 0;
            for (int j = i + len - 1; j <maxLen ; j++) {
                if(i == j){ // len = 1
                    dp[i][j] = 1;
                }else{

                    if(str.charAt(i) == str.charAt(j)){
                        char c = str.charAt(j);
                        int indexOfCharCImmediatePreviousToJButAfterI =
                                getIndexFromLast(i, j, c, listOfCharacterInfo);
                        if(indexOfCharCImmediatePreviousToJButAfterI == -1){
                            dp[i][j] = dp[i][j-1] + dp[i+1][j-1] + 1;
                        }else{
                            dp[i][j] = dp[i][j-1] + dp[i+1][j-1] -
                                dp[i+1][indexOfCharCImmediatePreviousToJButAfterI-1];
                        }

                    }else{
                        char c = str.charAt(j);
                        int indexOfCharCFromStarButtLessThanJGreaterThenI =
                                getIndexFromStart(i, j, c, listOfCharacterInfo);
                        if(indexOfCharCFromStarButtLessThanJGreaterThenI == -1){
                            dp[i][j] = dp[i][j-1] + dp[j][j];
                        }else{
                            dp[i][j] = dp[i][j-1] +
                                dp[indexOfCharCFromStarButtLessThanJGreaterThenI][j] -
                                dp[indexOfCharCFromStarButtLessThanJGreaterThenI][j-1];
                        }
                    }
                }
                //most important line
                dp[i][j] = dp[i][j]<0? dp[i][j] + mod:dp[i][j] % mod;
                i++;
            }
        }
    }

    private int getIndexFromStart(int start, int end, char c,
                                  List<CharacterInfo> listOfCharacterInfo) {
        int ascii = c;
        int indexInList = ascii-97;
        List<Integer> listOfIndexesOfC = listOfCharacterInfo.get(indexInList).indexes;
        int indexInIndexList =  binarySearchJustGreaterThenX(listOfIndexesOfC, start);
        if(indexInIndexList!=-1){
            int reqIndexOfC = listOfIndexesOfC.get(indexInIndexList);
            if(reqIndexOfC<end)
                return reqIndexOfC;
        }
        return -1;
    }

    private int getIndexFromLast(int start, int end, char c,
                                 List<CharacterInfo> listOfCharacterInfo) {
        int ascii = c;
        int indexInList = ascii-97;
        List<Integer> listOfIndexesOfC = listOfCharacterInfo.get(indexInList).indexes;
        int indexInIndexList =  binarySearch(listOfIndexesOfC, end);
        if(indexInIndexList  != 0 && indexInIndexList != -1) {
            int reqIndexOfC = listOfIndexesOfC.get(indexInIndexList-1);
            if(reqIndexOfC>start)
                return reqIndexOfC;
        }
        return -1;
    }

    private int binarySearch(List<Integer> list, int x){
        int left = 0;
        int right = list.size() - 1;
        int index = -1;
        while (left<=right){
            int mid = left+(right-left)/2;
            if(list.get(mid) == x){
                return mid;
            }else if(list.get(mid)<x){
                left = mid+1;
            }else{
                right = mid - 1;
            }
        }
        return index;
    }

    private int binarySearchJustGreaterThenX(List<Integer> list, int x) {
        int left = 0;
        int right = list.size() - 1;
        int index = -1;
        while (left<=right){
            int mid = left+(right-left)/2;
            if(list.get(mid) == x){
                left = mid+1;
            }else if(list.get(mid)>x){
                index = mid;
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return index;
    }


    private long[][] initializeDp(int n) {
        long[][]dp = new long[n][n];
        for(int i = 0; i<n; i++){
            for (int j = 0; j<n; j++){
                dp[i][j] = 0;
            }
        }
        return dp;
    }

    private List<CharacterInfo> buildCharacterInfo(String s) {
        List<CharacterInfo> listOfCharacterInfo = new ArrayList<>();
        for (int i = 0; i<4; i++)
            listOfCharacterInfo.add(new CharacterInfo());

        for(int i = 0; i<s.length(); i++){
            char c = s.charAt(i);
            int ascii = c;
            int index = ascii-97;

            listOfCharacterInfo.get(index).indexes.add(i);
        }
        return listOfCharacterInfo;
    }

    private void printDp(long[][] dp) {
        System.out.println("START PRINTING DP[][]");
        for (int i = 0; i<dp.length; i++){
            for (int j = 0; j<dp[i].length; j++){
                System.out.print(dp[i][j]+", ");
            }
            System.out.println();
        }
        System.out.println("END PRINTING DP[][]");
    }
}

/*
Logic1:
    public int countPalindromicSubsequences(String s) {
        List<List<Set<String>>>dp = new ArrayList<>();

        for (int i = 0; i<s.length(); i++) {
            List<Set<String>> temp = new ArrayList<>();
            Set<String> setOfStrings = new HashSet<>();
            temp.add(setOfStrings);
            dp.add(temp);
            for (int j = 0; j < s.length()-1; j++) {
                Set<String> setOfStrings1 = new HashSet<>();
                dp.get(i).add(setOfStrings1);
            }
        }
       return buildDp(s, dp);
    }

    private int buildDp(String s, List<List<Set<String>>>dp) {

        Set<String> uniquePalindromSeq = new HashSet<>();
        for (int len = 1; len<=s.length(); len++){
            //System.out.println("Len = "+len);
            int start = 0;
            for(int end = start+len-1; end<s.length(); end++){
                // Check All Possibility Of Palindroms from str[i,j]

                if(len>=3 && s.charAt(start) == s.charAt(end)) {
                    Set<String> setAtParticularIndex = dp.get(start).get(end);
                    buildMorePalindromes(s, start, end, dp, setAtParticularIndex, uniquePalindromSeq);
                }
                else if(len == 2 && s.charAt(start) == s.charAt(end)){
                    String palindrome = String.valueOf(s.charAt(start)) + s.charAt(end);

                    Set<String> setAtParticularIndex = dp.get(start).get(end);
                    setAtParticularIndex.add(palindrome);
                    setAtParticularIndex.add(String.valueOf(s.charAt(start)));

                    uniquePalindromSeq.add(palindrome);
                }
                else if(len == 1){
                    String palindrome = String.valueOf(s.charAt(start));

                    Set<String> setAtParticularIndex = dp.get(start).get(end);
                    setAtParticularIndex.add(palindrome);

                    uniquePalindromSeq.add(palindrome);
                }
                start++;
            }
        }
        System.out.println(uniquePalindromSeq);
        return uniquePalindromSeq.size();
    }

    private void buildMorePalindromes(String s, int start, int end,
        List<List<Set<String>>>dp, Set<String> setAtParticularIndex,
        Set<String> uniquePalindromSeq) {

        int maxLen =  end - start - 1;
        for (int len = 1; len<=maxLen; len++){
            int subStart = start + 1;

            for (int subEnd = subStart + len - 1; subEnd<end; subEnd++){
                Set<String> prevPalindromes = dp.get(subStart).get(subEnd);

                for (String palindromeStr: prevPalindromes){

                    StringBuilder newPalindromeStrBuilder = new StringBuilder();
                    newPalindromeStrBuilder.append(s.charAt(start));
                    newPalindromeStrBuilder.append(palindromeStr);
                    newPalindromeStrBuilder.append(s.charAt(end));

                    String newPalindromeStr = newPalindromeStrBuilder.toString();
                    setAtParticularIndex.add(newPalindromeStr);
                    if(!uniquePalindromSeq.contains(newPalindromeStr)){
                        //System.out.println(newPalindromeStr);
                        uniquePalindromSeq.add(newPalindromeStr);
                    }
                }
                subStart++;
            }
        }

        StringBuilder newPalindromeStrBuilder = new StringBuilder();
        newPalindromeStrBuilder.append(s.charAt(start));
        newPalindromeStrBuilder.append(s.charAt(end));

        String newPalindromeStr = newPalindromeStrBuilder.toString();
        setAtParticularIndex.add(newPalindromeStr);
        if(!uniquePalindromSeq.contains(newPalindromeStr)){
            //System.out.println(newPalindromeStr);
            uniquePalindromSeq.add(newPalindromeStr);
        }
    }






Logic2:

    public int countPalindromicSubsequences(String s) {
        List<CharacterInfo> listOfCharacterInfo = buildCharacterInfo(s);
        return countPalindromicSubsequencesHelper(s, listOfCharacterInfo);
    }

   private int countPalindromicSubsequencesHelper(String s,
        List<CharacterInfo> listOfCharacterInfo) {
        List<BuildAnswerForCurrentLength> listOfBuildAns = new ArrayList<>();
        int []count = new int[1];
        count[0] = 0;
        for (int len = 1; len<=s.length(); len++){
            if (len == 1) {
                listOfBuildAns =
                  countFortLengthOneAndPrepareHelperListForNextLength(count,
                          listOfCharacterInfo, len);
            }
            else if (len >= 2) {
                listOfBuildAns = countForCurrentLengthAndPrepareHelperListForNextLength(
                        listOfBuildAns, count, listOfCharacterInfo);
            }
            System.out.println("len = "+len+" count = "+listOfBuildAns.size());
            if(listOfBuildAns.isEmpty())
                break;
        }
        return count[0];
    }

    private List<BuildAnswerForCurrentLength>
    countForCurrentLengthAndPrepareHelperListForNextLength(
      List<BuildAnswerForCurrentLength> listOfBuildAns, int[] count,
      List<CharacterInfo> listOfCharacterInfo) {

        List<BuildAnswerForCurrentLength> listOfBuildAns2 = new ArrayList<>();

        for (int i = 0; i<listOfBuildAns.size(); i++){
            int prevLength = listOfBuildAns.get(i).length;
            if(prevLength%2 == 1){ //specific Character to pick
                char character =  listOfBuildAns.get(i).character;
                int index = nextIndex(listOfBuildAns.get(i).start, listOfBuildAns.get(i).end,
                        listOfBuildAns.get(i).flagStart, listOfCharacterInfo.get(character-97).indexes);
                if(index!=-1){
                    count[0] = (count[0] + 1)%Mod;
                    listOfBuildAns2.add(createNewAnsFromPrev(listOfBuildAns.get(i), index, (char) (i+97)));
                }
            }else{ //need to check all a, b, c, d
                for(int j = 0; j<4; j++){
                    int index = nextIndex(listOfBuildAns.get(i).start, listOfBuildAns.get(i).end,
                            listOfBuildAns.get(i).flagStart, listOfCharacterInfo.get(j).indexes);
                    if(index!=-1){
                        count[0] = (count[0] + 1)%Mod;
                        listOfBuildAns2.add(createNewAnsFromPrev(listOfBuildAns.get(i), index, (char) (j+97)));
                    }
                }
            }
        }
        return listOfBuildAns2;
    }

    private BuildAnswerForCurrentLength createNewAnsFromPrev(
            BuildAnswerForCurrentLength prevObj, int index, char c) {
        BuildAnswerForCurrentLength newObj = new BuildAnswerForCurrentLength();
        if(prevObj.flagStart == 1){
            newObj.start = index;
            newObj.end = prevObj.end;
            newObj.flagStart = 0;
        }else{
            newObj.start = prevObj.start;
            newObj.end = index;
            newObj.flagStart = 1;
        }
        newObj.length = prevObj.length + 1;
        if(newObj.length%2 == 1){
            newObj.character = c;
        }
        return newObj;
    }

    private int nextIndex(int start, int end, int flagStart,
                List<Integer> searchableIndexes) {
        if(flagStart == 1){
            for(int i = 0; i<searchableIndexes.size(); i++){
                int index = searchableIndexes.get(i);
                if(index>start && (index<end || end== -1)){
                    return index;
                }
            }
        }else{
            for(int i = searchableIndexes.size()-1; i>=0; i--){
                int index = searchableIndexes.get(i);
                if(index>start && (index<end || end== -1) ){
                    return index;
                }
            }
        }
        return -1;
    }

    private List<BuildAnswerForCurrentLength>
    countFortLengthOneAndPrepareHelperListForNextLength(
      int []count, List<CharacterInfo> listOfCharacterInfo, int length) {
        List<BuildAnswerForCurrentLength> listOfBuildAns = new ArrayList<>();
        for (int i = 0; i<listOfCharacterInfo.size(); i++){
            if(!listOfCharacterInfo.get(i).indexes.isEmpty()){
                count[0] = (count[0] + 1)%Mod;
                if(length == 1){
                    BuildAnswerForCurrentLength obj = new BuildAnswerForCurrentLength();
                    obj.start = listOfCharacterInfo.get(i).indexes.get(0);
                    obj.end = -1;
                    obj.character = (char) (i+97);
                    obj.length = 1;
                    obj.flagStart = 0;
                    listOfBuildAns.add(obj);
                }
            }
        }
        return listOfBuildAns;
    }
*/
