#----------------------------------------------------------------------
#	Abstract Syntax for PEST Statecharts
#	------------------------------------
#
#	$Id: Makefile,v 1.2 2000-05-28 08:51:48 unix01 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.1  2000/05/28 07:57:02  unix01
#	Erste Version, kopiert vom alten Javaprojekt
#	
#----------------------------------------------------------------------

################################################

LOCALDOCDIR=Doc

################################################



all:	$(patsubst %.java,%.class,$(wildcard *.java))
debug:	; make JAVACOPTS="-g" all

%.class:	%.java
		javac $(JAVACOPTS) $<


clean:
	\rm -f *.class *~

localdoc:	;
	javadoc  -version -author -d $(LOCALDOCDIR) *.java