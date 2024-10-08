package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/falling-squares/

Input 1:
1 2
2 3
6 1
-1 -1
Input 1 meaning: positions = [[1,2],[2,3],[6,1]]
Output: [2,5,5]
Explanation:
After the first drop, the tallest stack is square 1 with a height of 2.
After the second drop, the tallest stack is squares 1 and 2 with a height of 5.
After the third drop, the tallest stack is still squares 1 and 2 with a height of 5.
Thus, we return an answer of [2, 5, 5].

Input 2:
100 100
200 100
-1 -1
Output: [100,100]
Explanation:
After the first drop, the tallest stack is square 1 with a height of 100.
After the second drop, the tallest stack is either square 1 or square 2, both with heights of 100.
Thus, we return an answer of [100, 100].
Note that square 2 only brushes the right side of square 1, which does not count as landing on it

Memory Limit Exceeded:
if u treat segmentTree as a Segment Array then u fall in Memory Limit Exceeded case because so many
blocks of unused memory is there.
Example: Let's suppose userArray length is 6 for which u want to construct segment tree. According to
SegmentTree theory max* space required is 2*(Smallest power of 2 greater than or equal to userArray
length) - 1
SegmentTree theory max space required if userArray length is 6 = 15(Acc to Formula)
But, if u construct segment tree, the actual space which we consume is 11. So all rest indexes are
wasting the memory.
That why best Approach is take Map instaed of Array to build SegemntTree
Input 3:
3259169   614936
633696   602282
34120526   531664
909832   846630
5790720   608795
50628732   941784
13382424   834960
3596245   629947
11687192   602370
20752810   532662
3661596   662521
389620   544310
7276211   680822
25400940   724224
72010620   534619
28823814   532085
65363118   525203
32558064   542361
28662822   653781
46168239   608501
19230438   996255
11325145   983261
53008956   825525
29985984   828506
4259255   566068
13064058   945083
14911314   862276
31666647   736232
18493605   987495
33962139   870960
5216062   824892
5970550   581687
3540537   685142
20715660   644765
12519892   761178
11990168   544692
10070912   796927
2778193   946403
57953418   577733
29813250   986048
7432348   793255
7801759   520672
22139741   509679
9978363   187880
21592246   908704
25269636   578732
31617715   984068
6178722   859064
21040052   532656
19916014   889662
30441276   932956
2701074   569671
30925440   547675
30616562   334798
3630120   677482
1802446   784350
15985200   266955
18567636   745369
50528027   893653
22711776   815955
9309861   961224
31503284   673468
11758114   736624
37015836   959685
26915537   753418
44955580   602637
5690568   969903
1765140   612589
55711380   793015
35385950   789450
57388480   657067
58046919   587835
12474412   844468
9350250   557772
1893160   976964
47407600   513773
29855145   546096
5454162   814021
5985510   720373
36706477   548662
51356472   606157
1518646   870286
23592704   704550
145695   843824
41341568   601818
58985556   626118
59731383   759395
49629600   806289
1766475   521543
1477732   7682
2136150   649264
59908244   827720
15464520   528857
36105059   38849
6270015   935254
47752299   752910
39716820   578800
31292424   596857
23671152   758181
7671956   914213
85356539   884542
20132564   964818
4433920   735652
17050706   945250
7976140   723236
20794995   826492
5230456   865646
56392856   530738
78646324   651182
60586131   533290
2585120   549647
75774272   518869
24207538   504969
9580236   277588
5387620   620101
9500512   551600
5330588   594439
34850816   534534
37735618   428526
2128956   798497
16207360   856350
27071931   618836
73232966   537677
2467328   523038
37580   523507
54847864   901871
5515776   560752
42120286   542404
1237198   301140
28418600   526268
1305255   521665
42422940   647469
21020342   502291
13864446   920685
45314936   972624
8444535   676610
4195485   581906
1270479   832865
2938540   857764
76584573   597560
45441   602828
9078486   659199
39588252   509079
20709566   698931
30341511   580999
6926040   707316
47422715   641019
2734916   969969
1244524   943365
28600446   764159
45195311   811345
6094080   585949
673260   790187
56200600   563969
27026820   515280
18664184   677445
36818797   679360
36725052   808199
30002672   914373
1712595   708487
18736090   893810
14994990   516287
11437172   725206
10472452   511518
15536548   443332
72447490   930300
59471102   705456
33433020   618148
18246148   959652
17907344   559822
59653986   43424
33069500   967959
5703951   770189
29383650   526309
11142626   931527
8510931   894507
502414   599381
19561050   582058
41369889   887086
39672811   515455
2586432   531000
23279536   993537
81822354   983794
11856040   181908
11499246   677127
2606947   797473
46122912   614043
33534774   584064
13180725   733414
5899440   64780
15895607   694129
18374355   753197
4213110   664665
28462083   791742
7321626   776565
3767904   806695
2075216   686805
13551104   737698
49246785   563617
22632552   563257
49324198   667792
20225130   605517
939504   996360
77042400   660291
4405884   568924
360258   787152
32270154   586549
19560286   539058
33807895   657735
24713964   867627
67041788   858556
4110243   927214
6211163   752867
27191580   862612
30215700   743075
12329646   634508
22097472   669844
42793384   691334
48217700   501725
12527280   546728
56889456   667871
69579216   590872
27536352   198720
27113968   531853
52402324   615659
6300174   745345
3188770   692222
2435940   623626
71693578   579341
3287544   961608
35103145   655787
23166837   723022
1655220   897412
2790958   643027
51009098   894218
22129408   735093
17389640   713355
1999375   585174
25018420   649448
68903136   875086
10683545   581518
25153170   742902
93346   737600
228021   898084
6815802   834921
27627980   740847
27506592   830904
4934760   986746
9094245   670352
25501056   891616
13491150   969271
870672   535840
2328612   575723
63054270   505813
31162356   579991
199410   525239
13038390   974943
37818900   536248
19288710   547648
53562022   802320
18497736   738230
12272680   791912
38867073   697261
23085048   870672
2623000   897464
31728320   704018
27146886   775424
71886080   829629
12012858   564108
11629354   955139
14656680   50328
35333120   649275
32490636   503318
45559778   942281
16793152   581962
43579122   691133
17856188   964183
1442112   979192
68515848   818400
12149328   592275
18274482   540843
48180335   956792
15257306   509273
19495539   780167
14810298   520705
55487042   776460
21112522   867722
17004100   611420
12898731   647541
27162135   548257
4072530   972465
4244828   635831
23614808   13764
18589389   509517
52806228   515542
34464990   785590
31626675   918456
33273450   834139
74797344   621253
2048136   537530
6677222   876502
1707750   563151
8788392   512659
10039620   619729
26242139   852612
43899440   546453
24282996   725625
48434007   751549
33880830   550722
61733727   618579
49302512   579627
80220840   955839
34726894   842886
26110546   552902
1365684   795205
3450876   534054
6418146   648795
4947748   743542
32791972   888336
49657712   862057
46307312   702125
17590640   563369
13253400   821040
83152343   781952
27186560   325360
85304795   607799
31944260   568545
5701528   723394
3828864   638146
32532788   972238
67168625   994926
8679169   910337
7743008   694525
57708459   943964
40440852   648427
18844632   621403
38122984   622523
2216256   828672
24355260   843050
66655336   619944
7962978   807750
14026122   712518
10387610   534177
94462872   753984
19820622   563617
42353103   87674
12204948   586549
196354   968495
4607432   606312
29687114   961520
17566560   933439
23828140   962744
27816822   525533
649380   504681
8104941   652315
12953148   657882
73775840   988689
27814752   690360
35927844   512941
4152312   902225
6753438   813576
69697380   693524
21689080   594352
1130787   606186
5402025   764421
23881668   860822
1650545   634111
31295400   651144
4627596   552380
29177733   775489
4878414   797700
35179722   582075
8245718   694563
11576525   777000
45232184   742375
48454186   506633
506230   549450
7400902   585207
80556070   514185
35999979   684143
11093452   778904
15190104   511008
11975280   508850
17125515   732485
9418894   972098
4240809   665192
5145624   627978
30542750   678291
41592858   791803
6064270   982444
27137528   942687
13220064   399156
2097294   904754
14755983   776619
63434208   900046
60830848   881892
2776936   714189
1252083   525670
5528380   568845
36345036   707334
1684870   549281
23759560   691079
72947520   521453
1314240   835049
13011198   688705
5547462   555053
6093912   804770
37702875   642296
81248952   530768
1865816   939129
178068   698955
69603060   740328
18255636   710149
381376   868154
37185337   560706
306356   588655
7921560   701007
10037508   659704
2343033   571449
10299240   968069
32790149   614328
6468792   899322
2880110   739698
86728290   809100
4474960   753303
16246575   522553
5721192   744111
48252147   713829
35167335   727027
67858494   629059
7676539   842893
30179440   927841
1474018   536665
47885797   508059
72480466   872566
10429317   993221
48844088   665073
3303542   790617
5265542   565318
38575103   720849
1117339   635712
23390080   805164
78069546   670784
94278   767620
6278949   919868
40210780   752180
36446469   560504
43023585   645190
33661014   692010
17640535   718820
45273660   689775
9275577   950235
567932   940776
1396116   674970
14105104   800044
10203325   700245
21954452   570176
48612156   573597
9745148   942745
3659045   546768
64717719   559416
19894826   658215
32520474   908747
11990823   593134
73245384   603860
54627755   711541
12594996   651652
379308   738640
629506   836036
20592540   970490
47800130   30303
3280581   335820
23653224   677103
13538975   916443
15330540   858058
52996535   631400
7408380   851980
18524328   516613
8342996   801211
52992500   968567
32744936   973123
6542060   520853
22321152   768265
81454269   816439
32815590   725800
4956160   680824
39300192   763466
4131400   979623
12044850   504070
41030022   623086
70838340   608552
6770400   950281
29295238   552631
38820   627598
16610976   718544
16447995   799153
709222   813026
58170384   650745
18682500   861681
33656280   552851
20800   554652
54585118   959542
27796608   703961
18352512   537463
9496795   746006
25715845   557048
31243565   822785
24334242   942489
12237520   826948
11868642   610239
12655770   873932
14940831   537242
15453317   723233
36295210   821031
310496   765341
33124590   598394
4291936   634065
4571008   576940
17686494   963313
24487675   853514
53840112   832176
18503976   543697
9185430   980338
15080768   888076
19890387   500685
6492584   751734
42781312   973965
2160196   628023
1253370   690887
1759060   879675
33245496   690899
4701614   940715
83813621   968632
12389080   652675
23967216   665884
5410039   942084
31984004   850256
55162224   953819
72106740   896743
44499427   878602
51876069   755377
12621123   800429
23848370   937807
17216905   528965
6283043   768548
11581592   970397
38542336   534681
13087960   554094
25260450   703222
15736138   693399
12799224   581810
34030695   762083
20174952   780999
11396700   974383
21194047   626173
16037798   260022
5309816   669542
13986266   548003
12896260   542092
37055172   705005
1292595   595200
9497248   761254
34135200   678873
13202820   722214
20174109   533157
31652062   500334
24840060   698533
3800384   592784
56448996   854483
10914464   916480
59507964   847594
16274524   680344
55560514   860854
8794304   846004
10962035   756375
17274656   538745
39822120   615887
5999850   723565
1852176   347384
45248806   510862
6462958   683625
25941060   663263
1971318   562842
85849921   943740
2243976   743980
20670753   644598
14684320   811015
31446960   668175
16263990   682048
23077600   647736
901468   54510
1705536   734083
15418182   696444
9258403   741084
3769600   532240
31674150   526163
9895752   628196
87058400   834908
14900242   507851
36907719   776826
27996540   546687
2271282   548243
65834124   606811
30639840   996790
2682680   804056
57757880   820727
4487720   728805
26626330   817421
25948520   719194
19814316   529032
24275592   602407
20280312   595536
48362978   639405
33170534   645253
55869234   647981
6643728   613837
3627674   560469
18327037   647770
18982425   962613
3189970   801642
12235545   718847
74628864   705318
9834960   379240
794964   735690
53633020   595612
4362479   976779
34294932   875070
31868094   778085
17283960   873490
85015458   671448
18318852   605561
31580640   657635
12594400   554599
19835350   991736
270930   631306
75709218   960323
16523775   632363
20777526   955172
87201730   500614
22008545   983022
40829096   861311
11780439   804580
4867182   510142
200046   538475
3505356   570826
11214880   655492
20555262   965459
15613984   635605
32874626   822483
8237775   880698
3408700   709170
14757425   812782
8556256   599459
15212900   540680
97511269   925035
9057550   530431
30820698   501669
7702240   634119
34140800   943258
7309485   588627
17543591   521080
62524   636494
14983552   552387
14708556   707065
47396750   532614
7669695   831329
8819004   560256
14011326   821145
16252986   926891
21073754   693918
12349298   29232
12701565   848804
70357405   593072
17864652   585610
45050412   351960
326610   615254
21981752   818057
711852   595186
2322762   90000
23182896   583366
20623625   652739
11182724   759230
9472000   596877
66925584   607271
84852952   712770
10002942   658605
64085153   920695
822081   856772
5655535   744383
1516223   698246
2032105   912832
860160   590296
4374600   910695
14407936   865282
7017192   599044
11702544   647851
29648220   757873
4730616   982196
2921142   935812
1189818   859489
4861227   507250
11601928   596189
68657342   535500
4850515   602616
28437255   679458
13131068   960858
388871   840904
9627033   643570
8823375   975063
576096   802776
34161534   901151
54607960   700432
936096   633832
8294832   509105
32809200   563072
7145899   531454
1298592   823622
56805945   570391
30495   71192
4967946   772440
895104   741252
57616104   826030
3497104   531610
4756656   830295
10734042   562452
3107940   549708
46948564   527501
15267780   567138
46199088   530476
59637642   966937
14681680   948285
8086776   983623
47528478   712856
8143744   648330
4198220   626368
29055510   736440
16216368   898767
63898380   734039
26451600   558191
8206308   725246
27944114   896327
21635042   925213
28210419   319424
38420882   811150
6713364   502223
8251092   948675
30847252   989813
732940   617531
61239728   600255
3922976   525765
35796288   586953
80428425   751390
33125750   662199
15029118   579253
30487296   861064
31861920   160752
11050182   522944
41239512   795110
11223555   532041
12816496   739660
71570840   560083
37356930   910245
15604320   526197
16130480   516250
228656   321776
65242200   633972
43704125   598697
55078302   428309
25346871   807895
669693   960531
28341000   673194
13160142   996327
8205868   848388
23179064   609305
13689126   615835
13686918   502250
4131666   588981
24059301   641565
5222516   591781
4116768   870199
59260796   501639
53050762   558510
34587550   992531
13119603   741256
15323256   873237
11376478   270270
13626231   528658
68715156   737377
27715922   646616
9609056   834038
16628345   545845
1905750   911216
25813425   760237
9010503   752197
31435520   567113
45516940   975374
17702186   11270
63098518   760067
35381400   505800
3766955   892776
5032750   596068
33725940   750296
20265630   857463
47120040   943686
8901162   566227
32231472   662496
3196522   538436
24696251   574980
63622328   737232
13815322   506761
50522976   822090
19182283   276675
26348913   748034
5900864   706502
27031617   943019
8995086   612126
14420282   716362
27762952   543743
57992193   640659
10355760   786258
45812375   535354
780763   519091
3728816   725647
11567066   544722
44281348   500966
1597024   981853
18931878   503672
9744594   847630
19137500   670160
14897220   791463
32298354   966590
4521625   541777
8410058   653493
27757092   603286
17402028   684387
71634736   698888
9045052   608826
5129289   991042
7784118   906989
92446689   876532
8426985   561554
15595341   946738
59711835   675530
497768   603405
54996480   892426
6300954   706140
2148061   571524
5813720   756679
20800150   718180
153716   767570
29406000   637115
1624221   561702
4668743   322115
14060924   676985
15606544   640694
41093767   921989
3064446   856818
30496563   662464
13230166   586760
7898946   672330
2109411   700225
76529460   694652
29124528   769841
47274586   636324
11700292   786600
3004992   698700
35457354   582946
2078648   677490
32774184   666919
47840812   653945
6657510   733596
30163757   924602
12396100   845744
14393186   753867
49281466   684036
5287401   521453
2456775   543089
5000472   507322
18928761   895737
27742656   546412
11351395   502441
49358280   581275
36815384   893930
13401522   942925
16824468   504916
19169450   613893
40702012   516803
10047626   881923
31889010   764626
16203408   626832
346455   819694
48846756   614215
21003268   965219
18831223   512724
18138510   845490
49747470   957412
7076784   20064
41399568   426398
2069480   656085
10497920   978755
19840780   579414
24953822   765400
3346760   531892
22448500   575915
11974410   765105
28652701   638194
33498872   878143
41958736   544033
3433144   979154
1311975   638542
10119910   580686
65799360   539760
17814384   582218
24441728   725494
20344392   736939
24988040   555488
49572590   612434
15036280   944690
51076032   614079
15660216   962477
17263953   949689
28397612   771858
5437632   557142
72509796   678466
68854968   545878
9457140   718960
40172758   706753
15758600   746194
20830644   652333
7442060   568216
41869398   741779
11942013   566699
1831170   677344
25974622   931296
79484072   608625
18237132   969951
44925913   618982
2854365   796588
81380453   812759
12113877   912145
380378   816199
18843708   553829
7430610   849084
7239672   579340
38961250   824886
5362620   714886
4934293   621527
10832528   595517
21856170   610685
24260236   955456
53378068   536946
11160632   796463
2709516   764293
10750575   993870
22110487   401422
11193273   537140
28306806   502087
7572474   870231
15431472   932228
40679962   667432
2765040   576980
48814494   31296
16641903   534627
11778024   715159
22718715   992469
19645680   507382
28966520   941598
29388177   741879
31592142   826864
42553920   874039
81280108   848616
21557520   599440
51676975   603075
23343372   824671
1122888   559248
193128   949403
74420320   715121
37732940   922785
1724491   938772
19350472   602665
712530   685152
2939664   688323
48773152   775656
17972808   808428
825588   507275
36673692   786523
214809   675462
25729194   771960
2799460   672575
110360   583115
34248445   774493
22853585   714224
65484370   767928
35777076   737103
500193   713878
70096270   509022
31786032   801120
1484726   983711
27639664   796789
25183396   763529
-1           -1
 */


