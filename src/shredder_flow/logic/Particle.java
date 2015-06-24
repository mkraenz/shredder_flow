package shredder_flow.logic;

import javax.vecmath.Vector2d;

import shredder_flow.logic.Triangle;

public class Particle {

	private Vector2d position;
	private NextPositionStrategy strategy;
	private Triangle triangle;

	public Particle(double x, double y, Triangle triangle) {
		this.triangle = triangle;
		this.position = new Vector2d(x,y);
		this.strategy = new NextPositionStrategy();
	}

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
