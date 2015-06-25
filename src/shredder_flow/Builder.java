package shredder_flow;

import shredder_flow.logic.DraggablePolygon2DAdapter;
import shredder_flow.logic.FunctionGenerator;
import shredder_flow.logic.MeshModel;
import shredder_flow.logic.VectorFieldGenerator;
import shredder_flow.view.FunctionGeneratorInvoker;
import shredder_flow.view.MeshPlugin;
import shredder_flow.view.TriangulationInvoker;
import shredder_flow.view.VectorFieldGeneratorInvoker;
import de.jtem.discreteCurves.SubdividedPolygonPlugin;
import de.jtem.java2dx.plugin.Java2DViewer;

public class Builder {

	public void buildAndRegister(Java2DViewer viewer) {
		SubdividedPolygonPlugin subdividedPolygonPlugin = new SubdividedPolygonPlugin();
		viewer.registerPlugin(subdividedPolygonPlugin);
		DraggablePolygon2DAdapter polygon2DAdapter = new DraggablePolygon2DAdapter(
				subdividedPolygonPlugin);

		setMeshRelatedPlugins(viewer, polygon2DAdapter);
		setFunctionGeneratorPlugin(viewer);
		setVectorFieldGeneratorPlugin(viewer);
	}

	private void setVectorFieldGeneratorPlugin(Java2DViewer viewer) {
		VectorFieldGeneratorInvoker vectorFieldGeneratorInvoker = new VectorFieldGeneratorInvoker(
				new VectorFieldGenerator());
		viewer.registerPlugin(vectorFieldGeneratorInvoker);
	}

	private void setFunctionGeneratorPlugin(Java2DViewer viewer) {
		FunctionGeneratorInvoker functionGeneratorInvoker = new FunctionGeneratorInvoker(
				new FunctionGenerator());
		viewer.registerPlugin(functionGeneratorInvoker);
	}

	private void setMeshRelatedPlugins(Java2DViewer viewer,
			DraggablePolygon2DAdapter polygon2DAdapter) {
		MeshModel meshModel = new MeshModel();
		TriangulationInvoker triangulationInvoker = new TriangulationInvoker(
				meshModel, polygon2DAdapter);
		viewer.registerPlugin(triangulationInvoker);

		MeshPlugin meshPlugin = new MeshPlugin();
		meshPlugin.setModel(meshModel);
		viewer.registerPlugin(meshPlugin);
	}

	public static void main(String[] args) {
		Java2DViewer viewer = new Java2DViewer();
		Builder builder = new Builder();
		builder.buildAndRegister(viewer);
		viewer.startup();
	}
}
