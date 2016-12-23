package org.example.wordchain;

/**
 * @author mahedi
 *
 */
public class Vertex {
	private String id;

	public Vertex(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + "]";
	}
	
}
