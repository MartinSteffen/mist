package absynt;
import java.io.Serializable;

/**
 * Klasse f"ur die Speicherung der Zeilen.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Location.java,v 1.3 2000-07-10 16:12:26 unix01 Exp $
 */


public class Location implements Serializable {
    int line;
    public Location(int l) { line = l;}

  //----------------------------------------
    public String toString() {
        return new Integer(line).toString();
    }

}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist programs
//	------------------------------------
//
//	$Id: Location.java,v 1.3 2000-07-10 16:12:26 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/05/28 12:57:11  unix01
//	Zwischenzustand, vor Reorganisation
//	
//---------------------------------------------------------------------

