package absyn;
import java.io.Serializable;

/**
 * Klasse f"ur die Speicherung der Zeilen.
 * 
 * @author Initially provided by Martin Steffen.
 * @version $ $
 */


public class Location implements Serializable {

    int line;
    public Location(int l) { line = l;}

    public String toString() {
        return new Integer(line).toString();
    }

}


