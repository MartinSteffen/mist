package simulator;

import absynt.*;
import gui.*;

/**
 * class Simulator zum schrittweisen Simulieren von <b>Mist</b>-Programmen.
 *
 * Das Dokument, was als <B>Spezifikationsgrundlage</B> dient findet man <A HREF="../specification.txt" TARGET="classFrame">
 * hier </A>.
 * Beinhaltet bisher nur die Rahmen von als Schnittstellen anzubietenden Methoden
 * und die Deklaration benoetitgter Felder.
 * Die Methoden sind bisher noch NOOPs. 
 *
 * @author Michael Gˆmann
 * @author Michael Nimser 
 * @version  1.4, 06/26/2000
 */


public class Simulator {

    /**
     * Instanz-Feld f¸r ein Programm in abstrakter Syntax.
     */
    protected Program p ;

    /**
     * Instanz-Feld als Flag f¸r den Laufzustand eines Programmes im Simulator
     */
    protected boolean active;
    
    /**
     * Instanzfeld, um Zugriff auf Debugfunktionen zu haben
     */
    protected SimulatorDebug debug;


    /**
     * Instanzfeld fuer den Debuglevel 
     * Wert nur von 0 bis 4 zulaessig
     */
    protected int debugLvl;


    /** 
     * Instanzfeld fuer Referenz auf das Simulator-Programm des
     * zu simulierenden Absynt-Programms
     */
    private SimulatorProgram sProg;

    /**
     * Referenz zur aufrufenden GUI lokal merken
     */

    private GUI guiRef;

    /** 
     * Schafft ein neues Objekt vom Typ Simulator
     * 
     * @param gui Referenz auf die aufrufende Gui-Instanz
     */
    public Simulator(GUI gui) {
	this.active = false;
	guiRef = gui;
	debug = new SimulatorDebug(4);
    }


    /** 
     * Schafft ein neues Objekt vom Typ Simulator
     * Diese Methode NICHT benutzen, da nur f¸r interne 
     * Testzwecke !!!!
     */
    public Simulator() {
	this.active = false;
	debug = new SimulatorDebug(4);
    }


    /** 
     * Methode zum Anfahren der Simulation.
     *
     * Initialisiert die zur Berechnung der Simulation benˆtigten Felder und
     * setzt den Initialzustand.
     * @param program Parameter fuer das zu simulierende Programm 
     * @return Ein Array von Strings welches abhaengig vom eingestellten
     * Debuglevel Debugmeldungen der Initialisierung des Simulators enthaelt.
     */
    public String[] start(Program program) {
	this.p = program;
	this.active = true; 
	debug.addMsg(">> Simulator is starting ...",1);
	this.sProg = new SimulatorProgram(this); 
	debug.addMsg("<< Simulator is initialized, Pograms in their Start-States.",1);
	return (debug.getWhole()) ;
    }

    /**
     * Methode zum Ausf¸hren des n‰chsten Programmschrittes.
     *
     * Berechnet aus dem aktuellen Zustand den Nachfolgezustand und 
     * setzt diesen.
     * @return Ein Array von Strings welches abhaengig vom eingestellten
     * Debuglevel Debugmeldungen des letzten Programmschritts der Simulation enthaelt.
     */
    public String[] step() {
	debug.clear(); // Debugmeldungen lˆschen
	if (this.active) {
	    debug.addMsg(">> Simulator taking one step forwards ...",1);
	}
	else {
	    debug.addMsg("# PRESS <Start> to start up Simulation of Program !!!",0);
	}
	return (debug.getWhole());	    
    }

    /**
     * Methode zum Erfragen, ob das Programm im Simulator noch aktiv ist. 
     *
     * Aktiv heiﬂt hier, daﬂ das Programm noch nicht terminiert ist.
     * @return Ein boolescher Wert, true wenn Prog aktiv, false sonst.
     */
    public boolean isProgramRunning() {
	return (this.active);
    }

    /** 
     * Methode zum Einstellen des Debuglevels 
     * 
     * Je hoeher der Debuglevel desto ausfuehrlicher sind die generierten Ausgaben
     * @param _debugLvl Integerwert fuer den Debuglevel ( 0 <= _debugLvl <= 4)
     */
    public void setDebugLvl(int _debugLvl){
    }

}
