package absynt;
import java.io.Serializable;


/**
 * Zustand (au"ser initialem Zustand)
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: State.java,v 1.3 2000-06-04 09:04:59 unix01 Exp $
 */


public class State extends  Astate implements Serializable { 
  public String name;
  public Expr   assert;
  
  public State (String s, Expr e) {
    name     = s;
    assert   = e;
  };  

}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: State.java,v 1.3 2000-06-04 09:04:59 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 12:57:12  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:31  unix01
//	ok
//	
//	
//---------------------------------------------------------------------


