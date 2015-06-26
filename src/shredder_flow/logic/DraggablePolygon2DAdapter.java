package shredder_flow.logic;

import de.jtem.discreteCurves.SubdividedPolygonPlugin;
import de.jtem.java2dx.Point2DDouble;
import de.jtem.java2dx.Point2DList;
import de.jtem.java2dx.Polygon2D;
import de.jtem.java2dx.modelling.SubdividedPolygon2D;

public class DraggablePolygon2DAdapter {

	private SubdividedPolygonPlugin plugin;

	public DraggablePolygon2DAdapter(SubdividedPolygonPlugin plugin) {
		this.plugin = plugin;
	}

	public Boundary getBoundary(){
		SubdividedPolygon2D polygon = plugin.getSubdividedPolygon();
		Polygon2D polygonModel = polygon.getModel();
		Boundary boundary = point2DListToBounary(polygonModel);
		return boundary;
	}

	public Boundary point2DListToBounary(Point2DList controlPointList) {
		Boundary boundary = new Boundary();
		for (Point2DDouble point : controlPointList) {
			Vertex vertex = new Vertex(point.x, point.y);
			boundary.add(vertex);
		}
		return boundary;
	}
}
