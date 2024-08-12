package Solved;

import java.util.Scanner;
import java.util.Stack;

/*
https://leetcode.com/problems/tag-validator/

Input: <DIV>This is the first line <![CDATA[<div>]]></DIV>
Output: true
Explanation:
The code is wrapped in a closed tag : <DIV> and </DIV>.
The TAG_NAME is valid, the TAG_CONTENT consists of some characters and cdata.
Although CDATA_CONTENT has an unmatched start tag with invalid TAG_NAME, it should be considered as plain text, not parsed as a tag.
So TAG_CONTENT is valid, and then the code is valid. Thus return true.

Input: <DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>
Output: true
Explanation:
We first separate the code into : start_tag|tag_content|end_tag.
start_tag -> "<DIV>"
end_tag -> "</DIV>"
tag_content could also be separated into : text1|cdata|text2.
text1 -> ">>  ![cdata[]] "
cdata -> "<![CDATA[<div>]>]]>", where the CDATA_CONTENT is "<div>]>"
text2 -> "]]>>]"
The reason why start_tag is NOT "<DIV>>>" is because of the rule 6.
The reason why cdata is NOT "<![CDATA[<div>]>]]>]]>" is because of the rule 7.

Input: <A>  <B> </A>   </B>
Output: false
Explanation: Unbalanced. If "<A>" is closed, then "<B>" must be unmatched, and vice versa.

Input: <A></A><B></B>
Output: false

Input: <![CDATA[ABC]]><TAG>sometext</TAG>
Ouput: false
*/

class TagInfo_Tag_Validator {
    boolean isValid;
    String tagName;
    int closeIndex;
    int nextOption;
    /*
        nextOption = -1 notSure
        nextOption = 0 starTag
        nextOption = 1 endTag
        nextOption = 2 cdata
     */
    public TagInfo_Tag_Validator(){}

    public TagInfo_Tag_Validator(boolean isValid, String tagName, int closeIndex, int nextOption){
        this.isValid = isValid;
        this.tagName = tagName;
        this.closeIndex = closeIndex;
        this.nextOption = nextOption;
    }
    public void setValid(boolean valid) { this.isValid = valid;}

    public void setTagName(String tagName) { this.tagName = tagName;}

    public void setCloseIndex(int closeIndex) { this.closeIndex = closeIndex;}

    public void setNextOption(int nextOption) { this.nextOption = nextOption;}
}

public class Tag_Validator {

