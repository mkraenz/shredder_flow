package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.FunctionGenerator;

public class FunctionGeneratorInvoker extends View2DShrinkPanelPlugin {

	private FunctionGenerator generator;

	public FunctionGeneratorInvoker(FunctionGenerator generator) {
		this.generator = generator;
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
		// TODO Auto-generated method stub

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
			// TODO
		}
	}
}
