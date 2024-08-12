package Wrong;

/*
https://leetcode.com/problems/freedom-trail/

                        For Some Input Wrong Answer Is Coming
eg
Input:
caotmcaataijjxi
oatjiioicitatajtijciocjcaaxaaatmctxamacaamjjx
Output: 137

    We are Trying To Rectify This Problem In Approach2


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
import java.util.List;
import java.util.Scanner;

class Node_Freedom_Trail{
    List<Integer> indexes;
    Node_Freedom_Trail(){
        this.indexes = new ArrayList<>();
    }

    public void addIndex(int index){
        this.indexes.add(index);
    }

    public int getIndex(int index) {
        return this.indexes.get(index);
    }
}

class LowerCaseCharacterIndices_Freedom_Trail{
    Node_Freedom_Trail []arr;
    LowerCaseCharacterIndices_Freedom_Trail(String ring){
        this.arr = new Node_Freedom_Trail[26];
        for(int i = 0; i<26; i++)
            this.arr[i] = new Node_Freedom_Trail();
        addAlphabetIndices(ring);
    }

    public Node_Freedom_Trail getNode_Freedom_Trail(char c){
        int ascii = c;
        return this.arr[ascii - 97];
    }

    public void addAlphabetIndices(String ring){
        for(int i = 0; i<ring.length(); i++){
            int ascii = ring.charAt(i);
            Node_Freedom_Trail node_freedom_trail = this.arr[ascii - 97];
            node_freedom_trail.addIndex(i);
        }
    }
}

public class Freedom_Trail {
    public static void main(String[] args) {
        Freedom_Trail freedom_trail = new Freedom_Trail();
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
                int minSteps = freedom_trail.findRotateSteps(ring, key);
                System.out.println("Minimum Number Of Step To Clear The Key: "+minSteps);
            } else {
                ring = "godding";
                key = "godding";
                int minSteps = freedom_trail.findRotateSteps(ring, key);
                System.out.println("Minimum Number Of Step To Clear The Key: "+minSteps);
            }
            t--;
        }
    }

    public int findRotateSteps(String ring, String key) {
        if(ring.length()>0 && key.length()>0) {

            LowerCaseCharacterIndices_Freedom_Trail lowerCaseCharacterIndices_freedom_trail =
                    new LowerCaseCharacterIndices_Freedom_Trail(ring);
            int ringLength = ring.length();

            int rotationSteps = findRotateStepsRecursionHelper(0, 0,
                    0, lowerCaseCharacterIndices_freedom_trail, ringLength, key);
            return rotationSteps + key.length();
        }
        return -1;
    }

    private int findRotateStepsRecursionHelper(int start, int clockWise, int antiClockWise,
                  LowerCaseCharacterIndices_Freedom_Trail lowerCaseCharacterIndices_freedom_trail,
                  int ringLength, String key) {
        for(int i = start; i<key.length(); i++) {
            char keyCharacter = key.charAt(i);
            System.out.println("keyCharacter = "+keyCharacter);

            Node_Freedom_Trail node_freedom_trail =
                    lowerCaseCharacterIndices_freedom_trail.getNode_Freedom_Trail(keyCharacter);
            int stepForAntiClockWise = getAntiClockWiseSteps(
                    getOverallClockWiseRotation(clockWise, antiClockWise),
                    ringLength, node_freedom_trail, keyCharacter);
            int stepForClockWise = getClockWiseSteps(
                    getOverallClockWiseRotation(clockWise, antiClockWise), ringLength,
                    node_freedom_trail, keyCharacter);

            if(stepForClockWise<stepForAntiClockWise){
                clockWise = clockWise + stepForClockWise;
            }else if(stepForClockWise>stepForAntiClockWise){
                antiClockWise = antiClockWise + stepForAntiClockWise;
            }else{
                int clockWiseRotation = findRotateStepsRecursionHelper(i+1, clockWise+stepForClockWise,
                        antiClockWise, lowerCaseCharacterIndices_freedom_trail, ringLength, key);
                int antiClockWiseRotation = findRotateStepsRecursionHelper(i+1, clockWise,
                        antiClockWise+stepForAntiClockWise, lowerCaseCharacterIndices_freedom_trail,
                        ringLength, key);
                if(clockWiseRotation>antiClockWiseRotation){
                    antiClockWise = antiClockWise+stepForAntiClockWise;
                }else{
                    clockWise = clockWise + stepForClockWise;
                }
            }
        }
        return clockWise + antiClockWise;
    }

    private int getAntiClockWiseSteps(int overallClocWiseRotation, int ringLength,
                    Node_Freedom_Trail node_freedom_trail, char keyCharacter) {
        int minAntiClockwiseRotations = Integer.MAX_VALUE;
        for(int i = 0; i<node_freedom_trail.indexes.size(); i++){
            int initialIndex = node_freedom_trail.indexes.get(i);
            int finalIndex = getIndexAfterAllRotations(initialIndex, overallClocWiseRotation, ringLength);
            minAntiClockwiseRotations = Math.min(minAntiClockwiseRotations, finalIndex);
        }
        return minAntiClockwiseRotations;
    }

    private int getIndexAfterAllRotations(int initialIndex, int overallRotation, int ringLength) {
        int finalIndex = initialIndex + overallRotation;
        if(finalIndex<0){ // left index move to right side
            int multiplicationFactor = (int) Math.ceil((double) -1*finalIndex/ringLength);
            return finalIndex + multiplicationFactor*ringLength;
        }
        if(finalIndex>ringLength){ // right index move to left side
            return finalIndex%ringLength;
        }
        return finalIndex;
    }

    private int getOverallClockWiseRotation(int clockWise, int antiClockWise) {
        return clockWise - antiClockWise;
    }

    private int getClockWiseSteps(int overallClocWiseRotation, int ringLength,
                     Node_Freedom_Trail node_freedom_trail, char keyCharacter) {
        int minClockwiseRotations = Integer.MAX_VALUE;
        for(int i = 0; i<node_freedom_trail.indexes.size(); i++){
            int initialIndex = node_freedom_trail.indexes.get(i);
            int finalIndex = getIndexAfterAllRotations(initialIndex, overallClocWiseRotation, ringLength);
            minClockwiseRotations = Math.min(minClockwiseRotations, ringLength - finalIndex);
        }
        return minClockwiseRotations;
    }
}
