package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import editor.undo.*;
import absynt.*;
import editor.einterface.*;
import java.beans.PropertyVetoException;
import javax.swing.plaf.basic.*;
import gui.*;

public class EditorToolbar extends JToolBar {

    Editor editor;

    EditorToolbar (Editor editroot) {
      super(HORIZONTAL);
//      super(VERTICAL);
      editor = editroot;
      setFont(editroot.buttonfont);
      setFloatable(true);

  /* erzeuge Buttons fuer die Toolbar */

      Dimension buttonsize = new Dimension(35, 35);
      JToggleButton b_select = new JToggleButton(new ImageIcon("editor/images/t_arrow.gif"));
      b_select.setPreferredSize(buttonsize);
      b_select.setSelected(true);
      JToggleButton b_state = new JToggleButton(new ImageIcon("editor/images/t_state.gif"));
      b_state.setPreferredSize(buttonsize);
      JToggleButton b_transition = new JToggleButton(new ImageIcon("editor/images/t_trans.gif"));
      b_transition.setPreferredSize(buttonsize);
      JToggleButton b_zoom = new JToggleButton(new ImageIcon("editor/images/t_zoom.gif"));
      b_zoom.setPreferredSize(buttonsize);

  /* setze ActionCommand fuer jeden Button */

      b_select.setActionCommand("select");
      b_state.setActionCommand("state");
      b_transition.setActionCommand("trans");
      b_zoom.setActionCommand("zoom");

  /* setze Listener fuer jeden Button */

      RadioToolListener toollistener = new RadioToolListener();
      b_select.addActionListener(toollistener);
      b_state.addActionListener(toollistener);
      b_transition.addActionListener(toollistener);
      b_zoom.addActionListener(toollistener);

  /* Verknuepfung der Buttons zu einer Gruppe */

      ButtonGroup bgroup = new ButtonGroup();
      bgroup.add(b_select);
      bgroup.add(b_state);
      bgroup.add(b_transition);
      bgroup.add(b_zoom);

  /* Einfuegen der Buttons in die Toolbar */

      add(b_select);
      add(b_state);
      add(b_transition);
      add(b_zoom);
    }

  /** Listener fuer die Buttons der EditorToolbar */

    class RadioToolListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        editor.toolcommand = command;
        if (editor.debug) editor.debugText("Tool-Selection : "+command);
      }
    }

}