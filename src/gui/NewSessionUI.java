package gui;

import javax.swing.*;
import java.awt.event.*;

public class NewSessionUI extends JDialog implements ActionListener {
    
    GUI g;
    
    public NewSessionUI(GUI owner, String title, boolean modal) {
	super((JFrame) owner, title, modal);
	g = owner;
	initComponents();
	pack();
	setResizable(false);
	show();
	toFront();
    }
    
    private void initComponents() {
	getContentPane ().setLayout(new java.awt.FlowLayout());
	
	sessionname = new JTextField("Name der Session");
	sessionname.setColumns(20);
	getContentPane ().add(sessionname);
	
	ok_button = new JButton("ok");
	ok_button.addActionListener(this);
	getContentPane ().add(ok_button);
	
	cancel_button = new JButton("cancel");
	cancel_button.addActionListener(this);
	getContentPane ().add(cancel_button);
	
    }
    
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("ok")) {
	    g.actualsession = new GUI.Session(sessionname.getText());
	    hide();
	    dispose();
	} else if (e.getActionCommand().equals("cancel")) {
	    hide();
	    dispose();
	}

    }
    
    public java.awt.Dimension getPreferredSize() {
	return new java.awt.Dimension(280,130);
    }

    private JButton ok_button;
    private JButton cancel_button;
    private JTextField sessionname;
}
