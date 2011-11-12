package view;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.image.TextureLoader;

import model.Triangle;

public class TriangleArrayChild extends TriangleArray {

    public Triangle triangle;
    private boolean selected=false;
    
//    public TriangleArrayChild(){
//    	super(3, GeometryArray.COORDINATES | GeometryArray.COLOR_3
//                | GeometryArray.NORMALS);
//    	this.setCapability(ALLOW_COLOR_WRITE);
//    	this.setCapability(ALLOW_COLOR_READ);
//    	
//    }
    public TriangleArrayChild(Triangle tri) {
//    	super(6, GeometryArray.COORDINATES | GeometryArray.COLOR_3
//                | GeometryArray.NORMALS|GeometryArray.TEXTURE_COORDINATE_2);
    	super(3, GeometryArray.COORDINATES | GeometryArray.COLOR_3
                | GeometryArray.NORMALS|GeometryArray.TEXTURE_COORDINATE_2);
        this.triangle = tri;
        this.setCoordinate(0, new Point3d(this.triangle.getP1().getX(),
                this.triangle.getP1().getY(), this.triangle.getP1().getZ()));
        this.setCoordinate(1, new Point3d(this.triangle.getP2().getX(),
                this.triangle.getP2().getY(), this.triangle.getP2().getZ()));
        this.setCoordinate(2, new Point3d(this.triangle.getP3().getX(),
                this.triangle.getP3().getY(), this.triangle.getP3().getZ()));
//        this.setCoordinate(3, new Point3d(this.triangle.getP2().getX(),
//                this.triangle.getP2().getY(), this.triangle.getP2().getZ()));
//        this.setCoordinate(4, new Point3d(this.triangle.getP1().getX(),
//                this.triangle.getP1().getY(), this.triangle.getP1().getZ()));
//        this.setCoordinate(5, new Point3d(this.triangle.getP3().getX(),
//                this.triangle.getP3().getY(), this.triangle.getP3().getZ()));

        this.setNormal(0, TriangleViewer.convertNormal(this.triangle));
        this.setNormal(1, TriangleViewer.convertNormal(this.triangle));
        this.setNormal(2, TriangleViewer.convertNormal(this.triangle));

//        this.setTextureCoordinate(0, new Point2f(0.0f,1.0f));
//        this.setTextureCoordinate(1, new Point2f(0.0f,0.0f));
//        this.setTextureCoordinate(2, new Point2f(1.0f,0.0f));
//        this.setTextureCoordinate(3, new Point2f(0.0f,0.0f));
//        this.setTextureCoordinate(4, new Point2f(0.0f,1.0f));
//        this.setTextureCoordinate(5, new Point2f(1.0f,0.0f));
        this.changeColor();
        this.setCapability(ALLOW_COLOR_WRITE);
    	this.setCapability(ALLOW_COLOR_READ);
    	this.setCapability(ALLOW_TEXCOORD_READ);
    	this.setCapability(ALLOW_TEXCOORD_WRITE);
    	
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void selectOrUnselect() {
        if (this.selected) {
            this.selected = false;
            this.changeColor();
        } else {
            this.selected = true;
            if (this.selected) {
               this.changeColor();
            }
            
            
        }
    }
    
    public void setSelected(boolean selectedIn) {
        this.selected = selectedIn;
    }
    
    public void select() {
        this.selected = true;
        this.setTextureCoordinate(0, new Point2f(0.0f,1.0f));
        this.setTextureCoordinate(1, new Point2f(1.0f,1.0f));
        this.setTextureCoordinate(2, new Point2f(1.0f,0.0f));
//        this.setTextureCoordinate(3, new Point2f(1.0f,1.0f));
//        this.setTextureCoordinate(4, new Point2f(0.0f,1.0f));
//        this.setTextureCoordinate(5, new Point2f(1.0f,0.0f));
    }
    
    public void changeColor() {
    	
   
        if (this.selected) {
            Color3f ColorSelect=new Color3f(0,0,1);
            this.setColor(0, ColorSelect);
            this.setColor(1, ColorSelect);
            this.setColor(2, ColorSelect);
            this.setColor(3, ColorSelect);
//            this.setColor(4, ColorSelect);
//            this.setColor(5, ColorSelect);
//            TextureLoader loader=new TextureLoader("wood.jpg",null);
//        	ImageComponent2D image=loader.getImage();
//        	Texture2D texture=new Texture2D();
//        	texture.setImage(0,image);
            
            
//            this.setTextureCoordinate(0, new Point2f(0.0f,1.0f));
//            this.setTextureCoordinate(1, new Point2f(1.0f,1.0f));
//            this.setTextureCoordinate(2, new Point2f(1.0f,0.0f));
//            this.setTextureCoordinate(3, new Point2f(1.0f,1.0f));
//            this.setTextureCoordinate(4, new Point2f(0.0f,1.0f));
//            this.setTextureCoordinate(5, new Point2f(1.0f,0.0f));
        } else {
        	 Color3f ColorSelect=new Color3f(0,1,1);
             this.setColor(0, ColorSelect);
             this.setColor(1, ColorSelect);
             this.setColor(2, ColorSelect);
//             this.setColor(3, ColorSelect);
//             this.setColor(4, ColorSelect);
//             this.setColor(5, ColorSelect);
        	
//         	 this.setTextureCoordinate(0, new Point2f(0.0f,1.0f));
//             this.setTextureCoordinate(1, new Point2f(0.0f,0.0f));
//             this.setTextureCoordinate(2, new Point2f(1.0f,0.0f));
//             this.setTextureCoordinate(3, new Point2f(0.0f,0.0f));
//             this.setTextureCoordinate(4, new Point2f(0.0f,1.0f));
//             this.setTextureCoordinate(5, new Point2f(1.0f,0.0f));
        }
       
        
    }
}
