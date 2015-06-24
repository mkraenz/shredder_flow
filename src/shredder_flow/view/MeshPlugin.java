package shredder_flow.view;

import shredder_flow.logic.MeshModel;
import de.jtem.jrworkspace.plugin.Plugin;

public class MeshPlugin extends Plugin {
	
	private MeshModel model;
	
	public void setModel(MeshModel model) {
		this.model = model;
	}

	public void draw(){
		drawVertices();
		drawEdges();
		drawFieldVectors();
	}

	private void drawVertices() {
		// TODO Auto-generated method stub
		
	}

	private void drawEdges() {
		// TODO Auto-generated method stub
		
	}

	private void drawFieldVectors() {
		// TODO Auto-generated method stub
		
	}

}
