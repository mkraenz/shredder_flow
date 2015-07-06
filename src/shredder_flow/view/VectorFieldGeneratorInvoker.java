package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.VectorFieldGenerator;

public class VectorFieldGeneratorInvoker extends View2DShrinkPanelPlugin {

	private VectorFieldGenerator generator;
	private MeshPlugin vectorDrawer;
	private JCheckBox showVectorsCheckbox;

	public VectorFieldGeneratorInvoker(VectorFieldGenerator generator, MeshPlugin vectorDrawer) {
		this.generator = generator;
		this.vectorDrawer = vectorDrawer;
		addGuiElements();
	}

	public void invokeGeneration() {
		/*
		 * TODO implement, for this, find out how to add buttons and react on
		 * e.g. buttonPushed events. To fulfill this task it might not be the
		 * best solution to have this invokeGeneration() method. Feel free to do
		 * it in a more suitable way. See below for reference
		 */
	}	
	
	private void addGuiElements() {
		final int ROWS = 2;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addRandomVectorFieldButton();
		showVectorsCheckbox = new JCheckBox(new DrawVectorsAction());
		showVectorsCheckbox.setText( "Show Vector Field");
		shrinkPanel.add(showVectorsCheckbox);
	}
	
	private void addRandomVectorFieldButton() {
		addButton(new RandomVectorFieldAction(), "Random VectorField");
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}
	class DrawVectorsAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			vectorDrawer.setDrawFieldVectors(showVectorsCheckbox.isSelected());
		}
		
	}
	
	class RandomVectorFieldAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			generator.generateRandomVectorField();
		}
	}
}
