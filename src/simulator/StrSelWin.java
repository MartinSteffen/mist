package simulator;


/** 
 *
 * @author  broder@icepool.de
 * @version 007-0815
 */
public class StrSelWin extends javax.swing.JFrame {
    
    /** Creates new form StrSelWin */
    public StrSelWin(String _title, String _msg, String[] _choice, boolean _hasAuto) {
	super(_title);
	
	jLabel1 = new javax.swing.JLabel ();
	jButton1 = new javax.swing.JButton (); 
	bGroup = new javax.swing.ButtonGroup();
	
	jLabel1.setText (_msg);
	getContentPane ().add (jLabel1, java.awt.BorderLayout.NORTH);
	
	jButton1.setText ("OK");
	jButton1.addActionListener (new java.awt.event.ActionListener () {
		public void actionPerformed (java.awt.event.ActionEvent evt) {
		    okButtonActionPerformed (evt);
		}
	    });
	getContentPane ().add (jButton1, java.awt.BorderLayout.SOUTH);
	
	for(int i=0; i<_choice.length; i++) {
	    tempRButton = new javax.swing.JRadioButton(_choice[i]);
	    bGroup.add(tempRButton);
	    getContentPane().add(tempRButton, java.awt.BorderLayout.CENTER);
	}
	
	if(_hasAuto==true) {
	    jButton2 = new javax.swing.JButton();
	    jButton2.setText("Automatic");
	    jButton2.addActionListener (new java.awt.event.ActionListener () {
		    public void actionPerformed (java.awt.event.ActionEvent evt) {
			autoButtonActionPerformed (evt);
		    }
		});
	}
	choice = -2;
	hasSel = false;
	
	pack ();
    }
    
    private  void autoButtonActionPerformed (java.awt.event.ActionEvent evt) {
	
	choice = -1;
	hasSel = true;
    }
    
    
    
    private  void okButtonActionPerformed (java.awt.event.ActionEvent evt) {
	java.util.Enumeration tempEnum = bGroup.getElements();
	for(int i=0; tempEnum.hasMoreElements(); i++) 
	    if(((javax.swing.JRadioButton)tempEnum.nextElement()).isSelected()) choice=i;
	if(choice !=-2) hasSel=true;
    }
    
    public int getSel() {
	while(!hasSel);
	return choice;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JRadioButton tempRButton;
    private javax.swing.ButtonGroup bGroup;
    // End of variables declaration//GEN-END:variables
    
    private int choice;
    private boolean hasSel;
}

