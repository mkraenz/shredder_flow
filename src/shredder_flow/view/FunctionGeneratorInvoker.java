package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.FunctionGenerator;

public class FunctionGeneratorInvoker extends View2DShrinkPanelPlugin {

	private static final String RANDOM_VALUES = "Random Values";
	private FunctionGenerator generator;
	private JComboBox<String> functionsComboBox;
	private MeshPlugin valueDrawer;

	public FunctionGeneratorInvoker(FunctionGenerator generator, MeshPlugin valueDrawer) {
		this.generator = generator;
		this.valueDrawer = valueDrawer;
		addGuiElements();
	}

	public void invokeGeneration() {
		/*
		 * TODO implement, for this, find out how to add buttons and react on
		 * e.g. buttonPushed events. To fulfill this task it might not be the
		 * best solution to have this invokeGeneration() method. Feel free to do
		 * it in a more suitable way. See TriangulationInvoker.addGuiElements()
		 * for reference.
		 */
	}

	private void addGuiElements() {
		final int ROWS = 3;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addFunctionSelectBox();
		addButton(new ApplyFunctionAction(), "Apply");
	}

	private void addFunctionSelectBox() {
		functionsComboBox = new JComboBox<String>();
		functionsComboBox.addItem(RANDOM_VALUES);
		shrinkPanel.add(functionsComboBox);
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	class ApplyFunctionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(functionsComboBox.getSelectedItem() == RANDOM_VALUES){
				generator.generateRandomFunction(-10, 10);
			}
		}
	}
}
