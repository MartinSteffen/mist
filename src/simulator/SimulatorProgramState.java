package simulator;



/**
 * class SimulatorProgramState - Klasse die den Zustand eines im Ablauf befindlichen
 * <b>Mist-Programms</b> modelliert.
 * @author Michael Gömann
 * @author Michael Nimser 
 * @version  1.0, 07/03/2000
 */


public class SimulatorProgramState {
    
    /** Instanzfeld für die Prozesszustände eines Programms als Array
     */
    protected SimulatorProcessState[] processStateList;

    protected SimulatorChannel[] channelList;


    /** Konstruktor zur Generierung eines Objektes, das den Programmzustand 
     * wiederspiegeln können soll. 
     */

    SimulatorProgramState (int numberOfProcesses) {
	processStateList = new SimulatorProcessState[numberOfProcesses];
    }


}
