package absynt;
import java.io.Serializable;


/**
 * Variablendeklaration.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Chandec.java,v 1.2 2000-06-04 06:34:54 unix01 Exp $
 */


public class Chandec extends Absyn implements Serializable { 
  public String chan;    // Name als String

  public Chandec (String s) {chan = s;};
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Chandec.java,v 1.2 2000-06-04 06:34:54 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 15:11:15  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

