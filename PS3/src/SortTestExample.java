import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.print.attribute.standard.MediaSize.ISO;

/**
 * This class is a simple example for how to use the sorting classes.
 * It sorts three numbers, and measures how long it takes.
 */
public class SortTestExample {
    public static void main(String[] args) {
        // Create three key value pairs
        ISort[] sorters = new ISort[] {
            new SorterA(), // Merge Sort, Same time complexity, seems to be n log n ??
            new SorterB(), // Dr Evil
            new SorterC(), // Bubble, n on best, n^2 on unsorted
            new SorterD(), // Selection, Not stable, n^2 on best, n^2 on unsorted
            new SorterE(), // Quick sort, Same time complexity as well, nlogn, Not stable
            new SorterF()  // Insertion, n on best, n^2 or nlog n on unsorted
        };

        checkStable(sorters);

        checkCosts(sorters);
        
    }

    private static void checkSort(ISort[] sorters) {
        for (int size : new int[]{5000, 10000, 20000}) {
            for (int i = 0; i < 6; i++) {
                boolean isSorted = SortingTester.checkSort(sorters[i], size);
    
                if (!isSorted) {
                    System.out.println("Sorter "+i+": "+isSorted+" At "+size);
                }
            }
        }
    }

    private static void checkStable(ISort[] sorters) {
        for (int size : new int[]{20,40,100}) {
            for (int i = 0; i < 6; i++) {
                boolean isStable = SortingTester.isStable(sorters[i], size);
    
                if (!isStable) {
                    System.out.println("Sorter "+i+": "+isStable+" At "+size);
                }
            }
        }
    }

    private static void checkCosts(ISort[] sorters) {
        int[] sizes = new int[20];
        for (int i = 0; i < 20; i++) {
            sizes[i] = 200 * (i+1);
        }
        String[] names = new String[] {
            "A b","B b","C b","D b","E b","F b",
            "A","B","C","D","E","F",
            "A w","B w","C w","D w","E w","F w",
        };
        long[][] results = new long[names.length][sizes.length];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < sizes.length; j++) {
                results[i][j] = sorters[i].sort(SortingTester.createSortedTestArray(sizes[j]));
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < sizes.length; j++) {
                results[i+6][j] = sorters[i].sort(SortingTester.createTestArrayForSort(sizes[j]));
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < sizes.length; j++) {
                results[i+12][j] = sorters[i].sort(SortingTester.createReversedTestArray(sizes[j]));
            }
        }

        exportToCSV(names, results, "sortedTest.csv");
    }

    public static void exportToCSV(String[] names, long[][] data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < data.length; i++) {
                writer.append(names[i]+",");
                for (int j = 0; j < data[i].length; j++) {
                    writer.append(Long.toString(data[i][j]));
                    if (j < data[i].length - 1) {
                        writer.append(","); // Add comma except for last column
                    }
                } 
                writer.newLine();  // New line after each row
            }
            System.out.println("CSV file saved successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
