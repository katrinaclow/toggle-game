package ToggleGame.backend;
import java.util.ArrayList;

/**
 *  The Graph class represents a graph of vertices named 0 through V–1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all the vertices adjacent to a vertex. It also provides
 *  methods for returning the degree of a vertex, the number of vertices
 *  V in the graph, and the number of edges E in the graph.
 *  Parallel edges and self-loops are permitted.
 *
 *  This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of Bag objects.

 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Graph {
    private final int V; // Number of vertices
    private int E; // Number of edges
    private Bag<Integer>[] adj; // Adjacency lists for vertices

    /**
     * Initializes a graph with V vertices and adds appropriate edges. Creates Bag adjacency
     * lists for each vertex.
     *
     * @param  numberOfVertices number of vertices
     * @throws IllegalArgumentException if V < 0
     */
    public Graph(int numberOfVertices) {
        if (numberOfVertices < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = numberOfVertices;
        adj = (Bag<Integer>[]) new Bag[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            adj[i] = new Bag<Integer>();
        }
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     * Throw an IllegalArgumentException unless  0 <= v < V
     * @param v the vertex
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the directed edge v-w to this graph, as every vertex will be examined.
     *
     * @param  v one vertex in the edge.
     * @param  w the other vertex in the edge.
     * @throws IllegalArgumentException unless both 0 <= v < V and  0 <= w < V
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
    }

    /**
     * Returns the vertices adjacent to vertex v.
     *
     * @param  v the vertex
     * @return the vertices adjacent to vertex v, as an iterable
     * @throws IllegalArgumentException unless 0 <= v < V
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Return an ArrayList containing the vertices adjacent to given vertex
     * @param v the vertex
     * @return an ArrayList containing all the adjacent vertices to v.
     * @throws IllegalArgumentException if parameter v is not between 0 and V.
     */
    public ArrayList<Integer> getAdjacencyList(int v) {
        validateVertex(v);
        ArrayList<Integer> adjacencyList = new ArrayList<>();
        for (int x : adj[v]) {
            adjacencyList.add(x);
        }
        return adjacencyList;
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices (V), followed by the number of edges (E),
     *         followed by the vertices' adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges \n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}