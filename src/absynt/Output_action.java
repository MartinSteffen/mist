package absynt;
import java.io.Serializable;


/**
 * Kommunikation: Ausgabe
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Output_action.java,v 1.3 2000-06-04 10:10:13 unix01 Exp $
 */


public class Output_action extends Action implements Serializable { 
  public Channel chan;
  public Expr     val;

  public Output_action (Channel c, Expr e) {
  chan = c;
  val  = e;
  }
}





//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Output_action.java,v 1.3 2000-06-04 10:10:13 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 12:57:11  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:28  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

