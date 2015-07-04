package shredder_flow.logic;

import java.util.Random;

import javax.vecmath.Point2d;
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

	public void generateRandomVectorField() {
		int highestRandomValue = 100;
		double scale = 0.000001;
		Random random = new Random();
		for (Triangle triangle : triangles) {
			FieldVector vector = triangle.getFieldVector();
			vector.set(random.nextInt(highestRandomValue) * scale,
					random.nextInt(highestRandomValue) * scale);
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
		Point2d v2 = triangle.getVertices().get(1).getPosition();
		Point2d v3 = triangle.getVertices().get(2).getPosition();
		double f1 = triangle.getVertices().get(0).getFunctionValue();
		double f2 = triangle.getVertices().get(1).getFunctionValue();
		double f3 = triangle.getVertices().get(2).getFunctionValue();
		double gradX = 0; // TODO write the correct formula here
		double gradY = 0;// TODO write the correct formula here
		return new Vector2d(gradX, gradY);
	}
}
