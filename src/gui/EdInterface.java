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
    
    public boolean visible = true;
    public boolean inproject = true;
    /**
     * pos und groesse
     */
    public int x = 0;
    public int y = 0;
    public int height = 350;
    public int width = 400;
    /**
     * identifier des editors
     */
    public String name = "NA";
    
    /**
     * konstruktoren
     */
    public EdInterface() {
	editor = new editor.Editor(this);
    }
    
    public EdInterface(String _name) {
	name = new String(_name);
	editor = new editor.Editor(this);
    }
    
    public void setInProject(Boolean b) {
	inproject = b.booleanValue();
    }
 
    public void setVisible(Boolean b) {
	visible = b.booleanValue();
	if (editor == null)
	    editor = new editor.Editor(this);
	editor.setVisible(visible);
    }
    
    public void destroyEditor() {
	if (editor != null)
	    editor.destroy();
	editor = null;
	try {
	    this.finalize();
	} catch(java.lang.Throwable te) {
	    System.err.println("error finalizing EdInterface");
	}
   
	
	
    }

}
    

