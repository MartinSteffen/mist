#----------------------------------------------------------------------
#	Parser for Mist
#	------------------------------------
#
#	$Id: Makefile,v 1.2 2000-07-09 21:14:28 unix01 Exp $
#
#	$Log: not supported by cvs2svn $
#----------------------------------------------------------------------

################################################

LOCALDOCDIR=Doc
WEBDIR     = /home/info/www/inf/deRoever/SS00/Java/Mist/absynt/

################################################

all:	Parser.java
	javac Parser.java

Token.class:
	javac Token.java

Parser.java:	Parser.lex
	java JLex.Main Parser.lex
	mv Parser.lex.java Parser.java


clean:
	\rm -f *.class *~ Parser.java

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDIR)








