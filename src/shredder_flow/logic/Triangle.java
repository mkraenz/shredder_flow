package shredder_flow.logic;

import java.util.ArrayList;

public class Triangle {

	private TriangulationVertexList vertices;
	private TriangleList neighbors;
	private FieldVector fieldVector;

	public void setNeighbors(TriangleList neighbors) {
		if (neighbors.size() != 3) {
			throw new IllegalArgumentException(
					"A triangle can only have up to 3 neighbors. The given list contains "
							+ neighbors.size()
							+ " triangles. If there are no further triangles, specify null to fill up the list.");
		}
		this.neighbors = neighbors;
	}

	public boolean isNeighbor(Triangle triangle) {
		// TODO: implement
		return false;
	}

	public Triangle(TriangulationVertexList vertices) {
		this.vertices = vertices;
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
		for (Vertex vertex : vertices) {
			if (vertex.isPositionEqual(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the position (x,y) lies within or on the boundary of this
	 * triangle.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInTriangle(double x, double y) {
		// TODO: implement
		return true;
	}
}
