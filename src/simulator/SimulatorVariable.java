package simulator;

import absynt.*;


/** Klasse zum Darstellen von einer Prozessvariablen
 * @author Michael Goemann
 * @version  1.2, 07/03/2000
*/

public class SimulatorVariable{
    
    /** Instanzfeld, mit Referenz auf den Prozeßkontext der Variablen
     */
    private SimulatorProcess process;

    /** Instanzfeld fuer Namen der Variable, muss identisch zu entsprechenden 
     * Variablennamen aus abstrakter Syntax sein.
     */
    protected String name;

    /** Instanzfeld für Referenz auf Variable aus zu simulierendem 
     * Prozess.
     */
    private Variable progVariable;

    /** Boolesches Instanzfeld , was aussagt, ob die Variable einen Wert hat
     */
    private boolean hasValue;

    /** Referenz auf den Wert der Variablen 
     */
    protected SimulatorValue value;

    /**
     * Instanzfeld, um Zugriff auf Debugfunktionen zu haben
     */
    protected SimulatorDebug debug;


    /** Konstruktor, um eine in Absynt deklarierte Variable im Simulator handhabbar zu machen
     *
     * @param _vardec Variablendeklaration in Absynt
     */
    protected SimulatorVariable (SimulatorProcess _process, Vardec _vardec) {

	debug = new SimulatorDebug();

	process = _process ;

	progVariable = _vardec.var ;

	name = _vardec.var.name;
	
	if (_vardec.val != null) {
	    /* es gibt einen Ausdruck zu evaluieren , nach
		 Konvention handelt es sich im Deklarationsfall um einen
		 Ausdruck, der frei von anderen Variablen des Prozesskontextes ist */
	    
	    SimulatorExprEvaluator expr = new SimulatorExprEvaluator(process, _vardec.val);
	    debug.addMsg(">> trying to initialize Variable with value of an expression ...",3);
	    value = expr.giveResult();
      	    debug.addMsg("<< Variable set to value of expression.",3);
	    hasValue = true;
	}
	else {
	    /* es gibt keine Expression zu evaluieren */
	    // Exception , da Variable ohne zuweisung deklariert
	    hasValue = false ;
	    }

    } // --------------  Ende Konstruktor --------------


    
   
}
