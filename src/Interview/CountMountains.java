package Interview;

import java.util.Scanner;

public class CountMountains {
    public static void main(String []arr){
        CountMountains countMountains = new CountMountains();
        Scanner input = new Scanner(System.in);
        int t = 123;
        while(t>0) {
            String str = input.nextLine();

            int ans = countMountains.func(str);
            System.out.println("ans = " + ans);
            t--;
        }
    }

    private int  func(String str) {
        int up = 0;
        int down = 0;
        int flag = 0;
        int mountains = 0;

        for(int i = 0; i<str.length(); i++){

            if (str.charAt(i) == 'U') {
                up++;
            }else{
                down++;
            }
            if(down>up){
                flag = 1;
            }
            if(up == down){
                up = 0;
                down = 0;
                if(flag == 0)
                    mountains++;
                flag = 0;
            }
        }
        return mountains;
    }
}
