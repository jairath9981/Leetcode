package Solved;

/*
https://leetcode.com/problems/freedom-trail/

Input:
godding
gd
Input meaning: ring = "godding", key = "gd"
Output: 4
Explanation:
For the first key character 'g', since it is already in place, we just need 1 step to spell this character.
For the second key character 'd', we need to rotate the ring "godding" anticlockwise by two steps to make it become "ddinggo".
Also, we need 1 more step for spelling.
So the final output is 4.

Input:
godding
godding
Output: 13

Input:
aaaaa
aaaaa
Output: 5

Input:
iotfo
fioot
Output: 11

Input:
jhfhz
hfzjhzzfhhjhjhf
Output: 31
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Steps_Freedom_Trail2{
    int cl;
    int acl;
    Steps_Freedom_Trail2(int acl, int cl){
        this.acl = acl;
        this.cl = cl;
    }
}

class Node_Freedom_Trail2{
    List<Integer> indexes;
    Node_Freedom_Trail2(){
        this.indexes = new ArrayList<>();
    }
    public void addIndex(int index){
        this.indexes.add(index);
    }
}

class LowerCaseCharacterIndices_Freedom_Trail2{
    Node_Freedom_Trail2 []arr;
    LowerCaseCharacterIndices_Freedom_Trail2(String ring){
        this.arr = new Node_Freedom_Trail2[26];
        for(int i = 0; i<26; i++)
            this.arr[i] = new Node_Freedom_Trail2();
        addAlphabetIndices(ring);
    }

    public Node_Freedom_Trail2 getNode_Freedom_Trail(char c){
        int ascii = c;
        return this.arr[ascii - 97];
    }

    public void addAlphabetIndices(String ring){
        for(int i = 0; i<ring.length(); i++){
            int ascii = ring.charAt(i);
            Node_Freedom_Trail2 node_freedom_trail2 = this.arr[ascii - 97];
            node_freedom_trail2.addIndex(i);
        }
    }
}

public class Freedom_Trail2 {
    public static void main(String[] args) {
        Freedom_Trail2 freedom_trail2 = new Freedom_Trail2();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String ring = "";
            String key = "";
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter Ring String: ");
                ring = input.nextLine();
                System.out.println("Enter Key String: ");
                key = input.nextLine();
                int minSteps = freedom_trail2.findRotateSteps(ring, key);
                System.out.println("Minimum Number Of Step To Clear The Key: "+minSteps);
            } else {
                ring = "godding";
                key = "godding";
                int minSteps = freedom_trail2.findRotateSteps(ring, key);
                System.out.println("Minimum Number Of Step To Clear The Key: "+minSteps);
            }
            t--;
        }
    }

    public int findRotateSteps(String ring, String key) {
        if(ring.length()>0 && key.length()>0) {

            LowerCaseCharacterIndices_Freedom_Trail2 lowerCaseCharacterIndices_freedom_trail2 =
                    new LowerCaseCharacterIndices_Freedom_Trail2(ring);
            int ringLength = ring.length();

            int rotationSteps = findMinRotationSteps(key, ringLength,
                    lowerCaseCharacterIndices_freedom_trail2);
            return rotationSteps + key.length();
        }
        return -1;
    }

    private int findMinRotationSteps(String key, int ringLength,
                 LowerCaseCharacterIndices_Freedom_Trail2 lowerCaseCharacterIndices_freedom_trail2) {
        int minRotations = 0;

        List<Map<Integer,Steps_Freedom_Trail2>> dp = new ArrayList<>(key.length());

        // for zeroth Index
        char keyChar = key.charAt(0);
        dp.add(new HashMap<>());
        //System.out.println("keyChar: "+keyChar);
        populateDPForCurrentIndex(dp.get(0), 0, new Steps_Freedom_Trail2(0, 0),
                lowerCaseCharacterIndices_freedom_trail2.getNode_Freedom_Trail(keyChar).indexes,
                ringLength);
        //printMap(dp.get(0));

        // for other Indexes
        for(int i = 1; i<key.length(); i++) {
            keyChar = key.charAt(i);
            dp.add(new HashMap<>());
            //System.out.println("keyChar: "+keyChar);

            Map<Integer,Steps_Freedom_Trail2> prevIndex = dp.get(i-1);
            if(!prevIndex.isEmpty()) {
                for (Map.Entry<Integer, Steps_Freedom_Trail2> map : prevIndex.entrySet()) {
                    int prevOverAllRotations = map.getKey();
                    Steps_Freedom_Trail2 prevIndexSteps = map.getValue();
                    populateDPForCurrentIndex(dp.get(i), prevOverAllRotations, prevIndexSteps,
                      lowerCaseCharacterIndices_freedom_trail2.getNode_Freedom_Trail(keyChar).indexes,
                      ringLength);
                }
            }else
                return -1;
            //printMap(dp.get(i));
        }
        minRotations = findMinimumRotationsInLastIndex(dp.get(dp.size()-1));
        return minRotations;
    }

    private void populateDPForCurrentIndex(Map<Integer,Steps_Freedom_Trail2> currentIndexMap,
                   int prevOverAllRotations, Steps_Freedom_Trail2 prevIndexSteps,
                   List<Integer> ringCurrentCharIndex, int ringLength) {

        for(int i = 0; i<ringCurrentCharIndex.size(); i++){

            int finalIndex = getIndexAfterRotations(ringCurrentCharIndex.get(i),
                    prevOverAllRotations, ringLength);

            int antiClockRotationStepsForCurrIndex = findAntiClockWiseRotationRequired(finalIndex);
            insertInMap(currentIndexMap, prevIndexSteps.cl,
                    prevIndexSteps.acl + antiClockRotationStepsForCurrIndex, ringLength);

            int clockRotationStepsForCurrIndex = findClockWiseRotationRequired(finalIndex, ringLength);
            insertInMap(currentIndexMap, prevIndexSteps.cl + clockRotationStepsForCurrIndex,
                    prevIndexSteps.acl, ringLength);
        }
    }

    private int findAntiClockWiseRotationRequired(int finalIndex) {
        return finalIndex;
    }

    private int findClockWiseRotationRequired(int finalIndex, int ringLength) {
        if(finalIndex!=0)
            return ringLength - finalIndex;
        return 0;
    }

    private int getIndexAfterRotations(int initialIndex, int overallRotation, int ringLength) {
        int finalIndex = initialIndex + overallRotation;
        if(finalIndex<0){ // left index move to right side
            int multiplicationFactor =
                 (int) Math.ceil((double) -1 * finalIndex/ringLength);
            finalIndex = finalIndex + multiplicationFactor*ringLength;
        }
        if(finalIndex>ringLength){ // right index move to left side
            finalIndex = finalIndex%ringLength;
        }
        return finalIndex;
    }

    private int findMinimumRotationsInLastIndex(Map<Integer, Steps_Freedom_Trail2> Map) {
        int minRotations = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Steps_Freedom_Trail2> map : Map.entrySet()) {
            Steps_Freedom_Trail2 steps = map.getValue();
            if(minRotations>steps.acl+steps.cl){
                minRotations = steps.acl+steps.cl;
            }
        }
        return minRotations;
    }

    private void insertInMap(Map<Integer, Steps_Freedom_Trail2> map, int cl, int acl,
                             int ringLength) {
        int overAllRotations = getOverallClockWiseRotation(cl%ringLength,
                acl%ringLength);
        if(map.containsKey(overAllRotations)){
            Steps_Freedom_Trail2 steps = map.get(overAllRotations);
            if(isCurrentStepsMin(steps.acl+steps.cl, cl+acl)){
                map.put(overAllRotations, new Steps_Freedom_Trail2(acl, cl));
            }
        }else{
            map.put(overAllRotations, new Steps_Freedom_Trail2(acl, cl));
        }
    }

    private int getOverallClockWiseRotation(int cl, int acl) {
        return cl-acl;
    }

    private boolean isCurrentStepsMin(int prevInserted,
                       int newInsertionGoingToHappen) {
        if(prevInserted>newInsertionGoingToHappen)
            return true;
        return false;
    }

    private void printMap(Map<Integer, Steps_Freedom_Trail2> Map) {

        for (Map.Entry<Integer, Steps_Freedom_Trail2> map : Map.entrySet()) {
            int overAllRotation = map.getKey();
            Steps_Freedom_Trail2 steps = map.getValue();

            System.out.println("OverAllRotation: "+overAllRotation
                    +" Cl: "+steps.cl+" ACL: "+steps.acl);
        }
        System.out.println();
    }
}
