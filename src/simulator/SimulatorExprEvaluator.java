package simulator;

import absynt.*;

/** Klasse zum Auswerten von Expressions in einem Prozess
 * Alles noch nicht so recht ausgebrütet,
 * Problem, da Variablen nicht getypt sind, somit kann erst zur
 * Zeit der Auswertung der Expression der Ergebnisstyp bestimmt werden.
 * Hat unschöne Auswirkungen in der Benutzung dieser Klasse.
 * Vielleicht nochmal überdenken.
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.1, 07/06/2000
*/

public class SimulatorExprEvaluator {

    /** Instanzfeld für Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , nötig für den Variablenkontext 
    */
    private SimulatorProcess process;

    /** Instanzfeld für Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Instanzfeld , um den Ergebnistyp des Ausdrucks zu spezifizieren 
     */
    private boolean isAnInt;

    /** Instanzfeld , für booleschen Ergebniswert
     */
    private boolean boolVal;

    /** Instanzfeld , für integer Ergebniswert
     */
    private int intVal;

    
    /** Konstruktor für einen Expression Evaluator
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
     * NOCH KEINE ECHTE FUNKTIONALITÄT !!!
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

    /** Methode, die den Integerwert des evaluierten Ausdrucks zurückgibt 
     * @return berechneter Integerwert
     */
    protected int getIntVal() {
	return intVal;
    }

    /** Methode, die den booleschen Wert des evaluierten Ausdrucks zurückgibt 
     * @return berechneter boolescher Wert
     */
    protected boolean getBoolVal() {
	return boolVal;
    }

}

