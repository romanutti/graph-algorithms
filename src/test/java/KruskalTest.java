import org.junit.Test;
import util.GraphReader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class KruskalTest {

    private GraphReader graphReader;
    private Kruskal graph;

    /**
     * Setup for edge cases from input file.
     *
     * @param testcase input file suffix
     * @throws Exception
     */
    public void setupFromFile(int testcase) throws Exception {
        graphReader = new GraphReader("kruskal", testcase);

        // Read data
        int nodeCount = Integer.parseInt(graphReader.next());
        graph = new Kruskal(nodeCount);

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                int value = Integer.parseInt(graphReader.next());
                if (j < i && value != 0) {
                    // Only edges for half matrix has to be added
                    // as undirected
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

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual =new LinkedList<>();
        assertThat(actual, is(expected));
    }

    /**
     * Testcase 2:
     * Graph mit 1 Knoten ohne Schleife
     */
    @Test public void testcase_2() throws Exception {
        // Setup graph
        setupFromFile(2);

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual = new LinkedList<>();
        assertThat(actual, is(expected));
    }

    /**
     * Testcase 3:
     * Graph mit 1 Knoten mit Schleife
     */
    @Test public void testcase_3() throws Exception {
        // Setup graph
        setupFromFile(3);

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual = new LinkedList<>();
        assertThat(actual, is(expected));
    }

    /**
     * Testcase 4:
     * Ungerichteter, gewichteter Graph
     */
    @Test public void testcase_4() throws Exception {
        // Setup graph
        setupFromFile(4);

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();
        expected.add(new Kruskal.ComparableEdge(3, 2, 1));
        expected.add(new Kruskal.ComparableEdge(2, 1, 2));
        expected.add(new Kruskal.ComparableEdge(1, 0, 3));

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual = graph.mst();
        assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(actual.toArray()));
    }

    /**
     * Testcase 5:
     * Ungerichteter, gewichteter Graph
     * mit negativen Kanten
     */
    @Test public void testcase_5() throws Exception {
        // Setup graph
        setupFromFile(5);

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();
        expected.add(new Kruskal.ComparableEdge(2, 0, -4));
        expected.add(new Kruskal.ComparableEdge(3, 2, 1));
        expected.add(new Kruskal.ComparableEdge(2, 1, 2));

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual = graph.mst();
        assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(actual.toArray()));
    }

    /**
     * Testcase 6:
     * Nicht zusammenhängender Graph
     * ein Baum
     */
    @Test public void testcase_6() throws Exception {
        // Setup graph
        setupFromFile(6);

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();
        expected.add(new Kruskal.ComparableEdge(3, 2, 1));
        expected.add(new Kruskal.ComparableEdge(2, 1, 2));
        expected.add(new Kruskal.ComparableEdge(1, 0, 3));

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual = graph.mst();
        assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(actual.toArray()));
    }

    /**
     * Testcase 7:
     * Nicht zusammenhängender Graph
     * zwei Bäume und neues Gewicht
     */
    @Test public void testcase_7() throws Exception {
        // Setup graph
        setupFromFile(7);

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();
        expected.add(new Kruskal.ComparableEdge(3, 2, 1));
        expected.add(new Kruskal.ComparableEdge(2, 1, 2));
        expected.add(new Kruskal.ComparableEdge(1, 0, 3));
        expected.add(new Kruskal.ComparableEdge(5, 4, 5));

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual = graph.mst();
        assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(actual.toArray()));
    }

    /**
     * Testcase 8:
     * Nicht zusammenhängender Graph
     * zwei Bäume und vorhandenes Gewicht
     */
    @Test public void testcase_8() throws Exception {
        // Setup graph
        setupFromFile(8);

        // Build expected result
        LinkedList<Kruskal.ComparableEdge> expected = new LinkedList<>();
        expected.add(new Kruskal.ComparableEdge(3, 2, 1));
        expected.add(new Kruskal.ComparableEdge(5, 4, 2));
        expected.add(new Kruskal.ComparableEdge(2, 1, 2));
        expected.add(new Kruskal.ComparableEdge(1, 0, 3));

        // Check result
        LinkedList<Kruskal.ComparableEdge> actual = graph.mst();
        assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(actual.toArray()));
    }

    /**
     * Setup worst case graph for scalability.
     */
    public void setupWorstCase(int nodeCount) {
        graph = new Kruskal(nodeCount);
        Random random = new Random();

        for (int i = 0; i < nodeCount ; i++) {
            // Graph points to every neighbour
            // and weight is increased for every edge
            graph.addEdge(0, i, i);
        }
    }

    /**
     * Scalability 1:
     * Graph mit unausbalaciertem Komponentenbaum
     */
    @Test public void scalability_1() {

        double start, end, total;
        int randomGraphsCount = 10;

        outerloop:
        for (int i = 100000; i < Integer.MAX_VALUE; i += 1000) {

                total = 0;
            for (int j = 0; j < randomGraphsCount; j++) {
                // Try to execute cycle detection
                try {
                    setupWorstCase(i);
                    start = System.currentTimeMillis();

                    graph.mst();
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

