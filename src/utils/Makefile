#----------------------------------------------------------------------
#	Utilities Makefile
#     $Id: Makefile,v 1.1 2000-06-04 12:06:16 unix01 Exp $
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

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

###www:
###	cp $(LOCALDOCDIR)/*.html $(WEBDIR)




