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

class ChannelEditListener implements ActionListener {
    Editor editor;
    
    ChannelEditListener (Editor editroot) {
      editor = editroot;
    }
    
    public void actionPerformed(ActionEvent event) {
      System.out.println("ChannleEdit (action)");
      if (editor.channeleditor == null) {
        editor.channeleditor = new ChannelEditor(editor, editor.activeprogram.getProgram());
      } else {
      	editor.channeleditor.setVisible(true);
        editor.channeleditor.show();
        editor.channeleditor.refresh();	
      }
    }
}