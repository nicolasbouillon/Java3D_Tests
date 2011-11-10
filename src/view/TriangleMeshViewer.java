package view;

import java.util.HashSet;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

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
        this.centroid = m.getCentroid();
    }

    public Point getCentroid() {
        return this.centroid;
    }

    // Test pour augmenter la capacite d'affichage
    public TriangleArray createTriangle() {
        TriangleArray triangleArray = new TriangleArray(this.size() * 3 * 2,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3
                        | GeometryArray.NORMALS);

        int i = 0;
        for (TriangleViewer triangle : this) {
            triangleArray.setCoordinate(i, new Point3d(triangle.getTriangle()
                    .getP1().getX(), triangle.getTriangle().getP1().getY(),
                    triangle.getTriangle().getP1().getZ()));
            triangleArray.setCoordinate(i + 1, new Point3d(triangle
                    .getTriangle().getP2().getX(), triangle.getTriangle()
                    .getP2().getY(), triangle.getTriangle().getP2().getZ()));
            triangleArray.setCoordinate(i + 2, new Point3d(triangle
                    .getTriangle().getP3().getX(), triangle.getTriangle()
                    .getP3().getY(), triangle.getTriangle().getP3().getZ()));
            triangleArray.setCoordinate(i + 3, new Point3d(triangle
                    .getTriangle().getP2().getX(), triangle.getTriangle()
                    .getP2().getY(), triangle.getTriangle().getP2().getZ()));
            triangleArray.setCoordinate(i + 4, new Point3d(triangle
                    .getTriangle().getP1().getX(), triangle.getTriangle()
                    .getP1().getY(), triangle.getTriangle().getP1().getZ()));
            triangleArray.setCoordinate(i + 5, new Point3d(triangle
                    .getTriangle().getP3().getX(), triangle.getTriangle()
                    .getP3().getY(), triangle.getTriangle().getP3().getZ()));

            triangleArray.setNormal(i,
                    TriangleViewer.convertNormal(triangle.getTriangle()));
            triangleArray.setNormal(i + 1,
                    TriangleViewer.convertNormal(triangle.getTriangle()));
            triangleArray.setNormal(i + 2,
                    TriangleViewer.convertNormal(triangle.getTriangle()));
            triangleArray.setNormal(i + 3, TriangleViewer
                    .convertOppositeNormal(triangle.getTriangle()));
            triangleArray.setNormal(i + 4, TriangleViewer
                    .convertOppositeNormal(triangle.getTriangle()));
            triangleArray.setNormal(i + 5, TriangleViewer
                    .convertOppositeNormal(triangle.getTriangle()));

            i = i + 6;
        }
        return triangleArray;
    }
}
