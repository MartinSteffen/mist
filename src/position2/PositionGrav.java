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
import java.util.Random;

public class PositionGrav implements Positionierung {

  public static final int maxGenerations = 100;
  public static final int maxFitness = 1;

  /**
   * findet kluge Positionen für Zustände im übergebenen Prozess.
   *
   * @param process Prozess, der bearbeitet wird
   */
  public void positioniere(Process process) {

    Population population=new Population(process);
    int generation = 0;

    while ( population.getBestIndividual().getFitness() > maxFitness && generation<maxGenerations ) {
      generation++;
      population.shrink();
      population.expand();
      System.out.println(population.getBestIndividual());
    }

    population.getBestIndividual().apply();

    System.out.print("Nach "+generation+" Generation habe ich Konfiguration mit "+population.getBestIndividual().getFitness()+" Fitness gefunden.");
    System.out.println(population.getBestIndividual());
  }



  // BEGINN DER INNEREN KLASSE INDIVASTATE

  /**
  * Meine Eigene Astate ähnliche Klasse.
  */

  private class IndivAstate {

    /**
     * Lage des Zustands
     */
    public Position pos;

    /**
     * Anzahl der Verbindungen
     */
    int mass;

    /**
     * Der "echte Zustand", für den dies ein Platzhalter ist
     */
    Astate state;

    public IndivAstate(Astate astate, int mass, Position pos) {
      this.pos=new Position(pos.x,pos.y);
      this.mass=mass;
      this.state=astate;
    }

    public IndivAstate(Astate astate) {
      this.pos=new Position(0f,0f);
      this.mass=0;
      this.state=astate;
    }

    public IndivAstate(IndivAstate indivAstate) {
       this(indivAstate.state, indivAstate.mass, indivAstate.pos);
    }

    /**
     * gibt eine Kopie des Zustands zurück
     */
    public IndivAstate copy() {
      return(new IndivAstate(this));
    }

    /**
     * bewegt den Zustand in der Horizontalen
     */
    public void moveX(float offset) {
      if ( pos.x+offset>=0 && pos.x+offset<=1 ) { pos.x+=offset; }
    }

    /**
     * bewegt den Zustand in der Vertikalen
     */
    public void moveY(float offset) {
      if ( pos.y+offset>=0 && pos.y+offset<=1 ) { pos.y+=offset; }
    }

    public String toString() {
      return("("+pos.x+"/"+pos.y+")");
    }

    /**
     * schreibt die Positionen in den Prozess
     */

    public void apply() {
        state.pos=pos;
    }

  }

  // ENDE DER INNEREN KLASSE INDIVASTATE





  // BEGINN DER INNEREN KLASSE POPULATION

  /**
   * Verwaltet eine Menge von Individuen
   */

  class Population extends java.util.Vector {

    public final int maxPopulation = 80;
    public final int minPopulation = 15;

    Random rnd;

    /**
     * wie elementAt, aber für Individual angepasst.
     *
     * @param index Position im Vektor
     * @return das jeweilige Individuum
     */
    public Individual individualAt(int index) {
      return ( (Individual)elementAt(index) );
    }

    public Population(Process process) {
      rnd=new Random();
      addElement(new Individual(process));
      expand();
    }

    /**
     * füllt die Population wieder auf maxPopulation Individuen auf
     */
    public void expand() {
      int oldMaxIndex = size()-1;
      while ( size() < maxPopulation/3 ) {
        Individual n;
        n=individualAt(0).copy();
        for ( int x=0; x<n.size(); x++ ) {
          n.indivAstateAt(x).pos.x=rnd.nextFloat();
          n.indivAstateAt(x).pos.y=rnd.nextFloat();
        }
        addElement(n);
      }
      while ( size() < maxPopulation ) {
        Individual n;
        if (oldMaxIndex>0) {
          n=individualAt(rnd.nextInt(oldMaxIndex)).copy();
        } else {
          n=individualAt(0).copy();
        }
        n.mutate();
        addElement(n);
      }
    }

