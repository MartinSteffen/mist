/**
   Beispiel fuer die Verwendung des Pretty Printers.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: PpExample.java,v 1.3 2000-06-28 05:44:49 unix01 Exp $    
 */

package utils;
import absynt.Example;
import utils.PrettyPrint;

public class PpExample {

  /**
   * main-Methode -> Ausgabe des Beispiels
   */
  public static void main (String[] args) {

    // Erzeuge PP-Objekt.
    PrettyPrint pp = new PrettyPrint ();
    pp.print (Example.getExample1());
    
  } // method main

} // class PpExample



//----------------------------------------------------------------------
//	Utilites for Mist Programs
//	------------------------------------
//
//	$Id: PpExample.java,v 1.3 2000-06-28 05:44:49 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/06/04 12:06:16  unix01
//	Stubs implementiert
//	
//	
//---------------------------------------------------------------------
