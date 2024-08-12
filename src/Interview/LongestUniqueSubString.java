package Interview;

/*
Given a string s, find the longest substring without repeating characters.




Example 1:



abcadaabcbb


Input: s = "abcabcbb"

Output: abc

Explanation: The answer is "abc", with the length of 3.

Example 2:



Input: s = "bbbbb"

Output: b

Explanation: The answer is "b", with the length of 1.

Example 3:



Input: s = "pwwkew"

Output: wke or kew

Explanation: The answer is "wke", with the length of 3.

Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
*/
public class LongestUniqueSubString {
    public static void main(String[] args) {
        String s = "abcadaabcbb";
        String maxSubstring = maxSubstringWithUniqueChar(s);
        System.out.println("maxSubstring = "+maxSubstring);
    }

    private static String maxSubstringWithUniqueChar(String s) {
        int[] arr = new int[26];
        for(int i =0; i<26; i++)
            arr[i] = -1;
        int start = 0;
        int max = -1, maxStart = 0;
        for(int i = 0; i<s.length(); i++){
            int index = s.charAt(i) - 97;
            //abcadaabcbb
            if(arr[index] == -1){
                arr[index] = i;
                if(max<(i-start+1)){
                    max = (i-start+1);
                    maxStart = start;
                }
                //abcadaabcbb
            }else{
                start = Math.max(start, arr[index]+1);
                arr[index] = i;
                if(max<(i-start+1)){
                    max = (i-start+1);
                    maxStart = start;
                    System.out.println("i = "+i);
                }

            }
        }
        System.out.println("maxStart = "+maxStart+ " max: "+max);
        String s2 = "";
        for (int i = maxStart; i<maxStart+max; i++){
            s2 = s2+s.charAt(i);
        }
        return s2;
    }
}
