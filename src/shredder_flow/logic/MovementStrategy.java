package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class MovementStrategy {

	private TriangleList triangles;
	private final double EPS = 0.00001;
	private Vertex lastCollidingEdgeVertex1;
	private Vertex lastCollidingEdgeVertex2;
	private int edgeJumpsInThisUpdate = 2000;

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
			performTriangleJump(timeLeft, particle, pos, triangle, vec);

		}
	}

	private void performTriangleJump(double deltaT, Particle particle,
			Point2d pos, Triangle triangle, Vector2d vec) {
		Point2d intersection = getIntersectionWithBoundary(pos, vec, triangle);
		if (intersection == null) {
			particle.setReceivesUpdates(false);
			return;
		}
		double timeUntilEdgeHit = getTimeMovedInCurrentTriangle(pos, vec,
				intersection);
		particle.setPosition(intersection.x, intersection.y);
		Triangle newTriangle = getNextTriangleHeuristic(intersection, vec,
				triangle);
		if (newTriangle != null
				&& triangle.isVertex(intersection.x, intersection.y) == true) {
			initNextMoveInNewTriangleOrStopUpdates(particle, newTriangle,
					deltaT - timeUntilEdgeHit);
		} else if (newTriangle != null && deltaT - timeUntilEdgeHit > 0) {
			boolean bPointsBackwards = pointsBackwards(newTriangle);
			if (bPointsBackwards == true) {
				particle.setReceivesUpdates(false);
				return;
			} else {
				initNextMoveInNewTriangleOrStopUpdates(particle, newTriangle,
						deltaT - timeUntilEdgeHit);
			}
		} else {
			particle.setReceivesUpdates(false);
			return;
		}

	}

	private boolean pointsBackwards(Triangle triangle) {
		if (lastCollidingEdgeVertex1 != null
				&& lastCollidingEdgeVertex2 != null) {
			Point2d center = triangle.getBarycenter();
			Vector2d vec = triangle.getFieldVector();
			Point2d pVertex1 = new Point2d(lastCollidingEdgeVertex1.getX(),
					lastCollidingEdgeVertex1.getY());
			Point2d pVertex2 = new Point2d(lastCollidingEdgeVertex2.getX(),
					lastCollidingEdgeVertex2.getY());
			double lambda = getLambda(center, vec, pVertex1, pVertex2);
			double t = getT(pVertex1, pVertex2, center, vec, lambda);
			if (t > 0) {
				return true;
			}
		}
		return false;
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
		double deltaX = base.x - intersection.x;
		double deltaY = base.y - intersection.y;
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return distance / vec.length();
	}

	/**
	 * Method assumes that the targetPosition lies on the line spanned by the
	 * vector based at given base.
	 * 
	 * @return coefficient lambda of base + lambda * vec = target
	 */

	private Triangle getNextTriangleHeuristic(Point2d p, Vector2d dir,
			Triangle tri) {
		if (tri.isVertex(p.x, p.y)) {
			double y = p.y + Math.signum(dir.y) * EPS;
			double x = p.x + Math.signum(dir.x) * EPS;
			Triangle triangle = triangles.getTriangle(x, y);
			return triangle;
		} else if (lastCollidingEdgeVertex1 != null
				&& lastCollidingEdgeVertex2 != null) {
			Triangle triangle = tri.getNeighborWithVertices(
					lastCollidingEdgeVertex1, lastCollidingEdgeVertex2);
			return triangle;
		}
		return null;
	}

	private Point2d getIntersectionWithBoundary(Point2d position,
			Vector2d vector, Triangle triangle) {
		Point2d intersection = new Point2d();
		double lambda;
		double t;
		Vertex newColl1 = null;
		Vertex newColl2 = null;
		Point2d pVertex1 = new Point2d();
		Point2d pVertex2 = new Point2d();
		int i = 0;
		boolean bIntersection = false;
		while (i < triangle.getVertices().size() && bIntersection == false) {
			Vertex vertexA = triangle.getVertices().get(
					i % triangle.getVertices().size());
			pVertex1.set(vertexA.getX(), vertexA.getY());

			Vertex vertexB = triangle.getVertices().get(
					(i + 1) % triangle.getVertices().size());
			pVertex2.set(vertexB.getX(), vertexB.getY());

			lambda = getLambda(position, vector, pVertex1, pVertex2);
			t = getT(pVertex1, pVertex2, position, vector, lambda);
			if (lambda >= 0 && lambda <= 1 && t > 0) {
				bIntersection = true;
				intersection = getIntersection(pVertex1, pVertex2, lambda);
				newColl1 = vertexA;
				newColl2 = vertexB;
			}
			i++;
		}

		if (bIntersection == false) {
			return null;
		}
		lastCollidingEdgeVertex1 = newColl1;
		lastCollidingEdgeVertex2 = newColl2;
		return intersection;
	}

	/**
	 * See https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
	 * 
	 * @return intersection point of the two lines, spanned by given points
	 */
	private double getLambda(Point2d position, Vector2d vec, Point2d vertex1,
			Point2d vertex2) {
		double p0 = position.x;
		double p1 = position.y;
		double v0 = vec.x;
		double v1 = vec.y;
		double a0 = vertex1.x;
		double a1 = vertex1.y;
		double b0 = vertex2.x;
		double b1 = vertex2.y;

		double zaehler = a1 * v0 - a0 * v1 - p1 * v0 + p0 * v1;
		double nenner = v1 * (b0 - a0) - v0 * (b1 - a1);
		return zaehler / nenner;
	}

	private Point2d getIntersection(Point2d vertex1, Point2d vertex2,
			double lambda) {
		double a0 = vertex1.x;
		double a1 = vertex1.y;
		double b0 = vertex2.x;
		double b1 = vertex2.y;
		double x = a0 + lambda * (b0 - a0);
		double y = a1 + lambda * (b1 - a1);
		Point2d intersection = new Point2d(x, y);
		return intersection;
	}

	private double getT(Point2d a, Point2d b, Point2d p, Vector2d v,
			double lambda) {
		if (v.x != 0) {
			double zaehler = a.x + lambda * (b.x - a.x) - p.x;
			return zaehler / v.x;
		} else {
			double zaehler = a.y + lambda * (b.y - a.y) - p.y;
			return zaehler / v.y;
		}

	}

	/*
	 * private boolean pointsBackwards(Particle particle) { if
	 * (lastCollidingEdgeVertex1 != null && lastCollidingEdgeVertex2 != null) {
	 * Triangle triangle = particle.getTriangle(); Vector2d vec =
	 * triangle.getFieldVector(); Point2d point1OnFieldVectorLine =
	 * triangle.getBarycenter(); Point2d point2OnFieldVectorLine = new Point2d(
	 * point1OnFieldVectorLine.x + vec.x, point1OnFieldVectorLine.y + vec.y);
	 * Point2d point1OnLine1 = new Point2d( lastCollidingEdgeVertex1.getX(),
	 * lastCollidingEdgeVertex1.getY()); Point2d point2OnLine1 = new Point2d(
	 * lastCollidingEdgeVertex2.getX(), lastCollidingEdgeVertex2.getY());
	 * Point2d intersectionPoint = intersectToLines( point1OnFieldVectorLine,
	 * point2OnFieldVectorLine, point1OnLine1, point2OnLine1); if (coeff > 0) {
	 * return true; } else { return false; } } else { return false;
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * private Point2d intersectToLines(Point2d point1OnLine1, Point2d
	 * point2OnLine1, Point2d point1OnLine2, Point2d point2OnLine2) { double x1
	 * = point1OnLine1.x; double x2 = point2OnLine1.x; double x3 =
	 * point1OnLine2.x; double x4 = point2OnLine2.x;
	 * 
	 * double y1 = point1OnLine1.y; double y2 = point2OnLine1.y; double y3 =
	 * point1OnLine2.y; double y4 = point2OnLine2.y;
	 * 
	 * double pxNumerator = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) (x3 * y4
	 * - y3 * x4); double pyNumerator = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 -
	 * y2) (x3 * y4 - y3 * x4); double denominator = (x1 - x2) * (y3 - y4) - (y1
	 * - y2) * (x3 - x4); return new Point2d(pxNumerator / denominator,
	 * pyNumerator / denominator); }
	 */

}
