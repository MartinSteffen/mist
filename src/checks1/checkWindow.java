
/**
 * Title:        *M*I*S*T*<p>
 * Description:  In dieser Klasse wird ein Fenster zur Verfuegung gestellt,
 * in welchem die Statusmeldungen der einzelnen Checks ausgegeben werden
 * koennen<p>
 * Copyright:    Copyright (c) André Nitsche<p>
 * Company:      <p>
 * @author André Nitsche
 * @version 1.0
 */
package Checks1;
import java.awt.*;
import java.awt.event.*;

public class CheckWindow extends Frame{
  public TextArea text;

  public CheckWindow(String name) {
  super(name);
  setSize(250,250);
  text=new TextArea(10,30);
  text.setEditable(false);    //Text kann nicht veraendert werden
  Button quit=new Button("Ende");
  quit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          hide();
          dispose();
          System.exit(0);
              }
        });
  Button print=new Button("Drucken");
  this.setLayout(new FlowLayout());
  add(text);
  add(quit);add(print);
  }


}