/**
 * Calcualtes shortest paths in a directed, weighted graph.
 */
public class FloydWarshall {

    public static final String NEGATIVE_CYCLE = "neg.Cycle";

    /**
     * Adjacency matrix
     */
    public double[][] matrix;

    /**
     * Number of edges
     */
    public int edgeCount = 0;

    public FloydWarshall(int nodeCount){
        this.matrix = new double[nodeCount][nodeCount];
    }

    /**
     * Returns costs of shortest path between i and j, using v0, ..., vk-1 as possible hops.
     * @param i From node
     * @param j To node
     * @param k Nodes v0, ... , vk-1 that can be used for hops
     * @return Costs of shortest path or "Neg.Cycle" if negative cycle detected
     */
    public String shortestPath(int i, int j, int k) {

        if(floydWarshall(k)){
            // No negative cycles
            if(getNumberOfNodes() > 0 ){
                return String.valueOf(matrix[i][j]);
            } else {
                // No node results in no path
                return String.valueOf(0D);
            }
        } else {
            // Negative cycle
            return NEGATIVE_CYCLE;
        }
    }

    /**
     * Calculates matrix using v0, ..., vk-1 as possible hops
     * @param k Nodes v0, ... , vk-1 that can be used for hops
     * @return If matrix is valid, or if negative cycle has been found
     */
    private boolean floydWarshall(int k) {
        int nodeCount = getNumberOfNodes();
        for (int a = 0; a < k; a++) {
            for (int b = 0; b < nodeCount; b++) {
                for (int c = 0; c < nodeCount; c++) {
                    if (matrix[b][c] > matrix[b][a] + matrix[a][c]) {
                        // Adjust costs
                        matrix[b][c] = matrix[b][a] + matrix[a][c];

                        // Check for negative cycles
                        if(c == b && matrix[b][c] < 0){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public int getNumberOfNodes(){
        return matrix.length;
    }

    public void addEdge(int nodeFrom, int nodeTo, double weight){
        this.matrix[nodeFrom][nodeTo] = weight;
        edgeCount++;
    }

}
