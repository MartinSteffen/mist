package simulator;

import absynt.*;
import java.util.*;

/** Klasse zum Speichern und Ausgeben von Debugmeldungen
 * eines Simulator-Schrittes
 * @author Michael Goemann
 */

public class SimulatorDebug {

    /** Klassenfeld, für Aufnahme der Debugmeldungen. 
     * static, d.h. als Klassenfeld, damit u.U.von allen Klassen aus
     * einfach erreichbar. 
     */
    protected static ArrayList msg;

    /** Klassenfeld zum Vorhalten des Debuglevels
     * Range von 0 bis 4
     */
    protected static int debugLvl;

    /** Konstruktor mit Debuglvl (von Sumulator.java aus)
     * @param _debugLvl zum Debuglevel setzen
     */
    protected SimulatorDebug (int _debugLvl) {
	msg = new ArrayList();
	debugLvl = _debugLvl ;
    }


    /** Konstruktor
     */
    protected SimulatorDebug () {
    }


    /** Methode, die die gesamten Debugmeldungen eines Schrittes 
     * als normales Array je nach eingestelltem Debuglevel zurückgibt 
     * @return Array von Strings 
     */
    protected String[] getWhole() {
	int size = msg.size();
	String[] result= new String[size];

	// Schleife über alle Debugmeldungen in ArrayList msg
	for (int count = 0; count < size; count++) {
	    SimulatorDebugMsg dummy = (SimulatorDebugMsg) msg.get(count);
	    if (dummy.debugLvl <= debugLvl){
		result[count] = dummy.msg;
	    }
	}
	return result;
    }


    /** Methode zum Hinzufügen einer Nachricht zu dem Debugstring 
     */
    protected void addMsg (String _msg, int _debugLvl){
	// Message generieren
	SimulatorDebugMsg dummy = new SimulatorDebugMsg(_msg, _debugLvl);
	// Message hinzufügen in Liste
	msg.add(dummy);
	/* später löschen : */
	System.out.println(_msg); 	
    }


    /** Methode zum Setzen des Debuglevels
     * @param _debugLvl der Debuglevel halt
     */
    protected void setDebugLvl (int _debugLvl) {
	debugLvl = _debugLvl;
    }

    /** Methode zum Löschen der Debugmeldungsliste
     */
    protected void clear(){
	msg.clear();
    }


}

