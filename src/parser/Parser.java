package parser;
import absynt.*;
import java.io.*;

/**
 * Public class to provide methods to parse an .mist-file and get an
 * object of absynt.Program (syntax-tree).
 * 
 * Klasse um Methoden zum Parsen einer .mist-Datei und um damit ein
 * Objekt der Klasse absynt.Program (abstrakter Syntax-Baum) zu
 * erhalten.
 *
 * Stellt nur die Methode absynt.Program parseFile(java.io.File
 * sourcefile) zur Verfügung.
 *
 * @author Andreas Scott, Alexander Hegener (unix13)
 * @version $Id: Parser.java,v 1.1 2000-06-07 13:09:47 unix01 Exp $
 */


public class Parser extends Object{

    absynt.Program parseFile(java.io.File sourcefile) 
	throws ParseException, IOException{
	return null;
    }

}
