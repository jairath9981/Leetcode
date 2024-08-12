package Unsolved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
https://leetcode.com/problems/concatenated-words/

Example 1:
Input:
cat
cats
catsdogcats
dog
dogcatsdog
hippopotamuses
rat
ratcatdogcat
xxx
Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
"dogcatsdog" can be concatenated by "dog", "cats" and "dog";
"ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".

Example 2:
Input:
cat
dog
catdog
xxx
Output: ["catdog"]

Input:
a
b
ab
abc
xxx
Output: ["ab"]
*/

class TrieNode_ConcatenatedWords{
    int character[];
    boolean isEnd[];
    String str[];
    TrieNode_ConcatenatedWords next[];

    TrieNode_ConcatenatedWords(){
        this.character = new int[26];
        this.str = new String[26];
        this.isEnd = new boolean[26];
        this.next = new TrieNode_ConcatenatedWords[26];
        for(int i = 0; i<26; i++){
            this.character[i] = 0;
            this.isEnd[i] = false;
            this.str[i] = null;
            this.next[i] = null;;
        }
    }
}

class Trie_ConcatenatedWords{
    TrieNode_ConcatenatedWords trieRoot;
    Trie_ConcatenatedWords(){
        this.trieRoot = new TrieNode_ConcatenatedWords();
    }

    public void addWord(String str){

        TrieNode_ConcatenatedWords curr = this.trieRoot;
        int len = str.length();

        for(int i = 0; i<len; i++){
            int index = (int)str.charAt(i) - 97;

            if(curr.character[index] == 0){
                curr.character[index] = 1;
                TrieNode_ConcatenatedWords nextTrieNode = new TrieNode_ConcatenatedWords();
                curr.next[index] = nextTrieNode;
            }
            if(i == len - 1){
                curr.isEnd[index] = true;
                curr.str[index] = str;
            }
            curr = curr.next[index];
        }
    }
}

public class ConcatenatedWords {

