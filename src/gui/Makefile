#----------------------------------------------------------------------
#	Gui Makefile
#	------------------------------------
#
#	$Id: Makefile,v 1.2 2000-06-26 16:08:13 unix01 Exp $
#
#	$Log: not supported by cvs2svn $
#	Revision 1.1  2000/06/26 16:05:26  unix01
#	Ich habe ein Makefile bereitgestellt, weil
#	ich mit der Integration beginnen will
#	
#	[Steffen]
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




