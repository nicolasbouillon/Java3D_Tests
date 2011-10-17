package view;

import java.awt.Color;
import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import model.Triangle;

public class TriangleViewer extends Shape3D {

    private Triangle triangle;
    private boolean selected;

    public TriangleViewer(Triangle tri) {
        this.triangle = tri;
    }

    public Triangle getTriangle() {
        return this.triangle;
    }

    public void setTriangle(Triangle triangleIn) {
        this.triangle = triangleIn;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selectedIn) {
        this.selected = selectedIn;
    }

    public void createShape3D() {

        TriangleArray triangle1 = new TriangleArray(3,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);

        triangle1.setCoordinate(0, new Point3d(this.triangle.getP1().getX(),
                this.triangle.getP1().getY(), this.triangle.getP1().getZ()));
        triangle1.setCoordinate(1, new Point3d(this.triangle.getP2().getX(),
                this.triangle.getP2().getY(), this.triangle.getP2().getZ()));
        triangle1.setCoordinate(2, new Point3d(this.triangle.getP3().getX(),
                this.triangle.getP3().getY(), this.triangle.getP3().getZ()));

        triangle1.setNormal(0, convertNormal(triangle));
        triangle1.setNormal(1, convertNormal(triangle));
        triangle1.setNormal(2, convertNormal(triangle));
        
        if (!this.selected) {
            triangle1.setColor(0, new Color3f(1, 0, 0));
            triangle1.setColor(1, new Color3f(1, 0, 0));
            triangle1.setColor(2, new Color3f(1, 0, 0));
        } else {
            triangle1.setColor(0, new Color3f(1, 0, 1));
            triangle1.setColor(1, new Color3f(1, 0, 1));
            triangle1.setColor(2, new Color3f(1, 0, 1));
        }

        TriangleArray triangle2 = new TriangleArray(3,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);
        triangle2.setCoordinate(0, new Point3d(this.triangle.getP2().getX(),
                this.triangle.getP2().getY(), this.triangle.getP2().getZ()));
        triangle2.setCoordinate(1, new Point3d(this.triangle.getP1().getX(),
                this.triangle.getP1().getY(), this.triangle.getP1().getZ()));
        triangle2.setCoordinate(2, new Point3d(this.triangle.getP3().getX(),
                this.triangle.getP3().getY(), this.triangle.getP3().getZ()));
        triangle2.setColor(0, new Color3f(1, 0, 0));
        triangle2.setColor(1, new Color3f(1, 0, 0));
        triangle2.setColor(2, new Color3f(1, 0, 0));

        this.addGeometry(triangle1);
        this.addGeometry(triangle2);

        //Appearance to make the object visible if light
        Appearance app = new Appearance();
        Material mat = new Material(new Color3f(Color.red),new Color3f(Color.black),new Color3f(Color.red),new Color3f(Color.red),64);
        mat.setColorTarget(3);
        app.setMaterial(mat);
        this.setAppearance(app); 
        
        if (!this.selected) {
            triangle2.setColor(0, new Color3f(0, 1, 0));
            triangle2.setColor(1, new Color3f(0, 1, 0));
            triangle2.setColor(2, new Color3f(0, 1, 0));
        } else {
            triangle1.setColor(0, new Color3f(1, 0, 1));
            triangle1.setColor(1, new Color3f(1, 0, 1));
            triangle1.setColor(2, new Color3f(1, 0, 1));
        }

        this.addGeometry(triangle1);
        this.addGeometry(triangle2);
    }
    
    public Vector3f convertNormal(Triangle triangle){
    	Vector3f normalFloat = new Vector3f((float) triangle.getNormal().getX(),
    			(float) triangle.getNormal().getY(),(float) triangle.getNormal().getZ());
    	return normalFloat; 
    	
    }
}
