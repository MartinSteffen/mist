/**
 * Exception-Klasse fuer Modelchecker.
 * @author Frank Neumann, Aneta Kuemper, Eike Schulz
 * @version 1.0
 */
package modcheck;

import absynt.*;


public class MCheckException extends Exception {
    private Astate[] errorStates;

    /**
     * Konstruktor MCheckException
     * @param Fehlermeldung, Liste mit Fehler-Zustaenden.
     */
    public MCheckException (String _msg, Astate[] _errorStates) {
	super(_msg);
    }

    /**
     * Methode zur Rueckgabe der Fehlermeldung, ueberschreibt java.Exception
     * @return Fehlermeldung
     */
    public String getMessage() { return "";
    } 

    /**
     * Methode zur Rueckgabe der Fehler-Zustaende.
     * @return Fehler-Zustaende
     */
    public Astate[] getErrorStates () {
	return errorStates;
    }

} // class MCheckException
