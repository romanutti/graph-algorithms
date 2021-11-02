import java.util.LinkedList;

/**
 * Detects a cycle in a directed, weighted graph.
 */
public class CycleDetector {

    /**
     * Adjacency list
     */
    public LinkedList<Integer>[] list;
    /**
     * Graph visited flag
     */
    private boolean[] visited;
    /**
     * Graph in process flag
     */
    private boolean[] inProcess;
    /**
     * Flag if graph has cycle
     */
    boolean hasCycle = false;

    public CycleDetector(int nodeCount) {
        this.list = new LinkedList[nodeCount];
        this.visited = new boolean[nodeCount];
        this.inProcess = new boolean[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            this.list[i] = new LinkedList<>();
        }
    }

    /**
     * Checks if directed graph has cycle.
     * @return
     */
    public boolean hasCycle() {

        int i = 0;
        // Check possible cycle for every unprocessed node
        while (!hasCycle && i < getNumberOfNodes()) {
            if (!visited[i]) {
                hasCycle(i);
            }
            i++;
        }

        return hasCycle;
    }

    private boolean hasCycle(int cur) {
        visited[cur] = true;
        inProcess[cur] = true;

        // Check for every neighbour if cycle results
        for (int index = 0; index < getNumberOfNeighbours(cur); index++) {
            int neighbour = getNeighbour(cur, index);
             if (!visited[neighbour]) {
                // Check recursive for cycles
                hasCycle(neighbour);
            } else if (inProcess[neighbour]) {
                // Cycle found
                hasCycle = true;
            }
        }

        inProcess[cur] = false;

        return hasCycle;
    }

    public int getNumberOfNodes() {
        return list.length;
    }

    public int getNumberOfNeighbours(int cur) {
        return list[cur].size();
    }

    public int getNeighbour(int cur, int neighbour) {
        return list[cur].get(neighbour);
    }

    public void addEdge(int nodeFrom, int nodeTo, int weight) {
        // Weight is no saved, as not used for cycle detection
        this.list[nodeFrom].add(nodeTo);
    }

}
