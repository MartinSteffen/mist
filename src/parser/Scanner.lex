package parser;

import absynt.*;
import java.io.IOException;
/**
 * Klasse Scanner - automatisch generiert von JLex aus Scanner.lex
 *
 * @author Andreas Scott &amp; Alexander Hegener 
 * @version $Id: Scanner.lex,v 1.3 2000-07-12 20:47:20 unix01 Exp $
 * @see <a href="../../Scanner.lex">Scanner.lex</a>
 *
 * <!-- Log ==========
 * $Log: not supported by cvs2svn $
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
      if (token.type != Token.PROGRAM)
	  lexerror("Parserfehler");


      /* naechstes Token: NAME erwartet */
      token = nextToken();
      if (token.type != Token.NAME)
	  lexerror ("Identifikator (Programmname) erwartet.");
      //    progname = token.textValue;  // setze Namen des Programms      

      if (debug)
	  System.out.println ("Programm "+token.textValue);

      /* naechstes Token: { erwartet */
      token = nextToken();
      if (token.type != Token.LBRACE)
	  lexerror ("'{' erwartet.");

      /* naechstes Token: 'process' oder 'channel' erwartet */
      token = nextToken();
      while ((token.type == Token.CHANNEL) || (token.type == Token.PROCESS)){
	  if (token.type == Token.CHANNEL){
	      channels =  new absynt.ChandecList(
				new absynt.Chandec(buildChannel()), channels);
	  } else if (token.type == Token.PROCESS) {
	      buildProcess();

	  } else 
	      lexerror ("'channel' oder 'process' erwartet.");

	  /* aktueller Token: ';' oder '}' */

	  /* naechster Token */
	  token = nextToken();
      }
      
      if (token.type != Token.RBRACE)
	  lexerror("'}' erwartet.");

      /* Programm erzeugen */
      program = new absynt.Program(channels, processes);
      program.name = progname;

      if(debug)
	  System.out.println("Ende Programm.");
      
      return program;
  }/* Ende buildProgram() */
  


  /**
   * Methode um ein Objekt der Klasse absynt.Channel zu erzeugen
   * @returns absynt.Channel
   * @throws IOException falls kein Token mehr gelesen werden kann
   * @throws ParseException falls ein Syntax-Fehler aufgetreten ist
   */
  private absynt.Channel buildChannel() throws IOException, ParseException{
      /* aktueller Token-Typ: Token.CHANNEL */
      if (token.type != Token.CHANNEL)
	  lexerror("Parserfehler");
      absynt.Channel channel;

      /* naechstes Token: NAME erwartet */
      token = nextToken();
      if (token.type != Token.NAME)
	  lexerror("Identifikator (Channelname) erwartet");
      channel = new absynt.Channel(token.textValue);
//        /* naechstes Token: TYPE erwartet */
//        token = nextToken();
//        if (token.type != Token.TYPE)
//  	  lexerror("Typ (Channeltyp) erwartet");
//        if (token.intValue == Token.BOOL)
//  	  channel.m_type = new absynt.M_Bool();
//        else
//  	  channel.m_type = new absynt.M_Int();


      /* naechstes Token: SEMICOL erwartet */
      token = nextToken();
      if (token.type != Token.SEMICOL)
	  lexerror("';' erwartet");

//        if (debug)
//  	  System.outprintln("channel: "+channel.m_type+" "+channel.name);
      return channel;
  }
  


  protected void buildProcess() throws IOException, ParseException{
      //      absynt.Process process = null;
      //      absynt.VardecList vardecs = null;
      //      absynt.TransitionList transitions = null;
      //      absynt.AstateList astates = null;
      //      absynt.Initstate init = null;
      //      String processname = null; 
      
      int i= 0;
	  /* Name erwartet */
	  token = nextToken();
	  if (token.type != Token.NAME)
	      lexerror("Identifikator (Prozessname) erwartet");

	  if(debug)
	      System.out.println("  process "+token.textValue);

	  token = nextToken();

	  /* '{' erwartet */
	  if (token.type == Token.LBRACE){
	      /* bis '}' ... */
	      while (token.type != Token.RBRACE){
		  token = nextToken();
		  /* ...statements erwartet */
		  buildStatement();
		  System.err.println(i++);
	      }
	  } else
	      lexerror("'{'erwartet");
	  
	  if (token.type == Token.RBRACE){
	      System.out.println("Ende Prozess");
	  } else
	      lexerror("'}'erwartet");


  }/* ende buildProcess() */
  
  protected void buildStatement() throws IOException, ParseException{
      if (token.type == Token.LBRACE){
	  buildBlock();
	  if(debug)
	      System.out.println("Ende Block");
      } 
      
      switch (token.type){
      case Token.NAME:    buildAssignment(); break;
      case Token.WHILE:   buildWhileStatement(); break;
      case Token.ASSERT:  buildAssertStatement(); break;
      case Token.PUT:     buildPutStatement(); break; 
      case Token.GET:     buildGetStatement(); break;
      case Token.IF:      buildIfStatement(); break;
      case Token.SEMICOL: buildTauStatement(); break;
      }
      /* erwartetes Token: SEMICOL oder RBRACE */ 
  }
  

  protected void buildTauStatement() throws IOException, ParseException{
      token = nextToken();
  }
  
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
	  System.out.print("    Zuweisung: "+varname+"=");


      /* erwartetes Token: Teil einer Expr, 
       * also Konstante, Operator, '(' oder Id
       */
      token = nextToken();
      buildExpression();      
      
      /* erwartetes Token: SEMICOL */

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

  }

  protected void buildPutStatement() throws IOException, ParseException{
      if(debug)
	  System.out.print("    put ");

      /* '(' erwartet */
      token = nextToken();
      if(token.type != Token.LPAREN)
	 lexerror("'(' erwartet");
      
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
  }

  protected void buildGetStatement() throws IOException, ParseException{
      if(debug)
	  System.out.print("   get ");
      
      /* '(' erwartet */
      token = nextToken();
      if(token.type != Token.LPAREN)
	  lexerror("'(' erwartet");

      /* Identifikator erwartet */
      token = nextToken();
      if(token.type != Token.NAME)
	  lexerror("Identifikator  erwartet");
      
      /* ',' erwartet */
      token = nextToken();
      if(token.type != Token.COMMA)
	  lexerror("',' erwartet");
      
      /* Identifikator erwartet */
      token = nextToken();
      if(token.type != Token.NAME)
	  lexerror("Identifikator  erwartet");

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
	  System.out.print(")");

      /* erwartetes Token: Statement */
      token = nextToken();
      buildStatement();

  }


  protected void buildBlock() throws IOException, ParseException{
      /* aktuelles Token: LBRACE */
      if (token.type != Token.LBRACE)
	  lexerror ("Parserfehler");

      if(debug)
	  System.out.println("  block ");

      /* erwartetes Token: Statement */      
      while(token.type != Token.RBRACE){
	  token = nextToken();
	  buildStatement();
	  System.out.println();
      }

      /* aktuelles Token: RBRACE */

      /* erwartetes Token: naechstes Statement oder Prozessende ('}' */
      token = nextToken();
  }
  
  protected void buildExpression() throws IOException, ParseException{
      /* erwartetes Token: Konstante, Variable, '(' oder Operator */

      while ((token.type != Token.SEMICOL) && (token.type != Token.RPAREN)){
	  System.out.print("expression ");
	  token = nextToken();
      }

  }
  
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
"program"           { return new Token(Token.PROGRAM)         ; }
"channel"           { return new Token(Token.CHANNEL)         ; }
"process"           { return new Token(Token.PROCESS)         ; }
"while"             { return new Token(Token.WHILE)           ; }
"bool"              { return new Token(Token.TYPE, Token.BOOL); }
"int"               { return new Token(Token.TYPE, Token.INT) ; }
"if"                { return new Token(Token.IF)              ; }
"else"              { return new Token(Token.ELSE)            ; }
"put"               { return new Token(Token.PUT)             ; }
"get"               { return new Token(Token.GET)             ; }
"assert"            { return new Token(Token.ASSERT)          ; }
"="                 { return new Token(Token.ASSIGN)          ; }
";"                 { return new Token(Token.SEMICOL)         ; }
"("                 { return new Token(Token.LPAREN)          ; }
")"                 { return new Token(Token.RPAREN)          ; }
"{"                 { return new Token(Token.LBRACE)          ; }
"}"                 { return new Token(Token.RBRACE)          ; }
","                 { return new Token(Token.COMMA)           ; }

"&&"                { return new Token(Token.OP,Token.AND)    ; }
"||"                { return new Token(Token.OP,Token.OR)     ; }
">"                 { return new Token(Token.OP,Token.GREATER); }
">="                { return new Token(Token.OP,Token.GEQ)    ; }
"<"                 { return new Token(Token.OP,Token.LESS)   ; }
"<="                { return new Token(Token.OP,Token.LEQ)    ; }
"!"                 { return new Token(Token.OP,Token.NEG)    ; }
"!="                { return new Token(Token.OP,Token.NEQ)    ; }
"=="                { return new Token(Token.OP,Token.EQ)     ; }
"-"                 { return new Token(Token.OP,Token.MINUS)  ; }
"+"                 { return new Token(Token.OP,Token.PLUS)   ; }
"/"                 { return new Token(Token.OP,Token.DIV)    ; }
"*"                 { return new Token(Token.OP,Token.TIMES)  ; }

{name}              { return new Token(Token.NAME,yytext())   ; }
{number}            { return new Token(Token.NUMBER, Integer.valueOf(yytext()).intValue()); }
.					{ System.out.println("char: "+yytext()); }











