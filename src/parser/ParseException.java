package parser;

/**
 * Exception-Class um Ausnahmen an die aufrufenden Klassen
 * weiterzuleiten.
 *
 * @author Andreas Scott, Alexander Hegener (unix13)
 * @version $Id: ParseException.java,v 2.1 2000-07-18 22:19:12 unix01 Exp $
 *
 * <!-- Log=====
 * $Log: not supported by cvs2svn $
 * Revision 2.0  2000/07/17 10:45:22  unix01
 * Umstellung auf CUP
 *
 * Revision 1.3  2000/07/09 18:35:05  unix01
 * die Methode parseExpression(java.lang.String als stub hinzugefügt
 * javadoc Kommentare hinzugefügt
 * log aktiviert
 *
 * ============= //-->
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
     * @parameter 
     * - errString: Nachricht
     */
    ParseException(String errString){
        super(errString);
    }

}
