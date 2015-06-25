package shredder_flow.logic;

public class ParticleCreator {

	private ParticleList particles;
	private TriangleList triangles;

	public void addParticle(double x, double y) {
		Triangle triangleContainingPosition = triangles.getTriangle(x, y);
		if (triangleContainingPosition != null) {
			particles.add(new Particle(x, y, triangleContainingPosition));
		}
	}
}
