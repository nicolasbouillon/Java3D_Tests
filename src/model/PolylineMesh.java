package model;

import java.util.Collection;
import java.util.HashSet;

import javax.vecmath.Vector3d;

public class PolylineMesh extends HashSet<Polyline>{
	private static int currentID;
	
	private static final long serialVersionUID=1L;
	
	private final int iD;
	
	public PolylineMesh(){
		super();
		this.iD=++PolylineMesh.currentID;	
	}
	
	public PolylineMesh(final Collection<? extends Polyline> c){
		super(c);
		this.iD=++PolylineMesh.currentID;
	}
	
	public final Vector3d averageNormal(){
		final int n=this.size();
		final Vector3d average=new Vector3d();
		for (final Polyline face :this){
			average.add(face.getNormal());
		}
		average.scale(1/(double)n);
		
		return average;
	}
	
	public final Point getCentroid(){
		return new Point(this.xAverage(),this.yAverage(),this.zAverage());
	}
	
	public final double xAverage(){
		double xAverage=0;
		for(final Polyline face:this){
			xAverage+=face.xAverage();
		}
		return xAverage/this.size();
	}
	
	public final double yAverage(){
		double yAverage=0;
		for(final Polyline face:this){
			yAverage+=face.yAverage();
		}
		return yAverage/this.size();
	}
	
	public final double zAverage(){
		double zAverage=0;
		for(final Polyline face:this){
			zAverage+=face.zAverage();
		}
		return zAverage/this.size();
	}
}
