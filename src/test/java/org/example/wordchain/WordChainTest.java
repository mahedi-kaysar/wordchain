/**
 * 
 */
package org.example.wordchain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mahedi
 *
 */
public class WordChainTest {

	private List<Vertex> nodes;
	private List<Edge> edges;

	private static String[] dictionary;
	private static boolean isSetupDone = false;

	@Before
	public void setUp() throws Exception {
		if (!isSetupDone) {
			System.out.println("Initilize the dictionary");
			String path = "src/main/resources/websters-dictionary.txt";
			dictionary = createDictionary(path).toArray(new String[0]);
			isSetupDone = true;
		}
	}

	@Test
	public void test1Execute() {
		System.out.println("Running test1Execute()");
		String src = "lead";
		String dest = "gold";

		ForkJoinPool pool = new ForkJoinPool();
		WordVertexGenerator rootTask = new WordVertexGenerator(src.length(), dictionary, 0, dictionary.length);
		nodes = pool.invoke(rootTask);
		pool.shutdown();

		Graph graph = buildWordGraph();

		WordChain wordChain = new WordChain(graph);
		wordChain.execute(Constants.vertexMap.get(src));
		ArrayList<Vertex> path = wordChain.getPath(Constants.vertexMap.get(dest));

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (Vertex vertex : path) {
			System.out.println(vertex.getId());
		}
		System.out.println("================");

	}

	@Test
	public void test2Execute() {
		System.out.println("Running test2Execute()");

		String src = "volume";
		String dest = "golden";

		ForkJoinPool pool = new ForkJoinPool();
		WordVertexGenerator rootTask = new WordVertexGenerator(src.length(), dictionary, 0, dictionary.length);
		nodes = pool.invoke(rootTask);
		pool.shutdown();

		Graph graph = buildWordGraph();

		WordChain wordChain = new WordChain(graph);
		wordChain.execute(Constants.vertexMap.get(src));
		ArrayList<Vertex> path = wordChain.getPath(Constants.vertexMap.get(dest));

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (Vertex vertex : path) {
			System.out.println(vertex.getId());
		}
		System.out.println("================");
	}

	/*
	 * @param vertexNameList (list of same length words based on src and dest
	 * word length)
	 * 
	 * @return graph
	 */
	public Graph buildWordGraph() {
		edges = new ArrayList<Edge>();

		// build edges between the words differs by 1 character
		for (Vertex u : nodes) {
			for (Vertex v : nodes) {
				if (isDiffByOneChar(v.getId(), u.getId())) {
					addEdge(u, v, 1);
				}
			}
		}
		return new Graph(nodes, edges);
	}

	private void addEdge(Vertex source, Vertex destination, int weight) {
		Edge edge = new Edge(source, destination, weight);
		edges.add(edge);
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

	/*
	 * @path (text file)
	 * 
	 * @return (set of unique words which is called dictionary)
	 */
	public static Set<String> createDictionary(String path) {
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		final Set<String> dictionary = new LinkedHashSet<String>();

		try {
			fileReader = new FileReader(new File(path));
			bufferedReader = new BufferedReader(fileReader);
			try {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					if (!line.isEmpty())
						dictionary.add(line.trim().toLowerCase());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dictionary;
	}

	public static List<String> getSubset(int wordLenght, Set<String> dictionary) {
		Iterator<String> it = dictionary.iterator();
		List<String> newSet = new ArrayList<String>();
		String s;
		while (it.hasNext()) {
			s = it.next();
			if (s.length() == wordLenght)
				newSet.add(s);
		}
		return newSet;
	}

}
