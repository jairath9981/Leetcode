package Interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AnagramCollection {
    public static void main(String []args){
        String userStr = "cat dog act reserve reverse boy reveres";
        Map<Integer, Set<Integer>> answer = collectAnagram(userStr);
    }

    private static void printMap(Map<Integer, Set<Integer>> answer, String[] userWords) {
        for(Map.Entry<Integer, Set<Integer>> entry: answer.entrySet()){
            int index = entry.getKey();
            Set<Integer> anagrams = entry.getValue();
            System.out.print(userWords[index]+" ");
            for (Integer i: anagrams){
                System.out.print(userWords[i]+" ");
            }
            System.out.println();
        }
    }

    private static Map<Integer, Set<Integer>> collectAnagram(String userStr) {
        String[] userWords = userStr.split(" ");
        List<List<Integer>>arr = createCharacterInfo(userWords);
        Map<Integer, Set<Integer>> answer = collectAnagramHelper(userWords, arr);
        printMap(answer, userWords);
        return answer;
    }

    private static Map<Integer, Set<Integer>> collectAnagramHelper(String[] userWords,
                       List<List<Integer>> arr) {
        Map<Integer, Set<Integer>> answer = new HashMap<>();
        Set<Integer> usedWords = new HashSet<>();
        for (int i = 0; i<userWords.length-1; i++){
            Set<Integer> answerForCurrWord = new HashSet<>();
            if(!usedWords.contains(i)) {
                for (int j = i + 1; j < userWords.length; j++) {
                    if(isAnagram(i,j, arr)){
                        usedWords.add(j);
                        answerForCurrWord.add(j);
                    }
                }
                if(!answerForCurrWord.isEmpty())
                    answer.put(i, answerForCurrWord);
            }
        }
        return answer;
    }

    private static boolean isAnagram(int i, int j, List<List<Integer>> arr) {
        List<Integer> str1 = arr.get(i);
        List<Integer> str2 = arr.get(j);
        for(int k = 0; k<str1.size(); k++){
            int val = str1.get(k);
            if(!(val == str2.get(k))){
                return false;
            }
        }
        return true;
    }

    private static List<List<Integer>> createCharacterInfo(String[] userWords) {
        List<List<Integer>> characterIno = new ArrayList<>();

        for(int i = 0; i<userWords.length; i++){
            String str = userWords[i];
            List<Integer> currWordInfo = new ArrayList<>(26);
            for(int j = 0; j<26; j++)
                currWordInfo.add(0);
            for(int j = 0; j<str.length(); j++){
                int index = str.charAt(j)-97;
                currWordInfo.set(index,  currWordInfo.get(index)+1);
            }
            characterIno.add(currWordInfo);
        }
        return characterIno;
    }

}
