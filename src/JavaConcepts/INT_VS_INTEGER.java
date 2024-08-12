package JavaConcepts;

class Helper{
    int getA(int i){
        return i;
    }
    int getA(Integer i){
        return i+2;
    }
}

public class INT_VS_INTEGER {
    public static void main(String[] args) {
        Helper helper = new Helper();

        int i = 2;
        Integer j = 5;
        int x = helper.getA(i);
        int y = helper.getA(j);
        helper.getA(i);
        System.out.println(x+"   "+y);
    }
}
