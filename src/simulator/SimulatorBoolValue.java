
package simulator;

import absynt.*;


/**
 *   Klasse zur Darstellung einer booleschen Variablen in dem Simulator
 */

public class SimulatorBoolValue extends SimulatorValue {

    /** Boolesches Instanzfeld , f�r den Wert der Variablen. 
     */
    private boolean value;

    protected SimulatorBoolValue (boolean _value) {
	value = _value;
    }

}
