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
			particles.add(new Particle(x, y, triangleContainingPosition));
		}
	}

	public void addRandomParticleCloud() {
		int particleCount = 100;
		int highestRandomValue = 100;
		double scale = 0.1;
		Random random = new Random();
		for(int i = 0; i< particleCount; i++){
			addParticle(random.nextInt(highestRandomValue)*scale, random.nextInt(highestRandomValue)*scale);
		}
	}
}
