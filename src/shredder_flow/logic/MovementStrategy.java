package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class MovementStrategy {

	private TriangleList triangles;
	private final double EPS = 0.00001;
	private int edgeJumpsInThisUpdate;

	public MovementStrategy(TriangleList triangles) {
		this.triangles = triangles;
	}

	public void setNextPositionAndTriangle(double deltaT, Particle particle) {
		edgeJumpsInThisUpdate = 100;
		setNextPositionAndTriangleLogic(deltaT, particle);
	}

	private void setNextPositionAndTriangleLogic(double deltaT,
			Particle particle) {
		// TODO think of good name
		Point2d pos = particle.getPositionAsPoint2d();
		Triangle triangle = particle.getTriangle();
		Vector2d vec = triangle.getFieldVector();

		double newp1 = pos.x + deltaT * vec.x;
		double newp2 = pos.y + deltaT * vec.y;
		if (triangle.isInTriangle(newp1, newp2)) {
			particle.setPosition(newp1, newp2);
		} else {
			Point2d intersection = getIntersectionWithBoundary(pos, vec,
					triangle);
			double timeMoved = getTimeMovedInCurrentTriangle(pos, vec,
					intersection);
			particle.setPosition(intersection.x, intersection.y);
			Triangle newTriangle = getNextTriangleHeuristic(intersection,
					triangle.getFieldVector());
			if (newTriangle != null) {
				if (edgeJumpsInThisUpdate < 0) {
					particle.setMovement(false);
				} else {
					edgeJumpsInThisUpdate--;
					particle.setTriangle(newTriangle);
					setNextPositionAndTriangleLogic(deltaT - timeMoved, particle);
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
		double y = p.y + Math.signum(dir.y) * EPS;
		double x = p.x + Math.signum(dir.x) * EPS;
		Triangle triangle = triangles.getTriangle(x, y);
		return triangle;
	}

	private Point2d getIntersectionWithBoundary(Point2d position,
			Vector2d vector, Triangle triangle) {
		Point2d positionPlusEpsilon = new Point2d(position.x + vector.x,
				position.y + vector.y);
		double minCoefficient = Double.MAX_VALUE;
		Point2d intersection = null;

		for (int i = 0; i < triangle.getVertices().size(); i++) {
			Point2d vertexA = triangle.getVertices()
					.get(i % triangle.getVertices().size()).getPosition();
			Point2d vertexB = triangle.getVertices()
					.get((i + 1) % triangle.getVertices().size()).getPosition();
			Point2d intersectionWithEdge = intersectToLines(vertexA, vertexB,
					position, positionPlusEpsilon);

			double coefficient = getCoefficient(position, vector,
					intersectionWithEdge);

			if (coefficient >= 0 && coefficient < minCoefficient) {
				minCoefficient = coefficient;
				intersection = intersectionWithEdge;
			}
		}
		return intersection;
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
}
