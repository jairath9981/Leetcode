package Solved;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

/*
https://leetcode.com/problems/number-of-atoms/

Input 1:
H2O
Input Meaning: formula = "H2O"
Output: H2O
Explanation: The count of elements are {'H': 2, 'O': 1}.

Input 2:
Mg(OH)2
Output: "H2MgO2"
Explanation: The count of elements are {'H': 2, 'Mg': 1, 'O': 2}.

Input 3:
K4(ON(SO3)2)2
Output: "K4N2O14S4"
Explanation: The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 4}.

Input 4:
((N7Li31C7B10Be37B23H2H11Li40Be15)26(OBLi48B46N4)25(O48C22He)2N10O34N15B33Li39H34H26B15B23C31(C36N38O33Li38H15H46He21Be38B50)8)3
Output: "B7512Be4968C1635H2658He510Li10167N1833O1257"
*/

public class Number_Of_Atoms {

    public static void main(String[] args) {
        Number_Of_Atoms number_of_atoms = new Number_Of_Atoms();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String formula;
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter Formula String = ");
                formula = input.nextLine();
                String simplifyAndSortedFormula = number_of_atoms.countOfAtoms(formula);
                System.out.println("Simplify And Sorted Formula: " + simplifyAndSortedFormula);
            } else {
                formula = "K4(ON(SO3)2)2";
                String simplifyAndSortedFormula = number_of_atoms.countOfAtoms(formula);
                System.out.println("Simplify And Sorted Formula: " + simplifyAndSortedFormula);
            }
            t--;
        }
    }

    public String countOfAtoms(String formula) {

        Map<String, Integer> elementToCountMap = buildElementToCountMap(formula);

        PriorityQueue<String> minHeap = buildMinHeap(elementToCountMap);
        StringBuilder simplifyAndSortedFormula = new StringBuilder();
        while (!minHeap.isEmpty()) {
            String element = minHeap.poll();
            simplifyAndSortedFormula.append(element);

            int count = elementToCountMap.get(element);
            if (count > 1) {
                simplifyAndSortedFormula.append(count);
            }
        }
        return simplifyAndSortedFormula.toString();
    }

    private PriorityQueue<String> buildMinHeap(
            Map<String, Integer> elementToCountMap) {
        PriorityQueue<String> minHeap = new PriorityQueue<>();
        for (Map.Entry<String, Integer> entry : elementToCountMap.entrySet()) {
            String element = entry.getKey();
            minHeap.add(element);
        }
        return minHeap;
    }

    private Map<String, Integer> buildElementToCountMap(String formula) {

        Map<String, Integer> elementToCountMap = new HashMap<>();

        Stack<Character> bracketStack = new Stack<>();
        Stack<Map<String, Integer>> bracketElementCount = new Stack<>();

        int[] indexHelper = new int[1];
        for (int i = 0; i < formula.length(); ) {
            char c = formula.charAt(i);

            if (c == '(') {
                bracketStack.push('(');
                indexHelper[0] = i + 1;
                Map<String, Integer> map = new HashMap<>();
                solveBracket(bracketStack, bracketElementCount, map,
                        0, elementToCountMap, formula, indexHelper);
                i = indexHelper[0];
            } else if (c == ')') {
                indexHelper[0] = i;
                solveEndBracket(bracketStack, bracketElementCount, elementToCountMap,
                        formula, indexHelper);
            } else if (!bracketStack.isEmpty()) {
                indexHelper[0] = i;
                solveBracket(bracketStack, bracketElementCount, bracketElementCount.peek(),
                        1, elementToCountMap, formula, indexHelper);
                i = indexHelper[0];
            } else if (isUpperCase(c)) {
                indexHelper[0] = i;
                String element = getElement(formula, indexHelper);
                indexHelper[0]++;
                int count = getCount(formula, indexHelper);
                i = indexHelper[0];
                updateMap(elementToCountMap, element, count);
            }
            i++;
        }
        return elementToCountMap;
    }

    private void solveBracket(Stack<Character> bracketStack,
              Stack<Map<String, Integer>> bracketElementCount,
              Map<String, Integer> elementToCountForCurrBracketMap,
              int prevBracketContinueFlag,
              Map<String, Integer> elementToCountMap, String formula,
              int[] indexHelper) {

        int start = indexHelper[0];

        for (int i = start; i < formula.length(); ) {
            char c = formula.charAt(i);
            if (c == '(') {
                if(prevBracketContinueFlag == 0)
                    bracketElementCount.push(elementToCountForCurrBracketMap);
                indexHelper[0] = i - 1;
                break;
            } else if (c == ')') {
                indexHelper[0] = i;
                if(prevBracketContinueFlag == 0)
                    bracketElementCount.push(elementToCountForCurrBracketMap);
                solveEndBracket(bracketStack, bracketElementCount,
                        elementToCountMap, formula, indexHelper);
                break;
            } else {
                if (isUpperCase(c)) {
                    indexHelper[0] = i;
                    String element = getElement(formula, indexHelper);
                    indexHelper[0]++;
                    int count = getCount(formula, indexHelper);
                    i = indexHelper[0];
                    updateMap(elementToCountForCurrBracketMap, element, count);
                }
            }
            i++;
        }

    }

    private void solveEndBracket(Stack<Character> bracketStack,
                                 Stack<Map<String, Integer>> bracketElementCount,
                                 Map<String, Integer> elementToCountMap,
                                 String formula, int[] indexHelper) {

        indexHelper[0]++;
        int multiplicationFactor = getCount(formula,
                indexHelper);
        if (multiplicationFactor > 1) {
            updateMapForMultiplication(bracketElementCount.peek(),
                    multiplicationFactor);
        }
        bracketStack.pop();
        if (!bracketStack.isEmpty()) {
            Map<String, Integer> map = bracketElementCount.pop();
            updatePrevMap(bracketElementCount.peek(), map);
        } else {
            updatePrevMap(elementToCountMap, bracketElementCount.peek());
            bracketElementCount.pop();
        }
    }

    private void updatePrevMap(Map<String, Integer> prevMap,
                               Map<String, Integer> currMap) {

        for (Map.Entry<String, Integer> entry :
                currMap.entrySet()) {
            String element = entry.getKey();
            int count = entry.getValue();
            updateMap(prevMap, element, count);
        }
    }

    private void updateMapForMultiplication(
            Map<String, Integer> elementToCountForCurrBracketMap,
            int multiplicationFactor) {

        for (Map.Entry<String, Integer> entry :
                elementToCountForCurrBracketMap.entrySet()) {
            String element = entry.getKey();
            int count = entry.getValue();
            elementToCountForCurrBracketMap.put(element, count * multiplicationFactor);
        }
    }


    private void updateMap(Map<String, Integer> map, String element, int count) {
        if (map.containsKey(element)) {
            map.put(element, map.get(element) + count);
        } else {
            map.put(element, count);
        }
    }

    private int getCount(String formula, int[] indexHelper) {
        StringBuilder count = new StringBuilder();

        int start = indexHelper[0];
        if (start < formula.length()) {
            char c = formula.charAt(start);
            if (isDigit(c)) {
                count.append(c);
                int i, flag = 0;
                for (i = start + 1; i < formula.length(); i++) {
                    c = formula.charAt(i);
                    if (isDigit(c)) {
                        count.append(c);
                    } else {
                        indexHelper[0] = i - 1;
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0 && i == formula.length()) {
                    indexHelper[0] = i - 1;
                }
            } else {
                count.append('1');
                indexHelper[0]--;
            }
        } else {
            count.append('1');
            indexHelper[0] = start - 1;
        }
        return Integer.parseInt(count.toString());
    }

    private String getElement(String formula, int[] indexHelper) {
        StringBuilder element = new StringBuilder();

        int start = indexHelper[0];
        char c = formula.charAt(start);

        if (isUpperCase(c)) {
            element.append(c);
            for (int i = start + 1; i < formula.length(); i++) {
                c = formula.charAt(i);
                if (isLowerCase(c)) {
                    element.append(c);
                } else {
                    indexHelper[0] = i - 1;
                    break;
                }
            }
        }
        return element.toString();
    }

    private boolean isUpperCase(char c) {
        int ascii = c;
        if (ascii >= 65 && ascii <= 90)
            return true;
        return false;
    }

    private boolean isLowerCase(char c) {
        int ascii = c;
        if (ascii >= 97 && ascii <= 122)
            return true;
        return false;
    }

    private boolean isDigit(char c) {
        int ascii = c;
        if (ascii >= 48 && ascii <= 57)
            return true;
        return false;
    }
}
