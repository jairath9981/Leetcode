package Solved;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/prefix-and-suffix-search/
*/

class Trie_Indexes_WordFilter{
    Set<Integer> indexes;
    Set<Integer> endOfWordIndexes;
    Trie_Indexes_WordFilter(){
        this.indexes = new HashSet<>();
        endOfWordIndexes = new HashSet<>();
    }
}

class Trie_WordFilter{
    Trie_Indexes_WordFilter[] currAlphabetSet;
    Trie_WordFilter[] nextTrie;
    Trie_WordFilter(){
        currAlphabetSet = new Trie_Indexes_WordFilter[26];
        nextTrie = new Trie_WordFilter[26];
        for(int i = 0; i<26; i++) {
            currAlphabetSet[i] = null;
            nextTrie[i] = null;
        }
    }
}

public class WordFilter {
    Trie_WordFilter rootForWords;
    Trie_WordFilter rootForReverseWords;
    List<String>words;

    public WordFilter(String[] words) {
        rootForWords = new Trie_WordFilter();
        rootForReverseWords = new Trie_WordFilter();

        this.words = new ArrayList<>();
        Set<String> uniqueWords = new HashSet<>();

        for(int i = words.length-1; i>=0; i--){
            String word = words[i];
            if(!uniqueWords.contains(word)) {
                String reverseWord = reverse(word);

                this.words.add(word);
                saveInTrie(word, i, rootForWords);
                saveInTrie(reverseWord, i, rootForReverseWords);

                uniqueWords.add(word);
            }
        }
//        System.out.println("Print Trie For Words");
//        printTrie(rootForWords, "");
//        System.out.println("Print Trie For Reverse Words");
//        printTrie(rootForReverseWords, "");
    }

    private Set<String> getUniqueWords(String[] words) {
        Set<String> uniqueWords = new HashSet<>();
        for(int i = 0; i<words.length; i++)
            uniqueWords.add(words[i]);
        return uniqueWords;
    }

    private String reverse(String word) {
        StringBuilder wordBuilder = new StringBuilder(word);
        return wordBuilder.reverse().toString();
    }

    private void saveInTrie(String word, int index, Trie_WordFilter root) {
        Trie_WordFilter node = root;
        for(int i = 0; i<word.length(); i++){
            char c = word.charAt(i);
            int alphabetIndex = c - 97;

            if(node.currAlphabetSet[alphabetIndex] == null)
                node.currAlphabetSet[alphabetIndex] = new Trie_Indexes_WordFilter();
            node.currAlphabetSet[alphabetIndex].indexes.add(index);

            if(word.length()-1 == i){
                node.currAlphabetSet[alphabetIndex].endOfWordIndexes.add(index);
            }else {
                if(node.nextTrie[alphabetIndex] == null)
                    node.nextTrie[alphabetIndex] = new Trie_WordFilter();
                node = node.nextTrie[alphabetIndex];
            }
        }
    }

    public int f(String prefix, String suffix) {
        String reverseSuffix = reverse(suffix);
        Trie_Indexes_WordFilter prefixTrieIndexes = findIndexes(prefix, rootForWords);
        if(prefixTrieIndexes!=null) {
            Trie_Indexes_WordFilter suffixTrieIndexes = findIndexes(reverseSuffix,
                    rootForReverseWords);
            Set<Integer> intersectionOfSets = getIntersection(prefixTrieIndexes,
                    suffixTrieIndexes);
            //System.out.println("intersectionOfSets: "+intersectionOfSets);
            int largestWordIndex = findLargestIndex(intersectionOfSets,
                    prefix+suffix);
            return largestWordIndex;
        }else{
            return -1;
        }
    }

    private int findLargestIndex(Set<Integer> intersectionOfSets, String word) {
        int i = -1;
        int max = Integer.MIN_VALUE;
        if(intersectionOfSets!=null) {
            for (int index : intersectionOfSets) {
//                int equivalentIndex = this.wordIndexesToUniqueIndexInList.get(index);
                if (max < index) {
                    max = index;
                    i = index;
                }
            }
        }
        return i;
    }

    private Set<Integer> getIntersection(Trie_Indexes_WordFilter prefixTrieIndexes,
           Trie_Indexes_WordFilter suffixTrieIndexes) {
        if(prefixTrieIndexes!=null && suffixTrieIndexes!=null) {
            Set<Integer> intersection = new HashSet<>();
            if(intersection.isEmpty()){
                for (int index : prefixTrieIndexes.indexes) {
                    if (suffixTrieIndexes.indexes.contains(index)) {
                        intersection.add(index);
                    }
                }
            }
            return intersection;
        }
        return null;
    }


