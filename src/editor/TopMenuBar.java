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

public class TopMenuBar extends JMenuBar {

    Editor editor;

    TopMenuBar (Editor editroot) {
      super();
      editor = editroot;
      setFont(editor.menufont);

  /* Pull-Down Menus anlegen */

      JMenu menu_file = new JMenu("File");
      menu_file.setFont(editor.menufont);
 
      JMenu menu_edit = new JMenu("Edit");
      menu_edit.setFont(editor.menufont);

      JMenu menu_options = new JMenu("Options");
      menu_options.setFont(editor.menufont);

      JMenu menu_program = new JMenu("Program");
      menu_program.setFont(editor.menufont);

      JMenu menu_process = new JMenu("Process");
      menu_process.setFont(editor.menufont);
      
      JMenu menu_position = new JMenu("Position");
      menu_position.setFont(editor.menufont);

  /* Eintraege fuer die Pull-Down Menus */

// File-Menu :
      JMenuItem f_new = new JMenuItem("New");
      f_new.addActionListener(new NewProgramListener(editor));
      f_new.setFont(editor.menufont);

      JMenuItem f_open = new JMenuItem("Open");
//      f_open.addActionListener(new ExitProgram(editor));
      f_open.setFont(editor.menufont);

      JMenuItem f_save = new JMenuItem("Save");
//      f_save.addActionListener(new ExitProgram(editor));
      f_save.setFont(editor.menufont);

      JMenuItem f_close = new JMenuItem("Close");
//      f_close.addActionListener(new ExitProgram(editor));
      f_close.setFont(editor.menufont);

      JMenuItem f_exit = new JMenuItem("Exit");
      f_exit.addActionListener(new ExitProgramListener(editor));
      f_exit.setFont(editor.menufont);

// Edit-Menu
      JMenuItem e_copy = new JMenuItem("copy");
      e_copy.addActionListener(new EditCopyListener(editor));
      e_copy.setFont(editor.menufont);

      JMenuItem e_paste = new JMenuItem("paste");
      e_paste.addActionListener(new EditPasteListener(editor));
      e_paste.setFont(editor.menufont);
      
      JMenuItem e_cut = new JMenuItem("cut");
      e_cut.addActionListener(new EditCutListener(editor));
      e_cut.setFont(editor.menufont);
      
// Options-Menu
      JMenuItem o_toggle_debug = new JMenuItem("debug");
      o_toggle_debug.addActionListener(new ToggleDebugListener(editor));
      o_toggle_debug.setFont(editor.menufont); 

// Program-Menu
      JMenuItem p_setchannels = new JMenuItem("edit Channels");
//      f_exit.addActionListener(new ExitProgramListener(editor));
      p_setchannels.setFont(editor.menufont);
      
      JMenuItem p_prettyprint = new JMenuItem("PrettyPrint");
      p_prettyprint.addActionListener(new PrettyPrintListener(editor));
      p_prettyprint.setFont(editor.menufont);

// Process-Menu      
      JMenuItem p_setvariables = new JMenuItem("edit Variables");
//      f_exit.addActionListener(new ExitProgramListener(editor));
      p_setvariables.setFont(editor.menufont);

      JMenuItem p_newprocess = new JMenuItem("new Process");
      p_newprocess.addActionListener(new NewProcessListener(editor));
      p_newprocess.setFont(editor.menufont);

// Position-Menu
      JMenuItem p_pos1grav = new JMenuItem("pos1 Grav");
      p_pos1grav.addActionListener(new Position1Listener(editor, "Grav"));
      p_pos1grav.setFont(editor.menufont);
      
      JMenuItem p_pos2grav = new JMenuItem("pos2 Grav");
      p_pos2grav.addActionListener(new Position2Listener(editor, "Grav"));
      p_pos2grav.setFont(editor.menufont);
      
      JMenuItem p_pos2fr = new JMenuItem("pos2 FR");
      p_pos2fr.addActionListener(new Position2Listener(editor, "FR"));
      p_pos2fr.setFont(editor.menufont);
      
      JMenuItem p_pos2zufall = new JMenuItem("pos2 Zufall");
      p_pos2zufall.addActionListener(new Position2Listener(editor, "Zufall"));
      p_pos2zufall.setFont(editor.menufont);

  /* Eintraege in die Pull-Down Menus einfuegen */

      menu_file.add(f_new);
      menu_file.add(f_open);
      menu_file.add(f_save);
      menu_file.add(f_close);
      menu_file.add(f_exit);
      
      menu_edit.add(e_copy);
      menu_edit.add(e_paste);
      menu_edit.add(e_cut);

      menu_options.add(o_toggle_debug);

      menu_program.add(p_setchannels);
      menu_program.add(p_prettyprint);
      menu_program.addSeparator();
      
      menu_process.add(p_setvariables);
      menu_process.add(p_newprocess);
      menu_process.addSeparator();

      menu_position.add(p_pos1grav);
      menu_position.addSeparator();
      menu_position.add(p_pos2grav);
      menu_position.add(p_pos2fr);
      menu_position.add(p_pos2zufall);

  /* Menus zu der Menubar hinzufuegen */

      add(menu_file);
      add(menu_edit);
      add(menu_options);
      add(menu_program);
      add(menu_process);
      add(menu_position);
    }
    
    class Position1Listener implements ActionListener {
      Editor editor;
      String method;
      
      public Position1Listener(Editor editroot, String inmethod) {
        editor = editroot;
        method = inmethod;
      }
      public void actionPerformed(ActionEvent event) {
        editor.calcPosition1(method);
      }
    }
    
    class Position2Listener implements ActionListener {
      Editor editor;
      String method;
      
      public Position2Listener(Editor editroot, String inmethod) {
        editor = editroot;
        method = inmethod;
      }
      public void actionPerformed(ActionEvent event) {
        editor.calcPosition2(method);
      }
    }
}