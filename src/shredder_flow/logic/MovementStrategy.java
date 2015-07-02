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
		double p1 = particle.getX();
		double p2 = particle.getY();
		Triangle triangle = particle.getTriangle();
		double x1 = triangle.getFieldVector().getX();
		double x2 = triangle.getFieldVector().getY();
		
		double newp1 = p1 + deltaT * x1;
		double newp2 = p2 + deltaT * x2;
		if (triangle.isInTriangle(newp1, newp2)) {
			particle.setPosition(newp1, newp2);
		} else {
			Point2d intersection = getIntersectionWithBoundary(p1, p2, x1, x2,
					triangle);
			particle.setPosition(intersection.x, intersection.y);
			double timeMoved = getTimeMovedInCurrentTriangle(p1, p2, x1, x2,
					intersection);
			Triangle newTriangle = getNextTriangleHeuristic(intersection,
					triangle.getFieldVector());
			if (newTriangle != null) {
				if(timeMoved < MINIMAL_TIME_MOVED){
					// instead of using a counter for edge jumps
					particle.setMovement(false);
				}
				particle.setTriangle(newTriangle);
				setNextPositionAndTriangle(deltaT - timeMoved, particle);
			} else {
				particle.setMovement(false);
			}
		}
	}

	private double getTimeMovedInCurrentTriangle(double posX, double posY,
			double vecX, double vecY, Point2d intersection) {
		return (intersection.x - posX) / vecX;
	}

	private Triangle getNextTriangleHeuristic(Point2d p, Vector2d dir) {
		Triangle triangle = triangles.getTriangle(p.x + Math.signum(dir.x)
				* EPS, p.y + Math.signum(dir.y) * EPS);
		return triangle;
	}

	private Point2d getIntersectionWithBoundary(double posX, double posY,
			double vecX, double vecY, Triangle triangle) {
		// TODO Auto-generated method stub
		return new Point2d();
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
