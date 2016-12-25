package org.mahedi.wordchain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dijkstra is the well known shortest path algorithm
 * 
 * @author Mahedi Kaysar
 * 
 */
public class Dijkstra {

	private List<Vertex> nodes;
	private List<Edge> edges;
	// A node is moved to the permanent visited set if a shortest path from the
	// source to this node has been found
	private Set<Vertex> permanentNodes;
	// First only the source is in the set of temporary visited set.
	private Set<Vertex> temporaryNodes;
	// put the parent reference of each node
	private Map<Vertex, Vertex> parents;
	// put the calculated weights of each node
	private Map<Vertex, Integer> weights;

	public Dijkstra(Graph graph) throws NullPointerException {
		this.nodes = new ArrayList<Vertex>(graph.getVertexes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}

	/**
	 * 
	 * @param source
	 */
	public void execute(Vertex source) {

		temporaryNodes = new HashSet<Vertex>();
		permanentNodes = new HashSet<Vertex>();

		// Initially the weights of all nodes should be highest possible integer
		// number
		weights = new HashMap<Vertex, Integer>();
		parents = new HashMap<Vertex, Vertex>();

		// Initialize the source node with 0 weight for making it minimum
		weights.put(source, 0);
		// Add source to temporary node sets.
		temporaryNodes.add(source);

		while (temporaryNodes.size() > 0) {

			// extract a neighbor with a minimal weight.
			Vertex node = getMinimumNeighbor(temporaryNodes);
			// add and remove the node from permanent and temporary sets
			// respectively
			permanentNodes.add(node);
			temporaryNodes.remove(node);

			// update the weights or distance of the neighbors of a parent node
			updateNeighborNodesWeights(node);
		}
	}

	/**
	 * The method find extract the minimum nodes form the temporary node set by
	 * linear search. This method could be implemented as a <tt>min heap or min
	 * priority queue<tt> in future for getting time complexity log(V)
	 * 
	 * @param vertexes
	 *            vertexes are the set of temporary nodes
	 * @return minimum vertex from the temporary nodes
	 * 
	 */
	private Vertex getMinimumNeighbor(Set<Vertex> vertexes) {
		Vertex min = null;
		for (Vertex vertex : vertexes) {
			if (min == null)
				min = vertex;
			else {
				if (getCurrentNodeWeight(vertex) < getCurrentNodeWeight(min))
					min = vertex;
			}
		}
		return min;
	}

	/**
	 * This method will traverse all the neighbors of a permanent or settled
	 * node and update nodes weights by calculating the distance from the
	 * settled node. Also add those nodes of temporary sets and keep track the
	 * parent's reference of each child.
	 * 
	 * @param node
	 *            node is the node from permanent set.
	 * @return void
	 */
	private void updateNeighborNodesWeights(Vertex node) {
		List<Vertex> adjacentNodes = getUnSettledNeighbors(node);
		for (Vertex target : adjacentNodes) {
			int sourceNodeWeight = getCurrentNodeWeight(node);
			int edgeWeight = getEdgeWeight(node, target);
			if (getCurrentNodeWeight(target) > (sourceNodeWeight + edgeWeight)) {
				weights.put(target, sourceNodeWeight + edgeWeight);
				parents.put(target, node);
				temporaryNodes.add(target);
			}
		}

	}

	/**
	 * 
	 * @param destination
	 * @return weights of a vertex, if the vertex is <tt>null<tt> returns
	 *         <tt>MAX_VALUE<tt> of Integer
	 */
	private int getCurrentNodeWeight(Vertex destination) {
		Integer d = weights.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * 
	 * @param node
	 * @param target
	 * @return get the edge weight between source node and target node
	 */
	private int getEdgeWeight(Vertex node, Vertex target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	/**
	 * 
	 * @param node
	 * @return nodes with are not in permanent set
	 */
	private List<Vertex> getUnSettledNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	/**
	 * 
	 * @param vertex
	 * @return <tt>true<tt> if the vertex is in permanent set
	 */
	private boolean isSettled(Vertex vertex) {
		return permanentNodes.contains(vertex);
	}

	/**
	 * 
	 * @param target
	 * @return shortest path to the target if available; otherwise <tt>null<tt>
	 */
	public ArrayList<Vertex> getPath(Vertex target) {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		Vertex step = target;
		// check if a path exists
		if (parents.get(step) == null) {
			return null;
		}
		path.add(step);
		// backtrack till source
		while (parents.get(step) != null) {
			step = parents.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
}