package simulator;

import absynt.*;

/** Klasse zum Auswerten von booleschen Expressions in einem Prozess
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.1, 07/06/2000
*/

public class SimulatorBoolEvaluator {

    /** Instanzfeld f�r Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , n�tig f�r den Variablenkontext, wenn eine Auswertung au�erhalb
	von der Variablendeklaration erfolgen soll.
    */
    private SimulatorProcess process;

    /** Instanzfeld f�r Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Instanzfeld , f�r booleschen Ergebniswert
     */
    private boolean value;



    /** Konstruktor f�r einen Expression Evaluator mit Prozesskontext
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorBoolEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
    }


    /** Methode zum Errechnen des Wertes eines Ausdrucks
     *
     * @param expr, der auszurechnende boolesche Ausdruck
     * @return booleschen Wert des Ausdrucks
     * Bisher noch KEINE echte Funktionalit�t !!!
     */
    private boolean evalExpression (Expr expr){
	/* Typ der Expression abchecken und je nachdem weiter */
	if (expr instanceof B_expr) {	
	}
	if (expr instanceof U_expr) {
	}
	if (expr instanceof Variable) {
	}
	if (expr instanceof Constval) {
	}
	return true;
    }


    /** Methode zum Ansto�en der Berechnung und R�ckgabe des Ergebnisses
     *
     * @return booleschen Ergebniswert
     */
    protected boolean giveResult () {
	value = this.evalExpression(expr);
	return value;
    }

}
