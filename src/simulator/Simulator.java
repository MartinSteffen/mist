import absynt.*;

/**
 * class Simulator zum schrittweisen Simulieren von <b>Mist</b>-Programmen.
 *
 * Beinhaltet bisher nur die Rahmen von als Schnittstellen anzubietenden Methoden.
 * Die Methoden sind bisher noch NOOPs. 
 *
 * @author Michael Gömann
 * @author Michael Nimser 
 * @version  1.1, 06/18/2000
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
     * Schafft ein neues Objekt vom Typ Simulator
     * 
     * @param program das Program, das simuliert werden soll
     */
    public Simulator(Program program) {
	this.p = program;
	this.active = false;
    }


    /** 
     * Methode zum Anfahren der Simulation.
     *
     * Initialisiert die zur Berechnung der Simulation benötigten Felder und
     * setzt den Initialzustand.
     */
    public void Start() {
	this.active = true;
    }

    /**
     * Methode zum Ausführen des nächsten Programmschrittes.
     *
     * Berechnet aus dem aktuellen Zustand den Nachfolgezustand und 
     * setzt diesen.
     */
    public void Step() {
	if (this.active) {
	    Debug("Step","stepped to next state - still active");
	}
	else {
	    Debug("Step","no stepping possible - inactive");	    
	}
    }

    /**
     * Methode zum Erfragen, ob das Programm im Simulator noch aktiv ist. 
     *
     * Aktiv heißt hier, daß das Programm noch nicht terminiert ist.
     */
    public boolean IsProgamRunning() {
	return (this.active);
    }

    /**
     * Methode zum Erzeugen von Debugmeldungen
     */
    protected void Debug(String pos,String mes) {
	System.out.println("################## debug ##################"); 
	System.out.println("# Position : "+pos); 	
	System.out.println("# Message  : "+mes); 
    }



}
