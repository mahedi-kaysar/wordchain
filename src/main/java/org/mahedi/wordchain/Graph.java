package org.mahedi.wordchain;

import java.util.List;

/**
 * @author mahedi
 *
 */
public class Graph {
	private List<Vertex> vertexes;
	private List<Edge> edges;

	public Graph(List<Vertex> vertexes, List<Edge> edges) {
		this.vertexes = vertexes;
		this.edges = edges;
	}

	public List<Vertex> getVertexes() {
		return vertexes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

}
