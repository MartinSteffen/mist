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

public class Zeichenflaeche extends JComponent implements MouseListener, MouseMotionListener{

    ProcessWindow procwin;
    Dimension dimension;
    int x1, x2, old_x2, y1, y2, old_y2;
    float fx1, fx2, fy1, fy2;
    float staterange;
    float transrange;
    Estate selectedstate;
    Etransition selectedtransition;
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

    void calcTransRange() {
      transrange = (float)5 / (((float)dimension.height + (float)dimension.width) / (float)2);
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
//        g_alt.clearRect(0, 0, dimension.width, dimension.height);
      }

      if (g_alt == null) {
        System.out.println("Error !! g_alt == null");
      }  

      System.out.println("start painting ...");
      
      dimension = getSize();      
      g_alt.setColor(Color.white);
      System.out.println("color is set");
      g_alt.fillRect(0, 0, dimension.width, dimension.height);

      g_alt.setFont(procwin.editor.zeichenfont);
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

      paintContent(g_alt);    
        
      if (buffer) {
      	System.out.println("buffer active");
        g.drawImage(virtual, 0, 0, null);
      } else {

      }

//      procwin.editor.debugText("Zeichenflaeche (Paint): >> end <<");
    }

    void paintContent(Graphics g) {
      System.out.println("start paintig transitions");

      Etransition tsucher = procwin.eprocess.translist;
      while (tsucher != null) {
        paintTransition(g, tsucher, 0, tsucher.highlighted);
        tsucher = tsucher.next;
      }

      System.out.println("start paintig states");
      
      Estate ssucher = procwin.eprocess.statelist;
      while (ssucher != null) {
        paintState(g, ssucher, 0, ssucher.highlighted);
        ssucher = ssucher.next;
      }      
    }

    void checkActivation() {
      procwin.checkActivation();
    }

    void paintState (Graphics g, Estate estate, int mode, boolean highlight) {
      float x_cor = estate.getX();
      float y_cor = estate.getY();
      int int_x_cor = calcDistanceToInt(x_cor, dimension.width);
      int int_y_cor = calcDistanceToInt(y_cor, dimension.height);
      Color paintcolor = Color.black;
      g.setFont(procwin.editor.zeichenfont);

      if (highlight) paintcolor = Color.red; else if (mode == 1 || estate.isSelected()) paintcolor = Color.blue;
      
      g.setColor(paintcolor);

      if (x_cor != -1 || y_cor != -1) {
        if ((mode == 0) && (!(estate.isSelected()))){
          g.setColor(Color.white);
          g.fillOval(int_x_cor-15, int_y_cor-15, 30, 30);
          g.setColor(paintcolor);
          g.drawString(estate.getName(), int_x_cor-25, int_y_cor-25);
          g.drawOval(int_x_cor-15, int_y_cor-15, 30, 30);
          if (estate.type == 1) {
            g.drawLine(int_x_cor-40, int_y_cor, int_x_cor-15, int_y_cor);
            g.fillOval(int_x_cor-19, int_y_cor, 4, 4);
          }
          if (estate.type == 2) g.drawOval(int_x_cor-18, int_y_cor-18, 36, 36);
//          g.drawString(Integer.toString(z.identifier), int_x_cor-4, int_y_cor+4);
        } else if ((mode == 1) || (estate.isSelected())) {
          g.setColor(paintcolor);
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

    void paintTransition (Graphics g, Etransition etransition, int mode, boolean highlight) {
      Estate source_state = etransition.getSource();
      Estate target_state = etransition.getTarget();

      Color paintcolor = Color.black;

      if (highlight) paintcolor = Color.red; else if (mode == 1 || etransition.isSelected()) {
        System.out.println("Transition is selected !");
        paintcolor = Color.blue;
      }

      int statepixelrange = 15;
      
      float x1 = source_state.getX();
      float y1 = source_state.getY();
      int int_x1 = calcDistanceToInt(x1, dimension.width);
      int int_y1 = calcDistanceToInt(y1, dimension.height);
      
      float x2 = target_state.getX();
      float y2 = target_state.getY();
      int int_x2 = calcDistanceToInt(x2, dimension.width);
      int int_y2 = calcDistanceToInt(y2, dimension.height);

      int startx;
      int starty;
      int endx;
      int endy;

      int dx = Math.abs(int_x2 - int_x1);
      int dy = Math.abs(int_y2 - int_y1);
      double h = Math.sqrt((dx * dx) + (dy * dy));
      
//      System.out.println("dx = "+Integer.toString(dx));
//      System.out.println("dy = "+Integer.toString(dy));
//      System.out.println("h = "+Double.toString(h));

//      double hr = (15.0 / (double)dx) * h;
      double hr = 15.0;
      Double drx = new Double((hr / h) * dx);
      Double dry = new Double((hr / h) * dy);
      int rx = drx.intValue();
      int ry = dry.intValue();

//      System.out.println("drx = "+drx.toString());
//      System.out.println("dry = "+drx.toString());
//      System.out.println("hr = "+Double.toString(hr));
//      System.out.println("rx = "+Integer.toString(rx));
//      System.out.println("ry = "+Integer.toString(ry));

      if (int_x2 >= int_x1) {
        startx = int_x1 + rx;
        endx = int_x2 - rx;
      } else {
        startx = int_x1 - rx;
        endx = int_x2 + rx;
      }

      if (int_y2 >= int_y1) {
        starty = int_y1 + ry;
        endy = int_y2 - ry;
      } else {
        starty = int_y1 - ry;
        endy = int_y2 + ry;
      }
      
      int pslx = 0;
      int psly = 0;

      double pslength = 20.0;   // Pfeilspitzenlaenge
      
      int psdx = Math.abs(endx - startx);
      int psdy = Math.abs(endy - starty);
      double psh = Math.sqrt((psdx * psdx) + (psdy * psdy));
      
      Double dpsldx = new Double((pslength / psh) * psdx);
      Double dpsldy = new Double((pslength / psh) * psdy);
      int psldx = dpsldx.intValue();
      int psldy = dpsldy.intValue();
      
//      System.out.println("dpsldx = "+dpsldx.toString());
//      System.out.println("dpsldx = "+dpsldx.toString());
//      System.out.println("pslength = "+Double.toString(pslength));
//      System.out.println("psldx = "+Integer.toString(psldx));
//      System.out.println("psldy = "+Integer.toString(psldy));
      
      if (endx >= startx) {
        pslx = endx - psldx;
      } else {
        pslx = endx + psldx;
      }
      
      if (endy >= starty) {
        psly = endy - psldy;
      } else {
        psly = endy + psldy;
      }
      
      double pskh = 7.0; // Pfeilspitzenhoehe

      Double dpskdx = new Double((pskh / pslength) * psldy);
      Double dpskdy = new Double((pskh / pslength) * psldx);      
      int pskdx = dpskdx.intValue();
      int pskdy = dpskdy.intValue();

      int point1x;
      int point1y;
      int point2x;
      int point2y;

      if (startx <= endx) {
        point1x = pslx - pskdx;
        point2x = pslx + pskdx;
      } else {
        point1x = pslx + pskdx;
        point2x = pslx - pskdx;
      }

      if (starty <= endy) {
        point1y = psly + pskdy;
        point2y = psly - pskdy;
      } else {
        point1y = psly - pskdy;
        point2y = psly + pskdy;
      }

      int[] xarray = {point1x, point2x, endx};
      int[] yarray = {point1y, point2y, endy};
      int arraylength = 3; 

      int midx = (int_x1 + int_x2) / 2;
      int midy = (int_y1 + int_y2) / 2;

//      if (highlight) paintcolor = Color.red; else paintcolor = Color.black;

      g.setColor(paintcolor);
//old line      g.drawLine(int_x1, int_y1, int_x2, int_y2);

// draw transitionline
      g.drawLine(startx, starty, endx, endy);
      g.drawLine(pslx, psly, endx, endy);
      g.fillPolygon(xarray, yarray, arraylength);
      g.fillRect(midx-5, midy-5, 11, 11);

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
      calcTransRange();
      if (procwin.processselection != null) procwin.processselection.print();
      source_state = null;
      target_state = null;
      selectedstate = null;
      selectedtransition = null;
      selectedstate = procwin.eprocess.getStateInRange(fx1, fy1, staterange);
      if (selectedstate != null) selectedtransition = procwin.eprocess.getTransitionInRange(fx1, fy1, transrange);
      if (selectedstate != null) {
      	System.out.println("state selected !!");
      	if (!selectedstate.isSelected()) {
      	  System.out.println(".. clear selection");
          if (procwin.processselection != null) procwin.processselection = procwin.processselection.clear();
          System.out.println(".. insert state");
          procwin.processselection = new EditorSelection();
          procwin.processselection.editorobject = selectedstate;
          selectedstate.select();
//          paintState(getGraphics(), selectedstate, 1, selectedstate.highlighted);
          paintContent(getGraphics());
          if (procwin.processselection != null) procwin.processselection.print();
      	}
      } else if (selectedtransition != null) {
        System.out.println("transition selected !!");
        if (!selectedtransition.isSelected()) {
      	  System.out.println(".. clear selection");
          if (procwin.processselection != null) procwin.processselection = procwin.processselection.clear();
          System.out.println(".. insert transition");
          procwin.processselection = new EditorSelection();
          procwin.processselection.editorobject = selectedtransition;
          selectedtransition.select();
//          paintState(getGraphics(), selectedstate, 1, selectedstate.highlighted);
          paintContent(getGraphics());
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
            if (clicks > 1) {
              if (selectedstate != null) {
                SetStateDialog dialog = new SetStateDialog(procwin.editor, procwin.eprocess, selectedstate, false);
              } else if (selectedtransition != null) {
                SetTransitionDialog dialog = new SetTransitionDialog(procwin.editor, procwin.eprocess, selectedtransition, false);
              }
            }
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
              paintState(getGraphics(), selectedstate, 1, selectedstate.highlighted);
            }
          } else {
            System.out.println("set state :");
            String statename = procwin.editor.checkStateName(procwin.eprocess, "unnamed");
            Estate instate = new Estate(statename, null, fx1, fy1, 0);
            SetStateDialog ssdialog = new SetStateDialog(procwin.editor, procwin.eprocess, instate, true);
//            procwin.eprocess.addState(instate);
            if (procwin.eprocess.stateIsInProcess(instate)) paintState(getGraphics(), instate, 0, instate.highlighted);
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
              if (selectedstate != null) paintState(getGraphics(), selectedstate, 1, selectedstate.highlighted);
              paint(getGraphics());
            } else { paintState(getGraphics(), selectedstate,0, selectedstate.highlighted); }
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
            paintState(getGraphics(), selectedstate, 0, selectedstate.highlighted);
            paint(getGraphics());
          }
        } else if (procwin.editor.toolcommand.compareTo("trans") == 0) {
          if (source_state != null) {
            System.out.println("source ist nicht null");
            g.setXORMode(Color.white);
            int int_x = calcDistanceToInt(source_state.getX(), dimension.width);
            int int_y = calcDistanceToInt(source_state.getY(), dimension.height);
            g.drawLine(int_x, int_y, x2, y2);	
            target_state = procwin.eprocess.getStateInRange(fx2, fy2, staterange);
            if (target_state != null) {
              System.out.println("hauaha, Transition soll erzeugt werden");
              Etransition newtransition = new Etransition(source_state, target_state, null);
              System.out.println("starte Dialog");
              SetTransitionDialog stdialog = new SetTransitionDialog(procwin.editor, procwin.eprocess, newtransition, true);
              paintComponents(getGraphics());
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
//      System.out.println("mouseDragged");      
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
//            System.out.println("mouseDragged: "+Integer.toString(x2)+" "+Integer.toString(y2));
            drawSelection(g, x2, y2);
          }
        } else if (procwin.editor.toolcommand.compareTo("trans") == 0) {
          if (source_state != null) {
            g.setXORMode(Color.white);
            int int_x = calcDistanceToInt(source_state.getX(), dimension.width);
            int int_y = calcDistanceToInt(source_state.getY(), dimension.height);
            if ((old_x2 != x1) || (old_y2 !=y1)) g.drawLine(int_x, int_y, old_x2, old_y2);
            if (x2 < 0) x2 = 0;
            if (y2 < 0) y2 = 0;
            g.drawLine(int_x, int_y, x2, y2);
          }
        }
        g.setPaintMode();
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