package shredder_flow.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParticleCreatorTest {

	private Triangle unitTriangle;
	private Triangle bigTriangle;
	private ParticleCreator creatorForUnitTriangle;
	private ParticleCreator creatorForBigTriangle;
	private ParticleList particleListForUnitTriangle;
	private ParticleList particleListForBigTriangle;

	@Before
	public void setUp() throws Exception {
		TriangulationVertexList verticesForUnitTriangle = new TriangulationVertexList();
		verticesForUnitTriangle.add(new Vertex(0, 0));
		verticesForUnitTriangle.add(new Vertex(1, 0));
		verticesForUnitTriangle.add(new Vertex(0, 1));
		unitTriangle = new Triangle(verticesForUnitTriangle);
		TriangleList unitTriangleList = new TriangleList();
		unitTriangleList.add(unitTriangle);
		this.particleListForUnitTriangle = new ParticleList();
		this.creatorForUnitTriangle = new ParticleCreator(particleListForUnitTriangle,
				unitTriangleList);

		TriangulationVertexList verticesForBigTriangle = new TriangulationVertexList();
		verticesForBigTriangle.add(new Vertex(10000, 0));
		verticesForBigTriangle.add(new Vertex(0, 10000));
		verticesForBigTriangle.add(new Vertex(-10000, -10000));
		bigTriangle = new Triangle(verticesForBigTriangle);
		TriangleList bigTriangleList = new TriangleList();
		bigTriangleList.add(bigTriangle);
		this.particleListForBigTriangle = new ParticleList();
		this.creatorForBigTriangle = new ParticleCreator(particleListForBigTriangle,
				bigTriangleList);
	}

	@Test
	public void testAddRandomPointCloudTrianglesSet() throws Exception {
		creatorForBigTriangle.addRandomParticleCloud();
		for (Particle particle : particleListForBigTriangle) {
			assertNotNull(particle.getTriangle());
		}
	}
	
	@Test
	public void testAddParticleAllInside() throws Exception {
		creatorForUnitTriangle.addParticle(0, 0);
		creatorForUnitTriangle.addParticle(1, 0);
		creatorForUnitTriangle.addParticle(0, 1);
		creatorForUnitTriangle.addParticle(1.0/2, 1.0/2);
		for (Particle particle : particleListForUnitTriangle) {
			assertNotNull(particle.getTriangle());
		}
	}
	
	@Test
	public void testAddParticleAllOutside() throws Exception {
		creatorForUnitTriangle.addParticle(-1, 0);
		creatorForUnitTriangle.addParticle(1.0/2, 1);
		assertTrue(particleListForUnitTriangle.isEmpty());
	}

}
