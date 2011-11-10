package view;

import java.util.HashSet;
import java.util.Set;

import javax.media.j3d.TriangleArray;

import model.Mesh;
import model.Point;
import model.Triangle;

public class TriangleMeshViewer {

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
}