    private Trie_Indexes_WordFilter findIndexes(String prefixOrSuffix,
                          Trie_WordFilter node) {
        for(int i = 0; i<prefixOrSuffix.length(); i++){
            char c = prefixOrSuffix.charAt(i);
            int alphabetIndex = c - 97;
            if (node!=null && node.currAlphabetSet[alphabetIndex]!=null &&
                    !node.currAlphabetSet[alphabetIndex].indexes.isEmpty()) {
                if(prefixOrSuffix.length()-1>i)
                    node = node.nextTrie[alphabetIndex];
                else
                    return node.currAlphabetSet[alphabetIndex];
            }else{
                return null;
            }
        }
        return null;
    }

    private void printTrie(Trie_WordFilter root, String currWord) {
        if(root!=null) {
            for (int i = 0; i < root.currAlphabetSet.length; i++) {
                if (root.currAlphabetSet[i]!=null &&
                        !root.currAlphabetSet[i].indexes.isEmpty()) {
                    char c = (char) (i + 97);
                    for (int index : root.currAlphabetSet[i].indexes) {
                        currWord += c;
                        printTrieHelper(root.nextTrie[i], index, currWord);
                        currWord = "";
                    }
                }
            }
        }
    }

    private void printTrieHelper(Trie_WordFilter node, int index,
                                 String currWord) {
        for (int i = 0; i < node.currAlphabetSet.length; i++) {
            if (node!=null && node.currAlphabetSet[i]!=null &&
                    node.currAlphabetSet[i].indexes.contains(index)) {
                char c = (char) (i + 97);
                currWord += c;
                if (!node.currAlphabetSet[i].endOfWordIndexes.contains(index)) {
                    printTrieHelper(node.nextTrie[i], index, currWord);
                } else {
                    System.out.println(currWord);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if (choice == 1) {

                input.nextLine(); // garbage

                List<String> listOfWords = new ArrayList<>();
                String word;
                System.out.println("Enter Your Dictionary Of Words Array. " +
                        "To Stop Insertion In Word Array press xxx");
                word = input.nextLine();
                while(!word.equalsIgnoreCase("xxx")){
                    listOfWords.add(word);
                    word = input.nextLine();
                }
                String[] words = listOfWords.stream().toArray(String[]::new);
                WordFilter wordFilter = new WordFilter(words);

                System.out.println("Enter Prefix And Suffix Respectively For Stop Insertion " +
                        "Press xxx: ");
                List<String> listOfPrefixes = new ArrayList<>();
                List<String> listOfSuffixes = new ArrayList<>();
                String prefix_input = input.nextLine();
                String suffix_Input = input.nextLine();

                while(!prefix_input.equalsIgnoreCase("xxx") ||
                        !suffix_Input.equalsIgnoreCase("xxx")){

                    listOfPrefixes.add(prefix_input);
                    listOfSuffixes.add(suffix_Input);

                    prefix_input = input.nextLine();
                    suffix_Input = input.nextLine();
                }

                for (int i = 0; i<listOfPrefixes.size(); i++){

                    String prefix = listOfPrefixes.get(i);
                    String suffix = listOfSuffixes.get(i);
                    //System.out.println("Prefix: "+prefix+" Suffix: "+suffix);

                    int index = wordFilter.f(prefix, suffix);
                    System.out.print(index+", ");
//                    if(index!=-1)
//                     System.out.println("index of Word: "+index+" And Word: "+words[index]);
//                    else
//                       System.out.println("This Type Of Word Does Not Exist In Dictionary");
                }
            } else {

                String[] words = {"apple"};
                WordFilter wordFilter = new WordFilter(words);

                String []prefix_suffix = {"a", "e"};

                for (int i = 0; i<prefix_suffix.length; i = i + 2){
                    String prefix = prefix_suffix[i];
                    String suffix = prefix_suffix[i+1];
                    //System.out.println("Prefix: "+prefix+" Suffix: "+suffix);

                    int index = wordFilter.f(prefix, suffix);
                    System.out.print(index+", ");
//                    if(index!=-1)
//                        System.out.println("index of Word: "+index+" And Word: "+words[index]);
//                    else
//                        System.out.println("This Type Of Word Does Not Exist In Dictionary");
                }
            }
            System.out.println();
            t--;
        }
    }
}
