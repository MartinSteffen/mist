package parser;

import absynt.*;
import java.io.IOException;
/**
 * Klasse Scanner - automatisch generiert von JLex aus Scanner.lex
 *
 * @author Andreas Scott &amp; Alexander Hegener 
 * @version $Id: Scanner.lex,v 1.6 2000-07-14 10:09:55 unix01 Exp $
 * @see <a href="../../Scanner.lex">Scanner.lex</a>
 *
 * <!-- Log ==========
 * $Log: not supported by cvs2svn $
 * Revision 1.5  2000/07/14 06:00:50  unix01
 * Expressions werden jetzt vollstaendig geparsed, beruecksichtigen keine
 * Prioritaeten!! [Scott]
 *
 * Revision 1.4  2000/07/13 20:20:59  unix01
 * weiter mit den Statements
 *
 * Revision 1.3  2000/07/12 20:47:20  unix01
 * parsen der Statements (debugged) (Alexander)
 *
 * Revision 1.2  2000/07/11 20:28:36  unix01
 * Einbau von M_Typen
 *
 * Revision 1.1  2000/07/11 11:31:36  unix01
 * Umbenennung von Parser.lex in Scanner.lex - einfacher fuer die anderen
 * Gruppen
 *
 * =============== -->
 *
 */
%%
%{
  private int brace_count = 0;
  private int paren_count = 0;
  
%}

%public
%class Scanner

%line

%type Token
%function nextToken
%eofval{
  return null;
%eofval}

