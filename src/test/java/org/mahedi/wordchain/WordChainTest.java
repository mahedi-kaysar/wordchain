/**
 * 
 */
package org.mahedi.wordchain;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * WordChainTest tests the WordChain Algorithm
 * @author Mahedi Kaysar
 *
 */
public class WordChainTest {

	private static Map<Integer, Set<String>> customedDictionary;
	private static boolean isSetupDone = false;

	@Before
	public void setUp() throws Exception {
		if (!isSetupDone) {
			System.out.println("Initilize the dictionary");
			String path = "src/main/resources/websters-dictionary.txt";
			customedDictionary = createCustomDictionary(path);
			isSetupDone = true;
			System.out.println("Initialisation Done");
		}
	}

	@Test
	public void reachableTest1Execute() {
		System.out.println("Running reachableTest1Execute()");
		String src = "lead";
		String dest = "gold";

		WordChain wordChain = new DijkstraWordChain();
		ArrayList<String> path = wordChain.get(src, dest, customedDictionary);

		assertNotNull(path);
		assertTrue(path.size() > 0);
		print(path);
		System.out.println("reachableTest1Execute() done");

	}

	@Test
	public void reachableTest2Execute() {
		System.out.println("Running reachableTest2Execute()");

		String src = "volume";
		String dest = "golden";

		WordChain wordChain = new DijkstraWordChain();
		ArrayList<String> path = wordChain.get(src, dest, customedDictionary);

		assertNotNull(path);
		assertTrue(path.size() > 0);
		print(path);
		System.out.println("reachableTest2Execute() Done");
	}
	
	@Test
	public void unreachableTestExecute(){
		System.out.println("Running unreachableTestExecute()");

		String src = "abcdef";
		String dest = "zxyrpq";

		WordChain wordChain = new DijkstraWordChain();
		ArrayList<String> path = wordChain.get(src, dest, customedDictionary);
		assertNotNull(path);
		assertTrue(path.isEmpty());
		
		System.out.println("unreachableTestExecute() Done");
	}
	
	@Test
	public void nullTestExecute(){
		System.out.println("Running nullTestExecute()");

		String src = "abcd";
		String dest = "zxyrp";

		WordChain wordChain = new DijkstraWordChain();
		ArrayList<String> path = wordChain.get(src, dest, customedDictionary);
		assertNull(path);
		
		System.out.println("nullTestExecute() Done");
	}

	private static void print(ArrayList<String> path){
		for (int i=0;i<path.size();i++) {
			if(i==path.size()-1)
				System.out.print(path.get(i));
			else
				System.out.print(path.get(i)+"->");
		}
		System.out.println("\n==========================");
	}
	/**
	 * @path path of the text file
	 * @return set of unique words which is called word dictionary
	 */
	private static Set<String> createMainDictionary(String path) {
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

	/**
	 * @path path of the text file
	 * @return set of unique words with categorized with length
	 */
	private static Map<Integer, Set<String>> createCustomDictionary(String path) throws Exception{
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		final Map<Integer, Set<String>> customDictionary = new HashMap<>();

		try {
			fileReader = new FileReader(new File(path));
			bufferedReader = new BufferedReader(fileReader);
			try {
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					if (!line.isEmpty()) {
						String word = line.trim().toLowerCase();
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
		return customDictionary;
	}

	/**
	 * @dictionary dictionary is the set of unique words
	 * @return set of unique words with categorized with length
	 */
	private static Map<Integer, Set<String>> createCustomDictionary(Set<String> dictionary) {

		final Map<Integer, Set<String>> customDictionary = new HashMap<>();
		Iterator<String> it = dictionary.iterator();
		String line;
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
