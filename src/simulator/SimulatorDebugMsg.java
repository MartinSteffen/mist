package simulator;

import absynt.*;

/** Klasse zur Modellierung einer Debugmeldung
 * @author Michael Goemann
 */

public class SimulatorDebugMsg {

    /** Ausgabe ab welchem Debuglevel ?
     */
    protected int debugLvl;

    /** auszugebende Meldung 
     */
    protected String msg;


    protected SimulatorDebugMsg (String _msg, int _debugLvl) {
	debugLvl = _debugLvl;
	msg = _msg;
    }

}
