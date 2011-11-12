package view;

import java.util.HashSet;
import java.util.Set;

import javax.media.j3d.TriangleArray;

import model.Mesh;
import model.Point;
import model.Triangle;


public  class TriangleMeshViewer extends HashSet<TriangleViewer> {

//public class TriangleMeshViewer {


    public Point centroid;

    public Mesh mesh;

    public TriangleMeshViewer(Mesh m) {
        this.mesh = m;
        this.centroid = m.getCentroid();
    }

    public Point getCentroid() {
        return this.centroid;
    }

    // Test pour augmenter la capacite d'affichage
    public Set<TriangleArray> createTriangle() {
        Set<TriangleArray> triangleArrayList = new HashSet<>();

        for (Triangle triangle : this.mesh) {
            triangleArrayList.add(new TriangleArrayChild(triangle));
        }
        return triangleArrayList;
    }
    public TriangleArraySon createTriangleArraySon() {
       TriangleArraySon TAS=new TriangleArraySon(this.mesh);
       return TAS;
    }

    
   ////////////Test pour ajouter tous les triangles dans un seul Shape
//   public ArrayList<TriangleArray>  createTriangleArray(){
//	   ArrayList<TriangleArray> triangleList=new ArrayList<TriangleArray>();
//	   for(TriangleViewer triangle : this){
//		   triangleList.add(triangle.createTriangle1());
//		   triangleList.add(triangle.createTriangle2());   
//	   }
//	   return triangleList;
//   }
   //////////////////////
   
   //////////////////////test pour augmenter la capacite d'affichage
//   public TriangleArray createTriangle1(){
//	   TriangleArray triangle1=new TriangleArray(this.size()*3*2,GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);
//	   int i=0;
//	  
//	   for(TriangleViewer triangle :this){
//		  
////		   triangle1.setCoordinate(i,  triangle.getP1());
////		   triangle1.setCoordinate(i+1,triangle.getP2());
////		   triangle1.setCoordinate(i+2,triangle.getP3());
////		   triangle1.setCoordinate(i+3,triangle.getP2());
////		   triangle1.setCoordinate(i+4,triangle.getP1());
////		   triangle1.setCoordinate(i+5,triangle.getP3());
//
//			   triangle1.setCoordinate(i, new Point3d(triangle.getTriangle().getP1().getX(),
//					   triangle.getTriangle().getP1().getY(), triangle.getTriangle().getP1().getZ()));
//			   triangle1.setCoordinate(i+1, new Point3d(triangle.getTriangle().getP2().getX(),
//					   triangle.getTriangle().getP2().getY(), triangle.getTriangle().getP2().getZ()));
//			   triangle1.setCoordinate(i+2, new Point3d(triangle.getTriangle().getP3().getX(),
//					   triangle.getTriangle().getP3().getY(), triangle.getTriangle().getP3().getZ()));
//			   triangle1.setCoordinate(i+3, new Point3d(triangle.getTriangle().getP2().getX(),
//					   triangle.getTriangle().getP2().getY(), triangle.getTriangle().getP2().getZ()));
//			   triangle1.setCoordinate(i+4, new Point3d(triangle.getTriangle().getP1().getX(),
//					   triangle.getTriangle().getP1().getY(), triangle.getTriangle().getP1().getZ()));
//			   triangle1.setCoordinate(i+5, new Point3d(triangle.getTriangle().getP3().getX(),
//					   triangle.getTriangle().getP3().getY(), triangle.getTriangle().getP3().getZ()));
//			   
//			   triangle1.setNormal(i,   triangle.convertNormal(triangle.getTriangle()));
//			   triangle1.setNormal(i+1, triangle.convertNormal(triangle.getTriangle()));
//			   triangle1.setNormal(i+2, triangle.convertNormal(triangle.getTriangle()));
//			   triangle1.setNormal(i+3, triangle.convertOppositeNormal(triangle.getTriangle()));
//			   triangle1.setNormal(i+4, triangle.convertOppositeNormal(triangle.getTriangle()));
//			   triangle1.setNormal(i+5, triangle.convertOppositeNormal(triangle.getTriangle()));
//			   
//			
//			   
//			   i=i+6;
//   
//	   }
//	   return triangle1;
//   }
//   public TriangleArray createTriangle1(){
//	   TriangleArray triangle1=new TriangleArray(this.size()*3,GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);
//	   int i=0;
//	   Color3f color1=new Color3f(1,0,1);
//	   for(TriangleViewer triangle :this){
//		  
////		   triangle1.setCoordinate(i,  triangle.getP1());
////		   triangle1.setCoordinate(i+1,triangle.getP2());
////		   triangle1.setCoordinate(i+2,triangle.getP3());
////		   triangle1.setCoordinate(i+3,triangle.getP2());
////		   triangle1.setCoordinate(i+4,triangle.getP1());
////		   triangle1.setCoordinate(i+5,triangle.getP3());
//
//			   triangle1.setCoordinate(i, new Point3d(triangle.getTriangle().getP1().getX(),
//					   triangle.getTriangle().getP1().getY(), triangle.getTriangle().getP1().getZ()));
//			   triangle1.setCoordinate(i+1, new Point3d(triangle.getTriangle().getP2().getX(),
//					   triangle.getTriangle().getP2().getY(), triangle.getTriangle().getP2().getZ()));
//			   triangle1.setCoordinate(i+2, new Point3d(triangle.getTriangle().getP3().getX(),
//					   triangle.getTriangle().getP3().getY(), triangle.getTriangle().getP3().getZ()));
//			   
//			   triangle1.setNormal(i,   triangle.convertNormal(triangle.getTriangle()));
//			   triangle1.setNormal(i+1, triangle.convertNormal(triangle.getTriangle()));
//			   triangle1.setNormal(i+2, triangle.convertNormal(triangle.getTriangle()));
//			  
////			   triangle1.setColor(i,   color1);
////	           triangle1.setColor(i+1, color1);
////	           triangle1.setColor(i+2, color1);
//			   
//			  
//		      
//	           
//			   
//			   i=i+3;
//   
//	   }
//	   return triangle1;
//   }
   
//   public TriangleArray createTriangle2(){
//	   TriangleArray triangle2=new TriangleArray(this.size()*3,GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);
//	   int a=this.size();
//	   int i=0;
//	   Color3f color2=new Color3f(1,0,1);
//	   for(TriangleViewer triangle :this){
//		  
//
//
//			   triangle2.setCoordinate(i,   new Point3d(triangle.getTriangle().getP2().getX(),
//					   triangle.getTriangle().getP2().getY(), triangle.getTriangle().getP2().getZ()));
//			   triangle2.setCoordinate(i+1, new Point3d(triangle.getTriangle().getP1().getX(),
//					   triangle.getTriangle().getP1().getY(), triangle.getTriangle().getP1().getZ()));
//			   triangle2.setCoordinate(i+2, new Point3d(triangle.getTriangle().getP3().getX(),
//					   triangle.getTriangle().getP3().getY(), triangle.getTriangle().getP3().getZ()));
//			   
//			   triangle2.setNormal(i,   triangle.convertOppositeNormal(triangle.getTriangle()));
//			   triangle2.setNormal(i+1, triangle.convertOppositeNormal(triangle.getTriangle()));
//			   triangle2.setNormal(i+2, triangle.convertOppositeNormal(triangle.getTriangle()));
//			   
//			   triangle2.setColor(i, color2);
//	           triangle2.setColor(i+1, color2);
//	           triangle2.setColor(i+2, color2);
//			   
//			   i=i+3;
//   
//	   }
//	   return triangle2;
//   }
   /////////////////////

}
