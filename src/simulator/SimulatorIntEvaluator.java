package simulator;

import absynt.*;

/** Klasse zum Auswerten von Integer-Expressions in einem Prozess
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.1, 07/06/2000
*/

public class SimulatorIntEvaluator {

    /** Instanzfeld f�r Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , n�tig f�r den Variablenkontext, wenn eine Auswertung au�erhalb
	von der Variablendeklaration erfolgen soll.
    */
    private SimulatorProcess process;

    /** Instanzfeld f�r Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Instanzfeld , f�r integer Ergebniswert
     */
    private int value;


    /** Konstruktor f�r einen Expression Evaluator mit Prozesskontext
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorIntEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
    }


    /** Methode zum Errechnen des Wertes eines Ausdrucks
     *
     * @param expr, der auszurechnenden Integer-Ausdrucks
     * @return integer-Wert des Ausdrucks
     * Bisher noch KEINE echte Funktionalit�t !!!
     */
    private int evalExpression (Expr expr){
	return 42;
    }


    /** Methode zum Ansto�en der Berechnung und R�ckgabe des Ergebnisses
     *
     * @return Integer-Ergebniswert
     */
    protected int giveResult () {
	value = this.evalExpression(expr);
	return value;
    }


}
