/**
 * Interface für alle Positionierungsklassen
 * 
 * @author pm@ariva.de (original) c.buck@gmx.de (kontakt)
 * @version 1.0
 */
package position1;

import absynt.Process;

public interface Position {
  /**
   * setzt die Positionen aller Zustände eines Prozesses neu
   * 
   * @param process der zu positionierende Prozess
   */
   public void positioniere(Process process);   
}
