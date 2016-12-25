package org.mahedi.wordchain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class WordVertexGenerator extends RecursiveTask<List<Vertex>> {

	private static final long serialVersionUID = 1L;
	private int wordLength;
	private String[] dictionary;
	private int start;
	private int end;
	private int threshhold = 100;

	public WordVertexGenerator(int wordLength, String[] dictionary, int start, int end) {
		this.wordLength = wordLength;
		this.dictionary = dictionary;
		this.start = start;
		this.end = end;
		Constants.vertexMap.clear();
	}

	@Override
	protected List<Vertex> compute() {
		List<Vertex> vertexes = new ArrayList<Vertex>();
		if ((start - end) <= threshhold) {
			return getWordVertex();
		} else {
			int mid = start + (end - start) / 2;
			WordVertexGenerator left = new WordVertexGenerator(wordLength, dictionary, start, mid);
			WordVertexGenerator right = new WordVertexGenerator(wordLength, dictionary, mid, end);

			// invoke the tasks in parallel and wait for them to complete
			invokeAll(left, right);
			// join all the list from each sub-ranges.
			vertexes.addAll(left.join());
			vertexes.addAll(right.join());

			return vertexes;
		}
	}

	private ArrayList<Vertex> getWordVertex() {
		ArrayList<Vertex> list = new ArrayList<Vertex>();
		for (int i = start; i < end; i++) {
			if (dictionary[i].length() == wordLength) {
				Vertex vertex = new Vertex(dictionary[i]);
				list.add(vertex);
				Constants.vertexMap.put(dictionary[i], vertex);
			}
		}
		return list;
	}
}
