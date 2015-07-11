package shredder_flow.logic;

import java.util.ArrayList;

public class TriangleList extends ArrayList<Triangle> {

	private static final long serialVersionUID = 1L;

	/**
	 * Returns the first Triangle such that the given position (x,y) lies inside
	 * or on the boundary of that triangle. If there is no such triangle, returns null.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Triangle getTriangle(double x, double y) {
		for (Triangle triangle : this) {
			if (triangle.isInTriangle(x, y)) {
				return triangle;
			}
		}
		return null;
	}

}
