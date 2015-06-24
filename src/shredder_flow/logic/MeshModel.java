package shredder_flow.logic;
import shredder_flow.logic.TriangleList;
import shredder_flow.logic.TriangulationVertexList;

public class MeshModel {
	private TriangleList triangles;
	private TriangulationVertexList vertices;

	public TriangleList getTriangles() {
		return triangles;
	}

	public void setTriangles(TriangleList triangles) {
		this.triangles = triangles;
	}

	public TriangulationVertexList getVertices() {
		return vertices;
	}

	public void setVertices(TriangulationVertexList vertices) {
		this.vertices = vertices;
	}
}
