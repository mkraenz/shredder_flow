package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class MovementStrategy {

	private static final double MINIMAL_TIME_MOVED = 0.0000001;
	private TriangleList triangles;
	private final double EPS = 0.00001;
	private int leftEdgeJumps = 200;

	public MovementStrategy(TriangleList triangles) {
		this.triangles = triangles;
	}

	public void setNextPositionAndTriangle(double deltaT, Particle particle) {
		Point2d pos = particle.getPositionAsPoint2d();
		Triangle triangle = particle.getTriangle();
		Vector2d vec = triangle.getFieldVector();

		double newp1 = pos.x + deltaT * vec.x;
		double newp2 = pos.y + deltaT * vec.x;
		if (triangle.isInTriangle(newp1, newp2)) {
			particle.setPosition(newp1, newp2);
		} else {
			Point2d intersection = getIntersectionWithBoundary(pos, vec,
					triangle);
			particle.setPosition(intersection.x, intersection.y);
			double timeMoved = getTimeMovedInCurrentTriangle(pos, vec,
					intersection);
			Triangle newTriangle = getNextTriangleHeuristic(intersection,
					triangle.getFieldVector());
			if (newTriangle != null) {
				if (timeMoved < MINIMAL_TIME_MOVED) {
					// instead of using a counter for edge jumps
					particle.setMovement(false);
				} else {
					particle.setTriangle(newTriangle);
					setNextPositionAndTriangle(deltaT - timeMoved, particle);
				}
			} else {
				particle.setMovement(false);
			}
		}
	}

	private double getTimeMovedInCurrentTriangle(Point2d base, Vector2d vec,
			Point2d intersection) {
		return getCoefficient(base, vec, intersection);
	}

	/**
	 * Method assumes that the targetPosition lies on the line spanned by the
	 * vector based at given base.
	 * 
	 * @return coefficient lambda of base + lambda * vec = target
	 */
	private double getCoefficient(Point2d base, Vector2d vec,
			Point2d targetPosition) {
		return (targetPosition.x - base.x) / vec.x;
	}

	private Triangle getNextTriangleHeuristic(Point2d p, Vector2d dir) {
		Triangle triangle = triangles.getTriangle(p.x + Math.signum(dir.x)
				* EPS, p.y + Math.signum(dir.y) * EPS);
		return triangle;
	}

	private Point2d getIntersectionWithBoundary(Point2d position,
			Vector2d vector, Triangle triangle) {
		Point2d vertex1 = triangle.getVertices().get(0).getPosition();
		Point2d vertex2 = triangle.getVertices().get(1).getPosition();
		Point2d vertex3 = triangle.getVertices().get(2).getPosition();
		Point2d positionPlusEpsilon = new Point2d(position.x + vector.x,
				position.y + vector.y);

		Point2d intersectionWithEdge12 = intersectToLines(vertex1, vertex2,
				position, positionPlusEpsilon);
		Point2d intersectionWithEdge21 = intersectToLines(vertex2, vertex3,
				position, positionPlusEpsilon);
		Point2d intersectionWithEdge31 = intersectToLines(vertex3, vertex1,
				position, positionPlusEpsilon);

		return new Point2d(0, 0);
	}

	/**
	 * See https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
	 * 
	 * @return intersection point of the two lines, spanned by given points
	 */
	private Point2d intersectToLines(Point2d point1OnLine1,
			Point2d point2OnLine1, Point2d point1OnLine2, Point2d point2OnLine2) {
		double x1 = point1OnLine1.x;
		double x2 = point2OnLine1.x;
		double x3 = point1OnLine2.x;
		double x4 = point2OnLine2.x;

		double y1 = point1OnLine1.y;
		double y2 = point2OnLine1.y;
		double y3 = point1OnLine2.y;
		double y4 = point2OnLine2.y;

		double pxNumerator = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2)
				* (x3 * y4 - y3 * x4);
		double pyNumerator = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2)
				* (x3 * y4 - y3 * x4);
		double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		return new Point2d(pxNumerator / denominator, pyNumerator / denominator);
	}

	private Triangle getNextTriangle(double[] intersectionpoint,
			double[] vecarray, double eps) {
		return triangles.getTriangle(intersectionpoint[0] + eps * vecarray[0],
				intersectionpoint[1] + eps * vecarray[1]);
	}

	private double[] getFieldVectorArray(FieldVector vec) {
		double[] vecarray = { vec.x, vec.y };
		return vecarray;
	}

	private double getDistance(double[] a, double[] b) {
		return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
	}

	private double[] getPositionArray(Vector2d position) {
		double[] pos = { position.x, position.y };
		return pos;
	}

	private double getIntersection(double[] a, double[] b, double[] p,
			double[] v) {
		return (a[0] * v[1] - a[1] * v[0] - p[0] * v[1] + p[1] * v[0])
				/ (v[0] * (b[1] - a[1]) - v[1] * (b[0] - a[0]));
	}

	private double[] getIntersectionPoint(double[] a, double[] b, double lambda) {
		double[] point = new double[2];
		point[0] = a[0] + lambda * (b[0] - a[0]);
		point[1] = a[1] + lambda * (b[1] - a[1]);
		return point;
	}

	private double[] getVertexLocArray(VertexList vertices, int index) {
		double[] locarray = new double[2];
		Vertex ver = vertices.get(index);
		locarray[0] = ver.getX();
		locarray[1] = ver.getY();
		return locarray;
	}
}
