package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/

Input 1:
1 2 1 2 6 7 5 1 -999
2
Input meaning nums = [1,2,1,2,6,7,5,1], k = 2
Output: [0,3,5]
Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.


Input 2:
1 2 1 2 1 2 1 2 1 -999
2
Output: [0,2,4]

Input 3:
17 7 19 11 1 19 17 6 13 18 2 7 12 16 16 18 9 3 19 5 -999
6
Output: [0,6,13]

Input 4:
62368  43415  6411  30431  47476  43988  57619  4131  15320  8470  12424  32500  37914  256  54892  64542  44556  63623  32488  38391  21821  814  17407  25008  5503  20233  44981  21616  17906  34260  33045  6535  64008  53390  1376  44651  16181  18292  50643  15050  45411  5124  30452  9018  31760  7046  39294  51894  43549  15092  42683  31095  982  40803  44869  30685  58595  54124  5746  49021  37208  45952  34455  1884  19642  63556  21510  17701  25556  52905  29963  2716  17966  22775  56535  15474  11496  15975  34816  14486  53517  55132  2444  7493  50678  40509  20774  35932  9936  12255  54904  3246  60422  39236  21118  10657  50107  53038  4168  53669  26736  40270  45504  35101  1857  34941  63136  30522  52761  36214  41522  51187  25016  62798  55606  17470  12428  49736  53277  37848  45604  21964  58413  54931  38205  19175  51970  9769  4683  50149  23165  15010  6584  55412  59918  53641  64475  18216  47808  21116  29770  9262  31961  24894  20219  16035  60933  19904  15193  33039  12544  14650  29748  22824  6541  48083  3027  30777  55442  33284  54358  17329  10445  30616  16699  10617  41450  52177  60769  30161  31747  1537  8882  26213  380  58124  16460  37154  45252  65258  8795  36606  35192  34215  21100  39898  17820  16225  31017  23330  19812  14809  56587  50279  46723  25526  57230  15351  10101  55003  -999
40
Output: [26, 99, 157]

Input 5:
4108  3526  60684  28792  48308  3726  46426  57170  50613  12759  39083  28946  40225  17025  22278  63454  60347  21802  5515  63312  1637  20145  51303  62239  53393  8281  1464  27197  36875  48754  28133  39919  8762  35366  53519  41706  36767  4580  54368  42416  37089  44310  10877  12938  43000  3473  65283  29803  39119  12170  35424  54809  4558  55989  55518  61703  12691  62432  7558  231  40726  24997  6476  33026  24067  44630  33825  25676  30960  64027  12992  48628  21785  37226  28144  35138  39098  23012  61560  10987  14668  45351  9983  60578  59421  41195  64344  17663  19787  33226  35928  34004  40892  39853  62703  55931  41958  4690  19265  9975  10012  7547  57377  10503  62959  18492  33569  3173  54918  6997  34013  51295  27065  29639  49147  28640  21134  40965  30653  42829  48103  32779  40378  63553  10189  12390  52626  46668  49164  57457  6000  557  37961  20419  58904  34675  51604  19953  41255  17603  39515  43268  36689  13626  12657  26981  38486  1905  37824  353  27037  40451  28883  11561  41457  57059  868  46983  1415  8168  2604  34961  55139  23381  36016  55895  3406  49552  20219  43863  62044  14969  52263  32484  9164  63232  53407  39087  23266  28778  4983  25106  45590  43467  58117  33467  13351  40574  64942  36760  14235  63974  37681  56399  33843  18432  3940  26582  39905  45032  29576  5360  33279  16730  65000  47495  43200  48344  15387  13234  35623  59542  32093  8385  24534  26749  12006  54145  14017  10641  22811  19623  43048  57166  31203  10219  47512  5762  53845  29160  5132  37283  61256  54034  53063  15781  7374  13600  24807  26689  37701  13318  24599  139  47278  62838  13677  41574  29020  14709  9099  13885  45206  57808  2925  19616  51631  45251  50203  43362  16959  55289  19397  43968  29786  16589  9338  23715  40906  42258  8674  63235  23199  28297  19835  336  2677  17992  58533  27454  51619  27124  41717  15311  49266  41097  9503  12970  52481  4124  55202  25200  56006  28252  64022  34922  53058  22482  53086  15539  60445  34526  40984  63875  19159  19007  55242  40829  59693  13307  58555  3139  57810  58336  21427  51905  10279  14160  30612  42712  24829  50977  57157  38760  3169  62319  15183  63871  29417  13369  47741  29115  12289  42631  37367  63966  21691  18537  6164  7928  22371  3655  26643  62495  34514  48297  12729  600  42217  53204  17944  22110  17459  20932  2403  3659  28293  14462  29549  52021  52235  44650  29797  12380  19931  38357  51678  42913  5291  51479  65321  12953  46354  52015  9003  28556  31596  1124  30499  12861  54679  47237  14538  49552  22729  29041  36856  51955  2373  54284  51208  1605  9710  45274  62395  13294  44491  50922  20113  29615 -999
45
Output: [51, 162, 291]

