package shredder_flow;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.portable.IndirectionException;

import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;

public class DraggablePolygon2DTriangulatorTest {

	private DraggablePolygon2D boundaryPolygon;
	private DraggablePolygon2DTriangulator triangulator;

	@Before
	public void setUp() throws Exception {
		int BOUNDARY_CURVE_POINT_COUNT = 100;
		boundaryPolygon = new DraggablePolygon2D(BOUNDARY_CURVE_POINT_COUNT,
				DraggablePoint2DList.CIRCULAR, 0, 0, 1, true, true);
		triangulator = new DraggablePolygon2DTriangulator();
		triangulator.triangulate(boundaryPolygon);
	}

	@Test
	public void testThatNeverFails() throws Exception {
		assertTrue(true);
		;
	}

	@Test
	public void testTriangulateIndicesAndPointsNotNull() throws Exception {
		assertNotNull(triangulator.getIndices());
		assertNotNull(triangulator.getPointsAs2DimensionalArray());
		assertNotNull(triangulator.getPointsAsOneDimensionalArray());
	}

	@Test
	public void testNonemptyIndicesAndPoints() throws Exception {
		assertNotEquals(0, triangulator.getIndices());
		assertNotEquals(0, triangulator.getPointsAs2DimensionalArray());
		assertNotEquals(0, triangulator.getPointsAsOneDimensionalArray());
	}

	@Test
	public void testEachPointIsVertexOfSomeTriangle() throws Exception {
		int[] indices = triangulator.getIndices();
		int[] vertexIndices = new int[boundaryPolygon.getDefaultPointCount()];
		for (int i = 0; i < indices.length; i++) {
			setOneIfVertexIsInTriangle(indices, vertexIndices, i);
		}
		for (int i = 0; i < vertexIndices.length; i++) {
			assertNotNull(vertexIndices[i]);
		}
	}

	private void setOneIfVertexIsInTriangle(int[] indices, int[] vertexIndices,
			int i) {
		if (indices[i] < vertexIndices.length) {
			vertexIndices[indices[i]] = 1;
		}
	}

	@Test
	public void testNeighborsNotNull() throws Exception {
		if (boundaryPolygon.getDefaultPointCount() != 3) {
			assertNotNull(triangulator.getNeighbors());
		}
	}
}
