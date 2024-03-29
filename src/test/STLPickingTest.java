package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.media.j3d.AmbientLight;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;


import javax.media.j3d.Shape3D;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;



import view.MeshList;
import view.TriangleMeshView;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;

import com.sun.j3d.utils.picking.PickCanvas;
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
	TriangleMeshView meshViewer;
	
	SimpleUniverse simpleUniverse;

	MeshList meshList;
	
	
	
	
	
	public STLPickingTest() {
		if (this.pickCanvas == null)
			this.initJava3d();
//		ParserSTL parser = new ParserSTL("residential - 2.stl");
//		ParserSTL parser1 = new ParserSTL("residential - 1.stl"); 
//		try {
//			this.meshViewer = new TriangleMeshView(parser.read());
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		this.meshList=new MeshList();
	}

	@Override
	public void start() {
		//
	}

	@Override
	public void init() {
		this.setLayout(new BorderLayout());
		Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

		this.add("Center", c);

		this.doLayout();
		if (this.m_SceneBranchGroup != null) {
			c.addMouseListener(this);

			this.pickCanvas = new PickCanvas(c, this.m_SceneBranchGroup);
			this.pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
			this.pickCanvas.setTolerance(4.0f);
		}

		c.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Setups the SimpleUniverse, attaches the Canvas3D
		this.simpleUniverse = new SimpleUniverse(c);
		c.getView().setBackClipDistance(1000);
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

		// Point the view at the center of the object
		Point3d center = new Point3d();
		sceneBounds.getCenter(center);
		double radius = sceneBounds.getRadius();
		Vector3d temp = new Vector3d(center);
		viewTrans.set(temp);

		// Pull the eye back far enough to see the whole object
		double eyeDist = 1.4 * radius / Math.tan(Math.toRadians(40) / 2.0);
		temp.x = 0.0;
		temp.y = 0.0;
		temp.z = eyeDist;
		eyeTrans.set(temp);
		viewTrans.mul(eyeTrans);

		// Set the view transform
		tgArray[0].setTransform(viewTrans);

		return tgArray;
	}

	protected BranchGroup createSceneBranchGroup(Canvas3D c) {
		BranchGroup objRoot = super.createSceneBranchGroup();

		PickingEnvironment pick = new PickingEnvironment(c, objRoot);

//		TransformGroup transformGroup = STLPickingTest.createTransformGroup(
//				pick, this.meshViewer,this.meshViewer1);
		TransformGroup transformGroup = STLPickingTest.createTransformGroup(
				pick, this.meshList);

		objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// Light bound
		BoundingSphere lightBounds = new BoundingSphere(new Point3d(0.0, 0.0,
				0.0), 10000.0);

		// Ambient light
		AmbientLight ambLight = new AmbientLight(true, new Color3f(1.0f, 1.0f,
				1.0f));
		ambLight.setInfluencingBounds(lightBounds);
		objRoot.addChild(ambLight);

		// Directional light
		DirectionalLight headLight1 = new DirectionalLight(new Color3f(
				Color.white), new Vector3f(1.0f, -1.0f, -1.0f));
		headLight1.setInfluencingBounds(lightBounds);
		objRoot.addChild(headLight1);

		DirectionalLight headLight2 = new DirectionalLight(new Color3f(
				Color.white), new Vector3f(-1.0f, -1.0f, -1.0f));
		headLight2.setInfluencingBounds(lightBounds);
		objRoot.addChild(headLight2);

		objRoot.addChild(transformGroup);

		MovingEnvironment test = new MovingEnvironment(transformGroup);
		test.setSchedulingBounds(lightBounds);
		objRoot.addChild(test);

		// Translates camera
		
		this.translateCamera((float) this.meshList.getMeshList().get(0).getCentroid().getX(),
				(float) this.meshList.getMeshList().get(0).getCentroid().getY(),
				(float) this.meshList.getMeshList().get(0).getCentroid().getZ() + 20);
		Point3d orientPoint = new Point3d();
		orientPoint.setX(this.meshList.getMeshList().get(0).getCentroid().getX());
		orientPoint.setY(this.meshList.getMeshList().get(0).getCentroid().getY());
		orientPoint.setZ(this.meshList.getMeshList().get(0).getCentroid().getZ());
		pick.getMouseRotate().setCenter(orientPoint);

		objRoot.compile();

		return objRoot;
	}

	private static TransformGroup createTransformGroup(PickingEnvironment pick,
			MeshList meshList) {
		BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,
				0.0, 0.0), 10000);

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

		// Appearance et texture
//		Material mat = new Material(new Color3f(0, 0, 0f),
//				new Color3f(0, 0, 0), new Color3f(Color.white), new Color3f(
//						Color.white), 64);
//		mat.setColorTarget(3);
//
//		
//		Appearance app = new Appearance();
//
//		app.setMaterial(mat);

		
		
//		TextureLoader loader = new TextureLoader("texture.jpg", null);
//		ImageComponent2D image = loader.getImage();
//		Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB,
//				image.getWidth(), image.getHeight());
//		texture.setImage(0, image);
//		//app.setTexture(texture);
//		TextureAttributes texAtt = new TextureAttributes();
//		texAtt.setTextureMode(TextureAttributes.MODULATE);
//		//app.setTextureAttributes(texAtt);

		// Test pour augmenter la capacit�d'affichage
		BranchGroup sceneRoot = new BranchGroup();

		translationGroup2.addChild(sceneRoot);

//		Shape3D shape = new Shape3D();
//
//		//shape.addGeometry(meshViewer);
//		for(TriangleArray triangleArray: meshList.getMeshList()){
//			shape.addGeometry(triangleArray);
//		}
	    for(Shape3D shape: meshList.getShape3D()){
	    	sceneRoot.addChild(shape);
	    	
	    }

		
//		sceneRoot.addChild(shape);
//		shape.setAppearance(app);

		// Links the left button of the mouse with a rotation transformation
		NewMouseRotate mouseRotate = new NewMouseRotate(translationGroup1,
				rotationGroup, translationGroup2);
		mouseRotate.setSchedulingBounds(boundingSphere);
		translationGroup2.addChild(mouseRotate);
		pick.setMouseRotate(mouseRotate);

		// Links the middle button of the mouse with a zoom transformation
		MouseZoom mouseZoom = new MouseZoom();
		mouseZoom.setFactor(2);
		mouseZoom.setTransformGroup(transformGroup);
		transformGroup.addChild(mouseZoom);
		mouseZoom.setSchedulingBounds(boundingSphere);

		// Links the right button of the mouse with a translation transformation
		MouseTranslate mouseTranslate = new MouseTranslate();
		mouseTranslate.setFactor(2);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// this.pickCanvas.setShapeLocation(e);
		// PickResult pickResult = this.pickCanvas.pickClosest();
		//
		// if (pickResult != null) {
		// System.out.println("Closest PickResult: " + pickResult);
		//
		// Node actualNode = pickResult.getObject();
		//
		// if (actualNode.getClass().equals(TriangleView.class)) {
		// TriangleViewer triangle = (TriangleViewer) actualNode;
		// triangle.setSelected(true);
		// }
		// }
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

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		STLPickingTest pickingTest = new STLPickingTest();
		new MainFrame(pickingTest, 500, 400);
	}

	@Override
	public void destroy() {
		this.simpleUniverse.removeAllLocales();
	}

}
