package editor;

import absynt.*;
import editor.einterface.*;
import javax.swing.*;

/**
 * Diese Klasse dient dem Editor als Kapsel fuer ein absynt.Program.
 */
public class Eprogram {

  public AbsyntInterface ainterface;
  public Eprogram next;
  public Eprogram last;
  public Eprocess processlist;
  public absynt.Program program;

/**
 * Erzeugt ein neues absynt.Program mit dem uebergebenen Namen.
 */
  public Eprogram (String inname) {
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
    processlist = null;
    program = ainterface.makeNewProgram();
    setName(inname);
  }

/**
 * Wrappt ein Eprogram um das uebergebene absynt.Program.
 */
  public Eprogram (absynt.Program inprogram) {	
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
// bei uebergebenem Leerprogram neues Program erstellen
    if (inprogram == null) {
      program = ainterface.makeNewProgram();
    } else {
      program = inprogram;
    }
// bei vorhandenem Inhalt EditorSyntax darumbauen
    if (program.procs != null) {
      processlist = new Eprocess(this, program.procs.head, program.procs);
    }
  }

/**
 * Schliesst alle ProcessWindows.
 */
  void removeProcessWindows () {
    if (processlist != null) {
      Eprocess sucher = processlist;
      while (sucher != null) {
        sucher.removeProcessWindow();
        sucher = sucher.next;
      }
    }
  }

/**
 * Erzeugt Processwindows fuer alle Processe.
 */
  void createProcessWindows(Editor editroot, JDesktopPane dpane) {
    System.out.println("(Eprogram.createProcessWindows()) ...");
    if (processlist != null) {
      System.out.println("processlist not null !");
      Eprocess sucher = processlist;
      while (sucher != null) {
      	System.out.println("creating a window ...");
        sucher.createProcessWindow(editroot, dpane);
        System.out.println("window was created");
        sucher = sucher.next;
        System.out.println("switched to next process");
      }
    }
  }

/**
 * liefert die Namen aller Processes des Programms als Stringliste zurueck.
 */
  String[] getProcessNames() {
    System.out.println("(Eprogram.getProcessNames) starting ...");
    String[] outarray;
    if (processlist != null) outarray = processlist.getProcessNames();
    else outarray = new String[0];
    System.out.println("... ready (Eprogram.getProcessNames)");
    return(outarray);
  }

/**
 * Liefert den Namen des Programs zurueck.
 */
  String getName() {
    String outname = "";
    if (program != null) outname = program.name;
    else System.out.println("Error !!!! (Eprogram.getName) no absynt.Program in Eprogram");
    return(outname);
  }

/**
 * benennt das Programm mit dem uebergebenen String.
 */
  void setName(String inname) {
    if (program != null) program.name = inname;
    else System.out.println("Error !!!! (Eprogram.setName) no absynt.Program in Eprogram");
  } 

/**
 * Liefert das absynt.Program des Eprograms zurueck.
 */
  absynt.Program getProgram() {
    return(program);
  }

/**
 * fuegt ein uebergebenes Programm am Ende der Programmliste an.
 */
  public Eprogram appendProgram (absynt.Program inprogram) {
    Eprogram outprogram;
    if (next == null) {
      outprogram = new Eprogram(inprogram);
      outprogram.last = this;
      next = outprogram;
    } else {
      outprogram = next.appendProgram(inprogram);
    }
    return(outprogram);
  }

/**
 * erzeugt einen neuen Process mit dem uebergebenen Namen.
 */
  public Eprocess newProcess(String inname) {
    Eprocess outprocess;
    outprocess = new Eprocess(this, inname);
    if (processlist == null) {
      program.procs = outprocess.getList();
      processlist = outprocess;
    } else {
      Eprocess sucher = processlist;
      while (sucher.next != null) sucher = sucher.next;
      outprocess.last = sucher;
      sucher.next = outprocess;
      sucher.setNext(outprocess);
    }
    return(outprocess);
  }

/**
 * entfernt den uebergebenen Process aus dem Program.
 */
  void removeProcess(Eprocess inprocess) {
    if (processlist != null) {
      if (processlist == inprocess) {
        processlist = inprocess.next;
        if (processlist != null) {
          processlist.last = null;
          processlist.setNext(null);
          program.procs = processlist.getList();
        } else {
          program.procs = null;
        }
      } else {
        Eprocess sucher = processlist;
        while (sucher != inprocess && sucher != null) sucher = sucher.next;
        if (sucher != null) {
          sucher.last.next = sucher.next;
          if (sucher.next != null) sucher.next.last = sucher.last;
          sucher.last.setNext(sucher.next);
          sucher.setNext(null);
        }
      }
    }
  }

/**
 * Prueft, ob der uebergebene Name bereits fuer ein Program
 * vergeben ist.
 */
  public boolean checkProgramTitle (String inname) {
    boolean wert = false;
    if (getName().compareTo(inname) == 0) {
      wert = true;
    } else {
      if (next != null) {
        wert = next.checkProgramTitle(inname);
      }
    }
    return(wert);
  }

/**
 * Prueft, ob bereits ein Process mit dem Uebergebenen Namen
 * existiert.
 */
  public boolean checkProcessTitle (String inname) {
    boolean wert = false;
    if (processlist != null) {
      wert = processlist.checkProcessTitle(inname);
    }
    return(wert);
  }


/**
 * sucht inm Program nach einem Eprocess mit dem uebergebenen
 * Namen und liefert ihn zurueck.
 */
  Eprocess getProcessByName(String inname) {
    Eprocess outprocess = null;
    if (processlist != null) {
      outprocess = processlist.getProcessByName(inname);
    }
    return(outprocess);
  }

/**
 * liefert das Eprogram zurueck, welches das uebergeben absynt.Program kapselt
 */
  Eprogram getEprogramWithProgram(absynt.Program inprogram) {
    Eprogram outprogram = null;
    if (getProgram() == inprogram) outprogram = this;
    else {
      if (next != null) outprogram = next.getEprogramWithProgram(inprogram);
    }
    return(outprogram);
  }

/**
 * liefert den Eprocess zurueck, welcher den eubergebenen absynt.Process kapselt
 */
  Eprocess getEprocessWithProcess(absynt.Process inprocess) {
    Eprocess outprocess = null;
    if (processlist != null) outprocess = processlist.getEprocessWithProcess(inprocess);
    return(outprocess);
  }

}
