import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        Arrays.sort(houses);

        double start = 0.0, end = houses[houses.length-1] - houses[0];

        while (start < end - 0.5) {
            double mid = (end-start)/2 + start;

            if (coverable(houses, numOfAccessPoints, mid)) {
                end = mid;
            } else {
                start = mid;
            }
        }
        
        return start;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        if (houses.length == 0) return true;
        
        Arrays.sort(houses);
        int index = 0;

        for (int i = 0; i < numOfAccessPoints; i++) {
            double start = houses[index];
            
            double end = start + distance * 2;
            
            if (index + 1 == houses.length) return true;

            while (houses[index+1] <= end) {
                index += 1;
                if (index + 1 == houses.length) return true;
            }

            index += 1;
        }
        
        return false;
    }

    public static void main(String[] args) {
        WiFi.coverable(new int[]{1,3,10}, 2, 1.0);
    }
}
