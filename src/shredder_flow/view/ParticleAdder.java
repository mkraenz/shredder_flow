package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import shredder_flow.logic.ParticleCreator;
import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;

public class ParticleAdder extends View2DShrinkPanelPlugin {
	private ParticleCreator creator;
	
	public ParticleAdder(ParticleCreator creator) {
		this.creator = creator;
		addGuiElements();
	}

	private void addGuiElements() {
		final int ROWS = 2;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addRandomParticlesButton();
	}
	
	private void addRandomParticlesButton() {
		addButton(new RandomParticlesAction(), "Random Particles");
		
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}
	
	class RandomParticlesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			creator.addRandomParticleCloud();
		}
	}
}
