package control;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import view.TriangleViewer;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;

public class PickingEnvironment implements MouseListener,MouseMotionListener  {

	PickCanvas pickCanvas;
	NewMouseRotate mouseRotate = null;
	int xPressed;
	int yPressed;
	

	public PickingEnvironment(Canvas3D c, BranchGroup group) {

		Frame frame = new Frame("oooo");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvent) {
				System.exit(0);
			}
		});

		pickCanvas = new PickCanvas(c, group);
		pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
		c.addMouseListener(this);
		c.addMouseMotionListener(this);


	}

	public NewMouseRotate getMouseRotate() {
		return mouseRotate;
	}

	public void setMouseRotate(NewMouseRotate mouseRotate) {
		this.mouseRotate = mouseRotate;
	}

	public void mouseClicked(MouseEvent e) {
		
		int buttonDown = e.getButton();
		 
	    if (buttonDown == MouseEvent.BUTTON1) {
	           // Bouton GAUCHE enfonc�
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
					mouseRotate.setCenter(s);
				} else {
					System.out.println("null");
				}
			}
	    }
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse Pressed");
		xPressed = e.getX();
		yPressed = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int buttonDown = e.getButton();
	    if (buttonDown == MouseEvent.BUTTON1) {
			
			for(int x=xPressed; x<e.getX(); x=x+5 ){
				for(int y=yPressed; y<e.getY(); y=y+5){
					pickCanvas.setShapeLocation(x, y);
					PickResult result = pickCanvas.pickClosest();
					if (result == null) {
						System.out.println("Nothing picked");
					} else {
						TriangleViewer s = (TriangleViewer) result.getNode(PickResult.SHAPE3D);
						if (s != null) {
							System.out.println(s.getClass().getName());
							s.select();
							s.changeColor();
							mouseRotate.setCenter(s);
						} else {
							System.out.println("null");
						}
					}		
				}
			}
	    }
	}
	
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		//System.out.println("position is: " + arg0.getX() + "  " + arg0.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

}
