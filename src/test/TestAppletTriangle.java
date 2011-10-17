package test;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import model.Edge;
import model.Point;
import model.Triangle;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class TestAppletTriangle extends Applet {

    private static final long serialVersionUID = 1L;

    SimpleUniverse simpleUniverse;

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

        this.simpleUniverse.addBranchGraph(this.createSceneGraph());
    }

    /**
     * Creates a basic SceneGraph. Here we will create a basic SceneGraph with a
     * ColorCube object.
     * @return the branch group describing the scene graph
     */
    public BranchGroup createSceneGraph() {

        // Creates the big brother branch... which will contain all the things
        // created in this method.
        BranchGroup objRoot = new BranchGroup();

        // Adds a ColorCubeTranslated with mouse control
        objRoot.addChild(TestAppletTriangle.createTriangleTranslated());
        
        //Add light in the scene
        Color3f light1Color = new Color3f(Color.white);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0); 
        Vector3f light1Direction = new Vector3f(1.0f, -1.0f, -1.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);
        
        // Translates camera
        this.translateCamera();

        objRoot.compile();

        return objRoot;
    }

    public void translateCamera() {
        // Gets the ViewingPlatform of the SimpleUniverse
        ViewingPlatform camera = this.simpleUniverse.getViewingPlatform();

        // Gets the TransformGroup associated
        TransformGroup cameraTransformGroup = camera.getMultiTransformGroup()
                .getTransformGroup(0);

        // Creates a Transform3D for the ViewingPlatform
        Transform3D cameraTranslation = new Transform3D();
        // Gets the current 3d from the ViewingPlatform
        cameraTransformGroup.getTransform(cameraTranslation);

        // Sets view to x=0, y=0, z= 5
        cameraTranslation.setTranslation(new Vector3f(0.0f, 0.0f, 5.0f));
        // Assigns Transform3D to ViewPlatform
        cameraTransformGroup.setTransform(cameraTranslation);
    }

    public static TransformGroup createTriangleTranslated() {
        // Creates a TransformGroup for the ColorCube called transformGroup
        TransformGroup transformGroup = new TransformGroup();

        // Creates a Triangle object and adds it to the transform group
        // FIXME : change this thing...
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(-1, 0, 0);
        Point p3 = new Point(0, -1, 0);
        Edge e1 = new Edge(p1, p2);
        Edge e2 = new Edge(p2, p3);
        Edge e3 = new Edge(p1, p3);
        Vector3d normal = new Vector3d(0, 0, 1);
        Triangle triangle = new Triangle(p1, p2, p3, e1, e2, e3, normal);

        Shape3D shape = new Shape3D();

        TriangleArray triangle1 = new TriangleArray(3,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);

        triangle1.setCoordinate(0, new Point3d(triangle.getP1().getX(),
                triangle.getP1().getY(), triangle.getP1().getZ()));
        triangle1.setCoordinate(1, new Point3d(triangle.getP2().getX(),
                triangle.getP2().getY(), triangle.getP2().getZ()));
        triangle1.setCoordinate(2, new Point3d(triangle.getP3().getX(),
                triangle.getP3().getY(), triangle.getP3().getZ()));
        //triangle1.setColor(0, new Color3f(1, 0, 0));
        //triangle1.setColor(1, new Color3f(1, 0, 0));
        //triangle1.setColor(2, new Color3f(1, 0, 0));
        triangle1.setNormal(0, new Vector3f(0,0,1));
        triangle1.setNormal(1, new Vector3f(0,0,1));
        triangle1.setNormal(2, new Vector3f(0,0,1));

        TriangleArray triangle2 = new TriangleArray(3,
                GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);
        
        triangle2.setCoordinate(0, new Point3d(triangle.getP2().getX(),
                triangle.getP2().getY(), triangle.getP2().getZ()));
        triangle2.setCoordinate(1, new Point3d(triangle.getP1().getX(),
                triangle.getP1().getY(), triangle.getP1().getZ()));
        triangle2.setCoordinate(2, new Point3d(triangle.getP3().getX(),
                triangle.getP3().getY(), triangle.getP3().getZ()));
        //triangle2.setColor(0, new Color3f(0, 1, 0));
        //triangle2.setColor(1, new Color3f(0, 1, 0));
        //triangle2.setColor(2, new Color3f(0, 1, 0));
        triangle2.setNormal(0, new Vector3f(0,0,-1));
        triangle2.setNormal(1, new Vector3f(0,0,-1));
        triangle2.setNormal(2, new Vector3f(0,0,-1));
        
        
        
        shape.addGeometry(triangle1);
        shape.addGeometry(triangle2);
        

        Appearance app = new Appearance();
        Material mat = new Material(new Color3f(Color.red),new Color3f(Color.black),new Color3f(Color.red),new Color3f(Color.red),64);
        mat.setColorTarget(3);
        app.setMaterial(mat);
        shape.setAppearance(app); 
                       
		Sphere sphere = new Sphere(0.3f);
		sphere.setAppearance(app);
		transformGroup.addChild(sphere);
        
        // Adds the ColorCube to transformGroup
        transformGroup.addChild(shape);

        //

        //

        // Creates the Transform3D which will allow a TransformGroup to move
        Transform3D translation = new Transform3D();
        // Sets translation to x=0.8, y=1.0, z= -2.0
        translation.setTranslation(new Vector3f(-1.75f, -1.75f, -1.75f));

        // Sets Transform for TransformGroup
        transformGroup.setTransform(translation);

        //

        //

        // Creates a bounding sphere for the mouse translate, mouse rotate and
        // mouse zoom transformation
        BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,
                0.0, 0.0), 10000.);

        // Very important : authorize the scene graph to move during the
        // execution of the program (as we want)
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Links the left button of the mouse with a rotation transformation
        MouseRotate mouseRotate = new MouseRotate();
        mouseRotate.setTransformGroup(transformGroup);
        transformGroup.addChild(mouseRotate);
        mouseRotate.setSchedulingBounds(boundingSphere);

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

        // this function will allow Java3D to clean up upon quiting
        this.simpleUniverse.removeAllLocales();

    }
}