package parser;

import java.io.IOException;
import java.io.FileInputStream;

/**
 * Stellt die Methoden parseFile und parseException der
 * Oeffentlichkeit zur Verfuegung
 *
 * @author Andreas Scott &amp; Alexander Hegener 
 * @version $Id: Parser.java,v 1.4 2000-07-11 11:39:17 unix01 Exp $
 * @see <a href="../../Scanner.lex">Scanner.lex</a>
 * @see Scanner
 * <!-- Log ==========
 * $Log: not supported by cvs2svn $
 * =============== -->
 * */

public class Parser{
	private Token token;

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
	  try{
		  parser.Scanner scanner = new Scanner(new FileInputStream(sourcefile));
		  token = scanner.nextToken();
		  if (token.type == Token.PROGRAM){
			  scanner.buildProgram();
		  } else
			  scanner.lexerror ("\"program\" erwartet."); 
	  } catch (IOException e) { 
		  System.err.println(e); 
		  System.exit(1);
	  } catch (parser.ParseException e){
		  System.err.println(e);
	  }
	  System.out.println();
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
