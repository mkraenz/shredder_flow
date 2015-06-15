package shredder_flow;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.Ellipse2DDouble;
import de.jtem.java2dx.Point2DDouble;
import de.jtem.java2dx.modelling.DraggablePoint2DList;
import de.jtem.java2dx.modelling.DraggablePolygon2D;
import de.jtem.java2dx.plugin.Java2DView;
import de.jtem.jrworkspace.plugin.Controller;
import de.jtem.jrworkspace.plugin.Plugin;

public class TriangulatedDomainController extends Plugin {
	private TriangulatedDomainModel model;

	public TriangulatedDomainController(TriangulatedDomainModel model) {
		this.model = model;
	}

	private SceneComponent turnIntoDataThatTheJava2DViewerUnderstands() {
		double[][] points = model.getPoints();
		SceneComponent sceneComponent = new SceneComponent();
		List<Double> sceneComponentPointSet = sceneComponent.getPoints();
		for (int i = 0; i < points.length; i++) {
			sceneComponentPointSet.add(new Point2DDouble(points[i][0],
					points[i][1]));
		}
		sceneComponent.setPointPaint(Color.blue);

		return sceneComponent;

	}

	@Override
	public void install(Controller c) throws Exception {
		// add the circle scene to the scene root
		SceneComponent root = c.getPlugin(Java2DView.class).getViewer2D()
				.getRoot();
		// add scene that contains the polygon
		root.addChild(model.getBoundaryPolygon().getViewScene());
		// add the control scene
		root.addChild(model.getBoundaryPolygon().getControlScene());

		root.addChild(getSceneComponent());

		super.install(c);
	}

	public SceneComponent getSceneComponent() {
		return turnIntoDataThatTheJava2DViewerUnderstands();
	}

	public void setModel(TriangulatedDomainModel model) {
		this.model = model;
	}

}
