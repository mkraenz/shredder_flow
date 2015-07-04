package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.FunctionGenerator;

public class FunctionGeneratorInvoker extends View2DShrinkPanelPlugin {

	private static final String RANDOM_VALUES = "Random Values";
	private static final String HANDWRITTEN_FUNCTION = "Handwritten Function";
	private FunctionGenerator generator;
	private JComboBox<String> functionsComboBox;
	private JTextField alphaTextField;
	private JTextField xUpTextField;
	private JTextField yUpTextField;
	private JTextField constantTextField;

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
		functionsComboBox = new JComboBox<String>();
		functionsComboBox.addItem(RANDOM_VALUES);
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION);
		shrinkPanel.add(functionsComboBox);
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	private void buildFunction(){
		alphaTextField = new JTextField("1.0");
		
		shrinkPanel.add(new JLabel("alpha:"));
		shrinkPanel.add(alphaTextField);
		
		xUpTextField = new JTextField("1.0");
		
		shrinkPanel.add(new JLabel("xUp:"));
		shrinkPanel.add(xUpTextField);
		
		yUpTextField = new JTextField("1.0");
		
		shrinkPanel.add(new JLabel("yUp:"));
		shrinkPanel.add(yUpTextField);
		
		constantTextField = new JTextField("1.0");
		
		shrinkPanel.add(new JLabel("constant:"));
		shrinkPanel.add(constantTextField);
		
		addButton(new BuildFunctio(), "BuildFunction in Form: alpha*X^xUp*Y^yUp+constant");
	}
	
	class ApplyFunctionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(functionsComboBox.getSelectedItem() == RANDOM_VALUES){
				generator.generateRandomFunction(-10, 10);
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION){
				buildFunction();
			}
		}
	}
	class BuildFunctio extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			double alpha = 1;
			double xUp = 1;
			double yUp=1;
			double constant=1;
			try {
				alpha = Double.parseDouble(alphaTextField.getText());
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given alpha to double.");
			}
			try {
				xUp = Double.parseDouble(xUpTextField.getText());
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given xUp to double.");
			}
			try {
				yUp = Double.parseDouble(yUpTextField.getText());
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given yUp to double.");
			}
			try {
				constant = Double.parseDouble(constantTextField.getText());
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given constant to double.");
			}
			generator.generateHandwrittenFuction(alpha, xUp, yUp, constant);
		}
	}
}
