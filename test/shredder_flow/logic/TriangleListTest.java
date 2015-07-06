package shredder_flow.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TriangleListTest {

	private Vertex vertex0;
	private Vertex vertex1;
	private Vertex vertex2;
	private Vertex vertex3;

	@Before
	public void setUp() throws Exception {
		vertex0 = new Vertex(-1, 0);
		vertex1 = new Vertex(0, -1);
		vertex2 = new Vertex(0, 1);
		vertex3 = new Vertex(1, 0);
	}
	
	@Test
	public void testGetTriangle() throws Exception {
		Triangle leftTriangle = createTriangle(vertex0, vertex1, vertex2);
		Triangle rightTriangle = createTriangle(vertex1, vertex2, vertex3);
		
		TriangleList triangles = new TriangleList();
		triangles.add(leftTriangle);
		triangles.add(rightTriangle);
		assertEquals(leftTriangle, triangles.getTriangle(-1, 0));
		assertEquals(leftTriangle, triangles.getTriangle(-1.0/2, 0));
		assertEquals(rightTriangle, triangles.getTriangle(1, 0));
		assertEquals(rightTriangle, triangles.getTriangle(1.0/2, 0));
		assertEquals(leftTriangle, triangles.getTriangle(-0.00000000001, 0));
		assertEquals(rightTriangle, triangles.getTriangle(0.000000001, 0.9));
	}

	private Triangle createTriangle(Vertex vertex0, Vertex vertex1,
			Vertex vertex2) {
		TriangulationVertexList verticesForLeftTriangle = new TriangulationVertexList();
		verticesForLeftTriangle.add(vertex0);
		verticesForLeftTriangle.add(vertex1);
		verticesForLeftTriangle.add(vertex2);
		Triangle leftTriangle = new Triangle(verticesForLeftTriangle);
		return leftTriangle;
	}
}
