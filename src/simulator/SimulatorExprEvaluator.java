package simulator;

import absynt.*;

/** Klasse zum Auswerten von Expressions in einem Prozess
 * Alles noch nicht so recht ausgebr�tet,
 * Problem, da Variablen nicht getypt sind, somit kann erst zur
 * Zeit der Auswertung der Expression der Ergebnisstyp bestimmt werden.
 * Hat unsch�ne Auswirkungen in der Benutzung dieser Klasse.
 * Vielleicht nochmal �berdenken.
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.1, 07/06/2000
*/

public class SimulatorExprEvaluator {

    /** Instanzfeld f�r Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , n�tig f�r den Variablenkontext 
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

    
    /** Konstruktor f�r einen Expression Evaluator
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorExprEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
	isAnInt = true;
	boolVal = false;
    }

    /** Methode zur Auswertung einer booleschen Expression
     * @return Ergebnis vom Typ java.lang.Object eigentlich nur int oder boolean
     * NOCH KEINE ECHTE FUNKTIONALIT�T !!!
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

