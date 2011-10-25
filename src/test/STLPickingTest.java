package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import model.Point;
import model.Triangle;

import util.ParserSTL;

import view.TriangleMeshViewer;
import view.TriangleViewer;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import control.MovingEnvironment;
import control.NewMouseRotate;
import control.PickingEnvironment;

/**
 * This example loads a VRML file, automatically computes the view point to view
 * the objects in the file, and then mouse picks. For each pick all the selected
 * components of the scene are reported (by their VRML name). The VRML scene can
 * be rotates, scaled and translated using the mouse.
 */
public class STLPickingTest extends Java3dApplet implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	PickCanvas pickCanvas;
	TriangleMeshViewer meshViewer;
	SimpleUniverse simpleUniverse;

	public STLPickingTest() {
		ParserSTL parser = new ParserSTL("test.stl");
		try {
			this.meshViewer = new TriangleMeshViewer(parser.read());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public STLPickingTest(String[] args) {
		this.saveCommandLineArguments(args);
		this.initJava3d();
		ParserSTL parser = new ParserSTL("test.stl");
		try {
			this.meshViewer = new TriangleMeshViewer(parser.read());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		if (this.pickCanvas == null)
			this.initJava3d();
		ParserSTL parser = new ParserSTL("test.stl");
		try {
			this.meshViewer = new TriangleMeshViewer(parser.read());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
//	 protected void addCanvas3D(Canvas3D c3d) {
//	 setLayout(new BorderLayout());
//	 add(c3d, BorderLayout.CENTER);
//	 doLayout();
//	
//	 if (this.m_SceneBranchGroup != null) {
//	 c3d.addMouseListener(this);
//	
//	 this.pickCanvas = new PickCanvas(c3d, this.m_SceneBranchGroup);
//	 this.pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
//	 this.pickCanvas.setTolerance(4.0f);
//	 }
//	
//	 c3d.setCursor(new Cursor(Cursor.HAND_CURSOR));
//	
//	 this.simpleUniverse=new SimpleUniverse(c3d);
//	 BranchGroup group=this.createSceneBranchGroup(c3d);
//	 this.simpleUniverse.addBranchGraph(group);
//	 }
	
	public void init() {
		this.setLayout(new BorderLayout());
		Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add("Center", c);
//////////////////////
		doLayout();
		if (this.m_SceneBranchGroup != null) {
            c.addMouseListener(this);

            this.pickCanvas = new PickCanvas(c, this.m_SceneBranchGroup);
            this.pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
            this.pickCanvas.setTolerance(4.0f);
        }
		c.setCursor(new Cursor(Cursor.HAND_CURSOR));
		////////////////////////
		// Setups the SimpleUniverse, attaches the Canvas3D
		this.simpleUniverse = new SimpleUniverse(c);
		BranchGroup group = this.createSceneBranchGroup(c);
		this.simpleUniverse.addBranchGraph(group);

	}

	@Override
	public TransformGroup[] getViewTransformGroupArray() {
		TransformGroup[] tgArray = new TransformGroup[1];
		tgArray[0] = new TransformGroup();

		Transform3D viewTrans = new Transform3D();
		Transform3D eyeTrans = new Transform3D();

		BoundingSphere sceneBounds = (BoundingSphere) this.m_SceneBranchGroup
				.getBounds();

		// point the view at the center of the object
		Point3d center = new Point3d();
		sceneBounds.getCenter(center);
		double radius = sceneBounds.getRadius();
		Vector3d temp = new Vector3d(center);
		viewTrans.set(temp);

		// pull the eye back far enough to see the whole object
		double eyeDist = 1.4 * radius / Math.tan(Math.toRadians(40) / 2.0);
		temp.x = 0.0;
		temp.y = 0.0;
		temp.z = eyeDist;
		eyeTrans.set(temp);
		viewTrans.mul(eyeTrans);

		// set the view transform
		tgArray[0].setTransform(viewTrans);

		return tgArray;
	}

	protected BranchGroup createSceneBranchGroup(Canvas3D c) {
		BranchGroup objRoot = super.createSceneBranchGroup();

		PickingEnvironment pick = new PickingEnvironment(c, objRoot);

		TransformGroup transformGroup = STLPickingTest.createTransformGroup(
				pick, this.meshViewer);

		objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// light bound
		// Bounds lightBounds = getApplicationBounds();
		BoundingSphere lightBounds = new BoundingSphere(new Point3d(0.0, 0.0,
				0.0), 1000.0);

		// Ambient light
		AmbientLight ambLight = new AmbientLight(true, new Color3f(1.0f, 1.0f,
				1.0f));
		ambLight.setInfluencingBounds(lightBounds);
		objRoot.addChild(ambLight);
		// directional light
		// DirectionalLight headLight = new DirectionalLight();
		DirectionalLight headLight = new DirectionalLight(new Color3f(
				Color.white), new Vector3f(1.0f, -1.0f, -1.0f));
		headLight.setInfluencingBounds(lightBounds);
		objRoot.addChild(headLight);

		objRoot.addChild(transformGroup);

		MovingEnvironment test = new MovingEnvironment(transformGroup);
		test.setSchedulingBounds(lightBounds);
		objRoot.addChild(test);

		// Translates camera
		this.translateCamera(255, 91, 450);
		pick.getMouseRotate().setCenter(new Point3d(255, 91, 450));

		objRoot.compile();

		// TransformGroup mouseGroup = createMouseBehaviorsGroup();
		// BranchGroup sceneRoot = new BranchGroup();
		// objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		// objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		//
		// this.meshViewer.createTriangleShapes();
		// for (TriangleViewer shape : this.meshViewer) {
		// sceneRoot.addChild(shape);
		// }
		//
		// mouseGroup.addChild(sceneRoot);
		//
		// objRoot.addChild(mouseGroup);

		return objRoot;
	}

	private static TransformGroup createTransformGroup(PickingEnvironment pick,
			TriangleMeshViewer meshViewer) {
		BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,
				0.0, 0.0), 1000);

		TransformGroup transformGroup = new TransformGroup();
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		TransformGroup translationGroup1 = new TransformGroup();
		translationGroup1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		translationGroup1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformGroup.addChild(translationGroup1);

		TransformGroup rotationGroup = new TransformGroup();
		rotationGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotationGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		translationGroup1.addChild(rotationGroup);

		TransformGroup translationGroup2 = new TransformGroup();
		translationGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		translationGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		rotationGroup.addChild(translationGroup2);
		// /////
//		Point p1 = new Point(1, 1, 1);
//		Point p2 = new Point(0, 1, 1);
//		Point p3 = new Point(1, 0, 1);
		Point p1 = new Point(255, 91, 443);
		Point p2 = new Point(254, 91, 443);
		Point p3 = new Point(255, 90, 443);
		Vector3d normal = new Vector3d(0, 0, 1);
		Triangle triangle1 = new Triangle(p1, p2, p3, normal);

		TriangleViewer triangleViewer1 = new TriangleViewer(triangle1);
		triangleViewer1.createShape3D();
		translationGroup2.addChild(triangleViewer1);

		Point p1bis = new Point(1, 1, 1);
		Point p2bis = new Point(2, 1, 1);
		Point p3bis = new Point(1, 2, 1);
		Triangle triangle2 = new Triangle(p1bis, p2bis, p3bis, normal);

		TriangleViewer triangleViewer2 = new TriangleViewer(triangle2);
		triangleViewer2.createShape3D();
		translationGroup2.addChild(triangleViewer2);
		// ///////////////////
		BranchGroup sceneRoot=new BranchGroup();
		meshViewer.createTriangleShapes();
		for (TriangleViewer shape : meshViewer) {
			sceneRoot.addChild(shape);
		}
		translationGroup2.addChild(sceneRoot);
		

		// Links the left button of the mouse with a rotation transformation
		NewMouseRotate mouseRotate = new NewMouseRotate(translationGroup1,
				rotationGroup, translationGroup2);
		mouseRotate.setSchedulingBounds(boundingSphere);
		translationGroup2.addChild(mouseRotate);
		pick.setMouseRotate(mouseRotate);

		// Links the middle button of the mouse with a zoom transformation
		MouseZoom mouseZoom = new MouseZoom();
		mouseZoom.setTransformGroup(transformGroup);
		transformGroup.addChild(mouseZoom);
		mouseZoom.setSchedulingBounds(boundingSphere);

		// Links the right button of the mouse with a translation transformation
		MouseTranslate mouseTranslate = new MouseTranslate();
		mouseTranslate.setTransformGroup(transformGroup);
		transformGroup.addChild(mouseTranslate);
		mouseTranslate.setSchedulingBounds(boundingSphere);

		return transformGroup;

	}

	public void translateCamera(float x, float y, float z) {
		ViewingPlatform camera = this.simpleUniverse.getViewingPlatform();
		TransformGroup cameraTransformGroup = camera.getMultiTransformGroup()
				.getTransformGroup(0);
		Transform3D cameraTranslation = new Transform3D();
		cameraTransformGroup.getTransform(cameraTranslation);
		cameraTranslation.setTranslation(new Vector3f(x, y, z));
		cameraTransformGroup.setTransform(cameraTranslation);
	}

	// Juste du contrôle souris...
	// private TransformGroup createMouseBehaviorsGroup() {
	// BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,
	// 0.0, 0.0), 10000.);
	//
	// TransformGroup examineGroup = new TransformGroup();
	// examineGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	// examineGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	//
	// Bounds behaviorBounds = getApplicationBounds();
	//
	// MouseRotate mr = new MouseRotate(examineGroup);
	// mr.setSchedulingBounds(behaviorBounds);
	// examineGroup.addChild(mr);
	// mr.setSchedulingBounds(boundingSphere);
	//
	// MouseTranslate mt = new MouseTranslate(examineGroup);
	// mt.setSchedulingBounds(behaviorBounds);
	// examineGroup.addChild(mt);
	// mt.setSchedulingBounds(boundingSphere);
	//
	// MouseZoom mz = new MouseZoom(examineGroup);
	// mz.setSchedulingBounds(behaviorBounds);
	// examineGroup.addChild(mz);
	// mz.setSchedulingBounds(boundingSphere);
	//
	// return examineGroup;
	// }

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("*** MouseClick ***");

		this.pickCanvas.setShapeLocation(e);

		PickResult pickResult = this.pickCanvas.pickClosest();

		if (pickResult != null) {
			System.out.println("Closest PickResult: " + pickResult);

			Node actualNode = pickResult.getObject();

			if (actualNode.getClass().equals(TriangleViewer.class)) {
				TriangleViewer triangle = (TriangleViewer) actualNode;
				triangle.setSelected(true);
			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//
	}

	public static void main(String[] args) {
		System.out.println("Current user.dir: "
				+ System.getProperty("user.dir"));

		STLPickingTest pickingTest = new STLPickingTest(args);

		new MainFrame(pickingTest, 800, 700);
	}

	public void destroy() {
		this.simpleUniverse.removeAllLocales();
	}
}

