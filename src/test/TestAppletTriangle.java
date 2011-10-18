package test;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import model.Point;
import model.Triangle;
import view.TriangleViewer;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import control.MovingEnvironment;
import control.NewMouseRotate;
import control.PickingEnvironment;

public class TestAppletTriangle extends Applet {

    private static final long serialVersionUID = 1L;

    SimpleUniverse simpleUniverse;
    PickingEnvironment pick;

    public static void main(String[] args) {
        // if called as an application, a 500x500 window will be opened
        new MainFrame(new TestAppletTriangle(), 500, 500);
    }
    
    public TestAppletTriangle() {
        // This constructor is sometimes needed, even when empty as in here.
    }

    @Override
    public void init() {
    	
        this.setLayout(new BorderLayout());
        Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", c);

        // Setups the SimpleUniverse, attaches the Canvas3D
        this.simpleUniverse = new SimpleUniverse(c);
        BranchGroup group = this.createSceneGraph();
        this.simpleUniverse.addBranchGraph(group);
        
        this.pick = new PickingEnvironment(c,group);
    } 
    
    /**
     * Creates a basic SceneGraph. Here we will create a basic SceneGraph with a
     * ColorCube object.
     * @return the branch group describing the scene graph
     */
    public BranchGroup createSceneGraph() {

        // objRoot will contain all the things to display:
    	//- transformGroup
    	//-lights
        BranchGroup objRoot = new BranchGroup();

        TransformGroup transformGroup = TestAppletTriangle.createTriangleTranslated();
        
        // Add light in the scene
        Color3f light1Color = new Color3f(Color.white);
        Color3f light2Color = new Color3f(Color.white);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0); 
        //Strong directional light
        Vector3f light1Direction = new Vector3f(1.0f, -1.0f, -1.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        //Weak ambient light
        AmbientLight light2 = new AmbientLight(light2Color);
        light1.setInfluencingBounds(bounds);
        light2.setInfluencingBounds(bounds);
        objRoot.addChild(light1);
        objRoot.addChild(light2);
        
        objRoot.addChild(transformGroup);
        
        MovingEnvironment test = new MovingEnvironment(transformGroup);
        test.setSchedulingBounds(bounds);
        objRoot.addChild(test);
        
        // Translates camera
        this.translateCamera(0,0,5);

        objRoot.compile();

        return objRoot;
    }

    
    public void translateCamera(float x, float y, float z) {
    	
        // Gets the ViewingPlatform of the SimpleUniverse
        ViewingPlatform camera = this.simpleUniverse.getViewingPlatform();
        // Gets the TransformGroup associated
        TransformGroup cameraTransformGroup = camera.getMultiTransformGroup()
                .getTransformGroup(0);
        // Creates a Transform3D for the ViewingPlatform
        Transform3D cameraTranslation = new Transform3D();
        // Gets the current 3d from the ViewingPlatform
        cameraTransformGroup.getTransform(cameraTranslation);
        
        cameraTranslation.setTranslation(new Vector3f(x, y, z));
        // Assigns Transform3D to ViewPlatform
        cameraTransformGroup.setTransform(cameraTranslation);
    }

    public static TransformGroup createTriangleTranslated() {

        // Creates a bounding sphere for the mouse translate, mouse rotate and
        // mouse zoom transformation
        BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,0.0, 0.0), 10000.);

        //transformGroup containing shapes, behaviors
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
		
        // Creates 2 Triangle objects and adds them to the transform group
        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(0, 1, 1);
        Point p3 = new Point(1, 0, 1);
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
        
        ColorCube cube = new ColorCube(0.2);
        translationGroup2.addChild(cube);
               
        // Links the left button of the mouse with a rotation transformation
        NewMouseRotate mouseRotate = new NewMouseRotate(translationGroup1, 
        		rotationGroup, translationGroup2);
        mouseRotate.setSchedulingBounds(boundingSphere);
        translationGroup2.addChild(mouseRotate);

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

    @Override
    public void destroy() {

        // this function will allow Java3D to clean up upon quitting
        this.simpleUniverse.removeAllLocales();

    }
}