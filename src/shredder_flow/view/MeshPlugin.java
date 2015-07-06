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
		drawFieldVectors(vectorsSceneComponent, model.getTriangles());

		verticesSceneComponent.fireAppearanceChange();
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
			SceneComponent fieldVectorSceneComponent = getFieldVectorSceneComponent(triangle);
			sceneComponent.addChild(fieldVectorSceneComponent);
		}
	}

	private SceneComponent getFieldVectorSceneComponent(Triangle triangle) {
		// TODO Auto-generated method stub
		SceneComponent arrowSceneComponent = new SceneComponent();
		double length = getTriangleLength(triangle);
		//double[] normedFieldVec = getNormedFieldVector(triangle.getFieldVector(),length/8);
		double[] normedFieldVec ={length/4,length/4}; // for test generation
		double angle = Math.PI/4 -Math.atan2(normedFieldVec[1], normedFieldVec[0]);
		double s = Math.sin(angle)*length/12;
		double c = Math.cos(angle)*length/12;
		Vertex center = getTriangleCenter(triangle);
		Vertex root = getRootVertex(center, normedFieldVec);
		Vertex peak = getPeakVertex(center, normedFieldVec);
		Vertex right = new Vertex(peak.getX()-s, peak.getY()-c);
		Vertex left = new Vertex(peak.getX()-c, peak.getY()+s);
		arrowSceneComponent.addChild(getEdgeSceneComponent(root, peak));
		arrowSceneComponent.addChild(getEdgeSceneComponent(peak, left));
		arrowSceneComponent.addChild(getEdgeSceneComponent(peak, right));
		return arrowSceneComponent;
	}
	
	//returns length of smallest side of triangle
	private double getTriangleLength(Triangle triangle){
		Vertex vertex0 = triangle.getVertices().get(0);
		double x0 = vertex0.getX();
		double y0 = vertex0.getY();
		Vertex vertex1 = triangle.getVertices().get(1);
		double x1 = vertex1.getX();
		double y1 = vertex1.getY();
		Vertex vertex2 = triangle.getVertices().get(2);
		double x2 = vertex2.getX();
		double y2 = vertex2.getY();
		
		double a = Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
		double b = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		double c = Math.sqrt((x2-x0)*(x2-x0)+(y0-y2)*(y0-y2));
		return Math.min(Math.min(a, b),c);
	}

	private Vertex getRootVertex(Vertex center, double[] normedFieldVec){
			Vertex vertex = new Vertex(center.getX() - normedFieldVec[0], center.getY() - normedFieldVec[1]);
			return vertex;
	}

	private Vertex getPeakVertex(Vertex center, double[] normedFieldVec){
			Vertex vertex = new Vertex(center.getX() + normedFieldVec[0], center.getY() + normedFieldVec[1]);
			return vertex;
	}
	//return array of normed FieldVector with length = l
	private double[] getNormedFieldVector(FieldVector fieldVec, double l){
		double[] normedVector = new double[2];
		double length = Math.sqrt(fieldVec.x*fieldVec.x+fieldVec.y*fieldVec.y);
		normedVector[0]=(fieldVec.x/length)*l;
		normedVector[1]=(fieldVec.y/length)*l;
		return normedVector;
		
	}
	
	//return vertex of center
	private Vertex getTriangleCenter(Triangle triangle){
		Vertex vertex0 = triangle.getVertices().get(0);
		Vertex vertex1 = triangle.getVertices().get(1);
		Vertex vertex2 = triangle.getVertices().get(2);
		Vertex center = new Vertex((vertex0.getX()+vertex1.getX()+vertex2.getX())/3,(vertex0.getY()+vertex1.getY()+vertex2.getY())/3);
		return center;
	}
	@Override
	public void install(Controller c) throws Exception {
		super.install(c);
		SceneComponent parentSceneComponent = c.getPlugin(Java2DView.class).getViewer2D()
				.getRoot();
		parentSceneComponent.addChild(verticesSceneComponent);
		parentSceneComponent.addChild(edgesSceneComponent);
		parentSceneComponent.addChild(vectorsSceneComponent);
	}

}
