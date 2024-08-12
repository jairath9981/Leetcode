package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/stickers-to-spell-word/

Input 1:
with
example
science
xxx
thehat
Input 1 meaning : stickers = ["with","example","science"], target = "thehat"
Output: 3
Explanation:
We can use 2 "with" stickers, and 1 "example" sticker.
After cutting and rearrange the letters of those stickers, we can form the target "thehat".
Also, this is the minimum number of stickers necessary to form the target string.


Input 2:
notice
possible
xxx
basicbasic
Output: -1
Explanation:
We cannot form the target "basicbasic" from cutting letters from the given stickers.

Input 3:
safe
tire
gather
street
enter
believe
xxx
eventfat
Output: 4

Input 4:  Time Limit Exceed For Logic 1 And Wrong Anser with Logic 3 Combination Max Approach
control
heart
interest
stream
sentence
soil
wonder
them
month
slip
table
miss
boat
speak
figure
no
perhaps
twenty
throw
rich
capital
save
method
store
meant
life
oil
string
song
food
am
who
fat
if
put
path
come
grow
box
great
word
object
stead
common
fresh
the
operate
where
road
mean
xxx
stoodcrease
Output: 3

Input 5:
these
guess
about
garden
him
xxx
atomher
Output: 3
*/


class CharacterInfo_Stickers_to_Spell_Word {
    Set<Integer> indexes;

    CharacterInfo_Stickers_to_Spell_Word() {
        this.indexes = new HashSet<>();
    }
}

class ResultCreatorHelper_Stickers_to_Spell_Word {
    String str;
    Map<Character, Integer> map;
    List<Integer> usedIndexes;
    Set<Integer> notUsefulIndexes;

    ResultCreatorHelper_Stickers_to_Spell_Word() {
        this.map = new HashMap<>();
        this.usedIndexes = new ArrayList<>();
        this.notUsefulIndexes = new HashSet<>();
    }

    ResultCreatorHelper_Stickers_to_Spell_Word(
            ResultCreatorHelper_Stickers_to_Spell_Word resultCreatorHelper) {

        this.map = new HashMap<>();
        this.usedIndexes = new ArrayList<>();
        this.notUsefulIndexes = new HashSet<>();

        this.map.putAll(resultCreatorHelper.map);
        this.usedIndexes.addAll(resultCreatorHelper.usedIndexes);
        this.notUsefulIndexes.addAll(resultCreatorHelper.notUsefulIndexes);
    }
}


public class Stickers_to_Spell_Word {

