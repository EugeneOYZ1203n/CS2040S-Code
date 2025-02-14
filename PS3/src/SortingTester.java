import java.util.Random;

public class SortingTester {
    private static Random rng = new Random(1);
    
    public static boolean checkSort(ISort sorter, int size) {
        KeyValuePair[] testArray = createTestArrayForSort(size);

        sorter.sort(testArray);

        for (int i = 1; i < size; i++) {
            if (testArray[i].compareTo(testArray[i-1]) == -1) {
                return false;
            }
        }

        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        KeyValuePair[] testArray = createTestArrayForStable(size);

        sorter.sort(testArray);

        for (int i = 1; i < size; i++) {
            if (testArray[i].compareTo(testArray[i-1]) == 0
                && testArray[i].getValue() < testArray[i-1].getValue()) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        // TODO: implement this
    }

    public static KeyValuePair[] createTestArrayForSort(int size) {
        KeyValuePair[] output = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            output[i] = new KeyValuePair(rng.nextInt(size), 10);
        }

        return output;
    }

    public static KeyValuePair[] createTestArrayForStable(int size) {
        KeyValuePair[] output = new KeyValuePair[size];

        for (int i=0; i < size; i++) {
            output[i] = new KeyValuePair(rng.nextInt(10), i);
        }

        return output;
    }

    public static KeyValuePair[] createSortedTestArray(int size) {
        KeyValuePair[] output = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            output[i] = new KeyValuePair(i, 10);
        }

        return output;
    }

    public static KeyValuePair[] createReversedTestArray(int size) {
        KeyValuePair[] output = new KeyValuePair[size];

        for (int i = 0; i < size; i++) {
            output[i] = new KeyValuePair(size-i, 10);
        }

        return output;
    }
}
