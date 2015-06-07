package shredder_flow;

import de.jtem.discreteCurves.tutorial.DraggablePolygonPlugin;
import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.Point2DList;
import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;
import de.jtem.java2dx.plugin.Java2DView;
import de.jtem.java2dx.plugin.Java2DViewer;
import de.jtem.jrworkspace.plugin.Controller;
import de.jtem.jrworkspace.plugin.Plugin;
import de.jtem.numericalMethods.geometry.meshGeneration.ruppert.Ruppert;

public class PolygonalLine extends Plugin {

	@Override
	public void install(Controller c) throws Exception {

		DraggablePolygon2D polygon = new DraggablePolygon2D(10,
				DraggablePoint2DList.CIRCULAR, 0, 0, 1, true, true);
		// add the circle scene to the scene root
		SceneComponent root = c.getPlugin(Java2DView.class).getViewer2D()
				.getRoot();
		// add scene that contains the polygon
		root.addChild(polygon.getViewScene());
		// add the control scene
		root.addChild(polygon.getControlScene());

		super.install(c);
	}

	public double[] triangulate(DraggablePolygon2D polygon2d) {

		DraggablePoint2DList listOfContolPoints = polygon2d.getControlPoints();
		Point2DList pointList = listOfContolPoints.getModel();
		double[][] boundaryPoints = pointList.toDoubleArray();
		Ruppert ruppert = new Ruppert(boundaryPoints);
		ruppert.getIndices();
		double[] points = ruppert.getPoints();
		return points;
	}

	public double[][] rearrange(double[] points) {
		double[][] newPoints = new double[points.length / 2][2];
		for (int i = 0; i < points.length; i = i + 2) {
			newPoints[i][0] = points[i];
			newPoints[i][1] = points[i + 1];
		}
		return newPoints;
	}

	public static void main(String[] args) {
		Java2DViewer vr = new Java2DViewer();
		vr.registerPlugin(new PolygonalLine());
		vr.startup();
	}

}
