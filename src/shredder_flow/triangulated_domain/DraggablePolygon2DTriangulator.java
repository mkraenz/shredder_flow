package shredder_flow.triangulated_domain;

import de.jtem.java2dx.Point2DList;
import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;
import de.jtem.numericalMethods.geometry.meshGeneration.ruppert.Ruppert;

public class DraggablePolygon2DTriangulator implements ITriangulator {
	private int minimalAngleConstraint = 20;
	private Ruppert ruppert;

	public void triangulate(DraggablePolygon2D boundaryCurve) {
		DraggablePoint2DList listOfControlPoints = boundaryCurve.getControlPoints();
		Point2DList controlPointList = listOfControlPoints.getModel();
		double[][] pointsForRuppert = rearrangeToOneCrossNDimensionalArray(controlPointList);
		Ruppert ruppert = triangulateWithRuppert(pointsForRuppert);
		this.ruppert = ruppert;
	}

	private Ruppert triangulateWithRuppert(double[][] pointsForRuppert) {
		Ruppert ruppert = new Ruppert(pointsForRuppert);
		ruppert.setAngleConstraint(minimalAngleConstraint);
		ruppert.refine();
		return ruppert;
	}

	public double[][] getPoints2DArray() {
		return rearrangeToNCrossTwoDimensionalArray(this.ruppert.getPoints());
	}

	public double[] getPoints1DArray() {
		return this.ruppert.getPoints();
	}

	public int[] getIndices() {
		return this.ruppert.getIndices();
	}

	public int[] getNeighbors() {
		return ruppert.getNeighbors();
	}

	private double[][] rearrangeToOneCrossNDimensionalArray(
			Point2DList pointList) {
		double[][] boundaryPoints = pointList.toDoubleArray();
		double[][] pointsForRuppert = new double[1][boundaryPoints.length * 2];
		for (int i = 0; i < boundaryPoints.length; i++) {
			pointsForRuppert[0][i * 2] = boundaryPoints[i][0];
			pointsForRuppert[0][i * 2 + 1] = boundaryPoints[i][1];
		}
		return pointsForRuppert;
	}

	private double[][] rearrangeToNCrossTwoDimensionalArray(double[] points) {
		double[][] newPoints = new double[points.length / 2][2];
		for (int i = 0; i < points.length; i = i + 2) {
			newPoints[i / 2][0] = points[i];
			newPoints[i / 2][1] = points[i + 1];
		}
		return newPoints;
	}

	@Override
	public void setAngleConstraint(int maximalAngle) {
		this.minimalAngleConstraint = maximalAngle;
		
	}
}
