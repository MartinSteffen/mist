package absynt;
import java.io.Serializable;


/**
 * Variablendeklaration.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Vardec.java,v 1.6 2000-07-10 17:41:09 unix01 Exp $
 */


public class Vardec extends Absyn implements Serializable { 

  public Variable   var;
  public Expr       val = null;    // Wir werden verlangen, da"s es nicht null sein darf
  public M_AtomType mype;          // Wir werden verlangen, da"s es immer gesetzt ist


  /**
   * Momentan 3 Konstruktoren. Am Ende ist folgender
   * nur noch ein Konstruktor vorgesehen (nach Diskussion mit)
   * der Modelchecker/Simulatorgruppe:
   *
   *   public Vardec (Variable x, Constval e, M_AtomType _t) {
   *
   *   Aus Gruenden der tempor"aren Kompilierbarkeit werden die Konstruktoren
   *   fuer ein paar Tage noch uberladen beibehalten,
   */

  public Vardec (Variable x, Expr e, M_AtomType _t) {
    var = x;
    val = e;
    type = _t;
  };


  
  // diese Methode soll am Ende nicht mehr Unterst"utzt werden
  public Vardec (Variable x, Expr e) {
    var = x;
    val = e;
  };


  // diese Methode soll am Ende nicht mehr Unterst"utzt werden
  public Vardec (Variable x) {
    var = x;
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
//	$Id: Vardec.java,v 1.6 2000-07-10 17:41:09 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  2000/07/10 14:55:33  unix01
//	Neue Klassen fuer die Abstrakte Syntax hinzugefuegt, fuer die
//	typisierung:
//		     Type
//		    /   \
//	      AtomType  Chan
//	      /    \
//	     Bool   Int
//	
//	Revision 1.4  2000/07/10 14:41:39  unix01
//	ok
//	
//	Revision 1.3  2000/07/10 13:56:27  unix01
//	Printmethode eingefuegt
//	
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

