package absynt;
import java.io.Serializable;


/**
 * Bin"are Ausdr"ucke
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: B_expr.java,v 1.3 2000-06-26 13:11:43 unix01 Exp $
 */


public class B_expr   extends Expr implements Serializable{ 
  public Expr   left_expr;
  public Expr right_expr;
  public int  op;
/**
 * Die Operatoren sind as ``konstante'' Felder mit selbsterkl"arenden Namen definiert.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: B_expr.java,v 1.3 2000-06-26 13:11:43 unix01 Exp $
 */
  //---------------------------------------------------
  /**
     Konstructor in ``Infix''-Konvention. Aufruf immer mit Expr.<OP>
   */
  public B_expr (Expr l, int o,  Expr r) {
    left_expr = l;
    right_expr = r;
    op   = o;
  }
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: B_expr.java,v 1.3 2000-06-26 13:11:43 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 12:57:09  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:22  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

