package view;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

import model.Triangle;

public class TriangleArrayChild extends TriangleArray {

    public Triangle triangle;

    public TriangleArrayChild(Triangle tri) {
        super(3, GeometryArray.COORDINATES | GeometryArray.COLOR_3
                | GeometryArray.NORMALS);
        this.triangle = tri;
        this.setCoordinate(0, new Point3d(this.triangle.getP1().getX(),
                this.triangle.getP1().getY(), this.triangle.getP1().getZ()));
        this.setCoordinate(1, new Point3d(this.triangle.getP2().getX(),
                this.triangle.getP2().getY(), this.triangle.getP2().getZ()));
        this.setCoordinate(2, new Point3d(this.triangle.getP3().getX(),
                this.triangle.getP3().getY(), this.triangle.getP3().getZ()));

        this.setNormal(0, TriangleViewer.convertNormal(this.triangle));
        this.setNormal(1, TriangleViewer.convertNormal(this.triangle));
        this.setNormal(2, TriangleViewer.convertNormal(this.triangle));
    }
}
