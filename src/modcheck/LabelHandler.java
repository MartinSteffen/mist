/**
 * LabelHandler-Klasse zur Verwaltung von Labeltests/-aktionen.
 * @author Frank Neumann, Aneta Kuemper, Eike Schulz
 * @version 1.0
 */

package modcheck;

import absynt.*;

public class LabelHandler {


  // ----------------------------- public Methoden ----------------------------

  /**
   * Pruefe Zulaessigkeit eines Labels.
   * @param Label mit "Expr"
   * @param Modstate mit Ausgangsdaten (inbes. Variablenwerte)
   * @return true, falls Label zulaessig, false sonst.
   */
  public static boolean checkExpr (Label _l, Modstate _m) {

    // Betrachte Expression aus Parameterlabel, sowie Liste mit Variablen aus
    // Modstate-Object. Werte booleschen Ausdruck aus, gib Ergebniswert zurueck

    return extractExpr(_l.guard, _m.vars);

  } // method checkExpr



  /**
   * Fuehre Aktion eines Labels durch.
   * @param  Label mit "Action"
   * @param  Modstate mit Ausgangsdaten
   * @return VardecList mit neuen Variablenwerten
   */
  public static VardecList accomplishAction (Label _l, Modstate _m) throws
  NumberFormatException {

    // Initialisiere Default-Rueckgabewert.
    VardecList new_vL = (VardecList)(_m.vars.clone());

    if (_l.act instanceof Assign_action)
      new_vL = assignAction((Assign_action)(_l.act), _m.vars);
    else

      if(_l.act instanceof Tau_action)
	new_vL = tauAction((Tau_action)(_l.act), _m.vars);

    return new_vL;

  } // method accomplishAction


  // ----------------------------- private Methoden ---------------------------

  //                    >>> Hilfsmethoden fuer "checkExpr" <<<


  // Werte uebergebenen Ausdruck (rekursiv mit evtl. Unterausdruecken) aus.
  // Die ebenfalls uebergebene VardecList enthaelt dabei fuer alle Variablen
  // des Ausdrucks die "aktuellen" Werte.
  //
  // VORAUSSETZUNGEN:
  //  - Uebergabeobjekte wurden vom Checker auf "konkrete" Syntax geprueft.
  //  - VardecList enthaelt genau die Variablen, die im Ausdruck vorkommen.
  //
  // Rueckgabewert: boolean-Wert des ausgewerteten Ausdrucks.

