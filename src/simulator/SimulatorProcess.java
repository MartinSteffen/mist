package simulator;

import java.util.*;
import absynt.*;


/** Klasse zum Darstellen eines Prozesszustandes
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.2, 07/03/2000
*/

public class SimulatorProcess{

    /** Feld als ArrayList zur Repraesentation von den Variablen eines Prozesses.
	als ArrayList, damit dynamische Erweiterbarkeit gewaehrleistet werden kann.
     */
    protected ArrayList varList;

    /** Feld zum Merken des aktuell aktiven Prozesszustands 
     */
    protected Astate activeState;

    /** Feld als ArrayList zum Vorhalten der in einem Zustand konzessionierten Transitionen 
     */
    protected ArrayList permittedTransitions;

    /** Feld welches jeder Instanz vom Typ SimulatorProcess eine Referenz auf 
     * den jeweiligen Prozess aus dem zu simulierenden Programm zuweist.
     */
    protected absynt.Process progProcess;
    

    /** Konstruktor für einen Simulator-Prozess.
     * @param _process Referenz auf den zu simulierenden Prozess
     * macht derzeit noch nicht wirklich viel.
     */
    protected SimulatorProcess (absynt.Process _process) {
	progProcess = _process ;
	varList = this.makeVarList();
	activeState = progProcess.init;
	permittedTransitions = this.generatePermittedTransList();
    }
    
    /** Methode um für einen Prozess die benötigten Variablen und evtl. deren Werte
     * in varList zu generieren .
     * @return Eine ArrayList mit Objekten vom Typ SimulatorVariable
     */
    private ArrayList makeVarList () {

	/* pointer zeigt auf aktuell zu scannende Variable des Prozesses  */
	VardecList pointer = progProcess.vars;

	/* ArrayList in der die Variablen generiert werden,
	   ist Rückgabewert dieser Methode */
	ArrayList result = new ArrayList();

	/* boolesche Variable für while-Schleifen-Logik */
	boolean go = false;

	if (pointer != null) // checken, ob eine Variable in VardecList existiert
	    go = true;

	while (go) {
	    
	    /* Neue Variable in die Liste hinzufügen */
	    result.add( new SimulatorVariable(this, pointer.head) );

	    /* Die Variable wurde nun in die ArrayList der Prozessvariablen aufgenommen,
	       also weiter ....
	    */

	    if (pointer.hasMoreElements()) {
		// wenn es noch eine Variable hinter der aktuellen gibt
		pointer = pointer.next;
		// dann pointer auf Nachfolger versetzen
	    }
	    else
		/* Abbruchbedingung für While-Schleife setzen */
		go = false;

	}// ------ end while -------

	return result;
    }// ---------- end makeVarList() -----------------------



    /** Methode zum Generieren der Liste von konzessionierten Transitionen eines Prozesses
     * in einem Zustand. 
     * Dieses geschieht auf Prozessebene, d.h. Prozessübergreifende Channelkonflikte
     * sollen hier nicht brücksichtigt werden.
     */
    protected ArrayList generatePermittedTransList() {

	/* pointer zeigt auf aktuell zu scannende Transition des Prozesses  */
	TransitionList pointer = progProcess.steps;

	/* ArrayList in der die Transitionen generiert werden,
	   ist Rückgabewert dieser Methode */
	ArrayList result = new ArrayList();

	/* boolesche Variable für while-Schleifen-Logik */
	boolean go = false;

	if (pointer != null) // checken, ob eine Transition in der Transitionsliste existiert
	    go = true;

	while (go) {
	    
	    /* Die Klasse SimulatorPermTransition testet die hier uebergebene Transition
	     * ( pointer.head ) im ebenfalls uebergebenen Kontext ( this ) auf konzessionierung 
	     * zum feuern
	     */

	    SimulatorPermTransitions dummy = new SimulatorPermTransitions(this, pointer.head);
	    if (dummy.checkPermission()) {
	    /* Neue Transition evtl. in die Liste hinzufügen */
		result.add(dummy);
	    }
	    /* Die Transition wurde gecheckt und evtl. in die ArrayList der permitted Transitions aufgenommen,
	       also weiter ....
	    */

	    if (pointer.hasMoreElements()) {
		// wenn es noch eine Transition hinter der aktuellen gibt
		pointer = pointer.next;
		// dann pointer auf Nachfolger versetzen
	    }
	    else
		/* Abbruchbedingung für While-Schleife setzen */
		go = false;

	}// ------ end while -------

	return result;
    }// ---------- end() -----------------------



    /**
     * Durchgehen aller Transitionen und abchecken, ob Input / Output actions dabeisind.
     * Falls ja: Transition bei entsprechendem Channel als Reader / Writer eintragen.
     */

    protected ArrayList fillInTransitions (ArrayList _ChannelList ){
 
	/* pointer zeigt auf aktuell zu scannende Transition des Prozesses  */
	TransitionList pointer = progProcess.steps;

	/* ArrayList in der die Transitionen generiert werden,
	   ist Rückgabewert dieser Methode */
	ArrayList result = _ChannelList;
	SimulatorChannel Examine;
	int MaxEl = result.size();
	int Counter = 0;

	/* boolesche Variable für while-Schleifen-Logik */
	boolean go = false;

	if (pointer != null) // checken, ob eine Transition in der Transitionsliste existiert
	    go = true;

	/* Schleife ueber alle Transitionen */
	while (go) {
            if (pointer.head.source == activeState){

		/* Test, ob Action vom Typ Input-action. Wenn ja, so gehoert sie
		 * in die ReaderListe
		 */

		if (pointer.head.lab.act instanceof Input_action){
		    Counter = 0;

		    /* Schleife ueber alle Channels */

		    while (Counter<MaxEl) {
			Examine = (SimulatorChannel)result.get(Counter);

			/* Wenn der Channel der Input-Action in der Channelliste
			 * gefunden wurde, so wird die Transition dort in die
			 * ReaderListe eingetragen.
			 */

			if (Examine == pointer.head.lab.act.chan){
			    Examine.addReader(pointer.head);
				Counter = MaxEl;
			}
			else Counter ++;
		    }
		   }
		if (pointer.head.lab.act instanceof Output_action){
		    Counter = 0;
		    while (Counter<MaxEl) {
			Examine = (SimulatorChannel)result.get(Counter);
			if (Examine == pointer.head.lab.act.chan){
			    Examine.addWriter(pointer.head);
				Counter = MaxEl;
			}
			else Counter ++;
		    }
		   }
	    }
	
	    if (pointer.hasMoreElements()) {
		// wenn es noch eine Transition hinter der aktuellen gibt
		pointer = pointer.next;
		// dann pointer auf Nachfolger versetzen
	    }
	    else
		/* Abbruchbedingung für While-Schleife setzen */
		go = false;

	}// ------ end while -------

	return result;
    }// ---------- end() -----------------------


    /** Methode zum Loeschen der "permittedTransitions" */
    private void clearPermittedTransList () {
    }

    /** Methode zur Realisierung eines Zustandsueberganges 
     * @param Spezifiziert die zu feuernde Transition
     */
    protected void executeTransition(Transition _transition) {
	// letzte Aktion :
	this.clearPermittedTransList();
    }


}
