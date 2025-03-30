import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {
     
    public static int solve(int[] arr, int target) {
        // TODO: Implement your solution here

        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];

            Integer instances = map.get(val);

            if (instances != null) {
                count += 1;

                if (instances == 1) {
                    map.remove(val);
                } else {
                    map.put(val, instances - 1);
                }
                
            } else {
                map.put(target - val, map.getOrDefault(target - val, 0) + 1);
            }
        }

        return count;
    }
}
