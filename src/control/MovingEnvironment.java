package control;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

public class MovingEnvironment extends Behavior{

	WakeupCondition wakeupCondition = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	private TransformGroup rotationGroup = null;
	
	/**
	 * Constructeur de notre comportement
	 * @param rotationGroup TransformGroup
	 */
	public MovingEnvironment(TransformGroup rotationGroup) {
	   this.rotationGroup = rotationGroup;
	}
	
	@Override
	public void initialize() {
		this.wakeupOn(wakeupCondition);
	}

	@Override
	public void processStimulus(Enumeration criteria) {
		
		WakeupCriterion critere;
		// On boucle sur les criteres ayant declenche le comportement
	    while (criteria.hasMoreElements()) {
	    	// On recupere le premier critere de l'enumeration
	    	critere = (WakeupCriterion)criteria.nextElement();
	      
	        // On ne traite que les criteres correspondant a un evenement AWT
	      	if (critere instanceof WakeupOnAWTEvent) {
				// On recupere le tableau des evements AWT correspondant au critere
				AWTEvent[] events = ((WakeupOnAWTEvent)critere).getAWTEvent();

				if (events.length > 0) {
					 // On recupere l'evement
					 KeyEvent evt = (KeyEvent)events[events.length-1];
					
					 // Traitement au cas par cas selon la touche du clavier pressee
					 switch(evt.getKeyCode()) { 
					 case 32: //barre d'espace
					 System.out.println("barre d'espace");
					 }
				}
	      	}
		}
	    // Une fois le stimulus traite, on reinitialise le comportement
	    this.wakeupOn(wakeupCondition);
	    
	}
}