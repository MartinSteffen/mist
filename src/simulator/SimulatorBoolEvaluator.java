package simulator;

import absynt.*;

/** Klasse zum Auswerten von booleschen Expressions in einem Prozess
 *
 * Wir haben keine Ordnung auf den booleschen Constanten definiert, d.h.
 * daß LESS, GREATER, LEQ, GEQ, NEQ nur für Operanden mit Integerwerten
 * berechenbar sind.
 * @author Michael Goemann
 * @version  1.1, 07/06/2000
*/

public class SimulatorBoolEvaluator {

    /** Instanzfeld für Referenz auf den Prozess, in dem der Ausdruck ausgewertet
	werden soll , nötig für den Variablenkontext, wenn eine Auswertung außerhalb
	von der Variablendeklaration erfolgen soll.
    */
    private SimulatorProcess process;

    /** Instanzfeld für Referenz auf den zu evaluierenden Ausdruck
     */
    private Expr expr;

    /** Instanzfeld , für booleschen Ergebniswert
     */
    private boolean value;

    /**
     * Instanzfeld, um Zugriff auf Debugfunktionen zu haben
     */
    protected SimulatorDebug debug;

    /** Konstruktor für einen Expression Evaluator mit Prozesskontext
     * 
     * @param _process Referenz auf den SimulatorProcess, in dem evaluiert werden soll 
     * @param _expr Referenz auf die zu evaluierende Expression
     */
    protected SimulatorBoolEvaluator(SimulatorProcess _process, Expr _expr) {
	debug = new SimulatorDebug();
	process = _process;
	expr = _expr;
    }


    /** Methode zum Errechnen des Wertes eines Ausdrucks
     *
     * @param expr, der auszurechnende boolesche Ausdruck
     * @return booleschen Wert des Ausdrucks
     */
    private boolean evalExpression (){
	/* Typ der Expression abchecken und je nachdem weiter */
	if (expr instanceof B_expr) {
	    /* zweistellige boolesche Expression auswerten */ 
	    return evalB_expr( (B_expr) expr);
	}
	if (expr instanceof U_expr) {
	    /* einstellige boolesche Expression auswerten */
	    return evalU_expr( (U_expr) expr);
	}
	if (expr instanceof Variable) {
	    /* Expression ist eine Variable; diese im Prozesskontext suchen und 
	       dann deren Value zurückgeben. 
	       Das Value muß hier boolean sein */
	    return evalVariable( (Variable) expr );
	}
	if (expr instanceof Constval) {
	    /* Expression ist eine boolesche Konstante */
	    return evalConstval( (Constval) expr);
	}
	/* Platz für Exception , ungültige Expression */
	return true;
    }


    /** Methode zum Erfassen eines konstanten Wertes */
    private boolean evalConstval (Constval cVal) {
	Boolean dummy;    // Wrapper für boolean
	boolean result;   // echter boolescher Wert für Ergebnis
	if (cVal.val instanceof Boolean) {
	    debug.addMsg("!!! Haaaloooo !!! : ",0);
	    dummy = (Boolean)cVal.val;
	    result = dummy.booleanValue();
	    return result;
	}
	/* Platz für Exception, da die Konstante nicht boolean ist */
	debug.addMsg("!!! EXCEPTION !!! : ",0);
	debug.addMsg("!!! Class: SimulatorBoolEvaluator, Method : evalConstval, pos.:1 ",0);
       	return false;
    }


    /** Methode zum Erfassen des Wertes einer Variablen 
     */
    private boolean evalVariable (Variable var) {
	SimulatorVariable sVar;
	/* die entsprechende SimulatorVariable an Land ziehen lassen */
	sVar = process.getVariable(var) ;
	/* casting ... */
	SimulatorBoolValue bValue = (SimulatorBoolValue) sVar.value;
	return bValue.value;
    }


    /** Methode zum Berechnen von einer U_expr mit booleschem Returnwert
     *	@param bExpr , die zu evaluierende U_expr
     */
    private boolean evalU_expr(U_expr uExpr) {
	/* einzige boolesche einstellige Operation ist die Negation,
	   also hat das Ergebnis der Sub_Expression ebenfalls boolean zu sein */
	SimulatorExprEvaluator oExpr = new SimulatorExprEvaluator(process, uExpr.sub_expr);
	SimulatorBoolValue oValue = (SimulatorBoolValue) oExpr.giveResult();
	boolean dummy = !(oValue.value);
	return dummy;
    }


