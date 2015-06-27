package shredder_flow.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RuppertAdapterTest {

	private RuppertAdapter ruppertAdapter;
	private Boundary unitSquare;
	private Boundary hexagon;

	@Before
	public void setUp() throws Exception {
		ruppertAdapter = new RuppertAdapter(new TriangulationVertexList(), new TriangleList());
		createUnitSquare();
		createHexagon();
	}

	private void createHexagon() {
		hexagon = new Boundary();
		hexagon.add(new Vertex(0, 0));
		hexagon.add(new Vertex(1, 1));
		hexagon.add(new Vertex(0, 1));
		hexagon.add(new Vertex(0, 0.9));
		hexagon.add(new Vertex(0, 0.85));
	}

	private void createUnitSquare() {
		unitSquare = new Boundary();
		unitSquare.add(new Vertex(0, 0));
		unitSquare.add(new Vertex(1, 0));
		unitSquare.add(new Vertex(1, 1));
		unitSquare.add(new Vertex(0, 1));
	}

	@Test
	public void testTriangulationOfUnitSquare() throws Exception {
		ruppertAdapter.triangulate(unitSquare);
		assertEquals(2, ruppertAdapter.getTriangleList().size());
		assertEquals(4, ruppertAdapter.getVertexList().size());
	}

	@Test
	public void testSetNeighborsOfUnitSquare() throws Exception {
		ruppertAdapter.triangulate(unitSquare);
		Triangle triangle0 = ruppertAdapter.getTriangleList().get(0);
		Triangle triangle1 = ruppertAdapter.getTriangleList().get(1);
		triangle0.getNeighbors();
		assertEquals(triangle1, triangle0.getNeighbors().get(0));
		assertEquals(triangle0, triangle1.getNeighbors().get(0));
	}
	
	@Test
	public void testNeighborsSetOfHexagon() throws Exception {
		ruppertAdapter.setMinimalAngleConstraint(30);
		ruppertAdapter.triangulate(hexagon);
		for (Triangle triangle : ruppertAdapter.getTriangleList()) {
			assertBetweenZeroAndThreeElements(triangle.getNeighbors());
		}
	}

	private void assertBetweenZeroAndThreeElements(TriangleList triangles) {
		if(triangles.size() <= 0){
			fail("Less or equal to 0 elements");
		}
		else {
			if(triangles.size() > 3){
				fail("Strictly more than 3 elements");
			}
		}
		
	}
}
