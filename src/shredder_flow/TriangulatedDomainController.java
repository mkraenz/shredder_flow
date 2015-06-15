package shredder_flow;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.Ellipse2DDouble;
import de.jtem.java2dx.Line2DDouble;
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
		SceneComponent sceneComponent = new SceneComponent();
		addPointsToSceneComponent(sceneComponent);
		addEdgesToSceneComponent(sceneComponent);
		return sceneComponent;
	}

	private void addEdgesToSceneComponent(SceneComponent sceneComponent) {
		int[] indices = model.getIndices();
		double[][] points = model.getPoints();

		for (int i = 0; i < indices.length / 3 - 1; i++) {
			SceneComponent edgeSceneComponent1 = new SceneComponent();
			SceneComponent edgeSceneComponent2 = new SceneComponent();
			SceneComponent edgeSceneComponent3 = new SceneComponent();
			Line2DDouble edge1 = new Line2DDouble(points[indices[3*i]][0],
					points[indices[3*i]][1], points[indices[3*i + 1]][0],
					points[indices[3*i + 1]][1]);
			Line2DDouble edge2 = new Line2DDouble(points[indices[3*i + 1]][0],
					points[indices[3*i + 1]][1], points[indices[3*i + 2]][0],
					points[indices[3*i + 2]][1]);
			Line2DDouble edge3 = new Line2DDouble(points[indices[3*i + 2]][0],
					points[indices[3*i + 2]][1], points[indices[3*i]][0],
					points[indices[3*i]][1]);

			edgeSceneComponent1.setShape(edge1);
			edgeSceneComponent2.setShape(edge2);
			edgeSceneComponent3.setShape(edge3);
			
			sceneComponent.addChild(edgeSceneComponent1);
			sceneComponent.addChild(edgeSceneComponent2);
//			sceneComponent.addChild(edgeSceneComponent3);
		}
	}

	private void addPointsToSceneComponent(SceneComponent sceneComponent) {
		double[][] points = model.getPoints();
		List<Double> sceneComponentPointSet = sceneComponent.getPoints();
		for (int i = 0; i < points.length; i++) {
			sceneComponentPointSet.add(new Point2DDouble(points[i][0],
					points[i][1]));
		}
		sceneComponent.setPointPaint(Color.blue);
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
