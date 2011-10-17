package view;

import java.util.HashSet;

import javax.media.j3d.Shape3D;

import model.Edge;
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
            shapeSet.add(triangle.display());
        }

        return shapeSet;
    }
}
