package shredder_flow.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TriangleTest {

	private Triangle unitTriangle;

	@Before
	public void setUp() throws Exception {
		TriangulationVertexList vertices = new TriangulationVertexList();
		vertices.add(new Vertex(0, 0));
		vertices.add(new Vertex(1, 0));
		vertices.add(new Vertex(0, 1));
		unitTriangle = new Triangle(vertices);
	}

	@Test
	public void testThatNeverFails() {
		assertTrue(true);
	}

	@Test
	public void testIsInTriangle() throws Exception {
		assertTrue(unitTriangle.isInTriangle(0, 0));
		assertTrue(unitTriangle.isInTriangle(1, 0));
		assertTrue(unitTriangle.isInTriangle(0, 1));
		assertTrue(unitTriangle.isInTriangle(1.0 / 2, 0));
		assertTrue(unitTriangle.isInTriangle(0, 1.0 / 2));

		assertFalse(unitTriangle.isInTriangle(0, -1));
		assertFalse(unitTriangle.isInTriangle(0, -0.00000001));
		assertFalse(unitTriangle.isInTriangle(200, 50));
		assertFalse(unitTriangle.isInTriangle(-123, -1));
		assertFalse(unitTriangle.isInTriangle(1.0 / 2, 1));
	}

	@Test
	public void testGetDistanceToEdge() throws Exception {
		assertEquals(0, unitTriangle.getDistanceToEdge(1/ 2.0, 0, 0, 1), 0);
		assertEquals(1/Math.sqrt(2), unitTriangle.getDistanceToEdge(0, 0, 1, 2), 0);
		assertEquals(1.2, unitTriangle.getDistanceToEdge(1/3.0, 1.2, 0, 1), 0);
		assertEquals(1.52, unitTriangle.getDistanceToEdge(-1.52, 1/6, 2, 0), 0);
		
	}
}
