/**
 * Exception-Klasse fuer Modelchecker.
 * @author Frank Neumann, Aneta Kuemper, Eike Schulz
 * @version 1.0
 */
package modcheck;

import absynt.*;


public class MCheckException extends Exception {
    private Astate[] errorStates;
	private String msg;
    /**
     * Konstruktor MCheckException
     * @param Fehlermeldung, Liste mit Fehler-Zustaenden.
     */
    public MCheckException (String _msg, Astate[] _errorStates) {
	msg=_msg;

errorStates= _errorStates;
    }

    /**
     * Methode zur Rueckgabe der Fehlermeldung, ueberschreibt java.Exception
     * @return Fehlermeldung
     */
    public String getMessage() { return msg;
    } 

    /**
     * Methode zur Rueckgabe der Fehler-Zustaende.
     * @return Fehler-Zustaende
     */
    public Astate[] getErrorStates () {
	return errorStates;
    }

} // class MCheckException
