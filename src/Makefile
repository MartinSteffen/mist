#----------------------------------------------------------------------
# developers' top-level Makefile for the Mist project
# $Id: Makefile,v 1.3 2000-06-26 17:56:29 unix01 Exp $
#----------------------------------------------------------------------

################################################

WEBDOCDIR     =/home/info/www/inf/deRoever/SS00/Java/Mist/Doc
LOCALDOCDIR=Doc

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



clean:
	(cd ./absynt; make clean)
	(cd ./utils; make clean)
	(cd ./editor; make clean)
	(cd ./gui; make clean)
	(cd ./checks1; make clean) 
#	(cd ./checks2; make clean) 
	(cd ./parser; make clean)
	(cd ./modcheck; make clean)
	(cd ./position1; make clean)
	(cd ./position2; make clean)




%.class:	%.java
		javac $(JAVACOPTS) $<


doc:	;
	javadoc  -version -author -d $(WEBDOCDIR) absynt\
	                                        utils\
	                                        editor\
	                                        gui\
	                                        checks1\
	                                        parser\
	                                        modcheck\
	                                        position1\
	                                        position2

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) absynt\
	                                        utils\
	                                        editor\
	                                        gui\
	                                        checks1\
	                                        parser\
	                                        modcheck\
	                                        position1\
	                                        position2

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDOCDIR)




