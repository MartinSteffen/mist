package position1;

//import java.applet.*;
//import java.awt.*;
//import java.awt.event.*;

import java.util.*;
import absynt.*;

/**
Diese Klasse enthaelt die fuer den Emp-Algorithmus relevanten Transitions-Informationen:
source und target als Astates.
Nur interner Gebrauch.
**/
class empTrans{

 public Astate source;
 public Astate target;

 public empTrans(Astate msource,Astate mtarget){		// Konstruktor
	this.source=msource;
	this.target=mtarget;
 }

}

/**
Diese Klasse enthaelt die fuer den Emp-Algorithmus relevanten Zustands-Informationen:
Position, Anz. Transitionen usw.
Nur interner Gebrauch.
**/
class empState{

 public Astate vater; // hier wird ein Verweis auf den originalen Astate gesichert.
 public float x,y;
 public int inTrans=0;
 public int outTrans=0; // Anzahl der ein- bzw. ausgehenden Transitionen dieses Zustandes
 public boolean touch=true; // für sortierer; wenn false: nicht mehr beachten

 // folgende werte sind defaults für einen graph mit nur einem schwerpunkt
 public float delta=0.3f; // [0..0.5]; sozusagen der zur verfügung stehende radius im canvas
 public float centerx=0.5f; // das Zentrum in x für obiges Intervall
 public float centery=0.5f; // das Zentrum in y für obiges Intervall

/**
nur f. development, kann im cvs raus
**/
 public empState(float mx,float my,int mintrans,int mouttrans){
	x=mx;
	y=my;
	inTrans=mintrans;
	outTrans=mouttrans;
 }

 public empState(Astate mvater){
	vater=mvater;
 }

}


/**
Empirisches Platzieren der Zustaende eines Processes.
Limitierungen: Ab ca. 140 Zustaenden koennte es etwas unuebersichtlich werden ;-)
@author Christian Buck ( c.buck@gmx.de )
@version 1.2
@param Process p - der zu pos. Process
**/

public class PositionEmp implements position1.Position{
 
 private Vector myStates=new Vector();
 private Vector myTrans=new Vector();

