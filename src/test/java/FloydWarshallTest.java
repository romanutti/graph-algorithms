import org.junit.Test;
import util.GraphReader;

import java.util.Random;

import static org.junit.Assert.*;

public class FloydWarshallTest {
    private GraphReader graphReader;
    private FloydWarshall graph;

    // Shortest path params
    int i;
    int j;
    int k;

    /**
     * Setup for edge cases from input file.
     *
     * @param testcase input file suffix
     * @throws Exception
     */
    public void setupFromFile(int testcase) throws Exception {
        graphReader = new GraphReader("floydwarshall", testcase);

        // Read data
        int nodeCount = Integer.parseInt(graphReader.next());
        graph = new FloydWarshall(nodeCount);

        // From node
        i = Integer.parseInt(graphReader.next());
        // To node
        j = Integer.parseInt(graphReader.next());
        // Nodes v0, ... , vk-1 that can be used for hops
        k = Integer.parseInt(graphReader.next());

        // Setup graph
        for (int a = 0; a < nodeCount; a++) {
            for (int b = 0; b < nodeCount; b++) {
                int value = Integer.parseInt(graphReader.next());
                if (a == b) {
                    // Set diagonal elements to 0
                    graph.addEdge(a, b, 0);
                } else if (value == 0) {
                    // Set unknown values to infinity
                    graph.addEdge(a, b, Double.POSITIVE_INFINITY);
                } else
                    graph.addEdge(a, b, value);
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
        assertEquals("0.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 2:
     * Graph mit 1 Knoten ohne Schleife
     */
    @Test public void testcase_2() throws Exception {
        // Setup graph
        setupFromFile(2);

        // Check result
        assertEquals("0.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 3:
     * Graph mit 1 Knoten und Schleife
     */
    @Test public void testcase_3() throws Exception {
        // Setup graph
        setupFromFile(3);

        // Check result
        assertEquals("0.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 4:
     * Gerichteter, gewichteter Graph
     * i = 2, j = 1, k = 0
     */
    @Test public void testcase_4() throws Exception {
        // Setup graph
        setupFromFile(4);

        // Check result
        assertEquals("Infinity", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 5:
     * Gerichteter, gewichteter Graph
     * i = 0, j = 2, k = 0
     */
    @Test public void testcase_5() throws Exception {
        // Setup graph
        setupFromFile(5);

        // Check result
        assertEquals("Infinity", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 6:
     * Gerichteter, gewichteter Graph
     * i = 2, j = 1, k = 1
     */
    @Test public void testcase_6() throws Exception {
        // Setup graph
        setupFromFile(6);

        // Check result
        assertEquals("12.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 7:
     * Gerichteter, gewichteter Graph
     * i = 0, j = 2, k = 2
     */
    @Test public void testcase_7() throws Exception {
        // Setup graph
        setupFromFile(7);

        // Check result
        assertEquals("9.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 8:
     * Gerichteter, gewichteter Graph
     * i = 2, j = 1, k = 4
     */
    @Test public void testcase_8() throws Exception {
        // Setup graph
        setupFromFile(8);

        // Check result
        assertEquals("7.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 9:
     * Gerichteter, gewichteter Graph
     * i = 0, j = 2, k = 4
     */
    @Test public void testcase_9() throws Exception {
        // Setup graph
        setupFromFile(9);

        // Check result
        assertEquals("4.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 10:
     * Gerichteter, gewichteter Graph
     * mit negativem Zyklus
     */
    @Test public void testcase_10() throws Exception {
        // Setup graph
        setupFromFile(10);

        // Check result
        assertEquals(FloydWarshall.NEGATIVE_CYCLE, graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 11:
     * Gerichteter, gewichteter Graph
     * mit negativen Kanten
     */
    @Test public void testcase_11() throws Exception {
        // Setup graph
        setupFromFile(11);

        // Check result
        assertEquals("4.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 12:
     * Ungerichteter, gewichteter Graph
     */
    @Test public void testcase_12() throws Exception {
        // Setup graph
        setupFromFile(12);

        // Check result
        assertEquals("8.0", graph.shortestPath(i, j, k ));
    }

    /**
     * Testcase 13:
     * Nicht zusammenhängender Graph
     */
    @Test public void testcase_13() throws Exception {
        // Setup graph
        setupFromFile(13);

        // Check result
        assertEquals("Infinity", graph.shortestPath(i, j, k ));
    }

    /**
     * Setup worst case graph for scalability.
     */
    public void setupWorstCase(int nodeCount) {
        graph = new FloydWarshall(nodeCount);
        Random random = new Random();

        for (int i = 0; i < nodeCount - 1; i++) {
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
        int randomGraphsCount = 1;

        outerloop:
        for (int i = 1; i < Integer.MAX_VALUE; i += 1000) {

        total = 0;
            for (int j = 0; j < randomGraphsCount; j++) {
                // Try to execute cycle detection
                try {
                    setupWorstCase(i);
                    start = System.currentTimeMillis();

                    graph.shortestPath(0, i-1, i );
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
