package parser;

import absynt.*;
import java.io.IOException;
/**
 * Klasse Scanner - automatisch generiert von JLex aus Scanner.lex
 *
 * @author Andreas Scott &amp; Alexander Hegener 
 * @version $Id: Scanner.lex,v 1.1 2000-07-11 11:31:36 unix01 Exp $
 * @see <a href="../../Scanner.lex">Scanner.lex</a>
 *
 * <!-- Log ==========
 * $Log: not supported by cvs2svn $
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
  protected void buildProgram() throws IOException, ParseException{
	  token = nextToken();
	  
	  /* NAME erwartet */
	  if (token.type == Token.NAME){
		  System.out.println ("Programm "+token.textValue);
	  } else 
		  lexerror ("Identifikator (Programmname) erwartet.");

	  /* { erwartet */
	  token = nextToken();
	  if (token.type == Token.LBRACE){
		  
		  /* 'process' oder 'channel' erwartet */
		  token = nextToken();
		  while ((token.type == Token.CHANNEL) || (token.type == Token.PROCESS)){
			  if (token.type == Token.CHANNEL){
				  buildChannel();
			  } else if (token.type == Token.PROCESS) {
				  buildProcess();
			  } else 
				  lexerror ("'channel' oder 'process' erwartet.");
			  token = nextToken();
		  }
	  } else 
		  lexerror ("'{' erwartet.");
	  
	  token = nextToken();
	  if (token.type == Token.RBRACE){
		  System.out.println("Ende Programm.");
	  } else
		  lexerror("'}' erwartet.");
  }
  
  private void buildChannel() throws IOException, ParseException{
	  if (token.type == Token.NAME)
		  System.out.println("  channel "+token.textValue);
	  else
		  lexerror("Identifikator (Channelname) erwartet");
	  token = nextToken();

	  if (token.type != Token.SEMICOL)
		  lexerror("';'erwartet");

	  token = nextToken();
  }

  protected void buildProcess() throws IOException, ParseException{
	  /* Name erwartet */
	  token = nextToken();
	  if (token.type == Token.NAME)
		  System.out.println("  process "+token.textValue);
	  else
		  lexerror("Identifikator (Prozessname) erwartet");

	  token = nextToken();
	  if (token.type == Token.LBRACE){
		  token = nextToken();
		  while (token.type != Token.RBRACE){
			  /* statements erwartet */
			  System.out.println("statement");
			  token = nextToken();
		  }
	  } else
		  lexerror("'{'erwartet");

	  if (token.type == Token.RBRACE){
		  System.out.println("Ende Prozess");
	  } else
		  lexerror("'}'erwartet");
  }

  protected void buildStatement() throws IOException, ParseException{
	  switch (token.type){
	  case Token.NAME:    buildAssignment(); break;
	  case Token.WHILE:   buildWhileStatement(); break;
	  case Token.ASSERT:  System.out.println ("Assertion"); break;
	  case Token.PUT:     System.out.println ("'put'-statement"); break; 
	  case Token.GET:     System.out.println ("'get'-statement"); break;
	  case Token.IF:      System.out.println ("'if'-statement"); break;
	  case Token.LBRACE:  System.out.println ("statement-block"); break;
	  }
	  token = nextToken();
  }

  protected void buildAssignment() throws IOException, ParseException{
	  System.out.print("    Zuweisung: "+token.textValue+"=");
	  token = nextToken();

	  if (token.type == Token.ASSIGN){
		  token = nextToken();
		  buildExpression();
	  } else 
		  lexerror ("'=' erwartet.");
  }

  protected void buildWhileStatement() throws IOException, ParseException{
	  System.out.print("    while ");
	  token = nextToken();
	  
	  if (token.type == Token.LPAREN){
		  token = nextToken();
		  buildExpression();
		  if (token.type == Token.RPAREN){
			  token = nextToken();
			  buildStatement();
		  } else
			  lexerror("')' erwartet.");
	  } else
		  lexerror("'(' erwartet.");
  }

  protected void buildExpression() throws IOException, ParseException{
	  while ((token.type != Token.SEMICOL) && (token.type != Token.RPAREN)){
		  System.out.print("expression ");
		  token = nextToken();
	  }
	  System.out.println();
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
"if"                { return new Token(Token.IF)              ; }
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











