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

public class PopupMenuState extends JPopupMenu {
    Editor editor;
    Estate estate;
    Component invoker;
    int x;
    int y;
    
    public PopupMenuState (Estate instate, int x, int y) {
      super();
      estate = instate;
      invoker = getInvoker();
      this.x = x;
      this.y = y;
      
      JMenuItem renamemenu = new JMenuItem("rename");
      JMenuItem editexprmenu = new JMenuItem("edit expr");
      JMenuItem togglemenu = new JMenuItem("toggle type");
      
      add(renamemenu);
      add(editexprmenu);
      add(togglemenu);
      show(invoker, this.x, this.y);
    }
}