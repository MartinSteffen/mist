package absynt;
import java.io.Serializable;


/**
 * Abstrakte Klasse f"ur Zustand
 *
 * @author Initially provided by Martin Steffen.
 * @version $Id: Astate.java,v 1.6 2000-06-29 19:45:43 unix01 Exp $
 */


public abstract class Astate extends Absyn implements Serializable { 
    
    public String name;
    public Expr   assert;
    public Position pos;
    
}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Astate.java,v 1.6 2000-06-29 19:45:43 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  2000/06/19 17:17:38  unix01
//	-  Zus"atzliche "uberladene Konstruktoren eingef"ugt
//	
//	-  Felder f"ur State/Initstate nach  Astate verschoben.
//	
//	Revision 1.4  2000/06/19 17:06:44  unix01
//	Die abstrakte Syntax angepa"st (gem"a"s den heutigen Entscheidungen)
//	
//	
//		1) Positionen f"ur Zust"ande eingef"uhrt
//		2) Zustandsliste + Initialer Zustand in Prozessen extra gespeichert.
//	
//	Revision 1.3  2000/06/08 14:20:07  unix01
//	*** empty log message ***
//	
//	Revision 1.2  2000/05/28 12:57:09  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.1  2000/05/28 11:11:22  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

