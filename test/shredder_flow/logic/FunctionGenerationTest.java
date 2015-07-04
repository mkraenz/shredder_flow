package shredder_flow.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shredder_flow.view.FunctionGeneratorInvoker;

public class FunctionGenerationTest {

	private FunctionGenerator generator;
	private TriangulationVertexList vertices;

	@Before
	public void setUp() throws Exception {
		Vertex vertex1 = new Vertex(0, 0);
		Vertex vertex2 = new Vertex(1231.310, 123.3);
		Vertex vertex3 = new Vertex(123.3, 645.54);
		vertices = new TriangulationVertexList();
		vertices.add(vertex1);
		vertices.add(vertex2);
		vertices.add(vertex3);
		generator = new FunctionGenerator(vertices);
	}

	@Test
	public void testRandomGeneration() throws Exception {
		generator.generateRandomFunction(1, 1);
		for (Vertex vertex : vertices) {
			assertNotEquals(0, vertex.getFunctionValue());
		}
	}

}
