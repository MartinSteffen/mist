package simulator;

import absynt.*;

/** Klasse zum Auswerten von booleschen Expressions in einem Prozess
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.1, 07/06/2000
*/

public class SimulatorBoolEvaluator {

    /** Instanzfeld für Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , nötig für den Variablenkontext, wenn eine Auswertung außerhalb
	von der Variablendeklaration erfolgen soll.
    */
    private SimulatorProcess process;

    /** Instanzfeld, besagt ob, Prozesskontext für Auswertung zur Verfügung steht 
     */
    private boolean withContext;

    /** Instanzfeld für Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Instanzfeld , für booleschen Ergebniswert
     */
    private boolean value;



    /** Konstruktor für einen Expression Evaluator mit Prozesskontext
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorBoolEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
	withContext = true;
    }


    /** Konstruktor für einen Expression Evaluator ohne Prozesskontext
     * 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorBoolEvaluator(Expr _expr) {
	expr = _expr;
	withContext = false;
    }


    /** Methode zum Errechnen des Wertes eines Ausdrucks
     *
     * @param expr, der auszurechnende boolesche Ausdruck
     * @return booleschen Wert des Ausdrucks
     * Bisher noch KEINE echte Funktionalität !!!
     */
    private boolean evalExpression (Expr expr){
	return true;
    }


    /** Methode zum Anstoßen der Berechnung und Rückgabe des Ergebnisses
     *
     * @return booleschen Ergebniswert
     */
    protected boolean giveResult () {
	value = this.evalExpression(expr);
	return value;
    }

}
