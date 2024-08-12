package Algos_Plus_DataStructures;

public class LazyPropagationSegmentTree {
    long[] segmentArr;
    long[] lazyPropagationArr;
    int segmentArrSize;
    int lastindexInUserArr;

    LazyPropagationSegmentTree(int lastindexInUserArr) {
        this.lastindexInUserArr = lastindexInUserArr;
        int userArrLength = lastindexInUserArr + 1;
        int powOf2 = getNearestGreaterPowerOf2(userArrLength);
        this.segmentArrSize = (powOf2 * 2) - 1;
        this.segmentArr = new long[this.segmentArrSize];
        this.lazyPropagationArr = new long[this.segmentArrSize];

        for (int i = 0; i < this.segmentArrSize; i++) {
            this.segmentArr[i] = 0;
            this.lazyPropagationArr[i] = 0;
        }
    }

    private int getNearestGreaterPowerOf2(int n) {
        if (n > 0 && (n & (n - 1)) == 0)
            return n;
        int count = 0;
        while (n != 0) {
            n >>= 1;
            count += 1;
        }
        return 1 << count;
    }

    public void updateSegmentTree(int start, int end, long increasedBy) {
        updateSegmentTreeHelper(start, end, 0, lastindexInUserArr,
                0, increasedBy);
    }

    private long updateSegmentTreeHelper(int rangeStart, int rangeEnd,
             int treeStart, int treeEnd, int treeIndex, long increasedBy) {

        if (rangeStart <= rangeEnd) {
            updateIfRequired(treeIndex);
            if (isCompleteOverlap(rangeStart, rangeEnd, treeStart, treeEnd)) {
                this.segmentArr[treeIndex] += increasedBy;
                updateLazyPropagationTree(treeIndex, increasedBy);
                return this.segmentArr[treeIndex];
            } else if (isNoOverlap(rangeStart, rangeEnd, treeStart, treeEnd)) {
                return Integer.MIN_VALUE;
            } else {
                int leftChild = getLeftChild(treeIndex);
                int rightChild = getRightChild(treeIndex);
                int mid = treeStart + (treeEnd - treeStart) / 2;

                long leftMax = updateSegmentTreeHelper(rangeStart, rangeEnd, treeStart,
                        mid, leftChild, increasedBy);
                long rightMax = updateSegmentTreeHelper(rangeStart, rangeEnd, mid + 1,
                        treeEnd, rightChild, increasedBy);
                long max = leftMax <= rightMax ? rightMax : leftMax;
                this.segmentArr[treeIndex] = max;
                return max;
            }
        }
        return Integer.MIN_VALUE;
    }

    // For Future reference
    public LazyPropagationSegmentTree buildSegmentTree(int[] arr) {

        LazyPropagationSegmentTree segmentTree =
                new LazyPropagationSegmentTree(arr.length - 1);
        buildSegmentTreeHelper(0, arr.length, 0, arr, segmentTree);
        return segmentTree;
    }

    private void buildSegmentTreeHelper(int treeStart, int treeEnd, int currIndex,
                         int[] arr, LazyPropagationSegmentTree segmentTree) {
        if (treeStart < treeEnd) {
            int mid = treeStart + (treeEnd - treeStart) / 2;

            int leftChild = getLeftChild(currIndex);
            int rightChild = getRightChild(currIndex);

            buildSegmentTreeHelper(treeStart, mid, leftChild, arr, segmentTree);
            buildSegmentTreeHelper(mid + 1, treeEnd, rightChild, arr, segmentTree);

            segmentTree.segmentArr[currIndex] = Math.max(segmentTree.segmentArr[leftChild],
                    segmentTree.segmentArr[rightChild]);

        } else if (treeStart == treeEnd) {
            segmentTree.segmentArr[currIndex] = arr[treeStart];
        }
    }

    public long getMaxRangeQuery(int start, int end) {
        return getMaxRangeQueryHelper(start, end, 0, lastindexInUserArr, 0);
    }

    private long getMaxRangeQueryHelper(int queryStart, int queryEnd,
                                        int treeStart, int treeEnd, int treeIndex) {

        if (queryStart <= queryEnd) {
            updateIfRequired(treeIndex);
            if (isCompleteOverlap(queryStart, queryEnd, treeStart, treeEnd)) {
                return this.segmentArr[treeIndex];
            } else if (isNoOverlap(queryStart, queryEnd, treeStart, treeEnd)) {
                return Integer.MIN_VALUE;
            } else { // Partial Overlap
                int leftChild = getLeftChild(treeIndex);
                int rightChild = getRightChild(treeIndex);
                int mid = treeStart + (treeEnd - treeStart) / 2;

                long leftMax = getMaxRangeQueryHelper(queryStart, queryEnd, treeStart,
                        mid, leftChild);
                long rightMax = getMaxRangeQueryHelper(queryStart, queryEnd, mid + 1,
                        treeEnd, rightChild);
                long max = leftMax <= rightMax ? rightMax : leftMax;
                this.segmentArr[treeIndex] = max;
                return max;
            }
        }
        return Integer.MIN_VALUE;
    }

    private void updateIfRequired(int treeIndex) {
        if (this.lazyPropagationArr[treeIndex] == 0) {
            return;
        } else {
            long incrementValue = this.lazyPropagationArr[treeIndex];
            this.segmentArr[treeIndex] += incrementValue;
            updateLazyPropagationTree(treeIndex, incrementValue);
        }
    }

    private void updateLazyPropagationTree(int treeIndex, long incrementValue) {
        this.lazyPropagationArr[treeIndex] = 0;
        int leftChildIndex = getLeftChild(treeIndex);
        int rightChildIndex = getRightChild(treeIndex);

        if (leftChildIndex < this.segmentArrSize)
            this.lazyPropagationArr[leftChildIndex] += incrementValue;
        if (rightChildIndex < this.segmentArrSize)
            this.lazyPropagationArr[rightChildIndex] += incrementValue;
    }

    private int getLeftChild(int treeIndex) {
        return (2 * treeIndex) + 1;
    }

    private int getRightChild(int treeIndex) {
        return (2 * treeIndex) + 2;
    }

    private boolean isPartialOverlap(int queryStartRange, int queryEndRange,
                                     int treeStartRange, int treeEndRange) {

        if ((treeStartRange <= queryStartRange && treeEndRange >= queryEndRange)
                ||
                (treeStartRange < queryStartRange && treeEndRange >= queryStartRange)
                ||
                (treeStartRange <= queryEndRange && treeEndRange > queryEndRange)) {
            return true;
        }
        return false;
    }

    private boolean isCompleteOverlap(int queryStartRange, int queryEndRange,
                                      int treeStartRange, int treeEndRange) {

        if (treeStartRange >= queryStartRange && treeEndRange <= queryEndRange) {
            return true;
        }
        return false;
    }

    private boolean isNoOverlap(int queryStartRange, int queryEndRange,
                                int treeStartRange, int treeEndRange) {

        if (treeEndRange < queryStartRange || treeStartRange > queryEndRange) {
            return true;
        }
        return false;
    }
}
