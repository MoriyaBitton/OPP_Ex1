package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph g;

    public WGraph_Algo() {
        weighted_graph g = new WGraph_DS();
    }

    /**
     * this algorithm should find the shortest path between node src to node dest.
     * to do it I use hash map, deleted list and and priority queue.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the lowest weight path from node src to node dest
     */
    private double Dijkstra(int src, int dest) {
        if (g == null || this.g.getNode(src) == null || this.g.getNode(dest) == null)
            return -1;//this nodes not in my graph
        HashMap<Integer, Double> weighted = new HashMap<Integer, Double>();
        Iterator<node_info> it = g.getV().iterator();
        ArrayList<Integer> deletedList = new ArrayList<Integer>();
        PriorityQueue<node_info> pq = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info a, node_info b) {
                return -(Double.compare(weighted.get(a.getKey()), weighted.get(b.getKey()))); //- is min heap
            }
        });
        while (it.hasNext()) {
            node_info temp = it.next();
            weighted.put(temp.getKey(), Double.MAX_VALUE); //EVERY V WEIGHT START WITH INFINITY
        }
        weighted.put(src, 0.0);//my first node info w is 0
        pq.add(this.g.getNode(src));

        while (!pq.isEmpty()) {
            node_info cur = pq.poll();
            deletedList.add(cur.getKey());
            Iterator<node_info> it2 = g.getV(cur.getKey()).iterator(); //all ni of cur
            while (it2.hasNext()) {
                node_info tempNi = it2.next();
                if (weighted.get(cur.getKey()) + g.getEdge(cur.getKey(), tempNi.getKey()) < weighted.get(tempNi.getKey())) { //ni
                    weighted.put(tempNi.getKey(), weighted.get(cur.getKey()) + g.getEdge(cur.getKey(), tempNi.getKey())); //ni //reset the new weight
                    pq.add(tempNi);
                }
            }
        }
        if (weighted.get(dest) == Double.MAX_VALUE) return -1; //I dont have any route
        return weighted.get(dest);
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * @return underlying graph of which this class works
     */
    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return the new copy graph
     */
    @Override
    public weighted_graph copy() {
        weighted_graph newWG = new WGraph_DS();
        if (g == null) return null;
        for (node_info Node : g.getV()) { //copy nodes
            node_info newNode = new WGraph_DS.NodeInfo(); //copy constructor- NodeInfo
            newNode.setTag(Node.getTag()); //copy Node Tag
            newNode.setInfo(Node.getInfo()); //copy Node Info
        }
        for (node_info Node1 : g.getV()) { //copy each node ni == edges
            for (node_info ni : g.getV(Node1.getKey())) {

                if (!g.hasEdge(Node1.getKey(), ni.getKey())) {
                    g.connect(Node1.getKey(), ni.getKey(), g.getEdge(Node1.getKey(), ni.getKey()));
                }
            }
        }
        return newWG;
    }

    /**
     * Returns true if and only if there is a valid path from EVERY node to each other node.
     * NOTE: assume directional graph.
     *
     * @return true- iff the graph is connected
     */
    @Override
    public boolean isConnected() {
        if (g.nodeSize() == 0 || g.nodeSize() == 1) {
            return true;
        }
        for (node_info Node : g.getV()) {
            Node.setTag(-1.0); //mark each node in g graph as not visited
        }
        int counter = 0;
        int s = -1;
        for (node_info Node : g.getV()) {
            s = Node.getKey();
            break;
        }
        LinkedList<Integer> queue = new LinkedList<Integer>();
        if (g.getNode(s) != null) {
            g.getNode(s).setTag(0.0); //first Node with s=key mark as visited
            counter++;
            queue.add(s);
        }
        while (queue.size() != 0) { //queue is not empty
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            // visited and enqueue it
            for (node_info ni : g.getV(s)) {
                if (ni.getTag() == -1.0) { //not visited in this node
                    ni.setTag(0.0); // number- mark as visited; 0- mark as not visited
                    counter++;
                    queue.add(ni.getKey());
                }
            }
        }
        return counter == g.nodeSize();
    }

    /**
     * returns the length of the shortest path between src to dest.
     * Note: if no such path --> returns -1.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the shortest path weight
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return Dijkstra(src, dest);
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest.
     * Note: if no such path --> returns null.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return list of node_info that represent my shortest path steps
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> list = new ArrayList<>();
        Iterator<node_info> itZero = g.getV().iterator(); // setup all of my nodes tag to zero
        while (itZero.hasNext()) {
            itZero.next().setTag(0);
        }
        HashMap<Integer, List<node_info>> ListN = new HashMap<>();
        if (this.g == null || this.g.getNode(src) == null || this.g.getNode(dest) == null || Dijkstra(src, dest) == -1)
            return null;//this nodes not in my graph
        HashMap<Integer, Double> weighted = new HashMap<Integer, Double>();
        Iterator<node_info> it = g.getV().iterator();
        ArrayList<Integer> deletedList = new ArrayList<Integer>();
        PriorityQueue<node_info> pq = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info a, node_info b) {
                return -(Double.compare(weighted.get(a.getKey()), weighted.get(b.getKey()))); //- is min heap
            }
        });
        while (it.hasNext()) {
            node_info temp = it.next();
            weighted.put(temp.getKey(), Double.MAX_VALUE); //EVERY V WEIGHT START WITH INFINITY
            temp.setInfo(Integer.toString(temp.getKey()));
        }
        weighted.put(src, 0.0);//my first node info w is 0
        pq.add(this.g.getNode(src));

        while (!pq.isEmpty()) {
            node_info cur = pq.poll();
            deletedList.add(cur.getKey());
            Iterator<node_info> it2 = g.getV(cur.getKey()).iterator(); //all ni of cur
            while (it2.hasNext()) {

                node_info tempNi = it2.next();
                if (weighted.get(cur.getKey()) + g.getEdge(cur.getKey(), tempNi.getKey()) < weighted.get(tempNi.getKey())) { //ni
                    tempNi.setTag(cur.getKey()); //my temp ni tag is my last node key
                    weighted.put(tempNi.getKey(), weighted.get(cur.getKey()) + g.getEdge(cur.getKey(), tempNi.getKey())); //ni //reset the new weight
                    pq.add(tempNi);
                }
            }
        }
        Stack<node_info> helpAns = new Stack<>();
        node_info curN = g.getNode(dest);
        while (curN != g.getNode(src)) {
            helpAns.push(curN);
            curN = g.getNode((int) curN.getTag());
        }
        helpAns.push(g.getNode(src));
        while (!helpAns.empty()) {
            list.add(helpAns.pop());
        }
        return list;
    }

    /**
     * Saves this weighted (undirected) graph to the given file name.
     *
     * @param file - the file name (may include a relative path)
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(g);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph of this class will be changed (to the loaded one),
     * in case the graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.to load a file with graph from my desktop as a java doc
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.g = (weighted_graph) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return false;
        }
        return true;
    }
}
