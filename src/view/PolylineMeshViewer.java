package view;

import java.util.HashSet;

import javax.media.j3d.Shape3D;

import model.Point;
import model.Polyline;
import model.PolylineMesh;

public class PolylineMeshViewer extends HashSet<PolylineViewer>{
	private static final long serialVersionUID=1L;
  
	public Point centroid;
	
	public PolylineMeshViewer(PolylineMesh pm){
		for(Polyline poly: pm){
			this.add(new PolylineViewer(poly));
		}
		
		this.centroid=pm.getCentroid();
	}
	
	public Point getCentroid(){
		return this.centroid;
	}
	
	public HashSet<Shape3D> createPolylineShapes(){
		HashSet<Shape3D> shapeSet=new HashSet<Shape3D>();
		
		for(PolylineViewer polyline:this){
			shapeSet.add(polyline.display());
		}
		return shapeSet;
	}
}
