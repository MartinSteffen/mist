package absynt;
import java.io.Serializable;


/**
 * Variablen: der Einfachheit halber als String implementiert
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Variable.java,v 1.1 2000-06-04 07:24:58 unix01 Exp $	
 */


public class Variable extends Absyn implements Serializable { 
  public String name;

  public Variable (String s) {
    name = s;};
};




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Variable.java,v 1.1 2000-06-04 07:24:58 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	
//---------------------------------------------------------------------

