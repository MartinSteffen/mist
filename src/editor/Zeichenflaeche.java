package editor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import editor.undo.*;
import absynt.*;
import editor.einterface.*;
import java.beans.PropertyVetoException;
import javax.swing.plaf.basic.*;
import gui.*;

  /** Zeichenflaeche, auf der nachher die Zustaende und
   *  Uebergaenge gezeichnet werden sollen
   */

public class Zeichenflaeche extends Canvas implements MouseListener, MouseMotionListener{

    ProcessWindow procwin;
    Dimension dimension;
    int x1, x2, old_x2, y1, y2, old_y2;
    float fx1, fx2, fy1, fy2;
    float staterange;
    Estate selectedstate;
    Estate source_state;
    Estate target_state;

    Zeichenflaeche (ProcessWindow procroot) {
      super();
      procwin = procroot;
      this.setBackground(Color.white);
      addMouseListener(this);
      addMouseMotionListener(this);
      setFont(procwin.editor.zeichenfont);
      staterange = (float)0.1;
    }

    void calcStateRange() {
      staterange = (float)15 / (((float)dimension.height + (float)dimension.width) / (float)2);
    }

    float calcDistanceToFloat (int pix, int max) {
      return((float)pix / (float)max);
    }

    int calcDistanceToInt (float pix, int max) {
      return((int)(pix*max));
    }

    void paintBorder() {
      Dimension size = getSize();
      Graphics g = getGraphics();
      g.setColor(Color.black);
      g.drawLine(0, 0, size.width-1, 0);
      g.drawLine(0, 0, 0, size.height-1);
      g.drawLine(0, size.height-1, size.width-1, size.height-1);
      g.drawLine(size.width-1, 0, size.width-1, size.height-1);
    }

    public void update(Graphics g) {
      if (procwin.editor.debug) procwin.editor.debugText("Zeichenflaeche (update): >> start <<"); 
//      if (procwin.editor.debug) procwin.editor.debugText("Zeichenflaeche (update): >> end <<");
    }
    public void repaint(long tm, int x, int y, int width, int height) {
      if (procwin.editor.debug) procwin.editor.debugText("Zeichenflaeche (repaint(x,x,x,x,x)): >> start <<");
//      if (procwin.editor.debug) procwin.editor.debugText("Zeichenflaeche (repaint(x,x,x,x,x)): >> end <<");
    }
    public void repaint(Rectangle r) {
      if (procwin.editor.debug) procwin.editor.debugText("Zeichenflaeche (repaint(x)): >> start <<");
//      if (procwin.editor.debug) procwin.editor.debugText("Zeichenflaeche (repaint(x)): >> end <<");
    }
    public void paint(Graphics g) {
      procwin.editor.debugText("Zeichenflaeche (Paint): >> start <<");
//      BufferedImage bufferimage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
//      Graphics ig = bufferimage.getGraphics();

      boolean buffer = procwin.editor.getGraficBufferStatus();

      Image virtual = null;
      Graphics g_alt = null;

      if (buffer) {
      	System.out.println("buffer active");
        virtual = createImage(getSize().width, getSize().height);
        System.out.println("image created, getting graphics");
        g_alt = virtual.getGraphics();
      } else {
      	System.out.println("buffer inactive");
        g_alt = g;
        g_alt.clearRect(0, 0, dimension.width, dimension.height);
      }

      System.out.println("start painting ...");
      g_alt.setFont(procwin.editor.zeichenfont);
      dimension = getSize();
      calcStateRange();
      g_alt.setColor(Color.black);
      paintBorder();
      if (procwin.isGrid()) {
        for (int gx=10; gx <= dimension.width; gx += 10) {
          for (int gy=10; gy <= dimension.height; gy += 10) {
            g_alt.drawLine(gx,gy,gx,gy);
          }
        }
      }
//      imageUpdate(bufferimage, ALLBITS, 0, 0, dimension.width, dimension.height);
      Etransition tsucher = procwin.eprocess.translist;
      while (tsucher != null) {
        paintTransition(g_alt, tsucher, 0);
        tsucher = tsucher.next;
      }      
      Estate ssucher = procwin.eprocess.statelist;
      while (ssucher != null) {
        paintState(g_alt, ssucher, 0);
        ssucher = ssucher.next;
      }
      
      if (buffer) {
      	System.out.println("buffer active");
        g.drawImage(virtual, 0, 0, null);
      } else {

      }

//      procwin.editor.debugText("Zeichenflaeche (Paint): >> end <<");
    }

    void checkActivation() {
      procwin.checkActivation();
    }

