package simulator;

import java.util.ArrayList;
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
    

    /** Konstruktor f�r einen Simulator-Prozess.
     * @param _process Referenz auf den zu simulierenden Prozess
     * macht derzeit noch nicht wirklich viel.
     */
    protected SimulatorProcess (absynt.Process _process) {
	progProcess = _process ;
	varList = this.makeVarList();
	activeState = null;
	permittedTransitions = this.generatePermittedTransList();
    }
    
    /** Methode um f�r einen Prozess die ben�tigten Variablen und evtl. deren Werte
     * in varList zu generieren .
     * @return Eine ArrayList mit Objekten vom Typ SimulatorVariable
     */
    private ArrayList makeVarList () {

	/* pointer zeigt auf aktuell zu scannende Variable des Prozesses  */
	VardecList pointer = progProcess.vars;

	/* ArrayList in der die Variablen generiert werden,
	   ist R�ckgabewert dieser Methode */
	ArrayList result = new ArrayList();

	/* boolesche Variable f�r while-Schleifen-Logik */
	boolean go = false;

	if (pointer != null) // checken, ob eine Variable in VardecList existiert
	    go = true;

	while (go) {
	    
	    /* Neue Variable in die Liste hinzuf�gen */
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
		/* Abbruchbedingung f�r While-Schleife setzen */
		go = false;

	}// ------ end while -------

	return result;
    }// ---------- end makeVarList() -----------------------



    /** Methode zum Generieren der Liste von konzessionierten Transitionen eines Prozesses
     * in einem Zustand. 
     * Dieses geschieht auf Prozessebene, d.h. Prozess�bergreifende Channelkonflikte
     * sollen hier nicht br�cksichtigt werden.
     */
    protected ArrayList generatePermittedTransList() {

	/* pointer zeigt auf aktuell zu scannende Transition des Prozesses  */
	TransitionList pointer = progProcess.steps;

	/* ArrayList in der die Transitionen generiert werden,
	   ist R�ckgabewert dieser Methode */
	ArrayList result = new ArrayList();

	/* boolesche Variable f�r while-Schleifen-Logik */
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
	    /* Neue Transition evtl. in die Liste hinzuf�gen */
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
		/* Abbruchbedingung f�r While-Schleife setzen */
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
