package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
https://leetcode.com/problems/special-binary-string/

Input 1:
11011000
Output: "11100100"
Explanation: The strings "10" [occuring at s[1]] and "1100" [at s[3]] are swapped.
This is the lexicographically largest string possible after some number of swaps.

Input 2:
10
Output: "10"

Input 3:
1010101100  (multiple s1)
Output: "1100101010"

Input 4:
10101101100010 (every time start from zero)
Output: "11100100101010"

Input 5:
101110110011010000 (multiple s2)
Output: "111101001100100010"

Input 6:
101101011000  (maintain swapPurpose string separately)
Output:
"111001010010"

Input 7:
1011010011010010 (Perfect example of special Binary string)
Output:
"1101001101001010"

special Binary string:
The number of 0's is equal to the number of 1's. -> in the end string count0 = count1
Every prefix of the binary string has at least as many 1's as 0's. -> at any point of time in Prefix of string s countOf1>=countOf0.

Input:
1101001110001101010110010010 (maintain map left Corner IndexOf s2)
Output:
"1110010101010011100011010010"
*/

class Triplet_Special_Binary_String{
    int start;
    int end;
    int count1;
}

public class Special_Binary_String {

    public static void main(String[] args) {
        Special_Binary_String special_binary_string = new Special_Binary_String();
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
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter Special Binary String: ");
                s = input.nextLine();

                System.out.println("This Is The Lexicographically Largest String Possible After " +
                        "Some Number Of Swaps: "+
                        special_binary_string.makeLargestSpecial(s));
                System.out.println();
            }
            else {
                s = "11011000";

                System.out.println("This Is The Lexicographically Largest String Possible After " +
                        "Some Number Of Swaps: "+
                        special_binary_string.makeLargestSpecial(s));
                System.out.println();
            }
            t--;
        }
    }

    public String makeLargestSpecial(String s) {
        Map<Integer, Integer> zeroIndexToCountOfImmediate1s =
                generateMapToZeroIndexToCountOfImmediate1s(s);
        String largestLexicographicalString = makeSpecialStringSwaps(s,
                zeroIndexToCountOfImmediate1s);
        return largestLexicographicalString;
    }

    private String makeSpecialStringSwaps(String s,
             Map<Integer, Integer> zeroIndexToCountOfImmediate1s) {
        String s2 = s;
        while(true){
            int i;
            for(i = 0; i<s.length(); i++){
                //may need to swap
                if(zeroIndexToCountOfImmediate1s.containsKey(i)){
                    int countStart1Ins2 = zeroIndexToCountOfImmediate1s.get(i);
                    List<Triplet_Special_Binary_String> listOfTripletForPart1 =
                            generateS1(s, i, countStart1Ins2);

                    //may need to swap
                    if(listOfTripletForPart1!= null) {
                        List<Triplet_Special_Binary_String> listOfTripletForPart2 =
                                generateS2(s, i + 1, countStart1Ins2);
                        // may need to swap
                        if(listOfTripletForPart2!=null){
                            Triplet_Special_Binary_String[] triplet_1_and_2 =
                                    greedyComparison(s2, listOfTripletForPart1, listOfTripletForPart2);
                            if(triplet_1_and_2!=null) {
                                s2 = swapSpecialString(s2, triplet_1_and_2[0], triplet_1_and_2[1]);
                                // maintain map
                                zeroIndexToCountOfImmediate1s = maintainMap(s2,
                                        triplet_1_and_2[0], triplet_1_and_2[1], zeroIndexToCountOfImmediate1s);
                               // System.out.println("Moderate output: "+s2+" At Index: "+i);
                                break;
                            }
                        }
                    }
                }
            }
            if(i == s.length()) {
                //System.out.println("Breaking Index: "+i);
                break;
            }
            s = s2;
        }
        return s2;
    }

    private Triplet_Special_Binary_String[] greedyComparison(String s2,
            List<Triplet_Special_Binary_String> listOfTripletForPart1,
            List<Triplet_Special_Binary_String> listOfTripletForPart2) {
        Triplet_Special_Binary_String[] tripletArr = new Triplet_Special_Binary_String[2];
        tripletArr[0] = null; tripletArr[1] = null;
        String swapPurpose = s2;
        for (int i = 0; i<listOfTripletForPart1.size(); i++){
            Triplet_Special_Binary_String triplet1 = listOfTripletForPart1.get(i);
            for(int j = 0; j<listOfTripletForPart2.size(); j++){
                Triplet_Special_Binary_String triplet2 = listOfTripletForPart2.get(j);
                String s3 = swapSpecialString(swapPurpose, triplet1, triplet2);
                if(s3.compareTo(s2)>0){
                    tripletArr[0] = triplet1;
                    tripletArr[1] = triplet2;
                    s2 = s3;
                }
            }
        }
        if(tripletArr[0]!=null && tripletArr[1]!=null){
//            System.out.println();
//            System.out.println("String s: "+swapPurpose);
//            System.out.println("Swap: s["+tripletArr[0].start+"-"+tripletArr[0].end+"] " +
//                    "with s["+tripletArr[1].start+"-"+tripletArr[1].end+"]");
//            System.out.println("Moderater Output: "+s2);
//            System.out.println();
            return tripletArr;
        }
        return null;
    }

    private Map<Integer, Integer> maintainMap(String s2,
                    Triplet_Special_Binary_String tripletS1,
                    Triplet_Special_Binary_String tripletS2,
                    Map<Integer, Integer> zeroIndexToCountOfImmediate1s) {
        Map<Integer, Integer> updatedMap = new HashMap<>();

        int lens1 = tripletS1.end - tripletS1.start + 1;
        int lens2 = tripletS2.end - tripletS2.start + 1;
        for(int i = tripletS1.start; i<=tripletS1.end; i++){
            int updatedIndex = i + lens2;
            if(zeroIndexToCountOfImmediate1s.containsKey(i) && i!=tripletS1.end){
                int val = zeroIndexToCountOfImmediate1s.get(i);
                updatedMap.put(updatedIndex, val);
                zeroIndexToCountOfImmediate1s.remove(i);
            }else if(i==tripletS1.end){
                int val = count1s(s2, updatedIndex+1);
                if(val>0) {
                    updatedMap.put(updatedIndex, val);
                }
                zeroIndexToCountOfImmediate1s.remove(i);
            }
        }
        for(int i = tripletS2.start; i<=tripletS2.end; i++){
            int updatedIndex = i - lens1;
            if(zeroIndexToCountOfImmediate1s.containsKey(i) && i!=tripletS2.end){
                int val = zeroIndexToCountOfImmediate1s.get(i);
                updatedMap.put(updatedIndex, val);
                zeroIndexToCountOfImmediate1s.remove(i);
            }else if(i==tripletS2.end){
                int val = count1s(s2, updatedIndex+1);
                if(val>0) {
                    updatedMap.put(updatedIndex, val);
                }
                zeroIndexToCountOfImmediate1s.remove(i);
            }
        }
        int indexS2LeftCornerIndex = tripletS2.start - lens1;
        int firstZeroIndexInLeftOfS2 = indexS2LeftCornerIndex - 1;
        for(int i = firstZeroIndexInLeftOfS2; i>=0 && s2.charAt(i) == '1'; i--){
            firstZeroIndexInLeftOfS2--;
        }
        updatedMap.put(firstZeroIndexInLeftOfS2, tripletS2.count1+(
                indexS2LeftCornerIndex-firstZeroIndexInLeftOfS2-1));
        zeroIndexToCountOfImmediate1s.remove(firstZeroIndexInLeftOfS2);
        updatedMap.putAll(zeroIndexToCountOfImmediate1s);
        return updatedMap;
    }

    private String swapSpecialString(String s2,
                     Triplet_Special_Binary_String tripletS1,
                     Triplet_Special_Binary_String tripletS2) {

        String beforePart1 = s2.substring(0, tripletS1.start);
        String part1 = s2.substring(tripletS1.start, tripletS1.end+1);
        String part2 = s2.substring(tripletS2.start, tripletS2.end+1);
        String afterPart2 = s2.substring(tripletS2.end+1);

        s2 = beforePart1 + part2 + part1 + afterPart2;
        return s2;
    }

    private List<Triplet_Special_Binary_String> generateS2(String s,
                int start, int countStart1Ins2) {

        List<Triplet_Special_Binary_String> listOfTripletForPart2 =
                new ArrayList<>();

        int count0 = 0;
        int count1 = countStart1Ins2;
        for(int i = start+countStart1Ins2; i<s.length(); i++){
            if(s.charAt(i) == '0'){
                count0++;
            }
            else if(s.charAt(i) == '1'){
                count1++;
            }
            if(count1<count0)
                break;
            if(count0 == count1 && s.charAt(i) == '0'){
                listOfTripletForPart2.add(createTriplet(start, i, countStart1Ins2));
            }
        }
        if(!listOfTripletForPart2.isEmpty())
            return listOfTripletForPart2;
        return null;
    }

    private List<Triplet_Special_Binary_String> generateS1(String s, int end,
                                   int count1InS2) {
        int count0 = 1;
        int count1 = 0;
        int countStarting1 = 0;

        List<Triplet_Special_Binary_String> listOfTripletForPart1 =
                new ArrayList<>();

        for(int i = end-1; i>=0; i--){
            if(s.charAt(i) == '0'){
                count0++;
                countStarting1 = 0;
            }
            else if(s.charAt(i) == '1'){
                count1++;
                countStarting1++;
            }
            if(count1>count0)
                break;
            if(count0 == count1 && s.charAt(i) == '1'
                    && count1InS2>=countStarting1){
                listOfTripletForPart1.add(createTriplet(i, end, countStarting1));
            }
        }
        if(!listOfTripletForPart1.isEmpty())
            return listOfTripletForPart1;
        return null;
    }

    private Triplet_Special_Binary_String createTriplet(int start, int end, int countStarting1) {

        Triplet_Special_Binary_String triplet = new Triplet_Special_Binary_String();
        triplet.start = start;
        triplet.end = end;
        triplet.count1 = countStarting1;

        return triplet;
    }

    private Map<Integer, Integer> generateMapToZeroIndexToCountOfImmediate1s(
            String s) {

        Map<Integer, Integer> zeroIndexToCountOfImmediate1s = new HashMap<>();
        for(int i = 0; i<s.length()-1; i++){
            if(s.charAt(i) == '0' && s.charAt(i+1) == '1'){
                zeroIndexToCountOfImmediate1s.put(i, count1s(s, i+1));
            }
        }
        return zeroIndexToCountOfImmediate1s;
    }

    private Integer count1s(String s, int start) {
        int count = 0;
        for (int i = start; i<s.length() && s.charAt(i) == '1'; i++)
            count++;
        return count;
    }
}
