package shredder_flow.triangulated_domain;

import de.jtem.java2dx.modelling.DraggablePolygon2D;

public interface ITriangulator {

	public double[] getPoints1DArray();

	public double[][] getPoints2DArray();

	public int[] getIndices();

	public int[] getNeighbors();

	public void triangulate(DraggablePolygon2D boundaryCurve);

	public void setAngleConstraint(int maximalAngle);
}
