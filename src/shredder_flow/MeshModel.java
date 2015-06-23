package shredder_flow;
import shredder_flow.TriangleList;
import shredder_flow.TriangulationVertexList;

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
