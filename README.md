# Word Chain Solver
A Java library for word chain solver. More specifically, an algorithm for transforming one word to another word using the dictionary words as a intermediate transformations. This algorithm finds the shortest path to make the word being transformation.

# Installing
This project is compiled with Java 1.8 and [Apache Maven](https://maven.apache.org/)

    $ git clone git@github.com:mahedi-kaysar/wordchain
    $ cd wordchain
    $ mvn clean install

# Examples

Input: $src = lead and $dest = gold
Output: [lead -> load -> goad -> gold]

		WordChain wordChain = new DijkstraWordChain();
		ArrayList<String> path = wordChain.print(src, dest, dictionary);

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (String word : path) {
			System.out.println(word);
		}
		
All the intermediate words are produced from the dictionary.

# Algorithm Complexity

1) if N = total words in the dictionary, M = number of similar length words like source or target, E = total edges, then
2) For Generating list of Vertexes -> worse case complexity is: 		0(M) 
3) For generating list of Edges -> worse case complexity is: 			0(M*M)
4) For executing the Dijkstra using graph -> worse case complexity is: 	0(M + 2*E) 	

There the total worse case complexity is: O(2*M + 2*E + M*M). In this implementation, the edge size could be much higher than node size.


#Future Work
For improving the performance build the graph using parallel execution and implement graph partitioning algorithm.
