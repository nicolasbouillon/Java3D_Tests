package control;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;


import model.Triangle;

import view.TriangleViewer;

import com.sun.j3d.utils.picking.PickCanvas;

import com.sun.j3d.utils.picking.PickIntersection;
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
//////////////////////////test pour essayer selectionner un triangle
//public void mouseClicked(MouseEvent e) {
//		
//		int buttonDown = e.getButton();
//		 
//	    if (buttonDown == MouseEvent.BUTTON1) {
//	           // Bouton GAUCHE enfonc�
//			pickCanvas.setShapeLocation(e);
//			PickResult result = pickCanvas.pickClosest();
//			
//			if (result == null) {
//				System.out.println("Nothing picked");
//			} else {
//				Shape3D s = (Shape3D) result.getNode(PickResult.SHAPE3D);
//				
//				if (s != null) {
//					System.out.println(s.getClass().getName());
//					
//					PickIntersection PI=result.getIntersection(0);
//					TriangleArray TA = new TriangleArray(3,
//			                 GeometryArray.COORDINATES | GeometryArray.COLOR_3 | GeometryArray.NORMALS);
//			        TA=(TriangleArray) PI.getGeometryArray();
//			        Point3d[] PointSelect1=new Point3d[3];
//			        TA.getCoordinate(0, PointSelect1[0]);
//			        TA.getCoordinate(1, PointSelect1[1]);
//			        TA.getCoordinate(2, PointSelect1[2]);
//			        System.out.println(PointSelect1[0].x);
//			        System.out.println(PointSelect1[0].y);
//			        System.out.println(PointSelect1[0].z);
//			        System.out.println(PointSelect1[1].x);
//			        System.out.println(PointSelect1[1].y);
//			        System.out.println(PointSelect1[1].z);
//			        System.out.println(PointSelect1[2].x);
//			        System.out.println(PointSelect1[2].y);
//			        System.out.println(PointSelect1[2].z);
//			        
//					Point3d[] PointSelect=new Point3d[3];
//                    PointSelect=PI.getPrimitiveCoordinates();
//	                System.out.println(PointSelect[0].x);
//	                System.out.println(PointSelect[0].y);
//	                System.out.println(PointSelect[0].z);
//	                
////					s.selectOrUnselect();
////					s.changeColor();
//					mouseRotate.setCenter(PointSelect[0],PointSelect[1],PointSelect[2]);
//				} else {
//					System.out.println("null");
//				}
//			}
//	    }
//	}
	/////////////////////////
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
					//////////////////////
//					for(int i=0;i<s.getTriangle().getNumNeighbours();i++){
//						List<Triangle> neighbour=s.getTriangle().getNeighbours();
//						System.out.println(neighbour.get(i).getP1().getX());
//						System.out.println(neighbour.get(i).getP1().getY());
//						System.out.println(neighbour.get(i).getP1().getZ());
//					}
					//////////////////////
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
			
			for(int x=xPressed; x<e.getX(); x=x+2 ){
				for(int y=yPressed; y<e.getY(); y=y+2){
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
