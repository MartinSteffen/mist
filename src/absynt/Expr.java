package absynt;
import java.io.Serializable;


/**
 * Abstrakte Klasse f"ur Ausdr"ucke
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Expr.java,v 1.6 2000-06-26 13:11:44 unix01 Exp $
 */






public abstract class Expr extends Absyn implements Serializable{ 
  public final  static int PLUS  = 0;
  public final  static int MINUS = 1;
  public final  static int TIMES = 2;
  public final  static int DIV   = 3;
  public final  static int AND   = 4;
  public final  static int OR    = 5;
  public final  static int NEG   = 6;
  public final  static int EQ    = 7;
  public final  static int LESS  = 8;
  public final  static int GREATER = 9;
  public final  static int LEQ     = 10;
  public final  static int GEQ     = 11;
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Expr.java,v 1.6 2000-06-26 13:11:44 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  2000/06/04 12:11:54  unix01
//	ok
//	
//	Revision 1.4  2000/06/04 07:11:27  unix01
//	TRUE/FALSE als 0-stellige operatoren himzigefuegt
//	
//	Revision 1.3  2000/06/02 07:47:40  unix01
//	unimportant change
//	
//	Revision 1.2  2000/05/28 12:57:09  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:24  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

