#----------------------------------------------------------------------
# developers' top-level Makefile for the Mist project
# $Id: Makefile,v 1.1 2000-06-20 08:12:38 unix01 Exp $
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
#	(cd ./editor; make)
#	(cd ./gui; make)
#	(cd ./checks1; make)
#	(cd ./checks2; make)
	(cd ./parser; make)
#	(cd ./modcheck; make)
#	(cd ./position1; make)
	(cd ./position2; make)





%.class:	%.java
		javac $(JAVACOPTS) $<


clean:
	\rm -f *.class *~

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDIR)




