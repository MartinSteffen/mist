########################################################
## $Id: Makefile,v 1.1 2000-05-19 14:47:00 softtech Exp $
########################################################


HEVEA       = hevea
HEVEAOPTS   = -fix
HACHA       = hacha

DOC         = main-web
CODEDIR     = 
WWWDIR      = 

index.html: $(DOC).html
	$(HACHA) -o index.html $(DOC).html

xinstall:  index.html
	mv *.html $(WWWDIR)

$(DOC).html: macros.hva $(DOC).tex
	$(HEVEA) $(HEVEAOPTS) macros.hva $(DOC).tex

html:
	hevea macros.hva handout1.tex
## 	hacha main-web.html



clean:
	rm -f *.aux *.log *.dvi *.ps *.bbl *.blg *~

www:
	cp *.ps /home/info/www/inf/deRoever/SS00/Java/Handouts/