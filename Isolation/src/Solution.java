import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Solution {
    // TODO: Implement your solution here
    public static int solve(int[] arr) {
        int maxLength = -1;

        int left = 0;

        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];

            if (!set.contains(val)) {
                set.add(val);
                maxLength = Math.max(i - left + 1, maxLength);
                continue;
            }

            while (set.contains(val)) {
                set.remove(arr[left++]);
            }
            set.add(val);
        }


        return maxLength;
    }
}