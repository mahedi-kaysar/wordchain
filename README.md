# Word Chain Solver
A Java library for word chain solver. More specifically, an algorithm for transforming one word to another word using the dictionary words as an intermediate transformations. This algorithm finds the shortest path to make the word being transformed.

# Installing
This project is compiled with Java 1.8 and [Apache Maven](https://maven.apache.org/)

    $ git clone git@github.com:mahedi-kaysar/wordchain
    $ cd wordchain
    $ mvn clean install

# Examples

Input: #src = lead and #dest = gold
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

1) if N = total words in the dictionary, M = number of similar length words like source or target, E = total edges (worse case, E=M*(M-1)), then
	
	# For Generating list of Vertexes -> worse case complexity is: 			0(M) 
	# For generating list of Edges -> worse case complexity is: 			0(M*M)
 	# For executing the Dijkstra using graph -> worse case complexity is: 	0(M + 2*E) 	

There the total worse case complexity is: O(2*M + 2*E + M*M). In this implementation, the edge size could be much higher than node size.


#Future Work
For improving the performance build the graph using parallel execution and implement graph partitioning algorithm.
