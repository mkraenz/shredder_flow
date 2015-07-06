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
	private double minimalAngleConstraint;
	private double maximalAreaConstraint;
	private JTextField maxTriangleTextField;
	private RuppertAdapter ruppertAdapter;
	private JTextField minAngleOfTriangleTextField;
	private JTextField maxAreaTextField;

	public TriangulationInvoker(MeshModel model,
			DraggablePolygon2DAdapter polygonAdapter) {
		this.model = model;
		this.polygonAdapter = polygonAdapter;
		ruppertAdapter = new RuppertAdapter(model.getVertices(),
				model.getTriangles());
		addGuiElements();
	}

	private void addGuiElements() {
		final int ROWS = 14;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addButton(new TriangulateAction(), "Triangulate");
		addButton(new ResetTriangulationAction(), "Reset Triangulation");
		addTriangleOptButton();
	}

	private void addTriangleOptButton() {
		maxTriangleTextField = new JTextField(String.valueOf(ruppertAdapter
				.getMaximalTriangleNumber()));
		minAngleOfTriangleTextField = new JTextField(
				String.valueOf(ruppertAdapter.getMinimalAngleConstraint()));
		maxAreaTextField = new JTextField(String.valueOf(ruppertAdapter
				.getMaximalAreaConstraint()));

		shrinkPanel.add(new JLabel("Max Number of Triangle:"));
		shrinkPanel.add(maxTriangleTextField);
		addButton(new SetMaxTriangleAction(), "Set new maximum number of triangle");
		shrinkPanel.add(new JLabel("Minimum Angle:"));
		shrinkPanel.add(minAngleOfTriangleTextField);
		addButton(new SetMinAngleTriangleAction(),
				"Set new minimum angle");
		shrinkPanel.add(new JLabel("Maximal Area of Triangles:"));
		shrinkPanel.add(maxAreaTextField);
		addButton(new SetMaxAreaAction(), "Set maximum area of triangle");

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
				ruppertAdapter.setMaximalTriangleNumber(maximalTriangleNumber);
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given maxTriangleNumber to integer.");
			}
		}
	}

	class SetMinAngleTriangleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				minimalAngleConstraint = Double.parseDouble(minAngleOfTriangleTextField.getText());
				ruppertAdapter.setMinimalAngleConstraint(minimalAngleConstraint);
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given minTriangleAngleNumber to double.");
			}
		}
	}

	class SetMaxAreaAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				maximalAreaConstraint = Double.parseDouble(maxAreaTextField
						.getText());
				ruppertAdapter.setMaximalAreaConstraint(maximalAreaConstraint);
			} catch (Exception e2) {
				System.out
						.println("WARNING: Could not convert given maxAreaOfTriangle to double.");
			}
		}
	}
}
