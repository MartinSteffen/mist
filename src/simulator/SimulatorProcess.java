package simulator;

import java.util.ArrayList;
import absynt.*;

/** Klasse zum Darstellen eines Prozesszustandes
 * @author Michael Gömann
 * @author Michael Nimser 
 * @version  1.0, 07/03/2000
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
    
    /** Methode zum Einfuegen einer Transition in die "permittedTransitions" */
    protected void addTransToList(Transition _trans) {
    }

    /** Methode zum Loeschen der "permittedTransitions" */
    protected void clearTransList () {
    }

    /** Methode zur Realisierung eines Zustandsueberganges */
    protected void executeTransition() {
	// letzte Aktion :
	this.clearTransList();
    }

	
	
}
