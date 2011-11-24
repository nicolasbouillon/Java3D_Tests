package control;


import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import javax.media.j3d.Shape3D;

import javax.vecmath.Vector3d;

import model.Triangle;


import view.MeshList;
import view.TriangleMeshView;
import view.TriangleView;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;


public class PickingEnvironment implements MouseListener, MouseMotionListener {

    PickCanvas pickCanvas;
    NewMouseRotate mouseRotate = null;
    int xPressed;
    int yPressed;
    List<Integer> trianglePicked=new ArrayList<Integer>();
    ArrayList<Integer>selectedIndex=new ArrayList<Integer>();
    
    private ArrayList<TriangleView> trianglesViewSelected=new ArrayList<TriangleView>();
    private ArrayList<TriangleMeshView> triangleMesh=new ArrayList<TriangleMeshView>();
    private TriangleMeshView triangleMeshView;
    private TriangleMeshView triangleMeshLastSelected;
    private Shape3D shape3DLastSelected;
    

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
            	this.triangleMeshView=triangleMeshView;
            	
            	if(this.triangleMeshView.getMeshSelectedOrNot()==false){
            	  	//return the shape3D selected
                	Shape3D shapePicked=(Shape3D)result.getNode(PickResult.SHAPE3D);
                	this.selectShape3D(shapePicked);
                	this.triangleMeshView.selectMesh();
                	if(this.shape3DLastSelected!=null){
                		if(this.shape3DLastSelected!=shapePicked){
                			this.unselectShape3D(this.shape3DLastSelected);
                			this.triangleMeshLastSelected.unselectMesh();
                			
                		}
                		
                	}
                	this.shape3DLastSelected=shapePicked;
                	this.triangleMeshLastSelected=this.triangleMeshView;
            	}
            	
