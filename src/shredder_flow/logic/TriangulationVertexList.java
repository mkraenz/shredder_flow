package shredder_flow.logic;

public class TriangulationVertexList extends VertexList {

	private static final long serialVersionUID = 1L;

	public boolean hasTwoOrMoreEqualVertices(VertexList vertices) {
		TriangulationVertexList commonVertices = (TriangulationVertexList) this
				.clone();
		commonVertices.retainAll(vertices);
		if (commonVertices.size() >= 2) {
			return true;
		} else {
			return false;
		}
	}
}
