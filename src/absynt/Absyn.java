package absyn;
import java.io.Serializable;



/**
 * Abstract class to provide coordinates and locations.
 * (Some) graphical objects may have <em>coordinates</em>.
 * Those should inherit from this abstract class.
 *
 * The classes of this package
 * are a straighforward implmementation of the EBNF definition,
 * so in most cases, the code is self-explanatory.
 * 
 * @author Initially provided by Martin Steffen.
 * @version  $Id: Absyn.java,v 1.1 2000-05-28 08:51:46 unix01 Exp $
 */



abstract public class Absyn implements Serializable {
    public Location location;
};



//----------------------------------------------------------------------
//	Abstract Syntax for Mist programs
//	------------------------------------
//
//	$Id: Absyn.java,v 1.1 2000-05-28 08:51:46 unix01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.1  2000/05/28 08:14:57  unix01
//	Initiale Revision (Test fuer $ENVIRONMENT)
//	
//---------------------------------------------------------------------
