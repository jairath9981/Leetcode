package Solved;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/*
https://leetcode.com/problems/parse-lisp-expression/

Input 1:
(let x 2 (mult x (let x 3 y 4 (add x y))))
Input 1 meaning: expression = "(let x 2 (mult x (let x 3 y 4 (add x y))))"
Output: 14
Explanation: In the expression (add x y), when checking for the value of the variable x,
we check from the innermost scope to the outermost in the context of the variable we are trying to evaluate.
Since x = 3 is found first, the value of x is 3.

Input 2:
(let x 3 x 2 x)
Output: 2
Explanation: Assignment in let statements is processed sequentially.

Input 3:
(let x 1 y 2 x (add x y) (add x y))
Output: 5
Explanation: The first (add x y) evaluates as 3, and is assigned to x.
The second (add x y) evaluates as 3+2 = 5.

Input 4: (let x 7 -12)
Output: -12

Input 5: (let x0 -4 x1 1 x2 -1 x3 -1 x4 3 x5 1 x6 -4 x7 -1 x8 -5 x9 3 (let x0 -5 x2 -2 x4 -4 x6 -4 x8 0 (let x0 3 x3 -1 x6 4 x9 -2 (let x0 0 x4 -3 x8 -2 (add (add x4 (let x0 -5 x7 1 (let x0 -2 x8 -2 (mult x2 x7)))) x0)))))
Output: -5
*/

enum Operator_Parse_Lisp_Expression {
    LET("let"),
    ADD("add"),
    MULTIPLICATION("mult"),
    UNKNOWN("unknown");
    private String operator;

    private Operator_Parse_Lisp_Expression(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return this.operator;
    }

}

class Evaluate_Parse_Lisp_Expression{
    int ans;
    String operation;
    int a;
    int b;
    boolean aFilled;
    boolean bFilled;
    String lastVariable;
    String prevVariable;
    Map<String, Integer> values;

    Evaluate_Parse_Lisp_Expression(){
        values = new HashMap<>();
        aFilled = false;
        bFilled = false;
    }

    Evaluate_Parse_Lisp_Expression(String operation){
        this.operation = operation;
        aFilled = false;
        bFilled = false;
        values = new HashMap<>();
    }

    public void setValues(Map<String, Integer> prevMap) {
        if(this.values == null)
            this.values = new HashMap<>();
        for (Map.Entry<String, Integer> entry : prevMap.entrySet()) {
            this.values.put(entry.getKey(), entry.getValue());
        }
    }
}

