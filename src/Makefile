#----------------------------------------------------------------------
# developers' top-level Makefile for the Mist project
# $Id: Makefile,v 1.17 2000-07-17 07:31:35 unix01 Exp $
#----------------------------------------------------------------------

################################################


WEBDIR        = /home/info/www/inf/deRoever/SS00/Java/Mist
WEBDOCDIR     = $(WEBDIR)/Doc
BASELINESDIR  = $(WEBDIR)/Baselines
LOCALDOCDIR   = Doc
PACKAGEDIRS   = absynt  utils editor editor.einterface gui checks1 parser\
	      modcheck simulator position1 position2

################################################



all:	packets $(patsubst %.java,%.class,$(wildcard *.java))

debug:	; make JAVACOPTS="-g" all

run:	all
	java gui.GUI



packets:
	(cd ./absynt; make)
	(cd ./utils; make)
	(cd ./editor; make)
	(cd ./gui; make)
	(cd ./checks1; make) # -->  compilation error
#	(cd ./checks2; make) -->  No code, no compilation
	(cd ./parser; make)
	(cd ./simulator; make)
	(cd ./modcheck; make)
	(cd ./position1; make)
	(cd ./position2; make)



jar:
	jar cvf mist.jar ./absynt/*.class \
	    ./utils/*.class \
	    ./editor/*.class \
	    ./editor/einterface/*.class\
	    ./editor/images/*.gif\
	    ./gui/*.class \
	    ./checks1/*.class \
	    ./parser/*.class \
	    ./simulator/*.class \
	    ./modcheck/*.class \
	    ./position1/*.class \
	    ./position2/*.class \

jarinstall: 
	cp mist_v*.jar $(BASELINESDIR)


clean:
	(cd ./absynt; make clean)
	(cd ./utils; make clean)
	(cd ./editor; make clean)
	(cd ./gui; make clean)
	(cd ./checks1; make clean) 
#	(cd ./checks2; make clean) 
	(cd ./parser; make clean)
	(cd ./modcheck; make clean)
	(cd ./simulator; make clean)
	(cd ./position1; make clean)
	(cd ./position2; make clean)
	(cd $(LOCALDOCDIR); make clean)




%.class:	%.java
		javac $(JAVACOPTS) $<


doc:	;
	javadoc  -version -author -d $(WEBDOCDIR) $(PACKAGEDIRS)
	cp ./simulator/specification.txt $(WEBDOCDIR)

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) $(PACKAGEDIRS)
	cp ./simulator/specification.txt $(LOCALDOCDIR)





