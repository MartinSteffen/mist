package simulator;

import absynt.*;

/** Klasse zum Abpruefen, ob eine Transition feuern kann
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.0, 17/07/2000
*/

public class SimulatorPermTransitions {

 protected SimulatorProcess simProc;
 
 protected Transition actTrans;

    /* Konstruktor, nur uebergebene Felder umkopieren ... */

    public SimulatorPermTransitions(SimulatorProcess _simProc,Transition _actTrans ) {
         simProc = _simProc;
         actTrans = _actTrans;

    }

    /* Methode checkPermission, soll eine Transition mit hilfe des ExpressionEvaluators auf konzession
     * zum feuern abchecken. 
     * Rueckgabewert: ist boolean, true bedeutet: hat Konzession zum feuern.
     */

    protected boolean checkPermission (){

	/* SimulatorBoolEvaluator ins leben rufen, um im uebergebenen Prozesskontext den Guard der zu pruefenden
	 * Transition auswerten zu koennen.
	 */

	SimulatorBoolEvaluator boolExpr = new SimulatorBoolEvaluator (simProc, simProc.progProcess.steps.head.lab.guard);

	/* Ueberpruefen, ob der Ausgangszustand der zu checkenden Transition mit dem aktuellen Zustand uebereinstimmt.
	 * Anderenfalls hat die Transition keine Konzession zum feuern, daher dann false zurueckgeben.
	 */

	if (actTrans.source == simProc.activeState)

	    /* Falls also diese Transition vom aktuellen Zustand ausgeht, bestimmt der boolesche Wert der Guard-Expression,
	     * ob Konzession zum feuern besteht: ist Guard false, dann keine Konzession, ist er True, dann Konzession.
	     */

	     return ( boolExpr.giveResult() );  
	else return ( false );
    }

}