    /**
     * töte die schlechten, damit wir minPopulation Individuen haben
     */
    public void shrink() {
      while ( size() > minPopulation ) {
        int worst=0; float worstFitness=0;
        for ( int i=0; i<size(); i++ ) {
          float f=individualAt(i).getFitness();
          if ( f>worstFitness ) { worstFitness=f; worst=i; }
        }
        removeElementAt(worst);
      }
    }


    /**
     * sucht das beste Individuum (mit dem niedrigsten Fitness-Wert).
     *
     * @return das beste Individuum
     */
    public Individual getBestIndividual() {
      int best=0; float bestFitness=Float.MAX_VALUE;
      for ( int i=0; i<size(); i++ ) {
        float f=individualAt(i).getFitness();
        if ( f<bestFitness ) { bestFitness=f; best=i; }
      }
      return ( individualAt(best) );
    }

  }

  // ENDE DER INNEREN KLASSE INDIVIDUAL




  // BEGINN DER INNEREN KLASSE INDIVIDUAL

  /**
   * Eine Klasse, deren Instanz jeweils ein Individuum in dem Algorithmus ist,
   * also jeweils eine Konfiguration von Positionen für Astates gespeichert in
   * einer Anzahl IndivAstates.
   */

  private class Individual extends java.util.Vector {

    /**
     * Prozess, der hier positioniert wird.
     */
    private Process process;

    /**
     * Schrittweite, um die Zustände beim Evolvieren bewegt werden sollen.
     */
    private float step;

    /**
     * Zufallszahlengenerator
     */
    private Random rnd;

    /**
     * Cache für die Fitness, damit sie nicht immer neu berechet werden muß, -1 bedeutet, daß der Inhalt ungültig ist.
     */
    private float fitnessCache;

    int crossings=0;
    /**
     * wie elementAt, aber für indivAstate angepasst.
     *
     * @param index Position im Vektor
     * @return den jeweiligen Zustandswrapper
     */
    public IndivAstate indivAstateAt(int index) {
      return ( (IndivAstate)elementAt(index) );
    }

    /**
     * erzeugt eine Kopie des Individuums, erzeugt auch
     * neue Instanzen der indivAstates.
     *
     * @return neue Instanz des Individuums
     */
    public Individual copy() {
       Individual n=new Individual();
       for (int i=0; i<size(); i++) {
         n.addElement(indivAstateAt(i).copy());
       }
       n.process=process;
       n.step=step;
       return(n);
    }

    /**
     * verändert einige Parameter an dem Individuum, verschiebt Zustände
     */
    public void mutate() {
      for (int i=0; i<size(); i++) {
        indivAstateAt(i).moveX((float)(rnd.nextGaussian()*step));
        indivAstateAt(i).moveY((float)(rnd.nextGaussian()*step));
      }

/*      int count=rnd.nextInt((int)(size()/5+2.5))-1;
       for (int i=0; i<count; i++) {
        int a=rnd.nextInt(size()-1);
        int b=rnd.nextInt(size()-1);
        Position swap=indivAstateAt(a).pos;
        indivAstateAt(a).pos=indivAstateAt(b).pos;
        indivAstateAt(b).pos=swap;
      }
  */    // Cache leeren
      fitnessCache=-1f;
    }




    boolean SAME_SIGNS ( float a, float b ) {
     return ( ( java.lang.Math.abs(a)>=0 && java.lang.Math.abs(b)>=0 ) || ( java.lang.Math.abs(a)<0 && java.lang.Math.abs(b)<0 ) );
    }


  public boolean intersect(Position line1EndPosition1, Position line1EndPosition2, Position line2EndPosition1, Position line2EndPosition2 ) {
    Position p=intersectPos(  line1EndPosition1, line1EndPosition2, line2EndPosition1, line2EndPosition2 );

    return (
      p != null &&
      !( p.equalsn(line1EndPosition1) || p.equalsn(line1EndPosition2) || p.equalsn(line2EndPosition2) || p.equalsn(line2EndPosition1) )
    );
  }

