/**
 * plaziert alle Zustände eines Prozesses mit einer Art Gravitationsalgortihmus 
 * 
 * @author pm@ariva.de
 * @version 1.0
 */
package position2;

import absynt.Process;
import absynt.Position;
import absynt.*;
import java.util.Enumeration;
import java.util.Vector;


public class PositionGrav implements Positionierung {

  Vector states;

  public PositionGrav () {
  }
  
  public void positioniere(Process process) {
    AstateList stateList1 = process.states;
//    for (;stateList1.hasMoreElements();) {
//        states.(new gravAstate((Astate)stateList1.nextElement()));
//    }
/*    AstateList stateList2 = process.states;
    for (;stateList2.hasMoreElements();) {
	gravAstate v = (gravAstate) stateList2.nextElement();
	System.out.println(v+" "+v.pos+" "+v.velocity);
    }*/
  }
  
  /**
  * Erweiterung von Astate um Anziehung etc.
  */

   private class gravAstate {

    Position pos;
    Position velocity;		
    float mass;		
    float radius;

    Astate state;

    final float massg = 0;

    public gravAstate(Astate state) {
      this.state=state;
    }

    /**
    * berechnet die Position eines Zustandes nach seiner Geschwindigkeit neu
    */

    public void update() {
      pos.x += velocity.x;
      if (pos.x+radius > 1) {
  	velocity.x = -Math.abs(velocity.x);	
      }
      if (pos.x-radius < 0) {
  	velocity.x = Math.abs(velocity.x);
      }

      pos.y += velocity.y;
      if (pos.y+radius > 1) {
  	velocity.y = -Math.abs(velocity.y);	
      }
      if (pos.y-radius < 0) {
  	velocity.y = Math.abs(velocity.y);
      }

    }


    /**
    * Berechnet Beeinflussung eines Zustandes durch den Zustand b
    */

    public boolean interact(gravAstate b) {

      float p = b.pos.x - pos.x; // x abstand
      float q = b.pos.y - pos.y; // y abstand

      float h2 = p*p + q*q;
      float h = (float)Math.sqrt(h2);

      if (h < radius+b.radius) {			// HIT
  	if (h > 1e-10) {
  	  //  Compute the elastic collision of two balls.
  	  //  The math involved here is not for the faint of heart!

  	  float v1,v2,r1,r2,s,t,v;
  	  p /= h;  q /= h;		// normalized impact direction
  	  v1 = velocity.x*p + velocity.y*q;
  	  v2 = b.velocity.x*p + b.velocity.y*q;		// impact velocity
  	  r1 = velocity.x*q - velocity.y*p;
  	  r2 = b.velocity.x*q - b.velocity.y*p;		// remainder velocity
  	  if (v1<v2) return false;
  	  s = mass + b.mass;			// total mass
  	  if (s==0) return false;

  	  t = (v1*mass + v2*b.mass)/s;
  	  v = t + (v2 - v1)*b.mass/s;
  	  velocity.x = v*p + r1*q;
  	  velocity.y = v*q - r1*p;
  	  v = t + (v1 - v2)*mass/s;
  	  b.velocity.x = v*p + r2*q;
  	  b.velocity.y = v*q - r2*p;
  	}
      }
      if ( false ) {	 // gravity is enabled
        float dv;
        dv = massg*b.mass/h2/h;	
        velocity.x += dv*p;
        velocity.y += dv*q;
        dv = massg*mass/h2/h;
        b.velocity.x -= dv*p;
        b.velocity.y -= dv*q;
      }
      return false;
    }

  }



  public static void main(String argv[]) {
    Example e=new Example();
    Process p=e.getExample1().procs.head;
    System.out.println(p);
    new PositionGrav().positioniere(p);
    System.out.println(p);
  }

}