public class Parse_Lisp_Expression {
    public static void main(String[] args) {
        Parse_Lisp_Expression parse_expression = new Parse_Lisp_Expression();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String expression;
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter Expression Which You Want To Evaluate = ");
                expression = input.nextLine();
                int ans = parse_expression.evaluate(expression);
                System.out.println("Evaluated Ans: " + ans);
            } else {
                expression = "(let x 2 (mult x (let x 3 y 4 (add x y))))";
                int ans = parse_expression.evaluate(expression);
                System.out.println("Evaluated Ans: " + ans);
            }
            t--;
        }
    }

    public int evaluate(String expression) {
        if(expression.length()>0){
            Evaluate_Parse_Lisp_Expression evaluateExpression  =
                    evaluateAllBrackets(expression);
            return evaluateExpression.ans;
        }
        return -1;
    }

    private Evaluate_Parse_Lisp_Expression evaluateAllBrackets(String expression) {
        Stack<Evaluate_Parse_Lisp_Expression> stack = new Stack<>();
        Evaluate_Parse_Lisp_Expression evaluate_ans = new Evaluate_Parse_Lisp_Expression();
        for(int i = 0; i<expression.length(); i++){
            int[] tempi = new int[1];
            tempi[0] = i;
            if(expression.charAt(tempi[0]) == '(') {
                tempi[0]++;
                Operator_Parse_Lisp_Expression operator =
                        findOperatorType(expression, tempi[0]);
                switch (operator){
                    case LET:
                        tempi[0] = tempi[0] + operator.getOperator().length();
                        Evaluate_Parse_Lisp_Expression evaluateLet =
                                new Evaluate_Parse_Lisp_Expression(operator.getOperator());
                        deepCopyMap(evaluateLet, stack);
                        evaluateLetStartBracket(expression, tempi, evaluateLet);
                        stack.add(evaluateLet);
                        break;
                    case MULTIPLICATION:
                    case ADD:
                        tempi[0] = tempi[0] + operator.getOperator().length();
                        Evaluate_Parse_Lisp_Expression evaluateObj =
                                new Evaluate_Parse_Lisp_Expression(operator.getOperator());
                        deepCopyMap(evaluateObj, stack);
                        evaluateOperatorBracket(expression, tempi, evaluateObj);
                        stack.add(evaluateObj);
                        break;
                    default:
                        System.out.println("Something Bad Happens Need To Check");
                }
            }
            if(expression.charAt(tempi[0]) == ')') {
                evaluate_ans = stack.pop();
                mergeWithPrevious(expression, stack, evaluate_ans, tempi);
            }
            i = tempi[0];
        }
        return evaluate_ans;
    }

    private void mergeWithPrevious(String expression, Stack<Evaluate_Parse_Lisp_Expression> stack,
         Evaluate_Parse_Lisp_Expression prevAns, int[] tempi) {
        if(!stack.isEmpty()){
            if(stack.peek().operation.equals(Operator_Parse_Lisp_Expression.LET.getOperator())){
                mergeAnsWithLetOperator(prevAns.ans, stack.peek());
                tempi[0]++;
                evaluateLetStartBracket(expression, tempi, stack.peek());
            }else{
                mergeAnsWithOperator(prevAns.ans, stack.peek());
                tempi[0]++;
                evaluateOperatorBracket(expression, tempi, stack.peek());
            }
        }
    }

    private void mergeAnsWithLetOperator(int ans,
           Evaluate_Parse_Lisp_Expression evaluateObj) {
        if(!evaluateObj.lastVariable.isEmpty()){
            evaluateObj.values.put(evaluateObj.lastVariable, ans);
            evaluateObj.ans = ans;
            evaluateObj.lastVariable = "";
        }else{
            evaluateObj.ans = ans;
        }
    }

    private void mergeAnsWithOperator(int ans, Evaluate_Parse_Lisp_Expression evaluateObj) {
        if(!evaluateObj.aFilled){
            evaluateObj.a = ans;
            evaluateObj.aFilled = true;
            return;
        }else if(!evaluateObj.bFilled){
            evaluateObj.b = ans;
            evaluateObj.bFilled = true;
            getAnsForOperator(evaluateObj);
            return;
        }
    }

    private void deepCopyMap(Evaluate_Parse_Lisp_Expression evaluateObj,
           Stack<Evaluate_Parse_Lisp_Expression> stack) {
        if(!stack.isEmpty()){
            evaluateObj.setValues(stack.peek().values);
        }
    }

    private Operator_Parse_Lisp_Expression findOperatorType(String expression, int i) {
        if(findOperatorHelper(expression, i,
                Operator_Parse_Lisp_Expression.LET))
            return Operator_Parse_Lisp_Expression.LET;
        else if(findOperatorHelper(expression, i,
                Operator_Parse_Lisp_Expression.MULTIPLICATION))
            return Operator_Parse_Lisp_Expression.MULTIPLICATION;
        else if(findOperatorHelper(expression, i,
                Operator_Parse_Lisp_Expression.ADD))
            return Operator_Parse_Lisp_Expression.ADD;
        return Operator_Parse_Lisp_Expression.UNKNOWN;
    }

    private boolean findOperatorHelper(String expression, int index,
               Operator_Parse_Lisp_Expression operator) {
        String operatorType = operator.getOperator();
        String subStr = expression.substring(index, index+operatorType.length());
        if(operatorType.equals(subStr))
            return true;
        return false;
    }

    /*
        (mult 1 1)
        (mult x y)
        (mult x (add 1 1)) // 2nd value b needs to find by solving add
        (mult (add 1 1) (add 1 1))
        '''' same for add()
     */
    private void evaluateOperatorBracket(String expression, int[] tempi,
       Evaluate_Parse_Lisp_Expression evaluateObj) {
        for (int i = tempi[0]; i<expression.length(); ) {
            if (expression.charAt(i) == ' ') {
                i++;
                if(isVariable(expression, i)){
                    tempi[0] = i; //variable name starts from here
                    String variable = getVariableName(expression, tempi);
                    i = tempi[0]; //space or ')' Index after getting variable name
                    fillVariablesOrAns(evaluateObj, variable, 0, 1, 0);
                }else if(isValue(expression, i)){
                    tempi[0] = i; //variable value starts from here
                    int value = getValue(expression, tempi);
                    fillVariablesOrAns(evaluateObj, "", value, 0, 1);
                    i = tempi[0]; //space or ')' Index after getting variable name
                }
            }
            else if(expression.charAt(i) == '(' || expression.charAt(i) == ')'){
                /* stop At one index before ')' as need to
                solveBy Parent Loop evaluateAllBrackets it will also increase i++
               */
                tempi[0] = i-1;
                break;
            }
        }
    }

    private void fillVariablesOrAns(Evaluate_Parse_Lisp_Expression evaluateObj,
            String variable, int value, int variableFlag, int valueFlag) {
        if(variableFlag == 1){
            if(!evaluateObj.aFilled){
                evaluateObj.a = evaluateObj.values.get(variable);
                evaluateObj.aFilled = true;
            }else{
                evaluateObj.b = evaluateObj.values.get(variable);
                evaluateObj.bFilled = true;
                getAnsForOperator(evaluateObj);
            }
        }else{
            if(!evaluateObj.aFilled){
                evaluateObj.a = value;
                evaluateObj.aFilled = true;
            }else{
                evaluateObj.b = value;
                evaluateObj.bFilled = true;
                getAnsForOperator(evaluateObj);
            }
        }
    }

    private void getAnsForOperator(Evaluate_Parse_Lisp_Expression evaluateObj) {
        if(evaluateObj.operation.equals(Operator_Parse_Lisp_Expression.ADD.getOperator())){
            evaluateObj.ans = evaluateObj.a + evaluateObj.b;
            return;
        }
        if(evaluateObj.operation.equals(Operator_Parse_Lisp_Expression.MULTIPLICATION.getOperator())){
            evaluateObj.ans = evaluateObj.a * evaluateObj.b;
            return;
        }
    }

    // let x 5 our index is at space before x
    /*
        (let x 5 y 2 y)
        (let x (add 1 1) x) // value need to find by solving another expression
        (let x 2 x (add 4 4) x)// value need to be Updated
        (let x 2)// let expression can end after giving value ans is 2 here
        (let x 2 y 2 (add x y))
     */
    private void evaluateLetStartBracket(String expression, int []tempi,
               Evaluate_Parse_Lisp_Expression evaluateLet) {
        for (int i = tempi[0]; i < expression.length(); ) {
            if (expression.charAt(i) == ' ') { // space after let , space after variable name let x
                i++;
                if(evaluateLet.lastVariable == null || evaluateLet.lastVariable.isEmpty()){ // need to find variableName first then its value
                    /*
                        after getting value we get space but there is no variable Name
                        there after (let x 2 y 2 (add x y))
                     */
                    if(isVariable(expression, i)){
                        tempi[0] = i; // VariableName Starts From here
                        String variable = getVariableName(expression, tempi);
                        evaluateLet.lastVariable = variable;
                        i = tempi[0]; //space or ')' Index after getting variable name
                        //space or ')' Index after getting variable name
                    /*
                        1 (let x (add 1 1))
                        after space may be expression need to solve for variable value(checking in next iteration in else block)
                        2 (let x 2)
                        after space may be actual value exist(also checking in next iteration in else block)
                        3 (let x 2 x)
                        after ) let expression ends
                     */
                    }else if(isValue(expression, i)){ // override previous variable value (let x 7 -12) // ans -12
                        tempi[0] = i;
                        int value = getValue(expression, tempi); // space or ')' Index after getting variable value
                        evaluateLet.values.put(evaluateLet.prevVariable, value);
                        evaluateLet.ans = value;
                        i = tempi[0];
                    }else{
                        tempi[0] = i - 1; // (let x 2 y 2 (add x y)) need to fill add ans in let ans
                        break;
                    }
                }
                else{ // try to find variable value
                    if(isValue(expression, i)){
                        tempi[0] = i;
                        int value = getValue(expression, tempi); // space or ')' Index after getting variable value
                        evaluateLet.values.put(evaluateLet.lastVariable, value);
                        evaluateLet.prevVariable = evaluateLet.lastVariable;
                        evaluateLet.lastVariable = "";
                        evaluateLet.ans = value;
                        i = tempi[0]; //space or ')' Index after getting variable value
                    }else { // value need to find by solving expression
                        tempi[0] = i - 1; // i - 1 == '(' -> let x (add 1 1)
                        break;
                    }
                }
            }
            /*
                after getting variable name let expression end (let x 5 y 2 y)
                after getting value let expression end (let x 5) // ans 5 in this case
             */
            else if(expression.charAt(i) == ')'){
                /* stop At one index before ')' as need to
                solveBy Parent Loop evaluateAllBrackets as it will also increase i++
               */
                tempi[0] = i - 1;
                break;
            }
        }
    }

    private boolean isVariable(String expression, int i) {
        if(isValue(expression, i))
           return false;
        if(expression.charAt(i) == '(')
            return false;
        if(expression.charAt(i) == ')')
            return false;
        return true;
    }

    private boolean isValue(String expression, int i) {
        if(expression.charAt(i) == '-')
            return true;
        if(expression.charAt(i)>=48 && expression.charAt(i)<=57)
            return true;
        return false;
    }

    private int getValue(String expression, int[] arri) {
        String value = "";
        for (int i = arri[0]; i<expression.length(); i++){
            if(expression.charAt(i) == ' ' || expression.charAt(i) == ')'){
                arri[0] = i;
                break;
            }else{
                value+=expression.charAt(i);
            }
        }
        return Integer.parseInt(value);
    }

    private String getVariableName(String expression, int[] arri) {
        String variable = "";
        for (int i = arri[0]; i<expression.length(); i++){
            if(expression.charAt(i) == ' ' || expression.charAt(i) == ')'){
                arri[0] = i;
                break;
            }else{
                variable+=expression.charAt(i);
            }
        }
        return variable;
    }
}
