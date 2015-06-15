package shredder_flow;

import de.jtem.java2dx.Point2DList;
import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;
import de.jtem.numericalMethods.geometry.meshGeneration.ruppert.Ruppert;

public class DraggablePolygon2DTriangulator {
	private static final int ANGLE_CONSTRAINT_IN_DEGREE = 25;
	private double[][] points;
	private int[] indices;

	public double[][] getPoints() {
		return points;
	}

	public int[] getIndices() {
		return indices;
	}

	public void triangulate(DraggablePolygon2D polygon2d) {
		DraggablePoint2DList listOfControlPoints = polygon2d.getControlPoints();
		Point2DList controlPointList = listOfControlPoints.getModel();
		double[][] pointsForRuppert = rearrangeAsAnArrayForRuppert(controlPointList);
		
		Ruppert ruppert = triangulateWithRuppert(pointsForRuppert);
		
		this.indices = ruppert.getIndices();
		double[] triangulationVertices = ruppert.getPoints();
		this.points = rearrangeAsTwoDimensionalArray(triangulationVertices);
	}

	private Ruppert triangulateWithRuppert(double[][] pointsForRuppert) {
		Ruppert ruppert = new Ruppert(pointsForRuppert);
		ruppert.setAngleConstraint(ANGLE_CONSTRAINT_IN_DEGREE);
		ruppert.refine();
		return ruppert;
	}

	private double[][] rearrangeAsAnArrayForRuppert(Point2DList pointList) {
		double[][] boundaryPoints = pointList.toDoubleArray();
		double[][] pointsForRuppert = new double[1][boundaryPoints.length * 2];
		for (int i = 0; i < boundaryPoints.length; i++) {
			pointsForRuppert[0][i * 2] = boundaryPoints[i][0];
			pointsForRuppert[0][i * 2 + 1] = boundaryPoints[i][1];
		}
		return pointsForRuppert;
	}

	private double[][] rearrangeAsTwoDimensionalArray(double[] points) {
		double[][] newPoints = new double[points.length / 2][2];
		for (int i = 0; i < points.length; i = i + 2) {
			newPoints[i / 2][0] = points[i];
			newPoints[i / 2][1] = points[i + 1];
		}
		return newPoints;
	}
}
