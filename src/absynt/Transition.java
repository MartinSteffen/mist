package absynt;
import java.io.Serializable;



/**
 * Class implementing a transition.
 * A transition is given by it's target and source, and the label.
 *
 * @author Initially provided by Martin Steffen.
 * @version $Id: Transition.java,v 1.2 2000-05-28 12:57:12 unix01 Exp $
 */
public class Transition extends Absyn implements Serializable {
  
  public Astate source;
  public Astate target;
  public Label  lab;


  public Transition (Astate s, Astate t, Label l ) {
    source = s;
    target = t;
    lab    = l;
  };
  
}



//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Transition.java,v 1.2 2000-05-28 12:57:12 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 11:11:32  unix01
//	ok
//	
//---------------------------------------------------------------------
