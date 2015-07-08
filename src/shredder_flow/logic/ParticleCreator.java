package shredder_flow.logic;

import java.util.Random;

public class ParticleCreator {

	private ParticleList particles;
	private TriangleList triangles;

	public ParticleCreator(ParticleList particleList, TriangleList triangleList) {
		this.particles = particleList;
		this.triangles = triangleList;
	}

	public void addParticle(double x, double y) {
		Triangle triangleContainingPosition = triangles.getTriangle(x, y);
		if (triangleContainingPosition != null) {
			particles.add(new Particle(x, y, triangleContainingPosition, new MovementStrategy(triangles)));
		}
	}

	/**
	 * Uniform distribution around zero in a square of side length 1.
	 */
	public void addRandomParticleCloud() {
		int particleCount = 1;
		int highestRandomValue = 100;
		double xShift = -0.5;
		double yShift = -0.5;
		double scale = 0.01;
		Random random = new Random();
		for (int i = 0; i < particleCount; i++) {
			addParticle(random.nextInt(highestRandomValue) * scale + xShift,
					random.nextInt(highestRandomValue) * scale + yShift);
		}
	}
	
	public void clearParticleList(){
		particles.clear();
	}
}
