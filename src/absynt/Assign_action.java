package absynt;
import java.io.Serializable;


/**
 * Aktion: Wertzuweisung
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Assign_action.java,v 1.3 2000-06-04 09:18:26 unix01 Exp $
 */


public class Assign_action  extends Action implements Serializable { 
  public Variable var;
  public Expr     val;


  public Assign_action (Variable x, Expr e) {
    var = x;
    val = e;
  };
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Assign_action.java,v 1.3 2000-06-04 09:18:26 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 12:57:08  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:21  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

