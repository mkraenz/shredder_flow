package shredder_flow.logic;

import java.util.Random;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class VectorFieldGenerator {

	private TriangleList triangles;

	public VectorFieldGenerator(TriangleList triangleList) {
		this.triangles = triangleList;
	}

	public void generateRandomVectorField() {
		int highestRandomValue = 100;
		double scale = 0.000001;
		Random random = new Random();
		for (Triangle triangle : triangles) {
			FieldVector vector = triangle.getFieldVector();
			vector.set(
					random.nextInt(highestRandomValue) * scale
							* Math.pow(-1, random.nextInt(highestRandomValue)),
					random.nextInt(highestRandomValue) * scale
							* Math.pow(-1, random.nextInt(highestRandomValue)));
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

	/**
	 * Builds gradient with method from
	 * http://dgd.service.tu-berlin.de/wordpress/vismathws10
	 * /2012/10/17/gradient-of-scalar-functions/.
	 * 
	 * @param triangle
	 */
	private Vector2d getGradient(Triangle triangle) {
		Point2d v1 = triangle.getVertices().get(0).getPosition();
		double x1 = v1.x;
		double y1 = v1.y;
		Point2d v2 = triangle.getVertices().get(1).getPosition();
		double x2 = v2.x;
		double y2 = v2.y;
		Point2d v3 = triangle.getVertices().get(2).getPosition();
		double x3 = v3.x;
		double y3 = v3.y;
		double f1 = triangle.getVertices().get(0).getFunctionValue();
		double f2 = triangle.getVertices().get(1).getFunctionValue();
		double f3 = triangle.getVertices().get(2).getFunctionValue();
		double gradY = (f1 - f3) * (x3 - x2);
		gradY = gradY / ((y1 - y3) * (x3 - x2) - (x1 - x3) * (y3 - y2));
		double gradX = (f3 - f2 - gradY * (y3 - y2)) / (x3 - x2);
		return new Vector2d(gradX, gradY);
	}
}
