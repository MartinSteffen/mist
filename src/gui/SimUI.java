/*
 * SimUI.java
 *
 * Created on June 20, 2000, 11:40 PM
 */
 
package gui;




/** 
 *
 * @author  broder@icepool.de, hkraas@web.de
 * @version 0.1
 */
public class SimUI extends javax.swing.JFrame {

  /** Creates new form SimUI */
  public SimUI(simulator.Simulator _sim) {
      mySim = _sim;

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

  }//GEN-END:initComponents

  private void autorunTButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autorunTButtonActionPerformed
// Add your handling code here:
  }//GEN-LAST:event_autorunTButtonActionPerformed

  private void stepButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
// Add your handling code here:
  }//GEN-LAST:event_stepButtonActionPerformed

  private void startButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
// Add your handling code here:
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



  /**
  * @param args the command line arguments
  */
    /*  public static void main (String args[]) {
    new SimUI ().show ();
  } 
    */

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton startButton;
  private javax.swing.JButton stepButton;
  private javax.swing.JToggleButton autorunTButton;
  private javax.swing.JLabel debuglvlLabel;
  private javax.swing.JSlider debuglvlSlider;
  private javax.swing.JTextArea debugTextArea;
  // End of variables declaration//GEN-END:variables

    protected simulator.Simulator mySim;

}