package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/bus-routes/

Input 1:
1 2 7 -999
3 6 7 -999
-999
1
6
Input Meaning: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
Output: 2
Explanation: The best strategy is take the first bus to the bus stop 7, then take the second bus to the
bus stop 6.

Input 2:
7 12 -999
4 5 15 -999
6 -999
15 19 -999
9 12 13 -999
-999
15
12
Output: -1

Input 3:
25   33  -999
3   5   13   22   23   29   37   45   49  -999
15   16   41   47  -999
5   11   17   23   33  -999
10   11   12   29   30   39   45  -999
2   5   23   24   33  -999
1   2   9   19   20   21   23   32   34   44  -999
7   18   23   24  -999
1   2   7   27   36   44  -999
7   14   33  -999
-999
7
47
Output: -1

Input 5:
1 7 -999
3 5 -999
-999
5
5
Output: 0

Input 6:
148  167  216  -999
6  23  25  40  43  58  63  69  77  86  94  96  106  117  119  127  139  151  153  155  157  186  191  196  200  204  210  216  219  -999
2  6  7  16  27  30  42  47  49  68  69  77  93  94  96  102  104  111  114  126  131  137  150  161  167  171  174  193  198  199  200  223  -999
46  131  211  -999
25  36  51  52  65  78  90  102  103  105  108  114  123  151  152  153  162  174  175  -999
217  -999
9  10  15  27  37  38  41  43  46  51  67  74  81  82  83  94  95  107  113  120  122  123  124  132  149  160  162  169  170  171  174  177  185  192  193  195  196  198  213  217  220  221  -999
74  78  85  95  130  136  145  152  173  175  180  181  184  193  199  202  -999
13  18  28  38  41  42  47  75  87  91  106  151  158  166  181  182  199  216  -999
44  63  71  74  144  162  169  220  -999
2  23  115  185  208  -999
0  8  13  14  35  46  67  89  91  122  124  126  130  156  177  193  212  214  -999
2  4  24  37  40  43  55  68  81  92  106  107  109  127  132  138  145  159  163  165  170  172  183  184  209  213  215  220  -999
5  16  17  34  38  48  55  59  60  65  69  84  86  94  100  103  109  110  112  127  130  131  134  145  148  149  154  161  166  169  182  183  201  203  208  214  223  -999
0  2  5  6  8  19  49  50  53  79  92  94  97  109  110  112  121  129  132  135  138  139  144  160  166  170  194  197  198  201  212  -999
27  52  61  112  118  133  142  159  175  186  216  -999
2  20  34  64  65  77  87  91  95  96  97  125  126  131  144  146  149  152  154  164  165  170  179  205  207  -999
24  85  123  132  172  173  194  222  -999
2  4  5  15  23  36  44  47  63  64  78  80  84  97  99  102  104  114  120  130  132  143  161  162  163  167  171  172  176  179  180  194  196  199  202  204  209  214  216  221  -999
8  22  26  31  38  39  41  59  78  90  102  108  110  138  141  146  176  185  190  198  200  219  220  -999
5  24  30  46  55  64  67  74  78  136  194  216  -999
133  142  202  -999
13  40  49  57  63  75  76  85  91  107  116  121  128  135  137  141  154  193  198  200  204  223  -999
4  13  14  26  28  33  39  49  58  65  67  74  77  81  90  96  122  124  144  156  158  166  169  170  179  203  204  208  215  223  -999
6  20  28  36  46  90  107  115  124  131  135  144  147  148  149  161  162  174  176  214  221  -999
10  20  21  29  35  36  62  65  67  70  72  87  89  92  100  103  107  109  113  126  129  139  140  145  146  147  174  176  180  184  189  190  193  196  198  199  200  209  217  -999
19  22  27  54  59  63  77  102  122  126  140  143  154  164  165  175  212  216  217  218  -999
11  13  16  18  27  31  46  49  69  77  88  109  111  119  121  146  161  169  193  194  198  200  204  -999
1  7  28  58  73  91  98  138  150  173  182  186  213  -999
3  25  28  33  46  68  70  74  78  97  141  146  149  169  172  178  185  188  202  212  223  -999
3  4  19  22  24  37  38  43  54  55  56  57  58  62  66  72  75  77  88  106  114  119  127  132  133  137  144  146  150  156  161  164  165  179  181  195  200  213  214  215  222  -999
9  11  14  15  38  46  55  61  66  68  69  75  76  79  82  91  100  101  102  113  135  141  142  171  175  180  198  208  210  215  218  221  -999
2  30  33  62  93  104  124  127  128  147  158  160  161  173  181  189  192  199  201  215  223  -999
4  26  29  38  47  58  61  69  78  93  94  112  114  131  136  144  182  193  198  203  206  209  -999
5  13  14  16  17  22  30  32  45  47  49  55  63  64  68  77  82  84  86  92  98  100  104  107  117  119  122  127  134  153  164  179  185  197  201  209  212  213  220  223  -999
2  4  5  6  42  55  75  81  84  93  102  111  112  113  118  129  142  149  159  169  191  193  200  214  223  -999
10  12  15  19  20  24  33  34  40  47  54  64  93  104  115  121  123  124  155  172  189  190  193  196  202  212  219  222  -999
104  108  143  -999
14  15  20  21  31  47  48  59  67  70  74  82  94  102  109  121  125  128  148  162  165  171  180  196  199  202  205  212  214  -999
2  6  17  18  41  50  60  70  118  151  155  158  166  167  172  180  182  186  188  195  -999
1  23  25  30  39  41  42  48  58  65  67  94  100  121  126  135  145  152  163  164  171  174  206  210  220  224  -999
18  25  96  123  172  -999
5  7  9  12  13  19  22  25  34  51  62  64  74  79  81  85  88  101  102  119  123  140  143  149  155  165  166  167  178  182  189  204  213  222  223  -999
1  5  18  21  23  50  54  59  62  67  68  72  87  94  95  96  110  116  118  122  133  135  151  155  156  158  171  178  183  184  192  198  208  212  222  224  -999
18  20  24  34  47  52  56  68  77  82  89  91  97  101  105  106  107  109  118  123  139  141  143  152  153  162  174  180  184  187  188  192  198  202  206  216  224 -999
-999
180
143
Output: 1
*/

