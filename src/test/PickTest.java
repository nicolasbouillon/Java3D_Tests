package test;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;






import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;



import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;



import javax.vecmath.Vector3f;



import com.sun.j3d.utils.applet.MainFrame;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.pickfast.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class PickTest extends Applet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;








	public PickTest() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		BranchGroup scene = createSceneGraph(canvas3D);
		scene.compile();

		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
	}


	
	
	public BranchGroup createSceneGraph(Canvas3D canvas) {
		 // Create the root of the branch graph
		 BranchGroup objRoot = new BranchGroup();
		
		 TransformGroup objRotate = null;
		 PickRotateBehavior pickRotate = null;
		 Transform3D transform = new Transform3D();
		 BoundingSphere behaveBounds = new BoundingSphere();
		
		 // create ColorCube and PickRotateBehavior objects
		 transform.setTranslation(new Vector3f(-0.6f, 0.0f, -0.6f));
		 objRotate = new TransformGroup(transform);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		 objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
				 objRoot.addChild(objRotate);
		 objRotate.addChild(new ColorCube(0.4));
		 pickRotate = new PickRotateBehavior(objRoot,canvas, behaveBounds);

		 objRoot.addChild(pickRotate);
		 // add a second ColorCube object to the scene graph
		 transform.setTranslation(new Vector3f( 0.6f, 0.0f, -0.6f));
		 objRotate = new TransformGroup(transform);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		 objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		 objRoot.addChild(objRotate);
		 objRotate.addChild(new ColorCube(0.4));
		
		 // Let Java 3D perform optimizations on this scene graph.
		 objRoot.compile();
		
		 return objRoot;
		 }
	
	public static void main(String[] args) {
		new MainFrame(new PickTest(), 500, 500);
	}

}