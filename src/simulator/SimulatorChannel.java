package simulator;

import absynt.*;
import java.util.*;

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
    protected ArrayList writerList; 
    protected ArrayList readerList; 
    
    protected SimulatorChannel (Channel _progChannel){
    progChannel = _progChannel;
    }

    /** 
     * Methoden zum eintragen der Writers / Readers
     */

    protected void addWriter(Transition writerTrans){
	writerList.add(writerProcess);
    }

    protected void addReader(Transition readerTrans){
	readerList.add(readerProcess);
    }
}
