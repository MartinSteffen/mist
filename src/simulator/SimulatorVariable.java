package simulator;

import absynt.*;


/** Klasse zum Darstellen von einer Prozessvariablen
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.2, 07/03/2000
*/

public class SimulatorVariable{
    
    /** Instanzfeld, mit Referenz auf den Prozeßkontext der Variablen
     */
    private SimulatorProcess process;


    /** Instanzfeld fuer Namen der Variable, muss identisch zu entsprechenden 
     * Variablennamen aus abstrakter Syntax sein.
     */
    private String name;

    /** Instanzfeld für Referenz auf Variable aus zu simulierendem 
     * Prozess.
     */
    private Variable progVariable;

    /** Boolesches Instanzfeld , zeigt an, von welchem Typ die
	Variable ist.
     */
    private boolean isBool;

    /** Boolesches Instanzfeld , was aussagt, ob die Variable einen Wert hat
     */
    private boolean hasValue;

    /** Boolesches Instanzfeld , für den Fall, daß die Variable vom Typ boolean ist. 
     */
    private boolean boolValue;

    /** Integer Instanzfeld, für den Fall, daß die Variable vom Typ int ist. 
     */
    private int intValue;



    /** Konstruktor, um eine in Absynt deklarierte Variable im Simulator handhabbar zu machen
     *
     * @param _vardec Variablendeklaration in Absynt
     */
    protected SimulatorVariable (SimulatorProcess _process, Vardec _vardec) {

	process = _process ;

	progVariable = _vardec.var ;

	name = _vardec.var.name;
	
	if (_vardec.type.equals(new M_Bool() )) {
	    /* Variable ist vom Typ Boolean */

	    isBool = true;
	    if (_vardec.val != null) {
		/* es gibt einen booleschen Ausdruck zu evaluieren , nach
		 Konvention handelt es sich hierbei (im Deklarationsfall) um einen
		 Ausdruck, der frei von anderen Variablen des Prozesskontextes ist */

		SimulatorBoolEvaluator boolExpr = new SimulatorBoolEvaluator(_vardec.val);
		boolValue = boolExpr.giveResult();
		hasValue = true;
	    }
	    else {
		/* es gibt keinen booleschen Ausdruck zu evaluieren */
		hasValue = false ;
	    }

	}
	else {
	    /* Variable ist vom Typ Integer */

	    isBool = false;
	    if (_vardec.val != null) {
		/* es gibt einen Integer-Ausdruck zu evaluieren , nach
		 Konvention handelt es sich hierbei (im Deklarationsfall) um einen
		 Ausdruck, der frei von anderen Variablen des Prozesskontextes ist */

		SimulatorIntEvaluator intExpr = new SimulatorIntEvaluator(_vardec.val);
		intValue = intExpr.giveResult();
		hasValue = true;
	    }
	    else {
		/* es gibt keinen booleschen Ausdruck zu evaluieren */
		hasValue = false ;
	    }
	}



    } // --------------  Ende Konstruktor --------------


}
