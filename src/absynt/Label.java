package absynt;
import java.io.Serializable;


/**
 * Klasse fuer Labels entsprechend der Grammatik.
 *
 * @author Initially provided by Martin Steffen.
 * @version $Id: Label.java,v 1.2 2000-05-28 12:57:11 unix01 Exp $
 */


public class Label extends Absyn implements Serializable { 
  public Expr    guard;
  public Action  act;

  public Label (Expr g,  Action a) {
    guard =  g;
    act   =  a;
  };
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Label.java,v 1.2 2000-05-28 12:57:11 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 11:11:27  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

