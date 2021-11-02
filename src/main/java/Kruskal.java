import java.util.LinkedList;
import java.util.PriorityQueue;

public class Kruskal {

    /**
     * Priority queue for edge processing
     */
    public PriorityQueue<ComparableEdge> pq = new PriorityQueue<>();

    /**
     * Lookup structure for quick union
     */
    public Components components;

    /**
     * Number of nodes
     */
    public int nodeCount;

    public Kruskal(int nodeCount) {
        this.nodeCount = nodeCount;
        this.components = new Components(nodeCount);
    }

    public LinkedList<ComparableEdge> mst() {
        LinkedList<ComparableEdge> mst = new LinkedList<>();

        // Add edges as long edges are left and
        while (!pq.isEmpty() && mst.size() < nodeCount - 1) {
            ComparableEdge edge = pq.poll();
            int source = edge.source();
            int target = edge.target();

            if (components.connected(source, target)) {
                // Edge can not be added to mst
                continue;
            } else {
                // Merge components.
                components.union(source, target);
                // Add edge to mst.
                mst.addLast(edge);
            }
        }

        return mst;
    }

    public void addEdge(int source, int target, int weight) {
        pq.add(new ComparableEdge(source, target, weight));
    }

    /**
     * Edge that is compared via its weight.
     */
    static class ComparableEdge implements Comparable<ComparableEdge> {
        public int source;
        public int target;
        public int weight;

        public ComparableEdge(int source, int target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public int source() {
            return source;
        }

        public int target() {
            return this.target;
        }

        public int compareTo(ComparableEdge n) {
            if (weight > n.weight) {
                return 1;
            } else if (weight < n.weight) {
                return -1;
            }
            return 0;
        }

        public String toString() {
            return source + "/" + target + ":" + weight + " ";
        }
    }

    /**
     * Lookup structure for quick union
     */
    static class Components {
        // Tree pointing to parent
        private int[] parents;
        // Size of trees
        private int[] sizes;

        public Components(int nodeCount) {

            // Initialize components
            parents = new int[nodeCount];
            sizes = new int[nodeCount];

            for (int i = 0; i < nodeCount; i++) {
                // Initially all components are its own parent
                parents[i] = i;
                // Initially all trees have size 1
                sizes[i] = 1;
            }
        }

        public boolean connected(int source, int target) {
            // Components with same root
            return findRoot(source) == findRoot(target);
        }

        private int findRoot(int component) {
            // Root points to itself
            while (component != parents[component])
                component = parents[component];

            return component;
        }

        public void union(int source, int target) {
            // Give source and target the same root.
            int sourceRoot = findRoot(source);
            int targetRoot = findRoot(target);

            if (sourceRoot == targetRoot) {
                // Components are already connected
                return;
            } else {
                // Connect smaller side to larger
                if (sizes[sourceRoot] < sizes[targetRoot]) {
                    parents[sourceRoot] = targetRoot;
                    sizes[targetRoot] += sizes[sourceRoot];
                } else {
                    parents[targetRoot] = sourceRoot;
                    sizes[sourceRoot] += sizes[targetRoot];
                }
            }
        }
    }
}