    void paintState (Graphics g, Estate estate, int mode) {
      float x_cor = estate.getX();
      float y_cor = estate.getY();
      int int_x_cor = calcDistanceToInt(x_cor, dimension.width);
      int int_y_cor = calcDistanceToInt(y_cor, dimension.height);
      g.setFont(procwin.editor.zeichenfont);
      if (mode == 0) g.setColor(Color.black);

      if (x_cor != -1 || y_cor != -1) {
        if ((mode == 0) && (!(estate.isSelected()))){
          g.setColor(Color.white);
          g.fillOval(int_x_cor-15, int_y_cor-15, 30, 30);
          g.setColor(Color.black);
          g.drawString(estate.getName(), int_x_cor-25, int_y_cor-25);
          g.drawOval(int_x_cor-15, int_y_cor-15, 30, 30);
          if (estate.type == 1) {
            g.drawLine(int_x_cor-40, int_y_cor, int_x_cor-15, int_y_cor);
            g.fillOval(int_x_cor-19, int_y_cor, 4, 4);
          }
          if (estate.type == 2) g.drawOval(int_x_cor-18, int_y_cor-18, 36, 36);
//          g.drawString(Integer.toString(z.identifier), int_x_cor-4, int_y_cor+4);
        } else if ((mode == 1) || (estate.isSelected())) {
          g.setColor(Color.black);
          g.drawString(estate.getName(), int_x_cor-25, int_y_cor-25); 
          g.fillOval(int_x_cor-15, int_y_cor-15, 30, 30);
          if (estate.type == 1) {
            g.drawLine(int_x_cor-40, int_y_cor, int_x_cor-15, int_y_cor);
            g.fillOval(int_x_cor-19, int_y_cor, 4, 4);
          } 
          if (estate.type == 2) g.drawOval(int_x_cor-18, int_y_cor-18, 36, 36);
          g.setColor(Color.white);
//          g.drawString(Integer.toString(z.identifier), int_x_cor-4, int_y_cor+4); 
        }
      }
    }

    void paintTransition (Graphics g, Etransition etransition, int mode) {
      Estate source_state = etransition.getSource();
      Estate target_state = etransition.getTarget();

      float x1 = source_state.getX();
      float y1 = source_state.getY();
      int int_x1 = calcDistanceToInt(x1, dimension.width);
      int int_y1 = calcDistanceToInt(y1, dimension.height);
      float x2 = target_state.getX();
      float y2 = target_state.getY();
      int int_x2 = calcDistanceToInt(x2, dimension.width);
      int int_y2 = calcDistanceToInt(y2, dimension.height);
      g.setColor(Color.black);
      g.drawLine(int_x1, int_y1, int_x2, int_y2);
    }