  private static boolean extractExpr (Expr _e, VardecList _vL) {

    // Initialisiere Rueckgabewert (der Initialwert ist eigentlich egal, sofern
    // der Uebergabeausdruck den Anspruechen der konkreten Syntax genuegt).

    boolean ret_b = true;


    // Pruefe, um was fuer ein Expr-Objekt es sich bei _e handelt.

    // _e Variable ?

    if (_e instanceof Variable) {

      // Finde Variableneintrag in uebergebener Vardec-Liste.

      VardecList vL = _vL;
      while (!vL.head.var.name.equals( ((Variable)_e).name ))
	vL = vL.next;

      // Lies nun Variablenwert aus Liste (bzw. aus Expr-Object "Constval").

      ret_b = extractExpr(vL.head.val, _vL);

    } else

      // _e (Boolean-)Konstante ? 

      if (_e instanceof Constval) {

	Boolean bool = (Boolean)(((Constval)_e).val);
	ret_b = bool.booleanValue();

      } else

	// _e unaere Verknuepfung ?

	if (_e instanceof U_expr)

	  // Als unaerer Operator kommt bei (booleschen) Ausdruecken nur die
	  // Negation in Frage, daher entfaellt eine evtl. Fallunterscheidung.

	  ret_b = !extractExpr( ((U_expr)_e).sub_expr, _vL );

	else

	  // _e binaere Verknuepfung ?

	  if (_e instanceof B_expr)

	    // Pruefe, ob es sich bei den Operatoren des Ausdrucks um boolesche
	    // Verknuepfungen (AND, OR) oder Vergleichsoperationen ( "=", "<",
	    // ">", "<=", ">=" ) handelt.

	    // Boolesche Verknuepfungen

	    if ( ((B_expr)_e).op == Expr.AND )
	      ret_b =
		extractExpr( ((B_expr)_e).left_expr, _vL ) &&
		extractExpr( ((B_expr)_e).right_expr, _vL );
	    else

	      if ( ((B_expr)_e).op == Expr.OR )
		ret_b =
		  extractExpr( ((B_expr)_e).left_expr, _vL ) ||
		  extractExpr( ((B_expr)_e).right_expr, _vL );
	      else {

		// Vergleichsoperationen

		// Hier werden Terme miteinander verglichen. Terme werden in
		// der Methode "extractTerm" ausgewertet. Tritt dort ein Fehler
		// in der Auswertung auf, so wird dies durch eine Exception
		// signalisiert. Diese wird hier abgefangen.

		try {

		  if ( ((B_expr)_e).op == Expr.EQ )
		    ret_b =
		      extractTerm( ((B_expr)_e).left_expr, _vL ) ==
		      extractTerm( ((B_expr)_e).right_expr, _vL );
		  else

		    if ( ((B_expr)_e).op == Expr.LESS )
		      ret_b =
			extractTerm( ((B_expr)_e).left_expr, _vL ) <
			extractTerm( ((B_expr)_e).right_expr, _vL );
		    else

		      if ( ((B_expr)_e).op == Expr.GREATER )
			ret_b =
			  extractTerm( ((B_expr)_e).left_expr, _vL ) >
			  extractTerm( ((B_expr)_e).right_expr, _vL );
		      else

			if ( ((B_expr)_e).op == Expr.LEQ )
			  ret_b =
			    extractTerm( ((B_expr)_e).left_expr, _vL ) <=
			    extractTerm( ((B_expr)_e).right_expr, _vL );
			else

			  if ( ((B_expr)_e).op == Expr.GEQ )
			    ret_b =
			      extractTerm( ((B_expr)_e).left_expr, _vL ) >=
			      extractTerm( ((B_expr)_e).right_expr, _vL );

		} catch (NumberFormatException _nfe) {

		  // Fehler in Termauswertung aufgetreten.

		  ret_b = false;

		} // catch

	      } // else

    return ret_b;

  } // method extractExpr



  // Werte uebergebenen Term (rekursiv mit evtl. Untertermen) aus.
  // Die ebenfalls uebergebene VardecList enthaelt dabei fuer alle Variablen
  // des Terms die "aktuellen" Werte.
  // Wirft eine NumberFormatException, falls Division durch 0 stattfindet.
  //
  // VORAUSSETZUNG:
  //  - Uebergabeobjekte wurden vom Checker auf "konkrete" Syntax geprueft.
  //  - VardecList enthaelt genau die Variablen, die im Term vorkommen.
  //
  // Rueckgabewert: int-Wert des ausgewerteten Terms.

