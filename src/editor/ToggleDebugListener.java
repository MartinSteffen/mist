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

class ToggleDebugListener implements ActionListener {
    Editor editor;

    ToggleDebugListener (Editor editroot) {
      editor = editroot;
    }
    public void actionPerformed(ActionEvent event) {
      if (editor.debug) {
        editor.debugText("toggle debug: on -> off");
        editor.debug = false;
        if (editor.debugwindow != null) editor.debugwindow.setVisible(false);
        System.out.println("debug : on -> off");
      } else {
        System.out.println("debug : off -> on");
        editor.debug = true;
        if (editor.debugwindow == null) {
          editor.debugwindow = new DebugWindow();
          System.out.println("debug : window == null");
        } else {
          editor.debugwindow.setVisible(true);
          System.out.println("debug: window != null");
        }
        System.out.println("debug: enabled");
        editor.debugText("toggle debug: off -> on"); 
      }
    }
}