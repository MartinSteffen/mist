package parser;

import java.io.IOException;
import java.io.*;


/**
 * Zum Testen des Parsers mit einzelnen Ausdruecken
 * @author Andreas Scott &amp; Alexander Hegener
 * @version $Id: LineParser.java,v 2.0 2000-07-17 10:45:22 unix01 Exp $
 * <-- ================
 * $Log: not supported by cvs2svn $
 * Revision 1.1  2000/07/14 06:09:33  unix01
 * Klasse um den Parser zu testen [Scott]
 *
 * ============== //-->
 */
class LineParser{
 
  public static void main (String args []) {
	  absynt.Expr ausdr = null;

	  if (args.length < 1){
		  System.out.println("Usage: LineParser <expression>");
		  return;
	  }
	  try{
		  
		  parser.Parser p = new parser.Parser();
		  ausdr = p.parseExpression(args[0]);
		  if (ausdr == null)
			  System.out.println("Ausdruck ist null");
	  } catch (ParseException e){
		  System.err.println(e.toString());
		  System.exit(1);
	  } catch (IOException e){
		  System.exit(1);
	  }
  }
}



