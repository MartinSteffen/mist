package editor.undo;

public abstract class UndoStepPart {

    UndoStepPart last;
    UndoStepPart next;
    String label;

    abstract String identify ();
};
