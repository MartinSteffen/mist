package absynt;
import java.io.Serializable;

/**
 * One process = local variables + transitions
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Process.java,v 1.4 2000-06-19 17:06:45 unix01 Exp $
 */





public class Process extends Absyn implements Serializable {
  public VardecList      vars;
  public TransitionList  steps;
  public AstateList      states;

  public Process (VardecList vl, TransitionList _steps, AstateList _states) {
    vars    = vl;
    steps   = _steps;
    states  = _states;

  };
};









//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Process.java,v 1.4 2000-06-19 17:06:45 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  2000/06/04 12:11:55  unix01
//	ok
//	
//	Revision 1.2  2000/05/28 12:57:11  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:28  unix01
//	ok
//	
//---------------------------------------------------------------------
