
package modcheck;

import java.io.Serializable;

import absynt.*;


/**
 * ModstateList-Klasse.
 *
 * @author Initially provided by Frank Neumann, Aneta Kuemper, Eike Schulz
 * @version $Id: ModstateList.java,v 1.3 2000-07-17 13:01:26 unix01 Exp $
 */

/** Liste von Modelchecker-Zustaenden
 *
 */  

  public class ModstateList  
     extends Absyn 
     implements java.util.Enumeration, Serializable   { 
     public Modstate     head;
     public ModstateList next;

  public ModstateList (Modstate s, ModstateList sl)   {
     head = s;
     next = sl;
  }

//---------------------------------------------------------------------------------

  public boolean hasMoreElements()   {
     return next != null;
  };
  

  public Object nextElement()   {
     return  next;
  };

}

//---------------------------------------------------------------------------------
