package shredder_flow.view;

import shredder_flow.logic.ParticleCreator;
import shredder_flow.logic.ParticleList;
import de.jtem.jrworkspace.plugin.Plugin;

public class ParticlePlugin extends Plugin {

	private ParticleList particles;
	private ParticleCreator creator;

	public void draw() {
		// TODO: implement
	}

	public ParticlePlugin(ParticleCreator creator, ParticleList particles) {
		this.creator = creator;
		this.particles = particles;
	}

	public void addParticles() {
		/*
		 * TODO find a way to properly add particles using the datafield
		 * creator. It might even be better to have an own class to handle the
		 * adding. The ParticlePlugin would then only draw the particles. While
		 * the other one handles only adding.
		 */
	}
}
