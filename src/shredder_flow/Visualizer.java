package shredder_flow;

import de.jtem.java2dx.plugin.Java2DViewer;

public class Visualizer {
	public static void main(String[] args) {		
		int BOUNDARY_CONTROL_POINT_COUNT = 20;
		TriangulatedDomainModel model = new TriangulatedDomainModel(BOUNDARY_CONTROL_POINT_COUNT);
		TriangulatedDomainController controller = new TriangulatedDomainController(model);
		
		Java2DViewer vr = new Java2DViewer();
		vr.registerPlugin(controller);
		vr.startup();
	}
}
