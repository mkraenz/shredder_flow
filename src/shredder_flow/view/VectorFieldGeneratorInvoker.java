package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.VectorFieldGenerator;

public class VectorFieldGeneratorInvoker extends View2DShrinkPanelPlugin {

	private static final String SYMPLECTIC_FIELD = "Symplectic Field";
	private static final String RANDOM_FIELD = "Random Field";
	private static final String GRADIENT_FIELD = "Gradient Field";
	private VectorFieldGenerator generator;
	private MeshPlugin vectorDrawer;
	private JCheckBox showVectorsCheckbox;
	private JComboBox<String> fieldComboBox;

	public VectorFieldGeneratorInvoker(VectorFieldGenerator generator,
			MeshPlugin vectorDrawer) {
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
		addShowVectorsCheckbox();
		addFunctionSelectBox();
		addButton(new ApplyFunctionAction(), "Apply");
	}

	private void addShowVectorsCheckbox() {
		showVectorsCheckbox = new JCheckBox(new DrawVectorsAction());
		showVectorsCheckbox.setText("Show Vector Field");
		shrinkPanel.add(showVectorsCheckbox);
	}

	private void addFunctionSelectBox() {
		fieldComboBox = new JComboBox<String>();
		shrinkPanel.add(fieldComboBox);

		fieldComboBox.addItem(RANDOM_FIELD);
		fieldComboBox.addItem(GRADIENT_FIELD);
		fieldComboBox.addItem(SYMPLECTIC_FIELD);
	}

	class ApplyFunctionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (fieldComboBox.getSelectedItem() == RANDOM_FIELD) {
				generator.generateRandomVectorField();
			}
			if (fieldComboBox.getSelectedItem() == GRADIENT_FIELD) {
				generator.generateGradiantField();
			}
			if (fieldComboBox.getSelectedItem() == SYMPLECTIC_FIELD) {
				generator.generateSymplecticVectorField();
			}
		}
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	class DrawVectorsAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

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
