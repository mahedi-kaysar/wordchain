package org.example.wordchain;

import java.util.ArrayList;
import java.util.Set;

public interface WordChain {
	public ArrayList<String> print(String source, String target, Set<String> dictionary);
}