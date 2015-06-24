package shredder_flow.logic;

import javax.vecmath.Vector2d;


public class Vertex {

	private Vector2d position;
	
	private double functionValue;

	public Vertex(double x, double y) {
		this.position = new Vector2d(x,y);
		this.setFunctionValue(0);
	}

	public double getY() {
		return position.x;
	}

	public double getX(){
		return position.y;
	}
	
	public double getFunctionValue() {
		return functionValue;
	}

	public void setFunctionValue(double functionValue) {
		this.functionValue = functionValue;
	}

	public boolean isPositionEqual(Vertex vertex) {
		/**
		 * Check if the position coordinates of this vertex and the given one are equal.
		 */
		if (this.getX() != vertex.getX() || this.getY() != vertex.getY()) {
			return false;
		}
		else{
			return true;
		}
	}
}
