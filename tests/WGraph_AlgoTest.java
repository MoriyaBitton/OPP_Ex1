package ex1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    private static int i = 0;
    private static String [] tests  = {"init", "getGraph", "copy", "isConnected", "shortestPathDist", "shortestPath", "save", "load"};
    private static Random random = null;

    @BeforeEach
    void Before() {
        System.out.println("test: " + tests[i]);
        i++;
    }

    @Test
    void init() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g); //empty graph
        int n = g.nodeSize();
        int e = g.edgeSize();
        boolean c = g1.isConnected();
        double s = g1.shortestPathDist(1, 2);
        assertEquals(0, n, "if graph g is an empty graph: node Size");
        assertEquals(0, e, "if graph g is an empty graph: edge Size");
        assertEquals(true, c, "if graph g is an empty graph: is Connection");
        assertEquals(-1, s, "if graph g is an empty graph: shortest Path Dist");
    }

    @Test
    void getGraph() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(g, g1.getGraph(), "correct");
    }

    @Test
    void copy() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);

        weighted_graph g2 = g1.copy();
        g2.removeEdge(16, 17);
        boolean e1;
        if (g.hasEdge(16, 17) && (!g2.hasEdge(16, 17))) {
            e1 = true;
        } else {
            e1 = false;
        }
        assertEquals(false, e1, "deep copy- remove non legal edge");

        g2.removeEdge(9, 17);
        boolean e2;
        if (g.hasEdge(9, 17) && (!g2.hasEdge(9, 17))) {
            e2 = true;
        } else {
            e2 = false;
        }
        assertEquals(true, e2, "deep copy- remove a legal edge");

        g2.removeNode(16);
        boolean n;
        Collection<node_info> c = g.getV();
        Collection<node_info> c2 = g2.getV();
        if (c.contains(g.getNode(16)) && (!c2.contains(g.getNode(16)))) {
            n = true;
        } else {
            n = false;
        }
        assertEquals(true, n, "deep copy- remove legal node");
    }

    @Test
    void isConnected() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        boolean c = g1.isConnected();
        assertEquals(true, c, "full graph- graph1");
        g.removeNode(1);
        g.removeNode(6);
        boolean c1 = g1.isConnected();
        assertEquals(false, c1, "full graph- graph1");

        weighted_graph gc = graph_creator(10000, 100000, 2);
        weighted_graph_algorithms gc1 = new WGraph_Algo();
        gc1.init(gc);
        System.out.println(gc1.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        double d2 = g1.shortestPathDist(0, 6);
        assertEquals(2.14, d2, "if graph g is a full graph_num2: shortest Path Dist");
    }

    public static void PrintShortestPathDist(ArrayList<node_info> L) { //t
        if (L == null) return;
        int i = 0;
        for (node_info node : L) {
            System.out.print(node.getKey() + " -> ");
            i++;
            if (i == L.size()) {
                System.out.println("|| "); //the end
            }
        }
    }

    @Test
    void shortestPath() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        List<node_info> L2 = g1.shortestPath(0, 6);
        int Ls2 = L2.size();
        PrintShortestPathDist((ArrayList<node_info>) L2);
        assertEquals(3, Ls2, "if graph g is a full graph_num2: shortest Path Size");
    }

    @Test
    void save() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        g1.save("C:\\Users\\moria\\Desktop\\graph");
        weighted_graph g2 = new WGraph_DS();
        g1.init(g2);
    }

    @Test
    void load() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        g1.load("C:\\Users\\moria\\Desktop\\graph");
//        for (node_info node : g1.getGraph().getV()) {
//            System.out.println("The Key is: " + node.getKey());
//            for (node_info ni : g1.getGraph().getV(node.getKey())) {
//                System.out.println(ni.getKey() + ",");
//            }
//        }
    }

    public static weighted_graph graph1() {
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        for (int i = 0; i < 20; i++) {
            g.addNode(i);
        } //20 nodes

        g.connect(1, 2, 0.5);
        g.connect(1, 7, 5);
        g.connect(1, 10, 0.7);
        g.connect(1, 18, 12);
        g.connect(2, 6, 3);
        g.connect(2, 11, 9);
        g.connect(2, 16, 1);
        g.connect(2, 19, 2);
        g.connect(3, 4, 9);
        g.connect(3, 7, 0.1);
        g.connect(3, 8, 4);
        g.connect(3, 12, 5);
        g.connect(4, 13, 11);
        g.connect(4, 17, 11);
        g.connect(4, 0, 3);
        g.connect(5, 9, 1);
        g.connect(5, 13, 3);
        g.connect(5, 14, 1);
        g.connect(6, 10, 0.14);
        g.connect(6, 15, 0.37);
        g.connect(6, 18, 19);
        g.connect(6, 0, 4.7);
        g.connect(7, 11, 11.7);
        g.connect(7, 12, 7.12);
        g.connect(8, 15, 8.15);
        g.connect(8, 9, 8.9);
        g.connect(8, 14, 3);
        g.connect(9, 19, 10);
        g.connect(9, 17, 8);
        g.connect(10, 0, 2);
        g.connect(11, 9, 0.01);
        g.connect(12, 16, 40);
        g.connect(17, 19, 100);
        //33 edges

        return g;
    }

    /**
     * graph_creator should return a weighted_graph g who has a v_size vertical and e_size edges.
     *
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        random = new Random(seed);
        for (int i = 0; i < v_size; i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while (g.edgeSize() < e_size) {
            int i = (int) nodes[nextRnd(0, v_size)];
            int j = (int) nodes[nextRnd(0, v_size)];
            g.connect(i, j, 10);
        }
        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = random.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }

    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
//        System.out.println(size);
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nodes[i].getKey();
        }
        Arrays.sort(ans);
        return ans;
    }
}