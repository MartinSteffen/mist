########################################################
## $Id: Makefile,v 1.9 2000-07-10 17:55:12 unix01 Exp $
########################################################


HEVEA       = hevea
HEVEAOPTS   = -fix -exec xxdate.exe
HACHA       = hacha

DOC         = handout1
CODEDIR     = 
############# Achtung: die Versionen �ndern sich!
WWWDIR      = /home/info/www/inf/deRoever/SS00/Java/Mist/Pflichtenheft5


index.html: $(DOC).html
	$(HACHA) -o index.html $(DOC).html

html: macros.hva $(DOC).tex
	$(HEVEA) $(HEVEAOPTS) macros.hva -o $(DOC)-sec.html $(DOC).tex
	$(HACHA) -o $(DOC).html $(DOC)-sec.html



clean:
	rm -f *.aux *.log *.dvi *.ps *.bbl *.blg *~ 
	rm *.html *.gif *.haux

www:
	cp *.html *.ps *.gif  $(WWWDIR)