class Pair__Falling_Squares{
    int start;
    int end;

    Pair__Falling_Squares(int start, int end){
        this.start = start;
        this.end = end;
    }
}

class SegmentTree_Falling_Squares {
    Map<Integer, Integer> segmentArr;
    Map<Integer, Integer> lazyPropagationArr;
    int lastindexInUserArr;

    SegmentTree_Falling_Squares(int lastindexInUserArr) {
        this.lastindexInUserArr = lastindexInUserArr;
        int userArrLength = lastindexInUserArr + 1;
        this.segmentArr = new HashMap<>();
        this.lazyPropagationArr = new HashMap<>();
    }

    public void updateSegmentTree(int start, int end, int newValue) {
        updateSegmentTreeHelper(start, end, 0, lastindexInUserArr,
                0, newValue);
    }

    private int updateSegmentTreeHelper(int rangeStart, int rangeEnd,
                                        int treeStart, int treeEnd, int treeIndex, int newValue) {

        if (rangeStart <= rangeEnd) {
            updateIfRequired(treeIndex);
            if (isCompleteOverlap(rangeStart, rangeEnd, treeStart, treeEnd)) {
                this.segmentArr.put(treeIndex, newValue);
                updateLazyPropagationTree(treeIndex, newValue);
                return this.segmentArr.getOrDefault(treeIndex, 0);
            } else if (isNoOverlap(rangeStart, rangeEnd, treeStart, treeEnd)) {
                return Integer.MIN_VALUE;
            } else {
                int leftChild = getLeftChild(treeIndex);
                int rightChild = getRightChild(treeIndex);
                int mid = treeStart + (treeEnd - treeStart) / 2;

                int leftMax = updateSegmentTreeHelper(rangeStart, rangeEnd, treeStart,
                        mid, leftChild, newValue);
                int rightMax = updateSegmentTreeHelper(rangeStart, rangeEnd, mid + 1,
                        treeEnd, rightChild, newValue);
                int max = Math.max(leftMax, rightMax);
                this.segmentArr.put(treeIndex, max);
                return max;
            }
        }
        return Integer.MIN_VALUE;
    }

