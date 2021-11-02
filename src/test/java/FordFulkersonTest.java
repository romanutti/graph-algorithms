import org.junit.Test;
import util.GraphReader;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class FordFulkersonTest {
    private GraphReader graphReader;
    private FordFulkerson graph;

    // Max flow params
    int s;
    int t;

    /**
     * Setup for edge cases from input file.
     *
     * @param testcase input file suffix
     * @throws Exception
     */
    public void setupFromFile(int testcase) throws Exception {
        graphReader = new GraphReader("fordfulkerson", testcase);

        // Read data
        int nodeCount = Integer.parseInt(graphReader.next());
        graph = new FordFulkerson(nodeCount);

        s = Integer.parseInt(graphReader.next());
        t = Integer.parseInt(graphReader.next());

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                int value = Integer.parseInt(graphReader.next());
                if (value > 0) {
                    graph.addEdge(i, j, value);
                }
            }
        }
    }

    /**
     * Testcase 1:
     * Graph mit 0 Knoten
     */
    @Test public void testcase_1() throws Exception {
        // Setup graph
        setupFromFile(1);

        // Check result
        assertEquals(0.0, graph.maxFlow(s, t), 0.01);
    }

    /**
     * Testcase 2:
     * Graph mit 1 Knoten ohne Schleife
     */
    @Test public void testcase_2() throws Exception {
        // Setup graph
        setupFromFile(2);

        // Check result
        assertEquals(0.0, graph.maxFlow(s, t), 0.01);
    }

    /**
     * Testcase 3:
     * Gerichteter, gewichteter Graph
     * s = 0, t = 5
     */
    @Test public void testcase_3() throws Exception {
        // Setup graph
        setupFromFile(3);

        // Check result
        assertEquals(8.0, graph.maxFlow(s, t), 0.01);
    }

    /**
     * Testcase 4:
     * Gerichteter, gewichteter Graph
     * s = 0, t = 1
     */
    @Test public void testcase_4() throws Exception {
        // Setup graph
        setupFromFile(4);

        // Check result
        assertEquals(4.0, graph.maxFlow(s, t), 0.01);
    }

    /**
     * Testcase 5:
     * Gerichteter, gewichteter Graph
     * s = 0, t = 0
     */
    @Test public void testcase_5() throws Exception {
        // Setup graph
        setupFromFile(5);

        // Check result
        assertEquals(0.0, graph.maxFlow(s, t), 0.01);
    }

    /**
     * Testcase 6:
     * Nicht zusammenhängender Graph
     * unverbundene Knoten
     */
    @Test public void testcase_6() throws Exception {
        // Setup graph
        setupFromFile(6);

        // Check result
        assertEquals(0.0, graph.maxFlow(s, t), 0.01);
    }

    /**
     * Testcase 7:
     * Nicht zusammenhängender Graph
     * verbundene Knoten
     */
    @Test public void testcase_7() throws Exception {
        // Setup graph
        setupFromFile(7);

        // Check result
        assertEquals(2.0, graph.maxFlow(s, t), 0.01);
    }

    /**
     * Testcase 8:
     * Graph zur Verifikation von
     * Edmonds-Karp
     */
    @Test public void testcase_8() throws Exception {
        // Setup graph
        setupFromFile(8);
        // Check result
        assertEquals(20000.0, graph.maxFlow(s, t), 0.01);

        // Reset graph
        setupFromFile(8);
        // Check number of iterations
        assertEquals(2, graph.numberOfUpdates(s, t));
    }

    /**
     * Setup worst case graph for scalability.
     */
    public void setupWorstCase(int nodeCount) {
        graph = new FordFulkerson(nodeCount);
        Random random = new Random();

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount ; j++) {
                graph.addEdge(i, j, random.nextInt() & Integer.MAX_VALUE);
            }
        }
    }

    /**
     * Scalability 1:
     * Graph mit vielen Knoten und Kanten
     */
    @Test public void scalability_1() {

        double start, end, total;
        int randomGraphsCount = 5;

        outerloop:
        for (int i = 0; i < Integer.MAX_VALUE; i += 100) {

            total = 0;
            for (int j = 0; j < randomGraphsCount; j++) {
                // Try to execute cycle detection
                try {
                    setupWorstCase(i);
                    start = System.currentTimeMillis();

                    graph.maxFlow(0, i - 1 );
                } catch (OutOfMemoryError error) {
                    System.out.println("OutOfMemoryError at " + i + " nodes!");
                    break outerloop;
                }

                end = System.currentTimeMillis();
                total += end - start;
            }

            System.out.println(
                    "Number of nodes: " + i + " Avg. execution time: " + String.valueOf(total / randomGraphsCount));
        }
    }
}
