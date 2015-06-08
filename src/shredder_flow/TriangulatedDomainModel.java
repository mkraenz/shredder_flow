package shredder_flow;

import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;

public class TriangulatedDomainModel {
	private DraggablePolygon2D boundaryPolygon;
	private double[][] points;
	private int[] indices;
	private DraggablePolygon2DTriangulator triangulator = new DraggablePolygon2DTriangulator();

	public TriangulatedDomainModel(int pointCount) {
		boundaryPolygon = new DraggablePolygon2D(pointCount,
				DraggablePoint2DList.CIRCULAR, 0, 0, 1, true, true);
		triangulator.triangulate(boundaryPolygon);
		points = triangulator.getPoints();
		indices = triangulator.getIndices();
	}

	public void updateTriangulation() {
		triangulator.triangulate(boundaryPolygon);
		points = triangulator.getPoints();
		indices = triangulator.getIndices();
	}

	public DraggablePolygon2D getBoundaryPolygon() {
		return boundaryPolygon;
	}

	public double[][] getPoints() {
		return points;
	}

	public int[] getIndices() {
		return indices;
	}

}
