package shredder_flow.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class ParticleUpdater implements ActionListener {

	private ParticleList particles;
	private double fastForwardFactor;
	private int updatesPerSecond;
	private Timer updateTimer;

	public ParticleUpdater(ParticleList particles, int updatesPerSecond,
			Timer updateTimer) {
		this.setFastForwardFactor(1);
		this.particles = particles;
		this.updatesPerSecond = updatesPerSecond;
		this.updateTimer = updateTimer;
		configureTimer();
	}

	private void configureTimer() {
		updateTimer.addActionListener(this);
		double updateInterval = Math.floor(1.0 / updatesPerSecond * 1000);
		updateTimer.setDelay((int) updateInterval);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		particles.update(fastForwardFactor * updatesPerSecond);
	}

	public void setParticles(ParticleList particles) {
		this.particles = particles;
	}

	public void setFastForwardFactor(double fastForwardFactor) {
		this.fastForwardFactor = fastForwardFactor;
	}

	public void stopUpdates() {
		updateTimer.stop();
	}

	public void startUpdates() {
		updateTimer.start();
	}

}
