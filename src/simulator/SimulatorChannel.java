package simulator;

import absynt.*;

/** Klasse zur Simulation von Channels
 * @author Michael Gömann
 * @author Michael Nimser 
 * @version  1.0, 07/03/2000
*/


public class SimulatorChannel {

    /** Feld das jeder Instanz vom Typ SimulatorChannel eine Referenz auf 
     * den Channel aus dem zu simulierenden Programm zuweist 
     */
    protected Channel progChannel;

    /** Feld als Array fuer */
    SimulatorProcess[] writerList; 
    SimulatorProcess[] readerList; 
    
    
}
