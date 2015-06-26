package shredder_flow.logic;

import de.jtem.numericalMethods.geometry.meshGeneration.ruppert.Ruppert;

public class RuppertAdapter {

	private TriangulationVertexList vertices;
	private TriangleList triangles;
	private int maximalTriangleNumber = -1;
	private int minimalAngleConstraint = 10;

	public RuppertAdapter(TriangulationVertexList vertexList,
			TriangleList triangleList) {
		this.vertices = vertexList;
		this.triangles = triangleList;
	}

	public void setMaximalTriangleNumber(int maximalTriangleNumber) {
		this.maximalTriangleNumber = maximalTriangleNumber;
	}

	public void setMinimalAngleConstraint(int minimalAngleConstraint) {
		this.minimalAngleConstraint = minimalAngleConstraint;
	}

	public TriangulationVertexList getVertexList() {
		return vertices;
	}

	public int getMaximalTriangleNumber() {
		return maximalTriangleNumber;
	}

	public int getMinimalAngleConstraint() {
		return minimalAngleConstraint;
	}

	public TriangleList getTriangleList() {
		return triangles;
	}

	/**
	 * Triangulate the domain within the given Boundary and store the resulting
	 * data in this class's datafields. Further constraints can (optionally) be
	 * set via setMaximalTriangleNumber(int) and setMaximalTriangleNumber(int).
	 */
	public void triangulate(Boundary boundary) {
		double[][] pointsForRuppert = rearrangeAsOneCrossNDimensionalArray(boundary);
		Ruppert ruppert = triangulateWithRuppert(pointsForRuppert);
		double[] ruppertPoints = ruppert.getPoints();
		setVertices(ruppertPoints);
		setTriangles(ruppert.getIndices());
	}

	private void setTriangles(int[] indices) {
		createTriangles(indices);
		setNeighbors();
	}

	private void setNeighbors() {
		for (int i = 0; i < triangles.size(); i++) {
			TriangleList neighbors = new TriangleList();
			for (int j = 0; j < triangles.size(); j++) {
				if (i != j) {
					if (triangles.get(i).isNeighbor(triangles.get(j))) {
						neighbors.add(triangles.get(j));
						// TODO: test if following 3 lines work before enabling
						// it 
						// if(neighbors.size() == 3){
						// break;
						// }
					}
				}
			}
		}
	}

	private void createTriangles(int[] indices) {
		for (int i = 0; i < indices.length; i += 3) {
			TriangulationVertexList triangleVertices = new TriangulationVertexList();
			triangleVertices.add(vertices.get(indices[i]));
			triangleVertices.add(vertices.get(indices[i + 1]));
			triangleVertices.add(vertices.get(indices[i + 2]));

			triangles.add(new Triangle(triangleVertices));
		}
	}

	private void setVertices(double[] points) {
		for (int i = 0; i < points.length; i += 2) {
			vertices.add(new Vertex(points[i], points[i + 1]));
		}
	}

	private Ruppert triangulateWithRuppert(double[][] vertexCoordinates) {
		Ruppert ruppert = new Ruppert(vertexCoordinates);
		ruppert.setAngleConstraint(minimalAngleConstraint);
		if (maximalTriangleNumber > 0) {
			ruppert.setMaximalNumberOfTriangles(maximalTriangleNumber);
		}
		ruppert.refine();
		return ruppert;
	}

	private double[][] rearrangeAsOneCrossNDimensionalArray(Boundary boundary) {
		double[][] a = new double[1][boundary.size() * 2];
		for (int i = 0; i < boundary.size() * 2; i += 2) {
			Vertex vertex = boundary.get(i / 2);
			a[0][i] = vertex.getX();
			a[0][i + 1] = vertex.getY();
		}
		return a;
	}
}
