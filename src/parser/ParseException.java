package parser;

/**
 * Exception-Class to provide some information about parse-errors.
 * 
 * Exception-Class um Informationen an die aufrufenden Klassen
 * weiterzuleiten.
 *
 * @author Andreas Scott, Alexander Hegener (unix13)
 * @version $Id: ParseException.java,v 1.2 2000-06-07 13:13:04 unix01 Exp $
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
