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

public class TopMenuToolbar extends JToolBar {

    Editor editor;

    TopMenuToolbar (Editor editroot) {
      super(HORIZONTAL);
      editor = editroot;

  /* Buttons fuer die Toolbar erzeugen */

      Dimension buttonsize = new Dimension(25, 25);
      JButton b_new = new JButton(new ImageIcon("editor/images/filenew.gif"));
      b_new.setPreferredSize(buttonsize);
      JButton b_open = new JButton(new ImageIcon("editor/images/fileopen.gif"));
      b_open.setPreferredSize(buttonsize);
      JButton b_save = new JButton(new ImageIcon("editor/images/filesave.gif"));
      b_save.setPreferredSize(buttonsize);
      JButton b_clear = new JButton("clear");
      b_clear.setFont(editor.buttonfont); 

      JButton b_edit_copy = new JButton(new ImageIcon("editor/images/editcopy.gif"));
      b_edit_copy.setPreferredSize(buttonsize);
      JButton b_edit_cut = new JButton(new ImageIcon("editor/images/editcut.gif"));
      b_edit_cut.setPreferredSize(buttonsize);
      JButton b_edit_paste = new JButton(new ImageIcon("editor/images/editpaste.gif"));
      b_edit_paste.setFont(editor.buttonfont);

      JButton b_undo = new JButton(new ImageIcon("editor/images/undo.gif"));
      b_undo.setPreferredSize(buttonsize);
      JButton b_redo = new JButton(new ImageIcon("editor/images/redo.gif"));
      b_redo.setPreferredSize(buttonsize);

      JButton b_addProcess = new JButton(new ImageIcon("editor/images/filenew.gif"));
      b_addProcess.setPreferredSize(buttonsize);

      JButton b_grid = new JButton(new ImageIcon("editor/images/grid.gif"));
      b_grid.setFont(editor.buttonfont);

  /* ActionCommand fuer jeden Button festlegen */

      b_new.setActionCommand("new");
      b_open.setActionCommand("open");
      b_save.setActionCommand("save");

      b_clear.setActionCommand("clear");

      b_edit_copy.setActionCommand("copy");
      b_edit_cut.setActionCommand("cut");
      b_edit_paste.setActionCommand("paste");

      b_undo.setActionCommand("undo");
      b_redo.setActionCommand("redo");
      b_addProcess.setActionCommand("addProcess");
      b_grid.setActionCommand("grid");

  /* ActionListener fuer die Buttons festlegen */

      TopButtonListener toplistener = new TopButtonListener();
      b_new.addActionListener(new NewProgramListener(editor));
      b_open.addActionListener(toplistener);
      b_save.addActionListener(toplistener);

      b_clear.addActionListener(toplistener);

      b_edit_copy.addActionListener(new EditCopyListener(editor));
      b_edit_cut.addActionListener(new EditCutListener(editor));
      b_edit_paste.addActionListener(new EditPasteListener(editor));

      b_undo.addActionListener(toplistener);
      b_redo.addActionListener(toplistener);
      b_addProcess.addActionListener(new NewProcessListener(editor));
      b_grid.addActionListener(toplistener);

  /* Buttons in die Toolbar einfuegen */

      if (editor.gui == null) {
        add(b_new);
        add(b_open);
        add(b_save);
      }
      addSeparator();
//      add(b_clear);
      add(b_edit_copy);
      add(b_edit_paste);
      add(b_edit_cut);
      addSeparator();
      add(b_undo);
      add(b_redo);
      add(b_addProcess);
      add(b_grid);
    }

    class TopButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        editor.execute(event.getActionCommand());
      }
    }

}