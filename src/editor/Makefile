#----------------------------------------------------------------------
#	Editor Makefile
#	------------------------------------
#
#
#	$Log: not supported by cvs2svn $
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





