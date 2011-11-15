package control;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;


import model.Triangle;

import view.TriangleMeshView;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;


public class PickingEnvironment implements MouseListener, MouseMotionListener {

    PickCanvas pickCanvas;
    NewMouseRotate mouseRotate = null;
    int xPressed;
    int yPressed;

    public PickingEnvironment(Canvas3D c, BranchGroup group) {

        Frame frame = new Frame("oooo");
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent winEvent) {
                System.exit(0);
            }
        });

        this.pickCanvas = new PickCanvas(c, group);
        this.pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
        c.addMouseListener(this);
        c.addMouseMotionListener(this);

    }

    public NewMouseRotate getMouseRotate() {
        return this.mouseRotate;
    }

    public void setMouseRotate(NewMouseRotate mouseRotateIn) {
        this.mouseRotate = mouseRotateIn;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int buttonDown = e.getButton();

        if (buttonDown == MouseEvent.BUTTON1) {

            // Bouton gauche enfonc

            this.pickCanvas.setShapeLocation(e);
            PickResult result = this.pickCanvas.pickClosest();
            if (result == null) {
                System.out.println("Nothing picked");
            } else {

            	PickIntersection PI=result.getIntersection(0);
            	int []PointIndex=PI.getPrimitiveVertexIndices();
            	int TriangleIndex=PointIndex[0]/3;
            	TriangleMeshView triangleMeshView=(TriangleMeshView)PI.getGeometryArray();
            	triangleMeshView.selectOrUnselect(TriangleIndex);                 
                mouseRotate.setCenter(triangleMeshView.getTriangleArray().get(TriangleIndex));

            }
        }
    }
//    public void mouseClicked(MouseEvent e) {
//        int buttonDown = e.getButton();
//
//        if (buttonDown == MouseEvent.BUTTON1) {
//
//            // Bouton gauche enfonc
//
//            this.pickCanvas.setShapeLocation(e);
//            PickResult result = this.pickCanvas.pickClosest();
//            if (result == null) {
//                System.out.println("Nothing picked");
//            } else {
//
//            	PickIntersection PI=result.getIntersection(0);
//            	int []PointIndex=PI.getPrimitiveVertexIndices();
//            	int TriangleIndex=PointIndex[0]/3;
//            	TriangleMeshView triangleMeshView=(TriangleMeshView)PI.getGeometryArray();
//            	triangleMeshView.selectOrUnselect(TriangleIndex); 
//            	List<Integer>triangleSelected=new ArrayList<Integer>();
//            	List<Integer>triangleNewSelected=new ArrayList<Integer>();
//            	triangleSelected.add(TriangleIndex);
//            	triangleNewSelected.add(TriangleIndex);
//            	int turn=10;
//            	selectVoisin(triangleSelected,triangleNewSelected,triangleMeshView,turn);
//                mouseRotate.setCenter(triangleMeshView.getTriangleArray().get(TriangleIndex));
//
//            }
//        }
//    }
    //slectionner des triangles autour du triangle clique, turn est le tour de selection. le premier tour,
    //on selectionne trois voisins du triangle clique, le deuxieme tour, on selectionne des voisins de ces trois triangles,
    //pour chaque tour, on refait comme ca
    //triangleSelected pour recuperer des triangles selectionnes
    //triangleNewSelected sauvgarder des triangels selctionnes dans chaque tour
    public void selectVoisin(List<Integer>triangleSelected,List<Integer>triangleNewSelected,TriangleMeshView triangleMeshView,int turn){
 	   for(int i=0;i<turn;i++){
 		   List<Integer>triangleCount=new ArrayList<Integer>();
 		   for(int j:triangleNewSelected){
 			   List<Triangle> triangleNeighbour=triangleMeshView.getTriangleArray().get(j).getTriangle().getNeighbours();
 			  
 			  for(int l=0;l<triangleNeighbour.size();l++){
 			   for(int k=0;k<triangleMeshView.getTriangleArray().size();k++){
 				   
 				   if(triangleMeshView.getTriangleArray().get(k).getTriangle().equals(triangleNeighbour.get(l))){
 					   triangleMeshView.select(k);
 					   triangleSelected.add(k);
 					   triangleCount.add(k);
 					    break;
		   
 				   }
 				   
 			   }
 			  }
 			  
 		   }
 		   triangleNewSelected.clear();
 		   for(int m=0;m<triangleCount.size();m++){
 			   triangleNewSelected.add(triangleCount.get(m));
 		   }
 			  
 			  triangleCount.clear();
 	   }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
        this.xPressed = e.getX();
        this.yPressed = e.getY();
    }

    @Override

    public void mouseReleased(MouseEvent e) {
        int buttonDown = e.getButton();
        if (buttonDown == MouseEvent.BUTTON1) {
        	//puisque chaque fois on selectionne certains triangles autour du triangle pique, donc on peut agrandir le pas de recurrence
            for (int x = this.xPressed; x < e.getX(); x = x + 10) {
                for (int y = this.yPressed; y < e.getY(); y = y + 10) {
                    this.pickCanvas.setShapeLocation(x, y);
                   
                    PickResult result = this.pickCanvas.pickClosest();
                    if (result == null) {
                        System.out.println("Nothing picked");
                    } else {
                    	PickIntersection PI=result.getIntersection(0);
                    	int []PointIndex=PI.getPrimitiveVertexIndices();
                    	int TriangleIndex=PointIndex[0]/3;
                    	TriangleMeshView triangleMeshView=(TriangleMeshView)PI.getGeometryArray();
                        triangleMeshView.select(TriangleIndex);
                        List<Integer>triangleSelected=new ArrayList<Integer>();
                    	List<Integer>triangleNewSelected=new ArrayList<Integer>();
                        triangleSelected.add(TriangleIndex);
                    	triangleNewSelected.add(TriangleIndex);
                    	int turn=3;
                    	selectVoisin(triangleSelected,triangleNewSelected,triangleMeshView,turn);
                        mouseRotate.setCenter(triangleMeshView.getTriangleArray().get(TriangleIndex));

                    }
                }
            }
        }
    }
  
   
   
    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub
    	
    	
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

