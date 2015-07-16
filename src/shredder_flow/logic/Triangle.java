package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class Triangle {

	private TriangulationVertexList vertices;
	private TriangleList neighbors;

	public TriangleList getNeighbors() {
		return neighbors;
	}

	private FieldVector fieldVector;

	public void setNeighbors(TriangleList neighbors) {
		this.neighbors = neighbors;
	}

	public boolean isNeighbor(Triangle triangle) {
		if (vertices.hasTwoOrMoreEqualVertices(triangle.getVertices())) {
			return true;
		} else {
			return false;
		}
	}

	public Triangle(TriangulationVertexList vertices) {
		this.vertices = vertices;
		this.fieldVector = new FieldVector(0, 0);
	}

	/**
	 * Gives the neighboring triangle closest to this position. If there are
	 * more than one, then returns null.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Triangle getNeighbor(double x, double y) {
		double dist1 = getDistanceToEdge(x, y, 0, 1);
		double dist2 = getDistanceToEdge(x, y, 1, 2);
		double dist3 = getDistanceToEdge(x, y, 2, 0);
		if (dist1 < dist2 && dist1 < dist3) {
			return getNeighborWithVertices(vertices.get(0), vertices.get(1));
		} else {
			if (dist2 < dist1 && dist2 < dist3) {
				return getNeighborWithVertices(vertices.get(1), vertices.get(2));
			} else {
				if (dist3 < dist1 && dist3 < dist2) {
					return getNeighborWithVertices(vertices.get(2),
							vertices.get(0));
				} else {
					return null;
				}
			}
		}
	}

	public Triangle getNeighborWithVertices(Vertex vertex1, Vertex vertex2) {
		for (Triangle triangle : neighbors) {
			if (triangle.getVertices().contains(vertex1)
					&& triangle.getVertices().contains(vertex2)) {
				return triangle;
			}
		}
		return null;
	}

	protected double getDistanceToEdge(double x, double y, int vertexIndex1,
			int vertexIndex2) {
		double x1 = vertices.get(vertexIndex1).getX();
		double y1 = vertices.get(vertexIndex1).getY();
		double x2 = vertices.get(vertexIndex2).getX();
		double y2 = vertices.get(vertexIndex2).getY();
		double nominator = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1
				- y2 * x1);
		double denominator = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1)
				* (x2 - x1));
		return nominator / denominator;
	}

	/**
	 * Gives the neighboring triangle closest to this position. If there are
	 * more than one, then returns the triangle that lies in the given direction
	 * based at given position.
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @return
	 */
	public Triangle getNeighbor(double x, double y, Vector2d direction) {
		Triangle neighbor = getNeighbor(x, y);
		if (neighbor != null) {
			return neighbor;
		} else {
			return getNeighborInDirection(x, y, direction);
		}
	}

	private Triangle getNeighborInDirection(double x, double y,
			Vector2d direction) {
		// TODO Auto-generated method stub
		return null;

	}

	public VertexList getVertices() {
		return this.vertices;
	}

	public FieldVector getFieldVector() {
		return this.fieldVector;
	}

	public boolean isVertex(double x, double y) {
		for (Vertex vertex : vertices) {
			if (vertex.isPositionEqual(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the position (x,y) lies within or on the boundary of this
	 * triangle.
	 * 
	 * @param x
	 * @param y
	 * @return true, if lies within or on the boundary, else false.
	 */
	public boolean isInTriangle(double x, double y) {
		// We use Barycentric coordinate system. See wikipedia
		// for reference.
		double x1 = vertices.get(0).getX();
		double y1 = vertices.get(0).getY();
		double x2 = vertices.get(1).getX();
		double y2 = vertices.get(1).getY();
		double x3 = vertices.get(2).getX();
		double y3 = vertices.get(2).getY();
		double detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
		double alpha = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / detT;
		double beta = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / detT;
		double gamma = 1.0 - alpha - beta;

		if (alpha >= 0 && beta >= 0 && gamma >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public Point2d getBarycenter() {
		double x1 = vertices.get(0).getX();
		double y1 = vertices.get(0).getY();
		double x2 = vertices.get(1).getX();
		double y2 = vertices.get(1).getY();
		double x3 = vertices.get(2).getX();
		double y3 = vertices.get(2).getY();
		double x = (x1 + x2 + x3) / 3;
		double y = (y1 + y2 + y3) / 3;
		Point2d vert = new Point2d(x, y);
		return vert;
	}

	public double getArea() {
		double x1 = vertices.get(0).getX();
		double y1 = vertices.get(0).getY();
		double x2 = vertices.get(1).getX();
		double y2 = vertices.get(1).getY();
		double x3 = vertices.get(2).getX();
		double y3 = vertices.get(2).getY();

		double area = (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2;
		return area;
	}

	public Vector2d getMiddlePoint() {
		Vector2d middle = new Vector2d(1, 1);
		middle.set((this.getVertices().get(0).getX()
				+ this.getVertices().get(1).getX() + this.getVertices().get(2)
				.getX()) / 3, (this.getVertices().get(0).getY()
				+ this.getVertices().get(1).getY() + this.getVertices().get(2)
				.getY()) / 3);
		return middle;
	}

	@Override
	public String toString() {
		return "Triangle [vertices=" + vertices + "]";
	}
}
