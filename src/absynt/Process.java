
package absyn;
import java.io.Serializable;

/**
 * One process = local variables + transitions
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Process.java,v 1.1 2000-05-28 11:11:28 unix01 Exp $
 */





public class Process extends Absyn implements Serializable {
  public VardecList      vars;
  public TransitionList  body;

  public Process (VardecList vl, TransitionList b) {
    vars    = vl;
    b       = b;
  };
};









//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Process.java,v 1.1 2000-05-28 11:11:28 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//---------------------------------------------------------------------
