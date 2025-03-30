import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Solution {
    // TODO: Include your data structures here
    static class Team implements Comparable<Team> {
        int id, solves;
        long penalty;

        public Team(int id) {
            this.id = id;
            solves = 0;
            penalty = 0;
        }

        public void update(long newPenalty) {
            solves += 1;
            penalty += newPenalty;
        }

        public int compareTo(Team other) {
            if (this.solves != other.solves) {
                return other.solves - this.solves;
            }
            if (this.penalty > other.penalty) {
                return 1;
            } else if (this.penalty == other.penalty) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    private Team[] teams;
    private TreeSet<Team> tree;

    private int rankOfTeam1; 


    public Solution(int numTeams) {
        teams = new Team[numTeams];
        tree = new TreeSet<>();

        for (int i = 0; i < numTeams; i++) {
            teams[i] = new Team(i);
            tree.add(teams[i]);
        }

        rankOfTeam1 = 1;
    }

    public int update(int teamId, long newPenalty){
        Team curr = teams[teamId-1];
        boolean wasBeforeTeam1 = false;

        tree.remove(curr);
        if (curr.compareTo(teams[0]) >= 0) {
            wasBeforeTeam1 = true;
        }

        //System.out.println(curr.compareTo(teams[0]));

        curr.update(newPenalty);

        tree.add(curr);

        //System.out.println(curr.compareTo(teams[0]) < 0);

        if (teamId == 1) {
            rankOfTeam1 = tree.headSet(teams[0]).size() + 1;
            return rankOfTeam1;
        } else if (wasBeforeTeam1 && curr.compareTo(teams[0]) < 0) {
            return ++rankOfTeam1;
        }

        return rankOfTeam1;
    }

    public static void main(String[] args) {
        Solution soln = new Solution(3);
        System.out.println(soln.update(2, 7)); // 2
        System.out.println(soln.update(3, 5)); // 3
        System.out.println(soln.update(1, 6)); // 2
        System.out.println(soln.update(1, 9)); // 1
        System.out.println(soln.update(3, 5)); // 2
        System.out.println(soln.update(2, 15)); // 2
        System.out.println(soln.update(1, 2)); // 1
    }
}
