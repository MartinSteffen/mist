package absynt;
import java.io.Serializable;


/**
 * Eine Liste von Zustaenden (init oder nicht)
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: AstateList.java,v 1.2 2000-06-26 13:11:43 unix01 Exp $
 */


public class AstateList  
  extends Absyn 
  implements java.util.Enumeration, Serializable { 
  public Astate     head;
  public AstateList next;

  public AstateList (Astate s, AstateList sl) {
    head = s;
    next = sl;
  }

  //---------------------------------------

  public boolean hasMoreElements() {
    return next != null;
  };
  

  public Object nextElement() {
    return  next;
  };

}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: AstateList.java,v 1.2 2000-06-26 13:11:43 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/06/19 17:06:44  unix01
//	Die abstrakte Syntax angepa"st (gem"a"s den heutigen Entscheidungen)
//	
//	
//		1) Positionen f"ur Zust"ande eingef"uhrt
//		2) Zustandsliste + Initialer Zustand in Prozessen extra gespeichert.
//	
//	Revision 1.3  2000/06/04 12:11:55  unix01
//	ok
//	
//	Revision 1.2  2000/05/28 15:00:33  unix01
//	ok
//	
//	Revision 1.1  2000/05/28 13:02:16  unix01
//	Zwischenzustand
//	
//	
//---------------------------------------------------------------------