    public static void main(String[] args) {
        ConcatenatedWords concatenatedWords = new ConcatenatedWords();
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
                List<String> wordsList = new ArrayList<>();
                System.out.println("Enter String Array To Stop Insertion In Array Enter xxx");
                input.nextLine();
                s1 = input.nextLine();
                while(!s1.equalsIgnoreCase(stopString)){
                    wordsList.add(s1);
                    s1 = input.nextLine();
                }
                String[] words = wordsList.stream().toArray(String[]::new);
                List<String> concatenatedWordsList = concatenatedWords.findAllConcatenatedWordsInADict(words);
                System.out.println("Concatenated Words In Array:");
                concatenatedWords.printListOfString(concatenatedWordsList);
            } else {
                String[] words = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
                List<String> concatenatedWordsList = concatenatedWords.findAllConcatenatedWordsInADict(words);
                System.out.println("Concatenated Words In Array:");
                concatenatedWords.printListOfString(concatenatedWordsList);
            }
            t--;
        }
    }

    public List<String> findAllConcatenatedWordsInADict(String[] words) {

        TrieNode_ConcatenatedWords root = addWordList(words);
        List<String> concatenatedWords = new ArrayList<>();
        for(int i = 0; i<words.length; i++){
            System.out.println(i+ " words[i] = "+words[i]);
            if(isFormedByConcatenation(words[i], root)){
                System.out.println("Part of Answer = "+words[i]);
                concatenatedWords.add(words[i]);
            }
        }
        return concatenatedWords;
    }

    private boolean isFormedByConcatenation(String word, TrieNode_ConcatenatedWords root) {
        return isFormedByConcatenationHelper(word, 0, 0, root);
    }

    private boolean isFormedByConcatenationHelper(String word, int startIndex, int count,
                                                  TrieNode_ConcatenatedWords root) {
        TrieNode_ConcatenatedWords curr = root;
        for(int i = startIndex; i<word.length(); i++){
            int index = word.charAt(i) - 97;
            //System.out.println("character to check = "+word.charAt(i)+" corresponding index to check node = "+index);
            if(curr.character[index] == 1){
                if(curr.isEnd[index]){
                    //System.out.println("end character of node = "+word.charAt(i));
                    if(i == word.length() -1) {
                        //System.out.println("end character of word, count is: "+count);
                        if (count >= 1)
                            return true;
                        else
                            return false;
                    }
                    boolean status = isFormedByConcatenationHelper(word, i+1, count+1, root);
                    if(status)
                        return status;
                }
            } else{
                //System.out.println("Not found in node");
                return false;
            }
            curr = curr.next[index];
        }
        return false;
    }

    private TrieNode_ConcatenatedWords addWordList(String[] words) {
        Trie_ConcatenatedWords trie_concatenatedWords = new Trie_ConcatenatedWords();
        for (int i = 0; i<words.length; i++)
            trie_concatenatedWords.addWord(words[i]);
        return trie_concatenatedWords.trieRoot;
    }

    private void printTrie(TrieNode_ConcatenatedWords root) {
        System.out.println("*************printArrayOfString*******************");
        printTrieRecursiveHelper(root);
    }

    private void printTrieRecursiveHelper(TrieNode_ConcatenatedWords root) {
        if(root!=null){
            for(int i = 0; i<26; i++){
                if(root.character[i] == 1){
                    char c = (char)(i+97);
                    if(root.isEnd[i])
                        System.out.println("String In Trie: "+root.str[i]);
                    printTrieRecursiveHelper(root.next[i]);
                }
            }
        }
    }

    private void printArrayOfString(String[] arrayOfString) {
        System.out.println("*************printArrayOfString*******************");
        for(int i = 0; i<arrayOfString.length; i++)
            System.out.print(arrayOfString[i]+", ");
        System.out.println();
    }

    private void printListOfString(List<String> listOfString){
        System.out.println("*************printListOfString*******************");
        for(int i = 0; i<listOfString.size(); i++)
            System.out.print(listOfString.get(i)+", ");
        System.out.println();
    }
}
/*
                                                    ALL Logics:
 1
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Unsolved.TrieNode_ConcatenatedWords root = addWordList(words);
        List<String> concatenatedWords = new ArrayList<>();
        for(int i = 0; i<words.length; i++){
            if(isConcatenated(words[i], root)){
                concatenatedWords.add(words[i]);
            }
        }
        return concatenatedWords;
    }

    private boolean isConcatenated(String word, Unsolved.TrieNode_ConcatenatedWords root) {
        //System.out.println("word:"+word );
        return isConcatenatedRecursionHelper(word, 0, word.length()-1, 0, root);
    }

    private boolean isConcatenatedRecursionHelper(String word, int startIndex, int stopIndex,
                                 int countOfConcatenation, Unsolved.TrieNode_ConcatenatedWords root) {
        if(startIndex>stopIndex){
            //System.out.println("startIndex:"+startIndex+" countOfConcatenation:"+countOfConcatenation);
            if(countOfConcatenation>1)
                return true;
            else
                return false;
        }
        else {
            boolean returnValue = false;
            Unsolved.TrieNode_ConcatenatedWords curr = root;
            for (int i = startIndex; i <= stopIndex; i++) {
                //System.out.println("i = "+i);
                int index = (int) word.charAt(i) - 97;
                if (curr.character[index] == 1) {
                    //System.out.println("Congo :)Found Character");
                    if (curr.isEnd[index]) {
                        //System.out.println("String End found i.e. one concatenation found");
                        returnValue =  isConcatenatedRecursionHelper(word, i + 1, stopIndex,
                                countOfConcatenation + 1, root);
                        //System.out.println("returnValue "+returnValue);
                        if(returnValue)
                            return returnValue;
                    }
                } else {
                    //System.out.println(":( Sorry Character Not Found");
                    return false;
                }
                curr = curr.next[index];
            }
            return returnValue;
        }
    }

 2
   public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Unsolved.TrieNode_ConcatenatedWords root = addWordList(words);
        Set<String> wordsFound = new HashSet<String>();
        for(int i = 0; i<words.length; i++)
            wordsFound.add(words[i]);
        List<String> concatenatedWords = new ArrayList<>();
        for(int i = 0; i<words.length; i++){
            if(isConcatenated(words[i], wordsFound, root)){
                concatenatedWords.add(words[i]);
            }
        }
        return concatenatedWords;
    }

    private boolean isConcatenated(String word, Set<String> wordsFound,
                                   Unsolved.TrieNode_ConcatenatedWords root) {
        //System.out.println("word:"+word );
        return isConcatenatedRecursionHelper(word, 0, word.length()-1,
                0, wordsFound,  root);
    }

    private boolean isConcatenatedRecursionHelper(String word, int startIndex, int stopIndex,
                       int countOfConcatenation, Set<String> wordsFound,
                                                Unsolved.TrieNode_ConcatenatedWords root) {
        if(startIndex<stopIndex && startIndex!=0) {
            String remainingString = word.substring(startIndex, stopIndex + 1);
            if(wordsFound.contains(remainingString)) {
                //System.out.println("Set help in Optimization");
                return true;
            }
        }
        if(startIndex>stopIndex){
            //System.out.println("startIndex:"+startIndex+" countOfConcatenation:"+countOfConcatenation);
            if(countOfConcatenation>1)
                return true;
            else
                return false;
        }
        else {
            boolean returnValue = false;
            Unsolved.TrieNode_ConcatenatedWords curr = root;
            for (int i = startIndex; i <= stopIndex; i++) {
                //System.out.println("i = "+i);
                int index = (int) word.charAt(i) - 97;
                if (curr.character[index] == 1) {
                    //System.out.println("Congo :)Found Character");
                    if (curr.isEnd[index]) {
                        //System.out.println("String End found i.e. one concatenation found");
                        returnValue = isConcatenatedRecursionHelper(word, i + 1, stopIndex,
                                countOfConcatenation + 1, wordsFound, root);
                        //System.out.println("returnValue "+returnValue);
                        if(returnValue) {
                            String currStringFound = word.substring(startIndex, i+1);
                            wordsFound.add(currStringFound);
                            return returnValue;
                        }
                    }
                } else {
                    //System.out.println(":( Sorry Character Not Found");
                    return false;
                }
                curr = curr.next[index];
            }
            return returnValue;
        }
    }

 3
     HashSet<String> answer = new HashSet<>();
    public List<String> findAllConcatenatedWordsInADict(String[] words) {

        Unsolved.TrieNode_ConcatenatedWords root = addWordList(words);
        findAllPossibleConcatenationsFromOneWord(root, root);
        return new ArrayList<>(answer);
    }

    private void findAllPossibleConcatenationsFromOneWord(Unsolved.TrieNode_ConcatenatedWords curr,
                                                          Unsolved.TrieNode_ConcatenatedWords root) {
        for(int i = 0; i<26; i++){
            if(curr.isEnd[i]){
                //System.out.println("current word ended = "+curr.str[i]);
                concatWords(curr.next[i], root, root);
            }
            if(curr.character[i] == 1){
                findAllPossibleConcatenationsFromOneWord(curr.next[i], root);
            }
        }
    }

    private void concatWords(Unsolved.TrieNode_ConcatenatedWords curr, Unsolved.TrieNode_ConcatenatedWords newWord,
                             Unsolved.TrieNode_ConcatenatedWords root) {
        for(int i = 0; i<26; i++){
            if(curr.isEnd[i] && newWord.isEnd[i]){
                answer.add(curr.str[i]);
                //System.out.println("can be anser = "+curr.str[i]);
            }
            if(curr.character[i] == 1 && newWord.character[i] == 1){
                char c = (char)(i + 97);
                //System.out.println("word match = "+c);
                if(newWord.isEnd[i]){
                    //System.out.println("root end so start from root node again = ");
                    //printTrie(curr); System.out.println();
                    concatWords(curr.next[i], root, root);
                }
                concatWords(curr.next[i], newWord.next[i], root);
            }
        }
    }
 */

