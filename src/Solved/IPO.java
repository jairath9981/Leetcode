package Solved;

/*
https://leetcode.com/problems/ipo/

Input:
2
0
1 2 3 -999
0 1 1 -999
Input Meaning:
k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
Output: 4
Explanation: Since your initial capital is 0, you can only start the project indexed 0.
After finishing it you will obtain profit 1 and your capital becomes 1.
With capital 1, you can either start the project indexed 1 or the project indexed 2.
Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.

Input:
3
0
1 2 3 -999
0 1 2 -999
Output: 6

Input:
1
0
1 2 3 -999
1 1 2 -999
Output: 0
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

class MaxCost_IPO{

    int capital;
    List<Integer> profits;
    int size;

    MaxCost_IPO(int capital, int prosit){
        this.capital = capital;

        this.profits = new ArrayList<>();
        this.profits.add(prosit);
        this.size = 1;
    }

    public void addProfit(int profit){
        if(this.profits.isEmpty())
            this.profits = new ArrayList<>();
        this.profits.add(profit);
        this.size = this.size + 1;
    }

    public int getMaxProfit(){
        if(this.size>=1) {
            int profit = this.profits.get(this.size - 1);
            return profit;
        }else{
            return -1;
        }
    }

    public void deleteMaxUsedProfit(){
        this.size--;
    }

    public int getCapital() {
        return this.capital;
    }
}

class Capital_IPOComparator implements Comparator<MaxCost_IPO> {
    public int compare(MaxCost_IPO maxCost_ipo1, MaxCost_IPO maxCost_ipo2) {
        return maxCost_ipo1.getCapital() - maxCost_ipo2.getCapital();
    }
}

class MaxProfits_IPO{
    int profit;
    int index;
    MaxProfits_IPO(int profit, int index){
        this.profit = profit;
        this.index = index;
    }
    public int getProfit() {
        return this.profit;
    }

    public int getIndex() {
        return this.index;
    }
}

class MaxProfits_IPOComparator implements Comparator<MaxProfits_IPO>{

    public int compare(MaxProfits_IPO maxProfits_ipo1, MaxProfits_IPO maxProfits_ipo2) {
        if (maxProfits_ipo1.getProfit() < maxProfits_ipo2.getProfit())
            return 1;
        else if (maxProfits_ipo1.getProfit() > maxProfits_ipo2.getProfit())
            return -1;
        return 0;
    }
}

public class IPO {
    public static void main(String[] args) {
            IPO ipo = new IPO();
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

                    int k, w, x;
                    System.out.println("Enter Max Number Of Projects That Can Be Covered Before IPO Release: ");
                    k = input.nextInt();
                    System.out.println("Enter Initial Capital That We Have To Complete Projects: ");
                    w = input.nextInt();
                    List<Integer> listOfProfit = new ArrayList<>();
                    System.out.println("Enter Your Profit Array, For Stop Insertion Press -999");
                    x = input.nextInt();
                    while(x!=-999) {
                        listOfProfit.add(x);
                        x = input.nextInt();
                    }
                    List<Integer> listOfCapital = new ArrayList<>();
                    System.out.println("Enter Respective Capital Requirement, For Stop Insertion Press -999");
                    x = input.nextInt();
                    while(x!=-999) {
                        listOfCapital.add(x);
                        x = input.nextInt();
                    }
                    int[] profits = listOfProfit.stream().mapToInt(i->i).toArray();
                    int[] capital = listOfCapital.stream().mapToInt(i->i).toArray();
                    int ans = ipo.findMaximizedCapital(k, w, profits, capital);
                    System.out.println("The Final Maximized Capital = "+ans);
                }
                else
                {
                    int k = 3;
                    int w = 0;
                    int [] profits = {1,2,3};
                    int [] capital = {0,1,2};
                    int ans = ipo.findMaximizedCapital(k, w, profits, capital);
                    System.out.println("The Final Maximized Capital = "+ans);
                }
                t--;
            }
    }

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        if(k>0) {
            List<MaxCost_IPO> maxCostIpoList = prepareListForOptimization(profits, capital);
            PriorityQueue<MaxProfits_IPO> profitQueue =
                    new PriorityQueue<>(new MaxProfits_IPOComparator());
            int start = 0, maxCapitalProfit = w;
            for(int i = 1; i<=k; i++){
                start = getProfitLessThenInitialCapital(maxCostIpoList, maxCapitalProfit, start, profitQueue);
                if(!profitQueue.isEmpty()) {
                    MaxProfits_IPO maxProfits_ipo = profitQueue.peek();
                    maxCapitalProfit = maxCapitalProfit + maxProfits_ipo.getProfit();
                    profitQueue.poll();
                    addInPriorityQueue(maxCostIpoList, maxProfits_ipo.getIndex(), profitQueue);
                }
                else
                    break;
            }
            return maxCapitalProfit;
        }
        return 0;
    }

    private int getProfitLessThenInitialCapital(
            List<MaxCost_IPO> maxCostIpoList, int w,
            int start, PriorityQueue<MaxProfits_IPO> profit) {

        int stopIndex = start;
        for(int i = start; i<maxCostIpoList.size() && w>=maxCostIpoList.get(i).capital; i++) {
            addInPriorityQueue(maxCostIpoList, i, profit);
            stopIndex = i;
        }
        return stopIndex;
    }

    private void addInPriorityQueue(List<MaxCost_IPO> maxCostIpoList,
                      int index, PriorityQueue<MaxProfits_IPO> profit) {
        int currentIndexMaxProfit = maxCostIpoList.get(index).getMaxProfit();
        if (currentIndexMaxProfit>=0) {
            MaxProfits_IPO maxProfits_ipo = new MaxProfits_IPO(currentIndexMaxProfit, index);
            profit.add(maxProfits_ipo);
            maxCostIpoList.get(index).deleteMaxUsedProfit();
        }
    }

    private List<MaxCost_IPO> prepareListForOptimization(int[] profits, int[] capital) {
        List<MaxCost_IPO> maxCostIpoList = new ArrayList<>();
        Map<Integer,Integer> keyCapitalValueListIndex = new HashMap<Integer,Integer>();
        for(int i = 0; i<capital.length; i++){
            if(!keyCapitalValueListIndex.containsKey(capital[i])){
                maxCostIpoList.add(new MaxCost_IPO(capital[i], profits[i]));
                keyCapitalValueListIndex.put(capital[i], maxCostIpoList.size()-1);
            }else {
                int index = keyCapitalValueListIndex.get(capital[i]);
                maxCostIpoList.get(index).addProfit(profits[i]);
            }
        }
        for(int i = 0; i<maxCostIpoList.size(); i++)
            Collections.sort(maxCostIpoList.get(i).profits);
        Collections.sort(maxCostIpoList, new Capital_IPOComparator());
        return maxCostIpoList;
    }
}
