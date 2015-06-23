package shredder_flow;

import java.util.ArrayList;

public class Triangle {

	private TriangulationVertexList vertices;
	private TriangleList neighbors;
	private FieldVector fieldVector;

	public Triangle(TriangulationVertexList vertices, TriangleList neighbors) {
		this.vertices = vertices;
		this.neighbors = neighbors;
		this.fieldVector = new FieldVector(0, 0);
	}

	public Triangle getNeighbor(double x, double y) {
		// TODO: implement
		return null;
	}

	public ArrayList<Vertex> getVertices() {
		return this.vertices;
	}

	public FieldVector getFieldVector() {
		return this.fieldVector;
	}

	public boolean isVertex(double x, double y) {
		// TODO: implment
		return false;
	}
}
