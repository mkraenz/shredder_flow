package shredder_flow.view;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.List;

import shredder_flow.logic.FieldVector;
import shredder_flow.logic.MeshModel;
import shredder_flow.logic.Triangle;
import shredder_flow.logic.TriangleList;
import shredder_flow.logic.TriangulationVertexList;
import shredder_flow.logic.Vertex;
import de.jtem.java2d.SceneComponent;
import de.jtem.java2dx.Point2DDouble;
import de.jtem.java2dx.plugin.Java2DView;
import de.jtem.jrworkspace.plugin.Controller;
import de.jtem.jrworkspace.plugin.Plugin;

public class MeshPlugin extends Plugin {

	private MeshModel model;
	private SceneComponent parentSceneComponent;
	private SceneComponent verticesSceneComponent;
	private SceneComponent edgesSceneComponent;
	private SceneComponent vectorsSceneComponent;

	public MeshPlugin(MeshModel model) {
		this.model = model;
		this.verticesSceneComponent = new SceneComponent();
		this.edgesSceneComponent = new SceneComponent();
		this.vectorsSceneComponent = new SceneComponent();
	}

	public void draw() {
		clear();

		drawVertices(verticesSceneComponent, model.getVertices());
		drawEdges(edgesSceneComponent, model.getTriangles());
		// TODO: draw field vectors
		// drawFieldVectors(vectorsSceneComponent, model.getTriangles());

		parentSceneComponent.addChild(verticesSceneComponent);
		parentSceneComponent.addChild(edgesSceneComponent);
		parentSceneComponent.addChild(vectorsSceneComponent);
	}

	private void clear() {
		verticesSceneComponent.removeAllChildren();
		verticesSceneComponent.getPoints().clear();
		edgesSceneComponent.removeAllChildren();
		vectorsSceneComponent.removeAllChildren();

	}

	private void drawVertices(SceneComponent sceneComponent,
			TriangulationVertexList vertices) {
		List<Double> scenePointSet = sceneComponent.getPoints();
		for (Vertex vertex : vertices) {
			scenePointSet.add(new Point2DDouble(vertex.getX(), vertex.getY()));
		}
		sceneComponent.setPointPaint(Color.blue); // TODO: proper color, size...
	}

	private void drawEdges(SceneComponent sceneComponent, TriangleList triangles) {
		for (Triangle triangle : triangles) {
			SceneComponent triangleSceneComponent = getTriangleEdgesSceneComponent(triangle);
			sceneComponent.addChild(triangleSceneComponent);
		}
	}

	private SceneComponent getTriangleEdgesSceneComponent(Triangle triangle) {
		SceneComponent triangleSceneComponent = new SceneComponent();

		Vertex vertex0 = triangle.getVertices().get(0);
		Vertex vertex1 = triangle.getVertices().get(1);
		Vertex vertex2 = triangle.getVertices().get(2);

		triangleSceneComponent
				.addChild(getEdgeSceneComponent(vertex0, vertex1));
		triangleSceneComponent
				.addChild(getEdgeSceneComponent(vertex1, vertex2));
		triangleSceneComponent
				.addChild(getEdgeSceneComponent(vertex2, vertex0));
		return triangleSceneComponent;
	}

	private SceneComponent getEdgeSceneComponent(Vertex vertex1, Vertex vertex2) {
		SceneComponent edgeSceneComponent = new SceneComponent();
		Edge edge1 = new Edge(vertex1, vertex2);
		edgeSceneComponent.setShape(edge1);
		return edgeSceneComponent;
	}

	private void drawFieldVectors(SceneComponent sceneComponent,
			TriangleList triangles) {
		for (Triangle triangle : triangles) {
			// TODO: if(triangle.getFieldVector().length() != 0)
			SceneComponent fieldVectorSceneComponent = getFieldVectorSceneComponent(triangle
					.getFieldVector());
			sceneComponent.addChild(fieldVectorSceneComponent);
		}
	}

	private SceneComponent getFieldVectorSceneComponent(FieldVector fieldVector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void install(Controller c) throws Exception {
		super.install(c);
		this.parentSceneComponent = c.getPlugin(Java2DView.class).getViewer2D()
				.getRoot();
	}

}
