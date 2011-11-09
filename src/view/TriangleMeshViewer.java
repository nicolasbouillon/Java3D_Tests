package view;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;

import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;

import model.Mesh;
import model.Point;
import model.Triangle;

public class TriangleMeshViewer extends HashSet<TriangleViewer> {

    private static final long serialVersionUID = 1L;

   
    public Point centroid;
    
    public TriangleMeshViewer(Mesh m) {
        for (Triangle tri : m) {
            this.add(new TriangleViewer(tri));
        }

        this.centroid=m.getCentroid();
       
    }

    public Point getCentroid() {
        return this.centroid;
    }
    

    public HashSet<Shape3D> createTriangleShapes() {

        HashSet<Shape3D> shapeSet = new HashSet<Shape3D>();

        for (TriangleViewer triangle : this) {
        	triangle.createShape3D();
        }

        return shapeSet;
    }
    
   ////////////Test pour ajouter tous les triangles dans un seul Shape
   public ArrayList<TriangleArray>  createTriangleArray(){
	   ArrayList<TriangleArray> triangleList=new ArrayList<TriangleArray>();
	   for(TriangleViewer triangle : this){
		   triangleList.add(triangle.createTriangle1());
		   triangleList.add(triangle.createTriangle2());   
	   }
	   return triangleList;
   }
   //////////////////////
}
