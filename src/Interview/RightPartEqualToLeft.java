package Interview;

/*
abcdefu
a+b+c+d+e+f = 21
u=21

sum = 42
1   41
3   39
6   36
10  32
15  27
21  21

a bcdefu
ab cdefu
abc defu
abcd efu
abcde fu
abcdef u

abcdefu
lll   r
*/


public class RightPartEqualToLeft {
    public static void main(String[] args) {
        RightPartEqualToLeft solution2 = new RightPartEqualToLeft();
        String str = "abcdefu";

        boolean isValid = solution2.leftRightSumEqual(str);
        System.out.println("Right-Left Sum Possible: "+isValid);
    }

    private boolean leftRightSumEqual(String str) {
        int sum = 0;
        for(int i = 0; i<str.length(); i++){
            sum+=str.charAt(i)-96;
        }
        //System.out.println("Sum = "+sum);
        int leftSum = 0, rightSum = sum;

        for(int i = 0; i<str.length(); i++){
            int x = str.charAt(i)-96;
            leftSum+=x;
            rightSum-=x;
            //System.out.println(leftSum+"   "+rightSum);
            if(leftSum==rightSum)
                return true;
        }
        return false;
    }
}
