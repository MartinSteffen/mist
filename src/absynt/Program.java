package absynt;
import java.io.Serializable;

/**
 * Toplevel syntactic contruct for Mist programs.
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Program.java,v 1.3 2000-05-28 12:57:12 unix01 Exp $
 */





public class Program extends Absyn implements Serializable {
  public ChandecList chans;
  public ProcessList procs;

 /**
  * Haupt-konstruktor, übernimmt schlicht die Argumente in die Felder
  */
  public Program (ChandecList cl, ProcessList pl) {
    chans    = cl;
    procs    = pl;
  };
};


//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Program.java,v 1.3 2000-05-28 12:57:12 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 08:51:48  unix01
//	ok
//	
//	Revision 1.1  2000/05/28 08:08:12  unix01
//	Initiale Revision, zum testen der cvs-log-Funktion
//	
//---------------------------------------------------------------------
