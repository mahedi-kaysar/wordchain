package org.mahedi.wordchain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DijkstraWordChain is the implementation of <tt>WordChain</tt> interface which uses
 * Dijkstra Algorithm for finding the shortest path
 * 
 * @author Mahedi Kaysar
 */
public class DijkstraWordChain implements WordChain {

	private Map<String, Vertex> vertexMap;
	/**
	 * @see org.mahedi.wordchain.WordChain#get(java.lang.String,
	 *      java.lang.String, java.util.Set)
	 */
	@Override
	public ArrayList<String> get(String source, String target, Set<String> dictionary) {

		return get(source, target, createCustomDictionary(dictionary));
	}

	/**
	 * @see org.mahedi.wordchain.WordChain#get(java.lang.String,
	 *      java.lang.String, java.util.Map)
	 */
	@Override
	public ArrayList<String> get(String source, String target, Map<Integer, Set<String>> dictionary) {
		ArrayList<String> result = new ArrayList<>();

		if (source.length() == target.length()) {
			// in case if source and target words are not available in the dictionary
			dictionary.get(source.length()).add(source);
			dictionary.get(target.length()).add(target);
			// generate graph nodes and edges based on word length
			List<Vertex> nodes = generateNodes(source.length(), dictionary);
			System.out.println("nodes generated: " + nodes.size());
			List<Edge> edges = generateEdges(nodes);
			System.out.println("edges generated: " + edges.size());
			// initialize the graph
			Graph graph = new Graph(nodes, edges);
			System.out.println("graph generated");

			// run Dijkstra algorithm over the graph
			Dijkstra dijkstra = new Dijkstra(graph);
			dijkstra.execute(vertexMap.get(source));

			// get the shortest path between source and target words
			ArrayList<Vertex> shortestPath = dijkstra.getPath(vertexMap.get(target));

			if(shortestPath!=null){
				for (Vertex v : shortestPath)
					result.add(v.getId());
			}

			return result;
		}
		return null;
	}

	/**
	 * This method generates nodes by using mapped dictionary based to word
	 * length
	 * 
	 * @param wordSize
	 *            wordSize is the length of the words which to be transformed.
	 * @param dictionary
	 *            dictionary is categorized by word lengths.
	 * 
	 * @return list of <tt>Vertex<tt>
	 */
	private List<Vertex> generateNodes(int wordSize, Map<Integer, Set<String>> dictionary) {
		List<Vertex> nodes = new ArrayList<>();
		vertexMap = new HashMap<>();
		if (dictionary.containsKey(wordSize)) {
			Iterator<String> it = dictionary.get(wordSize).iterator();
			while (it.hasNext()) {
				String word = it.next();
				Vertex v = new Vertex(word);
				vertexMap.put(word, v);
				nodes.add(v);
			}
		}
		return nodes;

	}

	/**
	 * This method generates nodes by using mapped dictionary based to word
	 * length
	 * 
	 * @param wordSize
	 *            wordSize is the length of the words which to be transformed.
	 * @param dictionary
	 *            dictionary is categorized by word lengths.
	 * 
	 * @return list of <tt>Vertex<tt>
	 */
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

	/**
	 * This method creates a categorized set to words with respect to word
	 * lengths. The same sized words have been grouped together.
	 * 
	 * @param dictionary
	 *            dictionary is set to uncategorized words
	 * 
	 * @return map of categorized words
	 */
	private Map<Integer, Set<String>> createCustomDictionary(Set<String> dictionary) {

		final Map<Integer, Set<String>> customDictionary = new HashMap<>();
		Iterator<String> it = dictionary.iterator();
		while (it.hasNext()) {
			String word = it.next();
			int key = word.length();
			if (customDictionary.containsKey(word.length())) {
				Set<String> wordSet = customDictionary.get(key);
				wordSet.add(word);
			} else {
				Set<String> newWordSet = new HashSet<>();
				newWordSet.add(word);
				customDictionary.put(key, newWordSet);
			}
		}

		return customDictionary;
	}

}
