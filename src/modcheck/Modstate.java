package modcheck;

import absynt.*;

import absynt.Process;


/** 
 * Modstate-Klasse
 *
 * @author Initially provided by Frank Neumann, Aneta Kuemper, Eike Schulz
 * @version $Id: Modstate.java,v 1.2 2000-07-17 13:01:26 unix01 Exp $
 */

/** 
 *  Modstate ist Zustand für Modelchecker
 *  es besteht aus Astate und VardecList
 */

   public class Modstate {

   public Astate state;

   public VardecList vars;

   public Modstate(Astate a, VardecList v)  {

                   state=a;
                   vars=v;
  }
 } // class Modstate