  /**
   Use this method to tell if two lines (with defined endpoints) intersect.
   Specifically, if the two lines intersect, the method will return the point
   of intersection.  Else it will return null (for no existing intersection point).<P>

   Faster Line Segment Intersection by Franklin Antonio

   * @param line1 is a LineSegment object
   * @param line2 is a LineSegment object
   * @return the point where line1 & line2 intersect, or null if they do not intersect
  */

  public Position intersectPos( Position line1EndPosition1, Position line1EndPosition2, Position line2EndPosition1, Position line2EndPosition2 ){
    /* Start local variables */

    float Ax,Bx,Cx,Ay,By,Cy,d,e,f,num,offset;
    float x1lo,x1hi,y1lo,y1hi;
    float x,y; // intersection components

    /* End local variables */
    Ax = line1EndPosition2.x-line1EndPosition1.x;
    Bx = line2EndPosition1.x-line2EndPosition2.x;

    if ( Ax<0 ) {                /* X bound box test*/
         x1lo=(short)line1EndPosition2.x;
         x1hi=(short)line1EndPosition1.x;
    } else {
        x1hi=(short)line1EndPosition2.x; x1lo=(short)line1EndPosition1.x;
    }

    if ( Bx>0 ) {
        if( x1hi < (short)line2EndPosition2.x || (short)line2EndPosition1.x < x1lo )
            return null;
    } else {
        if ( x1hi < (short)line2EndPosition1.x || (short)line2EndPosition2.x < x1lo )
            return null;
    }

    Ay = line1EndPosition2.y-line1EndPosition1.y;
    By = line2EndPosition1.y-line2EndPosition2.y;

    if ( Ay<0 ) {                /* Y bound box test*/
        y1lo=(short)line1EndPosition2.y;
        y1hi=(short)line1EndPosition1.y;
    } else {
        y1hi=(short)line1EndPosition2.y;
        y1lo=(short)line1EndPosition1.y;
    }
    if ( By>0 ) {
        if ( y1hi < (short)line2EndPosition2.y || (short)line2EndPosition1.y < y1lo )
            return null;
    } else {
        if ( y1hi < (short)line2EndPosition1.y || (short)line2EndPosition2.y < y1lo )
            return null;
    }

    Cx = line1EndPosition1.x-line2EndPosition1.x;
    Cy = line1EndPosition1.y-line2EndPosition1.y;
    d = By*Cx - Bx*Cy;              /* alpha numerator*/
    f = Ay*Bx - Ax*By;              /* both denominator*/
    if ( f>0 ) {                 /* alpha tests*/
        if ( d<0 || d>f )
            return null;
    } else {
        if ( d>0 || d<f )
            return null;
    }

    e = Ax*Cy - Ay*Cx;              /* beta numerator*/
    if ( f>0 ) {                 /* beta tests*/
        if ( e<0 || e>f )
            return null;
    } else {
        if ( e>0 || e<f )
            return null;
    }

    /* compute intersection coordinates */

    if ( f==0 ) // THEN THE LINES ARE COLLINEAR
        return null;


    num = d*Ax;                  /* numerator */
    offset = SAME_SIGNS(num,f) ? f/2 : -f/2;    /* round direction*/
    x = (int)(line1EndPosition1.x + (num+offset) / f);            /* intersection x */

    num = d*Ay;
    offset = SAME_SIGNS(num,f) ? f/2 : -f/2;
    y = (int)(line1EndPosition1.y + (num+offset) / f);            /* intersection y */

    return new Position(x,y);

}


