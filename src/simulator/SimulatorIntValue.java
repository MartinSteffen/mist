package simulator;

import absynt.*;


/**
 * Klasse zur Darstellung einer Integer Variablen in dem Simulator
 * @author Michael Goemann 
 */

public class SimulatorIntValue extends SimulatorValue {

    /** Integer Instanzfeld , für den Wert der Variablen. 
     */
    protected int value;

    protected SimulatorIntValue (int _value) {
	value = _value;
    }

}
