/*
 * SimUI.java
 *
 * Created on June 20, 2000, 11:40 PM
 */
 
package gui;

import simulator.*;

/** 
 *
 * @author  broder@icepool.de, hkraas@web.de
 * @version 0.1
 */
public class SimUI extends javax.swing.JFrame {

  /** Creates new form SimUI */
  public SimUI(simulator.Simulator _sim, absynt.Program _prog) {
      mySim = _sim;
      myProgram = _prog;
      autorunState = 0;
    initComponents ();
    pack ();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () {//GEN-BEGIN:initComponents
    startButton = new javax.swing.JButton ();
    stepButton = new javax.swing.JButton ();
    autorunTButton = new javax.swing.JToggleButton ();
    debuglvlLabel = new javax.swing.JLabel ();
    debuglvlSlider = new javax.swing.JSlider ();
    debugTextArea = new javax.swing.JTextArea ();
    getContentPane ().setLayout (new java.awt.FlowLayout ());

    setResizable (false);
    setName ("Simulator");
    addWindowListener (new java.awt.event.WindowAdapter () {
      public void windowClosing (java.awt.event.WindowEvent evt) {
        exitForm (evt);
      }
    }
    );

    startButton.setText ("Start");
    startButton.addActionListener (new java.awt.event.ActionListener () {
      public void actionPerformed (java.awt.event.ActionEvent evt) {
        startButtonActionPerformed (evt);
      }
    }
    );


    getContentPane ().add (startButton);

    stepButton.setText ("Step");
    stepButton.addActionListener (new java.awt.event.ActionListener () {
      public void actionPerformed (java.awt.event.ActionEvent evt) {
        stepButtonActionPerformed (evt);
      }
    }
    );


    getContentPane ().add (stepButton);

    autorunTButton.setText ("Autorun");
    autorunTButton.addActionListener (new java.awt.event.ActionListener () {
      public void actionPerformed (java.awt.event.ActionEvent evt) {
        autorunTButtonActionPerformed (evt);
      }
    }
    );


    getContentPane ().add (autorunTButton);

    debuglvlLabel.setText ("DebugLevel:");


    getContentPane ().add (debuglvlLabel);

    debuglvlSlider.setMaximum (4);
    debuglvlSlider.setSnapToTicks (true);
    debuglvlSlider.setMajorTickSpacing (1);
    debuglvlSlider.setPaintTicks (true);
    debuglvlSlider.setPaintLabels (true);
    debuglvlSlider.setName ("DebugLevel");
    debuglvlSlider.addChangeListener(new javax.swing.event.ChangeListener() {
	    public void stateChanged(javax.swing.event.ChangeEvent e) {setDebugLvl();};});
    // so ?????

    getContentPane ().add (debuglvlSlider);

    debugTextArea.setPreferredSize (new java.awt.Dimension(600, 300));
    //    debugTextArea.setMaximumSize (new java.awt.Dimension(600, 300));
    debugTextArea.setMinimumSize (new java.awt.Dimension(600, 300));
    debugTextArea.setEditable(false);
    debugTextArea.setText("Hier erscheinen die DebugMessages............. spaeter mal ... hoffentlich denn endlich........ tja.");

    getContentPane ().add (new javax.swing.JScrollPane(debugTextArea));

    setTitle("Simulator");

  }//GEN-END:initComponents

  private void autorunTButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autorunTButtonActionPerformed
// Add your handling code here:
      //sowas in der art wie:
      if(autorunState==0) {
	  autorunState=1;
	  autorun();
      }
      else autorunState=0;

      //jajaja.... hier liegt der hund begraben..... die performedroutine kann nicht rekursiv
      //funktionieren..... wie soll das gehen ?? multithreaded is wohl zu hart ....... SHIT!
      //idee: evtl sich selbst mehrfach clicken mit doClick() ????
     
  }//GEN-LAST:event_autorunTButtonActionPerformed

  private void stepButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
// Add your handling code here:
      mySim.step(); //returns String[] . diesen in debugTextArea einfuegen!!
  }//GEN-LAST:event_stepButtonActionPerformed

  private void startButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
// Add your handling code here
      mySim.start(myProgram); //returns String[] . diesen in debugTextArea einfuegen!!
  }//GEN-LAST:event_startButtonActionPerformed

  /** Exit the Application */
  private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
      this.hide();
  }//GEN-LAST:event_exitForm


    private void setDebugLvl() {

    }



    public java.awt.Dimension getPreferredSize() {
	return new java.awt.Dimension(650,400);
    }
    private void autorun() {
	/*
	System.out.println("autorun("+autorunState+") called.\n");
	while(autorunState==1
	      // && mySim.isProgamRunning() 
	      ) {
	    mySim.step();
	    System.out.println("sim:STEP called.\n");
	}
	*/
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton startButton;
  private javax.swing.JButton stepButton;
  private javax.swing.JToggleButton autorunTButton;
  private javax.swing.JLabel debuglvlLabel;
  private javax.swing.JSlider debuglvlSlider;
  private javax.swing.JTextArea debugTextArea;
  // End of variables declaration//GEN-END:variables

    protected simulator.Simulator mySim;
    protected absynt.Program myProgram;
    protected int autorunState;
}
