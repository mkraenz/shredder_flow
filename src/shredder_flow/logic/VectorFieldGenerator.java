package shredder_flow.logic;

import java.util.Random;

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
	public boolean genTangentialFlowAtBoundary(){
		return false;
	}
	public void setTangentialFlowAtBoundary(){
		for(Triangle triangle : triangles){
			if(triangle.isBoundaryTriangle()==true){
				
			}
		}
	}
}
