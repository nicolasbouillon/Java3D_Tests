package view;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;

import model.Mesh;
import model.Triangle;

public class TriangleArraySon extends TriangleArray {

    public List<Triangle> Triangle=new ArrayList<>();
    private boolean []selected;
    

    public TriangleArraySon(Mesh mesh) {

    	super(mesh.size()*3, GeometryArray.COORDINATES | GeometryArray.COLOR_3
                | GeometryArray.NORMALS|GeometryArray.TEXTURE_COORDINATE_2);
    	this.setCapability(ALLOW_COLOR_WRITE);
       	this.setCapability(ALLOW_COLOR_READ);
       	this.setCapability(ALLOW_TEXCOORD_READ);
       	this.setCapability(ALLOW_TEXCOORD_WRITE);
    	int i=0;
    	selected=new boolean[mesh.size()];
    	for(int j=0;j<mesh.size();j++){
    		selected[j]=false;
    	}
    	 for (Triangle triangle : mesh){
    		Triangle.add(triangle);
    		
    	     this.setCoordinate(i, new Point3d(triangle.getP1().getX(),
    	                triangle.getP1().getY(), triangle.getP1().getZ()));
    	     this.setCoordinate(i+1, new Point3d(triangle.getP2().getX(),
    	                triangle.getP2().getY(), triangle.getP2().getZ()));
    	     this.setCoordinate(i+2, new Point3d(triangle.getP3().getX(),
    	                triangle.getP3().getY(), triangle.getP3().getZ()));
    	     this.setNormal(i,   TriangleViewer.convertNormal(triangle));
    	     this.setNormal(i+1, TriangleViewer.convertNormal(triangle));
    	     this.setNormal(i+2, TriangleViewer.convertNormal(triangle));
    	     Color3f ColorSelect=new Color3f(0,0,1);

           this.setTextureCoordinate(i, new Point2f(0.0f,1.0f));
           this.setTextureCoordinate(i+1, new Point2f(0.0f,0.0f));
           this.setTextureCoordinate(i+2, new Point2f(1.0f,0.0f));
    	     i=i+3;		
    	     
             
    	}
       

     
    	
    }
    
    public boolean isSelected(int i) {
        return this.selected[i];
    }
    
    public void selectOrUnselect(int i) {
        if (this.selected[i]) {
            this.selected[i] = false;
            this.changeColor(i);
        } else {
            this.selected[i] = true;
            if (this.selected[i]) {
               this.changeColor(i);
            }
            
            
        }
    }
    
    public void setSelected(boolean selectedIn, int i) {
        this.selected[i] = selectedIn;
    }
    
    public void select(int i) {
        this.selected[i] = true;
        this.setTextureCoordinate(i*3, new Point2f(0.0f,1.0f));
        this.setTextureCoordinate(i*3+1, new Point2f(1.0f,1.0f));
        this.setTextureCoordinate(i*3+2, new Point2f(1.0f,0.0f));

    }
    
    public void changeColor(int i) {
    	
   
        if (this.selected[i]) {

        	 this.setTextureCoordinate(i*3, new Point2f(0.0f,1.0f));
             this.setTextureCoordinate(i*3+1, new Point2f(1.0f,1.0f));
             this.setTextureCoordinate(i*3+2, new Point2f(1.0f,0.0f));

        } else {
        	 Color3f ColorSelect=new Color3f(1,1,1);

        	 this.setTextureCoordinate(i*3, new Point2f(0.0f,1.0f));
             this.setTextureCoordinate(i*3+1, new Point2f(0.0f,0.0f));
             this.setTextureCoordinate(i*3+2, new Point2f(1.0f,0.0f));

        }
       
        
    }
}
