package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import shredder_flow.logic.ParticleCreator;
import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;

public class ParticleAdderPanel extends View2DShrinkPanelPlugin {
	private ParticleCreator creator;
	private ParticlePlugin particlePlugin;
	private JTextField xComponentTextField;
	private JTextField yComponentTextField;
	private double xComponent;
	private double yComponent;
	
	public ParticleAdderPanel(ParticleCreator creator,
			ParticlePlugin particlePlugin) {
		this.creator = creator;
		this.particlePlugin = particlePlugin;
		addGuiElements();
	}

	private void addGuiElements() {
		final int ROWS = 5;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addButton(new RandomParticlesAction(), "Random Particles around 0");
		addButton(new ResetParticlesAction(), "Reset");
		addParticleOptBox();
		addButton(new SetOneParticleAction(), "Set One Partce at the Coordinate (x,y)");
		addButton(new SetOneRandomParticleAction(), "One Random Particle around 0");
	}

	private void addParticleOptBox() {
		xComponentTextField = new JTextField("0");
		yComponentTextField = new JTextField("0");
		shrinkPanel.add(new JLabel("x:"));
		shrinkPanel.add(xComponentTextField);
		shrinkPanel.add(new JLabel("y:"));
		shrinkPanel.add(yComponentTextField);
		
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
	
	private void setOneRandomParticle() {
		creator.addOneRandomParticleCloud();
		particlePlugin.draw();		
	}

	private void setOneParticle(){
		try {
			xComponent = Double.parseDouble(xComponentTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given xComponent to double.");
		}
		try {
			yComponent = Double.parseDouble(yComponentTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given xComponent to double.");
		}
		System.out.println(xComponent);
		creator.addParticle(xComponent, yComponent);
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
	
	class SetOneParticleAction extends AbstractAction{
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setOneParticle();
		}
	}
	
	class SetOneRandomParticleAction extends AbstractAction{
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setOneRandomParticle();
		}
	}

	private void resetParticles() {
		creator.clearParticleList();
		particlePlugin.draw();
	}
}
