package org.mahedi.wordchain;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @author Mahedi Kaysar
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
