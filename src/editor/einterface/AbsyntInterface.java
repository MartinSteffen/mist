package editor.einterface;

import absynt.*;

public class AbsyntInterface {

/**
 * erzeugt ein leeres Program und liefert es zurueck
 */
    public absynt.Program makeNewProgram() { return( new absynt.Program(null, null)); }

/**
 * Erzeugt einen leeren Prozess und liefert ihn zurueck
 */
    public absynt.Process makeNewProcess() { return( new absynt.Process(null, null, null, null)); }

/**
 * Erzeugt einen neuen InitZustand anhand der uebergebene Daten und
 * liefert ihn zurueck
 */
    public absynt.Astate  makeNewInitState(String name, Expr expression, Position position) {
      return( new absynt.Initstate(name, expression));
    }

/**
 * Erzeugt einen neuen Zustand anhand der uebergebenen Parameter und
 * liefert diesen zurueck
 */
    public absynt.Astate  makeNewState(String name, Expr expression, Position position) {
      return(new absynt.State(name, expression, position));
    }

/**
 * Erzeugt eine neue Transition mit uebergebenen Zustaenden und Label und liefert
 * sie zurueck
 */
    public absynt.Transition makeNewTransition(Astate s, Astate t, Label l) {
      return(new absynt.Transition(s, t, l));
    }

/**    
 *  Erzeugt eine leere TransitionList und gibt sie zurueck
 */
    public absynt.TransitionList makeNewTransitionList() {
      return(new absynt.TransitionList(null, null));
    }

/**
 * Kopiert einen Channel und liefert diesen zurueck
 */
    public absynt.Channel copyChannel(absynt.Channel inchannel) {
      absynt.Channel outchannel = null;
      if (inchannel != null) {
        outchannel = new absynt.Channel(inchannel.name);
      }
      return(outchannel);
    }

/**
 * Kopiert eine Constval und liefert diese zurueck
 */
    public absynt.Constval copyConstval(absynt.Constval inconstval) {
      absynt.Constval outconstval = null;
      if (inconstval != null) {
      	if (inconstval.val != null) {
      	 if (inconstval.val instanceof Boolean) outconstval = new absynt.Constval(((Boolean)inconstval.val).booleanValue());
         else if (inconstval.val instanceof Integer) outconstval = new absynt.Constval(((Integer)inconstval.val).intValue());
        }
      }
      return(outconstval);
    }

/**
 * Kopiert eine B_expr und liefert sie zurueck
 */
    public absynt.B_expr copyBexpr(absynt.B_expr inexpr) {
      absynt.B_expr outexpr = null;
      if (inexpr != null) {
      	
// >>> Achtung <<< hier stimmte was mit der abstrakten Syntax nicht !!      	
        outexpr = new absynt.B_expr(copyExpr(inexpr.left_expr), 0 ,copyExpr(inexpr.right_expr));
      }
      return(outexpr);
    }

/**
 * Kopiert eine U_expr und liefert sie zurueck
 */
    public absynt.U_expr copyUexpr(absynt.U_expr inexpr) {
      absynt.U_expr outexpr = null;
      if (inexpr != null) {     	
        outexpr = new absynt.U_expr();
      }
      return(outexpr);
    }

/**
 * Kopiert eine Expr und liefert diese zurueck
 */
    public absynt.Expr copyExpr(absynt.Expr inexpr) {
      absynt.Expr outexpr = null;
      if (inexpr != null) {
        if (inexpr instanceof absynt.B_expr) outexpr = copyBexpr((absynt.B_expr)inexpr);
        else if (inexpr instanceof absynt.U_expr) outexpr = copyUexpr((absynt.U_expr)inexpr);
        else if (inexpr instanceof absynt.Constval) outexpr = copyConstval((absynt.Constval)inexpr);
        else if (inexpr instanceof absynt.Variable) outexpr = copyVariable((absynt.Variable)inexpr);
      }
      return(outexpr);
    }

/**
 * Kopiert eine Variable und liefert sie zurueck
 */    
    public absynt.Variable copyVariable(absynt.Variable invariable) {
      absynt.Variable outvariable = null;
      if (invariable != null) {
        outvariable = new absynt.Variable(invariable.name);
      }
      return(outvariable);
    }

/**
 * Kopiert eine Tau_action und liefert diese zurueck
 */
    public absynt.Tau_action copyTauAction(absynt.Tau_action inaction) {
      absynt.Tau_action outaction = null;
      if (inaction != null) {
        outaction = new absynt.Tau_action();
      }
      return(outaction);
    }

/**
 * Kopiert eine Input_action und liefert diese zurueck
 */    
    public absynt.Input_action copyInputAction(absynt.Input_action inaction) {
      absynt.Input_action outaction = null;
      if (inaction != null) {
        outaction = new absynt.Input_action(copyChannel(inaction.chan), copyVariable(inaction.var));
      }
      return(outaction);
    }

/**
 * Kopiert eine Output_action und liefert diese zurueck
 */
    public absynt.Output_action copyOutputAction(absynt.Output_action inaction) {
      absynt.Output_action outaction = null;
      if (inaction != null) {
        outaction = new absynt.Output_action(copyChannel(inaction.chan), copyExpr(inaction.val));
      }
      return(outaction);
    }

/**
 * Kopiert eine Assign_action und liefert diese zurueck
 */
    public absynt.Assign_action copyAssignAction(absynt.Assign_action inaction) {
      absynt.Assign_action outaction = null;
      if (inaction != null) {
        outaction = new absynt.Assign_action(copyVariable(inaction.var), copyExpr(inaction.val));
      }
      return(outaction);
    }

/**
 * Kopiert eine Action und liefert diese zurueck
 */
    public absynt.Action copyAction(absynt.Action inaction) {
      absynt.Action outaction = null;
      if (inaction != null) {
        if (inaction instanceof absynt.Tau_action) outaction = copyTauAction((absynt.Tau_action)inaction);
        else if (inaction instanceof absynt.Input_action) outaction = copyInputAction((absynt.Input_action)inaction);
        else if (inaction instanceof absynt.Output_action) outaction = copyOutputAction((absynt.Output_action)inaction);
        else if (inaction instanceof absynt.Assign_action) outaction = copyAssignAction((absynt.Assign_action)inaction);
      }
      return(outaction);
    }

/**
 * Kopiert ein Label und liefert es zurueck
 */    
    public absynt.Label copyLabel(absynt.Label inlabel) {
      absynt.Label outlabel = null;
      if (inlabel != null) {
        outlabel = new absynt.Label(copyExpr(inlabel.guard), copyAction(inlabel.act));
      }
      return(outlabel);
    }

/**
 * kopiert eine Transition und liefert sie zurueck
 */    
    public absynt.Transition copyTransition(absynt.Transition intransition) {
      absynt.Transition outtransition = null;
      if (intransition != null) {
        outtransition = makeNewTransition(intransition.source, intransition.target, copyLabel(intransition.lab));
      }
      return(outtransition);
    }

    public absynt.Astate getStateByLabel (absynt.Process process, String label) {
      absynt.Astate returnstate = null;
      return(returnstate);
    }
    
}