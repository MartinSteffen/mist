package absynt;
import java.io.Serializable;



/**
 * Abstract class to provide coordinates and locations.
 * Um sp"ater das Parsen zu erleichten, ist ein Feld
 * vorgesehen, welches zumindest die Zeilennummber aufnehmen kann.
 * Alle syntaktischen konstrukte, bei denen Zeileninformation
 * sinnvoll ist, sollen hiervon erben.
 *
 * The classes of this package
 * are a straighforward implmementation of the EBNF definition,
 * so in most cases, the code is self-explanatory.
 * 
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Absyn.java,v 1.4 2000-05-29 12:52:20 unix01 Exp $
 */



abstract public class Absyn implements Serializable {
    public Location location;
};





//----------------------------------------------------------------------
//	Abstract Syntax for Mist programs
//	------------------------------------
//
//	$Id: Absyn.java,v 1.4 2000-05-29 12:52:20 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.3  2000/05/28 12:57:08  unix01
//	Zwischenzustand, vor Reorganisation
//	
//	Revision 1.2  2000/05/28 11:11:21  unix01
//	ok
//	
//	Revision 1.1  2000/05/28 08:51:46  unix01
//	ok
//	
//	Revision 1.1  2000/05/28 08:14:57  unix01
//	Initiale Revision (Test fuer $ENVIRONMENT)
//	
//---------------------------------------------------------------------
