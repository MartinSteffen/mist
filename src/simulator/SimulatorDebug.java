package simulator;

import absynt.*;
import java.util.*;

/** Klasse zum Speichern und Ausgeben von Debugmeldungen
 * eines Simulator-Schrittes
 * @author Michael Goemann
 */

public class SimulatorDebug {

    /** Klassenfeld, f�r Aufnahme der Debugmeldungen. 
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
     * als normales Array je nach eingestelltem Debuglevel zur�ckgibt 
     * @return Array von Strings 
     */
    protected String[] getWhole() {
	int size = msg.size();
	String[] result= new String[size];

	// Schleife �ber alle Debugmeldungen in ArrayList msg
	for (int count = 0; count < size; count++) {
	    SimulatorDebugMsg dummy = (SimulatorDebugMsg) msg.get(count);
	    if (dummy.debugLvl <= debugLvl){
		result[count] = dummy.msg;
	    }
	}
	return result;
    }


    /** Methode zum Hinzuf�gen einer Nachricht zu dem Debugstring 
     */
    protected void addMsg (String _msg, int _debugLvl){
	// Message generieren
	SimulatorDebugMsg dummy = new SimulatorDebugMsg(_msg, _debugLvl);
	// Message hinzuf�gen in Liste
	msg.add(dummy);
	/* sp�ter l�schen : */
	System.out.println(_msg); 	
    }


    /** Methode zum Setzen des Debuglevels
     * @param _debugLvl der Debuglevel halt
     */
    protected void setDebugLvl (int _debugLvl) {
	debugLvl = _debugLvl;
    }

    /** Methode zum L�schen der Debugmeldungsliste
     */
    protected void clear(){
	msg.clear();
    }


}

