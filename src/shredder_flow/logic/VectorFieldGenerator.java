package shredder_flow.logic;

import java.util.Random;

import javax.vecmath.Vector2d;

public class VectorFieldGenerator {

	private TriangleList triangles;

	public VectorFieldGenerator(TriangleList triangleList) {
		this.triangles = triangleList;
	}

	public void generate() {
		// TODO: implement
		// The task is to provide different generate()
		// methods that can be called from the VectorFieldInvoker, e.g.
		// generateRandomField(), generateAsGradientOfPiecewiseLinearFunction(),
		// generateAs90DegreeRotationOfGradientFieldOfPiecewiseLinearFunction()
		// ...
	}

	public void generateRandomVectorField(double rangeMin, double rangeMax, double scale) {
		Random random = new Random();
		for (Triangle triangle : triangles) {
			FieldVector vector = triangle.getFieldVector();
			vector.set(rangeMin + (rangeMax - rangeMin) * random.nextDouble(),
					rangeMin + (rangeMax - rangeMin) * random.nextDouble());
			vector.scale(scale);
		}
	}

	public void generateGradiantField() {
		for (Triangle triangle : triangles) {
			Vector2d vec = getGradient(triangle);
			FieldVector triangleVec = triangle.getFieldVector();
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateSymplecticVectorField() {
		for (Triangle triangle : triangles) {
			Vector2d vec = getGradient(triangle);
			FieldVector triangleVec = triangle.getFieldVector();
			triangleVec.set(-vec.y, vec.x);
		}
	}

	public void generateAVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = -triangle.getMiddlePoint().y * i.x
					+ triangle.getMiddlePoint().x * j.x;
			vec.y = -triangle.getMiddlePoint().y * i.y
					+ triangle.getMiddlePoint().x * j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateBVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = -triangle.getMiddlePoint().y * i.x
					- triangle.getMiddlePoint().x * j.x;
			vec.y = -triangle.getMiddlePoint().y * i.y
					- triangle.getMiddlePoint().x * j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateCVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = triangle.getMiddlePoint().y * i.x
					- triangle.getMiddlePoint().x * j.x;
			vec.y = triangle.getMiddlePoint().y * i.y
					- triangle.getMiddlePoint().x * j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateDVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = i.x + triangle.getMiddlePoint().x * j.x;
			vec.y = i.y + triangle.getMiddlePoint().x * j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateEVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = triangle.getMiddlePoint().x * i.x
					+ triangle.getMiddlePoint().y * j.x;
			vec.y = triangle.getMiddlePoint().x * i.y
					+ triangle.getMiddlePoint().y * j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateFVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = -triangle.getMiddlePoint().x * i.x
					+ triangle.getMiddlePoint().y * j.x;
			vec.y = -triangle.getMiddlePoint().x * i.y
					+ triangle.getMiddlePoint().y * j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateGVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = i.x
					+ (triangle.getMiddlePoint().x + triangle.getMiddlePoint().y)
					* j.x;
			vec.y = i.y
					+ (triangle.getMiddlePoint().x + triangle.getMiddlePoint().y)
					* j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateHVectorField(Vector2d i) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = i.x * Math.abs(triangle.getMiddlePoint().x);
			vec.y = i.y * Math.abs(triangle.getMiddlePoint().x);
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateIVectorField(Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = j.x * Math.abs(triangle.getMiddlePoint().y);
			vec.y = j.y * Math.abs(triangle.getMiddlePoint().y);
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateJVectorField(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vec.x = -triangle.getMiddlePoint().y
					* j.x
					+ (triangle.getMiddlePoint().x + triangle.getMiddlePoint().y)
					* j.x;
			vec.y = -triangle.getMiddlePoint().y
					* j.y
					+ (triangle.getMiddlePoint().x + triangle.getMiddlePoint().y)
					* j.y;
			triangleVec.set(vec.x, vec.y);
		}
	}

	public void generateMassGravityVectorField(double M, double m) {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			double g = 6.67 * Math.pow(10, -11);
			vec.x = -M
					* m
					* g
					* triangle.getMiddlePoint().x
					/ (Math.pow(
							Math.sqrt(Math.pow(triangle.getMiddlePoint().x, 2)
									+ Math.pow(triangle.getMiddlePoint().y, 2)),
							3));
			vec.y = -M
					* m
					* g
					* triangle.getMiddlePoint().y
					/ (Math.pow(
							Math.sqrt(Math.pow(triangle.getMiddlePoint().x, 2)
									+ Math.pow(triangle.getMiddlePoint().y, 2)),
							3));
			triangleVec.set(vec.x, vec.y);
		}
	}

	private Vector2d getGradient(Triangle triangle) {
		VertexList vertices = triangle.getVertices();
		Vector2d grad = new Vector2d(1, 1);
		Vector2d e01 = new Vector2d(vertices.get(1).getX()
				- vertices.get(0).getX(), vertices.get(1).getY()
				- vertices.get(0).getY());
		Vector2d e12 = new Vector2d(vertices.get(2).getX()
				- vertices.get(1).getX(), vertices.get(2).getY()
				- vertices.get(1).getY());
		Vector2d e20 = new Vector2d(vertices.get(0).getX()
				- vertices.get(2).getX(), vertices.get(0).getY()
				- vertices.get(2).getY());
		double area = Math.abs(triangle.getArea());
		grad.x = -(vertices.get(0).getFunctionValue() * e12.y
				+ vertices.get(1).getFunctionValue() * e20.y + vertices.get(2)
				.getFunctionValue() * e01.y)
				/ (2 * area);
		grad.y = (vertices.get(0).getFunctionValue() * e12.x
				+ vertices.get(1).getFunctionValue() * e20.x + vertices.get(2)
				.getFunctionValue() * e01.x)
				/ (2 * area);
		return grad;
	}

	public Vector2d rotateGradient(Triangle triangle) {
		Vector2d vec = triangle.getFieldVector();
		double temp = vec.x;
		vec.x = -vec.y;
		vec.y = temp;
		return vec;
	}

	public void generateWhirlpool(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			Vector2d vector = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			vector.set(
					-triangle.getMiddlePoint().y
							/ (Math.sqrt(Math.pow(triangle.getMiddlePoint().x,
									2)
									* i.x
									+ Math.pow(triangle.getMiddlePoint().y, 2)))
							+ triangle.getMiddlePoint().x
							/ (Math.sqrt(Math.pow(triangle.getMiddlePoint().x,
									2)
									+ Math.pow(triangle.getMiddlePoint().y, 2)))
							* j.x,
					-triangle.getMiddlePoint().y
							/ (Math.sqrt(Math.pow(triangle.getMiddlePoint().x,
									2)
									* i.y
									+ Math.pow(triangle.getMiddlePoint().y, 2)))
							+ triangle.getMiddlePoint().x
							/ (Math.sqrt(Math.pow(triangle.getMiddlePoint().x,
									2)
									+ Math.pow(triangle.getMiddlePoint().y, 2)))
							* j.y);
			triangleVec.set(vector.x, vector.y);
		}
	}