    public void mouseClicked(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {
      int clicks = event.getClickCount();
      checkActivation();
      if (procwin.editor.debug) procwin.editor.debugText("mousepressed-event : "+event.getSource());
      x1 = event.getX();
      y1 = event.getY();    
      fx1 = calcDistanceToFloat(x1, dimension.width);
      fy1 = calcDistanceToFloat(y1, dimension.height);
      x2 = x1;
      y2 = y1;
      fx2 = fx1;
      fy2 = fy1;
      if (procwin.processselection != null) procwin.processselection.print();
      source_state = null;
      target_state = null;
      selectedstate = null;
      selectedstate = procwin.eprocess.getStateInRange(fx1, fy1, staterange);
      if (selectedstate != null) {
      	System.out.println("state selected !!");
      	if (!selectedstate.isSelected()) {
      	  System.out.println(".. clear selection");
          if (procwin.processselection != null) procwin.processselection = procwin.processselection.clear();
          System.out.println(".. insert state");
          procwin.processselection = new EditorSelection();
          procwin.processselection.editorobject = selectedstate;
          selectedstate.select();
          paintState(getGraphics(), selectedstate, 1);
          if (procwin.processselection != null) procwin.processselection.print();
      	}
      } else {
        if (procwin.processselection != null) procwin.processselection = procwin.processselection.clear();
      }
      if (procwin.editor.debug) procwin.editor.debugText("mousePressed: "+Integer.toString(x1)+" "+Integer.toString(y1));
      System.out.println("mousePressed: "+Integer.toString(x1)+" : "+Integer.toString(y1)+" -> "+Float.toString(fx1)+" : "+Float.toString(fy1));

// rechter Mouse-Button gedrueckt
      if (event.getModifiers() == 4) {
        System.out.println("pressed - popupmenu");
        if (procwin.editor.toolcommand.compareTo("select") == 0) {
//          if (selectedstate != null) new PopupMenuState(selectedstate, x1, y1);
        }

// linker Mouse-Button gedrueckt
      } else {
      	System.out.println("pressed - no popup");
        if (procwin.editor.toolcommand.compareTo("select") == 0) {
          System.out.println("select ...");
          if ((selectedstate == null) && (procwin.processselection != null)) procwin.processselection.clear();
          if (procwin.processselection != null) {
            // move Selection
          } else {
            // make selection
//            Graphics g = getGraphics();
//            g.setXORMode(Color.white);
//            drawSelection(g);
          }
        } else if (procwin.editor.toolcommand.compareTo("state") == 0) {
          if (selectedstate != null) {
            if (clicks > 1) {
              SetStateDialog dialog = new SetStateDialog(procwin.editor, procwin.eprocess, selectedstate, false);
            } else {
              System.out.println("state selected to move");
              paintState(getGraphics(), selectedstate, 1);
            }
          } else {
            System.out.println("set state :");
            String statename = procwin.editor.checkStateName(procwin.eprocess, "unnamed");
            Estate instate = new Estate(statename, null, fx1, fy1, 0);
            SetStateDialog ssdialog = new SetStateDialog(procwin.editor, procwin.eprocess, instate, true);
//            procwin.eprocess.addState(instate);
            if (procwin.eprocess.stateIsInProcess(instate)) paintState(getGraphics(), instate, 0);
          }
        } else if (procwin.editor.toolcommand.compareTo("trans") == 0) {
          System.out.println("draw transition");
          if (selectedstate != null) {
            source_state = selectedstate;
          }
        }
      }
    }
    public void mouseReleased(MouseEvent event) {
      System.out.println("mouseReleased");
      Graphics g = getGraphics();
      
// rechter Mouse-Button
      if (event.getModifiers() == 4) {
        System.out.println("released - popupmenu");

// linker Mouse-Button
      } else {
        if (procwin.editor.toolcommand.compareTo("select") == 0) {
          if (procwin.processselection != null) {
            // move Selection
            if ((x2 != x1) || (y2 != y1)) {
//              selectedstate.x_cor = fx2;
//              selectedstate.y_cor = fy2;
              procwin.processselection.move(fx2 - fx1, fy2 - fy1);
              if (selectedstate != null) paintState(getGraphics(), selectedstate, 1);
              paint(getGraphics());
            } else { paintState(getGraphics(), selectedstate,0); }
          } else {
            if ((x2 != x1) || (y2 != y1)) {
              g.setXORMode(Color.white);
              drawSelection(g, x2, y2);
              if (procwin.processselection != null) procwin.processselection = procwin.processselection.clear();
              procwin.processselection = procwin.eprocess.rangeSelect(fx1, fy1, fx2, fy2);
              paint(getGraphics());
            } else {
              paint(getGraphics());
            }
          }
        } else if (procwin.editor.toolcommand.compareTo("state") == 0) {
          if ((selectedstate != null) && ((x1 != x2) && (y1 != y2))) {
            selectedstate.setPosition(fx2, fy2);
            paintState(getGraphics(), selectedstate, 0);
            paint(getGraphics());
          }
        } else if (procwin.editor.toolcommand.compareTo("trans") == 0) {
          if (source_state != null) {
            System.out.println("source ist nicht null");	
            target_state = procwin.eprocess.getStateInRange(fx2, fy2, staterange);
            if (target_state != null) {
              System.out.println("hauaha, Transition soll erzeugt werden");
              Etransition newtransition = new Etransition(source_state, target_state, null);
              System.out.println("starte Dialog");
              SetTransitionDialog stdialog = new SetTransitionDialog(procwin.editor, newtransition);
              procwin.eprocess.addTransition(newtransition);
            }
          }
        }
      }
      if (procwin.processselection != null) procwin.processselection.print();
    }
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    public void mouseDragged(MouseEvent event) {
      old_x2 = x2;
      old_y2 = y2;
      x2 = event.getX();
      y2 = event.getY();
      fx2 = calcDistanceToFloat(x2, dimension.width);
      fy2 = calcDistanceToFloat(y2, dimension.height);      
      System.out.println("mouseDragged");      
      Graphics g = getGraphics();
      if (event.getModifiers() == 4) {
        System.out.println("dragged - popupmenu");
      } else {
        if (procwin.editor.toolcommand.compareTo("select") == 0) {
          if (selectedstate != null) {

          } else {
            g.setXORMode(Color.white);
            if ((old_x2 != x1) || (old_y2 !=y1)) drawSelection(g, old_x2, old_y2);
            if (x2 < 0) x2 = 0;
            if (y2 < 0) y2 = 0;
            System.out.println("mouseDragged: "+Integer.toString(x2)+" "+Integer.toString(y2));
            drawSelection(g, x2, y2);
          }
        } else g.setPaintMode();
      }
    }
    public void mouseMoved(MouseEvent event) {}

    void refresh() {
      paint(getGraphics());
    }

    public void drawSelection (Graphics g, int dx, int dy) {
      if ((x1 <= dx) && (y1 <= dy)) g.drawRect(x1, y1, dx-x1, dy-y1);
      else if ((x1 > dx) && (y1 <= dy)) g.drawRect(dx, y1, x1-dx, dy-y1);
      else if ((x1 <= dx) && (y1 > dy)) g.drawRect(x1, dy, dx-x1, y1-dy);
      else if ((x1 > dx) && (y1 > dy)) g.drawRect(dx, dy, x1-dx, y1-dy);
    }

}