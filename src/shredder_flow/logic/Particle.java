package shredder_flow.logic;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

public class Particle {

	private Vector2d position;
	private MovementStrategy strategy;
	private Triangle triangle;
	private boolean receivesUpdates;

	public Particle(double x, double y, Triangle triangle,
			MovementStrategy strategy) {
		this.triangle = triangle;
		this.position = new Vector2d(x, y);
		this.strategy = strategy;
		this.receivesUpdates = true;
	}

	public Triangle getTriangle() {
		return triangle;
	}

	public void setPosition(double x, double y) {
		this.position.set(x, y);
	}

	public void setReceivesUpdates(boolean movement) {
		this.receivesUpdates = movement;
	}

	public void setTriangle(Triangle triangle) {
		this.triangle = triangle;
	}

	public void update(double deltaT) {
		if (receivesUpdates == true) {
			strategy.setNextPositionAndTriangle(deltaT, this);
		}
	}

	public double getX() {
		return position.x;
	}

	public double getY() {
		return position.y;
	}

	public Vector2d getPosition() {
		return position;
	}

	public Point2d getPositionAsPoint2d() {
		return new Point2d(position.x, position.y);
	}
}