 empState findState(Astate objekt){				// ein Astate im empStates-Vector finden
	for (int i=0;i<myStates.size();i++){
		empState tmpState=(empState)myStates.get(i);
		if (tmpState.vater==objekt){
			return tmpState;		
		}
	}
	return null;
 }


/**
Konvertiere hier die States; der Algorithmus braucht von den States nur x und y.
Interne Funktion.
**/
 void wrapStates(absynt.Process p){
 	AstateList mstatelist=p.states;
 	if (mstatelist.head==null){
 		return;					// leere Liste, da passiert i.f. nichts mehr
 	}
 	while (mstatelist.hasMoreElements()){
 		Astate tmpAstate=(Astate)mstatelist.head;
 		empState tmpState=new empState(tmpAstate);
 		myStates.add(tmpState);			// alle States in den Vektor
 		mstatelist=(AstateList)mstatelist.next;
 	}
	TransitionList mtranslist=p.steps;
	if (mtranslist.head==null){
		return;
	}
	while(mtranslist.hasMoreElements()){
		empTrans tmpTrans=new empTrans((Astate)mtranslist.head.source,(Astate)mtranslist.head.target);
		mtranslist=(TransitionList)mtranslist.next;
	}
 }


/**
Abzählen der ein- und ausgehenden Transitionen eines Zustandes
Vorher muss wrapStates(Process p) aufgerufen worden sein.
Interne Funktion.
**/
 void countTrans(absynt.Process p){
	for (int i=0;i<myStates.size();i++){
		empState tmpState=(empState)myStates.get(i);
		for (int j=0;j<myTrans.size();j++){
			empTrans tmpTrans=(empTrans)myTrans.get(i);
			if (tmpTrans.source==tmpState.vater){
				tmpState.outTrans++;
			}
			if (tmpTrans.target==tmpState.vater){
				tmpState.inTrans++;
			}
		}
	}
 }

/**
Sortierte Untermenge in Process-Positionen überführen
Interne Funktion.
**/
 void unwrap(Vector mempStates){
	for (int i=0;i<mempStates.size();i++){
		empState tmpState=(empState)mempStates.get(i);
		tmpState.vater.pos.x=tmpState.x;
		tmpState.vater.pos.y=tmpState.y;
	}
 }

/**
Eine Menge von Zustaenden empirisch sortieren.
Interne Funktion.
**/
 void empSort(Vector mempStates){

 int reihenfolge[]=new int[65536]; // das ist zwar unschön, aber schnell...
 int j=0;
 int max=0;
 boolean mark;

 float winkel=(float)Math.PI/4.0f;	// recht interessant: hier kann die Ausrichtung der Zustaende geaendert werden:
				// ruhig mal probieren: [0..2PI].
 float stepping=0f;

	if (mempStates.size()==0){
		System.out.println("Warning: Empty set to sort!");
		return;
	}

	Vector moribund=(Vector)mempStates.clone();
// Fuer den folgenden Block schaeme ich mich....
	while(j<moribund.size()){
	max=0;
		for (int i=0;i<moribund.size();i++){
			empState tmpState=(empState)moribund.get(i);
			if (max<(tmpState.inTrans+tmpState.outTrans) && tmpState.touch){
				max=tmpState.inTrans+tmpState.outTrans;
			}
		}
		mark=true;
		for (int i=0;i<moribund.size()&&mark;i++){
			empState tmpState=(empState)moribund.get(i);
			if (max==(tmpState.inTrans+tmpState.outTrans) && tmpState.touch){
				reihenfolge[j++]=i;
				tmpState.touch=false;
				mark=false;
				//System.out.println("Rem "+i+" in "+j);
			}
		}
	//System.out.println("sort: 1 it");
	}

	stepping=2.0f*(float)Math.PI/(float)(mempStates.size()-1);
	//System.out.println("Stepping: "+stepping);
	for (int i=0;i<mempStates.size();i++){
		if (i==0){ // Mittelpunkt
			empState tmpState=(empState)mempStates.get(reihenfolge[0]);
			tmpState.x=tmpState.centerx;
			tmpState.y=tmpState.centery;
		} else {
			//System.out.println("reihenfolge i: "+reihenfolge[i]);
			empState tmpState=(empState)mempStates.get(reihenfolge[i]);
			tmpState.x=tmpState.centerx+((float)Math.sin(winkel)*tmpState.delta*0.9f);
			tmpState.y=tmpState.centery+((float)Math.cos(winkel)*tmpState.delta*0.9f);
			winkel+=stepping;
		}
	}


// noch mal schnell nachsehen:
	for (int i=0;i<mempStates.size();i++){
		empState tmpState=(empState)mempStates.get(i);
		System.out.println("State at "+i+" x: "+tmpState.x+" y: "+tmpState.y);
	}
 }

/**
Dies ist die Implementation des Interfaces und die einzige Methode, die von extern aufgerufen wird.
Externe Funktion.
**/
 public void positioniere(absynt.Process p){

 Vector interessant=new Vector(); // Liste der mgl. Schwerpunkte
 Vector tosort; // der hält später die zu sortierende untermenge
 float faktor=1.0f;	// für bestimmung schwerpunkte
 int reihenfolge2[]=new int[65536]; // immer noch unschön, aber schnell...
 int j=0;
 int max=0;
 boolean mark;

// Liste der präferierten Schwerpunkte idF ( (x,y,delta),...,(x,y,delta) )
 float zentren2[]={0.5f,0.33f,0.25f,0.5f,0.66f,0.25f};
 float zentren3[]={0.33f,0.33f,0.25f,0.66f,0.33f,0.25f,0.5f,0.66f,0.25f};
 float zentren4[]={0.25f,0.25f,0.25f,0.75f,0.25f,0.25f,0.25f,0.75f,0.25f,0.75f,0.75f,0.25f};
 float zentren5[]={0.16f,0.16f,0.16f,1.0f-0.16f,0.16f,0.16f,0.5f,0.5f-0.16f,0.16f,0.16f,1.0f-0.16f,0.16f,1.0f-0.16f,1.0f-0.16f,0.16f};
 float zentren6[]={0.33f,0.25f,0.16f,0.66f,0.25f,0.16f,0.33f,0.5f,0.16f,0.66f,0.5f,0.16f,0.33f,0.75f,0.16f,0.66f,0.75f,0.16f};
 float zentren7[]={0.16f,0.16f,0.16f, 1.0f-0.16f,0.16f, 0.16f, 0.16f,0.5f,0.16f, 1.0f-0.16f,0.5f,0.16f, 0.16f,1.0f-0.16f,0.16f, 1.0f-0.16f,1.0f-0.16f,0.16f,0.5f,0.5f-0.16f,0.16f};
 float zentren8[]={0.16f,0.16f,0.16f, 1.0f-0.16f,0.16f, 0.16f, 0.16f,0.5f,0.16f, 1.0f-0.16f,0.5f,0.16f, 0.16f,1.0f-0.16f,0.16f, 1.0f-0.16f,1.0f-0.16f,0.16f,0.5f,0.5f-0.16f,0.16f,0.5f,0.5f+0.16f,0.16f};
 float zentren9[]={0.16f,0.16f,0.16f, 1.0f-0.16f,0.16f, 0.16f, 0.16f,0.5f,0.16f, 1.0f-0.16f,0.5f,0.16f, 0.16f,1.0f-0.16f,0.16f, 1.0f-0.16f,1.0f-0.16f,0.16f, 0.5f,0.16f,0.16f, 0.5f,1.0f-0.16f,0.16f, 0.5f,0.5f,0.16f};

 float[] zentren[]={zentren2,zentren3,zentren4,zentren5,zentren6,zentren7,zentren8,zentren9};

 //absynt.Process p;		// !!! weg damit!!!!


	wrapStates(p);
	countTrans(p);

// ein paar dummies zum testen
	/*
	myStates.add(new empState(0.2f,0.3f,1,1));
	myStates.add(new empState(0.3f,0.2f,1,1));
	myStates.add(new empState(0.5f,0.5f,1,1));
	myStates.add(new empState(0.7f,0.7f,1,1));
	myStates.add(new empState(0.1f,0.1f,1,1));
	myStates.add(new empState(0.1f,0.1f,1,1));
	myStates.add(new empState(0.1f,0.1f,1,1));
	myStates.add(new empState(0.1f,0.1f,1,1));
	myStates.add(new empState(0.1f,0.1f,1,1));
	myStates.add(new empState(0.1f,0.1f,0,0));
	*/
// prüfen, ob sich Vector aufteilen lässt ( i.e. es ex. überhaupt Transitionen )
	boolean isOk=false;
	for (int i=0;i<myStates.size();i++){
		empState tmpState=(empState)myStates.get(i);
		if (tmpState.inTrans!=0 || tmpState.outTrans!=0){
			isOk=true;
		}
	}
	if (!isOk){
		System.out.println("Error: No transitions in Process!");
		return;	
	}
// den Vector untersuchen auf interessante Zustände, die die (visuellen) Schwerpunkte darstellen könnten
	while (interessant.size()==0){
		for (int i=0;i<myStates.size();i++){
			empState tmpState=(empState)myStates.get(i);
			if( (tmpState.inTrans+tmpState.outTrans)>=(float)Math.sqrt(myStates.size())/faktor ){
				interessant.add(tmpState);
			}
		}
		faktor+=2.0f;
	}

// gefundene Interessante nach Groesse sortieren - falls wir ein paar abschneiden müssen, dann die wertloseren ;-)
	while(j<interessant.size()){
	max=0;
		for (int i=0;i<interessant.size();i++){
			empState tmpState=(empState)interessant.get(i);
			if (max<(tmpState.inTrans+tmpState.outTrans) && tmpState.touch){
				max=tmpState.inTrans+tmpState.outTrans;
			}
		}
		mark=true;
		for (int i=0;i<interessant.size()&&mark;i++){
			empState tmpState=(empState)interessant.get(i);
			if (max==(tmpState.inTrans+tmpState.outTrans) && tmpState.touch){
				reihenfolge2[j++]=i;
				tmpState.touch=false;
				mark=false;
				//System.out.println("Rem "+i+" in "+j);
			}
		}
	//System.out.println("interessant sort: 1 it");
	}

	System.out.println("PositionEmp: Found interesting states -  "+interessant.size());

// touch für den empSort() wieder zurücksetzen
	for (int i=0;i<interessant.size();i++){
		empState tmpState=(empState)interessant.get(i);
		tmpState.touch=true;
	}

	int mycount=interessant.size();
	if (mycount>9){
		for (int i=9;i<mycount;i++){
			interessant.removeElementAt(i);
		}
	}

// interessante enfernen
	for (int i=0;i<interessant.size();i++){
		empState tmpState=(empState)interessant.get(i);
		myStates.remove(tmpState);
	}

	mycount=interessant.size();
	
	if (mycount==1){	// unlustiger Graph, 1 Schwerpunkt --> default-Sortierung
		Vector toSort=new Vector();
		empState tmpState=(empState)interessant.get(0); // erster immer Mittelpunkt!
		toSort.add(tmpState);
		for (int i=0;i<myStates.size();i++){
			empState objekt=(empState)myStates.get(i);
			toSort.add(objekt);
		}
		empSort(toSort);
		//a_devfrm.draw(toSort);	// !!!raus!!!
		unwrap(toSort);
	} else {
	
	float[] aktzentren=zentren[mycount-2];		// passenden Schwerpunktarray holen
	double anzahl=myStates.size()/interessant.size();
	double summe=anzahl;
	int benutzt=0;
	boolean found=false;
	for (int i=0;i<mycount-1;i++){		// < ( und nicht <= ) ist kein bug, s.u.
		Vector toSort=new Vector();
		empState tmpState=(empState)interessant.get(i); // erster immer Mittelpunkt!
		tmpState.centerx=aktzentren[(i*3)];
		tmpState.centery=aktzentren[(i*3)+1];
		tmpState.delta=aktzentren[(i*3)+2];
		toSort.add(tmpState);
		int tmp=(int)Math.floor(summe);
		for (j=benutzt;j<tmp;j++){
			found=false;
			for (int k=0;k<myTrans.size() && !found;k++){	// ausreichend viele zugehörige suchen
				empTrans tmpTrans=(empTrans)myTrans.get(k);					
				if (tmpTrans.source==tmpState.vater){
					empState objekt=findState(tmpTrans.target);
					myStates.remove(objekt);
					objekt.centerx=aktzentren[(i*3)];
					objekt.centery=aktzentren[(i*3)+1];
					objekt.delta=aktzentren[(i*3)+2];
					toSort.add(objekt);
					found=true;
				} else
				if (tmpTrans.target==tmpState.vater){
					empState objekt=findState(tmpTrans.source);
					myStates.remove(objekt);
					objekt.centerx=aktzentren[(i*3)];
					objekt.centery=aktzentren[(i*3)+1];
					objekt.delta=aktzentren[(i*3)+2];
					toSort.add(objekt);
					found=true;
				}
			}
			benutzt++;
			summe+=anzahl;
		}
		empSort(toSort);
		//a_devfrm.draw(toSort);	// !!!raus!!!
		unwrap(toSort);
	}
// der letzte frisst den rest!
	Vector toSort=new Vector();
	empState tmpState=(empState)interessant.get(mycount-1); // erster immer Mittelpunkt!
	tmpState.centerx=aktzentren[((mycount-1)*3)];
	tmpState.centery=aktzentren[((mycount-1)*3)+1];
	tmpState.delta=aktzentren[((mycount-1)*3)+2];
	toSort.add(tmpState);
	for (int i=0;i<myStates.size();i++){
		empState objekt=(empState)myStates.get(i);
		objekt.centerx=aktzentren[((mycount-1)*3)];
		objekt.centery=aktzentren[((mycount-1)*3)+1];
		objekt.delta=aktzentren[((mycount-1)*3)+2];
		toSort.add(objekt);
	}
	empSort(toSort);
	//a_devfrm.draw(toSort);	// !!!raus!!!
	unwrap(toSort);

	} // inter.size!=1
 }

}
