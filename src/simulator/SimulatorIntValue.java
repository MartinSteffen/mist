package simulator;

import absynt.*;


/**
 *   Klasse zur Darstellung einer Integer Variablen in dem Simulator
 */

public class SimulatorIntValue extends SimulatorValue {

    /** Integer Instanzfeld , für den Wert der Variablen. 
     */
    private int value;

    protected SimulatorIntValue (int _value) {
	value = _value;
    }

}
