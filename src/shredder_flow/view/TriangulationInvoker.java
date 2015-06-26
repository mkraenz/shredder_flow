package shredder_flow.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.DraggablePolygon2DAdapter;
import shredder_flow.logic.MeshModel;
import shredder_flow.logic.RuppertAdapter;

public class TriangulationInvoker extends View2DShrinkPanelPlugin {

	private MeshModel model;
	private DraggablePolygon2DAdapter polygonAdapter;
	private MeshPlugin meshPlugin;

	public TriangulationInvoker(MeshModel model,
			DraggablePolygon2DAdapter polygonAdapter, MeshPlugin drawerPlugin) {
		this.model = model;
		this.polygonAdapter = polygonAdapter;
		this.meshPlugin = drawerPlugin;
		addGuiElements();
	}

	private void addGuiElements() {
		final int ROWS = 2;
		final int COLUMNS = 2;
		shrinkPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		addTriangulateButton();
		addResetButton();
	}

	private void addResetButton() {
		final JButton triangulateButton = new JButton();
		triangulateButton.setAction(new ResetTriangulationAction());
		shrinkPanel.add(triangulateButton);
		triangulateButton.setText("Reset Triangulation");
	}

	private void addTriangulateButton() {
		final JButton triangulateButton = new JButton();
		triangulateButton.setAction(new TriangulateAction());
		shrinkPanel.add(triangulateButton);
		triangulateButton.setText("Triangulate");
	}

	class TriangulateAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			invokeTriangulation();
			meshPlugin.draw();
		}
	}

	class ResetTriangulationAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO reset triangulation properly
			model.getVertices().clear();
			model.getTriangles().clear();
			meshPlugin.draw();
		}
	}

	public void invokeTriangulation() {
		RuppertAdapter ruppertAdapter = new RuppertAdapter(model.getVertices(),
				model.getTriangles());
		ruppertAdapter.triangulate(polygonAdapter.getBoundary());
	}
}