            	else{
            		triangleMeshView.select(TriangleIndex);
                	if(trianglePicked.contains(TriangleIndex)==false){
                		//this.trianglePicked.add(TriangleIndex);
                		this.trianglesViewSelected.add(this.triangleMeshView.getTriangleArray().get(TriangleIndex));
                		///////////
                		this.triangleMesh.add(triangleMeshView);
                		////////////
                		this.selectedIndex.add(this.trianglesViewSelected.size()-1);
                	}          	
                    this.mouseRotate.setCenter(triangleMeshView.getTriangleArray().get(TriangleIndex));
            	}
            	
                

            }
        }
        else if (buttonDown == MouseEvent.BUTTON3) {

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
            	this.triangleMeshView=triangleMeshView;
            	
            	if(this.triangleMeshView.getMeshSelectedOrNot()==false){
            	  	//return the shape3D selected
                	Shape3D shapePicked=(Shape3D)result.getNode(PickResult.SHAPE3D);
                	this.selectShape3D(shapePicked);
                	this.triangleMeshView.selectMesh();
                	if(this.shape3DLastSelected!=null){
                		if(this.shape3DLastSelected!=shapePicked){
                			this.unselectShape3D(this.shape3DLastSelected);
                			this.triangleMeshLastSelected.unselectMesh();
                			
                		}
                		
                	}
                	this.shape3DLastSelected=shapePicked;
                	this.triangleMeshLastSelected=this.triangleMeshView;
            	}
            	else{
            		triangleMeshView.select(TriangleIndex);
                	if(this.trianglesViewSelected.contains(this.triangleMeshView.getTriangleArray().get(TriangleIndex))==false){
                		this.trianglesViewSelected.add(this.triangleMeshView.getTriangleArray().get(TriangleIndex));
                		/////////////////////
                		this.triangleMesh.add(triangleMeshView);
                		//////////////////////
                	}       
                	
                	List<Integer>triangleNewSelected=new ArrayList<Integer>();
                	
                	triangleNewSelected.add(TriangleIndex);
                	//this.trianglePicked.add(TriangleIndex);
                	int turn=30;
                	selectVoisin(TriangleIndex,triangleNewSelected,triangleMeshView,turn);
                    this.mouseRotate.setCenter(triangleMeshView.getTriangleArray().get(TriangleIndex));
                    this.selectedIndex.add(this.trianglesViewSelected.size()-1);
            	}
            	
            	
            	
            	

            }
        }
        //click the wheel all the triangles selected will be canceled
        else if (buttonDown == MouseEvent.BUTTON2){
        	for(int i=0;i<this.trianglesViewSelected.size();i++){
        		int index=this.trianglesViewSelected.get(i).getTriangle().getTriangleViewIndex();
        		this.triangleMesh.get(i).unselect(index);
        	}
        	this.trianglesViewSelected.clear();
        	this.selectedIndex.clear();
        	this.triangleMesh.clear();
//this is the method to cancled a zone of triangles, don't delete it 
//        	 this.pickCanvas.setShapeLocation(e);
//             PickResult result = this.pickCanvas.pickClosest();
//             if (result == null) {
//                 System.out.println("Nothing canceled");
//             } else {
//
//             	PickIntersection PI=result.getIntersection(0);
//             	int []PointIndex=PI.getPrimitiveVertexIndices();
//             	int TriangleIndex=PointIndex[0]/3;
//             	TriangleMeshView triangleMeshView=(TriangleMeshView)PI.getGeometryArray();
//             	this.triangleMeshView=triangleMeshView;
//
//             	
//             	if(this.trianglesViewSelected.contains(this.triangleMeshView.getTriangleArray().get(TriangleIndex))){
//             		
//             		int triangleIndex=this.trianglesViewSelected.indexOf(this.triangleMeshView.getTriangleArray().get(TriangleIndex));
//             		int beginIndex=0;
//             		int finishIndex=0;
//             		int indexOfBeginIndex=0;
//             		int indexOfFinishIndex=0;
//             		for(int i=0;i<this.selectedIndex.size();i++){
//             			if(i==0&&this.selectedIndex.get(i)>=triangleIndex){
//             				beginIndex=-1;
//             				finishIndex=this.selectedIndex.get(i);
//             				break;
//             			}
//             			else{
//             				if(this.selectedIndex.get(i)<triangleIndex&&this.selectedIndex.get(i+1)>=triangleIndex){
//             					beginIndex=this.selectedIndex.get(i);
//             					finishIndex=this.selectedIndex.get(i+1);
//             					indexOfBeginIndex=i;
//             					indexOfFinishIndex=i+1;
//             					break;
//             				}
//             			}
//             		}
//             		
//             		for(int i=finishIndex;i>beginIndex;i--){
//             			TriangleView triangleSelected=this.trianglesViewSelected.get(i);
//             			int index=triangleSelected.getTriangle().getTriangleViewIndex();
//             			this.triangleMeshView.unselect(index);
//             			this.trianglesViewSelected.remove(i);
//             			
//             		}
//             		
//             		this.selectedIndex.remove(indexOfFinishIndex);
//             		for(int i=indexOfFinishIndex;i<this.selectedIndex.size();i++){
//             			int index=this.selectedIndex.get(i);
//             			this.selectedIndex.set(i, index-(finishIndex-beginIndex));
//             			
//             		}
//             		
//             	}          	
//                 
//
//             }
        }

    }

   
    //slectionner des triangles autour du triangle clique, turn est le tour de selection. le premier tour,
    //on selectionne trois voisins du triangle clique, le deuxieme tour, on selectionne des voisins de ces trois triangles,
    //pour chaque tour, on refait comme ca
    //triangleSelected pour recuperer des triangles selectionnes
    //triangleNewSelected sauvgarder des triangels selctionnes dans chaque tour
    public void selectVoisin(int TriangleIndex,List<Integer>triangleNewSelected,TriangleMeshView triangleMeshView,int turn){
    	
    	Vector3d normal=triangleMeshView.getTriangleArray().get(TriangleIndex).getTriangle().getNormal();
 	   for(int i=0;i<turn;i++){
 		   List<Integer>triangleCount=new ArrayList<Integer>();
 		   for(int j:triangleNewSelected){
 			   List<Triangle> triangleNeighbour=triangleMeshView.getTriangleArray().get(j).getTriangle().getNeighbours();
 			  
 			  for(int l=0;l<triangleNeighbour.size();l++){

 				 int triangleNeighbourIndex=triangleNeighbour.get(l).getTriangleViewIndex();
 				 if(this.trianglesViewSelected.contains(this.triangleMeshView.getTriangleArray().get(triangleNeighbourIndex))==false){
 					  Boolean isParalle;
 					  isParalle=triangleNeighbour.get(l).isParalleTo(normal, 0.5);
 					 
 					 if(isParalle){
 						triangleMeshView.select(triangleNeighbourIndex);
 	 	 				 this.trianglesViewSelected.add(this.triangleMeshView.getTriangleArray().get(triangleNeighbourIndex));
 	 	 				 ////////////
 	 	 				 this.triangleMesh.add(this.triangleMeshView);
 	 	 				 /////////////////////////
 	 	 				 triangleCount.add(triangleNeighbourIndex);
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

    public void meshSelect(TriangleMeshView triangleMeshView){
    	triangleMeshView.selectMesh();
    	
    }
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
        this.xPressed = e.getX();
        this.yPressed = e.getY();
    }

    @Override

    public void mouseReleased(MouseEvent e) {

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

    public void selectShape3D(Shape3D shape){
    	
    	Appearance app=shape.getAppearance();
		app.setMaterial(MeshList.matSelected);
		shape.setAppearance(app);
		
    }
    
 public void unselectShape3D(Shape3D shape){
    	
    	Appearance app=shape.getAppearance();
		app.setMaterial(MeshList.matUnSelected);
		shape.setAppearance(app);
		
    }


    
}

