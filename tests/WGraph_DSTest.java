package ex1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    private static int i = 0;
    private static String[] tests = {"getNode", "hasEdge", "getEdge", "addNode", "connect", "removeNode", "removeEdge", "nodeSize", "edgeSize", "getMC"};
    private static Random random = null;

    @BeforeEach
    void Before() {
        System.out.println("test: " + tests[i]);
        i++;
    }

    @Test
    void getNode() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        int key = 16;
        node_info n = g.getNode(key);
        assertEquals(g.getNode(16), n, "correct");
    }

    @Test
    void hasEdge() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        boolean e;
        if (g.hasEdge(16, 17)) {
            e = true;
        } else {
            e = false;
        }
        assertEquals(false, e, "remove non legal edge");
        boolean e1;
        if (g.hasEdge(1, 2)) {
            e1 = true;
        } else {
            e1 = false;
        }
        assertEquals(true, e1, "remove legal edge");
    }

    @Test
    void getEdge() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        assertEquals(-1, g.getEdge(16, 17), "non legal edge");
        assertEquals(0.5, g.getEdge(1, 2), "legal edge");
    }

    @Test
    void addNode() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        int MC = g.getMC();
        if (g.getNode(21) == null) {
            g.addNode(21);
        }
        int nodeSize = g.nodeSize();
        if (g.getNode(0) == null) {
            g.addNode(0);
        }
        assertEquals(MC + 1, g.getMC(), "node(21) is a new node to g graph");
        assertEquals(nodeSize, g.nodeSize(), "node(0) is not a new node to g graph");
    }

    @Test
    void connect() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        g.connect(16, 17, 12);
        assertEquals(12, g.getEdge(16, 17), "new edge");
        g.connect(1, 2, 12);
        assertEquals(0.5, g.getEdge(1, 2), "this edge already excite");
        g.connect(1, 25, 12);
        assertEquals(-1, g.getEdge(1, 25), "non legal node");
    }

    @Test
    void removeNode() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        int ns = g.nodeSize();
        g.removeNode(25);//node 25 not exist in g graph
        assertEquals(ns, g.nodeSize());
        g.removeNode(1);
        g.removeNode(3);
        assertEquals(ns - 2, g.nodeSize());
    }

    @Test
    void removeEdge() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        int es = g.edgeSize();
        g.removeEdge(1, 4); //this edge is not exist
        assertEquals(es, g.edgeSize());
        g.removeEdge(1, 2);
        assertEquals(es - 1, g.edgeSize());
    }

    @Test
    void nodeSize() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        int nodeSize = g.nodeSize();
        g.removeNode(1);
        g.removeNode(4);
        g.removeNode(60);
        g.removeNode(3);
        g.removeNode(5);
        g.addNode(4);
        g.addNode(16);
        g.addNode(15);
        assertEquals(nodeSize - 3, g.nodeSize(), "four of five legal actions");
    }

    @Test
    void edgeSize() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        int edgeSize = g.edgeSize();
        g.removeEdge(1, 7);
        g.removeEdge(1, 10);
        g.removeEdge(1, 18);
        g.connect(16, 0, 3.6);
        g.connect(16, 20, 3.6);
        assertEquals(edgeSize - 2, g.edgeSize(), "four of five legal actions");
    }

    // @Disabled - ignoring one function under

    @Test
    void getMC() {
        weighted_graph g = graph1();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g);
        int MC = g.getMC();
        g.removeNode(0);
        g.addNode(21);
        g.removeEdge(1, 2);
        g.removeEdge(16, 17);
        assertEquals(MC + 3, g.getMC(), "3 of 4 legal changes in the graph");
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

    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        random = new Random(seed);
        for (int i = 0; i < v_size; i++) {
            node_info n = new WGraph_DS.NodeInfo();
            g.addNode(n.getKey());
        }
        int[] nodes = nodes(g);
        while (g.edgeSize() < e_size) {
            int i = nodes[nextRnd(0, v_size)];
            int j = nodes[nextRnd(0, v_size)];
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