package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class Vertex {

	private Vector2d position;

	private double functionValue;

	public Vertex(double x, double y) {
		this.position = new Vector2d(x, y);
		this.setFunctionValue(0);
	}

	public double getY() {
		return position.y;
	}

	@Override
	public String toString() {
		return getPosition().toString();
	}

	public double getX() {
		return position.x;
	}

	/**
	 * @return new instance representing the position of the vertex.
	 */
	public Point2d getPosition() {
		return new Point2d(position.x, position.y);
	}

	public double getFunctionValue() {
		return functionValue;
	}

	public void setFunctionValue(double functionValue) {
		this.functionValue = functionValue;
	}

	/**
	 * Check if the position coordinates of this vertex and the given position
	 * are equal.
	 */
	public boolean isPositionEqual(double x, double y) {
		if (this.getX() != x || this.getY() != y) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check if the position coordinates of this vertex and the given one are
	 * equal.
	 */
	public boolean isPositionEqual(Vertex vertex) {
		return isPositionEqual(vertex.getX(), vertex.getY());
	}
}
