package absynt;
import java.io.Serializable;


/**
 * Klasse fuer Kanalnamen. Im wesentlichen ein Wrapper fuer Strings
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Channel.java,v 1.3 2000-07-13 08:29:27 unix01 Exp $
 */


public class Channel extends Absyn implements Serializable { 
	public String name ;
  
	public Channel (String s) {
		name = s;};

}




//----------------------------------------------------------------------
//	Abstract Syntax for Mist Programs
//	------------------------------------
//
//	$Id: Channel.java,v 1.3 2000-07-13 08:29:27 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.2  2000/07/12 17:01:56  unix01
//	Typ hinzugefuegt (und somit auch zusaetzlichen Konstruktor)
//	
//	Revision 1.1  2000/06/04 09:35:21  unix01
//	Klasse fuer Kanalnamen neu hinzugefuegt. Die Klasse
//	war _nicht_ im originalen Syntaxvorschlag!
//	
//	
//---------------------------------------------------------------------

