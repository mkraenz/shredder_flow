package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.DraggablePolygon2DAdapter;
import shredder_flow.logic.MeshModel;
import shredder_flow.logic.RuppertAdapter;

public class TriangulationInvoker extends View2DShrinkPanelPlugin {

	private MeshModel model;
	private DraggablePolygon2DAdapter polygonAdapter;
	private int maximalTriangleNumber;
	private int minimalAngleConstraint;
	private JTextField maxTriangleTextField;
	private RuppertAdapter ruppertAdapter;
	private JTextField minAngleOfTriangleTextField;

	public TriangulationInvoker(MeshModel model,
			DraggablePolygon2DAdapter polygonAdapter) {
		this.model = model;
		this.polygonAdapter = polygonAdapter;
		ruppertAdapter = new RuppertAdapter(model.getVertices(),
				model.getTriangles());
		addGuiElements();
	}

	private void addGuiElements() {
		final int ROWS = 5;
		final int COLUMNS = 1;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addTriangleOptButton();
		addTriangulateButton();
		addResetButton();
	}

	private void addResetButton() {
		JButton resetButton = new JButton();
		resetButton.setAction(new ResetTriangulationAction());
		shrinkPanel.add(resetButton);
		resetButton.setText("Reset Triangulation");
	}

	private void addTriangulateButton() {
		final JButton triangulateButton = new JButton();
		triangulateButton.setAction(new TriangulateAction());
		shrinkPanel.add(triangulateButton);
		triangulateButton.setText("Triangulate");
	}

	private void addTriangleOptButton() {
		maxTriangleTextField = new JTextField("-1");
		minAngleOfTriangleTextField = new JTextField("30");

		shrinkPanel.add(new JLabel("Max Number of Triangle:"));
		shrinkPanel.add(maxTriangleTextField);
		shrinkPanel.add(new JLabel("Min Angle of Triangles:"));
		shrinkPanel.add(minAngleOfTriangleTextField);
		addButton(new SetMaxTriangleAction(), "Set new max number of triangle");
		addButton(new SetMinAngleTriangleAction(),
				"Set new min angle for triangle");
		addButton(new ResetMaxTriangleAction(), "Reset max number of triangle");
		addButton(new ResetMinAngleAction(), "Reset min angle of triangle");
	}

	private void addButton(AbstractAction action, String caption) {
		final JButton button = new JButton();
		button.setAction(action);
		shrinkPanel.add(button);
		button.setText(caption);
	}

	class TriangulateAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
			invokeTriangulation();
		}
	}

	class ResetTriangulationAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
		}

	}

	public void invokeTriangulation() {
		ruppertAdapter.setMinimalAngleConstraint(minimalAngleConstraint);
		ruppertAdapter.triangulate(polygonAdapter.getBoundary());
	}

	private void reset() {
		model.getVertices().clear();
		model.getTriangles().clear();
	}

	class SetMaxTriangleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				maximalTriangleNumber = Integer.parseInt(maxTriangleTextField
						.getText());
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given maxTriangleNumber to integer.");
			}
			ruppertAdapter.setMaximalTriangleNumber(maximalTriangleNumber);
		}
	}

	class SetMinAngleTriangleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				minimalAngleConstraint = Integer
						.parseInt(minAngleOfTriangleTextField.getText());
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given minTriangleAngleNumber to integer.");
			}
			ruppertAdapter.setMinimalAngleConstraint(minimalAngleConstraint);
		}
	}

	class ResetMinAngleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			ruppertAdapter.setMinimalAngleConstraint(30);
		}
	}

	class ResetMaxTriangleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			ruppertAdapter.setMaximalTriangleNumber(-1);
		}
	}

}
