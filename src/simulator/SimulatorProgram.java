package simulator;

import absynt.*;


/**
 * class SimulatorProgramState - Klasse die den Zustand eines im Ablauf befindlichen
 * <b>Mist-Programms</b> modelliert.
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.2, 07/03/2000
 */


public class SimulatorProgram {
    
    /** Instanzfeld für die Prozesse und deren Zustände eines Programms als Array
     */
    protected SimulatorProcess[] processList;

    /** Instanzfeld für die Channel eines Programms als Array
     */
    protected SimulatorChannel[] channelList;

    /** Instanzfeld , das die Referenz auf das zu simulierende Programm 
     * enthält.
     */
    protected Program progProgram;


    /** Konstruktor für ein SimulatorProgramm 
     * @param _program  Referenz auf zu simulierendes Programm
     */
    SimulatorProgram (Program _program) {
	progProgram = _program ; 	// Referenz auf zu simulierendes Programm setzen  
      	processList = null;
	channelList = null;
    }


}
