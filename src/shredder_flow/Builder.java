package shredder_flow;

import javax.swing.Timer;

import shredder_flow.logic.DraggablePolygon2DAdapter;
import shredder_flow.logic.FunctionGenerator;
import shredder_flow.logic.MeshModel;
import shredder_flow.logic.ParticleCreator;
import shredder_flow.logic.ParticleList;
import shredder_flow.logic.ParticleUpdater;
import shredder_flow.logic.TriangleList;
import shredder_flow.logic.TriangulationVertexList;
import shredder_flow.logic.VectorFieldGenerator;
import shredder_flow.view.FunctionGeneratorInvoker;
import shredder_flow.view.MeshPlugin;
import shredder_flow.view.ParticlePlugin;
import shredder_flow.view.ParticleUpdateInvoker;
import shredder_flow.view.TriangulationInvoker;
import shredder_flow.view.VectorFieldGeneratorInvoker;
import de.jtem.discreteCurves.SubdividedPolygonPlugin;
import de.jtem.java2dx.plugin.Java2DViewer;

public class Builder {

	public void buildAndRegister(Java2DViewer viewer) {
		int UPDATES_PER_SECOND = 60;
		
		ParticleList particles = new ParticleList();
		TriangleList triangles = new TriangleList();
		TriangulationVertexList vertices = new TriangulationVertexList();
		
		SubdividedPolygonPlugin subdividedPolygonPlugin = new SubdividedPolygonPlugin();
		viewer.registerPlugin(subdividedPolygonPlugin);
		DraggablePolygon2DAdapter polygon2DAdapter = new DraggablePolygon2DAdapter(
				subdividedPolygonPlugin);

		setMeshRelatedPlugins(viewer, polygon2DAdapter, triangles, vertices);
		setFunctionGeneratorPlugin(viewer, vertices);
		setVectorFieldGeneratorPlugin(viewer, triangles);
		setParticlePlugin(viewer, particles, triangles);
		setParticleUpdaterPlugin(viewer, UPDATES_PER_SECOND, particles);
	}

	private void setParticleUpdaterPlugin(Java2DViewer viewer, int UPDATES_PER_SECOND,
			ParticleList particles) {
		Timer updateTimer = new Timer(0, null); // TODO maybe bind the timer to the viewers timer
		ParticleUpdater particleUpdater = new ParticleUpdater(particles, UPDATES_PER_SECOND, updateTimer);
		ParticleUpdateInvoker particleUpdateInvoker = new ParticleUpdateInvoker(particleUpdater);
		viewer.registerPlugin(particleUpdateInvoker);
	}

	private void setParticlePlugin(Java2DViewer viewer, ParticleList particles, TriangleList triangles) {
		ParticlePlugin particlePlugin = new ParticlePlugin(new ParticleCreator(particles, triangles), particles);
		viewer.registerPlugin(particlePlugin);
	}

	private void setVectorFieldGeneratorPlugin(Java2DViewer viewer, TriangleList triangles) {
		VectorFieldGeneratorInvoker vectorFieldGeneratorInvoker = new VectorFieldGeneratorInvoker(
				new VectorFieldGenerator(triangles));
		viewer.registerPlugin(vectorFieldGeneratorInvoker);
	}

	private void setFunctionGeneratorPlugin(Java2DViewer viewer, TriangulationVertexList vertices) {
		FunctionGeneratorInvoker functionGeneratorInvoker = new FunctionGeneratorInvoker(
				new FunctionGenerator(vertices));
		viewer.registerPlugin(functionGeneratorInvoker);
	}

	private void setMeshRelatedPlugins(Java2DViewer viewer,
			DraggablePolygon2DAdapter polygon2DAdapter, TriangleList triangles, TriangulationVertexList vertices) {
		MeshModel meshModel = new MeshModel(triangles, vertices);
		TriangulationInvoker triangulationInvoker = new TriangulationInvoker(
				meshModel, polygon2DAdapter);
		viewer.registerPlugin(triangulationInvoker);

		MeshPlugin meshPlugin = new MeshPlugin(meshModel);
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
