package editor;

import absynt.*;
import editor.einterface.*;

public class Eprogram {

  public AbsyntInterface ainterface;
  public Eprogram next;
  public Eprogram last;
  public Eprocess processlist;
  public absynt.Program program;
  public String name;

  public Eprogram (String inname) {
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
    processlist = null;
    program = ainterface.makeNewProgram();
    name = inname;
  }

  public Eprogram (String inname, absynt.Program inprogram) {	
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
// bei uebergebenem Leerprogram neues Program erstellen
    if (inprogram == null) {
      program = ainterface.makeNewProgram();
    } else {
      program = inprogram;
    }
    name = inname;
// bei vorhandenem Inhalt EditorSyntax darumbauen
    if (program.procs != null) {
      processlist = new Eprocess(this, "???", program.procs.head, program.procs);
    }
  }

  absynt.Program getProgram() {
    return(program);
  }

  public Eprogram appendProgram (String inname, absynt.Program inprogram) {
    Eprogram outprogram;
    if (next == null) {
      outprogram = new Eprogram(inname, inprogram);
      outprogram.last = this;
      next = outprogram;
    } else {
      outprogram = next.appendProgram(inname, inprogram);
    }
    return(outprogram);
  }

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

  public boolean checkProgramTitle (String inname) {
    boolean wert = false;
    if (name.compareTo(inname) == 0) {
      wert = true;
    } else {
      if (next != null) {
        wert = next.checkProgramTitle(inname);
      }
    }
    return(wert);
  }

  public boolean checkProcessTitle (String inname) {
    boolean wert = false;
    if (processlist != null) {
      wert = processlist.checkProcessTitle(inname);
    }
    return(wert);
  }

  public void setName (String inname) {
    name = inname;
  }

  public String getName () {
    return(name);
  } 

}
