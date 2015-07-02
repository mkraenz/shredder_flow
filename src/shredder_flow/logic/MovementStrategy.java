package shredder_flow.logic;

import javax.vecmath.Vector2d;

public class MovementStrategy {

	private TriangleList triangles;

	public MovementStrategy(TriangleList triangles) {
		this.triangles = triangles;
	}

	public void setNextPositionAndTriangle(double deltaT, Particle particle) {
		// TODO Auto-generated method stub
		int i;
		int counter = 0;
		double lambda = 0;
		double tau = 0;
		double eps = 0.001;
		double distance = 0;
		double triangletime = 0;
		double[] intersectionpoint = new double[2];
		double[] posarray;
		double[] vecarray;
		double[] a;
		double[] b;
		double velocity;
		boolean intersection;
		boolean overflow = false;
		Vector2d position;
		Vertex vertex1;
		Vertex vertex2;
		Triangle tri;
		VertexList vlist;
		FieldVector vec;

		double time = deltaT;
		while ((time > 0) && (overflow == false)) {
			position = particle.getPosition();
			posarray = getPositionArray(position);
			tri = particle.getTriangle();
			if (tri.getVertices() == null) {
				System.out.print("on boundary");
				particle.setMovement(false);
				overflow = true;
			} else {
				vlist = tri.getVertices();
				vertex1 = vlist.get(0);
				vertex2 = vlist.get(1);
				vec = tri.getFieldVector();
				vecarray = getFieldVectorArray(vec);
				velocity = vec.length();
				intersection = false;
				i = 0;
				System.out.println(vecarray[0]);
				System.out.println(vecarray[1]);

				while ((intersection == false) && (i < 3)) {
					vertex1 = vlist.get(i);
					vertex2 = vlist.get((i + 1) % 3);
					a = getVertexLocArray(vertex1);
					b = getVertexLocArray(vertex2);
					lambda = getIntersection(a, b, posarray, vecarray);
					tau = getTau(a[0], b[0], posarray[0], vecarray[0], lambda);
					if ((lambda <= 1) && (lambda >= 0) && tau > 0) {
						intersectionpoint = getIntersectionPoint(a, b, lambda);
						distance = getDistance(posarray, intersectionpoint);
						intersection = true;
						triangletime = distance / velocity;
					} else {
						i++;
					}
				}
				if (intersection == false) {
					particle.setMovement(false);
					System.out.println("overfolw");
					System.out.println(vecarray[0]);
					System.out.println(vecarray[1]);
					overflow = true;
				} else {
					if (triangletime >= time) {
						particle.setPosition(posarray[0] + vecarray[0] * time,
								posarray[1] + vecarray[1] * time);
						time = 0;
					} else if (triangletime < time) {
						if (tri.isVertex(intersectionpoint[0],
								intersectionpoint[1]) == true) {
							particle.setPosition(intersectionpoint[0]
									+ vecarray[0] * eps, intersectionpoint[1]
									+ vecarray[0] * eps);
							particle.setTriangle(getNextTriangle(
									intersectionpoint, vecarray, eps));
							System.out.println("Intersectionpoint is vertex");
						} else {
							particle.setPosition(intersectionpoint[0],
									intersectionpoint[1]);
							particle.setTriangle(getNextTriangle(
									intersectionpoint, vecarray, eps));
						}

						counter++;
						if (counter >= 200) {
							particle.setMovement(false);
							System.out.println("Counter exceeded");
							overflow = true;
						}
						time = time - triangletime;
					}
				}
			}

		}

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
		return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1])
				* (a[1] - b[1]));
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

	private double getTau(double a, double b, double p, double v, double lambda) {
		return (a - lambda * (b - a) - p) / v;
	}

	private double[] getIntersectionPoint(double[] a, double[] b, double lambda) {
		double[] point = new double[2];
		point[0] = a[0] + lambda * (b[0] - a[0]);
		point[1] = a[1] + lambda * (b[1] - a[1]);
		return point;
	}

	private double[] getVertexLocArray(Vertex ver) {
		double[] locarray = new double[2];
		locarray[0] = ver.getX();
		locarray[1] = ver.getY();
		return locarray;
	}
}
