package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Locale;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import util.ParserSTL;

import view.TriangleMeshViewer;
import view.TriangleViewer;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;



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
    protected void addCanvas3D(Canvas3D c3d) {
        setLayout(new BorderLayout());
        add(c3d, BorderLayout.CENTER);
        doLayout();

        if (this.m_SceneBranchGroup != null) {
            c3d.addMouseListener(this);

            this.pickCanvas = new PickCanvas(c3d, this.m_SceneBranchGroup);
            this.pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
            this.pickCanvas.setTolerance(4.0f);
        }

        c3d.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    @Override
    protected BranchGroup createSceneBranchGroup() {
        BranchGroup objRoot = super.createSceneBranchGroup();

        objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        //light bound
        //Bounds lightBounds = getApplicationBounds();
        BoundingSphere lightBounds=new BoundingSphere(new Point3d(0.0,0.0,0.0),1000.0);

        //Ambient light
        AmbientLight ambLight = new AmbientLight(true, new Color3f(1.0f, 1.0f,
                1.0f));
        ambLight.setInfluencingBounds(lightBounds);
        objRoot.addChild(ambLight);
        //directional light
       //DirectionalLight headLight = new DirectionalLight();
        DirectionalLight headLight = new DirectionalLight(new Color3f(Color.white),new Vector3f(1.0f,-1.0f,-1.0f));
        headLight.setInfluencingBounds(lightBounds);
        objRoot.addChild(headLight);

        TransformGroup mouseGroup = createMouseBehaviorsGroup();
        BranchGroup sceneRoot = new BranchGroup();
        objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objRoot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        this.meshViewer.createTriangleShapes();
        for (TriangleViewer shape : this.meshViewer) {
            sceneRoot.addChild(shape);
        }

        mouseGroup.addChild(sceneRoot);

        objRoot.addChild(mouseGroup);

        return objRoot;
    }

    // Juste du contrôle souris...
    private TransformGroup createMouseBehaviorsGroup() {
        BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,
                0.0, 0.0), 10000.);

        TransformGroup examineGroup = new TransformGroup();
        examineGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        examineGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Bounds behaviorBounds = getApplicationBounds();

        MouseRotate mr = new MouseRotate(examineGroup);
        mr.setSchedulingBounds(behaviorBounds);
        examineGroup.addChild(mr);
        mr.setSchedulingBounds(boundingSphere);

        MouseTranslate mt = new MouseTranslate(examineGroup);
        mt.setSchedulingBounds(behaviorBounds);
        examineGroup.addChild(mt);
        mt.setSchedulingBounds(boundingSphere);

        MouseZoom mz = new MouseZoom(examineGroup);
        mz.setSchedulingBounds(behaviorBounds);
        examineGroup.addChild(mz);
        mz.setSchedulingBounds(boundingSphere);

        return examineGroup;
    }

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

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        System.out.println("Current user.dir: "
                + System.getProperty("user.dir"));

        STLPickingTest pickingTest = new STLPickingTest(args);

        new MainFrame(pickingTest, 400, 400);
    }
}
