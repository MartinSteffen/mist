package simulator;

/** Klasse zum Darstellen von einer Prozessvariablen
 * @author Michael Gömann
 * @author Michael Nimser 
 * @version  1.1, 07/03/2000
*/

public class SimulatorVariable{
    
    /** Feld fuer Namen der Variable, muss identisch zu entsprechenden Variablennamen aus 
     * abstrakter Syntax sein.
     */
    protected String name;

    /** Feld zur Aufnahme des Variablenwertes
     */
    protected Object wert;

    /** Konstruktor fuer Initialisierung einer Variablen */
    SimulatorVariable (String _name, Object _wert) {
       this.name = _name;
       this.wert = _wert;
    }

    /** Konstruktor fuer Deklaration einer Variablen */
    SimulatorVariable (String _name) {
       this.name = _name;
       this.wert = null;
    }

    /** Methode zum Zuweisen eines Wertes an eine existierende Variable */
    protected setVariable (Object _wert) {
	this.wert = _wert;
    }


}
