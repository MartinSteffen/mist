
package simulator;

import absynt.*;


/**
 * Klasse zur Darstellung einer booleschen Variablen in dem Simulator
 * @author Michael Nimser Goemann
 */

public class SimulatorBoolValue extends SimulatorValue {

    /** Boolesches Instanzfeld , für den Wert der Variablen. 
     */
    protected boolean value;

    protected SimulatorBoolValue (boolean _value) {
	value = _value;
    }

}
