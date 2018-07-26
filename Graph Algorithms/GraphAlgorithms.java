import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementations of various graph algorithms.
 *
 * @author Jongwoo Jang
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex. You will return a List of the vertices in the order that you
     * visited them. Make sure to include the starting vertex at the beginning
     * of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes that
     * implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException
     *             if any input is null, or if {@code start} doesn't exist in
     *             the graph
     * @param start
     *            the Vertex you are starting at
     * @param graph
     *            the Graph we are searching
     * @param <T>
     *            the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "starting vertex not existing in graph");
        }
        Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
        List<Vertex<T>> retList = new ArrayList<Vertex<T>>();
        Queue<Vertex<T>> myQ = new LinkedList<Vertex<T>>();
        visited.add(start);
        myQ.add(start);
        retList.add(start);
        while (!myQ.isEmpty()) {
            Vertex<T> temp = myQ.remove();
            for (VertexDistancePair<T> v : graph.getAdjacencyList().get(temp)) {
                if (!visited.contains(v.getVertex())) {
                    visited.add(v.getVertex());
                    retList.add(v.getVertex());
                    myQ.add(v.getVertex());
                }
            }
        }
        return retList;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex. You will return a List of the vertices in the order that you
     * visited them. Make sure to include the starting vertex at the beginning
     * of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You MUST implement this method recursively. Do not use any data structure
     * as a stack to avoid recursion. Implementing it any other way WILL cause
     * you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the aforementioned
     * interfaces.
     *
     * @throws IllegalArgumentException
     *             if any input is null, or if {@code start} doesn't exist in
     *             the graph
     * @param start
     *            the Vertex you are starting at
     * @param graph
     *            the Graph we are searching
     * @param <T>
     *            the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("invalid parameter");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "starting vertex not existing in graph");
        }
        Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
        List<Vertex<T>> retList = new ArrayList<Vertex<T>>();
        visited.add(start);

        retList.add(start);
        depthFirstSearchHelper(start, graph, visited, retList);
        return retList;
    }

    /**
     * Recursive helper method for Depth First Search
     * 
     * @param start
     *            the starting vertex
     * @param graph
     *            the graph to execute
     * @param visited
     *            visited vertex
     * @param <T>
     *            the data type representing the vertices in the graph.
     * @param retList
     *            a List of vertices in the order that was visited
     */
    private static <T> void depthFirstSearchHelper(Vertex<T> start,
            Graph<T> graph, Set<Vertex<T>> visited, List<Vertex<T>> retList) {
        for (VertexDistancePair<T> v : graph.getAdjacencyList().get(start)) {
            if (!visited.contains(v.getVertex())) {
                visited.add(v.getVertex());
                retList.add(v.getVertex());
                v.getVertex().toString();
                depthFirstSearchHelper(v.getVertex(), graph, visited, retList);
            }
        }
    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance to
     * that node from start, or Integer.MAX_VALUE (representing infinity) if no
     * path exists. You may assume that going from a vertex to itself has a
     * distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue}, {@code java.util.Map}
     * , and any class that implements the aforementioned interface.
     *
     * @throws IllegalArgumentException
     *             if any input is null, or if {@code start} doesn't exist in
     *             the graph
     * @param start
     *            the Vertex you are starting at
     * @param graph
     *            the Graph we are searching
     * @param <T>
     *            the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node in
     *         the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("invalid paramter");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "starting vertex not existing in graph");
        }
        Set<VertexDistancePair<T>> visited = 
                new HashSet<VertexDistancePair<T>>();
        Map<Vertex<T>, Integer> retMap = new HashMap<Vertex<T>, Integer>();
        PriorityQueue<VertexDistancePair<T>> pQueue = 
                new PriorityQueue<VertexDistancePair<T>>();
        pQueue.add(new VertexDistancePair<T>(start, 0));
        visited.add(new VertexDistancePair<T>(start, 0));
        Set<Edge<T>> initiate = graph.getEdgeList();
        retMap.put(start, 0);
        for (Edge<T> e : initiate) {
            retMap.putIfAbsent(e.getU(), Integer.MAX_VALUE);
            retMap.putIfAbsent(e.getV(), Integer.MAX_VALUE);
        }

        while (!pQueue.isEmpty()) {
            VertexDistancePair<T> temp = pQueue.remove();
            for (VertexDistancePair<T> v : graph.getAdjacencyList()
                    .get(temp.getVertex())) {
                int dist = retMap.get(temp.getVertex());
                if (dist == Integer.MAX_VALUE) {
                    dist = v.getDistance();
                } else {
                    dist = retMap.get(temp.getVertex()) + v.getDistance();
                }
                if (retMap.get(v.getVertex()) > dist) {
                    retMap.replace(v.getVertex(), dist);
                    VertexDistancePair<T> toAdd = new VertexDistancePair<T>(
                            v.getVertex(), dist);
                    pQueue.add(toAdd);
                }
                visited.add(v);
            }
        }
        return retMap;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree in the form of a set of Edges. If the graph is disconnected, and
     * therefore there is no valid MST, return null.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may assume that for a given starting vertex, there will only be one
     * valid MST that can be formed. In addition, only an undirected graph will
     * be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue}, {@code java.util.Set}
     * , and any class that implements the aforementioned interface.
     *
     * @throws IllegalArgumentException
     *             if any input is null, or if {@code start} doesn't exist in
     *             the graph
     * @param start
     *            the Vertex you are starting at
     * @param graph
     *            the Graph we are searching
     * @param <T>
     *            the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("invalid paramter");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "starting vertex not existing in graph");
        }

        Set<Edge<T>> retSet = new HashSet<Edge<T>>();
        PriorityQueue<Edge<T>> pqueue = new PriorityQueue<Edge<T>>();
        Set<Vertex<T>> cycleCheck = new HashSet<Vertex<T>>();
        int checkConnection = 0;
        for (VertexDistancePair<T> vd : graph.getAdjacencyList().get(start)) {
            pqueue.add(new Edge<T>(start, vd.getVertex(), vd.getDistance(),
                    false));
            checkConnection++;
        }
        cycleCheck.add(start);

        while (!pqueue.isEmpty()) {
            Edge<T> temp = pqueue.remove();
            if (!cycleCheck.contains(temp.getV())) {
                retSet.add(temp);
                cycleCheck.add(temp.getV());
                for (VertexDistancePair<T> v : graph.getAdjacencyList()
                        .get(temp.getV())) {
                    if (!cycleCheck.contains(v.getVertex())) {
                        pqueue.add(new Edge<T>(temp.getV(), v.getVertex(),
                                v.getDistance(), false));
                    }
                    checkConnection++;
                }
            }
        }
        if ((checkConnection / 2) != graph.getEdgeList().size()) {
            return null;
        }
        return retSet;
    }

}
