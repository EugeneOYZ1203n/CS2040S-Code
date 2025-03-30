import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BellmanFord {
    // DO NOT MODIFY THE TWO STATIC VARIABLES BELOW
    public static int INF = 20000000;
    public static int NEGINF = -20000000;

    // TODO: add additional attributes and/or variables needed here, if any
    private ArrayList<int[]> edges;
    private Map<Integer, Integer> dists;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        edges = new ArrayList<>();
        dists = new HashMap<>();

        for (int u = 0; u < adjList.size(); u++) { 
            for (IntPair v : adjList.get(u)) { 
                edges.add(new int[]{u, v.first, v.second});

                dists.putIfAbsent(u, INF);
                dists.putIfAbsent(v.first, INF);
            }
        }
    }

    // TODO: add additional methods here, if any

    public void computeShortestPaths(int source) {
        for (int i = 0; i < dists.size(); i++) {
            dists.put(i, INF);
        }

        dists.put(source, 0);

        for (int i = 0; i < dists.size(); i++) {
            for (int j = 0; j < edges.size(); j++) {
                int[] edge = edges.get(j);
                int from = edge[0], to = edge[1], wt = edge[2];

                int dist_from = dists.get(from);
                int dist_to = dists.get(to);

                if (dist_from != INF && dist_from + wt < dist_to) {
                    if (i == dists.size()-1) {
                        markNegativeCycle(to);
                    } else {
                        dists.put(to, dist_from + wt);
                    }
                }
            }
        }
    }

    private void markNegativeCycle(int node) {
        // Perform a DFS or BFS to mark all reachable nodes as part of a negative cycle
        if (dists.get(node) != NEGINF) {
            dists.put(node, NEGINF);
            for (int[] edge : edges) {
                if (edge[0] == node) {
                    markNegativeCycle(edge[1]);  // Recurse on the neighboring node
                }
            }
        }
    }

    public int getDistance(int node) { 
        return dists.get(node);
    }

}