	public void generateMagneticField() {
		for (Triangle triangle : triangles) {
			Vector2d vec = new Vector2d(1, 1);
			FieldVector triangleVec = triangle.getFieldVector();
			double temp = Math.sqrt(Math.pow(triangle.getMiddlePoint().x, 2)
					+ Math.pow(triangle.getMiddlePoint().y - 2, 2));

			vec.x = -triangle.getMiddlePoint().x
					/ (Math.pow(
							Math.sqrt(Math.pow(triangle.getMiddlePoint().x, 2)
									+ Math.pow(triangle.getMiddlePoint().y, 2)),
							2) * Math.sqrt(Math.pow(
							triangle.getMiddlePoint().x, 2)
							+ Math.pow(triangle.getMiddlePoint().y, 2)))
					+ triangle.getMiddlePoint().x / Math.pow(temp, 3);
			vec.y = -triangle.getMiddlePoint().y
					/ (Math.pow(
							Math.sqrt(Math.pow(triangle.getMiddlePoint().x, 2)
									+ Math.pow(triangle.getMiddlePoint().y, 2)),
							2) * Math.sqrt(Math.pow(
							triangle.getMiddlePoint().x, 2)
							+ Math.pow(triangle.getMiddlePoint().y, 2)))
					+ (triangle.getMiddlePoint().y - 2) / Math.pow(temp, 3);
			triangleVec.set(vec.x, vec.y);
		}

	}
}
