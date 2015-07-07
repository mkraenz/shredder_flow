package shredder_flow.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.FunctionGenerator;

public class FunctionGeneratorInvoker extends View2DShrinkPanelPlugin {

	private static final String RANDOM_VALUES = "Random Values";
	private static final String HANDWRITTEN_FUNCTION = "Handwritten Function";
	private static final String HANDWRITTEN_FUNCTION_SUM = "Handwritten Function Sum";
	private FunctionGenerator generator;
	private JComboBox<String> functionsComboBox;
	private JTextField alphaTextField;
	private JTextField xExponentTextField;
	private JTextField yExponentTextField;
	private JTextField constantTextField;
	private JTextField betaTextField;
	private JPanel handwrittenFunctionPannel;
	private JPanel handwrittenFunctionPannelSum;
	private JButton button;

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
		shrinkPanel.removeAll();
		final int ROWS = 4;
		final int COLUMNS = 1;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addFunctionSelectBox();
		addButton(new ApplyFunctionAction(), "Apply");
		handwrittenFunctionPannel = new JPanel();
		shrinkPanel.add(handwrittenFunctionPannel);
		handwrittenFunctionPannel.setMaximumSize(new Dimension(shrinkPanel
				.getWidth() - 5, shrinkPanel.getHeight() - 5));
		handwrittenFunctionPannelSum = new JPanel();
		shrinkPanel.add(handwrittenFunctionPannelSum);
		handwrittenFunctionPannelSum.setMaximumSize(new Dimension(shrinkPanel
				.getWidth() - 5, shrinkPanel.getHeight() - 5));
	}

	private void addFunctionSelectBox() {
		functionsComboBox = new JComboBox<String>();
		// functionsComboBox.removeAll();
		functionsComboBox.addItem(RANDOM_VALUES);
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION);
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION_SUM);
		shrinkPanel.add(functionsComboBox);
	}

	private void addGuiElementsForHandwrittenFunction() {
		shrinkPanel.removeAll();
		final int ROWS = 4;
		final int COLUMNS = 1;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addFunctionSelectBoxFunction();
		addButton(new ApplyFunctionAction(), "Apply");
		handwrittenFunctionPannel = new JPanel();
		shrinkPanel.add(handwrittenFunctionPannel);
		handwrittenFunctionPannel.setMaximumSize(new Dimension(shrinkPanel
				.getWidth() - 5, shrinkPanel.getHeight() - 5));
		handwrittenFunctionPannelSum = new JPanel();
		shrinkPanel.add(handwrittenFunctionPannelSum);
		handwrittenFunctionPannelSum.setMaximumSize(new Dimension(shrinkPanel
				.getWidth() - 5, shrinkPanel.getHeight() - 5));
	}

	private void addFunctionSelectBoxFunction() {
		functionsComboBox = new JComboBox<String>();
		// functionsComboBox.removeAll();
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION);
		functionsComboBox.addItem(RANDOM_VALUES);
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION_SUM);
		shrinkPanel.add(functionsComboBox);
	}

	private void addGuiElementsForHandwrittenSumFunction() {
		shrinkPanel.removeAll();
		final int ROWS = 4;
		final int COLUMNS = 1;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addFunctionSelectBoxFunctionSum();
		addButton(new ApplyFunctionAction(), "Apply");
		handwrittenFunctionPannel = new JPanel();
		shrinkPanel.add(handwrittenFunctionPannel);
		handwrittenFunctionPannel.setMaximumSize(new Dimension(shrinkPanel
				.getWidth() - 5, shrinkPanel.getHeight() - 5));
		handwrittenFunctionPannelSum = new JPanel();
		shrinkPanel.add(handwrittenFunctionPannelSum);
		handwrittenFunctionPannelSum.setMaximumSize(new Dimension(shrinkPanel
				.getWidth() - 5, shrinkPanel.getHeight() - 5));
	}

	private void addFunctionSelectBoxFunctionSum() {
		functionsComboBox = new JComboBox<String>();
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION_SUM);
		functionsComboBox.addItem(RANDOM_VALUES);
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION);
		shrinkPanel.add(functionsComboBox);
	}

	private void addButton(AbstractAction action, String caption) {
		button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	private void buildFunction() {

		handwrittenFunctionPannel.removeAll();
		handwrittenFunctionPannelSum.removeAll();

		handwrittenFunctionPannel.add(new JLabel("f(x,y)="));
		alphaTextField = new JTextField("1.0");
		handwrittenFunctionPannel.add(alphaTextField);

		handwrittenFunctionPannel.add(new JLabel("*x^"));
		xExponentTextField = new JTextField("1.0");
		handwrittenFunctionPannel.add(xExponentTextField);

		handwrittenFunctionPannel.add(new JLabel("*y^"));
		yExponentTextField = new JTextField("1.0");
		handwrittenFunctionPannel.add(yExponentTextField);

		handwrittenFunctionPannel.add(new JLabel("+"));
		constantTextField = new JTextField("1.0");
		handwrittenFunctionPannel.add(constantTextField);

		addButton(new BuildFunctionAction(), "Apply");
	}

	private void buildFunctionWithSum() {

		handwrittenFunctionPannel.removeAll();
		handwrittenFunctionPannelSum.removeAll();

		handwrittenFunctionPannelSum.add(new JLabel("f(x,y)="));
		alphaTextField = new JTextField("1.0");
		handwrittenFunctionPannelSum.add(alphaTextField);

		handwrittenFunctionPannelSum.add(new JLabel("*x^"));
		xExponentTextField = new JTextField("1.0");
		handwrittenFunctionPannelSum.add(xExponentTextField);

		handwrittenFunctionPannelSum.add(new JLabel("+"));
		betaTextField = new JTextField("1.0");
		handwrittenFunctionPannelSum.add(betaTextField);

		handwrittenFunctionPannelSum.add(new JLabel("*y^"));
		yExponentTextField = new JTextField("1.0");
		handwrittenFunctionPannelSum.add(yExponentTextField);

		handwrittenFunctionPannelSum.add(new JLabel("+"));
		constantTextField = new JTextField("1.0");
		handwrittenFunctionPannelSum.add(constantTextField);

		addButton(new BuildFunctionSumAction(), "Apply");
	}

	private void readerAndExecutorforHandwrittenFunctions() {
		double alpha = 1;
		double xUp = 1;
		double yUp = 1;
		double constant = 1;
		try {
			alpha = Double.parseDouble(alphaTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given alpha to double.");
		}
		try {
			xUp = Double.parseDouble(xExponentTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given xUp to double.");
		}
		try {
			yUp = Double.parseDouble(yExponentTextField.getText());
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

	private void readerAndExecutorForHandwrittenSumFunction() {
		double alpha = 1;
		double xUp = 1;
		double beta = 1;
		double yUp = 1;
		double constant = 1;
		try {
			alpha = Double.parseDouble(alphaTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given alpha to double.");
		}
		try {
			xUp = Double.parseDouble(xExponentTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given xUp to double.");
		}
		try {
			beta = Double.parseDouble(betaTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given beta to double.");
		}
		try {
			yUp = Double.parseDouble(yExponentTextField.getText());
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
		generator
				.generateHandwrittenFuctionSum(alpha, xUp, beta, yUp, constant);
	}

	private void buttonRemoverAndFunctionBuilderForHandwrittenFunction() {
		shrinkPanel.remove(button);
		buildFunction();
	}
	
	private void buttonRemoverAndFunctionBuilderForHandwrittenFunctionSum() {
		shrinkPanel.remove(button);
		buildFunctionWithSum();
	}
	
	class ApplyFunctionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (functionsComboBox.getSelectedItem() == RANDOM_VALUES) {
				generator.generateRandomFunction(-10, 10);
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION) {
				buttonRemoverAndFunctionBuilderForHandwrittenFunction();
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION_SUM) {
				buttonRemoverAndFunctionBuilderForHandwrittenFunctionSum();
			}
		

		}

	}

	class BuildFunctionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		// @Override
		public void actionPerformed(ActionEvent e) {

			if (functionsComboBox.getSelectedItem() == RANDOM_VALUES) {
				addGuiElements();
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION) {
				readerAndExecutorforHandwrittenFunctions();
				buttonRemoverAndFunctionBuilderForHandwrittenFunction();
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION_SUM) {
				addGuiElementsForHandwrittenSumFunction();
				buttonRemoverAndFunctionBuilderForHandwrittenFunctionSum();
			}
		}

	}

	class BuildFunctionSumAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		// @Override
		public void actionPerformed(ActionEvent e) {

			if (functionsComboBox.getSelectedItem() == RANDOM_VALUES) {
				addGuiElements();
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION) {
				addGuiElementsForHandwrittenFunction();
				buttonRemoverAndFunctionBuilderForHandwrittenFunction();
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION_SUM) {
				readerAndExecutorForHandwrittenSumFunction();
				buttonRemoverAndFunctionBuilderForHandwrittenFunctionSum();
			}
		}

	}

}
