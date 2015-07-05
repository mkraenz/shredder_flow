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
	private JTextField xUpTextField;
	private JTextField yUpTextField;
	private JTextField constantTextField;
	private JTextField betaTextField;
	private JLabel alphaLabel;
	private JLabel xUpLabel;
	private JLabel yUpLabel;
	private JLabel betaLabel;
	private JPanel HandrittenFctPannel;
	private JPanel HandrittenFctPannelSum;
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
		final int ROWS = 3;
		final int COLUMNS = 1;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addFunctionSelectBox();
		addButton(new ApplyFunctionAction(), "Apply");
		HandrittenFctPannel = new JPanel();
		shrinkPanel.add(HandrittenFctPannel);
		HandrittenFctPannel.setMaximumSize(new Dimension(shrinkPanel.getWidth()-5,shrinkPanel.getHeight()-5));
		HandrittenFctPannelSum = new JPanel();
		shrinkPanel.add(HandrittenFctPannelSum);
		HandrittenFctPannelSum.setMaximumSize(new Dimension(shrinkPanel.getWidth()-5,shrinkPanel.getHeight()-5));
	}

	private void addFunctionSelectBox() {
		functionsComboBox = new JComboBox<String>();
//		functionsComboBox.removeAll();
		functionsComboBox.addItem(RANDOM_VALUES);
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION);
		functionsComboBox.addItem(HANDWRITTEN_FUNCTION_SUM);
		shrinkPanel.add(functionsComboBox);
	}

	private void addButton(AbstractAction action, String caption) {
		button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	private void buildFunction(){
		
		
		HandrittenFctPannel.removeAll();
		HandrittenFctPannelSum.removeAll();

		
		HandrittenFctPannel.add(new JLabel("alpha:"));
		alphaTextField = new JTextField("1.0");
		HandrittenFctPannel.add(alphaTextField);
		
		HandrittenFctPannel.add(new JLabel("xUp:"));
		xUpTextField = new JTextField("1.0");
		HandrittenFctPannel.add(xUpTextField);
		
		HandrittenFctPannel.add(new JLabel("yUp:"));
		yUpTextField = new JTextField("1.0");
		HandrittenFctPannel.add(yUpTextField);
		
		HandrittenFctPannel.add(new JLabel("constant:"));
		constantTextField = new JTextField("1.0");
		HandrittenFctPannel.add(constantTextField);
		
		addButton(new BuildFunctio(), "BuildFunction in Form: alpha*X^xUp*Y^yUp+constant");
	}
	
private void buildFunctionWithSum(){
		
		HandrittenFctPannel.removeAll();
		HandrittenFctPannelSum.removeAll();
		
		HandrittenFctPannelSum.add(new JLabel("alpha:"));
		alphaTextField = new JTextField("1.0");
		HandrittenFctPannelSum.add(alphaTextField);
		
		HandrittenFctPannelSum.add(new JLabel("xUp:"));
		xUpTextField = new JTextField("1.0");
		HandrittenFctPannelSum.add(xUpTextField);
		
		HandrittenFctPannelSum.add(new JLabel("beta:"));
		betaTextField = new JTextField("1.0");
		HandrittenFctPannelSum.add(betaTextField);
		
		HandrittenFctPannelSum.add(new JLabel("yUp:"));
		yUpTextField = new JTextField("1.0");
		HandrittenFctPannelSum.add(yUpTextField);
		
		HandrittenFctPannelSum.add(new JLabel("constant:"));
		constantTextField = new JTextField("1.0");
		HandrittenFctPannelSum.add(constantTextField);
		
		addButton(new BuildFunctioSum(), "BuildFunction in Form: alpha*X^xUp+beta*Y^yUp+constant");
	}
	
	private void performer() {
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
	
	private void performerSum() {
		double alpha = 1;
		double xUp = 1;
		double beta = 1;
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
			beta = Double.parseDouble(betaTextField.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given beta to double.");
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
		generator.generateHandwrittenFuctionSum(alpha, xUp, beta,yUp, constant);
	}
	
	class ApplyFunctionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(functionsComboBox.getSelectedItem() == RANDOM_VALUES){
				generator.generateRandomFunction(-10, 10);
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION){
				shrinkPanel.remove(button);
				buildFunction();
			}
			if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION_SUM){
				shrinkPanel.remove(button);
				buildFunctionWithSum();
			}

		}
	}
	class BuildFunctio extends AbstractAction {
		private static final long serialVersionUID = 1L;

//		@Override
		public void actionPerformed(ActionEvent e) {
//			performer();
//		}
		if(functionsComboBox.getSelectedItem() == RANDOM_VALUES){
			addGuiElements();		
			}
		if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION){
//			shrinkPanel.remove(button);
			performer();
			shrinkPanel.remove(button);
			buildFunction();
			}
		if(functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION_SUM){
			addGuiElements();
			shrinkPanel.remove(button);
			buildFunctionWithSum();		
			}
		}
		
	}
	
	class BuildFunctioSum extends AbstractAction {
		private static final long serialVersionUID = 1L;

//		@Override
		public void actionPerformed(ActionEvent e) {
//			performer();
//		}
		if(functionsComboBox.getSelectedItem() == RANDOM_VALUES){
//			System.out.println(HandrittenFctPannel.equals(null));
//			HandrittenFctPannelSum.removeAll();
//			generator.generateRandomFunction(-10, 10);
////			System.out.println("lol");
			addGuiElements();		
			}
		if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION){
			addGuiElements();
			shrinkPanel.remove(button);
////			performer();
			shrinkPanel.remove(button);
			buildFunction();
			}
		if (functionsComboBox.getSelectedItem() == HANDWRITTEN_FUNCTION_SUM){
//			shrinkPanel.remove(button);
			performerSum();
			shrinkPanel.remove(button);
			buildFunctionWithSum();
			}
		
		}
		
	}
	
}
