package editor;

import gui.*;

public class Editor extends javax.swing.JFrame {
    EdInterface edIn = null;
    
    
    public Editor(EdInterface ei) {
	setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
	edIn = ei;
	this.setBounds(edIn.x, edIn.y, edIn.width, edIn.height);
	this.setName(edIn.name);
	this.setTitle(edIn.name);
	this.setVisible(ei.visible);
	this.show();
    }
    
    public void destroy() {
	edIn.x = this.getX();
	edIn.y = this.getY();
	edIn.width = this.getWidth();
	edIn.height = this.getHeight();
	this.dispose();
	try {
	    this.finalize();
	} catch(java.lang.Throwable te) {
	    System.err.println("error finalizing Editor");
	}
    }
    
    
}
