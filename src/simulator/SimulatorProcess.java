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
    

    /** Konstruktor für einen Simulator-Prozess.
     * @param _process Referenz auf den zu simulierenden Prozess
     * macht derzeit noch nicht wirklich viel.
     */
    protected SimulatorProcess (absynt.Process _process) {
	progProcess = _process ;
	varList = this.makeVarList();
	activeState = null;
	permittedTransitions = null;
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
    protected void generatePermittedTransList() {
    }

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
