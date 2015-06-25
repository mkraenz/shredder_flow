package shredder_flow.logic;

import java.util.ArrayList;


public class TriangulationVertexList extends VertexList {

	private static final long serialVersionUID = 1L;

	public boolean hasTwoOrMoreEqualVertices(VertexList vertices) {
		ArrayList<Boolean> hasCommonVertex = new ArrayList<Boolean>();
		for(int i = 0; i< this.size(); i++){
			for( int j = i+1; j<vertices.size(); j++){
				if(this.get(i).isPositionEqual(vertices.get(j))){
					hasCommonVertex.add(true);
				}
				if(hasCommonVertex.size() == 2){
					return true;
				}
			}
		}
		return false;
	}

}
