package absynt;
import java.io.Serializable;


/**
 * Liste fuer Variablen-Deklarationen.
 * Zum standesgemaessen Iterieren implementiert die
 * Klasse das entsprechende Interface. (In Java.1.2 wird Iterator
 * dem Enumeration-Interface vorgezogen. Aber Loeschen in der
 * Liste brauchen wir vermutlich nicht.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: VardecList.java,v 1.5 2000-07-17 13:02:47 unix01 Exp $
 */


public class VardecList  extends Absyn implements java.util.Enumeration { 
    public VardecList next;
    public Vardec     head;

    /**
     * Ein Konstruktor mit Location ist  ueberfluessig.
     */
    public VardecList(Vardec d, VardecList dl) {
	head = d;
	next = dl;
    }

    
    //---------------------------------------------
    public boolean hasMoreElements() {
	return next != null;
    };
    public Object nextElement() {
	return  next;
    };
 public Object clone () {

    VardecList copiedList = new VardecList((Vardec)( head.clone() ), null);
    VardecList iterator   = this;

    while (iterator.hasMoreElements()) {

      Vardec v =
	(Vardec)( ((VardecList)(iterator.nextElement())).head.clone() );

      copiedList = new VardecList(v, copiedList);
      iterator   = (VardecList)(iterator.nextElement());

    } // while

    return copiedList;

  } // method clone




  //--- Methode fuer >> Modelchecker <<

  /**
   * Methode zum Kopieren einer VardecList.
   * Ueberschreibt java.lang.Object.clone()
   * @return Kopie dieses Objektes
   */
 

}

//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: VardecList.java,v 1.5 2000-07-17 13:02:47 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.4  2000/07/10 13:56:54  unix01
//	Printmethode eingefuegt
//	
//	Revision 1.3  2000/06/26 13:11:45  unix01
//	
//	
//	1)Fuer alle Listen-Klassen die head und tail ``public'' gemacht,
//	  dies wird von von dem Editor gebraucht.
//	
//	2) Fehler in der Klasse B_Expr behoben, der Operator
//	   war nicht gespeichert.
//	
//	Revision 1.2  2000/06/04 12:11:55  unix01
//	ok
//	
//	Revision 1.1  2000/05/28 15:11:17  unix01
//	ok
//	
//	
//---------------------------------------------------------------------