    public static void main(String[] args) {
        Stickers_to_Spell_Word stickers_to_spell_word = new Stickers_to_Spell_Word();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String s1;
            String stopString = "xxx";
            if (choice == 1) {
                List<String> listOfStickers = new ArrayList<>();
                System.out.println("Enter Stickers Array Of Type String And To Stop Insertion In Enter xxx");
                input.nextLine();
                s1 = input.nextLine();
                while (!s1.equalsIgnoreCase(stopString)) {
                    listOfStickers.add(s1);
                    s1 = input.nextLine();
                }
                String[] stickers = listOfStickers.stream().toArray(String[]::new);
                System.out.println("Enter Target String, to Check How Many Min Numbers Of Stickers Required " +
                        "To Create Target String");
                String target = input.nextLine();
                int minStickerRequired = stickers_to_spell_word.minStickers(stickers, target);
                System.out.println("Min Sticker Required To Create Target String: " + minStickerRequired);
            } else {
                String[] stickers = {"with", "example", "science"};
                String target = "thehat";
                int minStickerRequired = stickers_to_spell_word.minStickers(stickers, target);
                System.out.println("Min Sticker Required To Create Target String: " + minStickerRequired);
            }
            t--;
        }
    }

    public int minStickers(String[] stickers, String target) {

        String[] uniqueStickersArr = pickUniqueStickers(stickers);
        ResultCreatorHelper_Stickers_to_Spell_Word[] charMapOfStickers =
                new ResultCreatorHelper_Stickers_to_Spell_Word[uniqueStickersArr.length];
        CharacterInfo_Stickers_to_Spell_Word[] characterInfo =
                characterAnalysisOfStickers(uniqueStickersArr, charMapOfStickers);

        ResultCreatorHelper_Stickers_to_Spell_Word charMapOfTarget =
                characterAnalysisOfTarget(target);
        charMapOfTarget.str = target;

        int minSticker = -1;
        if (isPossibleToAchieveTarget(charMapOfTarget, characterInfo)) {
            minSticker = minNumberOfStickerRequired(charMapOfTarget, charMapOfStickers,
                    uniqueStickersArr);
        }
        return minSticker;
    }

    private int minNumberOfStickerRequired(
            ResultCreatorHelper_Stickers_to_Spell_Word charMapOfTarget,
            ResultCreatorHelper_Stickers_to_Spell_Word[] charMapOfStickers,
            String[] uniqueStickersArr) {

        Queue<ResultCreatorHelper_Stickers_to_Spell_Word> qu = new LinkedList<>();
        qu.add(charMapOfTarget);

        int usedString = 0;
        int quSize = 1;
        boolean flag = false;
        while (!qu.isEmpty()) {

            quSize = qu.size();
            usedString++;
            Set<String> uniqueStringForCurrLevel = new HashSet<>();
            for (int i = 0; i < quSize; i++) {
                ResultCreatorHelper_Stickers_to_Spell_Word resultCreatorForTarget = qu.poll();
                boolean finished = reducedTargetSize(resultCreatorForTarget, charMapOfStickers,
                        qu, uniqueStickersArr, uniqueStringForCurrLevel);
                if (finished) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        return usedString;
    }

    private boolean reducedTargetSize(
            ResultCreatorHelper_Stickers_to_Spell_Word resultCreatorForTarget,
            ResultCreatorHelper_Stickers_to_Spell_Word[] charMapOfStickers,
            Queue<ResultCreatorHelper_Stickers_to_Spell_Word> qu, String[] uniqueStickersArr,
            Set<String> uniqueStringForCurrLevel) {

        for (int i = 0; i < uniqueStickersArr.length; i++) {
            if ((!resultCreatorForTarget.notUsefulIndexes.contains(i))) {

                int numberOfCharMatch = numberOfMatchCharacter(resultCreatorForTarget.map,
                        charMapOfStickers[i].map);

                if (numberOfCharMatch == 0) {
                    resultCreatorForTarget.notUsefulIndexes.add(i);
                } else {
                    ResultCreatorHelper_Stickers_to_Spell_Word resultCreatorHelper =
                            reducedTargetSizeHelper(resultCreatorForTarget, charMapOfStickers[i].map);
                    if (!uniqueStringForCurrLevel.contains(resultCreatorHelper.str)) {
                        resultCreatorHelper.usedIndexes.add(i);
                        qu.add(resultCreatorHelper);
                        if (resultCreatorHelper.map.isEmpty()) {
                            //printUsedString(uniqueStickersArr, resultCreatorHelper.usedIndexes);
                            return true;
                        }
                        uniqueStringForCurrLevel.add(resultCreatorHelper.str);
                    }
                }
            }
        }
        return false;
    }

    private ResultCreatorHelper_Stickers_to_Spell_Word reducedTargetSizeHelper(
            ResultCreatorHelper_Stickers_to_Spell_Word resultCreatorForTarget,
            Map<Character, Integer> currStickerMap) {

        String prevTarget = resultCreatorForTarget.str;
        String reducedTarget = "";
        ResultCreatorHelper_Stickers_to_Spell_Word updatedResultCreator =
                new ResultCreatorHelper_Stickers_to_Spell_Word(resultCreatorForTarget);

        Map<Character, Integer> characterPicked = new HashMap<>();
        for (int i = 0; i < prevTarget.length(); i++) {
            char c = prevTarget.charAt(i);
            if (!characterPicked.containsKey(c)) {
                if (currStickerMap.containsKey(c)) {

                    removeCharacterFromTarget(currStickerMap.get(c),
                            resultCreatorForTarget.map.get(c), updatedResultCreator.map,
                            c, characterPicked);

                } else {
                    reducedTarget += c;
                }
            } else {
                int count = characterPicked.get(c);
                if (count == 1) {
                    reducedTarget += c;
                } else {
                    addInMapToDecrease(c, 1, characterPicked);
                }
            }
        }

        updatedResultCreator.str = reducedTarget;
        return updatedResultCreator;
    }

    private void removeCharacterFromTarget(int countOfCharInSticker, int countOfCharInTarget,
                                           Map<Character, Integer> updatedResultCreatorMap, char c,
                                           Map<Character, Integer> characterPicked) {
        int matchCharacter = countCharMatch(countOfCharInSticker,
                countOfCharInTarget);
        addInMapToDecrease(c, matchCharacter, updatedResultCreatorMap);
        characterPicked.put(c, matchCharacter);
    }


    private int numberOfMatchCharacter(Map<Character, Integer> targetMap,
                                       Map<Character, Integer> currStickerMap) {

        int matchCharacter = 0;
        for (Map.Entry<Character, Integer> entry : currStickerMap.entrySet()) {
            int countOfCharInSticker = entry.getValue();
            if (targetMap.containsKey(entry.getKey())) {
                int countOfCharInTarget = targetMap.get(entry.getKey());
                matchCharacter += countCharMatch(countOfCharInSticker,
                        countOfCharInTarget);
            }
        }
        return matchCharacter;
    }

    private int countCharMatch(int countOfCharInSticker, int countOfCharInTarget) {
        if (countOfCharInSticker >= countOfCharInTarget)
            return countOfCharInTarget;
        else
            return countOfCharInSticker;
    }

    private boolean isPossibleToAchieveTarget(
            ResultCreatorHelper_Stickers_to_Spell_Word charMapOfTarget,
            CharacterInfo_Stickers_to_Spell_Word[] characterInfo) {

        for (Map.Entry<Character, Integer> entry : charMapOfTarget.map.entrySet()) {
            char c = entry.getKey();
            int ascii = c;
            if (characterInfo[ascii - 97].indexes.isEmpty())
                return false;
        }
        return true;
    }

    private void addInMapToIncrease(char key, Map<Character, Integer> map) {
        if (map.containsKey(key)) {
            int count = map.get(key);
            map.put(key, count + 1);
        } else {
            int count = 1;
            map.put(key, count);
        }
    }

    private void addInMapToDecrease(char key, int value, Map<Character, Integer> map) {
        if (map.containsKey(key)) {
            int count = map.get(key) - value;
            if (count == 0) {
                map.remove(key);
            } else {
                map.put(key, count);
            }
        }
    }

    private ResultCreatorHelper_Stickers_to_Spell_Word characterAnalysisOfTarget(String target) {
        ResultCreatorHelper_Stickers_to_Spell_Word charMapOfTarget =
                new ResultCreatorHelper_Stickers_to_Spell_Word();

        for (int j = 0; j < target.length(); j++) {
            addInMapToIncrease(target.charAt(j), charMapOfTarget.map);
        }
        return charMapOfTarget;
    }

    private CharacterInfo_Stickers_to_Spell_Word[] characterAnalysisOfStickers(
            String[] uniqueStickersArr, ResultCreatorHelper_Stickers_to_Spell_Word[] charMapOfStickers) {

        CharacterInfo_Stickers_to_Spell_Word[] characterInfo =
                new CharacterInfo_Stickers_to_Spell_Word[26];

        for (int i = 0; i < 26; i++) {
            characterInfo[i] = new CharacterInfo_Stickers_to_Spell_Word();
        }

        for (int i = 0; i < uniqueStickersArr.length; i++) {

            String sticker = uniqueStickersArr[i];
            charMapOfStickers[i] = new ResultCreatorHelper_Stickers_to_Spell_Word();

            for (int j = 0; j < sticker.length(); j++) {
                int ascii = sticker.charAt(j);
                characterInfo[ascii - 97].indexes.add(i);
                addInMapToIncrease(sticker.charAt(j), charMapOfStickers[i].map);
            }
        }
        return characterInfo;
    }

    private String[] pickUniqueStickers(String[] stickers) {

        Set<String> setOfStickers = new HashSet<>(Arrays.asList(stickers));

        String[] uniqueStickersArr = new String[setOfStickers.size()];
        int i = 0;
        for (String str : setOfStickers) {
            uniqueStickersArr[i] = str;
            i++;
        }
        return uniqueStickersArr;
    }

    private void printMap(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.print(entry.getKey() + " -> " + entry.getValue() + ",   ");
        }
        System.out.println();
    }

    private void printUsedString(String[] uniqueStickersArr, List<Integer> usedIndexes) {
        System.out.println("Used Strings: ");
        for(int i = 0; i<usedIndexes.size(); i++){
            System.out.println(uniqueStickersArr[usedIndexes.get(i)]);
        }
    }
}

