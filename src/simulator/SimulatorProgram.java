package simulator;

import absynt.*;
import java.util.*;

/**
 * class SimulatorProgramState - Klasse die den Zustand eines im Ablauf befindlichen
 * <b>Mist-Programms</b> modelliert.
 * @author Michael Goemann
 * @author Michael Nimser 
 * @version  1.3, 07/03/2000
 */


public class SimulatorProgram {
    
    /** Instanzfeld für die Prozesse und deren Zustände eines Programms als ArrayListe
     */
    protected ArrayList processList;

    /** Instanzfeld für die Channel eines Programms als ArrayListe
     */
    protected ArrayList channelList;

    /** 
     *Referenz auf oberste Klasse "Simulator"
     */
    protected Simulator sim;

    /** 
     * Instanzfeld für Debugmeldung-Generation
     */
    protected SimulatorDebug debug;

    /** Instanzfeld , das die Referenz auf das zu simulierende Programm 
     * enthält.
     */
    protected Program progProgram;


    /** Konstruktor für ein SimulatorProgramm 
     * @param _program  Referenz auf zu simulierendes Programm
     */
    protected SimulatorProgram (Simulator _sim) {
        sim = _sim;
	progProgram = sim.p ; 	// Referenz auf zu simulierendes Programm setzen  
	debug = new SimulatorDebug();
	debug.addMsg("# Starting to generate processList ...",1);
      	processList = this.makeProcessList();
	debug.addMsg("# processList built up ...",1);
	debug.addMsg("# Starting to generate channelList ...",1);
	channelList = this.makeChannelList();
	debug.addMsg("# channelList built up ...",1);
    }


    /** Methode zum Generieren der Prozess-Liste 
     */
    protected ArrayList makeProcessList () {

	/* pointer zeigt auf den aktuell zu scannenden Prozess des Programms  */
	ProcessList pointer = this.progProgram.procs ;

	/* ArrayList in der die Prozesse generiert werden,
	   ist Rückgabewert dieser Methode */
	ArrayList result = new ArrayList();

	/* boolesche Variable für while-Schleifen-Logik */
	boolean go = false;

	if (pointer != null) // checken, ob ein Prozess in der Prozessliste existiert
	    go = true;

	while (go) {
	    
	    /* Neuen Prozess in die Liste hinzufügen */
	    result.add( new SimulatorProcess(pointer.head) );

	    /* Der Prozess wurde nun in die ArrayList aufgenommen,
	       also weiter ....
	    */

	    if (pointer.hasMoreElements()) {
		// wenn es noch einen Prozess hinter dem aktuellen gibt
		pointer = pointer.next;
		// dann pointer auf Nachfolger versetzen
	    }
	    else
		/* Abbruchbedingung für While-Schleife setzen */
		go = false;

	}// ------ end while -------

	return result;
	
    }




    /** Methode zum Generieren der Kanal-Liste
     */
    protected ArrayList makeChannelList () {

	/* pointer zeigt auf den aktuell zu scannenden Channel des Programms  */
	ChandecList pointer = this.progProgram.chans ;

	/* ArrayList in der die Kanäle generiert werden,
	   ist Rückgabewert dieser Methode */
	ArrayList result = new ArrayList();

	/* boolesche Variable für while-Schleifen-Logik */
	boolean go = false;

	if (pointer != null) // checken, ob ein Channel in der Channelliste existiert
	    go = true;

	while (go) {
	    
	    /* Neuen Channel in die Liste hinzufügen */
	    result.add( new SimulatorChannel(pointer.head.chan) );

	    /* Der Kanal wurde nun in die ArrayList aufgenommen,
	       also weiter ....
	    */

	    if (pointer.hasMoreElements()) {
		// wenn es noch einen Kanal hinter dem aktuellen gibt
		pointer = pointer.next;
		// dann pointer auf Nachfolger versetzen
	    }
	    else
		/* Abbruchbedingung für While-Schleife setzen */
		go = false;

	}// ------ end while -------

	return result;
    }


    /**
     * Fuer jeden Prozess werden die Transitionen, Welche lesend oder
     * schreibend mit Channels kommunizieren, als reader oder writer
     * am entsprechenden Ort in die Channelliste eingetragen.
     */

    protected ArrayList fillChannelList (ArrayList _ChannelList){
	ArrayList result = _ChannelList;
        SimulatorProcess CheckThisOut;
	int MaxEl = processList.size();
	int Counter = 0;
	for (Counter=0; Counter < MaxEl; Counter ++){
            CheckThisOut = (SimulatorProcess)processList.get(Counter);
	    result = CheckThisOut.fillInTransitions(result);
	}
	return result;
    }

    protected void askQuestion () {
	String[] ask= new String[3];
	ask[0]="Honig ?";
	ask[1]="Marmelade ?";
	ask[2]="Nutella ?";
	StrSelWin mySel = new StrSelWin("Benutzereingabe", "Bitte wähle aus", ask, true);
	int selection = mySel.getSel();
    }

}
