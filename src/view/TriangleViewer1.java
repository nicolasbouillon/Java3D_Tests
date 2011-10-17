package view;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import model.Triangle;

public class TriangleViewer1 {
    private Triangle triangle;
    private boolean selected;

    public TriangleViewer1(Triangle tri) {
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

    public Shape3D display() {
        Shape3D shape = new Shape3D();

        TriangleArray triangle1 = new TriangleArray(3,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3);

        triangle1.setCoordinate(0, new Point3d(this.triangle.getP1().getX(),
                this.triangle.getP1().getY(), this.triangle.getP1().getZ()));
        triangle1.setCoordinate(1, new Point3d(this.triangle.getP2().getX(),
                this.triangle.getP2().getY(), this.triangle.getP2().getZ()));
        triangle1.setCoordinate(2, new Point3d(this.triangle.getP3().getX(),
                this.triangle.getP3().getY(), this.triangle.getP3().getZ()));
        triangle1.setColor(0, new Color3f(1, 0, 0));
        triangle1.setColor(1, new Color3f(0, 1, 0));
        triangle1.setColor(2, new Color3f(0, 0, 1));

        TriangleArray triangle2 = new TriangleArray(3,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3);
        triangle2.setCoordinate(0, new Point3d(this.triangle.getP2().getX(),
                this.triangle.getP2().getY(), this.triangle.getP2().getZ()));
        triangle2.setCoordinate(1, new Point3d(this.triangle.getP1().getX(),
                this.triangle.getP1().getY(), this.triangle.getP1().getZ()));
        triangle2.setCoordinate(2, new Point3d(this.triangle.getP3().getX(),
                this.triangle.getP3().getY(), this.triangle.getP3().getZ()));
        triangle2.setColor(0, new Color3f(0, 1, 0));
        triangle2.setColor(1, new Color3f(0, 1, 0));
        triangle2.setColor(2, new Color3f(0, 1, 0));

        shape.addGeometry(triangle1);
        shape.addGeometry(triangle2);
        
        

        return shape;
    }

}
