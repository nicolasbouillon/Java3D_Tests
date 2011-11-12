package view;


import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import model.Polyline;


public class PolylineView {
	private Polyline polyline;
	private boolean selected;
	
	public PolylineView(Polyline poly){
		this.polyline=poly;
	}
	
	public Polyline getPolyline(){
		return this.polyline;
	}
	
	public void setPolyline(Polyline polylineIn){
		this.polyline=polylineIn;
	}
	
	public boolean isSelected(){
		return this.selected;
	}
	
	public void setSelected(boolean selectedIn){
		this.selected=selectedIn;
	}
	
	public Shape3D display(){
		Shape3D shape=new Shape3D();
		int count=this.polyline.getEdgeList().size();
		TriangleArray[] triangles1=new TriangleArray[count-2];
		TriangleArray[] triangles2=new TriangleArray[count-2];
		for(int i=1;i<count-1;i++){
			triangles1[i-1]=new TriangleArray(3,TriangleArray.COORDINATES|TriangleArray.COLOR_3);
		    triangles2[i-1]=new TriangleArray(3,TriangleArray.COORDINATES|TriangleArray.COLOR_3);
		    
		    Point3d pointa=new Point3d(this.polyline.getPointList().get(0).getX(),
		    		                   this.polyline.getPointList().get(0).getY(),
		    		                   this.polyline.getPointList().get(0).getZ());
		    Point3d pointb=new Point3d(this.polyline.getPointList().get((i)%count).getX(),
	                                   this.polyline.getPointList().get((i)%count).getY(),
	                                   this.polyline.getPointList().get((i)%count).getZ());
		    Point3d pointc=new Point3d(this.polyline.getPointList().get((i+1)%count).getX(),
                                       this.polyline.getPointList().get((i+1)%count).getY(),
                                       this.polyline.getPointList().get((i+1)%count).getZ());
		    triangles1[i-1].setCoordinate(0,pointa);
		    triangles1[i-1].setCoordinate(1,pointb);
		    triangles1[i-1].setCoordinate(2,pointc);
		    triangles2[i-1].setCoordinate(0,pointb);
		    triangles2[i-1].setCoordinate(1,pointa);
		    triangles2[i-1].setCoordinate(2,pointc);
		    triangles1[i-1].setColor(0,new Color3f(1,0,0));
		    triangles1[i-1].setColor(1,new Color3f(1,0,0));
		    triangles1[i-1].setColor(2,new Color3f(1,0,0));
		    triangles2[i-1].setColor(0,new Color3f(1,0,0));
		    triangles2[i-1].setColor(1,new Color3f(1,0,0));
		    triangles2[i-1].setColor(2,new Color3f(1,0,0));
		    
		    shape.addGeometry(triangles1[i-1]);
		    shape.addGeometry(triangles2[i-1]);
		   
		    }
		
		 
		 return shape;
		
	}
	


}
