package gui;

import absynt.*;
import gui.*;


/**
 * daten zum beiderseitigen austausch mit einem Editor
 *
 */
public class EdInterface {
    
    // Interface fuer Editor
    public editor.Editor editor = null;
    
    boolean inproject = true;
    /**
     * pos und groesse
     */
    public int x, y, height, width;
    /**
     * identifier des editors
     */
    public String name;
    
    /**
     * konstruktoren
     */
    public EdInterface() {
	x = y = 0;
	height = width = 500;
	name = "NA";
	editor = new editor.Editor(this);
    }
    
    public EdInterface(String _name) {
	x = y = 0;
	height = width = 500;
	name = new String(_name);
	editor = new editor.Editor(this);
    }

    public void setVisible(Boolean b) {
	if (editor == null)
	    System.err.println("Nullpointer\n");
	editor.setVisible(b.booleanValue());
    }

    public void setInProject(Boolean b) {
	if (editor == null)
	    System.err.println("Nullpointer\n");
	inproject = b.booleanValue();

    }

}
	
