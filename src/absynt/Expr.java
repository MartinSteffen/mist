package absynt;
import java.io.Serializable;


/**
 * Abstrakte Klasse f"ur Ausdr"ucke
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Expr.java,v 1.2 2000-05-28 12:57:09 unix01 Exp $
 */


public abstract class Expr extends Absyn implements Serializable{ 

  public final  static int PLUS  = 0;
  public final  static int MINUS = 1;
  public final  static int TIMES = 2;
  public final  static int DIV   = 3;
  public final  static int AND   = 4;
  public final  static int OR    = 5;
  public final  static int NEG   = 6;


  
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Expr.java,v 1.2 2000-05-28 12:57:09 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 11:11:24  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

