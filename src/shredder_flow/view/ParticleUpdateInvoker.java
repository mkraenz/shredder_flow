package shredder_flow.view;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.ParticleUpdater;

public class ParticleUpdateInvoker extends View2DShrinkPanelPlugin {
	private ParticleUpdater updater;

	public ParticleUpdateInvoker(ParticleUpdater particleUpdater) {
		this.updater = particleUpdater;
	}

	public void startUpdates() {
		updater.startUpdates();
	}

	public void stopUpdates() {
		updater.stopUpdates();
	}

	public void fastForward(double fastForwardFactor) {
		// TODO: check if this works the way we want it. Maybe one can even
		// delete the stop and start commands
		stopUpdates();
		updater.setFastForwardFactor(fastForwardFactor);
		startUpdates();
	}
}
