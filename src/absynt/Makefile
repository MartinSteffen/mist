#----------------------------------------------------------------------
#	Abstract Syntax for Mist
#	------------------------------------
#
#	$Id: Makefile,v 1.4 2000-06-26 16:04:28 unix01 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.3  2000/05/30 15:25:10  unix01
#	Gruppeneinteilung (+ mail geschrieben)
#	
#	Revision 1.2  2000/05/28 08:51:48  unix01
#	ok
#	
#	Revision 1.1  2000/05/28 07:57:02  unix01
#	Erste Version, kopiert vom alten Javaprojekt
#	
#----------------------------------------------------------------------

################################################

LOCALDOCDIR=Doc
WEBDIR     = /home/info/www/inf/deRoever/SS00/Java/Mist/absynt/

################################################



all:	$(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

%.class:	%.java
		javac $(JAVACOPTS) $<


clean:
	\rm -f *.class *~

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) *.java

www:
	cp $(LOCALDOCDIR)/*.html $(WEBDIR)