    public int getMaxRangeQuery(int start, int end) {
        return getMaxRangeQueryHelper(start, end, 0,
                lastindexInUserArr, 0);
    }

    private int getMaxRangeQueryHelper(int queryStart, int queryEnd,
                                       int treeStart, int treeEnd, int treeIndex) {

        if (queryStart <= queryEnd) {
            updateIfRequired(treeIndex);
            if (isCompleteOverlap(queryStart, queryEnd, treeStart, treeEnd)) {
                return this.segmentArr.getOrDefault(treeIndex, 0);
            } else if (isNoOverlap(queryStart, queryEnd, treeStart, treeEnd)) {
                return Integer.MIN_VALUE;
            } else { // Partial Overlap
                int leftChild = getLeftChild(treeIndex);
                int rightChild = getRightChild(treeIndex);
                int mid = treeStart + (treeEnd - treeStart) / 2;

                int leftMax = getMaxRangeQueryHelper(queryStart, queryEnd, treeStart,
                        mid, leftChild);
                int rightMax = getMaxRangeQueryHelper(queryStart, queryEnd, mid + 1,
                        treeEnd, rightChild);
                int max = Math.max(leftMax, rightMax);
                this.segmentArr.put(treeIndex, max);
                return max;
            }
        }
        return Integer.MIN_VALUE;
    }

