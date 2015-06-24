package shredder_flow.triangulated_domain;

import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;

public class TriangulatedDomainModel implements ITriangulatedDomainModel {
	private DraggablePolygon2D boundaryPolygon;
	private double[][] points;
	private int[] indices;
	private ITriangulator triangulator;
	private int[] neighbors;

	public TriangulatedDomainModel(int pointCount, ITriangulator triangulator) {
		this.triangulator = triangulator;
		boundaryPolygon = new DraggablePolygon2D(pointCount,
				DraggablePoint2DList.CIRCULAR, 0, 0, 1, true, true);
		triangulateAndSetPointsAndIndices();
	}

	public TriangulatedDomainModel(DraggablePolygon2D boundaryPolygon, ITriangulator triangulator) {
		this.triangulator = triangulator;
		this.boundaryPolygon = boundaryPolygon;
		triangulateAndSetPointsAndIndices();
	}
	

	public void updateTriangulation() {
		triangulateAndSetPointsAndIndices();
	}

	private void triangulateAndSetPointsAndIndices() {
		triangulator.triangulate(boundaryPolygon);
		points = triangulator.getPoints2DArray();
		indices = triangulator.getIndices();
		neighbors = triangulator.getNeighbors();
	}

	public double[][] getPoints() {
		return points;
	}

	public int[] getIndices() {
		return indices;
	}
	
	public int[] getNeighbors(){
		return neighbors;
	}

	public SceneComponent getSceneComponentOfBoundaryPolygon() {
		SceneComponent component = new SceneComponent();
		component.addChild(boundaryPolygon.getViewScene());
		component.addChild(boundaryPolygon.getControlScene());
		return component;
	}

	@Override
	public void setBoundaryCurve(DraggablePolygon2D boundaryCurve) {
		this.boundaryPolygon = boundaryCurve;
		
	}

}
