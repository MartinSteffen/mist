/**
 * plaziert alle Zustände eines Prozesses mit einem Algorithmus 
 * aus der Diplom-Arbeit von Stefan Meier 
 * 
 * @author rthoele@gmx.de
 * @version 1.0
 */

package position2;

import absynt.Process;
import absynt.Position;
import absynt.*;
import java.util.Enumeration;

public class PositionFR implements position2.Position {
    /** 
     * soll den Radius der leeren Fläche um eine Zustand (bei Gleichverteilung)
     * wiederspiegeln
     */
    private float k = 1;

    /** 
     * Anzahl der zu verarbeitenden Zustaende
     */
    private int anzZustaende = 0;

    /**
     * Anzahl der durchzuführenden Iterationen
     *
     */
    public int iterations = 50;

    /**
     * "Temperatur"
     */
    private float t=1;

    public PositionFR () {
    }

    /**
     * Hauptfunktion: positioniert nach einem Algorithmus aus der Diplom-Arbeit von 
     * Stefan Meier die in dem Process enthaltenen Zustände.
     *
     * @param process der zu positionierende Prozess
     */
    public void positioniere(Process process){
	anzZustaende = anzElemente(process.states);
	k=(float)Math.sqrt(1/anzZustaende);
	AusgangsPosition(process.states);

	AstateList stateList1 = process.states;
	AstateList stateList2 = process.states;
	TransitionList tlist = process.steps;

	for (int j=1;j<=iterations;j++){
	    for (;stateList1.hasMoreElements();){
		erwAstate v = (erwAstate) stateList1.nextElement();
		v.disp.x=0;
		v.disp.y=0;
		for (;stateList2.hasMoreElements();){
		    erwAstate u= (erwAstate) stateList2.nextElement();
		    if (v != u){
			Position delta = new Position();
			delta.x = v.pos.x - u.pos.x;
			delta.y = v.pos.y - u.pos.y;
			v.disp.x += (delta.x/betrag(delta))*f_r(betrag(delta));
			v.disp.y += (delta.y/betrag(delta))*f_r(betrag(delta));
		    }
		}    
	    }
	    for (;tlist.hasMoreElements();){
		Transition trans = (Transition) tlist.nextElement();
		Position delta = new Position();
		delta.x = trans.source.pos.x - trans.target.pos.x;
		delta.y = trans.source.pos.y - trans.target.pos.y;
		((erwAstate) trans.source).disp.x -= (delta.x/(betrag(delta))*f_a(betrag(delta)));
		((erwAstate) trans.source).disp.y -= (delta.y/(betrag(delta))*f_a(betrag(delta)));
	    }
	    stateList1 = process.states;
	    for (;stateList1.hasMoreElements();){
		erwAstate v = (erwAstate) stateList1.nextElement();
		v.pos.x += (v.disp.x/betrag(v.disp))*Math.min(v.disp.x,t);
		v.pos.y += (v.disp.y/betrag(v.disp))*Math.min(v.disp.y,t);
	    }
	    t = cool(t);
	}
    }

    /**
     * bestimmt die Anziehungskräfte zwischen zwei Zuständen
     * 
     * @param x (Betrag/)Position des Zustandes
     */
    float f_a(float x){
	return ((float)Math.pow(x,2)/k);
    }

    /** 
     * bestimmt die Abstoßungskräfte zwischen zwei Zuständen
     * 
     * @param x (Betrag/)Position des Zustandes
     */
    float f_r(float x){
	return ((float)Math.pow(k,2)/x);
    }

    /**
     * Abkühlungsfunktion
     * (welche Funktion hier gute Ergebnisse liefert muß noch geteste werden)
     */
    float cool(float t){
	return (t/2);
    }

    /**
     * liefert den Betrag einer Position
     *
     * @param pos Position, deren Betrag berechnet werden soll
     */
    float betrag(Position pos){
	return (float)Math.sqrt(Math.pow(pos.x,2)+Math.pow(pos.y,2));
    }

    /** 
     * liefert die Anzahl der Elemente einer Enumeration
     *
     * @param e Enumeration, dessen Elemente gezählt werden sollen.
     */
    int anzElemente(Enumeration e){
	int i = 0;
	for(i=0;e.hasMoreElements();i++){
	    e.nextElement();
	}
	return i;
    }

    /**
     * setzt die Zustaende auf eine Ausgangsposition
     * hier: moeglichst Gleichverteilung
     *
     * @param states zu positionierende Zustaende
     */
    void AusgangsPosition(AstateList states){
	State state;
	float x=0;
	float y=0;
	while(states.hasMoreElements()){
	    state = (State) states.nextElement();
	    state.pos.x= x;
	    state.pos.y= y;
	    x+=k;
	    if (x>1){
		x-=1;
		y+=k;
	    }
	}
    }

    private abstract class erwAstate extends Astate{
	public Position disp;
    }
}
