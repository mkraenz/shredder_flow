package shredder_flow.logic;

import static org.junit.Assert.*;

import java.io.ObjectInputStream.GetField;

import org.junit.Before;
import org.junit.Test;

public class MovementStrategyTest {

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
	public void testParticleUpdateOneStepInSingleTriangle() throws Exception {
		TriangulationVertexList verticesForBigTriangle = new TriangulationVertexList();
		verticesForBigTriangle.add(new Vertex(10000, 0));
		verticesForBigTriangle.add(new Vertex(0, 10000));
		verticesForBigTriangle.add(new Vertex(-10000, -10000));
		Triangle bigTriangle = new Triangle(verticesForBigTriangle);
		TriangleList bigTriangleList = new TriangleList();
		bigTriangleList.add(bigTriangle);
		ParticleList particleListForBigTriangle = new ParticleList();
		ParticleCreator creatorForBigTriangle = new ParticleCreator(
				particleListForBigTriangle, bigTriangleList);
		creatorForBigTriangle.addParticle(0, 0);
		Particle particle = particleListForBigTriangle.get(0);
		
		bigTriangle.getFieldVector().set(800.324,123.42);
		particle.update(1);
		assertEquals(800.324, particle.getX(),0);
		assertEquals(123.42, particle.getY(),0);
	}
	
	@Test
	public void testParticleMoveOverTwoTrianglesWithoutTouchingEdge() throws Exception {
		Triangle leftTriangle = createTriangle(vertex0, vertex1, vertex2);
		Triangle rightTriangle = createTriangle(vertex1, vertex2, vertex3);
		
		TriangleList triangles = new TriangleList();
		triangles.add(leftTriangle);
		triangles.add(rightTriangle);
		
		leftTriangle.getFieldVector().set(1, 0);
		
		Particle particle = new Particle(-1.0/2, 0, leftTriangle, new MovementStrategy(triangles));
		
		particle.update(1.0/4);
		assertEquals(-1.0/4, particle.getX(), 0);
		assertEquals(0, particle.getY(), 0);
		assertEquals(leftTriangle, particle.getTriangle());
		
		particle.update(0);
		assertEquals(-1.0/4, particle.getX(), 0);
		assertEquals(0, particle.getY(), 0);
		assertEquals(leftTriangle, particle.getTriangle());
		
		particle.update(1.0/4);
		assertEquals(0, particle.getX(), 0);
		assertEquals(0, particle.getY(), 0);
		assertEquals(leftTriangle, particle.getTriangle());
		
		particle.update(1.0/4);
		assertEquals(1.0/4, particle.getX(), 0);
		assertEquals(0, particle.getY(), 0);
		assertEquals(rightTriangle, particle.getTriangle());
		
		
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