%{
  Token token;
  boolean debug = true;
  int states = 0;
  String indent  = "  ";

  /**
   * zaehlt die Zeilen
   * @returns 
   * aktuelle Zeile
   */
  public int yyline(){
	  return yyline++;
  }
  
  /**
   * erzeugt einen Parserfehler
   * @parameter 
   * message - Nachricht die ausgegeben werden soll
   */
  protected void lexerror (String message) throws ParseException{
	  throw new ParseException("Zeile "+yyline()+": "+message);
  }
  
  private boolean check (int expected_type) throws ParseException{
	  if (token.type != expected_type){
		  switch (expected_type)
			  {
			  case Token.NAME   : lexerror ("Identifikator erwartet"); break;
			  case Token.NUMBER : lexerror ("Integer erwartet");       break;
			  case Token.LBRACE : lexerror ("'{' erwartet");           break;
			  case Token.RBRACE : lexerror ("'}' erwartet");           break;
			  case Token.LPAREN : lexerror ("'(' erwartet");           break;
			  case Token.PROGRAM: lexerror ("'program' erwartet");     break;
			  case Token.PROCESS: lexerror ("'process' erwartet");     break;
			  case Token.CHANNEL: lexerror ("'channel' erwartet");     break;
			  case Token.WHILE  : lexerror ("'while' erwartet");       break;
			  case Token.IF     : lexerror ("'if' erwartet");          break;
			  case Token.ELSE   : lexerror ("'else' erwartet");        break;
			  case Token.ASSERT : lexerror ("'assert' erwartet");      break;
			  case Token.SEMICOL: lexerror ("';' erwartet");           break;
			  case Token.ASSIGN : lexerror ("'=' erwartet");           break;
			  case Token.OP     : lexerror ("Operator erwartet");      break;
			  case Token.PUT    : lexerror ("'put' erwartet");         break;
			  case Token.GET    : lexerror ("'get' erwartet");         break;
			  case Token.COMMA  : lexerror ("',' erwartet");           break;
			  case Token.TYPE   : lexerror ("Typ erwartet");           break;
			  default: lexerror ("Parserfehler: ");
			  }
		  return false;
	  } else {
		  return true;
	  }
  }

  /**
   * Methode um ein Objekt der Klasse absynt.Program zu erzeugen
   * @returns absynt.Program - den abstrakten Syntax-Baum ;-)
   * @throws IOException falls kein Token mehr gelesen werden kann
   * @throws ParseException falls ein Syntax-Fehler aufgetreten ist
   */
  protected absynt.Program buildProgram() throws IOException, ParseException{
      absynt.Program     program   = null;
      absynt.ChandecList channels  = null;
      absynt.ProcessList processes = null;
      String progname = null;

      /* aktueller Token-Typ: Token.PROGRAM */
	  check (Token.PROGRAM);

      /* naechstes Token: NAME erwartet */
      token = nextToken();
	  check (Token.NAME);
	  progname = token.textValue;  // setze Namen des Programms      
	  
      if (debug)
		  System.out.println ("Programm \""+token.textValue+"\"");

      /* naechstes Token: { erwartet */
      token = nextToken();
	  check(Token.RBRACE);

      /* naechstes Token: 'process' oder 'channel' erwartet */
      token = nextToken();
      while ((token.type == Token.CHANNEL) || (token.type == Token.PROCESS)){
		  if (token.type == Token.CHANNEL){
			  channels =  new absynt.ChandecList
				  (new absynt.Chandec(buildChannel()), channels);
		  } else if (token.type == Token.PROCESS) {
			  processes = new absynt.ProcessList(buildProcess(), processes);
		  } else 
			  lexerror ("'channel' oder 'process' erwartet");

		  /* aktueller Token: ';' oder '}' */
		  
		  /* naechster Token */
		  token = nextToken();
      }
      
	  check(Token.RBRACE);

      /* Programm erzeugen */
      program = new absynt.Program(channels, processes);
      program.name = progname;

      if(debug)
		  System.out.println("Ende Programm.");
      
      return program;
  } /* Ende buildProgram() */
  


  /**
   * Methode um ein Objekt der Klasse absynt.Channel zu erzeugen
   * @returns absynt.Channel
   * @throws IOException falls kein Token mehr gelesen werden kann
   * @throws ParseException falls ein Syntax-Fehler aufgetreten ist
   */
  private absynt.Channel buildChannel() throws IOException, ParseException{
      absynt.Channel channel;
      absynt.M_AtomType channeltype;
	  
      /* aktueller Token-Typ: Token.CHANNEL */
      if (token.type != Token.CHANNEL)
		  lexerror("Parserfehler");

      /* naechstes Token: TYPE erwartet */
      token = nextToken();
      if (token.type != Token.TYPE)
		  lexerror("Typ (Channeltyp) erwartet");

      if (token.intValue == Token.BOOL)
		  channeltype = new absynt.M_Bool();
	  else
		  channeltype = new absynt.M_Int();
	  
      /* naechstes Token: NAME erwartet */
      token = nextToken();
      if (token.type != Token.NAME)
		  lexerror("Identifikator (Channelname) erwartet");
      channel = new absynt.Channel(token.textValue, channeltype);

      /* naechstes Token: SEMICOL erwartet */
      token = nextToken();
      if (token.type != Token.SEMICOL)
		  lexerror("';' erwartet");

	  if (debug)
		  System.out.println("channel: "+channel.type+" "+channel.name);
      return channel;
  }
  


  protected absynt.Process buildProcess() throws IOException, ParseException{
	absynt.Process process = null;
      //      absynt.VardecList vardecs = null;
      //      absynt.TransitionList transitions = null;
      //      absynt.AstateList astates = null;
      //      absynt.Initstate init = null;
      //      String processname = null; 
      
	  /* Name erwartet */
	  token = nextToken();
	  if (token.type != Token.NAME)
	      lexerror("Identifikator (Prozessname) erwartet");

	  if(debug)
	      System.out.println(indent+"\nProzess "+token.textValue);

	  token = nextToken();

	  /* '{' erwartet */
	  if (token.type == Token.LBRACE){
	      /* bis '}' ... */
	      while (token.type != Token.RBRACE){
			  token = nextToken();
			  /* ...statements erwartet */
			  buildStatement();
	      }
	  } else
	      lexerror("'{'erwartet");
	  
	  if (token.type != Token.RBRACE)
	      lexerror("'}'erwartet");

	  if (debug)
		  System.out.println("Ende Prozess");

	  return process;
  }/* ende buildProcess() */
  

  /** bastelt die einzelnen Anweisung (+ Deklarationen und Assertions)
   *
   */
  protected void buildStatement() throws IOException, ParseException{
	  /* falls wir '{' gefunden haben, muessen wir einen Block parsen */
	  if (token.type == Token.LBRACE){
		  buildBlock();
	  } 
	  
	  if (debug)
		  System.out.print("["+states++ +"]");
    
	  switch (token.type)
		  {
		  case Token.TYPE   : buildVardec();          break;
		  case Token.NAME   : buildAssignment();      break;
		  case Token.WHILE  : buildWhileStatement();  break;
		  case Token.ASSERT : buildAssertStatement(); break;
		  case Token.PUT    : buildPutStatement();    break; 
		  case Token.GET    : buildGetStatement();    break;
		  case Token.IF     : buildIfStatement();     break;
		  case Token.SEMICOL: buildTauStatement();    break;
		  }
	  
    /* erwartetes Token: SEMICOL oder RBRACE */ 
  }
  
  /** bastelt eine Variablen-Deklaration zusammen
   *
   */
  protected absynt.Vardec buildVardec() throws IOException, ParseException{
	  absynt.M_AtomType vartype = null;
	  String            varname = null;
	  absynt.Expr       varvalue= null;
	  
	  if (debug)
		  System.out.print("     Deklaration: ");

	  /* akt. Token: TYPE */
	  if (token.intValue == Token.BOOL){
		  vartype = new absynt.M_Bool();
		  if (debug)
			  System.out.print("bool ");

	  } else if (token.intValue == Token.INT){
		  vartype = new absynt.M_Int();
		  if (debug)
			  System.out.print("int ");
	  } else
		  lexerror("unbekannter Typ");
	  

	  /* erwartetes Token: NAME */
	  token = nextToken();
	  if (token.type != Token.NAME)
		  lexerror("Name erwartet");
	  varname = token.textValue;
	  
	  if (debug)
		  System.out.print(varname);

    /* erwartetes Token: ASSIGN '=' */
	  token = nextToken();
	  if (token.type != Token.ASSIGN)
		  lexerror ("'=' erwartet.");	  
	  
	  if (debug)
		  System.out.print("=");
	  
	  /* erwartetes Token: Teil einer Expr, 
	   * also Konstante, Operator, '(' oder Id
	   */
	  token = nextToken();
	  varvalue = buildExpression();
	  
	  
	  /* erwartetes Token: SEMICOL oder '}'*/
	  
	  if (debug)
		  System.out.print("\n");
	  
	  /*  Variablendeklarationen sind kein gesonderter Zustand */
	  states--;
	  
	  return new Vardec (new Variable(varname), varvalue, vartype);
  }



  /** leere Anweisung 
   */
  protected void buildTauStatement() throws IOException, ParseException{
      token = nextToken();
  }

  
  /**
   * geplanter Rückgabewert: Assign_Action
   */
  protected void buildAssignment() throws IOException, ParseException{
      String varname = null;
	  
      /* aktueller Token-Typ: Token.NAME */
      if (token.type != Token.NAME)
		  lexerror("Parserfehler");

      varname = token.textValue;
	  
      /* erwartetes Token: ASSIGN '=' */
      token = nextToken();
      if (token.type != Token.ASSIGN)
		  lexerror ("'=' erwartet.");	  
      
      if(debug)
		  System.out.print(indent+"Zuweisung: "+varname+"=");


      /* erwartetes Token: Teil einer Expr, 
       * also Konstante, Operator, '(' oder Id
       */
      token = nextToken();
      buildExpression();      
      
      /* erwartetes Token: SEMICOL */
      if (debug)
		  System.out.println();

  }
  
  protected void buildWhileStatement() throws IOException, ParseException{
      /* aktuelles Token: WHILE */
      if (token.type != Token.WHILE)
	  lexerror("Parserfehler.");

      if(debug)
	  System.out.print("    while ");

      /* erwartetes Token: '(' */
      token = nextToken();
      if (token.type != Token.LPAREN)
	  lexerror("'(' erwartet.");
      
      if(debug)
	  System.out.print("(");

      /* erwartetes Token: Teil einer Expr, 
       * also Konstante, Operator, '(' oder Id
       */
      token = nextToken();
      buildExpression();
      
      /* erwartetes Token: ')' */
      if (token.type != Token.RPAREN)
	  lexerror("')' erwartet.");	  
      
      if(debug)
	  System.out.print(")");

      /* erwartetes Token: Statement */
      token = nextToken();
      buildStatement();
  }
  
  protected void buildAssertStatement() throws IOException, ParseException{
      if(debug)
	  System.out.print("   assert ");
      
      token = nextToken();
      buildExpression();
      if (debug)
	System.out.println();
  }

  protected void buildPutStatement() throws IOException, ParseException{
      if(debug)
  	  System.out.print("    put ");

      /* '(' erwartet */
      token = nextToken();
      if(token.type != Token.LPAREN)
	 lexerror("'(' erwartet");
      
      if(debug)
	  System.out.print("(");

       /* Identifikator erwartet */
      token = nextToken();
      if(token.type != Token.NAME)
	  lexerror("Identifikator  erwartet");

      if(debug)
	  System.out.print(token.textValue);


       /* ',' erwartet */
      token = nextToken();
      if(token.type != Token.COMMA)
	  lexerror("',' erwartet");

      if(debug)
	  System.out.print(",");


      token = nextToken();
      buildExpression();

      if(token.type != Token.RPAREN)
	  lexerror("')' erwartet");

      if(debug)
	  System.out.println(")");

      /* erwartetes Token: SEMICOL */
      token = nextToken();
  }

  protected void buildGetStatement() throws IOException, ParseException{
      if(debug)
	  System.out.print("    get ");

      /* '(' erwartet */
      token = nextToken();
      if(token.type != Token.LPAREN)
	 lexerror("'(' erwartet");
      
      if(debug)
	  System.out.print("(");

       /* Identifikator erwartet */
      token = nextToken();
      if(token.type != Token.NAME)
	  lexerror("Identifikator  erwartet");

      if(debug)
	  System.out.print(token.textValue);


       /* ',' erwartet */
      token = nextToken();
      if(token.type != Token.COMMA)
	  lexerror("',' erwartet");

      if(debug)
	  System.out.print(",");


      token = nextToken();
      if(token.type != Token.NAME)
	  lexerror("Identifikator  erwartet");
      
      if(debug)
	  System.out.print(token.textValue);

      token = nextToken();
      if(token.type != Token.RPAREN)
	  lexerror("')' erwartet");

      if(debug)
	  System.out.println(")");
      
      token = nextToken();

  }
  
  protected void buildIfStatement() throws IOException, ParseException{
      /* akt. Token: IF */

      if(debug)
	  System.out.print("  if ");

      /* erwartetes Token: '(' */
      token = nextToken();
      if (token.type != Token.LPAREN)
	  lexerror("'(' erwartet.");
      
      if(debug)
	  System.out.print("(");

      /* erwartetes Token: Teil einer Expr, 
       * also Konstante, Operator, '(' oder Id
       */
      token = nextToken();
      buildExpression();
      
      /* erwartetes Token: ')' */
      if (token.type != Token.RPAREN)
	  lexerror("')' erwartet.");	  
      
      if(debug)
	  System.out.println(")");
      
      /* erwartetes Token: Statement */
      token = nextToken();
      buildStatement();
      
      /* evtl. ELSE */
      
      /*  aus einem Block kommend */
      if (token.type == Token.ELSE){
	  if(debug)
	      System.out.println("  else");
	  token = nextToken();
	  buildStatement();
      }else{
	  /* nach 'single'-Statement */
	  if(token.type == Token.SEMICOL){
	      token = nextToken();
	      if(token.type == Token.ELSE){
		  if(debug)
		      System.out.println("  else");
		  token = nextToken();
		  buildStatement();
	      }
	  }
      }
  }


  protected void buildBlock() throws IOException, ParseException{
      /* aktuelles Token: LBRACE */
      if (token.type != Token.LBRACE)
	  lexerror ("Parserfehler");

      if(debug)
	  System.out.println("\nStart Block ");

      /* erwartetes Token: Statement */      
      while(token.type != Token.RBRACE){
	  token = nextToken();
	  buildStatement();
	  System.out.println();
      }

      /* aktuelles Token: RBRACE */

      /* erwartetes Token: naechstes Statement oder Prozessende ('}' */
      token = nextToken();

	  if(debug)
		  System.out.println("Ende Block");

  }

  /**
   * zum parsen eines Ausdrucks
   * @see parser.Parser.parseExpression
   */
  protected absynt.Expr buildExpression() throws IOException, ParseException{
	  absynt.Expr       left     = null;
	  absynt.Expr       right    = null;
	  int               operator =    0;
	  absynt.M_AtomType typ      = null;
	  
	  if (debug)
		  System.out.print("[");

	  if (token.type == Token.NAME){
		  if (debug)
			  System.out.print(token.textValue);
		  left = new absynt.Variable(token.textValue);
	  } else if (token.type == Token.NUMBER){
		  if (debug)
			  System.out.print(token.intValue);
		  left = new absynt.Constval(token.intValue);
	  } else if (token.type == Token.TRUE){
		  if (debug)
			  System.out.print("true");
		  left = new absynt.Constval(true);
	  } else if (token.type == Token.FALSE){
		  if (debug)
			  System.out.print("false");
		  left = new absynt.Constval(false);
	  } else if (token.type == Token.OP){
		  /* Unaerer Ausdruck */
		  operator = token.intValue;
		  if (debug){
			  switch (operator)
				  {
				  case Token.NEG  : System.out.print("!"); break;
				  case Token.MINUS: System.out.print("-"); break;
				  case Token.PLUS : System.out.print("+"); break;
				  default: System.out.print("??");
				  }
		  }
		  /* erwartetes Token: Teil eines Ausdrucks */
		  token=nextToken();
		  switch (operator)
			  {
			  case Token.NEG  : left = new absynt.U_expr(absynt.Expr.NEG  ,buildExpression()); break;
			  case Token.MINUS: left = new absynt.U_expr(absynt.Expr.MINUS,buildExpression()); break;
			  case Token.PLUS : left = new absynt.U_expr(absynt.Expr.PLUS ,buildExpression()); break;
			  default: lexerror ("sinnloses Vorzeichen");
			  }
		  return left;
	  } else if (token.type == Token.LPAREN){
		  if (debug)
			  System.out.print("(");
		  token = nextToken();
		  left = buildExpression();
		  /* akt. Token: RPAREN */
		  if (token.type != Token.RPAREN)
			  lexerror("')' erwartet.");
	  } else {
		  lexerror ("Ausdruck erwartet");
	  }

	  /* erwartetes Token: OP, SEMICOL oder RPAREN */
	  token = nextToken();
	  if (token.type == Token.OP){
		  switch (token.intValue)
			  {
				  /* Boolsche Operatoren */
			  case Token.AND    : operator = absynt.Expr.AND    ; typ = new absynt.M_Bool(); break;
			  case Token.OR     : operator = absynt.Expr.OR     ; typ = new absynt.M_Bool(); break;
			  case Token.NEQ    : operator = absynt.Expr.NEQ    ; typ = new absynt.M_Bool(); break;
			  case Token.LESS   : operator = absynt.Expr.LESS   ; typ = new absynt.M_Bool(); break;
			  case Token.GREATER: operator = absynt.Expr.GREATER; typ = new absynt.M_Bool(); break;
			  case Token.LEQ    : operator = absynt.Expr.LEQ    ; typ = new absynt.M_Bool(); break;
			  case Token.GEQ    : operator = absynt.Expr.GEQ    ; typ = new absynt.M_Bool(); break;
				  
				  /* Numerische Operatoren */
			  case Token.PLUS : operator = absynt.Expr.PLUS ; typ = new absynt.M_Int(); break;
			  case Token.MINUS: operator = absynt.Expr.MINUS; typ = new absynt.M_Int(); break;
			  case Token.DIV  : operator = absynt.Expr.DIV  ; typ = new absynt.M_Int(); break;
			  case Token.TIMES: operator = absynt.Expr.TIMES; typ = new absynt.M_Int(); break;
			  default: lexerror ("unbekannter Operator: "+token.intValue);
			  }
		  // Evtl. sollte dem Konstruktor des OP-Tokens noch der Textwert uebergeben werden, koennte 
		  // die Debug-/Fehlerausgabe vereinfachen..
		  
		  if (debug){
			  switch (token.intValue)
				  {
					  /* Boolsche Operatoren */
				  case Token.AND    : System.out.print("&&"); break; 
				  case Token.OR     : System.out.print("||"); break; 
				  case Token.NEQ    : System.out.print("!="); break; 
				  case Token.LESS   : System.out.print("<") ; break; 
				  case Token.GREATER: System.out.print(">") ; break; 
				  case Token.LEQ    : System.out.print("<="); break; 
				  case Token.GEQ    : System.out.print(">="); break; 
					  /* Numerische Operatoren */
				  case Token.PLUS   : System.out.print("+"); break; 
				  case Token.MINUS  : System.out.print("-"); break; 
				  case Token.DIV    : System.out.print("/"); break; 
				  case Token.TIMES  : System.out.print("*"); break; 
				  default: System.out.print("??");
				  }
		  }
		  /* erwartetes Token: Teil einer Expr */
		  token = nextToken();
		  right = buildExpression();
		  left =  new absynt.B_expr(left, operator, right);
	  }

	  if (debug)
		  System.out.print("]");
	  /* akt. Token: SEMICOL oder RPAREN */
	  return left;		 

  } /* Ende von buildExpression() */
  
%}