//Logic 1  ---> Time Complexity error As We Are building All Possible Combination Character By Character. Input 4
// Logic 2 erase first Max Character Input 5 wrong answer.
// Logic 3 erase Combination Of Max Characters. Input 4 wrong answer. Shorten I/p 4 for Logic 3
/*
operate
stead,
control
xxx
stoodcrease
Logic 3 is erasing operate character from stoodcrease. Hence Leads to use 4 stickers

Wrong Answer By Logic 3:
operate, stead, control, stead
stoodcrease
operate -> sodcs
stead -> ocs
control -> s
stead-> x

Correct Answer By Current Logic:
stead, control, stead
stoodcrease
stead -> oocrse
control -> se
stead-> x
*/

/*
// Logic 1  ---> Time Complexity error As We Are building All Possible Combination Character By Character
class CharacterInfo_Stickers_to_Spell_Word{
    Set<Integer> indexes;

    CharacterInfo_Stickers_to_Spell_Word(){
        this.indexes = new HashSet<>();
    }
}

class ResultCreatorHelper_Stickers_to_Spell_Word{
    Map<Character, Integer> map;
    int usedStrings;

    ResultCreatorHelper_Stickers_to_Spell_Word(){
        this.map = new HashMap<>();
        usedStrings = 0;
    }

    ResultCreatorHelper_Stickers_to_Spell_Word(Map<Character, Integer> map,
         int usedString){
        if(this.map == null || this.map.isEmpty())
            this.map = new HashMap<>();
        this.map.putAll(map);
        this.usedStrings = usedString;
    }
}

public class Stickers_to_Spell_Word {

    public static void main(String[] args) {
        Stickers_to_Spell_Word stickers_to_spell_word = new Stickers_to_Spell_Word();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String s1;
            String stopString = "xxx";
            if (choice == 1) {
                List<String> listOfStickers = new ArrayList<>();
                System.out.println("Enter Stickers Array Of Type String And To Stop Insertion In Enter xxx");
                input.nextLine();
                s1 = input.nextLine();
                while(!s1.equalsIgnoreCase(stopString)){
                    listOfStickers.add(s1);
                    s1 = input.nextLine();
                }
                String[] stickers = listOfStickers.stream().toArray(String[]::new);
                System.out.println("Enter Target String, to Check How Many Min Numbers Of Stickers Required " +
                        "To Create Target String");
                String target = input.nextLine();
                int minStickerRequired = stickers_to_spell_word.minStickers(stickers, target);
                System.out.println("Min Sticker Required To Create Target String: "+minStickerRequired);
            } else {
                String[] stickers = {"with","example","science"};
                String target = "thehat";
                int minStickerRequired = stickers_to_spell_word.minStickers(stickers, target);
                System.out.println("Min Sticker Required To Create Target String: "+minStickerRequired);
            }
            t--;
        }
    }

    public int minStickers(String[] stickers, String target) {

        stickers = pickUniqueStickers(stickers);

        CharacterInfo_Stickers_to_Spell_Word[] characterInfo =
                createCharacterInfo(stickers);

        List<ResultCreatorHelper_Stickers_to_Spell_Word> listOfAllPossibleResults =
                new ArrayList<>();

        int minSticker = createResultList(target, characterInfo, stickers,
                listOfAllPossibleResults);

        return minSticker;
    }

    private int createResultList(String target,
      CharacterInfo_Stickers_to_Spell_Word[] characterInfo, String[] uniqueStickersArr,
      List<ResultCreatorHelper_Stickers_to_Spell_Word> listOfAllPossibleResults) {

        int[] index = new int[3];
        index[0] = -1; // finalIndex
        index[1] = -1; // currCharacterFound
        index[2] = -1; // currCharacterIndex

        for(int i = 0; i<target.length(); i++){
            char c = target.charAt(i);
            System.out.println("Finding For Char: "+c);
            index[1] = -1; // currCharacterFound
            index[2] = -1; // currCharacterIndex
            playWithResultList(c, characterInfo, uniqueStickersArr,
                    listOfAllPossibleResults, index);
            if(index[0]==-1)
                break;
        }
        return index[0];
    }

    private void playWithResultList(char c,
      CharacterInfo_Stickers_to_Spell_Word[] characterInfo, String[] uniqueStickersArr,
      List<ResultCreatorHelper_Stickers_to_Spell_Word> listOfAllPossibleResults,
      int[] resultHelperIndex) {

        int n = listOfAllPossibleResults.size();
        int flag = 0;
        int min = Integer.MAX_VALUE;
        if(n!=0){
            for(int i = 0; i<n; i++){
                Map<Character, Integer> map = listOfAllPossibleResults.get(i).map;
                if(map.containsKey(c)){
                    flag = 1;
                    addInMapToDecrease(c, map);
                    if(min>listOfAllPossibleResults.get(i).usedStrings) {
                        //Input 3
                        min = listOfAllPossibleResults.get(i).usedStrings;
                    }
                }else{
                    buildListOfResult(c, characterInfo, uniqueStickersArr,
                            listOfAllPossibleResults, i, resultHelperIndex);
                }
            }
        }else{
            buildListOfResult(c, characterInfo, uniqueStickersArr,
                  listOfAllPossibleResults, -1, resultHelperIndex);
        }
        if(flag == 0){
            if(resultHelperIndex[1] == 1)
                resultHelperIndex[0] = resultHelperIndex[2];
            else
                resultHelperIndex[0] = -1;
        }else{
            resultHelperIndex[0] = min;
        }
    }

    private void buildListOfResult(char c,
      CharacterInfo_Stickers_to_Spell_Word[] characterInfo, String[] uniqueStickers,
      List<ResultCreatorHelper_Stickers_to_Spell_Word> listOfAllPossibleResults, int listIndex,
      int[] resultHelperIndex) {

        if(listOfAllPossibleResults.isEmpty()){
            addInList(c, characterInfo, uniqueStickers, listIndex, listOfAllPossibleResults,
                    new HashMap<>(), 0, resultHelperIndex);
        }else{
            ResultCreatorHelper_Stickers_to_Spell_Word resultCreatorHelper =
                    listOfAllPossibleResults.get(listIndex);
            Map<Character, Integer> prevMap = resultCreatorHelper.map;
            int usedString = resultCreatorHelper.usedStrings;

            addInList(c, characterInfo, uniqueStickers, listIndex, listOfAllPossibleResults,
                    prevMap, usedString, resultHelperIndex);
        }
    }

    private void addInList(char c,
      CharacterInfo_Stickers_to_Spell_Word[] characterInfo, String[] uniqueStickers,
      int listIndex, List<ResultCreatorHelper_Stickers_to_Spell_Word> listOfAllPossibleResults,
      Map<Character, Integer> prevMap, int usedString, int[] resultHelperIndex) {
        int ascii = c;
        CharacterInfo_Stickers_to_Spell_Word currCharacterInfo =
                characterInfo[ascii-97];
        for(int i: currCharacterInfo.indexes){
            resultHelperIndex[1] = 1;
            resultHelperIndex[2] = usedString+1;
            String sticker = uniqueStickers[i];
            Map<Character, Integer> map = buildMap(prevMap, sticker);
            addInMapToDecrease(c, map);
            if(listIndex!=-1){
                listOfAllPossibleResults.set(listIndex,
                   new ResultCreatorHelper_Stickers_to_Spell_Word(map, usedString+1));
                listIndex = -1;
            }else if(listIndex==-1){
                listOfAllPossibleResults.add(
                   new ResultCreatorHelper_Stickers_to_Spell_Word(map, usedString+1));
            }
        }
    }

    private Map<Character, Integer> buildMap(Map<Character, Integer> prevMap,
                String sticker) {
        Map<Character, Integer> newMap = new HashMap<>();
        if(!prevMap.isEmpty())
            newMap.putAll(prevMap);
        for(int i = 0; i<sticker.length(); i++){
            char c = sticker.charAt(i);
            addInMapToIncrease(c, newMap);
        }
        return newMap;
    }

    private void addInMapToIncrease(char key, Map<Character, Integer> map) {
        if(map.containsKey(key)){
            int count = map.get(key);
            map.put(key, count+1);
        }else{
            int count = 1;
            map.put(key, count);
        }
    }

    private void addInMapToDecrease(char key, Map<Character, Integer> map) {
        if(map.containsKey(key)){
            int count = map.get(key);
            if(count == 1){
                map.remove(key);
            }else {
                map.put(key, count - 1);
            }
        }
    }

    private String[] pickUniqueStickers(String[] stickers) {

        Set<String> setOfStickers = new HashSet<>(Arrays.asList(stickers));

        String[] uniqueStickersArr = new String[setOfStickers.size()];
        int i = 0;
        for(String str: setOfStickers){
            uniqueStickersArr[i] = str;
            i++;
        }
        return uniqueStickersArr;
    }

    private CharacterInfo_Stickers_to_Spell_Word[] createCharacterInfo(
            String[] uniqueStickersArr) {

        CharacterInfo_Stickers_to_Spell_Word[] characterInfo =
                new CharacterInfo_Stickers_to_Spell_Word[26];

        for(int i = 0; i<26; i++) {
            characterInfo[i] = new CharacterInfo_Stickers_to_Spell_Word();
        }

        for(int i = 0; i<uniqueStickersArr.length; i++){
            String sticker = uniqueStickersArr[i];
            for(int j = 0; j<sticker.length(); j++){
                int ascii = sticker.charAt(j);
                characterInfo[ascii - 97].indexes.add(i);
            }
        }
        return characterInfo;
    }

    private void printMap(Map<Character, Integer> map) {
        for (Map.Entry<Character, Integer> entry:map.entrySet()){
            System.out.print(entry.getKey()+" -> "+entry.getValue()+",   ");
        }
        System.out.println();
    }
}
 */
