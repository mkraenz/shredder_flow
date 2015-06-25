package shredder_flow.view;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.DraggablePolygon2DAdapter;
import shredder_flow.logic.MeshModel;

public class TriangulationInvoker extends View2DShrinkPanelPlugin {

	private MeshModel model;
	private DraggablePolygon2DAdapter polygonAdapter;

	public TriangulationInvoker(MeshModel model,
			DraggablePolygon2DAdapter polygonAdapter) {
		this.model = model;
		this.polygonAdapter = polygonAdapter;
	}

	public void invokeTriangulation() {
		// TODO implement
	}
}
