import org.junit.Test;
import util.GraphReader;

import java.util.Random;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CycleDetectorTest {
    private GraphReader graphReader;
    private CycleDetector graph;

    /**
     * Setup for edge cases from input file.
     *
     * @param testcase input file suffix
     * @throws Exception
     */
    public void setupFromFile(int testcase) throws Exception {
        graphReader = new GraphReader("cycledetection", testcase);

        // Read data
        int nodeCount = Integer.parseInt(graphReader.next());
        graph = new CycleDetector(nodeCount);

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                int value = Integer.parseInt(graphReader.next());
                if (value != 0)
                    graph.addEdge(i, j, value);
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
        assertFalse(graph.hasCycle());
    }

    /**
     * Testcase 2:
     * Graph mit 1 Knoten ohne Schleife
     */
    @Test public void testcase_2() throws Exception {
        // Setup graph
        setupFromFile(2);

        // Check result
        assertFalse(graph.hasCycle());
    }

    /**
     * Testcase 3:
     * Graph mit 1 Knoten und Schleife
     */
    @Test public void testcase_3() throws Exception {
        // Setup graph
        setupFromFile(3);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Testcase 4:
     * Gewichteter, gerichteter Graph ohne Zyklus
     */
    @Test public void testcase_4() throws Exception {
        // Setup graph
        setupFromFile(4);

        // Check result
        assertFalse(graph.hasCycle());
    }

    /**
     * Testcase 5:
     * Gewichteter, gerichteter Graph mit Zyklus
     */
    @Test public void testcase_5() throws Exception {
        // Setup graph
        setupFromFile(5);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Testcase 6:
     * Gewichteter, gerichteter Graph ohne Zyklus mit Schleife
     */
    @Test public void testcase_6() throws Exception {
        // Setup graph
        setupFromFile(6);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Testcase 7:
     * Gewichteter, gerichteter Graph mit Zyklus und Schleife
     */
    @Test public void testcase_7() throws Exception {
        // Setup graph
        setupFromFile(7);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Testcase 8:
     * Gewichteter, gerichteter Graph mit trivialem Zyklus
     */
    @Test public void testcase_8() throws Exception {
        // Setup graph
        setupFromFile(8);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Testcase 9:
     * Negativ gewichteter, gerichteter Graph mit Zyklus
     */
    @Test public void testcase_9() throws Exception {
        // Setup graph
        setupFromFile(9);

        // Check result
        assertFalse(graph.hasCycle());
    }

    /**
     * Testcase 10:
     * Negativ gewichteter, gerichteter Graph mit Zyklus
     */
    @Test public void testcase_10() throws Exception {
        // Setup graph
        setupFromFile(10);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Testcase 11:
     * Graph mit gemeinsamen Target aber ohne Zyklus
     */
    @Test public void testcase_11() throws Exception {
        // Setup graph
        setupFromFile(11);

        // Check result
        assertFalse(graph.hasCycle());
    }

    /**
     * Testcase 12:
     * Nicht zusammenhängender Graph mit Zyklus
     */
    @Test public void testcase_12() throws Exception {
        // Setup graph
        setupFromFile(12);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Testcase 13:
     * Graph mit parallelen Kanten
     */
    @Test public void testcase_13() throws Exception {
        // Doppelt getestet: Ist selbes wie trivialer kreis
        // Setup graph
        setupFromFile(13);

        // Check result
        assertTrue(graph.hasCycle());
    }

    /**
     * Setup worst case graph for scalability.
     */
    public void setupWorstCase(int nodeCount) {
        graph = new CycleDetector(nodeCount);
        Random random = new Random();

        for (int i = 0; i < nodeCount - 1; i++) {
            graph.addEdge(i, i + 1, random.nextInt());
        }
    }

    /**
     * Scalability 1:
     * Graph mit tiefer Rekursion
     */
    @Test public void scalability_1() {

        double start, end, total;
        int randomGraphsCount = 10;

        outerloop:
        for (int i = 1; i < Integer.MAX_VALUE; i += 100) {

            total = 0;
            for (int j = 0; j < randomGraphsCount; j++) {
                setupWorstCase(i);
                start = System.currentTimeMillis();

                // Try to execute cycle detection
                try {
                    graph.hasCycle();
                } catch (StackOverflowError error) {
                    System.out.println("StackOverflow at " + i + " nodes!");
                    break outerloop;
                }

                end = System.currentTimeMillis();
                total += end - start;
            }
            System.out.println(
                    "Number of edges: " + i + " Avg. execution time: " + String.valueOf(total / randomGraphsCount));
        }
    }
}
