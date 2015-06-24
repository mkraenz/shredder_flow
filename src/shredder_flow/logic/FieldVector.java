package shredder_flow.logic;

import javax.vecmath.Vector2d;

public class FieldVector extends Vector2d {

	private static final long serialVersionUID = 1L;

	public FieldVector(double x, double y) {
		super(x, y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