Input 6:
24528  57871  19898  1758  33754  24616  23739  43277  41106  23864  19729  22087  33466  62107  17895  43062  28084  25617  17544  64563  984  36108  47965  18707  6918  62831  41421  10772  24292  62913  15378  43870  27927  58125  26048  2863  9546  5777  33268  5569  63342  3165  5401  51993  65486  44805  61797  56193  9168  56740  28041  25742  50966  14812  11182  32743  15089  48626  16267  22910  54840  22359  7819  44420  26608  23233  15731  34203  54027  4757  29409  38400  41431  51389  22963  33939  23714  32766  5418  34654  23088  13067  22509  32023  46101  25373  12784  45970  8491  47975  54694  34145  43196  1266  24406  25434  54156  62629  33103  12035  61735  50620  23676  54228  9146  20955  13973  9170  48528  44769  53729  61336  61327  31143  61027  49892  6233  37226  15691  20277  34734  42859  7787  2659  12618  5882  57048  45747  46553  7770  25272  36073  36238  6628  40541  22907  26696  41809  57818  42419  901  64213  35137  55251  50816  35637  29984  54133  52616  24837  15251  1189  21364  16190  24363  51572  41431  55667  54017  45494  45081  56340  415  56196  4306  51682  9062  127  27360  8077  56097  28056  27970  22055  4863  59580  173  31551  47459  58100  27388  46282  20082  57443  14933  32232  40058  11010  31566  57670  60305  43393  64072  57964  56996  3600  11440  17306  15482  56875  31398  58317  21187  47554  4212  10675  35355  63072  33556  16809  14575  33764  50120  44681  20051  29281  64449  10167  24238  21354  43997  45527  11021  47321  35311  12853  56145  33451  48426  44567  26923  30221  40282  24603  25846  3757  10713  24624  25690  981  51246  41713  23127  1412  2563  19622  4880  47447  45207  24272  51548  25776  21563  36829  58358  11115  11800  42296  22809  56411  28094  14882  28355  8014  1861  49316  48937  37292  61000  24498  49920  54284  62774  64243  1815  36635  12660  63245  22066  36972  26030  18763  13887  13791  8234  14695  51965  54633  48387  64049  27003  24964  36625  56783  32235  18241  51872  45570  24459  45310  57223  2092  16899  31442  26668  38679  12482  64669  52105  19295  21034  15497  44653  12517  48231  25578  1921  10124  32613  31377  51061  64429  50925  38969  58007  30261  61830  10361  5664  38562  37200  1306  57218  38745  6322  20013  60309  13625  13859  27381  64696  37226  38517  18896  7100  36906  8602  42012  35197  769  799  38901  24361  54764  13430  63630  30228  32489  5681  61875  45352  45049  33413  37518  17266  4099  15321  28577  48831  26488  55289  48176  60040  42545  27674  592  53335  69  39186  12581  48241  2506  1258  6790  14093  63459  64417  47944  48189  62111  55906  31454  14120  6015  52772  53808  13185  21925  43541  38481 -999
85
Output: [41, 137, 248]
*/



class CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays{

    long sum;
    List<Integer> start;

    CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays(){
        sum = 0;
        start = new ArrayList<>();
    }
}


