package absynt;
import java.io.Serializable;


/**
 * Position, fuer die Graphische Darstellung. Momentan x/y Koordinaten
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Position.java,v 1.2 2000-07-02 15:52:30 unix01 Exp $
 */


public class Position implements Serializable{ 
  public float x;
  public float y;
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Position.java,v 1.2 2000-07-02 15:52:30 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/06/19 17:06:44  unix01
//	Die abstrakte Syntax angepa"st (gem"a"s den heutigen Entscheidungen)
//	
//	
//		1) Positionen f"ur Zust"ande eingef"uhrt
//		2) Zustandsliste + Initialer Zustand in Prozessen extra gespeichert.
//	
//	
//---------------------------------------------------------------------

