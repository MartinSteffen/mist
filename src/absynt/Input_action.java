package absynt;
import java.io.Serializable;


/**
 * Kommunikation: Eingabe
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Input_action.java,v 1.4 2000-06-04 09:40:11 unix01 Exp $
 */


public class Input_action  extends Action implements Serializable { 
  public Channel  chan;
  public Variable  var;

  public Input_action (Channel c, Variable x) {
    chan = c;
    var  = x;
  };
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Input_action.java,v 1.4 2000-06-04 09:40:11 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  2000/06/04 09:36:28  unix01
//	Kontruktor/Felder entsprechend des Pflichtenhefts hinzugefuegt
//	
//	Revision 1.2  2000/05/28 12:57:10  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:26  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

