/**
 * 
 */
package shredder_flow.triangulated_domain;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;

public class TriangulatedDomainModelTest extends TestCase {

	private DraggablePolygon2DTriangulator triangulator;
	private DraggablePolygon2D polygon;

	@Before
	public void setUp() throws Exception {
		int POINT_COUNT = 10;
		triangulator = new DraggablePolygon2DTriangulator();
		polygon = new DraggablePolygon2D(POINT_COUNT,
				DraggablePoint2DList.CIRCULAR, 0, 0, 1, true, true);
	}

	@Test
	public void testThatNeverFails() {
		assertTrue(true);
	}

	@Test
	public void testIndicesNotNull() throws Exception {
		TriangulatedDomainModel model = new TriangulatedDomainModel(polygon,
				triangulator);
		assertNotNull(model.getIndices());
	}

	@Test
	public void testIndicesNotEmpty() throws Exception {
		TriangulatedDomainModel model = new TriangulatedDomainModel(polygon,
				triangulator);
		assertNotEquals(0, model.getIndices().length);
	}

	@Test
	public void testBoundarySceneComponentNotNull() throws Exception {
		TriangulatedDomainModel model = new TriangulatedDomainModel(polygon,
				triangulator);
		assertNotNull(model.getSceneComponentOfBoundaryPolygon());
		
		TriangulatedDomainModel model2 = new TriangulatedDomainModel(5,
				triangulator);
		assertNotNull(model2.getSceneComponentOfBoundaryPolygon());
	}
}
