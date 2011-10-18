package control;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import view.TriangleViewer;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;

public class PickingEnvironment extends MouseAdapter {

	PickCanvas pickCanvas;

	public PickingEnvironment(Canvas3D c, BranchGroup group) {

		Frame frame = new Frame("oooo");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvent) {
				System.exit(0);
			}
		});

		pickCanvas = new PickCanvas(c, group);
		pickCanvas.setMode(PickCanvas.BOUNDS);
		c.addMouseListener(this);

	}

	public void mouseClicked(MouseEvent e) {
		
		pickCanvas.setShapeLocation(e);
		PickResult result = pickCanvas.pickClosest();
		if (result == null) {
			System.out.println("Nothing picked");
		} else {
			TriangleViewer s = (TriangleViewer) result.getNode(PickResult.SHAPE3D);
			if (s != null) {
				System.out.println(s.getClass().getName());
				s.selectOrUnselect();
				s.changeColor();
			} else {
				System.out.println("null");
			}
		}
	}

}
