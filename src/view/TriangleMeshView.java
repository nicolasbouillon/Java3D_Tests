package view;

import java.awt.Color;
import java.util.ArrayList;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;

import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import model.Mesh;
import model.Point;
import model.Triangle;

public class TriangleMeshView extends TriangleArray {
	private ArrayList<TriangleView> trianglesViewList;

	private Point centroid;

	private Mesh mesh;

	public TriangleMeshView(Mesh m) {
		super(m.size() * 3, GeometryArray.COORDINATES | GeometryArray.COLOR_3
				| GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2);

		this.mesh = m;
		this.centroid = m.getCentroid();

		this.trianglesViewList = new ArrayList<>();
		int triangleCount=0;
		for (Triangle triangle : this.mesh) {
			TriangleView triangleView = new TriangleView(triangle);
			this.trianglesViewList.add(triangleView);
			triangle.setTriangleViewIndex(triangleCount);
			triangleCount++;
		}

		this.setCapability(ALLOW_COLOR_WRITE);
		this.setCapability(ALLOW_COLOR_READ);
		this.setCapability(ALLOW_TEXCOORD_READ);
		this.setCapability(ALLOW_TEXCOORD_WRITE);

		int i = 0;
		for (TriangleView triangleView : this.trianglesViewList) {

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

			this.setNormal(i, convertNormal(triangleView.getTriangle()));
			this.setNormal(i + 1, convertNormal(triangleView.getTriangle()));
			this.setNormal(i + 2, convertNormal(triangleView.getTriangle()));

			// FIXME by Siju !

			this.setTextureCoordinate(i, new Point2f(0.0f, 1.0f));
			this.setTextureCoordinate(i + 1, new Point2f(0.0f, 0.0f));
			this.setTextureCoordinate(i + 2, new Point2f(1.0f, 0.0f));
//			Color3f color1=new Color3f(0,0,1);
//			this.setColor(i,   color1);
//			this.setColor(i+1, color1);
//			this.setColor(i+2, color1);

			i = i + 3;
		}

	}

	public ArrayList<TriangleView> getTriangleArray() {
		return this.trianglesViewList;
	}

	// FIXME
	public void select(int i) {
		this.trianglesViewList.get(i).setSelected(true);
		this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
		this.setTextureCoordinate(i * 3 + 1, new Point2f(1.0f, 1.0f));
		this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
//		Color3f color1=new Color3f(1,0,1);
//		this.setColor(i,   color1);
//		this.setColor(i+1, color1);
//		this.setColor(i+2, color1);
	}

	public void selectOrUnselect(int i) {
		if (this.trianglesViewList.get(i).isSelected()) {
			this.trianglesViewList.get(i).setSelected(false);

			this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
			this.setTextureCoordinate(i * 3 + 1, new Point2f(0.0f, 0.0f));
			this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
//			Color3f color1=new Color3f(0,0,1);
//			this.setColor(i*3,   color1);
//			this.setColor(i*3+1, color1);
//			this.setColor(i*3+2, color1);
		} else {
			this.trianglesViewList.get(i).setSelected(true);

			this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
			this.setTextureCoordinate(i * 3 + 1, new Point2f(1.0f, 1.0f));
			this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
//			Color3f color1=new Color3f(1,0,1);
//			this.setColor(i*3,   color1);
//			this.setColor(i*3+1, color1);
//			this.setColor(i*3+2, color1);
		}
	}

	// FIXME
	public void changeColor(int i) {
		if (this.trianglesViewList.get(i).isSelected()) {
			this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
			this.setTextureCoordinate(i * 3 + 1, new Point2f(1.0f, 1.0f));
			this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
//			Color3f color1=new Color3f(1,0,1);
//			this.setColor(i*3,   color1);
//			this.setColor(i*3+1, color1);
//			this.setColor(i*3+2, color1);
		} else {

			this.setTextureCoordinate(i * 3, new Point2f(0.0f, 1.0f));
			this.setTextureCoordinate(i * 3 + 1, new Point2f(0.0f, 0.0f));
			this.setTextureCoordinate(i * 3 + 2, new Point2f(1.0f, 0.0f));
//			Color3f color1=new Color3f(0,0,1);
//			this.setColor(i*3,   color1);
//			this.setColor(i*3+1, color1);
//			this.setColor(i*3+2, color1);
		}

	}

	public Point getCentroid() {
		return this.centroid;
	}

	public static Vector3f convertNormal(Triangle triangle) {
		Vector3f normalFloat = new Vector3f(
				(float) triangle.getNormal().getX(), (float) triangle
						.getNormal().getY(), (float) triangle.getNormal()
						.getZ());
		return normalFloat;

	}
}
