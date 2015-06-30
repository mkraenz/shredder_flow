package shredder_flow.logic;

import javax.vecmath.Vector2d;

import shredder_flow.logic.Triangle;

public class Particle {

	private Vector2d position;
	private MovementStrategy strategy;
	private Triangle triangle;

	public Particle(double x, double y, Triangle triangle, MovementStrategy strategy) {
		this.triangle = triangle;
		this.position = new Vector2d(x, y);
		this.strategy = strategy;
	}

	public Triangle getTriangle() {
		return triangle;
	}
	public void setPosition(double x, double y){
		this.position.set(x,y);
	}

	public void setTriangle(Triangle triangle) {
		this.triangle = triangle;
	}

	public void update(double deltaT) {
		strategy.setNextPositionAndTriangle(deltaT, this);
	}

	public double getX() {
		return position.x;
	}

	public double getY() {
		return position.y;
	}
	
	public Vector2d getPosition(){
		return position;
	}
}
