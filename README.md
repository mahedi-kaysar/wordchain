# Word Chain Solver
A Java library for word chain solver. More specifically, an algorithm for transforming one word to another word using the dictionary words as a intermediate transformations. This algorithm find the shortest path to make the transformation. The algorithm complexity is O(N2) where N is the number of words (length of each words is the source or targeted word's length) in the dictionary. 

# Installing
This project is compiled with Java 1.8 and [Apache Maven](https://maven.apache.org/)

    $ git clone git@github.com:mahedi-kaysar/wordchain
    $ cd wordchain
    $ mvn clean install

# Examples

Input: $src = lead and $dest = gold
Output: [lead -> load -> goad -> gold]

All the intermediate words came from dictionary.

#Future Work
For improving the performance build the graph using parallel execution.
For example, if the dictionary size is N = 1000000
Partitioned could be: P = 100
Size of each partition, M = N/P = 10000
Worse Case time complexity would be: ~ O (M) for graph building.