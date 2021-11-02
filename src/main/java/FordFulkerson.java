import java.util.LinkedList;

public class FordFulkerson {

    /**
     * Node Adjacency list
     */
    public LinkedList<Edge>[] list;
    /**
     * Holds last bfs path
     */
   public Edge[] edgeTo;

    /**
     * Number of nodes
     */
    public int nodeCount;

   public FordFulkerson(int nodeCount){
       this.list = new LinkedList[nodeCount];
       this.nodeCount = nodeCount;

       for (int i = 0; i < nodeCount; i++) {
           this.list[i] = new LinkedList<>();
       }
   }

    /**
     * Calculates max flow in a s/t network.
     *
     * @param s Source node
     * @param t Target node
     * @return Maximal possible flow
     */
    public double maxFlow(int s, int t){

        double maxFlow = 0;
        // No flow in empty graph
        if (nodeCount == 0)  return 0;


        while (augmentingPathExists(s, t)) {
            // Get smallest capacity
            int cur = t;
            double min = Double.MAX_VALUE;
            while (cur != s) {
                Edge edge = edgeTo[cur];
                min = Math.min(min, edge.remainingCapacity(cur));
                cur = edge.getOtherEnd(cur);
            }

            // Adjust capacities along flow
            cur = t;
            while (cur != s) {
                Edge edge = edgeTo[cur];
                edge.updateFlow(cur, min);
                cur = edge.getOtherEnd(cur);
            }

            maxFlow += min;
        }
        return maxFlow;
    }

    /**
     * Builds BFS from s to t (saved in static variable edgeTo!)
     *
     * @param s Start node
     * @param t Target node
     * @return If path exists
     */
    private boolean augmentingPathExists(int s, int t) {
        LinkedList<Integer> queue = new LinkedList<>();

        // Reset memory
        boolean[] isMarked = new boolean[nodeCount];
        edgeTo = new Edge[nodeCount];

        // Start bfs from s
        isMarked[s] = true;
        queue.addFirst(s);

        // As long as target node not reached and still neighbours in queue
        while (edgeTo[t] == null && queue.size() > 0) {

            int cur = queue.pollLast();
            for (int i = 0; i < getNumberOfNeighbours(cur); i++) {
                Edge edge = getNeighbour(cur, i);

                int other = edge.getOtherEnd(cur);
                if (edge.remainingCapacity(other) > 0 && !isMarked[other]) {
                    // Still capacity remaining and other not discovered
                    edgeTo[other] = edge; // Remember "fastest" edge to this node
                    isMarked[cur] = true; // Mark current node as discovered
                    queue.addFirst(other);
                }
            }
        }

        // Path to t found?
        return edgeTo[t] != null;
    }

    /**
     * Calculates number of updates when calculating max flow in a s/t network.
     * Used for testing purposes only.
     *
     * @param s Source node
     * @param t Target node
     * @return Number of flow updates
     */
    protected int numberOfUpdates(int s, int t){

        int numberOfUpdates = 0;
        // No flow in empty graph
        if (nodeCount == 0)  return 0;

        while (augmentingPathExists(s, t)) {
            // Get smallest capacity
            int cur = t;
            double min = Double.MAX_VALUE;
            while (cur != s) {
                Edge edge = edgeTo[cur];
                min = Math.min(min, edge.remainingCapacity(cur));
                cur = edge.getOtherEnd(cur);
            }

            // Adjust capacities along flow
            cur = t;
            while (cur != s) {
                Edge edge = edgeTo[cur];
                edge.updateFlow(cur, min);
                cur = edge.getOtherEnd(cur);
            }
            numberOfUpdates++;
        }
        return numberOfUpdates;
    }

    public void addEdge(int nodeFrom, int nodeTo, int capacity) {
        Edge edge = new Edge(nodeFrom, nodeTo, capacity);
        this.list[nodeFrom].add(edge);
    }

    public int getNumberOfNeighbours(int cur) {
        return list[cur].size();
    }

    public Edge getNeighbour(int cur, int neighbour) {
        return list[cur].get(neighbour);
    }

    /**
     * Represents a max flow edge
     */
    public static class Edge {
        public int source;
        public int target;
        public double capacity;
        public double flow;

        public Edge(int source, int target, double capacity) {
            this.source = source;
            this.target = target;
            this.capacity = capacity;
            this.flow = 0d;
        }

        public int getOtherEnd(int node) {
            return node == source ? target : source;
        }

        public void updateFlow(int fromNode, double value) {
            if (fromNode == source)
                // Input node is source node
                flow -= value;
            else
                // Input node is target node
                flow += value;
        }

        public double remainingCapacity(int node) {
            if (node == source)
                // Input node is source node
                return flow;
            else
                // Input node is target node
                return capacity - flow;
        }
    }
}
