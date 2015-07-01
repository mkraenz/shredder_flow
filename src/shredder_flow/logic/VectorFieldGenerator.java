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
			vector.set(random.nextInt(highestRandomValue)*scale, random.nextInt(highestRandomValue)*scale);
		}
	}
	public void generateGradiantField(){
		for (Triangle triangle : triangles) {
			Vector2d vec = getGradient(triangle);
			FieldVector triangleVec = triangle.getFieldVector();
			triangleVec.set(vec.x, vec.y);
		}
	}

	protected Vector2d getGradient(Triangle triangle) {
		VertexList vertices = triangle.getVertices();
		//Vector2d grad = null;
		Vector2d grad = new Vector2d(1,1);
		double lengthAB = Math.sqrt(Math.pow(vertices.get(1).getX()
				- vertices.get(0).getX(), 2)
				+ Math.pow(vertices.get(1).getY() - vertices.get(0).getY(), 2));
		double lengthAC = Math.sqrt(Math.pow(vertices.get(2).getX()
				- vertices.get(0).getX(), 2)
				+ Math.pow(vertices.get(2).getY() - vertices.get(0).getY(), 2));
		grad.x = (-vertices.get(1).getFunctionValue()
				+ vertices.get(0).getFunctionValue()) / lengthAB;
		grad.y = (-vertices.get(2).getFunctionValue()
				+ vertices.get(0).getFunctionValue()) / lengthAC;
		return grad;
	}
	
	public Vector2d rotateGradient(Triangle triangle){
		Vector2d vec = triangle.getFieldVector();
		double temp=vec.x;
		vec.x=-vec.y;
		vec.y=temp;
		return vec;
	}
}
