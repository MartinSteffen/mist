package editor;

public class EditorSelection {
    EditorSelection last;
    EditorSelection next;
    EditorObject editorobject;
    
    public EditorSelection () {
      last = null;
      next = null;
      editorobject = null;
    }
    
    EditorSelection clear () {
      EditorSelection outselection = null;
      if (editorobject != null) editorobject.deselect();
        else System.out.println("Achtung : leeres Selectionfeld");
      editorobject = null;
      last = null;
      if (next != null) next = next.clear();
      return(outselection);
    }

    EditorSelection copy () {
      return(copy(null));
    }
    
    EditorSelection copy (EditorSelection inselection) {
      EditorSelection outselection = new EditorSelection();
      outselection.last = inselection;
// ist eine Object in der Liste, dann kopiere das Object
      if (editorobject != null) outselection.editorobject = editorobject.copy();
// wenn ein neues Object erstellt wurde markiere es als "selected"      
      if (outselection.editorobject != null) {
      	outselection.editorobject.select();
// wenn das Kopeirte Object eine Transition ist, dann muessen die Referenzen
// der Zustaende fuer diese Transition angepasst werden
      	if (outselection.editorobject instanceof Etransition) {
      	  Estate sourcestate = null;
      	  Estate targetstate = null;
      	  String sname = ((Etransition)outselection.editorobject).getSource().getName();
      	  String tname = ((Etransition)outselection.editorobject).getTarget().getName();
      	  System.out.println("Transition : s = "+sname+" t = "+tname);
          sourcestate = outselection.getSelectedStateByName(sname);
          targetstate = outselection.getSelectedStateByName(tname);
          if (sourcestate != null) ((Etransition)outselection.editorobject).setSource(sourcestate);
            else System.out.println(">> Error : sourcestate == null !!!!!");
          if (targetstate != null) ((Etransition)outselection.editorobject).setTarget(targetstate);
            else System.out.println(">> Error : targetstate == null !!!!!");
      	}
      }
// bearbeite den Rest der Selection      
      if (next != null) outselection.next = next.copy(outselection);
      return(outselection);
    }
    
    void cut (Eprocess eprocess) {
      if (editorobject != null) editorobject.cut(eprocess);
      if (next != null) next.cut(eprocess);	
    }
    
    void paste (Eprocess eprocess) {
      if (editorobject != null) eprocess.paste(editorobject);
      if (next != null) next.paste(eprocess);
    }
    
    void move (float x, float y) {
      if (editorobject != null) {
      	if (editorobject instanceof Estate) {
          ((Estate)editorobject).move(x,y);
      	}
      }
      if (next != null) next.move(x, y);
    }
    
    void print () {
      if (editorobject != null) editorobject.print();
        else System.out.println("leeres Selectionfeld");
      if (next != null) next.print();
    }

    Estate getSelectedStateByName (String inname) {
      Estate outstate = null;
      if (editorobject != null) {
        if (editorobject instanceof Estate) {
          String sname = ((Estate)editorobject).getName();
          if (sname.compareTo(inname) == 0) {
            System.out.println("compare: inname="+inname+" sname="+sname+" (success)");
            outstate = (Estate)editorobject;
          } else {
            System.out.println("compare: inname="+inname+" sname="+sname+" (fail)");
            if (last != null) {
              System.out.println("checking next entry");
              outstate = last.getSelectedStateByName(inname);
            } else {
              System.out.println("no further entry");
            }
          }
        } else {
          if (last != null) outstate = last.getSelectedStateByName(inname);
        }
      }
      return(outstate);
    }
}