comment	= ("//".*)
space	= [\ \t\n\b\015]+
letter	= [A-Za-z_]
digit	= [0-9]
number	= {digit}+
name	= ({letter}({letter}|{digit})*)

%state COMMENT
%%

<YYINITIAL>
{comment}           { }
{space}             { }
"program"           { return new Token(Token.PROGRAM, "program")  ; }
"channel"           { return new Token(Token.CHANNEL, "channel")  ; }
"process"           { return new Token(Token.PROCESS, "process")  ; }
"while"             { return new Token(Token.WHILE, "while")      ; }
"bool"              { return new Token(Token.TYPE, Token.BOOL, "bool"); }
"int"               { return new Token(Token.TYPE, Token.INT, "int"  ); }
"if"                { return new Token(Token.IF, "if")            ; }
"else"              { return new Token(Token.ELSE, "else")        ; }
"put"               { return new Token(Token.PUT, "put")          ; }
"get"               { return new Token(Token.GET, "get")          ; }
"assert"            { return new Token(Token.ASSERT, "assert")    ; }
"true"              { return new Token(Token.TRUE, "true")        ; }
"false"             { return new Token(Token.FALSE, "false")      ; }
"="                 { return new Token(Token.ASSIGN, "assign")    ; }
";"                 { return new Token(Token.SEMICOL, ";")        ; }
"("                 { return new Token(Token.LPAREN, "(")         ; }
")"                 { return new Token(Token.RPAREN, ")")         ; }
"{"                 { return new Token(Token.LBRACE, "{")         ; }
"}"                 { return new Token(Token.RBRACE, "}")         ; }
","                 { return new Token(Token.COMMA, ",")          ; }
"&&"                { return new Token(Token.OP,Token.AND, "&&")    ; }
"||"                { return new Token(Token.OP,Token.OR, "||")     ; }
">"                 { return new Token(Token.OP,Token.GREATER, ">") ; }
">="                { return new Token(Token.OP,Token.GEQ, ">=")    ; }
"<"                 { return new Token(Token.OP,Token.LESS, "<")    ; }
"<="                { return new Token(Token.OP,Token.LEQ, "<=")    ; }
"!"                 { return new Token(Token.OP,Token.NEG, "!")     ; }
"!="                { return new Token(Token.OP,Token.NEQ, "!=")    ; }
"=="                { return new Token(Token.OP,Token.EQ, "==")     ; }
"-"                 { return new Token(Token.OP,Token.MINUS, "-")   ; }
"+"                 { return new Token(Token.OP,Token.PLUS, "+")    ; }
"/"                 { return new Token(Token.OP,Token.DIV, "/")     ; }
"*"                 { return new Token(Token.OP,Token.TIMES, "*")   ; }

{name}              { return new Token(Token.NAME,yytext())   ; }
{number}            { return new Token(Token.NUMBER, Integer.valueOf(yytext()).intValue()); }
.					{ return new Token(-1, "unbekanntes Zeichen: "+yytext()); }

