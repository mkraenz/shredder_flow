package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import shredder_flow.logic.ParticleCreator;
import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;

public class ParticleAdderPanel extends View2DShrinkPanelPlugin {
	private ParticleCreator creator;
	private ParticlePlugin particlePlugin;

	public ParticleAdderPanel(ParticleCreator creator,
			ParticlePlugin particlePlugin) {
		this.creator = creator;
		this.particlePlugin = particlePlugin;
		addGuiElements();
	}

	private void addGuiElements() {
		final int ROWS = 2;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addButton(new RandomParticlesAction(), "Random Particles around 0");
		addButton(new ResetParticlesAction(), "Reset");
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	private void addRandomParticleCloud() {
		creator.addRandomParticleCloud();
		particlePlugin.draw();
	}

	class RandomParticlesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			addRandomParticleCloud();
		}
	}

	class ResetParticlesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			resetParticles();
		}

	}


	class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 2) {
				creator.addParticle(evt.getX(), evt.getY());
			}
		}
	}

	private void resetParticles() {
		creator.clearParticleList();
		particlePlugin.draw();
	}
}
