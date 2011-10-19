package test;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;

import javax.media.j3d.AudioDevice;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Group;
import javax.media.j3d.Locale;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.audioengines.javasound.JavaSoundMixer;

// *****************************************************************************
/**
 * Java3dApplet Base class for defining a Java 3D applet. Contains some useful
 * methods for defining views and scenegraphs etc.
 * @author Daniel Selman
 * @version 1.0
 */
// *****************************************************************************

public abstract class Java3dApplet extends Applet {

    private static final long serialVersionUID = 1L;

    public static int m_kWidth = 300;

    public static int m_kHeight = 300;

    protected String[] m_szCommandLineArray;

    protected VirtualUniverse m_Universe;

    protected BranchGroup m_SceneBranchGroup;

    protected Bounds m_ApplicationBounds;

    protected com.tornadolabs.j3dtree.Java3dTree m_Java3dTree;

    public Java3dApplet() {
    }

    public static boolean isApplet() {
        try {
            System.getProperty("user.dir");
            System.out.println("Running as Application.");
            return false;
        } catch (Exception e) {
            //
        }

        System.out.println("Running as Applet.");
        return true;
    }

    public URL getWorkingDirectory() {
        try {
            File file = new File(System.getProperty("user.dir"));
            System.out.println("Running as Application:");
            System.out.println("   " + file.toURI().toURL());
            return file.toURI().toURL();
        } catch (Exception e) {
            //
        }

        System.out.println("Running as Applet:");
        System.out.println("   " + getCodeBase());

        return getCodeBase();
    }

    public VirtualUniverse getVirtualUniverse() {
        return this.m_Universe;
    }

    public com.tornadolabs.j3dtree.Java3dTree getJ3dTree() {
        return this.m_Java3dTree;
    }

    public Locale getFirstLocale() {
        Enumeration<?> enume = this.m_Universe.getAllLocales();

        if (enume.hasMoreElements() != false)
            return (Locale) enume.nextElement();

        return null;
    }

    protected Bounds getApplicationBounds() {
        if (this.m_ApplicationBounds == null)
            this.m_ApplicationBounds = createApplicationBounds();

        return this.m_ApplicationBounds;
    }

    protected Bounds createApplicationBounds() {
        this.m_ApplicationBounds = new BoundingSphere(
                new Point3d(0.0, 0.0, 0.0), 100.0);
        return this.m_ApplicationBounds;
    }

    protected Background createBackground() {
        Background back = new Background(new Color3f(0.9f, 0.9f, 0.9f));
        back.setApplicationBounds(createApplicationBounds());
        return back;
    }

    public void initJava3d() {
        this.m_Java3dTree = new com.tornadolabs.j3dtree.Java3dTree();
        this.m_Universe = createVirtualUniverse();

        Locale locale = createLocale(this.m_Universe);

        BranchGroup sceneBranchGroup = this.createSceneBranchGroup();

        ViewPlatform vp = createViewPlatform();
        BranchGroup viewBranchGroup = createViewBranchGroup(
                getViewTransformGroupArray(), vp);

        createView(vp);

        Background background = createBackground();

        if (background != null)
            sceneBranchGroup.addChild(background);

        this.m_Java3dTree.recursiveApplyCapability(sceneBranchGroup);
        this.m_Java3dTree.recursiveApplyCapability(viewBranchGroup);

        locale.addBranchGraph(sceneBranchGroup);
        addViewBranchGroup(locale, viewBranchGroup);

        onDoneInit();
    }

    protected void onDoneInit() {
        this.m_Java3dTree.updateNodes(this.m_Universe);
    }

    protected static double getScale() {
        return 1.0;
    }

    @SuppressWarnings("static-method")
    public TransformGroup[] getViewTransformGroupArray() {
        TransformGroup[] tgArray = new TransformGroup[1];
        tgArray[0] = new TransformGroup();

        // move the camera BACK a little...
        // note that we have to invert the matrix as
        // we are moving the viewer
        Transform3D t3d = new Transform3D();
        t3d.setScale(getScale());
        t3d.setTranslation(new Vector3d(0.0, 0.0, -20.0));
        t3d.invert();
        tgArray[0].setTransform(t3d);

        return tgArray;
    }

