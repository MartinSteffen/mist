package parser;
import absynt.*;
import java.io.*;
import java.lang.*;

/**
 * Public class to provide methods to parse a .mist-file and get an
 * object of absynt.Program (syntax-tree).<br><br>
 * 
 * Klasse um Methoden zum Parsen einer .mist-Datei und um damit ein
 * Objekt der Klasse absynt.Program (abstrakter Syntax-Baum) zu
 * erhalten.
 *
 * Stellt nur die Methode absynt.Program parseFile(java.io.File
 * sourcefile) zur Verfügung.
 *
 * @author Andreas Scott, Alexander Hegener (unix13)
 * @version $Id: Parser.java,v 1.2 2000-07-09 18:35:06 unix01 Exp $
 *
 * <!-- Log=====
 * $Log: not supported by cvs2svn $
 * ============= //-->
 */


public class Parser extends Object{

	/** 
	 * public Methode um eine Datei zu parsen
	 *
	 * @parameter 
	 * sourcefile - zu parsende Datei
	 * @return 
	 * der abstrakte Syntaxbaum mit absynt.Program als Wurzel
	 * @throws 
	 * parser.ParseException falls ein Syntax-Fehler in der Datei vorliegt
	 * @throws
	 * java.io.IOException falls Datei nicht gelesen werden kann o. ae.
	 */
	public absynt.Program parseFile(java.io.File sourcefile) 
		throws ParseException, IOException{
		return null;
    }

	/**
	 * public method to parse a single expression<br><br>
	 *
	 * public Methode um einen einzelnen Ausdruck zu parsen
	 *
	 * @parameter
	 * die zu parsende Zeichenkette 
	 * @return 
	 * ein Ausdruck wie absynt.Expr
	 * @throws 
	 * parser.ParseException falls ein Syntax-Fehler vorliegt. Der Rueckgabe wert ist dann <b>null</b>
	 */
	public absynt.Expr parseExpression(java.lang.String expression) 
		throws ParseException{
		return null;
	}
	
}







