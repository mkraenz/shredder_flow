package shredder_flow;

import shredder_flow.logic.DraggablePolygon2DAdapter;
import de.jtem.discreteCurves.SubdividedPolygonPlugin;
import de.jtem.java2dx.plugin.Java2DViewer;

public class Builder {

	public void build(){
		Java2DViewer viewer = new Java2DViewer();
		
		SubdividedPolygonPlugin subdividedPolygonPlugin = new SubdividedPolygonPlugin();//TODO register
		DraggablePolygon2DAdapter polygon2DAdapter = new DraggablePolygon2DAdapter(subdividedPolygonPlugin);
		
		
		
		viewer.registerPlugin(subdividedPolygonPlugin);
		
		
		
		viewer.startup();
	}
	public static void main(String[] args) {
		Builder builder = new Builder();
		builder.build();
	}
}
