package parser;
/** 
 * Klasse Token
 */

public class Token{

	/** Identifikator */ public static final int NAME    =  0;
	/** Nummer        */ public static final int NUMBER  =  1;  
	/** "{"           */ public static final int LBRACE  =  2;  
	/** "}"           */ public static final int RBRACE  =  3;  
	/** "("           */ public static final int LPAREN  =  4;  
	/** ")"           */ public static final int RPAREN  =  5;  
	/** "program"     */ public static final int PROGRAM =  6;
	/** "process"     */ public static final int PROCESS =  7;
	/** "channel"     */ public static final int CHANNEL =  8;
	/** "while"       */ public static final int WHILE   =  9;
	/** "if"          */ public static final int IF      = 10;
	/** "assert"      */ public static final int ASSERT  = 11;
	/** ";"           */ public static final int SEMICOL = 12;
	/** "="           */ public static final int ASSIGN  = 13;
	/** Operatoren    */ public static final int OP      = 14;
	/** "put"         */ public static final int PUT     = 15;
	/** "get"         */ public static final int GET     = 16;
	/** ","           */ public static final int COMMA   = 17;
	/** wahr o. falsch*/ public static final int BOOL    = 18;
	
	/*
	 * Operatoren
	 */
	
	/** Ungleich: "!="*/ public static final int NEQ     =  1;
	/** Kleiner: "<"  */ public static final int LESS    =  2; 
	/** Kleinergleich */ public static final int LEQ     =  3;  
	/** Und: "&&"     */ public static final int AND     =  4;  
	/** Oder: "||"    */ public static final int OR      =  5; 
	/** Groesser: ">" */ public static final int GREATER =  6; 
	/** Groessergleich*/ public static final int GEQ     =  7; 
	/** Negation: "!" */ public static final int NEG     =  8; 
	/** Plus: "+"     */ public static final int PLUS    =  9;
	/** Minus: "-"    */ public static final int MINUS   = 10; 
	/** Division: "/" */ public static final int DIV     = 11;  
	/** Mulitpl. "*"  */ public static final int TIMES   = 12; 
	/** Gleich "=="   */ public static final int EQ      = 13; 
	
	
	public String  textValue = null;
	public int     intValue = 0;
	public int     type = -1;
	
	/**
	 * Konstruktor um Token mit Typ zu erzeugen
	 * @parameter
	 * type - Typ des Tokens
	 */
	Token(int type){
		this.type = type; 
	}

	/**
	 * Konstruktor um Token mit Typ und Wert zu erzeugen
	 * @parameter
	 * type - Typ des Tokens
	 * text - Wert (Zeichenkette) des Tokens
	 */
	Token(int type, String text){
		this.type = type; 
		textValue = text;
	}

	/**
	 * Konstruktor um Token mit Typ und Wert zu erzeugen
	 * @parameter
	 * type - Typ des Tokens
	 * value - Wert (integer) des Tokens
	 */
	Token(int type, int value){
		this.type = type; 
		intValue = value;
	}
}	
