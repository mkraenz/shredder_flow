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

	public void generateRandomVectorField() {
		int highestRandomValue = 100;
		double scale = 0.000001;
		Random random = new Random();
		for (Triangle triangle : triangles) {
			FieldVector vector = triangle.getFieldVector();
			vector.set(random.nextInt(highestRandomValue)*scale,random.nextInt(highestRandomValue)*scale);
		}
	}
	
	public void generateWhirlpool(Vector2d i, Vector2d j) {
		for (Triangle triangle : triangles) {
			FieldVector vector = triangle.getFieldVector();
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
		}
	}
	
}