    /** Methode zum Berechnen von einer B_expr mit booleschem Returnwert
     *	@param bExpr , die zu evaluierende B_expr
     */
    private boolean evalB_expr(B_expr bExpr) {
	SimulatorExprEvaluator leftExpr = new SimulatorExprEvaluator(process, bExpr.left_expr);
	SimulatorExprEvaluator rightExpr = new SimulatorExprEvaluator(process, bExpr.right_expr);
	switch(bExpr.op) {
	case 4 : {
	    // AND 
	    // Hier wird implizit vorausgesetzt, daß beide Operanden boolesche sind 
	    SimulatorBoolValue leftValue = (SimulatorBoolValue) leftExpr.giveResult();
	    SimulatorBoolValue rightValue = (SimulatorBoolValue)rightExpr.giveResult();
     	    boolean dummy = leftValue.value & rightValue.value;
	    return dummy;
	}
	case 5 : {
	    // OR 
	    // Hier wird implizit vorausgesetzt, daß beide Operanden boolesche sind 
	    SimulatorBoolValue leftValue = (SimulatorBoolValue) leftExpr.giveResult();
	    SimulatorBoolValue rightValue = (SimulatorBoolValue)rightExpr.giveResult();
     	    boolean dummy = leftValue.value | rightValue.value;
	    return dummy;
	} 
	 
	case 7 : {
	    // EQ
	    /* hier ex. zwei Möglichkeiten :
	       1. leftExpr & rightExpr sind boolesch
	       2. beide sind vom Typ Integer, also : */
	    boolean dummy;
	    if ( bExpr.left_expr.type instanceof M_Bool) {
		// 1. Fall, also :
		SimulatorBoolValue leftValue = (SimulatorBoolValue) leftExpr.giveResult();
		SimulatorBoolValue rightValue = (SimulatorBoolValue)rightExpr.giveResult();
		dummy = leftValue.value == rightValue.value;
	    }
	    else {
		// 2. Fall, also :
		SimulatorIntValue leftValue = (SimulatorIntValue) leftExpr.giveResult();
		SimulatorIntValue rightValue = (SimulatorIntValue)rightExpr.giveResult();
		dummy = leftValue.value == rightValue.value;
	    }
	    return dummy;
	}
	case 8 : {
	    // LESS
	    // Implizite Annahme, daß beide Operanden vom Typ int sind
		SimulatorIntValue leftValue = (SimulatorIntValue) leftExpr.giveResult();
		SimulatorIntValue rightValue = (SimulatorIntValue)rightExpr.giveResult();
		boolean dummy = leftValue.value < rightValue.value;
		return dummy;
	}
	case 9 : {
	    // GREATER
	    // Implizite Annahme, daß beide Operanden vom Typ int sind
		SimulatorIntValue leftValue = (SimulatorIntValue) leftExpr.giveResult();
		SimulatorIntValue rightValue = (SimulatorIntValue)rightExpr.giveResult();
		boolean dummy = leftValue.value > rightValue.value;
		return dummy;
	}
	case 10 : {
	    // LEQ
	    // Implizite Annahme, daß beide Operanden vom Typ int sind
		SimulatorIntValue leftValue = (SimulatorIntValue) leftExpr.giveResult();
		SimulatorIntValue rightValue = (SimulatorIntValue)rightExpr.giveResult();
		boolean dummy = leftValue.value <= rightValue.value;
		return dummy;
	}
	case 11 : {
	    // GEQ
	    // Implizite Annahme, daß beide Operanden vom Typ int sind
		SimulatorIntValue leftValue = (SimulatorIntValue) leftExpr.giveResult();
		SimulatorIntValue rightValue = (SimulatorIntValue)rightExpr.giveResult();
		boolean dummy = leftValue.value >= rightValue.value;
		return dummy;
	}
	case 12 : {
	    // NEQ
	    /* hier ex. zwei Möglichkeiten :
	       1. leftExpr & rightExpr sind boolesch
	       2. beide sind vom Typ Integer, also : */
	    boolean dummy;
	    if  (bExpr.left_expr.type instanceof M_Bool) {
		// 1. Fall, also :
		SimulatorBoolValue leftValue = (SimulatorBoolValue) leftExpr.giveResult();
		SimulatorBoolValue rightValue = (SimulatorBoolValue)rightExpr.giveResult();
		dummy = leftValue.value != rightValue.value;
	    }
	    else {
		// 2. Fall, also :
		SimulatorIntValue leftValue = (SimulatorIntValue) leftExpr.giveResult();
		SimulatorIntValue rightValue = (SimulatorIntValue)rightExpr.giveResult();
		dummy = leftValue.value != rightValue.value;
	    }
	    return dummy;
	}    

		
	default : {
		//Platz zum Exception abfeuern
	    return false;
	}
	}
    } // ------------------------------------ Ende evalB_expr() -------------------------------


    /** Methode zum Anstoßen der Berechnung und Rückgabe des Ergebnisses
     *
     * @return booleschen Ergebniswert
     */
    protected boolean giveResult () {
	value = this.evalExpression();
	return value;
    }

}
