package editor;

import absynt.*;
import editor.einterface.*;

public class Eprogram {

  public AbsyntInterface ainterface;
  public Eprogram next;
  public Eprogram last;
  public Eprocess processlist;
  public absynt.Program program;

  public Eprogram (String inname) {
    ainterface = new AbsyntInterface();
    next = null;
    last = null;
    processlist = null;
    program = ainterface.makeNewProgram();
    setName(inname);
  }

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

  String[] getProcessNames() {
    String[] outarray;
    if (processlist != null) outarray = processlist.getProcessNames();
    else outarray = new String[0];
    return(outarray);
  }

  String getName() {
    String outname = "";
    if (program != null) outname = program.name;
    else System.out.println("Error !!!! (Eprogram.getName) no absynt.Program in Eprogram");
    return(outname);
  }

  void setName(String inname) {
    if (program != null) program.name = inname;
    else System.out.println("Error !!!! (Eprogram.setName) no absynt.Program in Eprogram");
  } 

  absynt.Program getProgram() {
    return(program);
  }

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
    if (getName().compareTo(inname) == 0) {
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

}
