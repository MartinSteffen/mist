/**
 * Interface für alle Positionierungsklassen
 * 
 * @author daniel.dietrich@gmx.net
 * @version 1.0
 */

// Der hier implementierte Algorithmus von Thomas M. J. Fruchterman
// und Edward M. Reingold ist eine Modifikation des Algorithmus von
// Eades' Algorithmus und basiert auf dem Atommodell.

package position1;

import absynt.*;

public class PositionGrav implements position1.Position
{
	private static final int ITERATIONS = 1;		// Iterationstiefe
	private static final float WORKSPACE = 1.0f;	// La"nge der quadr. Zeichenfläche

	private absynt.Process process;	// aktueller Prozess

	private int TCount;				// Anzahl der Transitionen
	private int ZCount;				// Anzahl der Zusta"nde
	private float k;				// Freier Platz um einen Knoten
	private float t;				// Temperatur der Atommodells
	private float area;				// Gro"sse der Zeichenfla"che
	private int[][] TA;         	// Array von Transitionen
	private Astate[] SA;        	// Array von Zusta"nden
	

	public PositionGrav() {}			// Konstruktor


	private void init() {			// Initialisierung der Variablen
		area = WORKSPACE*WORKSPACE;	// Gro"sse der Zeichenfla"che
		t = WORKSPACE;				// Temperatur des Atommodells

   		// Za"hlen der Zusta"nde:
		AstateList ZList = process.states;
		if (ZList.head != null) {
   	      	ZCount=1;
   	       	while (ZList.hasMoreElements()) {
   	       		ZList = (AstateList)(ZList.nextElement());
   	       		ZCount++;
   	       	}
   	    }
   	    else {
   	       	ZCount=0;
   	    }

	    // Freier Platz um einen Knoten:
	    k = (float)(Math.sqrt((float)(area)/(float)(ZCount)));

		// Za"hlen der Transitionen:
	    TransitionList TList = process.steps;
	    if (TList.head != null) {
		TCount=1;
			while (TList.next != null) {
		    	TList = TList.next;
		    	TCount++;
			}
	    }
	    else {
		TCount=0;
	    }

		// Aufbauen der Arrays fu"r Zusta"nde:
	    ZList = process.states;
	    SA = new Astate[ZCount];
	    for (int i=0; i<ZCount; i++) {
			SA[i] = ZList.head;
			ZList = ZList.next;
        }

		// Aufbauen der Arrays fu"r Transitionen:
	    TList = process.steps;
	    TA = new int[TCount][2];
	    for (int i=0; i<TCount; i++) {
			TA[i][0] = Index(TList.head.source);
			TA[i][1] = Index(TList.head.target);
			TList = TList.next;
	    }
	}


//	Anordnung der n Knoten in einem n-Eck,
//	als Ausgangssituation fu"r den Algorithmus:
	private void assign_initial_positions()	{
   		float r = WORKSPACE/2.5f;
   		for (int i=0; i<ZCount; i++) {
   			SA[i].pos.x = (float)(Math.cos((double)(i)/ZCount*Math.PI*2.0)*r+WORKSPACE/2.0f);
   			SA[i].pos.y = (float)(Math.sin((double)(i)/ZCount*Math.PI*2.0)*r+WORKSPACE/2.0f);
   		}  	
	} 


// Definition der Funktionen, die die Abstossungskra"fte im Atommodell
// bestimmen:
	private float f_a(float x) { return x*x/k; }
	private float f_r(float x) { return k*k/x; }

   
	private float VecLen (absynt.Position vec) {
		return (float)(Math.sqrt(vec.x*vec.x+vec.y*vec.y));
	}
	

	private float cool (float t) {
		return t/2.0f;
	}
	

	private int Index(Astate z) {
	    int idx=0;
	    
	    while (SA[idx] != z) { idx++; }
	    
	    return idx;
	}


	private void normalize() {
		for (int i=0; i<ZCount; i++) {
					SA[i].pos.x /= WORKSPACE;
					SA[i].pos.y /= WORKSPACE;
		}
	}

	public void positioniere (absynt.Process process) {
		if (process.steps != null)
		{
			this.process = process;

			init();
			assign_initial_positions();
   			
			absynt.Position[] disp = new absynt.Position[ZCount];
			absynt.Position delta;
		
			// Pro Iteration Berechnung der
			// - Abstossungskra"fte,
			// - Anziehungskra"fte,
			// - Begrenzung bzgl. der Temperatur
			for (int i=1; i<=ITERATIONS; i++) {
				
				// Aufsummierung der abstossenden Kra"fte aller Knoten
				// (bzw. die daraus resultierenden Displacements)
				// die auf v wirken:
				for (int v=0; v<ZCount; v++) {
					disp[v] = new absynt.Position(0.0f,0.0f);
					for (int u=0; u<ZCount; u++) {
						if (u!=v) {
							delta = new absynt.Position(SA[v].pos.x-SA[u].pos.x,SA[v].pos.y-SA[u].pos.y);
							disp[v].x += (delta.x/VecLen(delta))*f_r(VecLen(delta));
							disp[v].y += (delta.y/VecLen(delta))*f_r(VecLen(delta));
						}
					}
				}
				
				// Berechnung der Anziehungskra"fte (bzw. Displacements)
				// benachbarter Knoten und Aufaddierung zum entsprechenden
				// disp-Feld der Knoten:
				for (int idx=0; idx<TCount; idx++) {
					delta = new absynt.Position(SA[TA[idx][0]].pos.x-SA[TA[idx][1]].pos.x,SA[TA[idx][0]].pos.y-SA[TA[idx][1]].pos.y);
					disp[TA[idx][0]].x -= (delta.x/VecLen(delta))*f_a(VecLen(delta));
					disp[TA[idx][0]].y -= (delta.y/VecLen(delta))*f_a(VecLen(delta));					
					disp[TA[idx][1]].x += (delta.x/VecLen(delta))*f_a(VecLen(delta));
					disp[TA[idx][1]].y += (delta.y/VecLen(delta))*f_a(VecLen(delta));
				}	
				
				// Begrenzung des Displacements durch die Temperatur:
				for (int v=0; v<ZCount; v++) {
					SA[v].pos.x += (disp[v].x/VecLen(disp[v]))*Math.min(disp[v].x,t);
					SA[v].pos.y += (disp[v].y/VecLen(disp[v]))*Math.min(disp[v].y,t);
					SA[v].pos.x = Math.min(WORKSPACE/2,Math.max(-WORKSPACE/2,SA[v].pos.x));
					SA[v].pos.y = Math.min(WORKSPACE/2,Math.max(-WORKSPACE/2,SA[v].pos.y));
				}
				
				t = cool(t);				
			}
			
//			normalize();		// Positioniert Graph auf [0,1.0]x[0,1.0]-Intervall
		}
	}
}
