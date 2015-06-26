package shredder_flow.triangulated_domain;

import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.modelling.DraggablePolygon2D;

public interface ITriangulatedDomainModel {
	public double[][] getPoints();
	
	public int[] getIndices();
	
	public SceneComponent getSceneComponentOfBoundaryPolygon();
	
	public void setBoundaryCurve(DraggablePolygon2D boundaryCurve);
}
