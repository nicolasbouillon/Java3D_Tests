package test;


import java.awt.event.*;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

// SimpleBehaviorApp renders a single, rotated cube.

public class SimpleBehavior2 extends Behavior {

	private TransformGroup targetTG;
	private TransformGroup Translation1;
	private TransformGroup Translation2;
	private Transform3D Tran1 = new Transform3D();
	private Transform3D Tran2 = new Transform3D();
	private Transform3D rotation = new Transform3D();
	private Point3d center = new Point3d();
	private Angle angle;

	// create SimpleBehavior - set TG object of change

	SimpleBehavior2(TransformGroup targetTG, TransformGroup Translation1,
			TransformGroup Translation2, Point3d center, Angle angle,
			Transform3D rotation, Transform3D Tran1, Transform3D Tran2) {
		this.targetTG = targetTG;
		this.Translation1 = Translation1;
		this.Translation2 = Translation2;
		this.center = center;
		this.angle = angle;
		this.rotation = rotation;
		this.Tran1 = Tran1;
		this.Tran2 = Tran2;

	}

	// initialize the Behavior
	// set initial wakeup condition
	// called when behavior becomes live
	public void initialize() {
		this.wakeupOn(new WakeupOnAWTEvent(MouseEvent.MOUSE_WHEEL));
	}

	// called by Java 3D when appropriate stimulus occurs
	public void processStimulus(Enumeration criteria) {
		Point3d pointBouge = new Point3d();
		pointBouge.x = this.center.getX();
		pointBouge.y = this.center.getY();
		pointBouge.z = this.center.getZ();
		this.Tran1.transform(pointBouge);
		this.rotation.transform(pointBouge);
		this.Tran2.transform(pointBouge);
		Vector3d vector1 = new Vector3d();
		Vector3d vector2 = new Vector3d();
		vector2.set(pointBouge.x, pointBouge.y, pointBouge.z);

		vector1.set(-this.center.getX(), -this.center.getY(),
				-this.center.getZ());

		this.Tran2.setTranslation(vector2);
		this.Tran1.setTranslation(vector1);

		Translation1.setTransform(Tran1);
		Translation2.setTransform(Tran2);
		angle.setAngle(angle.getAngle() + 0.1);

		rotation.rotY(angle.getAngle());

		targetTG.setTransform(rotation);
		this.wakeupOn(new WakeupOnAWTEvent(MouseEvent.MOUSE_WHEEL));
	}

}
