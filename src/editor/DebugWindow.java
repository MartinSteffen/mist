package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DebugWindow extends JFrame {

    protected JTextArea textarea;
    protected Container cpane;
    Font debugfont;

    DebugWindow () {
      super("DebugWindow");
      setSize(300,300);
      debugfont = new Font("Helvetica", Font.PLAIN, 11);
      setFont(debugfont);
      cpane = getContentPane();
      cpane.setFont(debugfont);
      textarea = new JTextArea();
      textarea.setFont(debugfont);
      JScrollPane srollpane = new JScrollPane(textarea);
      cpane.add(srollpane, BorderLayout.CENTER);
//      cpane.add(textarea, BorderLayout.CENTER);
      setVisible(true);
      show();
    }

    void appendText (String text) {
      textarea.append(text + "\n");
    }

}
