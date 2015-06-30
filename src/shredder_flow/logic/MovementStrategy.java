
package shredder_flow.logic;

import javax.vecmath.Vector2d;

public class MovementStrategy {
	
	private TriangleList triangles;

	public MovementStrategy(TriangleList triangles) {
		this.triangles = triangles;
	}
	
	public void setNextPositionAndTriangle(double deltaT, Particle particle) {
		// TODO Auto-generated method stub
		int i;
		int counter = 0;
		double lambda;
		double eps = 0.001;
		double distance;
		double triangletime;
		double[] intersectionpoint = new double[2];
		double[] posarray;
		double[] vecarray;
		double[] a;	
		double[] b;
		double velocity;
		boolean intersection;
		boolean overflow = false;
		Vector2d position;
		Triangle tri;
		VertexList vlist;
		FieldVector vec;

		double time = deltaT;
		while((time>0)&&(overflow==false)){
			position = particle.getPosition();
			posarray = getPositionArray(position);
			tri = particle.getTriangle();
			vlist = tri.getVertices();
			vec = tri.getFieldVector();
			vecarray = getFieldVectorArray(vec);
			velocity = vec.length();
			intersection = false;
			i = 0;
			while((intersection == false)&&(i<3)){
				a = getVertexLocArray(vlist,i);
				b = getVertexLocArray(vlist,(i+1)%3);
		    	lambda = getIntersection(a,b,posarray,vecarray);
		    	System.out.println(lambda);
				if((lambda<=1)&&(lambda>=0)){
					intersectionpoint = getIntersectionPoint(a,b,lambda);
						intersection = true;
				}
				else{
					i++;
				}
			}
			if(intersection==false){
				particle.setMovement(false);
				overflow=true;
			}
			distance = getDistance(posarray,intersectionpoint);
			triangletime = distance/velocity;
			
			if(triangletime>=time){
				particle.setPosition(posarray[0]+ vecarray[0]*time, posarray[1]+vecarray[1]*time);
				time = 0;
			}	
			else{
				particle.setTriangle(getNextTriangle(intersectionpoint,vecarray,eps));
				counter++;
				if(counter>=200){
					particle.setMovement(false);
					overflow = true;
				}
				particle.setPosition(intersectionpoint[0], intersectionpoint[1]);
				time = time - triangletime;		
			}			
		}			
	}
	private Triangle getNextTriangle(double[] intersectionpoint, double[] vecarray, double eps){
		return triangles.getTriangle(intersectionpoint[0]+eps*vecarray[0], intersectionpoint[1]+eps*vecarray[1]);
	}
	private double[] getFieldVectorArray(FieldVector vec){
		double[] vecarray = {vec.x, vec.y};
		return vecarray;
	}
	
	private double getDistance(double[]a, double[]b){
		return Math.sqrt(Math.pow(a[0]-b[0],2)+Math.pow(a[1]-b[1],2));
	}
	
	private double[] getPositionArray(Vector2d position){
		double[] pos = {position.x ,position.y};
		return pos;
	}
	
	private double getIntersection(double[]a, double[]b, double[]p, double[]v){
		return (a[0]*v[1]-a[1]*v[0]-p[0]*v[1]+p[1]*v[0])/(v[0]*(b[1]-a[1])-v[1]*(b[0]-a[0]));
	}
	private double[] getIntersectionPoint(double[]a, double[]b, double lambda){
		double[] point = new double[2];
		point[0]=a[0]+lambda*(b[0]-a[0]);
		point[1]=a[1]+lambda*(b[1]-a[1]);
		return point;
	}
	
	private double[] getVertexLocArray(VertexList vertices, int index){
		double[] locarray = new double[2];
		Vertex ver = vertices.get(index);
		locarray[0]=ver.getX();
		locarray[1]=ver.getY();
		return locarray;
	}
}
