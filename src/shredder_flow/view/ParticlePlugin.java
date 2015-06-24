package shredder_flow.view;

import shredder_flow.logic.ParticleList;
import de.jtem.jrworkspace.plugin.Plugin;

public class ParticlePlugin extends Plugin {

	private ParticleList particleList;

	public void draw() {
		// TODO: implement
	}

	public ParticlePlugin(ParticleList particles) {
		this.particleList = particles;
	}

	public void setParticleList(ParticleList particleList) {
		this.particleList = particleList;
	}

}
