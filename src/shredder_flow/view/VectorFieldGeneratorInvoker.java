package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.vecmath.Vector2d;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.VectorFieldGenerator;
import shredder_flow.view.TriangulationInvoker.SetMaxTriangleAction;

public class VectorFieldGeneratorInvoker extends View2DShrinkPanelPlugin {

	private static final String SYMPLECTIC_FIELD = "Symplectic Field";
	private static final String RANDOM_FIELD = "Random Field";
	private static final String GRADIENT_FIELD = "Gradient Field";
	private static final String AFUNCTION_FIELD = "A-Function Field";
	private static final String BFUNCTION_FIELD = "B-Function Field";
	private static final String CFUNCTION_FIELD = "C-Function Field";
	private static final String DFUNCTION_FIELD = "D-Function Field";
	private static final String EFUNCTION_FIELD = "E-Function Field";
	private static final String FFUNCTION_FIELD = "F-Function Field";
	private static final String GFUNCTION_FIELD = "G-Function Field";
	private static final String HFUNCTION_FIELD = "H-Function Field";
	private static final String IFUNCTION_FIELD = "I-Function Field";
	private static final String JFUNCTION_FIELD = "J-Function Field";
	private VectorFieldGenerator generator;
	private JComboBox<String> fieldComboBox;
	private JTextField iXComponentTextField;
	private JTextField jXComponentTextField;
	private JTextField iYComponentTextField;
	private JTextField jYComponentTextField;
	private double iXComponent;
	private double jXComponent;
	private double iYComponent;
	private double jYComponent;
	private Vector2d i;
	private Vector2d j;




	public VectorFieldGeneratorInvoker(VectorFieldGenerator generator) {
		this.generator = generator;
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
		final int ROWS = 6;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addFunctionSelectBox();
		addButton(new ApplyFunctionAction(), "Apply");
		addFunctionOptBox();
	}
	
	private void addFunctionOptBox() {
		iXComponentTextField = new JTextField("1");
		iYComponentTextField = new JTextField("1");
		jXComponentTextField = new JTextField("0");
		jYComponentTextField = new JTextField("0");

		shrinkPanel.add(new JLabel("x component of i:"));
		shrinkPanel.add(iXComponentTextField);
		shrinkPanel.add(new JLabel("y component of i:"));
		shrinkPanel.add(iYComponentTextField);
		shrinkPanel.add(new JLabel("x component of j:"));
		shrinkPanel.add(jXComponentTextField);
		shrinkPanel.add(new JLabel("y component of j:"));
		shrinkPanel.add(jYComponentTextField);
		
	}

	private void addFunctionSelectBox() {
		fieldComboBox = new JComboBox<String>();
		shrinkPanel.add(fieldComboBox);
		
		fieldComboBox.addItem(RANDOM_FIELD);
		fieldComboBox.addItem(GRADIENT_FIELD);
		fieldComboBox.addItem(SYMPLECTIC_FIELD);
		fieldComboBox.addItem(AFUNCTION_FIELD);
		fieldComboBox.addItem(BFUNCTION_FIELD);
		fieldComboBox.addItem(CFUNCTION_FIELD);
		fieldComboBox.addItem(DFUNCTION_FIELD);
		fieldComboBox.addItem(EFUNCTION_FIELD);
		fieldComboBox.addItem(FFUNCTION_FIELD);
		fieldComboBox.addItem(GFUNCTION_FIELD);
		fieldComboBox.addItem(HFUNCTION_FIELD);
		fieldComboBox.addItem(IFUNCTION_FIELD);
		fieldComboBox.addItem(JFUNCTION_FIELD);
		
	}
	
	public void read(){
		try {
			iXComponent = Double.parseDouble(iXComponentTextField
					.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given iXComponent to integer.");
		}
		try {
			iYComponent = Double.parseDouble(iYComponentTextField
					.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given iYComponent to integer.");
		}
		try {
			jXComponent = Double.parseDouble(jXComponentTextField
					.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given jXComponent to integer.");
		}
		try {
			jYComponent = Double.parseDouble(jYComponentTextField
					.getText());
		} catch (Exception e2) {
			System.out
					.println("WARNING: Could not convert given jXComponent to integer.");
		}
		i = new Vector2d(iXComponent,iYComponent);
		j = new Vector2d(jXComponent,jYComponent);
	}
	
	class ApplyFunctionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(fieldComboBox.getSelectedItem() == RANDOM_FIELD){
				generator.generateRandomVectorField();
			}
			if(fieldComboBox.getSelectedItem() == GRADIENT_FIELD){
				generator.generateGradiantField();
			}
			if(fieldComboBox.getSelectedItem() == SYMPLECTIC_FIELD){
				generator.generateSymplecticVectorField();
			}
			if(fieldComboBox.getSelectedItem() == AFUNCTION_FIELD){
				read();
				generator.generateAVectorField(i, j);
			}
			if(fieldComboBox.getSelectedItem() == BFUNCTION_FIELD){
				read();
				generator.generateBVectorField(i, j);
			}
			if(fieldComboBox.getSelectedItem() == CFUNCTION_FIELD){
				read();
				generator.generateCVectorField(i, j);
			}
			if(fieldComboBox.getSelectedItem() == DFUNCTION_FIELD){
				read();
				generator.generateDVectorField(i, j);
			}
			if(fieldComboBox.getSelectedItem() == EFUNCTION_FIELD){
				read();
				generator.generateEVectorField(i, j);
			}
			if(fieldComboBox.getSelectedItem() == FFUNCTION_FIELD){
				read();
				generator.generateFVectorField(i, j);
			}
			if(fieldComboBox.getSelectedItem() == GFUNCTION_FIELD){
				read();
				generator.generateGVectorField(i, j);
			}
			if(fieldComboBox.getSelectedItem() == HFUNCTION_FIELD){
				read();
				generator.generateHVectorField(i);
			}
			if(fieldComboBox.getSelectedItem() == IFUNCTION_FIELD){
				read();
				generator.generateIVectorField(j);
			}
			if(fieldComboBox.getSelectedItem() == JFUNCTION_FIELD){
				read();
				generator.generateAVectorField(i, j);
			}
		}
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}
	
	class RandomVectorFieldAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			generator.generateRandomVectorField();
		}
	}
}
