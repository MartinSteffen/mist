package editor;

public abstract class EditorObject {
  boolean selected;
  void select () { selected = true; }
  void deselect () { selected = false; }
  boolean isSelected() { return(selected); }
  
  abstract EditorObject copy ();
  abstract void cut (Eprocess eprocess);
  abstract void paste (Eprocess eprocess);
  abstract void move (float x, float y);
  abstract void print ();

}