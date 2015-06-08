package shredder_flow;

import de.jtem.jrworkspace.plugin.Plugin;

public class TriangulatedDomainController extends Plugin {
	private TriangulatedDomainModel model;
	
	public TriangulatedDomainController(TriangulatedDomainModel model){
		this.model = model;
	}
	private void turnIntoDataThatTheJava2DViewerUnderstands(){
		
	}
	public void setModel(TriangulatedDomainModel model) {
		this.model = model;
	}

}
