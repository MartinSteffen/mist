package editor.undo;

public class UndoStep {
 
  UndoStep last;
  UndoStep next;
  String label;
  UndoStepPart partlist;
 
  UndoStep () {
    last = null;
    next = null;
    label = "";
    partlist = null;
  }

  UndoStep (String name) {
    last = null;
    next = null;
    label = name;
    partlist = null;
  }

  String getName () {
    return (label);
  }

}
