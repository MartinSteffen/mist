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

    /** Instanzfeld , um den Ergebnistyp des Ausdrucks zu spezifizieren 
     */
    private boolean isAnInt;

    /** Instanzfeld , f�r booleschen Ergebniswert
     */
    private boolean boolVal;

    /** Instanzfeld , f�r integer Ergebniswert
     */
    private int intVal;

    
    /** Konstruktor f�r einen Expression Evaluator, der in einem Prozesskontext arbeitet
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorExprEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
    }

    /** Methode zur Auswertung Expression
     * @return Ergebnis vom Typ java.lang.Object eigentlich nur int oder boolean
     * NOCH KEINE ECHTE FUNKTIONALIT�T !!!
     * bisher nur ein Fake, hier soll 
     * a) das Ergebnis und
     * b) der Ergebnistyp 
     * berechnet und gesetzt werden.
     */
    protected void evalExpression() {
	boolVal = false;
	intVal = 42;
	isAnInt = true;
    }

    /** Methode zum Abfragen, welcher Art der Wert ist.
     * @return Boolescher Wert, wenn true => Ergebnis ist vom Typ integer, boolescher Wert sonst
     */
    protected boolean isInt() {
	return isAnInt;
    }

    /** Methode, die den Integerwert des evaluierten Ausdrucks zur�ckgibt 
     * @return berechneter Integerwert
     */
    protected int getIntVal() {
	return intVal;
    }

    /** Methode, die den booleschen Wert des evaluierten Ausdrucks zur�ckgibt 
     * @return berechneter boolescher Wert
     */
    protected boolean getBoolVal() {
	return boolVal;
    }

}

