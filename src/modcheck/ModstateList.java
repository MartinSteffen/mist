package modcheck;
import java.io.Serializable;
import absynt.*;

/**
 * Eine Liste von Modelchecker-Zustaenden (init oder nicht)
 * 
 * @author Initially provided by Martin Steffen.
 * @version $Id: ModstateList.java,v 1.2 2000-07-10 13:54:22 unix01 Exp $
 */


public class ModstateList  
  extends Absyn 
  implements java.util.Enumeration, Serializable { 
  public Modstate     head;
  public ModstateList next;

  public ModstateList (Modstate s, ModstateList sl) {
    head = s;
    next = sl;
  }

  //---------------------------------------

  public boolean hasMoreElements() {
    return next != null;
  };
  

  public Object nextElement() {
    return  next;
  };

}


