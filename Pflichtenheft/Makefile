########################################################
## $Id: Makefile,v 1.3 2000-05-22 18:26:45 softtech Exp $
########################################################


HEVEA       = hevea
HEVEAOPTS   = -fix
HACHA       = hacha

DOC         = handout1
CODEDIR     = 
WWWDIR      = /home/info/www/inf/deRoever/SS00/Java/Mist

index.html: $(DOC).html
	$(HACHA) -o index.html $(DOC).html

xinstall:  index.html
	mv *.html $(WWWDIR)

html: macros.hva $(DOC).tex
	$(HEVEA) $(HEVEAOPTS) macros.hva -o $(DOC)-sec.html $(DOC).tex
	$(HACHA) -o $(DOC).html $(DOC)-sec.html



clean:
	rm -f *.aux *.log *.dvi *.ps *.bbl *.blg *~
	rm *.html *.gif

www:
	cp *.html *.ps   $(WWWDIR)
