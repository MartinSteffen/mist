########################################################
## $Id: Makefile,v 1.2 2000-05-22 11:09:11 softtech Exp $
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

$(DOC).html: macros.hva $(DOC).tex
	$(HEVEA) $(HEVEAOPTS) macros.hva $(DOC).tex

html:
	hevea macros.hva handout1.tex
	$(HACHA) -o index.html $(DOC).html
## 	hacha main-web.html



clean:
	rm -f *.aux *.log *.dvi *.ps *.bbl *.blg *~

www:
	cp *.html *.ps   $(WWWDIR)
