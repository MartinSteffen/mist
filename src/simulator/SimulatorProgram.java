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
    
    /** Instanzfeld f�r die Prozesse und deren Zust�nde eines Programms als Array
     */
    protected SimulatorProcess[] processList;

    /** Instanzfeld f�r die Channel eines Programms als Array
     */
    protected SimulatorChannel[] channelList;

    /** Instanzfeld , das die Referenz auf das zu simulierende Programm 
     * enth�lt.
     */
    protected Program progProgram;


    /** Konstruktor f�r ein SimulatorProgramm 
     * @param _program  Referenz auf zu simulierendes Programm
     */
    SimulatorProgram (Program _program) {
	progProgram = _program ; 	// Referenz auf zu simulierendes Programm setzen  
      	processList = null;
	channelList = null;
    }


}
