package absynt;
import java.io.Serializable;


/**
 * Un"are Ausdr"ucke
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: U_expr.java,v 1.3 2000-06-29 05:28:29 unix01 Exp $
 */


public class U_expr extends Expr implements Serializable{ 
  public Expr sub_expr;
  public int  op;

  public U_expr(int o, Expr s) {
    op = o;
    sub_expr = s;
  };
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: U_expr.java,v 1.3 2000-06-29 05:28:29 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 12:57:13  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:32  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

