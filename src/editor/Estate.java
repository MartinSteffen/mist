package editor;

import absynt.*;
import editor.einterface.*;

public class Estate extends EditorObject {

    absynt.Expr assert;
    Estate next;
    Estate last;
    absynt.Astate state;
    absynt.AstateList statelist;
    int type;
    static AbsyntInterface ainterface = new AbsyntInterface();
    boolean highlighted;

    public Estate (String name, absynt.Expr inexpr, float x, float y, int etype) {
      type = etype;
      absynt.Position xypos = new absynt.Position();
      xypos.x = x;
      xypos.y = y;
      if (type == 0) state = new absynt.State(name, inexpr, xypos);
      else if (type == 1) state = new absynt.Initstate(name, inexpr, xypos);
      statelist = new absynt.AstateList(state, null);
      highlighted = false;
    }

    float getX() {
      float outx = -1;
      if (state != null) {
      	if (state.pos != null) outx = state.pos.x;
      	  else System.out.println("(Estate.getX) no position in state");
      } else {
        System.out.println("Error !! (Estate.getX) no state in Estate");
      }
      return(outx);
    }

    float getY() {
      float outy = -1;
      if (state != null) {
      	if (state.pos != null) outy = state.pos.y;
      	  else System.out.println("(Estate.getY) no position in state");
      } else {
        System.out.println("Error !! (Estate.getY) no state in Estate");
      }
      return(outy);
    }
    
    void setPosition(float x, float y) {
      if (state != null) {
      	if (state.pos != null) {
      	  state.pos.x = x;
      	  state.pos.y = y;
        } else {
          state.pos = new absynt.Position();
          state.pos.x = x;
      	  state.pos.y = y;
	}
      } else {
        System.out.println("Error !! (Estate.setPosition) no state in Estate");
      }
    } 

    void setExpr (absynt.Expr inexpr) {
      if (this.state != null) {
      	this.state.assert = inexpr;
      } else {
        System.out.println("Error !! (Estate.setExpr) no state in Estate");
      }
    }

    absynt.Expr getExpr() {
      absynt.Expr outexpr = null;
      if (this.state != null) outexpr = this.state.assert;
      else System.out.println("Error !! (Estate.getExpr) no state in Estate");
      return(outexpr);
    }

    void setInitState(String inname, absynt.Expr inexpr) {
      if (type == 0) {
      	System.out.println("(Estate.setInitState) type was 0");
      	absynt.Position xypos = this.state.pos;
      	this.state = null;
      	this.state = new absynt.Initstate(inname, inexpr, xypos);
        this.statelist.head = this.state;
      	type = 1;
      } else {
      	System.out.println("(Estate.setInitState) type was 1");
        setName(inname);
        setExpr(inexpr);
      }
    }

    void setNormalState(String inname, absynt.Expr inexpr) {
      if (type == 0) {
      	System.out.println("(Estate.setNormalState) type was 0");
      	setName(inname);
      	setExpr(inexpr);
      } else {
      	System.out.println("(Estate.setNormalState) type was 1");
      	absynt.Position xypos = this.state.pos;
      	this.state = null;
      	this.state = new absynt.State(inname, inexpr, xypos);
        this.statelist.head = this.state;
      	type = 0;
      }
    }

    absynt.AstateList getList() {
      return(statelist);
    }

    absynt.Astate getAstate() {
      return(state);
    }
    
    void setNext (Estate nextstate) {
      if (nextstate != null) statelist.next = nextstate.getList();
        else statelist.next = null;
    }

    absynt.Astate getState() {
      return(state);
    }

    String getName() {
      String outname;
      if (this.state != null) outname = this.state.name;
      else outname = "Error !! (Estate.getName) no state in Estate";
      return(outname);
    }
    
    void setName(String inname) {
      if (this.state != null) this.state.name = inname;
      else System.out.println("Error !! (Estate.setName) no state in Estate");
    }


    public boolean checkStateName (String inname) {
      boolean wert = false;
      if (getName().compareTo(inname) == 0) wert = true;
      else {
        if (next != null) wert = next.checkStateName(inname); 
      }
      return(wert);
    }

    void clear() {
      next = null;
      last = null;
      if (statelist != null) {
        statelist.next = null;
        statelist.head = null;
//        statelist.dispose();
        statelist = null;
      }
      if (state != null) {
        state.assert = null;
        state.pos = null;
//        state.dispose();
        state = null;
      }
    }

    EditorObject copy() {
      Estate outstate = new Estate(getName(), ainterface.copyExpr(getExpr()), getX(), getY(), type);
      return(outstate);
    }
    
    void cut (Eprocess eprocess) {
      eprocess.removeState(this);
    }

    void paste (Eprocess eprocess) {}

    void move (float x, float y) {
      float x_cor = getX();
      float y_cor = getY();
      x_cor += x;
      if (x_cor > 1.0) x_cor = (float)1.0;
      else if (x_cor < 0) x_cor = (float)0.0;
      y_cor += y;
      if (y_cor > 1.0) y_cor = (float)1.0;
      else if (y_cor < 0) y_cor = (float)0.0;
      setPosition(x_cor, y_cor);   
    }
    
    void print () {
      System.out.println("Estate : "+getName());
    }
    
    boolean isInRectangle(float x1, float y1, float x2, float y2) {
      boolean outvalue = false;
      float x_cor = getX();
      float y_cor = getY();
      if (((x1 < x_cor) && (x2 > x_cor)) && ((y1 < y_cor) && (y2 > y_cor))) outvalue = true;
        else outvalue = false;
      return(outvalue);
    }

    Estate getStateInRange(float x, float y, float range) {
      Estate outstate = null;
      float x_cor = getX();
      float y_cor = getY();
/*      if ((((x-range) < x_cor) && ((x+range) > x_cor)) && (((y-range) < y_cor) && ((y+range) > y_cor))) outstate = this;
        else if (next != null) outstate = next.getStateInRange(x, y, range);
      if (outstate != null) {
      	System.out.println("state in Range");
      	Estate statesucher = null;
      	if (outstate.next != null) statesucher = outstate.next.getStateInRange(x, y, range);
      	if (statesucher != null) outstate = statesucher;
      }
*/
      if (next != null) outstate = next.getStateInRange(x, y, range);
      if (outstate == null) {
      	if ((((x-range) < x_cor) && ((x+range) > x_cor)) && (((y-range) < y_cor) && ((y+range) > y_cor))) {
          System.out.println("Zustand in Range");
          outstate = this;
        }
      }
      return(outstate);
    }
    
    EditorSelection rangeSelect (float x1, float y1, float x2, float y2) {
      EditorSelection outselection = null;
      if (isInRectangle(x1, y1, x2, y2)) {
      	select();
      	outselection = new EditorSelection();
      	outselection.editorobject = this;
      	if (next != null) outselection.next = next.rangeSelect(x1, y1, x2, y2);
      } else {
      	if (next != null) outselection = next.rangeSelect(x1, y1, x2, y2);
      }
      return (outselection);
    }

/**
 * liefert den Estate zurueck, der den uebergebenen absynt.Astate kapselt
 */
    Estate getEstateWithAstate (absynt.Astate instate) {
      Estate outstate = null;
      if (getState() == instate) outstate = this;
      else {
      	if (next != null) outstate = next.getEstateWithAstate(instate);
      }
      return(outstate);
    }

/**
 * schaltet highlighted an und aus
 */
    void setHighlighted(boolean mode) {
      highlighted = mode;
    }
}
