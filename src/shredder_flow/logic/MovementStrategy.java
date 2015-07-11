package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class MovementStrategy {

	private TriangleList triangles;
	private final double EPS = 0.00001;
	private Vertex lastCollidingEdgeVertex1;
	private Vertex lastCollidingEdgeVertex2;
	private int edgeJumpsInThisUpdate = 100;

	public MovementStrategy(TriangleList triangles) {
		this.triangles = triangles;
	}

	public void setNextPositionAndTriangle(double timeToMove, Particle particle) {
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
			if (pointsBackwards(particle) == true) {
				particle.setReceivesUpdates(false);
			} else {
				performTriangleJump(timeLeft, particle, pos, triangle, vec);
			}

		}
	}

	private boolean pointsBackwards(Particle particle) {
		if (lastCollidingEdgeVertex1 != null
				&& lastCollidingEdgeVertex2 != null) {
			Triangle triangle = particle.getTriangle();
			Vector2d vec = triangle.getFieldVector();
			Point2d point1OnFieldVectorLine = triangle.getBarycenter();
			Point2d point2OnFieldVectorLine = new Point2d(
					point1OnFieldVectorLine.x + vec.x,
					point1OnFieldVectorLine.y + vec.y);
			Point2d point1OnLine1 = new Point2d(
					lastCollidingEdgeVertex1.getX(),
					lastCollidingEdgeVertex1.getY());
			Point2d point2OnLine1 = new Point2d(
					lastCollidingEdgeVertex2.getX(),
					lastCollidingEdgeVertex2.getY());
			Point2d intersectionPoint = intersectToLines(
					point1OnFieldVectorLine, point2OnFieldVectorLine,
					point1OnLine1, point2OnLine1);
			double coeff = getCoefficient(point1OnFieldVectorLine, vec,
					intersectionPoint);
			if (coeff > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;

		}

	}

	private void performTriangleJump(double deltaT, Particle particle,
			Point2d pos, Triangle triangle, Vector2d vec) {
		Point2d intersection = getIntersectionWithBoundary(pos, vec, triangle);
		if (intersection == null) {
			particle.setReceivesUpdates(false);
			return;
		}
		if (particle.getTriangle().isInTriangle(intersection.x, intersection.y)) {
			// TODO is this a numerical error, i.e. is the intersection close to
			// the boundary or is it far away?
			System.out.println("Intersection not in triangle");
		}

		double timeUntilEdgeHit = getTimeMovedInCurrentTriangle(pos, vec,
				intersection);
		particle.setPosition(intersection.x, intersection.y);
		Triangle newTriangle = getNextTriangleHeuristic(intersection,
				triangle.getFieldVector());
		if (newTriangle != null && deltaT - timeUntilEdgeHit > 0) {
			initNextMoveInNewTriangleOrStopUpdates(particle, newTriangle,
					deltaT - timeUntilEdgeHit);
		} else {
			particle.setReceivesUpdates(false);
		}
	}

	private void initNextMoveInNewTriangleOrStopUpdates(Particle particle,
			Triangle newTriangle, double timeLeft) {
		if (edgeJumpsInThisUpdate < 0) {
			particle.setReceivesUpdates(false);
		} else {
			edgeJumpsInThisUpdate--;
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
		return Math.signum(targetMinusBase.dot(vec)) * targetMinusBase.length()
				/ vec.length();
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
		Vertex currentCollidingEdgeVertex1 = null;
		Vertex currentCollidingEdgeVertex2 = null;

		for (int i = 0; i < triangle.getVertices().size(); i++) {
			Vertex vertexA = triangle.getVertices().get(
					i % triangle.getVertices().size());
			Vertex vertexB = triangle.getVertices().get(
					(i + 1) % triangle.getVertices().size());
			if (vertexA.equals(lastCollidingEdgeVertex1)
					&& vertexB.equals(lastCollidingEdgeVertex2)
					|| vertexA.equals(lastCollidingEdgeVertex2)
					&& vertexB.equals(lastCollidingEdgeVertex1)) {
				continue;
			}
			Point2d intersectionWithEdge = intersectToLines(
					vertexA.getPosition(), vertexB.getPosition(), position,
					positionPlusEpsilon);

			double coefficient = getCoefficient(position, vector,
					intersectionWithEdge);

			if (coefficient >= 0 && coefficient < minCoefficient) {
				currentCollidingEdgeVertex1 = vertexA;
				currentCollidingEdgeVertex2 = vertexB;
				minCoefficient = coefficient;
				intersection = intersectionWithEdge;
			}
		}
		lastCollidingEdgeVertex1 = currentCollidingEdgeVertex1;
		lastCollidingEdgeVertex2 = currentCollidingEdgeVertex2;
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
