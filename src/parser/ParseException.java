package parser;

/**
 * Exception-Class to provide some information about parse-errors.
 * 
 * Exception-Class um Informationen an die aufrufenden Klassen
 * weiterzuleiten.
 *
 * @author Andreas Scott, Alexander Hegener (unix13)
 */

public class ParseException extends Exception{

    
    /**
     * Default-Konstruktor, erzeugt Exception mit Fehler: "syntax error"
     */
    ParseException(){
	super("syntax error");
    }

    /**
     * Konstruktor, erzeugt Exception mit Fehler errString
     */
    ParseException(String errString){
	super(errString);
    }

}
