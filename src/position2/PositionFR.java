/**
 * plaziert alle Zustände eines Prozesses mit einem Algorithmus 
 * aus der Diplom-Arbeit von Stefan Meier 
 * 
 * @author rthoele@gmx.de
 * @version 1.1
 */

package position2;

import absynt.Process;
import absynt.Position;
import absynt.*;
import java.util.Enumeration;
import java.util.Vector;

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
    public int iterations = 100;

    /**
     * "Temperatur"
     */
    private float t=10000;

    public PositionFR () {
    }

    /**
     * Hauptfunktion: positioniert nach einem Algorithmus aus der Diplom-Arbeit von 
     * Stefan Meier die in dem Process enthaltenen Zustände.
     *
     * @param process der zu positionierende Prozess
     */
    public void positioniere(Process process){
	erwAstateList stateList = new erwAstateList(process.states);
	erwTransitionList transitionlist = new erwTransitionList(process.steps, stateList);

	anzZustaende = stateList.size();
	k = (float)Math.sqrt(1.0/anzZustaende);
       	AusgangsPosition(stateList);
	for (int j=1;j<=iterations;j++){
	    //	    System.out.println("Schleifendurchlauf Nr.: " + j);
	    for (int i=0;i<stateList.size();i++){
		erwAstate v = (erwAstate) stateList.elementAt(i);
		v.disp.x=0;
		v.disp.y=0;
		for (int l=0;l<stateList.size();l++){
		    erwAstate u= (erwAstate) stateList.elementAt(l);
		    if (v != u){
			Position delta = new Position();
			if (v.astate.pos==null){ v.astate.pos = new Position();}
			if (u.astate.pos==null){ u.astate.pos = new Position();}
			delta.x = v.astate.pos.x - u.astate.pos.x;
			delta.y = v.astate.pos.y - u.astate.pos.y;
			v.disp.x += (delta.x/betrag(delta))*f_r(betrag(delta));
			v.disp.y += (delta.y/betrag(delta))*f_r(betrag(delta));
		    }
		}    
	    }
	    for (int i=0;i<transitionlist.size();i++){
		erwTransition trans = (erwTransition) transitionlist.elementAt(i);
		Position delta = new Position();
		int index_v = trans.sourceIndex;
		int index_u = trans.targetIndex;
		if (index_v != -1 && index_u !=-1) {
		    erwAstate v= (erwAstate) stateList.elementAt(index_v);
		    erwAstate u= (erwAstate) stateList.elementAt(index_u);
		    delta.x = trans.transition.source.pos.x - trans.transition.target.pos.x;
		    delta.y = trans.transition.source.pos.y - trans.transition.target.pos.y;
		    v.disp.x -= (delta.x/(betrag(delta))*f_a(betrag(delta)));
		    v.disp.y -= (delta.y/(betrag(delta))*f_a(betrag(delta)));
		}
	    }
	    for (int i=0;i<stateList.size();i++){
		erwAstate v = (erwAstate) stateList.elementAt(i);
		v.astate.pos.x += (v.disp.x/betrag(v.disp))*Math.min(v.disp.x,t);
		v.astate.pos.y += (v.disp.y/betrag(v.disp))*Math.min(v.disp.y,t);
	    }
	    t = cool(t);
        }
	skalieren(stateList);
	ausgabe(stateList);
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
	return ((float)Math.sqrt(t/4));
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
     * setzt die Zustaende auf eine Ausgangsposition
     * hier: moeglichst Gleichverteilung
     *
     * @param states zu positionierende Zustaende
     */
    void AusgangsPosition(erwAstateList statelist){
	Astate state;
	float x=0;
	float y=0;
	for(int i=0;i<statelist.size();i++){
	    state = ((erwAstate) statelist.elementAt(i)).astate;
	    state.pos = new Position();
	    state.pos.x= x;
	    state.pos.y= y;
	    x+=k;
 	    if (x>1){
		x-=1;
		y+=k;
	    }
	    //	    System.out.println("x: "+ x + " y: "+ y + " k: "+k);
	}
    }

    private void ausgabe(erwAstateList list){
	for(int i=0;i<list.size();i++){
	    erwAstate state = (erwAstate) list.elementAt(i);
	    System.out.println("State "+ i + ": x="+state.astate.pos.x + " y="+ state.astate.pos.y);
	} 
    }
    private void skalieren(erwAstateList list){
	float maxX = 0;
	float maxY = 0;
	for(int i=0;i<list.size();i++){
	    Astate state = ((erwAstate) list.elementAt(i)).astate;
	    //System.out.println("State "+ i + ": x="+state.astate.pos.x + " y="+ state.astate.pos.y);
	    maxX = Math.max(maxX,state.pos.x);
	    maxY = Math.max(maxY,state.pos.y);
	}
	maxX += 0.2;
	maxY += 0.2;
	for(int i=0;i<list.size();i++){
	    Astate state = ((erwAstate) list.elementAt(i)).astate;
	    state.pos.x = state.pos.x/maxX;
	    state.pos.y = state.pos.y/maxY;
	}
    }
    private class erwTransitionList extends Vector{
	public erwTransitionList(TransitionList list, erwAstateList stateList){
	    for (;list.hasMoreElements();){
		erwTransition erwtrans = new erwTransition(list.head);
		this.add(erwtrans);
		if (erwtrans.transition.source != null){
		    erwtrans.sourceIndex = stateList.indexOf(erwtrans.transition.source);
		}
		if (erwtrans.transition.target != null){
		    erwtrans.targetIndex = stateList.indexOf(erwtrans.transition.target);
		}						
		list = (TransitionList) list.nextElement();
	    }
	    erwTransition erwtrans = new erwTransition(list.head);
	    this.add(erwtrans);
	    if (erwtrans.transition.source != null){
		erwtrans.sourceIndex = stateList.indexOf(erwtrans.transition.source);
	    }
	    if (erwtrans.transition.target != null){
		erwtrans.targetIndex = stateList.indexOf(erwtrans.transition.target);
	    }						
	}
    }
    private class erwTransition{
	public int sourceIndex = -1;
	public int targetIndex = -1;
	public Transition transition;
	public erwTransition(Transition trans){
	    this.transition = trans;
	}
    }	
    private class erwAstateList extends Vector{
	public erwAstateList(AstateList stateList){
	    for (;stateList.hasMoreElements();){
		this.add(new erwAstate(stateList.head));
		stateList = (AstateList) stateList.nextElement();
	    }
	    this.add(new erwAstate(stateList.head));
	}
    }
    private class erwAstate{
	public Position disp;
	public Astate astate;
	public erwAstate(Astate astate){
	    this.astate = astate;
	    disp = new Position();
	}
    }
}
