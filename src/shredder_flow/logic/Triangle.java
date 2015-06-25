package shredder_flow.logic;

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

	public Triangle getNeighbor(double x, double y) {
		// TODO: implement
		return null;
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
	 * @return
	 */
	public boolean isInTriangle(double x, double y) {
		// TODO: implement
		return true;
	}
}
