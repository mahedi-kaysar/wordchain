package org.mahedi.wordchain;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * A WordChain is an interface for getting word chain with different algorithm
 *  
 * @author  Mahedi Kaysar
 * @see DijkstraWordChain
*/
public interface WordChain {
	 /**
     * @param source source is a word to be transformed
     * @param target target is a word (same length like a source)
     * @param dictionary dictionary is a set of words
     * @return list of words called the path for reaching source to target words.
     * 		The value could be <tt>empty</tt>, or
     *      <tt>list of valid words</tt>
     */
	public ArrayList<String> get(String source, String target, Set<String> dictionary);
	 /**
     * @param source source is a word to be transformed
     * @param target target is a word (same length like a source)
     * @param dictionary map of <tt>(K,V)<tt> where K = <tt>word length<tt> and V = <tt>set of words of same length<tt>
     * @return list of words called the path for reaching source to target words.
     * 		The value could be <tt>empty</tt>, or
     *      <tt>list of valid words</tt>
     */
	public ArrayList<String> get(String source, String target, Map<Integer, Set<String>> dictionary);

}