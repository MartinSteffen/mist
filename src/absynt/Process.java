package absynt;
import java.io.Serializable;

/**
 * One process = local variables + transitions
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Process.java,v 1.7 2000-07-03 16:31:05 unix01 Exp $
 */





public class Process extends Absyn implements Serializable {
  public String          name;
  public VardecList      vars;
  public TransitionList  steps;
  public AstateList      states;
  public Initstate       init;

  public Process (String _name, VardecList vl, TransitionList _steps, AstateList _states, Initstate _init) {
    name    = _name;
    vars    = vl;
    steps   = _steps;
    states  = _states;
    init    = _init;

  };

  public Process (VardecList vl, TransitionList _steps, AstateList _states, Initstate _init) {
    name    = "";
    vars    = vl;
    steps   = _steps;
    states  = _states;
    init    = _init;

  };
};









//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Process.java,v 1.7 2000-07-03 16:31:05 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.6  2000/07/03 16:25:05  unix01
//	Program/Process;  Name als String hinzugefuegt
//	Entsprechend auch das Beispiel angepa"st.
//	
//	[Steffen]
//	
//	Revision 1.5  2000/06/26 16:49:38  unix01
//	Einen expliziten ``Initstate'' fuer jeden Prozess hinzugef"ugt (wie
//	vor einer Woche besprochen und im Pflichtenheft festgehalten). [Steffen]
//	Das Beispiel nachgezogen.
//	
//	Revision 1.4  2000/06/19 17:06:45  unix01
//	Die abstrakte Syntax angepa"st (gem"a"s den heutigen Entscheidungen)
//	
//	
//		1) Positionen f"ur Zust"ande eingef"uhrt
//		2) Zustandsliste + Initialer Zustand in Prozessen extra gespeichert.
//	
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
