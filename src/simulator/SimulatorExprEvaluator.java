package simulator;

import absynt.*;

/** Klasse zum Auswerten von Expressions in einem Prozess, dient der Verzweigung
 * zu den typspezifischen Expressionevaluatoren
 * @author Michael Goemann
 * @version  1.1, 07/06/2000
*/

public class SimulatorExprEvaluator {
    

    /** Instanzfeld für Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , nötig für den Variablenkontext, wenn eine Auswertung außerhalb
	von der Variablendeklaration erfolgen soll.
    */
    private SimulatorProcess process;

     /** Instanzfeld für Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Ergebniswert der Expression zur Rückgabe 
     */
    private SimulatorValue value;

    /**
     * Instanzfeld, um Zugriff auf Debugfunktionen zu haben
     */
    protected SimulatorDebug debug;


    /** Konstruktor für einen Expression Evaluator, der in einem Prozesskontext arbeitet
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorExprEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
	debug = new SimulatorDebug();
    }


    /** Methode zum Berechnen eines Ausdrucks durch Weiterverteilung
	an die entsprechenden Expression-Evaluatoren
    */
    private void evalExpression () {
	if (expr.type != null) {
	    // Expression ist wie vorgesehen getypt
	    if (expr.type instanceof M_Bool) {
		// Expression ist eine boolesche, also :
		debug.addMsg("# expression to evaluate is boolean.",3);
		SimulatorBoolEvaluator boolExpr = new SimulatorBoolEvaluator(process, expr);
		value = new SimulatorBoolValue ( boolExpr.giveResult() );
	    }
	    else if (expr.type instanceof M_Int) {
		// Expression ist eine zu Integer evaluierende, also :
		debug.addMsg("# expression to evaluate is integer.",3);
		SimulatorIntEvaluator intExpr = new SimulatorIntEvaluator(process, expr);
		value = new SimulatorIntValue ( intExpr.giveResult() );    
	    }
	    else {
	    /* Platz für Exception, da Expression falsch getypt */
	    debug.addMsg("!!! EXCEPTION !!! : Expression not properly typed ",0);
	    debug.addMsg("!!! Class: SimulatorExprEvaluator \n!!! Method : evalExpression\n!!! Pos.:1 ",0);
	    }

	} //--------- end if(1) ------------
	else {
	    // Expression ist nicht getypt also :
	    debug.addMsg("!!! EXCEPTION !!! : Expression not typed ",0);
	    debug.addMsg("!!! Class: SimulatorExprEvaluator \n!!! Method : evalExpression\n!!! Pos.:2 ",0);
	} //-------- end else --------------
    }


    /** Methode zum Wiedergeben des Wertes der Expression 
     */
    protected SimulatorValue giveResult() {
	this.evalExpression();
	return value;
    }
    

}

