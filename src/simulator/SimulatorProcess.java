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
    private ArrayList varList;

    /** Feld zum Merken des aktuell aktiven Prozesszustands 
     */
    private Astate activeState;

    /** Feld als ArrayList zum Vorhalten der in einem Zustand konzessionierten Transitionen 
     */
    private ArrayList permittedTransitions;

    /** Feld welches jeder Instanz vom Typ SimulatorProcess eine Referenz auf 
     * den jeweiligen Prozess aus dem zu simulierenden Programm zuweist.
     */
    private absynt.Process progProcess;
    

    /** Konstruktor f�r einen Simulator-Prozess.
     * @param _process Referenz auf den zu simulierenden Prozess
     * macht derzeit noch nicht wirklich viel.
     */
    protected SimulatorProcess (absynt.Process _process) {
	progProcess = _process ;
	varList = this.makeVarList();
	activeState = null;
	permittedTransitions = null;
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

	if (pointer != null) // checken, ob Variable in VardecList existiert
	    go = true;

	while (go) {
	    
	    if (pointer.head.val == null) {
		/* Variable als SimulatorVariable ins Leben rufen und nur DEKLARIEREN,
		   da es hier keine auszuwertende (und zuzuweisende) Expression
		   gibt. Man wei� hier auch noch nichts �ber den Typ der Variablen,
		   da in Absynt nur ungetypte Variablen vorkommen.
		 */
		result.add( new SimulatorVariable(pointer.head.var.name,pointer.head.var) );
	    }
	    else {
		/* Die Variable wird nicht nur deklariert sondern auch initialisiert,
		   also mu� die Expression der Initialisierung ausgewertet und der 
		   Variablen zugeordnet werden.
		   Die Auswertung einer Expression mu� noch realisiert werden in
		   Klasse SimulatorExprEvaluator.
		*/

		SimulatorExprEvaluator exprEval = new SimulatorExprEvaluator(this, pointer.head.val);
		// Objekt fuer Ausdrucksauswertung ins Leben rufen
		exprEval.evalExpression();  // Ausdruck auswerten

		/* Wegen den ungetypten Variablen und somit Ausdr�cken, kann erst zur nach 
		   der Auswertung eines Ausdrucks dessen Datentyp festgestellt werden.
		   Unsch�n, aber somit Fallunterscheidung notwendig :
		*/

		if (exprEval.isInt()){
		    /* Ausdruck evaluiert zu einem Integerwert */
		       result.add( new SimulatorVariable(pointer.head.var.name,pointer.head.var, exprEval.getIntVal() ) );
		}
		else {
		    /* Ausdruck evaluiert zu einem booleschen Wert */ 
		    result.add( new SimulatorVariable(pointer.head.var.name,pointer.head.var, exprEval.getBoolVal() ) );
		}

	    } 
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
