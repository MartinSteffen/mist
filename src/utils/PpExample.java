/**
   Beispiel fuer die Verwendung des Pretty Printers.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: PpExample.java,v 1.1 2000-06-04 12:03:12 unix01 Exp $    
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