    /**
     * berechtnet die Fitness des Individuums.
     *
     * @return Fitness-Indikator: je größer, desto schlechter
     */
    private float fitness() {
      float fitness=0;
      crossings=0;

      float penaltyLineCrossing=50;

      // Die Transitionen durchgehen
      TransitionList tl = process.steps;
      while ( tl.hasMoreElements() ) {
        // Abstände
        IndivAstate a=findAstate(tl.head.source); Position p1=a.pos;
        IndivAstate b=findAstate(tl.head.target); Position p2=b.pos;
        float d=(float)java.lang.Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
        fitness+=d*d*(a.mass+b.mass)*2;
        tl=(TransitionList)tl.nextElement();
        // Kreuzungen
        TransitionList t2 = tl;
        while ( t2.hasMoreElements() ) {
          if (tl.head.source != tl.head.target ) {
            Position q1=findAstate(tl.head.source).pos;
            Position q2=findAstate(tl.head.target).pos;
            if ( intersect(p1,p2,q1,q2) ) { fitness+=penaltyLineCrossing; crossings++; }
            t2=(TransitionList)t2.nextElement();
          }
        }
        {
          if (tl.head.source != tl.head.target ) {
            Position q1=findAstate(tl.head.source).pos;
            Position q2=findAstate(tl.head.target).pos;
            if ( intersect(p1,p2,q1,q2) ) { fitness+=penaltyLineCrossing;crossings++;}
          }
        }
      }
      {
        // Abstände
        IndivAstate a=findAstate(tl.head.source); Position p1=a.pos;
        IndivAstate b=findAstate(tl.head.target); Position p2=b.pos;
        float d=(float)java.lang.Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
        fitness+=d*d*(a.mass+b.mass)*2;
        // Kreuzungen
        TransitionList t2 = tl;
        while ( t2.hasMoreElements() ) {
          Position q1=findAstate(tl.head.source).pos;
          Position q2=findAstate(tl.head.target).pos;
          if ( intersect(p1,p2,q1,q2) ) { fitness+=penaltyLineCrossing;crossings++; }
          t2=(TransitionList)t2.nextElement();
        }
        {
          Position q1=findAstate(tl.head.source).pos;
          Position q2=findAstate(tl.head.target).pos;
          if ( intersect(p1,p2,q1,q2) ) { fitness+=penaltyLineCrossing; crossings++;}
        }
      }

      // Mindestabstand
      for (int i=0; i<size(); i++) {
        for (int j=0; j<size(); j++) {
          if (i!=j) {
            Position p1=indivAstateAt(i).pos;
            Position p2=indivAstateAt(j).pos;
            float d=(float)java.lang.Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
//            if (d<2f*stepx) { fitness+=3*(2f*stepx-d)*(2f*stepx-d); }
//            d*=1.2f;
            fitness+=(1-d)*(1-d)*(indivAstateAt(i).mass+indivAstateAt(j).mass);
          }
        }
      }


      // Zentrale in die Mitte
      for (int i=0; i<size(); i++) {
        Position p1=indivAstateAt(i).pos;
        float d=(p1.x-0.5f)*(p1.x-0.5f)+(p1.y-0.5f)*(p1.y-0.5f);
        fitness+=indivAstateAt(i).mass*indivAstateAt(i).mass*d;
      }

      return(fitness);
    }

    /**
     * gibt die Fitness des Individuums zurück.
     *
     * @return Fitness-Indikator: je größer, desto schlechter
     */
    float getFitness() {
      if ( fitnessCache==-1 ) { fitnessCache=fitness(); }
      return(fitnessCache);
    };

    /**
     * sucht einen indivState raus
     *
     * @param state Suchergebnis ist Stellvertreter für state
     * @return den dazu passenden indivState
     */
    IndivAstate findAstate(Astate state) {
      int x=0; while ( x<size() && ( indivAstateAt(x)).state!=state ) { x++; }
      if (x<size()) { return( indivAstateAt(x)); } else { return(null); }
    }

