package shredder_flow.logic;

import java.util.Random;

public class VectorFieldGenerator {

	private TriangleList triangles;
	private boolean isTangentialFlow;

	public VectorFieldGenerator(TriangleList triangleList) {
		this.triangles = triangleList;
		this.isTangentialFlow = false;
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
		return isTangentialFlow;
	}
	public void setIsTangentialFlow(boolean isTangentialFlow){
		this.isTangentialFlow = isTangentialFlow;
		if(this.isTangentialFlow == true){
			setTangentialFlowAtBoundary();
		}
	}
	
	public void setTangentialFlowAtBoundary(){
		for(Triangle triangle : triangles){
			if(triangle.isBoundaryTriangle()==true){
				double length = triangle.getFieldVector().length();
				double [] direction = new double[2];
				VertexList vertexList = triangle.getVertices();
				if(triangle.getNeighborWithVertices(vertexList.get(0), vertexList.get(1))==null){
					direction = getLineDirection(vertexList.get(0),vertexList.get(1));
				}
				else if(triangle.getNeighborWithVertices(vertexList.get(1), vertexList.get(2))==null){
					direction = getLineDirection(vertexList.get(1),vertexList.get(2));
				}
				else if(triangle.getNeighborWithVertices(vertexList.get(2), vertexList.get(0))==null){
					direction = getLineDirection(vertexList.get(2),vertexList.get(0));
				}
				triangle.getFieldVector().set(direction[0]*length,direction[1]*length);
			}
			
		}
	}
	private double[] getLineDirection(Vertex vertex0, Vertex vertex1){
		double[] direction = new double[2];
		double x = vertex0.getX()-vertex1.getX();
		double y = vertex0.getY()-vertex1.getY();
		direction[0]=x/(x*x+y*y);
		direction[1]=y/(x*x+y*y);
		return direction;
	}
}
