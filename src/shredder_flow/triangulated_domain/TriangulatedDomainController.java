package shredder_flow.triangulated_domain;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.List;

import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.Line2DDouble;
import de.jtem.java2dx.Point2DDouble;
import de.jtem.java2dx.plugin.Java2DView;
import de.jtem.jrworkspace.plugin.Controller;
import de.jtem.jrworkspace.plugin.Plugin;

public class TriangulatedDomainController extends Plugin {
	private ITriangulatedDomainModel model;

	public TriangulatedDomainController(ITriangulatedDomainModel model) {
		this.model = model;
	}

	private SceneComponent getSceneComponent() {
		SceneComponent sceneComponent = new SceneComponent();
		double[][] points = model.getPoints();
		int[] indices = model.getIndices();
		addPointsToSceneComponent(sceneComponent, points);
		addEdgesToSceneComponent(sceneComponent, points, indices);

		sceneComponent.addChild(model.getSceneComponentOfBoundaryPolygon());
		return sceneComponent;
	}

	private void addEdgesToSceneComponent(SceneComponent sceneComponent,
			double[][] points, int[] indices) {
		for (int i = 0; i < indices.length / 3; i++) {
			SceneComponent edgeSceneComponent1 = createEdgeSceneComponent(
					3 * i, 3 * i + 1, points, indices);
			SceneComponent edgeSceneComponent2 = createEdgeSceneComponent(
					3 * i + 1, 3 * i + 2, points, indices);
			SceneComponent edgeSceneComponent3 = createEdgeSceneComponent(
					3 * i + 2, 3 * i, points, indices);

			sceneComponent.addChild(edgeSceneComponent1);
			sceneComponent.addChild(edgeSceneComponent2);
			sceneComponent.addChild(edgeSceneComponent3);
		}
	}

	private SceneComponent createEdgeSceneComponent(int i, int j,
			double[][] points, int[] indices) {
		SceneComponent edgeSceneComponent1 = new SceneComponent();
		Line2DDouble edge1 = new Line2DDouble(points[indices[i]][0],
				points[indices[i]][1], points[indices[j]][0],
				points[indices[j]][1]);

		edgeSceneComponent1.setShape(edge1);
		return edgeSceneComponent1;
	}

	private void addPointsToSceneComponent(SceneComponent sceneComponent,
			double[][] points) {
		List<Double> sceneComponentPointSet = sceneComponent.getPoints();
		for (int i = 0; i < points.length; i++) {
			sceneComponentPointSet.add(new Point2DDouble(points[i][0],
					points[i][1]));
		}
		sceneComponent.setPointPaint(Color.blue);
	}

	@Override
	public void install(Controller c) throws Exception {
		SceneComponent root = c.getPlugin(Java2DView.class).getViewer2D()
				.getRoot();

		root.addChild(getSceneComponent());
		super.install(c);
	}

	public void setModel(ITriangulatedDomainModel model) {
		this.model = model;
	}
}
