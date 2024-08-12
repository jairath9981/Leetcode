package JavaConcepts;

/*

*/
public class TypeConversion {
    public static void main(String[] args) {
        short a = 10;
        short b = 10;
        /*   Java don't allow Automatic data loss conversions.
             So, All Operators(*,+,-,/,%,^) wants to give result in bigger Data type. So that more
             precise result can be evaluated(without data loss). (Called binary numeric promotion)
             So "[short a = 10; a =    a       *        5;]"
                                    short(a) with(*) int(5) = int
             Rectification:
             int a = 5;
             a = a*5;
         */
        //a = a*5;
        // Explicit conversion
         a = (short) (a * 5);
         // use compound assignment, which implicitly casts;
        b*=5;
        System.out.println(a + "   " + b);
    }
}
