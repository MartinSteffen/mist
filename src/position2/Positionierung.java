/**
 * Interface für alle Positionierungsklassen
 * 
 * @author pm@ariva.de
 * @version 1.0
 */
package position2;

import absynt.Process;

public interface Positionierung {
  /**
   * setzt die Positionen aller Zustände eines Prozesses neu
   * 
   * @param process der zu positionierende Prozess
   */
   public void positioniere(Process process);   
}
