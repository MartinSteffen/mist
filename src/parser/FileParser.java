package parser;

import java.io.IOException;
import java.io.*;


/**
 * Zum Testen des Parsers mit Dateien
 * @author Andreas Scott &amp; Alexander Hegener
 * @version $Id: FileParser.java,v 2.1 2000-07-17 14:10:19 unix01 Exp $
 * <-- ================
 * $Log: not supported by cvs2svn $
 * Revision 2.0  2000/07/17 10:45:21  unix01
 * Umstellung auf CUP
 *
 * Revision 1.1  2000/07/14 06:09:32  unix01
 * Klasse um den Parser zu testen [Scott]
 *
 * ============== //-->
 */
class FileParser{
 
  public static void main (String args []) {
	  if (args.length < 1){
		  System.out.println("Usage: ParseTester <filename>");
		  return;
	  }
	  try{
		  File datei = new File(args[0]);

		  parser.Parser parser = new parser.Parser();
		  parser.parseFile(datei);
	  } catch (ParseException e){
		  System.err.println(e.toString());
		  System.exit(1);
	  } catch (IOException e){
		  System.err.println(e.toString());
		  System.exit(1);
	  } catch (Exception e){
	      System.err.println(e.toString());
	      System.exit(1);
	  }
  }
}