    /**
     * Einfacher Constructor
     */
    public Individual () {
      rnd=new Random();
      fitnessCache=-1f;
    }

    /**
     * erzeugt ein Individual nach dem Ebenbild eines Prozesses
     */

    public Individual (Process process) {

      this();
      this.process=process;

      // Alle Zustände kopieren
      AstateList sl = process.states;
      while ( sl.hasMoreElements() ) {
        addElement(new IndivAstate((Astate)sl.head));
        sl=(AstateList)sl.nextElement();
      }
      addElement(new IndivAstate((Astate)sl.head));

      // an ein freie Position packen
//      Verteiler v=new Verteiler(size()*2);
//      stepx=v.getXStep(); stepy=v.getYStep();
      step=(float)java.lang.Math.sqrt(size());
      for ( int x=0; x<size(); x++ ) {
        indivAstateAt(x).pos.x=rnd.nextFloat();
        indivAstateAt(x).pos.y=rnd.nextFloat();
//        indivAstateAt(x).pos=v.getNext();
//        v.getNext();
      }

      // Anzahl der Verbindungen jedes Zustands berechnen
      TransitionList tl = process.steps;
      while ( tl.hasMoreElements() ) {
        findAstate(tl.head.source).mass++;
        findAstate(tl.head.target).mass++;
        tl=(TransitionList)tl.nextElement();
      }
      findAstate(tl.head.source).mass++;
      findAstate(tl.head.target).mass++;

    }

    public String toString() {
      String n="[ Fitness: "+getFitness();
      for (int i=0; i<size(); i++) {
        if ( i>0 ) { n+=", "; }
        n+=indivAstateAt(i).toString();
      }
      n+=" Crossings: "+crossings+"]";
      return(n);
    }

    /**
     * schreibt die Positionen in den Prozess
     */

    public void apply() {
      for (int i=0; i<size(); i++) {
        indivAstateAt(i).apply();
      }
    }

    // BEGINN DER INNERERN INNEREN KLASSE VERTEILER

    /**
     * liefert eine Folge von Positionen, die gleichmäßig auf der Fläche (0,1)/(0,1) verteilt sind.
     */
    public class Verteiler {

      private int maxCount=0;
      private int step=0;

      private float xstep=0;
      private float ystep=0;
      private int colCount, rowCount;

      /**
       * Macht die Instanz fertig.
       *
       * @param anzahl Anzahl der Positionen, die insgesamt verteilt werden sollen
       */
      public Verteiler (int anzahl) {
        maxCount=anzahl;
        float rt=(float)java.lang.Math.sqrt(maxCount);
        if ( (int)rt != rt ) {
          rowCount=(int)(rt); colCount=(int)(rt+1);
        } else {
          rowCount=(int)rt; colCount=(int)rt;
        }
        xstep=1f/colCount;
        ystep=1f/rowCount;
        System.out.println(" "+xstep+" "+ystep+" "+colCount+" "+rowCount+" "+maxCount+" ");
      }

      /**
       * liefert die nächste Position.
       */
      public Position getNext() {
        Position p = new Position(xstep*(step/colCount),ystep*(step%colCount));
        step++;
        System.out.println("P: "+p.x+"."+p.y);
        return(p);
      }

      /**
       * Abstand zweier Positionen zurückgeben.
       *
       * @return Abstand in X-Richtung
       */
      public float getXStep() {
        return( xstep );
      }

      /**
       * Abstand zweier Positionen zurückgeben.
       *
       * @return Abstand in Y-Richtung
       */
      public float getYStep() {
        return( ystep );
      }

    }


    // ENDE DER INNEREN INNEREN KLASSE VERTEILER

  }

  // ENDE DER INNEREN KLASSE INDIVIDUAL


  public static void main(String argv[]) {
    Example e=new Example();
    Process p=e.getExample1().procs.head;
    System.out.println(p);
    new PositionGrav().positioniere(p);
    System.out.println(p);
  }

}
