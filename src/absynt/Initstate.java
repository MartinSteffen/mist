package absynt;
import java.io.Serializable;


/**
 * Initialer Zustand
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Initstate.java,v 1.5 2000-07-18 22:14:50 unix01 Exp $
 */


public class Initstate  extends  Astate implements Serializable { 
  
  public Initstate (String s, Expr e) {
    name     = s;
    assert   = e;
    pos      = new Position();
  };
  public Initstate (String s, Expr e, Position p) {
    name     = s;
    assert   = e;
    pos      = p;
  };
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Initstate.java,v 1.5 2000-07-18 22:14:50 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  2000/06/19 17:17:38  unix01
//	-  Zus"atzliche "uberladene Konstruktoren eingef"ugt
//	
//	-  Felder f"ur State/Initstate nach  Astate verschoben.
//	
//	Revision 1.3  2000/06/04 09:02:03  unix01
//	Konstruktor + Felder entsprechend dem Pflichtenheft hinzugefuegt
//	
//	Revision 1.2  2000/05/28 12:57:10  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:25  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

