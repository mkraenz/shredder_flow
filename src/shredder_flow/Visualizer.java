package shredder_flow;

import de.jtem.java2dx.modelling.DraggablePolygon2D;
import de.jtem.java2dx.plugin.Java2DViewer;

public class Visualizer {
	public static void main(String[] args) {
		Java2DViewer vr = new Java2DViewer();
		PolygonalLine polygonalLine = new PolygonalLine();
		vr.registerPlugin(polygonalLine);
		DraggablePolygon2D polygon = polygonalLine.getPolygon();
		DraggablePolygon2DTriangulator triangulator = new DraggablePolygon2DTriangulator();
		triangulator.triangulate(polygon);
		double[][] points = triangulator.getPoints();
		int[] indices = triangulator.getIndices();
		
		vr.startup();
		
	}
}
