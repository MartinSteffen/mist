########################################################
## $Id: Makefile,v 1.4 2000-06-05 07:04:51 unix01 Exp $
########################################################


HEVEA       = hevea
HEVEAOPTS   = -fix -exec xxdate.exe
HACHA       = hacha

DOC         = handout1
CODEDIR     = 
############# Achtung: die Versionen ändern sich!
WWWDIR      = /home/info/www/inf/deRoever/SS00/Java/Mist/Pflichtenheft2


index.html: $(DOC).html
	$(HACHA) -o index.html $(DOC).html

html: macros.hva $(DOC).tex
	$(HEVEA) $(HEVEAOPTS) macros.hva -o $(DOC)-sec.html $(DOC).tex
	$(HACHA) -o $(DOC).html $(DOC)-sec.html



clean:
	rm -f *.aux *.log *.dvi *.ps *.bbl *.blg *~
	rm *.html *.gif

www:
	cp *.html *.ps   $(WWWDIR)
