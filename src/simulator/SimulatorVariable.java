package simulator;

import absynt.*;


/** Klasse zum Darstellen von einer Prozessvariablen
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.2, 07/03/2000
*/

public class SimulatorVariable{
    
    /** Instanzfeld fuer Namen der Variable, muss identisch zu entsprechenden 
     * Variablennamen aus abstrakter Syntax sein.
     */
    private String name;

    /** Instanzfeld für Referenz auf Variable aus zu simulierendem 
     * Prozess.
     */
    private Variable progVariable;

    /** Boolesches Instanzfeld , zeigt an, ob der Typ der Variablen feststeht.
     */
    private boolean typeSet;

    /** Boolesches Instanzfeld , für den Fall, daß die Variable vom Typ boolean ist. 
     */
    private boolean boolValue;

    /** Integer Instanzfeld, für den Fall, daß die Variable vom Typ int ist. 
     */
    private int intValue;


    /** Konstruktor fuer INITIALISIERUNG einer neuen Variablen mit INTEGERwert
     * @param _name Name der Variablen 
     * @param _wert zuzuweisender Integerwert 
     * @param _variable Referenz auf Variable aus zu simulierendem Program
     */
    protected SimulatorVariable (String _name, Variable _variable, int _wert) {
       this.name = _name;
       this.progVariable = _variable;
       this.typeSet = true;
       this.intValue = _wert;
    }

    /** Konstruktor fuer INITIALISIERUNG einer neuen Variablen mit BOOLESCHEM Wert
     * @param _name Name der Variablen 
     * @param _wert zuzuweisender Integerwert 
     * @param _variable Referenz auf Variable aus zu simulierendem Program
     */
    protected SimulatorVariable (String _name, Variable _variable, boolean _wert) {
       this.name = _name;
       this.progVariable = _variable;
       this.typeSet = true;
       this.boolValue = _wert;
    }

    /** Konstruktor fuer DEKLARATION einer Variablen 
     * @param _name Name der Variablen 
     * @param _variable Referenz auf Variable aus zu simulierenden Programm
     */
    protected SimulatorVariable (String _name, Variable _variable) {
       this.name = _name;
       this.progVariable = _variable;
       this.typeSet = false;
    }

    /** Methode zum ZUWEISEN eines Integer-Wertes an eine existierende Variable
     * @param _wert zuzuweisender Integer-Wert
     */
    protected void setVariable (int _wert) {
	this.intValue = _wert;	
        this.typeSet = true;
    }
 
    /** Methode zum ZUWEISEN eines booleschen Wertes an eine existierende Variable
     * @param _wert zuzuweisender Integer-Wert
     */
    protected void setVariable (boolean _wert) {
	this.boolValue = _wert;
        this.typeSet = true;
    }

}
