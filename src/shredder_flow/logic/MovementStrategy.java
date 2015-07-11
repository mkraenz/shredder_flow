package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class MovementStrategy {

	private TriangleList triangles;
	private final double EPS = 0.00001;
	private int triangleJumpsInThisUpdate;

	public MovementStrategy(TriangleList triangles) {
		this.triangles = triangles;
	}

	public void setNextPositionAndTriangle(double timeToMove, Particle particle) {
		triangleJumpsInThisUpdate = 100;
		performStepInsideOneTriangle(timeToMove, particle);
	}

	/**
	 * Recursive method
	 * 
	 * @param timeLeft
	 * @param particle
	 */
	private void performStepInsideOneTriangle(double timeLeft, Particle particle) {
		Point2d pos = particle.getPositionAsPoint2d();
		Triangle triangle = particle.getTriangle();
		Vector2d vec = triangle.getFieldVector();
		double newPosX = pos.x + timeLeft * vec.x;
		double newPosY = pos.y + timeLeft * vec.y;
		if (triangle.isInTriangle(newPosX, newPosY)) {
			particle.setPosition(newPosX, newPosY);
		} else {
			performTriangleJump(timeLeft, particle, pos, triangle, vec);
		}
	}

	private void performTriangleJump(double deltaT, Particle particle,
			Point2d pos, Triangle triangle, Vector2d vec) {
		Point2d intersection = getIntersectionWithBoundary(pos, vec, triangle);
		if (intersection == null) {
			// TODO: this should never be null. We are dealing with triangles
			// and every line through a point inside that triangle must
			// intersect the triangle in positive vector direction.
			// Unfortunately, currently it does become null sometimes.
			particle.setReceivesUpdates(false);
			return;
		}
		double timeUntilEdgeHit = getTimeMovedInCurrentTriangle(pos, vec,
				intersection);
		particle.setPosition(intersection.x, intersection.y);
		Triangle newTriangle = getNextTriangleHeuristic(intersection,
				triangle.getFieldVector());
		if (newTriangle != null) {
			initNextMoveInNewTriangleOrStopUpdates(particle, newTriangle,
					deltaT - timeUntilEdgeHit);
		} else {
			particle.setReceivesUpdates(false);
		}
	}

	private void initNextMoveInNewTriangleOrStopUpdates(Particle particle,
			Triangle newTriangle, double timeLeft) {
		if (triangleJumpsInThisUpdate < 0) {
			particle.setReceivesUpdates(false);
		} else {
			triangleJumpsInThisUpdate--;
			particle.setTriangle(newTriangle);
			performStepInsideOneTriangle(timeLeft, particle);
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
		Vector2d targetMinusBase = new Vector2d(targetPosition.x - base.x,
				targetPosition.y - base.y);
		return targetMinusBase.dot(vec);
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
