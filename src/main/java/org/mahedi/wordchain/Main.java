package org.mahedi.wordchain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Please pass the parameters {source}, {target} word and dictionary {path}");
			System.exit(0);
		}
		String source = args[0];
		String target = args[1];
		String path = args[2];
		// String source = "lead";
		// String target = "gold";
		// String path = "src/main/resources/websters-dictionary.txt";

		Map<Integer, Set<String>> dictionary = null;
		try {

			dictionary = createCustomDictionary(path);
			WordChain wordChain = new DijkstraWordChain();
			ArrayList<String> pathChain = wordChain.get(source, target, dictionary);
			for (int i = 0; i < pathChain.size(); i++) {
				if (i == pathChain.size() - 1)
					System.out.print(pathChain.get(i));
				else
					System.out.print(pathChain.get(i) + "->");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @path path of the text file
	 * @return set of unique words with categorized with length
	 */
	public static Map<Integer, Set<String>> createCustomDictionary(String path) throws Exception {
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

}
