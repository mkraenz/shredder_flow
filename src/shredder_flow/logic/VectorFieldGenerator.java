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
		double scale = 0.01;
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
	
	public void generateSymplecticVectorField(){
		for (Triangle triangle : triangles) {
			Vector2d vec = getGradient(triangle);
			FieldVector triangleVec = triangle.getFieldVector();
			triangleVec.set(-vec.y, vec.x);
		}
	}

	/*
	 * Builds gradient with method from
	 * http://dgd.service.tu-berlin.de/wordpress/vismathws10
	 * /2012/10/17/gradient-of-scalar-functions/.
	 * 
	 * @param triangle
	 */
	private Vector2d getGradient(Triangle triangle) {
		VertexList vertices = triangle.getVertices();
		Vector2d grad = new Vector2d(1, 1);
		double e_ab1 = vertices.get(1).getX() - vertices.get(0).getX();
		double e_ab2 = vertices.get(1).getY() - vertices.get(0).getY();
		double e_bc1 = vertices.get(2).getX() - vertices.get(1).getX();
		double e_bc2 = vertices.get(2).getY() - vertices.get(1).getY();
		double temp = e_ab1 * e_bc2;
		grad.y = (vertices.get(2).getFunctionValue() / e_bc2
				- vertices.get(1).getFunctionValue() / e_bc2
				- vertices.get(1).getFunctionValue() * e_bc1 / temp + vertices
				.get(0).getFunctionValue() * e_bc1 / temp)
				/ (1 - (e_ab2 * e_bc1 / temp));
		grad.x = (vertices.get(1).getFunctionValue()
				- vertices.get(0).getFunctionValue() - grad.y * e_ab2)
				/ e_ab1;
		return grad;
	}

	public Vector2d rotateGradient(Triangle triangle) {
		Vector2d vec = triangle.getFieldVector();
		double temp = vec.x;
		vec.x = -vec.y;
		vec.y = temp;
		return vec;
	}
	
}
