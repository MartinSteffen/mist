package absynt;
import java.io.Serializable;


/**
 * Variablendeklaration.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Vardec.java,v 1.3 2000-07-10 13:56:27 unix01 Exp $
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


  //--- Methode(n) fuer >> Modelchecker <<

  /**
   * Methode zum Kopieren einer Vardec.
   * Ueberschreibt java.lang.Object.clone()
   * @return Kopie dieses Objektes
   */
  public Object clone () { return new Vardec(var, val); }

  public void print () {
    System.out.println(var.name);
  }

  // ---


}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Vardec.java,v 1.3 2000-07-10 13:56:27 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/06/04 10:22:04  unix01
//	Konstruktoren/Felder hunzigefuegt.
//	
//	Weicht vom Originalvorschlag der abstrakten Syntax im
//	Pfliichtenheft ab!
//	
//	Revision 1.1  2000/05/28 15:11:17  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

