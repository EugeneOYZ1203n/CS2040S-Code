import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Solution {
    // TODO: Include your data structures here
    private TreeMap<Long, PriorityQueue<Long>> quests;
    private Map<Long, Long> totalEnergy;
    private Map<Long, Long> totalGold;

    public Solution() {
        quests = new TreeMap<>();
        totalEnergy = new HashMap<>();
        totalGold = new HashMap<>();
    }

    void add(long energy, long value) {
        quests.putIfAbsent(energy, new PriorityQueue<>());
        quests.get(energy).add(-value);
        totalEnergy.put(energy, 
            totalEnergy.getOrDefault(energy, 0L)+energy);
        totalGold.put(energy, 
            totalGold.getOrDefault(energy, 0L)+value);
    }

    long query(long remainingEnergy) {
        long res = 0;

        while (remainingEnergy > 0) {
            Long key = quests.floorKey(remainingEnergy);

            if (key == null) break;

            long maxEnergy = totalEnergy.get(key);
            long gold = totalGold.get(key);

            if (maxEnergy < remainingEnergy) {
                quests.remove(key);
                totalEnergy.remove(key);
                totalGold.remove(key);
                remainingEnergy -= maxEnergy;
                res += gold;
                continue;
            }

            PriorityQueue<Long> maxEnergyQuests = quests.get(key);

            Long next = maxEnergyQuests.poll();

            if (next == null) break;

            next = -next;

            res += next;
            remainingEnergy -= key;

            totalEnergy.put(key, maxEnergy - key);
            totalGold.put(key, gold - next);

            if (maxEnergyQuests.isEmpty()) {
                quests.remove(key);
                totalEnergy.remove(key);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Solution soln = new Solution();
        soln.add(8, 10);
        soln.add(3,25);
        System.out.println(soln.query(8));
        soln.add(5,10);
        soln.add(5,6);
        System.out.println(soln.query(10));
        System.out.println(soln.query(7));
        soln.add(1,9);
        soln.add(2,13);
        System.out.println(soln.query(20));
        System.out.println(soln.query(1));
    }

}