    protected static void addViewBranchGroup(Locale locale, BranchGroup bg) {
        locale.addBranchGraph(bg);
    }

    protected static Locale createLocale(VirtualUniverse u) {
        return new Locale(u);
    }

    protected BranchGroup createSceneBranchGroup() {
        this.m_SceneBranchGroup = new BranchGroup();
        return this.m_SceneBranchGroup;
    }

    protected View createView(ViewPlatform vp) {
        View view = new View();

        PhysicalBody pb = createPhysicalBody();
        PhysicalEnvironment pe = createPhysicalEnvironment();

        AudioDevice audioDevice = createAudioDevice(pe);

        if (audioDevice != null) {
            pe.setAudioDevice(audioDevice);
            audioDevice.initialize();
        }

        view.setPhysicalEnvironment(pe);
        view.setPhysicalBody(pb);

        if (vp != null)
            view.attachViewPlatform(vp);

        view.setBackClipDistance(getBackClipDistance());
        view.setFrontClipDistance(getFrontClipDistance());

        Canvas3D c3d = createCanvas3D();
        view.addCanvas3D(c3d);
        addCanvas3D(c3d);

        return view;
    }

    protected static PhysicalBody createPhysicalBody() {
        return new PhysicalBody();
    }

    protected static AudioDevice createAudioDevice(PhysicalEnvironment pe) {
        JavaSoundMixer javaSoundMixer = new JavaSoundMixer(pe);

        return javaSoundMixer;
    }

    protected static PhysicalEnvironment createPhysicalEnvironment() {
        return new PhysicalEnvironment();
    }

    protected static float getViewPlatformActivationRadius() {
        return 100;
    }

    protected static ViewPlatform createViewPlatform() {
        ViewPlatform vp = new ViewPlatform();
        vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
        vp.setActivationRadius(getViewPlatformActivationRadius());

        return vp;
    }

    protected static Canvas3D createCanvas3D() {
        GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
        gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
        GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices();

        Canvas3D c3d = new Canvas3D(gd[0].getBestConfiguration(gc3D));
        c3d.setSize(getCanvas3dWidth(c3d), getCanvas3dHeight(c3d));

        return c3d;
    }

    /**
     * @param c3d
     */
    protected static int getCanvas3dWidth(Canvas3D c3d) {
        return m_kWidth;
    }

    /**
     * @param c3d
     */
    protected static int getCanvas3dHeight(Canvas3D c3d) {
        return m_kHeight;
    }

    protected static double getBackClipDistance() {
        return 100.0;
    }

    protected static double getFrontClipDistance() {
        return 1.0;
    }

    protected static BranchGroup createViewBranchGroup(
            TransformGroup[] tgArray, ViewPlatform vp) {
        BranchGroup vpBranchGroup = new BranchGroup();

        if (tgArray != null && tgArray.length > 0) {
            Group parentGroup = vpBranchGroup;
            TransformGroup curTg = null;

            for (int n = 0; n < tgArray.length; n++) {
                curTg = tgArray[n];
                parentGroup.addChild(curTg);
                parentGroup = curTg;
            }

            tgArray[tgArray.length - 1].addChild(vp);
        } else
            vpBranchGroup.addChild(vp);

        return vpBranchGroup;
    }

    protected void addCanvas3D(Canvas3D c3d) {
        setLayout(new BorderLayout());
        add(c3d, BorderLayout.CENTER);
        doLayout();
    }

    protected static VirtualUniverse createVirtualUniverse() {
        return new VirtualUniverse();
    }

    protected void saveCommandLineArguments(String[] szArgs) {
        this.m_szCommandLineArray = szArgs;
    }

    protected String[] getCommandLineArguments() {
        return this.m_szCommandLineArray;
    }
}
