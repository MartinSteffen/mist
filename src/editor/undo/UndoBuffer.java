package editor.undo;

public class UndoBuffer {

    int depth;
    int count;
    int position;
    UndoStep undosteplist;
    UndoStep undopointer;

    UndoBuffer () {
      depth = -1;
      count = 0;
      position = 0;
      undosteplist = null;
      undopointer = null;
    }

    UndoBuffer (int undodepth) {
      depth = undodepth;
      count = 0;
      position = 0;
      undosteplist = null;
      undopointer = null;
    }

    void correctBufferLength () {
      if (depth >= 0)
      while (count > depth) {
        undosteplist.next.last = null;
        undosteplist = undosteplist.next;
        count--;
        position--;
      }
    }

    void addUndoStep (UndoStep step) {
      step.next = null;
      if (undosteplist == null) {
        undosteplist = step;
        undopointer = step;
        step.last = null;
        count = 1;
        position = 1;
      } else {
        step.last = undopointer;
        undopointer.next.last = null;
        undopointer.next = step;
        undopointer = undopointer.next;
        position++;
        count = position;
        correctBufferLength();
      }
    }

    String getUndoName () {
      String stepname = "";
      if (undopointer != null) stepname = undopointer.getName();
      return (stepname);
    }

    String getRedoName () {
      String stepname = "";
      if (undopointer != null)
        if (undopointer.next != null) stepname = undopointer.next.getName();
      return (stepname);
    }
}
