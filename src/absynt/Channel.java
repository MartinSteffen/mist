package absynt;
import java.io.Serializable;


/**
 * Klasse fuer Kanalnamen. Im wesentlichen ein Wrapper fuer Strings
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: Channel.java,v 1.1 2000-06-04 09:35:21 unix01 Exp $
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
//	$Id: Channel.java,v 1.1 2000-06-04 09:35:21 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	
//---------------------------------------------------------------------

