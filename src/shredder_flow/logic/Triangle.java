package shredder_flow.logic;

import javax.vecmath.Vector2d;

public class Triangle {

	private TriangulationVertexList vertices;
	private TriangleList neighbors;
	private FieldVector fieldVector;

	public void setNeighbors(TriangleList neighbors) {
		this.neighbors = neighbors;
	}

	public boolean isNeighbor(Triangle triangle) {
		if (vertices.hasTwoOrMoreEqualVertices(triangle.getVertices())) {
			return true;
		} else {
			return false;
		}
	}

	public Triangle(TriangulationVertexList vertices) {
		this.vertices = vertices;
		this.fieldVector = new FieldVector(0, 0);
	}

	/**
	 * Gives the neighboring triangle closest to this position. If this there
	 * are more than once, i.e. we are at a vertex, then returns null.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Triangle getNeighbor(double x, double y) {
		// TODO: implement
		return null;
	}

	/**
	 * Gives the neighboring triangle closest to this position. If this there
	 * are more than once, i.e. we are at a vertex, then it the triangle that
	 * lies in the given direction based at given position is returned.
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @return
	 */
	public Triangle getNeighbor(double x, double y, Vector2d direction) {
		if (isVertex(x, y)) {
			// TODO: implement, taking the direction into account
			return null;
		} else {
			return getNeighbor(x, y);
		}
	}

	public VertexList getVertices() {
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
	 * @return true, if lies within or on the boundary, else false.
	 */
	public boolean isInTriangle(double x, double y) {
		// TODO: implement
		return true;
	}
}
