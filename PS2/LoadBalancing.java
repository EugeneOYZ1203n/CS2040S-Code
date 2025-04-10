/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        if (jobSizes.length == 0){
            return false;
        }

        int count = 0;
        int currLoad = 0;

        for (int i = 0; i < jobSizes.length; i++) {
            int val = currLoad + jobSizes[i];
            if (jobSizes[i] > queryLoad) {
                return false;
            }
            if (val > queryLoad) {
                currLoad = jobSizes[i];
                count += 1;
                if (count > p){
                    return false;
                }
            } else {
                currLoad = val;
            }
        }

        if (currLoad > 0) { 
            count += 1; 
            if (count > p){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSizes, int p) {

        if (jobSizes.length == 0 || p < 1) {
            return -1;
        }

        int start = 0;
        int end = 0;

        for (int x : jobSizes) {
            end = end+x;
        }
        start = end/p - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (isFeasibleLoad(jobSizes, mid, p)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        System.out.println(isFeasibleLoad(new int[] {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},17, 5));
    }
}
