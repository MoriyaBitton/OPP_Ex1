package ex1;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {

    private static int ID = 0;
    private HashMap<Integer, node_info> V;
    private HashMap<Integer, HashMap<Integer, Double>> WEdge;
    private int MC = 0;
    private int edgeSize;

    /**
     * empty constructor.
     */
    public WGraph_DS() { //empty contractor
        this.V = new HashMap<Integer, node_info>();
        this.WEdge = new HashMap<Integer, HashMap<Integer, Double>>();
        this.edgeSize = 0;
        this.MC = 0;
    }

    /**
     * return the node_info by the node_id.
     *
     * @param key - the node_id
     * @return node with the key "key"
     */
    @Override
    public node_info getNode(int key) {
        if (V.containsKey(key)) {
            return V.get(key);
        }
        return null;
    }

    /**
     * return true if and only if there is an edge between node1 and node2.
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        //true if n1 and n2 are ni
        if (V.containsKey(node1) && V.containsKey(node2)) { //both exist in the graph
            if (node1 == node2) {
                return false;
            }
            if (WEdge.get(node1).containsKey(node2) && WEdge.get(node2).containsKey(node1))
                return true;
        }
        return false;
    }

    /**
     * return the weight if the edge (node1, node1).
     * In case there is no such edge - should return -1.
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            return WEdge.get(node1).get(node2); //return the weight between n1 to n2
        }
        return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!V.containsKey(key)) { //not exist in my V collection
            node_info AddedNode = new NodeInfo(key);
            V.put(key, AddedNode); //add new node to the collection V
            WEdge.put(key, new HashMap<Integer, Double>());
            MC++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with non negative weight.
     * Note: this method run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (!hasEdge(node1, node2) && V.containsKey(node1) && V.containsKey(node2) && WEdge != null) {
            WEdge.get(node1).put(node2, w);
            WEdge.get(node2).put(node1, w);
            edgeSize++;
            MC++;
        }
    }

    /**
     * This method return a pointer for a Collection representing all the nodes in the graph.
     * Note: this method run in O(1).
     *
     * @return V collection
     */
    @Override
    public Collection<node_info> getV() {
        return V.values();
    }

    /**
     * This method returns a Collection containing all the nodes connected to node_id.
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @param node_id
     * @return V collection of node_id ni
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> ni = new LinkedList<>();
        if (!V.containsKey(node_id)) return ni;
        for (Map.Entry<Integer, Double> entry : WEdge.get(node_id).entrySet()) {
            ni.add(getNode(entry.getKey()));
        }
        return ni;
    }

    /**
     * Delete the node (with the given ID) from the graph - and removes all edges which starts or ends at this node.
     * This method run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the node_info we just removed
     */
    @Override
    public node_info removeNode(int key) {
        node_info ToBeRemoved = getNode(key);
        for (node_info node : getV(key)) {
            WEdge.get(node.getKey()).remove(key);
            edgeSize--;
        }
        WEdge.remove(key);
        V.remove(key);
        MC++;
        return ToBeRemoved;
    }

    /**
     * Delete the edge from the graph.
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            WEdge.get(node1).remove(node2);
            WEdge.get(node2).remove(node1);
            edgeSize--;
            MC++;
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method run in O(1) time.
     *
     * @return current numbers of nodes
     */
    @Override
    public int nodeSize() {
        return V.size();
    }

    /**
     * return the number of edges (unidirectional graph).
     * Note: this method run in O(1) time.
     *
     * @return current number of edges
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount.
     *
     * @return current number of changes in my graph
     */
    @Override
    public int getMC() {
        return MC;
    }

    public static class NodeInfo implements node_info, Serializable {
        private int Key;
        private double Tag;
        private int nodeId = 0;
        private String Info;

        /**
         * empty constructor.
         */
        public NodeInfo() { //empty contractor
            this.Key = nodeId;
            nodeId++;
            this.Tag = 0;
            this.Info = "";
        }

        /**
         * @param key
         */
        public NodeInfo(int key) {
            this.Key = key;
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_info have a unique key.
         *
         * @return key
         */
        @Override
        public int getKey() {
            return Key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return Info
         */
        @Override
        public String getInfo() {
            return Info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s - the new string of info
         */
        @Override
        public void setInfo(String s) {
            Info = s;
        }

        /**
         * Temporal data (aka distance, color, or state) which can be used be algorithms.
         *
         * @return Tag
         */
        @Override
        public double getTag() {
            return Tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            Tag = t;
        }
    }
}
