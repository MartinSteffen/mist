#----------------------------------------------------------------------
#	Parser for Mist
#	------------------------------------
#
#	$Id: Makefile,v 1.5 2000-07-14 10:09:55 unix01 Exp $
#
#----------------------------------------------------------------------

################################################

LOCALDOCDIR=Doc
WEBDIR     = /home/info/www/inf/deRoever/SS00/Java/Mist/absynt/
LEXLOGFILE = JLex.log
################################################

all:	Token.class Scanner.class Parser.class 

Token.class:	Token.java
	javac Token.java

Parser.class:	Parser.java
	javac Parser.java

Scanner.class:	Scanner.lex
	java JLex.Main Scanner.lex >$(LEXLOGFILE)
	mv Scanner.lex.java Scanner.java
	javac Scanner.java


testsuite:	LineParser.java	FileParser.java
	javac LineParser.java FileParser.java


clean:
	\rm -f *.class *~ Scanner.java $(LEXLOGFILE)

localdoc:	Scanner.class
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDIR)



# -----------------------------------------------------------------------
#	$Log: not supported by cvs2svn $
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




