#----------------------------------------------------------------------
#	Utilities Makefile
#     $Id: Makefile,v 1.2 2000-07-02 09:21:35 unix01 Exp $
#
#----------------------------------------------------------------------

################################################

LOCALDOCDIR=Doc
#WEBDIR     = /home/info/www/inf/deRoever/SS00/Java/Mist/absynt/

################################################



all:	$(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

%.class:	%.java
		javac $(JAVACOPTS) $<


clean:
	\rm -f *.class *~
	\rm -f undo/*.class *~
	\rm -f einterface/*.class

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

###www:
###	cp $(LOCALDOCDIR)/*.html $(WEBDIR)




