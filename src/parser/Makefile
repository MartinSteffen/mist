#----------------------------------------------------------------------
#	Parser for Mist
#	------------------------------------
#
#	$Id: Makefile,v 1.3 2000-07-11 11:31:36 unix01 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.2  2000/07/09 21:14:28  unix01
#	Umstieg auf JLex!
#	Parser.java entfernt, dafuer Parser.lex
#	Makefile entsprechend angepasst
#	Token.java stellt Klasse Token und Symbole zur Verfuegung
#	
#----------------------------------------------------------------------

################################################

LOCALDOCDIR=Doc
WEBDIR     = /home/info/www/inf/deRoever/SS00/Java/Mist/absynt/
LEXLOGFILE = JLex.log
################################################

all:	Scanner.java Token.class Parser.class
	javac Scanner.java


Token.class:
	javac Token.java

Parser.class:
	javac Parser.java

Scanner.java:	Scanner.lex
	java JLex.Main Scanner.lex >$(LEXLOGFILE)
	mv Scanner.lex.java Scanner.java


clean:
	\rm -f *.class *~ Scanner.java $(LEXLOGFILE)

localdoc:	Scanner.java
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDIR)








