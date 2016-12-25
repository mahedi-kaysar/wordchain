package org.example.wordchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class DijkstraWordChain implements WordChain {

	@Override
	public ArrayList<String> print(String source, String target, Set<String> dictionary) {
		// TODO Auto-generated method stub
		if (source.length() == target.length()) {
			// generate graph nodes and edges based on source word
			List<Vertex> nodes = generateNodes(source.length(), dictionary.toArray(new String[0]));
			List<Edge> edges = generateEdges(nodes);
			Graph graph = new Graph(nodes, edges);
			Dijkstra dijkstra = new Dijkstra(graph);
			dijkstra.execute(Constants.vertexMap.get(source));
			ArrayList<Vertex> shortestPath = dijkstra.getPath(Constants.vertexMap.get(target));
			ArrayList<String> result = new ArrayList<>();
			for (Vertex v : shortestPath)
				result.add(v.getId());
			return result;
		}
		return null;
	}

	private List<Vertex> generateNodes(int wordSize, String[] dictionary) {
		ForkJoinPool pool = new ForkJoinPool();
		WordVertexGenerator rootTask = new WordVertexGenerator(wordSize, dictionary, 0, dictionary.length);
		List<Vertex> nodes = pool.invoke(rootTask);
		pool.shutdown();
		return nodes;
	}

	private List<Edge> generateEdges(List<Vertex> nodes) {
		List<Edge> edges = new ArrayList<Edge>();
		// build edges between the words differs by 1 character
		for (Vertex u : nodes) {
			for (Vertex v : nodes) {
				if (isDiffByOneChar(v.getId(), u.getId())) {
					edges.add(addEdge(u, v, 1));
				}
			}
		}
		return edges;
	}

	private Edge addEdge(Vertex source, Vertex destination, int weight) {
		return new Edge(source, destination, weight);
	}

	private boolean isDiffByOneChar(String s1, String s2) {
		// System.out.println(s1+":"+s2);
		int count = 0;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i))
				count++;
			if (count > 1)
				return false;
		}
		if (count == 0)
			return false;
		return true;
	}
}
