package simulator;

import absynt.*;

/** Klasse zum Auswerten von Expressions in einem Prozess
 * Alles noch nicht so recht ausgebrütet,
 * @author Michael Goemann
 * @author Michael Nimser 
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

    /** Instanzfeld , um den Ergebnistyp des Ausdrucks zu spezifizieren 
     */
    private boolean isAnInt;

    /** Instanzfeld , für booleschen Ergebniswert
     */
    private boolean boolVal;

    /** Instanzfeld , für integer Ergebniswert
     */
    private int intVal;

    
    /** Konstruktor für einen Expression Evaluator, der in einem Prozesskontext arbeitet
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorExprEvaluator(SimulatorProcess _process, Expr _expr) {
	process = _process;
	expr = _expr;
    }


}

