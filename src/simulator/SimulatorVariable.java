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
    protected String name;

    /** Instanzfeld zur Aufnahme des Variablenwertes
     */
    protected Object wert;

    /** Instanzfeld für Referenz auf Variable aus zu simulierendem 
     * Prozess.
     */
    protected Variable progVariable;

    /** Konstruktor fuer INITIALISIERUNG einer neuen Variablen
     * @param _name Name der Variablen 
     * @param _wert Wert der Variablen
     * @param _variable Referenz auf Variable aus zu simulierenden Programm
     */
    protected SimulatorVariable (String _name, Variable _variable, Object _wert) {
       this.name = _name;
       this.wert = _wert;
       this.progVariable = _variable;
    }

    /** Konstruktor fuer DEKLARATION einer Variablen 
     * @param _name Name der Variablen 
     * @param _variable Referenz auf Variable aus zu simulierenden Programm
     */
    protected SimulatorVariable (String _name, Variable _variable) {
       this.name = _name;
       this.progVariable = _variable;
       this.wert = null;
    }

    /** Methode zum ZUWEISEN eines Wertes an eine existierende Variable
     * @param _wert zuzuweisender Wert
     * Es findet keine Typüberprüfung statt -> Aufgabe Gruppe Checker
     */
    protected void setVariable (Object _wert) {
	this.wert = _wert;
    }
 

}
