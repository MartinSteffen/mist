package simulator;

import absynt.*;

/** Klasse zum Auswerten von Integer-Expressions in einem Prozess
 * @author Michael Goemann
 * @version  1.1, 07/06/2000
*/

public class SimulatorIntEvaluator {

    /** Instanzfeld für Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , nötig für den Variablenkontext, wenn eine Auswertung außerhalb
	von der Variablendeklaration erfolgen soll.
    */
    private SimulatorProcess process;

    /** Instanzfeld für Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Instanzfeld , für integer Ergebniswert
     */
    private int value;


    /** Konstruktor für einen Expression Evaluator mit Prozesskontext
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
     * @param expr, der auszurechnende boolesche Ausdruck
     * @return int Wert des Ausdrucks
     */
    private int evalExpression (){
	/* Typ der Expression abchecken und je nachdem weiter */
	if (expr instanceof B_expr) {
	    /* zweistellige Integer-Expression auswerten */ 
	    return evalB_expr( (B_expr) expr);
	}
	if (expr instanceof U_expr) {
	    /* einstellige Integer-Expression auswerten */
	    return evalU_expr( (U_expr) expr);
	}
	if (expr instanceof Variable) {
	    /* Expression ist eine Variable; diese im Prozesskontext suchen und 
	       dann deren Value zurückgeben. 
	       Das Value muß hier integer sein */
	    return evalVariable( (Variable) expr );
	}
	if (expr instanceof Constval) {
	    /* Expression ist eine boolesche Konstante */
	    return evalConstval( (Constval) expr);
	}
	/* Platz für Exception , ungültige Expression */
	return 42;
    }


    /** Methode zum Erfassen eines konstanten Wertes */
    private int evalConstval (Constval cVal) {
	Integer dummy;    // Wrapper für int
	int result;   // echter Integerwert für Ergebnis
	if (cVal.val instanceof Integer) {
	    dummy = (Integer)cVal.val;
	    result = dummy.intValue();
	    return result;
	}
	/* Platz für Exception, da die Konstante nicht Integer ist */
	return 42;
    }


    /** Methode zum Erfassen des Wertes einer Variablen 
     */
    private int evalVariable (Variable var) {
	SimulatorVariable sVar;
	/* die entsprechende SimulatorVariable an Land ziehen lassen */
	sVar = process.getVariable(var) ;
	/* casting ... */
	SimulatorIntValue iValue = (SimulatorIntValue) sVar.value;
	return iValue.value;
    }


    /** Methode zum Berechnen von einer U_expr mit integer Returnwert
     *	@param bExpr , die zu evaluierende U_expr
     *  @return int Wert des Ausdrucks
     */
    private int evalU_expr(U_expr uExpr) {
	/* einzige boolesche einstellige Operation ist das MINUS
	   also hat das Ergebnis der Sub_Expression Integer zu sein */
	SimulatorExprEvaluator oExpr = new SimulatorExprEvaluator(process, uExpr.sub_expr);
	SimulatorIntValue oValue = (SimulatorIntValue) oExpr.giveResult();
	int dummy = 0-(oValue.value);
	return dummy;
    }


    /** Methode zum Berechnen von einer B_expr mit integer Returnwert
     *	@param bExpr , die zu evaluierende B_expr
     *  @return int Wert des Ausdrucks
     */
    private int evalB_expr(B_expr bExpr) {
	SimulatorExprEvaluator leftExpr = new SimulatorExprEvaluator(process, bExpr.left_expr);
	SimulatorExprEvaluator rightExpr = new SimulatorExprEvaluator(process, bExpr.right_expr);
	// Hier wird implizit vorausgesetzt, daß beide Operanden Integer sind 
	SimulatorIntValue leftValue = (SimulatorIntValue) leftExpr.giveResult();
	SimulatorIntValue rightValue = (SimulatorIntValue) rightExpr.giveResult();
	switch(bExpr.op) {
	case 0 : {
	    // PLUS 
     	    int dummy = leftValue.value + rightValue.value;
	    return dummy;
	}
	case 1 : {
	    // MINUS 
     	    int dummy = leftValue.value - rightValue.value;
	    return dummy;
	}
	case 2 : {
	    // TIMES 
     	    int dummy = leftValue.value * rightValue.value;
	    return dummy;
	}
	case 3 : {
	    // PLUS 
     	    int dummy = leftValue.value / rightValue.value;
	    return dummy;
	}
	default : {
	    // Platz fuer Exception, da Operator falsch/fehlt
	    return 42;
	}
	}
    } // ------------------------------------ Ende evalB_expr() -------------------------------



 

    /** Methode zum Anstoßen der Berechnung und Rückgabe des Ergebnisses
     *
     * @return Integer-Ergebniswert
     */
    protected int giveResult () {
	value = this.evalExpression();
	return value;
    }


}
