/**
 * 
 */
package org.mahedi.wordchain;

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
import org.mahedi.wordchain.DijkstraWordChain;
import org.mahedi.wordchain.WordChain;

/**
 * @author mahedi
 *
 */
public class WordChainTest {

	private static Set<String> dictionary;
	private static boolean isSetupDone = false;

	@Before
	public void setUp() throws Exception {
		if (!isSetupDone) {
			System.out.println("Initilize the dictionary");
			String path = "src/main/resources/websters-dictionary.txt";
			dictionary = createDictionary(path);
			isSetupDone = true;
		}
	}

	@Test
	public void test1Execute() {
		System.out.println("Running test1Execute()");
		String src = "lead";
		String dest = "gold";

		WordChain wordChain = new DijkstraWordChain();
		ArrayList<String> path = wordChain.print(src, dest, dictionary);

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (String word : path) {
			System.out.println(word);
		}
		System.out.println("================");

	}

	@Test
	public void test2Execute() {
		System.out.println("Running test2Execute()");

		String src = "volume";
		String dest = "golden";

		WordChain wordChain = new DijkstraWordChain();
		ArrayList<String> path = wordChain.print(src, dest, dictionary);

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (String word : path) {
			System.out.println(word);
		}

		System.out.println("================");
	}

	/*
	 * @path (text file)
	 * 
	 * @return (set of unique words which is called dictionary)
	 */
	private static Set<String> createDictionary(String path) {
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
}
