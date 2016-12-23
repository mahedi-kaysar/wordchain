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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mahedi
 *
 */
public class WordChainTest {

	private List<Vertex> nodes;
    private List<Edge> edges;		
	private Map<String, Vertex> nodeMap = new HashMap();
	private Graph graph;
	private List<String> wordList;
	private String src="lead";
	private String dest = "gold";
    
	
	@Before
	public void setUp() throws Exception {
		String path = "src/main/resources/websters-dictionary.txt";
		Set<String> dictionary = createDictionary(path);
		wordList = getSubset(src.length(), dictionary);
		graph = buildWordGraph(wordList);
	}

	@Test
	public void testExecute() {
		
        WordChain wordChain = new WordChain(graph);
        wordChain.execute(nodeMap.get(src));
        ArrayList<Vertex> path = wordChain.getPath(nodeMap.get(dest));

        assertNotNull(path);
        assertTrue(path.size() > 0);

        for (Vertex vertex : path) {
                System.out.println(vertex.getId());
        }
	}
	/*
	 * @param vertexNameList (list of same length words based on src and dest word length)
	 * @return graph
	 */
	public Graph buildWordGraph(List<String> vertexNameList) {
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		
		// build nodes by the WordList of same length
		for (String vertexId : vertexNameList) {
			Vertex node = new Vertex(vertexId);
			nodes.add(node);
			nodeMap.put(vertexId, node);
		}

		// build edges between the words differs by 1 character
		for (Vertex vertex : nodes) {
			for (String vertexId : vertexNameList) {
				if (isDiffByOneChar(vertexId, vertex.getId())) {
					Vertex destination = nodeMap.get(vertexId);
					addEdge(vertex, destination, 1);
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
		final Set<String> dictionary = new HashSet<String>();

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
		List<String> newSet = new ArrayList();
		String s;
		while (it.hasNext()) {
			s = it.next();
			if (s.length() == wordLenght)
				newSet.add(s);
		}
		return newSet;
	}
	
}