class Pair_Bus_Routes{
    int i;
    int j;

    Pair_Bus_Routes(int i, int j){
        this.i = i;
        this.j = j;
    }
}

class Triplet_Bus_Routes{
    int station;
    Pair_Bus_Routes busCoordinate;
    int busCount;

    Triplet_Bus_Routes(int station, Pair_Bus_Routes busCoordinate, int busCount){
        this.station = station;
        this.busCoordinate = busCoordinate;
        this.busCount = busCount;
    }
}

class Min_Traversed_Bus_Routes{
    int busCount;
    Set<Integer> routes;
    Min_Traversed_Bus_Routes(){
        this.routes = new HashSet<>();
    }
}

public class Bus_Routes {

    public static void main(String[] args) {
        Bus_Routes bus_routes = new Bus_Routes();
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
                List<List<Integer>> listOfRoutes = new ArrayList<>();
                int x;
                System.out.println("Enter Route Matrix. For Stop Insertion At Any Row Press -999");
                x = input.nextInt();
                while(x!=-999) {
                    List<Integer> routeRow = new ArrayList<>();
                    while(x!=-999) {
                        routeRow.add(x);
                        x = input.nextInt();
                    }
                    if(!routeRow.isEmpty())
                        listOfRoutes.add(routeRow);
                    x = input.nextInt();
                }
                int[][] routes = listOfRoutes.stream().map( u -> u.stream().mapToInt(i->i).toArray() ).toArray(int[][]::new);

                System.out.println("Enter Source Station");
                int source = input.nextInt();
                System.out.println("Enter Target Station");
                int target = input.nextInt();

                int minBusesReq = bus_routes.numBusesToDestination(routes, source, target);
                System.out.println("Min Buses Req From Source Station To Reach Target Station: "+minBusesReq);
            }
            else
            {
                int[][] routes = {{1,2,7},{3,6,7}};
                int source = 1;
                int target = 6;

                int minBusesReq = bus_routes.numBusesToDestination(routes, source, target);
                System.out.println("Min Buses Req From Source Station To Reach Target Station: "+minBusesReq);
            }
            t--;
        }
    }

    public int numBusesToDestination(int[][] routes,
          int source, int target) {
        Map<Integer, List<Pair_Bus_Routes>> stationToBusCoordinates =
                getStationBusesCoordinates(routes);
        if(source == target)
            return 0;
        int minBuses = bfsToFindBuses(stationToBusCoordinates, routes,
                source, target);
        return minBuses;
    }

    private int bfsToFindBuses(
         Map<Integer, List<Pair_Bus_Routes>> stationToBusCoordinates,
         int[][] routes, int source, int target) {

        int minBusesToReachTargetStation = Integer.MAX_VALUE;

        Map<Integer, Min_Traversed_Bus_Routes> stationTraversed = new HashMap<>();
        Queue<Triplet_Bus_Routes> qu = new LinkedList<>();

        addInQueue(qu, stationTraversed, stationToBusCoordinates,
                source, routes, null);

        while(!qu.isEmpty()){

            Triplet_Bus_Routes triplet_bus_routes = qu.poll();
            if(triplet_bus_routes.station == target){
                minBusesToReachTargetStation =  Math.min(minBusesToReachTargetStation,
                        triplet_bus_routes.busCount);

            }
            if(stationTraversed.get(triplet_bus_routes.station).busCount
                    == triplet_bus_routes.busCount) {
                addInQueue(qu, stationTraversed, stationToBusCoordinates,
                        triplet_bus_routes.station, routes, triplet_bus_routes);
            }
        }
        if(minBusesToReachTargetStation == Integer.MAX_VALUE)
            return -1;
        return minBusesToReachTargetStation;
    }

    private void addInQueue(Queue<Triplet_Bus_Routes> qu,
          Map<Integer, Min_Traversed_Bus_Routes> stationTraversed,
          Map<Integer, List<Pair_Bus_Routes>> stationToBusCoordinates,
          int source, int [][]routes, Triplet_Bus_Routes quPeekElement) {

        if(stationToBusCoordinates.containsKey(source)){
            List<Pair_Bus_Routes> listOfPrevStationBusCoordinates =
                    stationToBusCoordinates.get(source);

            for(Pair_Bus_Routes previousStationBusCoordinates:
                    listOfPrevStationBusCoordinates){

                int neighbourValidI = previousStationBusCoordinates.i;
                int neighbourValidJ = getValidJ(routes, previousStationBusCoordinates);
                int neighbourStation = routes[neighbourValidI][neighbourValidJ];

                int busCount = detectBusCount(quPeekElement, neighbourValidI);

                if(isNeedToAddInQueue(stationTraversed, neighbourStation,
                        busCount, neighbourValidI)) {
                    qu.add(new Triplet_Bus_Routes(neighbourStation,
                    new Pair_Bus_Routes(neighbourValidI, neighbourValidJ),
                    busCount));
                    addInTraversedStations(stationTraversed, neighbourStation,
                            busCount, neighbourValidI);
                }
            }
        }
    }

    private void addInTraversedStations(
            Map<Integer, Min_Traversed_Bus_Routes> stationTraversed,
            int neighbourStation, int busCount, int neighbourValidI) {
        if(stationTraversed.containsKey(neighbourStation)){
            Min_Traversed_Bus_Routes min_traversed_bus_routes =
                    stationTraversed.get(neighbourStation);
            if(min_traversed_bus_routes.busCount == busCount){
                min_traversed_bus_routes.routes.add(neighbourValidI);
            }else{
                Min_Traversed_Bus_Routes new_min_traversed_bus_routes =
                        createNewBusCount(busCount, neighbourValidI);
                stationTraversed.put(neighbourStation, new_min_traversed_bus_routes);
            }
        }else{
            Min_Traversed_Bus_Routes min_traversed_bus_routes =
                    createNewBusCount(busCount, neighbourValidI);
            stationTraversed.put(neighbourStation, min_traversed_bus_routes);
        }
    }

    private Min_Traversed_Bus_Routes createNewBusCount(int busCount, int neighbourValidI) {
        Min_Traversed_Bus_Routes min_traversed_bus_routes =
                new Min_Traversed_Bus_Routes();
        min_traversed_bus_routes.busCount = busCount;
        min_traversed_bus_routes.routes.add(neighbourValidI);
        return min_traversed_bus_routes;
    }

    private boolean isNeedToAddInQueue(
            Map<Integer, Min_Traversed_Bus_Routes> stationTraversed,
            int neighbourStation, int newBusCount, int newRoute) {
        if(!stationTraversed.containsKey(neighbourStation)){
            return true;
        }else{
            Min_Traversed_Bus_Routes min_traversed_bus_routes =
                    stationTraversed.get(neighbourStation);
            if(min_traversed_bus_routes.busCount>newBusCount)
                return true;
            else if(min_traversed_bus_routes.busCount == newBusCount &&
            !min_traversed_bus_routes.routes.contains(newRoute)){
                return true;
            }
        }
        return false;
    }

    private int detectBusCount(Triplet_Bus_Routes quPeekElement, int newBusNumber) {
        if(quPeekElement == null)
            return 1;
        if(quPeekElement.busCoordinate.i == newBusNumber)
            return quPeekElement.busCount;
        else
            return quPeekElement.busCount+1;
    }

    private int getValidJ(int[][] routes, Pair_Bus_Routes pairStationBusRoutes) {
        if(pairStationBusRoutes.j == routes[pairStationBusRoutes.i].length - 1)
            return 0;
        else
            return pairStationBusRoutes.j + 1;
    }

    private Map<Integer, List<Pair_Bus_Routes>> getStationBusesCoordinates(
            int[][] routes) {
        Map<Integer, List<Pair_Bus_Routes>> stationToBusCoordinates =
                new HashMap<>();

        for(int i = 0; i<routes.length; i++){
            for(int j = 0; j<routes[i].length; j++){
                if(!stationToBusCoordinates.containsKey(routes[i][j])){
                    stationToBusCoordinates.put(routes[i][j], new ArrayList<>());
                }
                stationToBusCoordinates.get(routes[i][j]).add(
                        new Pair_Bus_Routes(i, j));
            }
        }
        return stationToBusCoordinates;
    }
}
