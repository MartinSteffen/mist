package absynt;
import java.io.Serializable;


/**
 * Abstrakte Klasse fuer Mist Typen 
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: M_Chan.java,v 1.2 2000-07-12 11:49:52 unix01 Exp $
 */


public class M_Chan extends M_Type implements Serializable { 
  public M_AtomType carried;
  
  public M_Chan (M_AtomType _carried) {
    carried = _carried;
  };
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: M_Chan.java,v 1.2 2000-07-12 11:49:52 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/07/10 14:41:38  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

