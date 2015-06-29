package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.ParticleUpdater;

public class ParticleUpdateInvoker extends View2DShrinkPanelPlugin {
	private ParticleUpdater updater;
	private JTextField fastForwardFactorTextField;

	public ParticleUpdateInvoker(ParticleUpdater particleUpdater) {
		this.updater = particleUpdater;
		addGuiElements();
	}

	public void startUpdates() {
		updater.startUpdates();
	}

	public void stopUpdates() {
		updater.stopUpdates();
	}

	public void fastForward(double fastForwardFactor) {
		updater.setFastForwardFactor(fastForwardFactor);
	}

	private void addGuiElements() {
		final int ROWS = 3;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addStartButton();
		addStopButton();
		addFastForward();
	}

	private void addFastForward() {
		fastForwardFactorTextField = new JTextField("1.0");
		
		shrinkPanel.add(new JLabel("Fast Forward:"));
		shrinkPanel.add(fastForwardFactorTextField);
		addButton(new ApplyFastForwardAction(), "Apply Fast Forward");
		addButton(new UnitSpeedAction(), "1x Speed");
	}

	private void addStopButton() {
		addButton(new StopAction(), "Stop Flow");
	}

	private void addStartButton() {
		addButton(new StartAction(), "Start Flow");
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	class StartAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			startUpdates();
		}
	}

	class StopAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			stopUpdates();
		}
	}

	class UnitSpeedAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			fastForward(1.0);
		}
	}

	class ApplyFastForwardAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			double s = 1;
			try {
				s = Double.parseDouble(fastForwardFactorTextField.getText());
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given speed to double.");
			}
			fastForward(s);
		}
	}
}
