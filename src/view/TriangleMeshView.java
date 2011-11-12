package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;

import model.Mesh;
import model.Point;
import model.Triangle;

public class TriangleMeshView extends TriangleArray
{
    private ArrayList<TriangleView> trianglesViewList;

    private Point                   centroid;

    private Mesh                    mesh;

    public TriangleMeshView(Mesh m)
    {
        super(m.size() * 3, GeometryArray.COORDINATES | GeometryArray.COLOR_3
                | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2);

        this.mesh = m;
        this.centroid = m.getCentroid();

        this.trianglesViewList = new ArrayList<>();
        // FIXME : remplir !

        this.setCapability(ALLOW_COLOR_WRITE);
        this.setCapability(ALLOW_COLOR_READ);
        this.setCapability(ALLOW_TEXCOORD_READ);
        this.setCapability(ALLOW_TEXCOORD_WRITE);

        int i = 0;
        for (TriangleView triangleView : this.trianglesViewList)
        {

            triangleView.setSelected(false);

            this.setCoordinate(i, new Point3d(triangleView.getTriangle()
                    .getP1().getX(), triangleView.getTriangle().getP1().getY(),
                    triangleView.getTriangle().getP1().getZ()));
            this.setCoordinate(i + 1, new Point3d(triangleView.getTriangle()
                    .getP2().getX(), triangleView.getTriangle().getP2().getY(),
                    triangleView.getTriangle().getP2().getZ()));
            this.setCoordinate(i + 2, new Point3d(triangleView.getTriangle()
                    .getP3().getX(), triangleView.getTriangle().getP3().getY(),
                    triangleView.getTriangle().getP3().getZ()));

            this.setNormal(i,
                    TriangleView.convertNormal(triangleView.getTriangle()));
            this.setNormal(i + 1,
                    TriangleView.convertNormal(triangleView.getTriangle()));
            this.setNormal(i + 2,
                    TriangleView.convertNormal(triangleView.getTriangle()));

            // FIXME by Siju !
            Color3f ColorSelect = new Color3f(0, 0, 1);

            this.setTextureCoordinate(i, new Point2f(0.0f, 1.0f));
            this.setTextureCoordinate(i + 1, new Point2f(0.0f, 0.0f));
            this.setTextureCoordinate(i + 2, new Point2f(1.0f, 0.0f));

            i = i + 3;
        }

    }

    // FIXME
    public void select(int i)
    {
        this.trianglesViewList.get(i).setSelected(true);
        this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
        this.setTextureCoordinate(i * 3 + 1, new Point2f(1.0f, 1.0f));
        this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
    }

    // FIXME
    public void changeColor(int i)
    {
        if (this.trianglesViewList.get(i).isSelected())
        {
            this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
            this.setTextureCoordinate(i * 3 + 1, new Point2f(1.0f, 1.0f));
            this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
        } else
        {
            Color3f ColorSelect = new Color3f(1, 1, 1);

            this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
            this.setTextureCoordinate(i * 3 + 1, new Point2f(0.0f, 0.0f));
            this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
        }

    }

    public Point getCentroid()
    {
        return this.centroid;
    }
}
