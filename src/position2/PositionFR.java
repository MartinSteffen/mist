/**
 * plaziert alle Zustände eines Prozesses in Anlehnung an einem Algorithmus 
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
import java.util.Random;

public class PositionFR implements position2.Position {
    /**
     * Vektor der die verschiedene Zusammenhangskomponenten enthält
     */
    private Vector partition;

    /** 
     * Anzahl der zu verarbeitenden Zustaende
     */
    private int anzZustaende = 0;

    /**
     * Anzahl der durchzuführenden Iterationen
     *
     */
    public int iterations = 50;

    public PositionFR () {
    }

    /**
     * Hauptfunktion: positioniert in Anlehnung an einem Algorithmus aus der Diplom-Arbeit von 
     * Stefan Meier die in dem Process enthaltenen Zustände.
     *
     * @param process der zu positionierende Prozess
     */
    public void positioniere(Process process){
	partitioniere(process.states, process.steps);
	System.out.println("Anzahl der Zusammenhangskomponenten: "+ partition.size());
	for (int i=0;i<partition.size();i++){
	    System.out.println("Positionen für Komponente "+ i + " berechnen!");
	    ausgabe((Vector)partition.elementAt(i));
	    AusgangsPosition((Vector) partition.elementAt(i));
	    gravitation((Vector) partition.elementAt(i), process.steps);
	    skalieren((Vector) partition.elementAt(i));
	}
    }

    /**
     * bestimmt die Anziehungskraefte zwischen zwei Zustaenden
     * 
     * @param r Abstand der Zustaende
     */
    float f_a(float r){
	float atraction;
	atraction =  (float)(Math.pow(r,2)/ 100f);
	return atraction;
    }

    /** 
     * bestimmt die Abstoßungskraefte zwischen zwei Zustaenden
     * 
     * @param r Abstand des Zustaendes
     */
    float f_r(float r){
	float rejection;
	rejection = (float)(1/(100f * Math.pow(r,2)));
	return rejection;
    }

    /**
     * Abkühlungsfunktion
     * (welche Funktion hier gute Ergebnisse liefert muß noch geteste werden)
     */
    float cool(float t, int iteration){
	float cool = 0;
	cool = t -( t/(50f*(float) (iteration+1)) );// (float) Math.sqrt(t);//
	return cool;

    }

    /**
     * Vorzeichen der gegebenen Zahl
     *
     * @param x liefert -1 falls x<0, +1 falls x>=0
     */
    float sgn(float x){
	return (x<0? (float)-1 :(float)1);
    }

    /**
     * setzt die Zustaende auf eine Ausgangsposition
     * hier: zufällig Verteilung
     *
     * @param states zu positionierende Zustaende
     */

    
    void AusgangsPosition(Vector statelist){
	Astate state;
	float x=0;
	float y=0;
      	//Random rand = new Random();
	for(int i=0;i<statelist.size();i++){
	    state = (Astate) statelist.elementAt(i);
	    state.pos = new Position();
	    /*
	    state.pos.x= rand.nextFloat();
	    state.pos.y= rand.nextFloat();
	    */
	    state.pos.x = (float)(Math.cos((double)(i)/statelist.size()*Math.PI*2.0));
	    state.pos.y = (float)(Math.sin((double)(i)/statelist.size()*Math.PI*2.0));
	}
    }

    private void ausgabe(Vector list){
	for(int i=0;i<list.size();i++){
	    Astate state = (Astate) list.elementAt(i);
	    System.out.println("State "+ i + ": x="+state.pos.x + " y="+ state.pos.y);
	} 
    }

    private void skalieren(Vector list){
	Astate state = (Astate) list.elementAt(0);
	float maxX = state.pos.x;
	float maxY = state.pos.y;
	float minX = maxX;
	float minY = maxY;
	for(int i=1;i<list.size();i++){
	    state = (Astate) list.elementAt(i);
	    maxX = Math.max(maxX,state.pos.x);
	    minX = Math.min(minX,state.pos.x);
	    maxY = Math.max(maxY,state.pos.y);
	    minY = Math.min(minY,state.pos.y);
	}
	float mitX = (minX+maxX)/2;
	float mitY = (minY+maxY)/2;
	for(int i=0;i<list.size();i++){
	    state = (Astate) list.elementAt(i);
	    if((Math.max(Math.abs(maxX),Math.abs(minX))-mitX)!= 0){
		state.pos.x = ((state.pos.x-mitX)/(2.2f*(Math.max(Math.abs(maxX),Math.abs(minX))-mitX)));
	    } else {
		state.pos.x = 0;
	    }
	    if((Math.max(Math.abs(maxY),Math.abs(minY))-mitY)!= 0){
		state.pos.y = ((state.pos.y-mitY)/(2.2f*(Math.max(Math.abs(maxY),Math.abs(minY))-mitY)));
	    } else {
		state.pos.y = 0;
	    }
	    state.pos.x += 0.5f;
	    state.pos.y += 0.5f;
	}
    }
    
    private void partitioniere(AstateList alist, TransitionList tlist){
	partition = new Vector();
	Vector zustaende = new Vector();
	Vector transitionen = new Vector();
	if (alist != null){
	    for (;alist.hasMoreElements();){
		zustaende.add(alist.head);
		alist = (AstateList) alist.nextElement();
	    }
	    zustaende.add(alist.head);
	}
	anzZustaende = zustaende.size();
	if (tlist != null){
	    for (;tlist.hasMoreElements();){
		transitionen.add(tlist.head);
		tlist = (TransitionList) tlist.nextElement();
	    }
	    transitionen.add(tlist.head);
	}

	int zaehler = -1;
	while (zustaende.size()>0){
	    zaehler++;
	    partition.add(new Vector());
	    ((Vector) partition.lastElement()).add((zustaende.remove(0)));
	    boolean flag = true;
	    //	    System.out.println("neue Komponente, es bleiben noch (anzZustaende: "+zustaende.size()+ " anzTransitionnen: "+ transitionen.size());
	    while (flag){
		flag = false;
		for (int i=0;i<transitionen.size();i++){
		    for(int j = 0; j<=zaehler; j++){
			Astate source = (Astate) ((Transition)transitionen.elementAt(i)).source;
			Astate target = (Astate) ((Transition)transitionen.elementAt(i)).target;
			if (isElement( (Vector)partition.elementAt(j), source) && !(isElement(  (Vector)partition.elementAt(j), target)) ){
			    ((Vector)partition.elementAt(j)).add(target);
			    transitionen.remove(i);
			    zustaende.remove(target);
			    flag = true;
			} else if (isElement( (Vector)partition.elementAt(j), target) && !(isElement(  (Vector)partition.elementAt(j), source))){
			    ((Vector)partition.elementAt(j)).add(source);
			    transitionen.remove(i);
			    zustaende.remove(source);
			    flag = true;
			}
		    }
		}
	    }
	}
    }
    private boolean isElement(Vector vect, Astate state){
	return (vect.indexOf(state)>-1);
    }

    private void gravitation(Vector vect, TransitionList tlist){
	Vector transitionen = new Vector();
	Vector zustaende = new Vector();
	float flaeche = (float) Math.pow(vect.size(),2);
	float t = (float)Math.pow(anzZustaende,2)/4;

	if (zustaende.size()>1){
	    if (tlist != null){
		for (;tlist.hasMoreElements();){
		    erwTransition trans = new erwTransition(tlist.head);
		    trans.sourceIndex = vect.indexOf(trans.trans.source);
		    trans.targetIndex = vect.indexOf(trans.trans.target);
		    
		    if ( trans.sourceIndex>-1 || trans.targetIndex>-1 ){
			transitionen.add(trans);
		    }
		    tlist = (TransitionList)tlist.nextElement();
		}
		erwTransition trans = new erwTransition(tlist.head);
		trans.sourceIndex = vect.indexOf(trans.trans.source);
		trans.targetIndex = vect.indexOf(trans.trans.target);		
		if ( trans.sourceIndex>-1 || trans.targetIndex>-1 ){
		    transitionen.add(trans);
		}
	    }
	    for (int i=0;i<vect.size();i++){
		erwAstate state = new erwAstate ((Astate)vect.elementAt(i));
		zustaende.add(state);
	    }
	    
	    // eigentlicher Algorithmus
	    Position delta;
	    Position disp;
	    Astate v;
	    Astate u;
	    for (int iter=1;iter<=iterations;iter++){
		//		System.out.println("Schleifendurchlauf: "+ iter);
		for (int i=0;i<zustaende.size();i++){
		    v = ((erwAstate) zustaende.elementAt(i)).astate;
		    if (v.pos==null){ v.pos = new Position();}
		    disp = ((erwAstate) zustaende.elementAt(i)).disp;
		    
		    for (int j=0;j<zustaende.size();j++){
			if (i != j){
			    u = ((erwAstate) zustaende.elementAt(j)).astate;
			    delta = new Position();
			    if (u.pos==null){ u.pos = new Position();}
			    delta.x = u.pos.x - v.pos.x;
			    delta.y = u.pos.y - v.pos.y;
			    if (delta.x == 0) { delta.x += 0.01f; }
			    if (delta.y == 0) { delta.y += 0.01f; }
			    disp.x += sgn(delta.x)*f_r(Math.abs(delta.x));
			    disp.y += sgn(delta.y)*f_r(Math.abs(delta.y));
			}
		    }
		}
		for (int j=0;j<transitionen.size();j++){
		    erwTransition erwtrans = (erwTransition) transitionen.elementAt(j);
		    Transition trans = erwtrans.trans;
		    Position disp_v = ((erwAstate)zustaende.elementAt(erwtrans.sourceIndex)).disp;
		    Position disp_u = ((erwAstate)zustaende.elementAt(erwtrans.targetIndex)).disp;
		    delta = new Position();
		    delta.x = trans.source.pos.x - trans.target.pos.x;
		    delta.y = trans.source.pos.y - trans.target.pos.y;
		    if (delta.x == 0) { delta.x += 0.01f; }
		    if (delta.y == 0) { delta.y += 0.01f; }
		    disp_v.x -= sgn(delta.x)*f_a(Math.abs(delta.x));
		    disp_v.y -= sgn(delta.y)*f_a(Math.abs(delta.y));
		    disp_u.x += sgn(delta.x)*f_a(Math.abs(delta.x));
		    disp_u.y += sgn(delta.y)*f_a(Math.abs(delta.y));
		}
		for (int i=0;i<zustaende.size();i++){
		    erwAstate w = (erwAstate) zustaende.elementAt(i);		    
		    w.astate.pos.x += sgn(w.disp.x) * Math.min(Math.abs(w.disp.x),t);
		    w.astate.pos.x = sgn(w.disp.x) * Math.min(Math.abs(w.astate.pos.x),flaeche); 
		    w.astate.pos.y += sgn(w.disp.y) * Math.min(Math.abs(w.disp.y),t);
		    w.astate.pos.y = sgn(w.disp.y) * Math.min(Math.abs(w.astate.pos.y),flaeche); 
		}
		t = cool(t,iter);
	    }
	}	
    }
    private class erwAstate{
	Astate astate;
	Position disp;
	public erwAstate(Astate astate){
	    this.astate = astate;
	    disp = new Position();
	}
    }
    private class erwTransition{
	int sourceIndex = -1;
	int targetIndex = -1;
	Transition trans;
	public erwTransition(Transition trans){
	    this.trans = trans;
	}
    }
}
