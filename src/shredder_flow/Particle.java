package shredder_flow;

import javax.vecmath.Vector2d;
import shredder_flow.Triangle;

public class Particle {

	private Vector2d position;
	private NextPositionStrategy strategy;
	private Triangle triangle;

	public Triangle getTriangle() {
		return triangle;
	}

	public void setTriangle(Triangle triangle) {
		this.triangle = triangle;
	}

	public void update(double deltaT) {
		updatePosition(deltaT);
	}

	private void updatePosition(double deltaT) {
		Vector2d newPosition = strategy.getNextPosition(position, triangle,
				deltaT);
		position.set(newPosition);

	}

}