    private void updateIfRequired(int treeIndex) {
        if (this.lazyPropagationArr.containsKey(treeIndex)) {
            int newValue = this.lazyPropagationArr.getOrDefault(treeIndex, 0);
            this.segmentArr.put(treeIndex, newValue);
            updateLazyPropagationTree(treeIndex, newValue);
        }
    }

    private void updateLazyPropagationTree(int treeIndex, int newValue) {
        this.lazyPropagationArr.remove(treeIndex);
        int leftChildIndex = getLeftChild(treeIndex);
        int rightChildIndex = getRightChild(treeIndex);

        this.lazyPropagationArr.put(leftChildIndex, newValue);
        this.lazyPropagationArr.put(rightChildIndex, newValue);
    }

    private int getLeftChild(int treeIndex) {
        return (2 * treeIndex) + 1;
    }

    private int getRightChild(int treeIndex) {
        return (2 * treeIndex) + 2;
    }

    private boolean isCompleteOverlap(int queryStartRange, int queryEndRange,
                                      int treeStartRange, int treeEndRange) {

        return treeStartRange >= queryStartRange && treeEndRange <= queryEndRange;
    }

    private boolean isNoOverlap(int queryStartRange, int queryEndRange,
                                int treeStartRange, int treeEndRange) {

        return treeEndRange < queryStartRange || treeStartRange > queryEndRange;
    }
}

