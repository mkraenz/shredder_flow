package shredder_flow.logic;

import static org.junit.Assert.*;

import javax.vecmath.Vector2d;

import org.junit.Before;
import org.junit.Test;

public class MovementStrategyTestWhirl {

	private Vertex vertexZero;
	private Vertex vertexOne;
	private Vertex vertexPosI;
	private Vertex vertexNegI;

	private TriangleList triangles;

	private Triangle leftTriangle;
	private Triangle upperTriangle;
	private Triangle bottomTriangle;

	@Before
	public void setUp() throws Exception {
		vertexZero = new Vertex(0, 0);
		vertexOne = new Vertex(1, 0);
		vertexPosI = new Vertex(-1.0 / 2, Math.sqrt(3) / 2);
		vertexNegI = new Vertex(-1.0 / 2, -Math.sqrt(3) / 2);
		leftTriangle = createTriangle(vertexZero, vertexPosI, vertexNegI);
		upperTriangle = createTriangle(vertexZero, vertexPosI, vertexOne);
		bottomTriangle = createTriangle(vertexZero, vertexNegI, vertexOne);
		triangles = new TriangleList();
		triangles.add(leftTriangle);
		triangles.add(upperTriangle);
		triangles.add(bottomTriangle);
	}

	@Test
	public void testWhirlMovement() {
		Particle particle = new Particle(0.5, 0, upperTriangle,
				new MovementStrategy(triangles));
		setParallelFieldVector(vertexNegI, vertexPosI, leftTriangle);
		setParallelFieldVector(vertexPosI, vertexOne, upperTriangle);
		setParallelFieldVector(vertexOne, vertexNegI, bottomTriangle);

		particle.update(0.5);
		assertEquals(-0.25, particle.getX(), 0);
		assertEquals(Math.sqrt(3) / 4, particle.getY(), 0.001);
		assertEquals(upperTriangle, particle.getTriangle());
		particle.update(0.5);
		particle.update(0.5);
		assertEquals(0.5, particle.getX(), 0);
		assertEquals(0, particle.getY(), 0);
		assertEquals(bottomTriangle, particle.getTriangle());
		// TODO set breakpoint here
		particle.update(1.5);
		assertEquals(0.5, particle.getX(), 0);
		assertEquals(0, particle.getY(), 0);
		assertEquals(bottomTriangle, particle.getTriangle());
	}

	private void setParallelFieldVector(Vertex vertex1, Vertex vertex2,
			Triangle triangle) {
		Vector2d vec = new Vector2d(vertex1.getX() - vertex2.getX(),
				vertex1.getY() - vertex2.getY());
		triangle.getFieldVector().set(vec.x, vec.y);
	}

	private Triangle createTriangle(Vertex vertex0, Vertex vertex1,
			Vertex vertex2) {
		TriangulationVertexList vertices = new TriangulationVertexList();
		vertices.add(vertex0);
		vertices.add(vertex1);
		vertices.add(vertex2);
		Triangle triangle = new Triangle(vertices);
		return triangle;
	}
}