public class Maximum_Sum_of_3_Non_Overlapping_Subarrays {
    public static void main(String[] args) {
        Maximum_Sum_of_3_Non_Overlapping_Subarrays maximum_sum_of_3_subArray =
                new Maximum_Sum_of_3_Non_Overlapping_Subarrays();
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
            if(choice == 1) {

                int k, x;
                List<Integer> listOfNum = new ArrayList<>();
                System.out.println("Enter Your Positive Num Array, For Stop Insertion Press -999");
                x = input.nextInt();
                while(x!=-999) {
                    listOfNum.add(x);
                    x = input.nextInt();
                }
                System.out.println("Enter SubArray Length For 3 SubArray Max Sum: ");
                k = input.nextInt();
                int[] nums = listOfNum.stream().mapToInt(i->i).toArray();
                int []ans = maximum_sum_of_3_subArray.maxSumOfThreeSubarrays(nums, k);
                System.out.println("3 SubArray Starting Indexes For Max Sum:");
                for (int i = 0; i<=1; i++){
                    System.out.print(ans[i] +", ");
                }
                System.out.print(ans[2]);
                System.out.println();
            }
            else
            {
                int [] nums = {1,2,1,2,6,7,5,1};
                int k = 2;
                int []ans = maximum_sum_of_3_subArray.maxSumOfThreeSubarrays(nums, k);
                System.out.println("3 SubArray Starting Indexes For Max Sum:");
                for (int i = 0; i<=1; i++){
                    System.out.print(ans[i] +", ");
                }
                System.out.print(ans[2]);
                System.out.println();
            }
            t--;
        }
    }

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {

        //System.out.println("nums size = "+nums.length);
        List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult1 =
                new ArrayList<>();
        List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult2 =
                new ArrayList<>();
        List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult3 =
                new ArrayList<>();

        construct3SeparateLists(nums, k, listOfResult1, listOfResult2, listOfResult3);

        int []result = createAnswer(listOfResult3);
        //printResultList(listOfResult3, k);
        return result;
    }

    private int[] createAnswer(
       List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult3) {

        int[] result = new int[3];

        int index = listOfResult3.size() - 1;
        CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays collectMaxSum3Array =
                listOfResult3.get(index);

        List<Integer> listOfStarting = collectMaxSum3Array.start;
        for (int j = 0; j<listOfStarting.size(); j++){
            int  start = listOfStarting.get(j);
            result[j] = start;
        }
        return result;
    }


    private void construct3SeparateLists(int[] nums, int k,
        List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult1,
        List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult2,
        List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult3) {

        int n = nums.length -1;
        int s1 = 0, e1 = n-2*k;
        int s2 = k, e2 = n-k;
        int s3 = 2*k, e3 = n;

        long sum = 0;

        for(int i = 0; i<k-1; i++)
            sum+=nums[i];

        for(int i = k-1; i<nums.length; i++){

            sum+=nums[i];

            int endIndex = i;
            int startIndex = endIndex - (k - 1);

            if((s1<=startIndex && e1>=endIndex) || (s2<=startIndex && e2>=endIndex) ||
                    (s3<=startIndex && e3>=endIndex)) {

                addInSeparateList(listOfResult1, listOfResult2, listOfResult3,
                        s1, e1, s2, e2, s3, e3, sum, startIndex, endIndex, k);
            }
            sum = sum - nums[(i-k)+1];

        }
    }

    private void addInSeparateList(
            List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult1,
            List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult2,
            List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult3,
            int s1, int e1, int s2, int e2, int s3, int e3,
            long sum, int startIndex, int endIndex, int k) {

        if(s1<=startIndex && e1>=endIndex){

            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays collectMaxSum3Array =
                    new CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays();

            createCollectMaxSum3Array(collectMaxSum3Array, startIndex, sum);
            addInList(listOfResult1, collectMaxSum3Array);

        }

        if(s2<=startIndex && e2>=endIndex){

            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays collectMaxSum3Array =
                    new CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays();

            int indexFromList1 = (endIndex - k) - (k - 1);
            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays objOfList1 =
                    listOfResult1.get(indexFromList1);

            collectMaxSum3Array.sum = objOfList1.sum;

            int start1 = objOfList1.start.get(0);
            collectMaxSum3Array.start.add(start1);

            createCollectMaxSum3Array(collectMaxSum3Array, startIndex, sum);
            addInList(listOfResult2, collectMaxSum3Array);

        }

        if(s3<=startIndex && e3>=endIndex){

            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays collectMaxSum3Array =
                    new CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays();

            int indexFromList2 = (endIndex - k) - (2*k) + 1;
            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays objOfList2 =
                    listOfResult2.get(indexFromList2);

            collectMaxSum3Array.sum = objOfList2.sum;

            int start1 = objOfList2.start.get(0);
            collectMaxSum3Array.start.add(start1);

            int start2 = objOfList2.start.get(1);
            collectMaxSum3Array.start.add(start2);

            createCollectMaxSum3Array(collectMaxSum3Array, startIndex, sum);
            addInList(listOfResult3, collectMaxSum3Array);

        }
    }

    private void addInList(
            List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult,
            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays currForList) {
        if(listOfResult.isEmpty()){
            listOfResult.add(currForList);
        }else{
            int lastIndex = listOfResult.size() - 1;
            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays prevForList =
                    listOfResult.get(lastIndex);
            if(prevForList.sum>=currForList.sum)
                listOfResult.add(prevForList);
            else
                listOfResult.add(currForList);
        }
    }

    private void createCollectMaxSum3Array(
       CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays collectMaxSum3Array,
       int startIndex, long sum) {

        collectMaxSum3Array.start.add(startIndex);
        collectMaxSum3Array.sum+=sum;
    }

    private void printResultList(
            List<CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays> listOfResult, int k) {

        for(int i = 0; i<listOfResult.size(); i++){
            CollectMaxSum3Array_Maximum_Sum_of_3_Non_Overlapping_Subarrays obj = listOfResult.get(i);
            System.out.println(obj.sum+"   ");
            System.out.println("How We Reach To This Sum");
            List<Integer> listOfStarting = obj.start;
            for (int j = 0; j<listOfStarting.size(); j++){
                int start = listOfStarting.get(j);
                int end = start+k-1;
                System.out.print("Used Ranged Index Indexes ["+
                        start+" - "+end+"]");
                System.out.println();
            }
            System.out.println();
        }
    }
}
