package absynt;
import java.io.Serializable;





/**
 * Zustand (au"ser initialem Zustand)
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: State.java,v 1.5 2000-06-19 17:17:38 unix01 Exp $
 */


public class State extends  Astate implements Serializable { 
  
  public State (String s, Expr e) {
    name     = s;
    assert   = e;
    pos = null;
  };  
  public State (String s, Expr e, Position p) {
    name     = s;
    assert   = e;
    pos      = p;
  };  

}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: State.java,v 1.5 2000-06-19 17:17:38 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  2000/06/07 14:11:51  unix01
//	Nur zur Demo
//	
//	Revision 1.3  2000/06/04 09:04:59  unix01
//	Konstruktor/Felder entsprechend dem Pflichtenheft hinzugefuegt
//	
//	Revision 1.2  2000/05/28 12:57:12  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:31  unix01
//	ok
//	
//	
//---------------------------------------------------------------------


