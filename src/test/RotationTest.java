package test;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;


import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class RotationTest extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RotationTest() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		BranchGroup scene = createSceneGraph();
		scene.compile();

		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
	}

	public BranchGroup createSceneGraph() {
		// Create the root of the branch graph

		Angle angle = new Angle(0);// angle de rotation de l'axe Y

		BranchGroup objRoot = new BranchGroup();

		TransformGroup objTran1 = new TransformGroup();//la translation pour mettre le centre de rotation au centre du syst¨¨me du coordonn¨¦ 
		objTran1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTran1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D tran1 = new Transform3D();

		TransformGroup objTran2 = new TransformGroup();//la translation pour mettre le centre de rotation dans sa propre position  
		objTran2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTran2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D tran2 = new Transform3D();

		Vector3d vec1 = new Vector3d(-0.3, 0, 0);
		Vector3d vec2 = new Vector3d(0.3, 0, 0);

		tran1.setTranslation(vec1);
		objTran1.setTransform(tran1);
		tran2.setTranslation(vec2);
		objTran2.setTransform(tran2);

		TransformGroup objRotate1 = new TransformGroup();//la rotation d'un point 
		objRotate1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D rotation = new Transform3D();

		TriangleArray triangle1 = new TriangleArray(3,
				GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		TriangleArray triangle2 = new TriangleArray(3,
				GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		triangle1.setCapability(TriangleArray.ALLOW_COLOR_READ);
		triangle1.setCapability(TriangleArray.ALLOW_COLOR_WRITE);
		triangle2.setCapability(TriangleArray.ALLOW_COLOR_READ);
		triangle2.setCapability(TriangleArray.ALLOW_COLOR_WRITE);
		double[] p1 = { 0.1, 0, 0 };
		double[] p2 = { 0.2, 0.1, 0 };
		double[] p3 = { 0.3, 0, 0 };
		triangle1.setCoordinate(0, p1);
		triangle1.setCoordinate(1, p2);
		triangle1.setCoordinate(2, p3);
		triangle2.setCoordinate(0, p2);
		triangle2.setCoordinate(1, p1);
		triangle2.setCoordinate(2, p3);
		triangle1.setColor(0, new Color3f(1, 0, 0));
		triangle1.setColor(1, new Color3f(1, 0, 0));
		triangle1.setColor(2, new Color3f(1, 0, 0));
		triangle2.setColor(0, new Color3f(1, 0, 0));
		triangle2.setColor(1, new Color3f(1, 0, 0));
		triangle2.setColor(2, new Color3f(1, 0, 0));

		TriangleArray triangle3 = new TriangleArray(3,
				GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		TriangleArray triangle4 = new TriangleArray(3,
				GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		double[] p4 = { -0.1, 0, 0 };
		double[] p5 = { -0.2, 0.1, 0 };
		double[] p6 = { -0.3, 0, 0 };
		triangle3.setCoordinate(0, p4);
		triangle3.setCoordinate(1, p5);
		triangle3.setCoordinate(2, p6);
		triangle4.setCoordinate(0, p5);
		triangle4.setCoordinate(1, p4);
		triangle4.setCoordinate(2, p6);
		triangle3.setColor(0, new Color3f(1, 0, 0));
		triangle3.setColor(1, new Color3f(1, 0, 0));
		triangle3.setColor(2, new Color3f(1, 0, 0));
		triangle4.setColor(0, new Color3f(1, 0, 0));
		triangle4.setColor(1, new Color3f(1, 0, 0));
		triangle4.setColor(2, new Color3f(1, 0, 0));

		Shape3D shape = new Shape3D();//d'abord translater le point de centre au centre de syst¨¨me de coordonn¨¦e, puis faire une rotation, et puis le centre de rotation rentre sa position 
		objRoot.addChild(objTran2);
		objTran2.addChild(objRotate1);
		objRotate1.addChild(objTran1);
        objTran1.addChild(shape);

		shape.addGeometry(triangle1);
		shape.addGeometry(triangle2);
		shape.addGeometry(triangle3);
		shape.addGeometry(triangle4);

		SimpleBehavior myRotationBehavior1 = new SimpleBehavior(objRotate1,
				objTran1, objTran2, new Point3d(-0.3, 0, 0), angle, rotation,
				tran1, tran2, triangle1, triangle2);
		SimpleBehavior2 myRotationBehavior2 = new SimpleBehavior2(objRotate1,
				objTran1, objTran2, new Point3d(0.3, 0, 0), angle, rotation,
				tran1, tran2);

		myRotationBehavior1.setSchedulingBounds(new BoundingSphere());
		myRotationBehavior2.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(myRotationBehavior1);
		objRoot.addChild(myRotationBehavior2);

		// Let Java 3D perform optimizations on this scene graph.
		objRoot.compile();

		return objRoot;
	}
	

	
	
	public static void main(String[] args) {
		new MainFrame(new PickTest(), 500, 500);
	}

}
