package editor.undo;

public class USP_RemVertex extends UndoStepPart {

    float x_position;
    float y_position;
    String vertex_label;

    USP_RemVertex (float x_pos, float y_pos, String vlabel) {
      label = "RemVertex";
      x_position = x_pos;
      y_position = y_pos;
      vertex_label = vlabel;
    }

    String identify () {
      return (label);
    }

}
