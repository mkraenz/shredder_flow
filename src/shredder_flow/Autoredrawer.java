package shredder_flow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import shredder_flow.view.MeshPlugin;
import shredder_flow.view.ParticlePlugin;

/**
 * Pushes redraws the viewer each time an ActionEvent is performed. The idea is
 * to bound this to a Timer that updates about 60 times per second.
 * Set the datafields properly before running. The class is not safe with respect to NullPointerExceptions.
 * @author Mirco
 *
 */
public class Autoredrawer implements ActionListener {

	private MeshPlugin meshDrawer;
	private ParticlePlugin particleDrawer;

	@Override
	public void actionPerformed(ActionEvent e) {
		draw();
	}

	private void draw() {
		meshDrawer.draw();
		particleDrawer.draw();
	}

	public MeshPlugin getMeshDrawer() {
		return meshDrawer;
	}

	public void setMeshDrawer(MeshPlugin meshDrawer) {
		this.meshDrawer = meshDrawer;
	}

	public ParticlePlugin getParticleDrawer() {
		return particleDrawer;
	}

	public void setParticleDrawer(ParticlePlugin particleDrawer) {
		this.particleDrawer = particleDrawer;
	}

}