public class Falling_Squares {

    public static void main(String[] args) {
        Falling_Squares falling_squares = new Falling_Squares();
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
            if (choice == 1) {
                List<List<Integer>> listOfPositions = new ArrayList<>();
                int x;
                int y;
                System.out.println("Enter Left(i) Coordinate And SideLength Of Square. For Stop Insertion " +
                        "Enter Any Negative Left(i) Coordinate And SideLength");
                x = input.nextInt();
                y = input.nextInt();
                while (x >= 0 && y >= 0) {
                    listOfPositions.add(List.of(x, y));
                    x = input.nextInt();
                    y = input.nextInt();
                }
                int[][] positions = listOfPositions.stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);

                System.out.println();
                List<Integer> maxHeights = falling_squares.fallingSquares(positions);
                System.out.println("Maximum Height After Each Falling Squares: ");
                for (int height : maxHeights) {
                    System.out.print(height + ",  ");
                }
                System.out.println();
            } else {
                int[][] positions = {{1, 2}, {2, 3}, {6, 1}};

                System.out.println();
                List<Integer> maxHeights = falling_squares.fallingSquares(positions);
                System.out.println("Maximum Height After Each Falling Squares: ");
                for (int height : maxHeights) {
                    System.out.print(height + ",  ");
                }
                System.out.println();
            }
            t--;
        }
    }

    public List<Integer> fallingSquares(int[][] positions) {

        int[]arr = new int[2]; // arr[0] = MinXCoordinate, arr[1] = MaxXCoordinate
        /*
            treat MinXCoordinate is placed on Origin in Cardinal Plane. And Accordingly MaxXCoordinate
            in Placed. That's why we take diff. Means By which Amount we shift the Origin.
         */
        findMaxXCoordinate(positions, arr);
        int diff = arr[0];
        SegmentTree_Falling_Squares segmentTree =
                new SegmentTree_Falling_Squares(arr[1] - arr[0]);

        List<Integer> maxHeights = new ArrayList<>();
        Map<Integer, Pair__Falling_Squares> boundaryPointsToStartEnd = new HashMap<>();
        int max = Integer.MIN_VALUE;

        for(int i = 0; i<positions.length; i++){

            int prevHeight = getPrevMaxHeight(positions[i][0], positions[i][1],
                    segmentTree, boundaryPointsToStartEnd, diff);
            int currHeight = prevHeight + positions[i][1];

             segmentTree.updateSegmentTree(positions[i][0] - diff,
                     (positions[i][0]+positions[i][1])-diff, currHeight);
             updateMap(boundaryPointsToStartEnd, positions[i][0], positions[i][1],
                     currHeight, diff);

            if(max<currHeight)
                max = currHeight;
            maxHeights.add(max);
        }
        return maxHeights;
    }

    private void updateMap(Map<Integer, Pair__Falling_Squares> map,
          int xCoordinate, int squareLength, int newHeight, int diff) {

        int queryStart = xCoordinate - diff;
        int queryEnd = (xCoordinate + squareLength) - diff;

        deleteMapKeyIfRequired(map, queryStart, queryEnd);
        addInMap(map, queryStart, newHeight, 0);
        addInMap(map, queryEnd, 0, newHeight);
    }

    private void addInMap(Map<Integer, Pair__Falling_Squares> map,
              int key, int start, int end) {
        if(map.containsKey(key)){
            Pair__Falling_Squares pair = map.get(key);
            pair.start = Math.max(pair.start, start);
            pair.end = Math.max(pair.end, end);
        }else{
            Pair__Falling_Squares pair =
                    new Pair__Falling_Squares(start, end);
            map.put(key, pair);
        }
    }

    private void deleteMapKeyIfRequired(Map<Integer, Pair__Falling_Squares> map,
                    int queryStart, int queryEnd) {

        Set<Integer> keyNeedToRemove = new HashSet<>();
        for(Map.Entry<Integer, Pair__Falling_Squares> entry: map.entrySet()){
            int boundaryPoint = entry.getKey();
            if(queryStart < boundaryPoint &&  boundaryPoint < queryEnd){
                keyNeedToRemove.add(boundaryPoint);
            }
        }

        for (int key: keyNeedToRemove)
            map.remove(key);
    }

    private int getPrevMaxHeight(int xCoordinate, int squareLength,
                  SegmentTree_Falling_Squares segmentTree,
                  Map<Integer, Pair__Falling_Squares> map, int diff) {

        int preMaxHeight;

        int betweenMaxHeight = 0;
        int maxHeightAtLeftBoundary = 0;
        int maxHeightAtRightBoundary = 0;

        int queryStart = (xCoordinate + 1) - diff;
        int queryEnd = ((xCoordinate + squareLength) - 1) - diff;

        if(!map.containsKey(xCoordinate - diff))
            queryStart = queryStart - 1;
        else
            maxHeightAtLeftBoundary = map.get(xCoordinate - diff).start;

        if(!map.containsKey((xCoordinate + squareLength) - diff))
            queryEnd = queryEnd + 1;
        else
            maxHeightAtLeftBoundary = map.get((xCoordinate + squareLength) - diff).end;

        if(queryStart<=queryEnd)
            betweenMaxHeight = segmentTree.getMaxRangeQuery(queryStart, queryEnd);

        preMaxHeight = (int) Math.max(Math.max(maxHeightAtLeftBoundary, maxHeightAtRightBoundary),
                betweenMaxHeight);
        return preMaxHeight;
    }

    private void findMaxXCoordinate(int[][] positions, int[] arr) {

        int maxXCoordinate = Integer.MIN_VALUE;
        int minXCoordinate = Integer.MAX_VALUE;

        for (int i = 0; i<positions.length; i++){

            int xCoordinate = positions[i][0] + positions[i][1];

            if(maxXCoordinate<xCoordinate)
                maxXCoordinate = xCoordinate;

            if(minXCoordinate>positions[i][0])
                minXCoordinate = positions[i][0];
        }
        arr[0] = minXCoordinate;
        arr[1] = maxXCoordinate;
    }
}
