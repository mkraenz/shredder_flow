package shredder_flow.logic;

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
}