    public static void main(String[] args) {
        Tag_Validator tag_validator = new Tag_Validator();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String code = "";
            if (choice == 1) {
                System.out.println("Enter Code: ");
                input.nextLine();
                code = input.nextLine();
                boolean valid = tag_validator.isValid(code);
                System.out.println("Tag Valid: "+valid);
            } else {
                code = "<DIV>This is the first line <![CDATA[<div>]]></DIV>";
                boolean valid = tag_validator.isValid(code);
                System.out.println("Tag Valid: "+valid);
            }
            t--;
        }
    }

    // there should be only one independent tag. And all Other tags should be bounded inside first tag
    int independentTagCount = 0;
    public boolean isValid(String code) {
        if(code.length()>=7 && code.startsWith("<")) { // "<A></A>" // minimum length in valid code
            Stack<String> tagNameStack = new Stack<>();
            int maxTagCount = 0;
            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == '<') {
                    i = openAngularBracketStart(code, i+1, tagNameStack);
                    if(i == -1) {
                        independentTagCount = 0;
                        return false;
                    }
                }
                else{ // for any character check it should come inside some tag
                    if(tagNameStack.isEmpty()) {
                        independentTagCount = 0;
                        return false;
                    }
                }
                if(!tagNameStack.isEmpty() && maxTagCount<tagNameStack.size())
                    maxTagCount = tagNameStack.size();
            }
            independentTagCount = 0;
            // all nested stack should be balanced and there should be atLeast one tag in code
            if(tagNameStack.isEmpty() && maxTagCount>0) {
                return true;
            }
            return false;
        }
        return false;
    }

    private int openAngularBracketStart(String code, int startIndex, Stack<String> tagNameStack) {
        if(startIndex<=code.length()){
            // initially assume it is inValid
            TagInfo_Tag_Validator tagInfo = new TagInfo_Tag_Validator(false, "", -1, -1);
            nextOption(code, startIndex, tagInfo);
            /*
                nextOption = -1 notSure
                nextOption = 0 starTag
                nextOption = 1 endTag
                nextOption = 2 cdata
             */
            if(tagInfo.nextOption == 0)
                startTag(code, startIndex, tagInfo);
            else if(tagInfo.nextOption == 1) // startIndex+1 as startIndex = '/'
                endTag(code, startIndex+1, tagInfo);
            else if(tagInfo.nextOption == 2) // startIndex-1 = '<'
                cdata(code, startIndex-1, tagInfo);

            checkOpenAngularBracketValidity(tagInfo, tagNameStack);

            if(tagInfo.isValid){
                return tagInfo.closeIndex;
            }
        }
        return -1;
    }

    private void checkOpenAngularBracketValidity(TagInfo_Tag_Validator tagInfo,
                       Stack<String> tagNameStack) {
        // push or pop from stack depend upon nextOption value
        if(tagInfo.nextOption == 0) {
            tagNameStack.push(tagInfo.tagName);
            if(tagNameStack.size() == 1)
                independentTagCount++;
            // there should be only one independent tag. And all Other tags should be bounded inside first tag
            if(independentTagCount > 1){
                tagInfo.setValid(false);
                tagInfo.setCloseIndex(-1);
            }
        }
        else if(tagInfo.nextOption == 1) {
            if(!tagNameStack.isEmpty()){
                String prevOpenTag = tagNameStack.peek();
                if(!(prevOpenTag.equals(tagInfo.tagName))){ // nested tag should be balanced
                    tagInfo.setValid(false);
                    tagInfo.setCloseIndex(-1);
                }
                tagNameStack.pop();
            }
            else{  // endTag must come after startTag
                tagInfo.setValid(false);
                tagInfo.setCloseIndex(-1);
            }
        }else if(tagInfo.nextOption == 2){ // cdata must come inside tag
            if(tagNameStack.isEmpty()) {
                tagInfo.setValid(false);
                tagInfo.setCloseIndex(-1);
            }
        }
    }

    private void cdata(String code, int startIndex, TagInfo_Tag_Validator tagInfo) {
        cdataPrefixExist(code, startIndex, tagInfo);
        if(tagInfo.isValid){
            startIndex = tagInfo.closeIndex + 1;
            // again assume it as false till we find CDATA Suffix
            tagInfo.setValid(false);
            tagInfo.setCloseIndex(-1);
            cdataSuffix(code, startIndex, tagInfo);
        }
    }

    private void cdataSuffix(String code, int startIndex, TagInfo_Tag_Validator tagInfo) {
        String cdataSuffix = "]]>";
        if(startIndex<code.length()){
            for(int i = startIndex; i<code.length(); i++){
                if(i+2<code.length() && code.charAt(i) == ']' && code.charAt(i+1) == ']' && code.charAt(i+2) == '>'){
                    tagInfo.setValid(true);
                    tagInfo.setCloseIndex(i+2);
                    return;
                }
            }
        }
    }

    private void cdataPrefixExist(String code, int startIndex, TagInfo_Tag_Validator tagInfo) {
        String cdataPrefix = "<![CDATA[";
        if (startIndex + (cdataPrefix.length() - 1) < code.length()) {
            int endIndex = startIndex + cdataPrefix.length();
            String codeCdata = code.substring(startIndex, endIndex);
            if (codeCdata.equals(cdataPrefix)) {
                tagInfo.setValid(true);
                tagInfo.setCloseIndex(endIndex - 1);
            }
        }
    }

    private void endTag(String code, int startIndex, TagInfo_Tag_Validator tagInfo) {
        tagName(code, startIndex, tagInfo);
    }

    private void  startTag(String code, int startIndex, TagInfo_Tag_Validator tagInfo) {
        tagName(code, startIndex, tagInfo);
    }

    private void tagName(String code, int startIndex, TagInfo_Tag_Validator tagInfo) {
        StringBuilder tagName = new StringBuilder();
        int count = 0;
        for(int i = startIndex; i<code.length(); i++){
            int ascii = code.charAt(i);
            if(ascii>=65 && ascii<=90){
                tagName.append(code.charAt(i));
                count++;
            }
            else if(code.charAt(i) == '>'){
                if(tagName.length()>0) {  // helpful for so that tagName is not Empty tagName = <> (inValid)
                    tagInfo.setTagName(tagName.toString());
                    tagInfo.setCloseIndex(i);
                    tagInfo.setValid(true);
                }
                break;
            }else{ // only upper case letters are allowed
                break;
            }
            if(count==10){ // tag name length should not exceed 10
                break;
            }
        }
    }

    private TagInfo_Tag_Validator nextOption(String code, int startIndex, TagInfo_Tag_Validator tagInfo) {
        /*
            nextOption = -1 notSure
            nextOption = 0 starTag
            nextOption = 1 endTag
            nextOption = 2 cdata
        */
        int ascii = code.charAt(startIndex);
        if(ascii>=65 && ascii<=90)
            tagInfo.nextOption = 0;
        else if(code.charAt(startIndex) == '/')
            tagInfo.nextOption = 1;
        else if(code.charAt(startIndex) == '!')
            tagInfo.nextOption = 2;
        return tagInfo;
    }
}
