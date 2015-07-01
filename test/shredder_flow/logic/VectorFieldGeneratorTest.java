package shredder_flow.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VectorFieldGeneratorTest {

	private Triangle unitTriangle;
	private Vertex vertex1;
	private Vertex vertex2;
	private Vertex vertex3;
	private VectorFieldGenerator generator;

	@Before
	public void setUp() throws Exception {
		TriangulationVertexList vertices = new TriangulationVertexList();
		vertex1 = new Vertex(0, 0);
		vertex2 = new Vertex(1, 0);
		vertex3 = new Vertex(0, 1);

		vertices.add(vertex1);
		vertices.add(vertex2);
		vertices.add(vertex3);

		unitTriangle = new Triangle(vertices);
		TriangleList triangleList = new TriangleList();
		triangleList.add(unitTriangle);
		generator = new VectorFieldGenerator(triangleList);
	}

	@Test
	public void testGradientVectorFieldTwoVerticesGradientInNegXDir()
			throws Exception {
		vertex2.setFunctionValue(1);
		generator.generateGradiantField();
		assertInputIntegrity(0, 1, 0);
		FieldVector fieldVector = unitTriangle.getFieldVector();

		assertEquals(-1, fieldVector.getX(), 0);
		assertEquals(0, fieldVector.getY(), 0);
	}

	/**
	 * Check that none of the function values have been modified by the method call.
	 */
	private void assertInputIntegrity(
			double expectedValueForVertex1, double expectedValueForVertex2,
			double expectedValueForVertex3) {
		assertEquals(expectedValueForVertex1, vertex1.getFunctionValue(), 0);
		assertEquals(expectedValueForVertex2, vertex2.getFunctionValue(), 0);
		assertEquals(expectedValueForVertex3, vertex3.getFunctionValue(), 0);
	}

	@Test
	public void testGradientVectorFieldTwoVerticesGradientInNegYDir() throws Exception {
		vertex3.setFunctionValue(1);
		generator.generateGradiantField();
		assertInputIntegrity(0, 0, 1);
		FieldVector fieldVector = unitTriangle.getFieldVector();

		assertEquals(0, fieldVector.getX(), 0);
		assertEquals(-1, fieldVector.getY(), 0);
	}
}
