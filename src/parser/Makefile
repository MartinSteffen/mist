#----------------------------------------------------------------------
#	Parser for Mist
#	------------------------------------
#
#	$Id: Makefile,v 2.0 2000-07-17 10:45:22 unix01 Exp $
#
#----------------------------------------------------------------------

################################################

LOCALDOCDIR=Doc
WEBDIR     = /home/info/www/inf/deRoever/SS00/Java/Mist/absynt/
LEXLOGFILE = JLex.log
CUPLOGFILE = cup.log
PACKAGE = parser
################################################

all:	Scanner.cup	Scanner.lex
	java java_cup.Main -parser Parser -nonterms -package $(PACKAGE) Scanner.cup >$(CUPLOGFILE)
	java JLex.Main Scanner.lex >$(LEXLOGFILE)
	mv Scanner.lex.java Yylex.java
	javac Parser.java sym.java Yylex.java



clean:
	\rm -f *.class *~ Yylex.java Scanner.lex.java sym.java Parser.java $(LEXLOGFILE) $(CUPLOGFILE)

localdoc:	parser.java	sym.java
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDIR)



# -----------------------------------------------------------------------
#	$Log: not supported by cvs2svn $
#	Revision 1.5  2000/07/14 10:09:55  unix01
#	Tippfehler beseitigt (Scott)
#	
#	Revision 1.4  2000/07/14 06:16:58  unix01
#	Abhaengigkeiten abgestimmt
#	Target testsuite erstellt (zur Uebersetzung von LineParser, FileParser)
#	[Scott]
#	
#	Revision 1.3  2000/07/11 11:31:36  unix01
#	Umbenennung von Parser.lex in Scanner.lex - einfacher fuer die anderen
#	Gruppen
#	
#	Revision 1.2  2000/07/09 21:14:28  unix01
#	Umstieg auf JLex!
#	Parser.java entfernt, dafuer Parser.lex
#	Makefile entsprechend angepasst
#	Token.java stellt Klasse Token und Symbole zur Verfuegung
# ------------------------------------------------------------------------	









