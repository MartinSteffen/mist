#----------------------------------------------------------------------
# developers' top-level Makefile for the Mist project
# $Id: Makefile,v 1.2 2000-06-26 17:22:14 unix01 Exp $
#----------------------------------------------------------------------

################################################

#LOCALDOCDIR=Doc
#WEBDIR     = /home/info/www/inf/deRoever/SS00/Java/Mist/absynt/

################################################



all:	packets $(patsubst %.java,%.class,$(wildcard *.java))

debug:	; make JAVACOPTS="-g" all

packets:
	(cd ./absynt; make)
	(cd ./utils; make)
	(cd ./editor; make)
	(cd ./gui; make)
#	(cd ./checks1; make) -->  compilation error
#	(cd ./checks2; make) -->  No code, no compilation
	(cd ./parser; make)
	(cd ./modcheck; make)
	(cd ./position1; make)
#	(cd ./position2; make) -> compilation error





%.class:	%.java
		javac $(JAVACOPTS) $<


clean:
	\rm -f *.class *~

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDIR)




