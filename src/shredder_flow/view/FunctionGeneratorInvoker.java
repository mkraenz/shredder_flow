package shredder_flow.view;

import de.jtem.java2dx.plugin.View2DShrinkPanelPlugin;
import shredder_flow.logic.FunctionGenerator;
import shredder_flow.logic.TriangulationVertexList;

public class FunctionGeneratorInvoker extends View2DShrinkPanelPlugin {

	private TriangulationVertexList vertices;
	private FunctionGenerator generator;

	public FunctionGeneratorInvoker(FunctionGenerator generator) {
		this.generator = generator;
	}

	public void setVertexList(TriangulationVertexList vertices) {
		this.vertices = vertices;
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
