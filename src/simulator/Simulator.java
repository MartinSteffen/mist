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
 * @author Michael Gömann
 * @author Michael Nimser 
 * @version  1.4, 06/26/2000
 */


public class Simulator {

    /**
     * Instanz-Feld für ein Programm in abstrakter Syntax.
     */
    protected Program p ;

    /**
     * Instanz-Feld als Flag für den Laufzustand eines Programmes im Simulator
     */
    protected boolean active;
    

    /**
     * Instanzfeld fuer den Debuglevel 
     * Wert nur von 0 bis 4 zulaessig
     */
    protected int debugLvl;

    /** 
     * Schafft ein neues Objekt vom Typ Simulator
     * 
     * @param gui Referenz auf die aufrufende Gui-Instanz
     */
    public Simulator(GUI gui) {
	this.active = false;
    }


    /** 
     * Methode zum Anfahren der Simulation.
     *
     * Initialisiert die zur Berechnung der Simulation benötigten Felder und
     * setzt den Initialzustand.
     * @param program Parameter fuer das zu simulierende Programm 
     * @return Ein Array von Strings welches abhaengig vom eingestellten
     * Debuglevel Debugmeldungen der Initialisierung des Simulators enthaelt.
     */
    public String[] start(Program program) {
	this.p = program;
	this.active = true;
	return (null) ;
    }

    /**
     * Methode zum Ausführen des nächsten Programmschrittes.
     *
     * Berechnet aus dem aktuellen Zustand den Nachfolgezustand und 
     * setzt diesen.
     * @return Ein Array von Strings welches abhaengig vom eingestellten
     * Debuglevel Debugmeldungen des letzten Programmschritts der Simulation enthaelt.
     */
    public String[] step() {
	if (this.active) {
	    return (null) ;
	}
	else {
	    return (null );	    
	}
    }

    /**
     * Methode zum Erfragen, ob das Programm im Simulator noch aktiv ist. 
     *
     * Aktiv heißt hier, daß das Programm noch nicht terminiert ist.
     * @return Ein boolescher Wert, true wenn Prog aktiv, false sonst.
     */
    public boolean isProgamRunning() {
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

    /**
     * Methode zum Erzeugen von Debugmeldungen
     */
    protected void debug(String pos,String mes) {
	System.out.println("################## debug ##################"); 
	System.out.println("# Position : "+pos); 	
	System.out.println("# Message  : "+mes); 
    }



}
