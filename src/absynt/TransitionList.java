package absynt;
import java.io.Serializable;


/**
 * Eine List von Transitionen
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: TransitionList.java,v 1.2 2000-05-28 15:00:33 unix01 Exp $
 */


public class TransitionList  
  extends Absyn 
  implements java.util.Enumeration, Serializable { 
  Transition     head;
  TransitionList next;

  public TransitionList (Transition t, TransitionList tl) {
    head = t;
    next = tl;
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
//	$Id: TransitionList.java,v 1.2 2000-05-28 15:00:33 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 13:02:16  unix01
//	Zwischenzustand
//	
//	
//---------------------------------------------------------------------

