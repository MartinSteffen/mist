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

class VariablenEditListener implements ActionListener {
    Editor editor;
    
    VariablenEditListener (Editor editroot) {
      editor = editroot;
    }
    
    public void actionPerformed(ActionEvent event) {
      System.out.println("VariablenEdit (action)");
      if (editor.variableneditor == null) {
      	if (editor.activeprocess != null) editor.variableneditor = new VariablenEditor(editor, editor.activeprocess.getProcess());
      } else {
      	editor.variableneditor.setVisible(true);
        editor.variableneditor.show();
        editor.variableneditor.refresh();	
      }
    }
}