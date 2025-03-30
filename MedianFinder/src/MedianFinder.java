import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {

    // TODO: Include your data structures here
    private PriorityQueue<Integer> maxHeap; // Lower half
    private PriorityQueue<Integer> minHeap; // Upper half

    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }

    public void insert(int x) {
        if (maxHeap.isEmpty() || x <= maxHeap.peek()) {
            maxHeap.add(x);
        } else {
            minHeap.add(x);
        }

        if (maxHeap.size() > minHeap.size()) {
            minHeap.add(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
    }

    public int getMedian() {
        int median = minHeap.poll(); // Median is always at the top of maxHeap

        // Rebalance if needed
        if (minHeap.size() < maxHeap.size()) {
            minHeap.add(maxHeap.poll());
        }

        return median;
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.insert(4);
        mf.insert(2);
        mf.insert(3);
        mf.insert(1);

        System.out.println(mf.getMedian()); // 3
        System.out.println(mf.getMedian()); // 2
        System.out.println(mf.getMedian()); // 4
        System.out.println(mf.getMedian()); // 1
    }
}
