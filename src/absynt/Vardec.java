package absynt;
import java.io.Serializable;


/**
 * Variablendeklaration.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Vardec.java,v 1.2 2000-06-04 10:22:04 unix01 Exp $
 */


public class Vardec extends Absyn implements Serializable { 

  public Variable var;
  public Expr     val = null;    // Kann null sein


  /**
   * Zwei Konstruktoren: einer fuer die reine Deklaration,
   * eine, falls man die Variable deklariert und gleich mit
   * einem Wert fuellt. 
   *
   * Typen sind (noch) nicht vorgesehen, das haengt davon
   * abstract, ob wir eine Checker-Gruppe bekommen.
   */

  public Vardec (Variable x) {
    var = x;
  };
  
  public Vardec (Variable x, Expr e) {
    var = x;
    val = e;
  };

}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Vardec.java,v 1.2 2000-06-04 10:22:04 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 15:11:17  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

