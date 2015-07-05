package shredder_flow.logic;

import java.util.Random;

public class FunctionGenerator {

	private TriangulationVertexList vertices;

	public FunctionGenerator(TriangulationVertexList vertexList) {
		this.vertices = vertexList;
	}

	public void generate() {
		// TODO: implement
		// The task is to provide different generate()
		// methods that can be called from the FunctionGeneratorInvoker, e.g.
		// generateRandomValues(), generateNormOfVertex(),
		// generateblablub()
		// ...
	}

	public void generateRandomFunction(double rangeMin, double rangeMax) {
		Random r = new Random();
		for (Vertex vertex : vertices) {
			double randomValue = rangeMin + (rangeMax - rangeMin)
					* r.nextDouble();
			vertex.setFunctionValue(randomValue);
		}

	}
	
	public void generateHandwrittenFuction(double alpha, double xUp, double yUp, double constant){
		for (Vertex vertex : vertices) {
			double value =alpha*Math.pow(vertex.getX(), xUp)*Math.pow(vertex.getY(), yUp)+constant;
			vertex.setFunctionValue(value);
		}
	}

	public void generateHandwrittenFuctionSum(double alpha, double xUp,
			double beta, double yUp, double constant) {
		for (Vertex vertex : vertices) {
			double value =alpha*Math.pow(vertex.getX(), xUp)+beta*Math.pow(vertex.getY(), yUp)+constant;
			vertex.setFunctionValue(value);
		}
	}
}
