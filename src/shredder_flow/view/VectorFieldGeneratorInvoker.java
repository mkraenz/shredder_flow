package shredder_flow.view;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.TriangleList;
import shredder_flow.logic.VectorFieldGenerator;

public class VectorFieldGeneratorInvoker extends View2DShrinkPanelPlugin {

	private VectorFieldGenerator generator;
	private TriangleList triangles;

	public void setTriangleList(TriangleList triangles) {
		this.triangles = triangles;
	}

	public VectorFieldGeneratorInvoker(VectorFieldGenerator generator) {
		this.generator = generator;
	}

	public void invokeGeneration() {
		/*
		 * TODO implement, for this, find out how to add buttons and react on
		 * e.g. buttonPushed events. To fulfill this task it might not be the
		 * best solution to have this invokeGeneration() method. Feel free to do
		 * it in a more suitable way
		 */
	}
}