  private static int extractTerm (Expr _e, VardecList _vL) throws
  NumberFormatException {

    // Initialisiere Rueckgabewert (der Initialwert ist eigentlich egal, sofern
    // der Uebergabeterm den Anspruechen der konkreten Syntax genuegt).

    int ret_i = 0;


    // Pruefe, um was fuer ein Expr-Objekt es sich bei _e handelt.

    // _e Variable ?

    if (_e instanceof Variable) {

      // Finde Variableneintrag in uebergebener Vardec-Liste.

      VardecList vL = _vL;
      while (!vL.head.var.name.equals( ((Variable)_e).name ))
	vL = vL.next;

      // Lies nun Variablenwert aus Liste (bzw. aus Expr-Object "Constval").

      ret_i = extractTerm(vL.head.val, _vL);

    } else

      // _e (Integer-)Konstante ? 

      if (_e instanceof Constval) {

	Integer integer = (Integer)(((Constval)_e).val);
	ret_i = integer.intValue();

      } else

	// _e unaere Verknuepfung ?

	if (_e instanceof U_expr)

	  // Als unaere Operatoren kommen bei Termen nur "+" und "-" in Frage.

	  if ( ((U_expr)_e).op == Expr.PLUS )
	    ret_i = extractTerm( ((U_expr)_e).sub_expr, _vL );
	  else

	    if ( ((U_expr)_e).op == Expr.MINUS )
	      ret_i = -(extractTerm( ((U_expr)_e).sub_expr, _vL ));

	    else

	      // _e binaere Verknuepfung ?

	      if (_e instanceof B_expr)

		// Pruefe, um was fuer eine arithmetische Verknuepfung ( "+",
		// "-", "*", "/" ) es sich handelt.

		if ( ((B_expr)_e).op == Expr.PLUS )
		  ret_i =
		    extractTerm( ((B_expr)_e).left_expr, _vL ) +
		    extractTerm( ((B_expr)_e).right_expr, _vL );
		else

		  if ( ((B_expr)_e).op == Expr.MINUS )
		    ret_i =
		      extractTerm( ((B_expr)_e).left_expr, _vL ) -
		      extractTerm( ((B_expr)_e).right_expr, _vL );
		  else

		    if ( ((B_expr)_e).op == Expr.TIMES )
		      ret_i =
			extractTerm( ((B_expr)_e).left_expr, _vL ) *
			extractTerm( ((B_expr)_e).right_expr, _vL );
		    else

		      if ( ((B_expr)_e).op == Expr.DIV )

			// Teilerausdruck != 0 ? Falls nicht, wirf Exception.

			if ( extractTerm( ((B_expr)_e).right_expr, _vL ) != 0 )
			  ret_i =
			    extractTerm( ((B_expr)_e).left_expr, _vL ) /
			    extractTerm( ((B_expr)_e).right_expr, _vL );
			else
			  throw new NumberFormatException("division by zero");

    return ret_i;

  } // method extractTerm


  //                >>> Hilfsmethoden fuer "accomplishAction" <<<


  // Fuehre Wertzuordnung durch.
  // Parameter: Durchzufuehrende Aktion, VardecList mit Ausgangsdaten.
  // Gibt VardecList mit neuen Variablenwerten zurueck.
  //
  // VORAUSSETZUNG:
  //  - Uebergabeobjekte wurden vom Checker auf "konkrete" Syntax geprueft.
  //  - VardecList enthaelt genau die Variablen, die im Assign_action-Objekt
  //    vorkommen.
  //  - Vardec-Objekte der VardecList enthalten zugewiesene Conval-Objekte, die
  //    ungleich null sind.

  private
  static VardecList assignAction (Assign_action _aa, VardecList _vL) throws
  NumberFormatException {

    // Initialisiere Rueckgabewert.
    VardecList new_vL = (VardecList)(_vL.clone());


    // Finde Variableneintrag in uebergebener Vardec-Liste.

    VardecList vL = new_vL;
    //    System.out.println("aktuelle Vardec-Liste:");//--- Testzeile
    //    vL.print();//--- Testzeile
    //    System.out.println ("Vardec gesucht: " + _aa.var.name);//--- Testzeile
    while (!vL.head.var.name.equals( _aa.var.name ))
      vL = vL.next;

    // Lies nun Constval-Objekt zur Variable aus Liste.

    Constval cv = (Constval)(vL.head.val);
    if (cv == null) System.out.println("null !");

    // Pruefe, ob es sich bei cv um eine Boolean- oder Integer-Konstante
    // handelt und werte entsprechend neuen Ausdruck/Term aus.

    cv.val = (cv.val instanceof Boolean) ?
      new Constval( extractExpr(_aa.val, _vL) ):
      new Constval( extractTerm(_aa.val, _vL) );

    return new_vL;

  } // method assignAction



  // Fuehre Tau-Aktion durch.
  // Parameter: Durchzufuehrende Aktion, VardecList mit Ausgangsdaten.
  // Gibt VardecList mit neuen Variablenwerten zurueck.

  private static VardecList tauAction (Tau_action _aa, VardecList _vL) {

    // Initialisiere Rueckgabewert.
    VardecList new_vL = (VardecList)(_vL.clone());

    return new_vL;

  } // method tauAction


} // class LabelHandler
