package shredder_flow.logic;

import java.util.ArrayList;

public class ParticleList extends ArrayList<Particle> {

	private static final long serialVersionUID = 1L;

	public void update(double deltaT) {
		for (Particle particle : this) {
			particle.update(deltaT);
		}
	}

}
