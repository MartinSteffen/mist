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

class EditCutListener implements ActionListener {
    Editor editor;
    
    EditCutListener (Editor editroot) {
      editor = editroot;
    }
    
    public void actionPerformed(ActionEvent event) {
      System.out.println("EditCut (action)");
      editor.editCut();
    }
}