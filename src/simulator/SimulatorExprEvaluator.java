package simulator;

import absynt.*;

/** Klasse zum Auswerten von Expressions in einem Prozess
 * Alles noch nicht so recht ausgebr�tet,
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.1, 07/06/2000
*/

public class SimulatorExprEvaluator {
    

    /** Instanzfeld f�r Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , n�tig f�r den Variablenkontext, wenn eine Auswertung au�erhalb
	von der Variablendeklaration erfolgen soll.
    */
    private SimulatorProcess process;

     /** Instanzfeld f�r Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Ergebniswert der Expression zur R�ckgabe 
     */
    private SimulatorValue value;


    /** Konstruktor f�r einen Expression Evaluator, der in einem Prozesskontext arbeitet
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorExprEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
    }


    /** Methode zum Berechnen eines Ausdrucks durch Weiterverteilung
	an die entsprechenden Expression-Evaluatoren
    */
    private void evalExpression () {
	if (expr.type instanceof M_Bool) {
	    // Expression ist eine boolesche, also :
	    SimulatorBoolEvaluator boolExpr = new SimulatorBoolEvaluator(process, expr);
	    value = new SimulatorBoolValue ( boolExpr.giveResult() );
	}
	else {
	    // Expression ist eine zu Integer evaluierende, also :
	    SimulatorIntEvaluator intExpr = new SimulatorIntEvaluator(process, expr);
	    value = new SimulatorIntValue ( intExpr.giveResult() );
	    
	}
    }


    /** Methode zum Wiedergeben des Wertes der Expression 
     */
    protected SimulatorValue giveResult() {
	this.evalExpression();
	return value;
    }
    

}

