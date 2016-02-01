DOCUMENT=esa-sre-rawdar-sdd-0001
.PHONY: all clean
all:
	pdflatex -halt-on-error $(DOCUMENT).tex
	makeglossaries $(DOCUMENT)
	pdflatex -halt-on-error $(DOCUMENT).tex
spellcheck:
	aspell -t -c $(DOCUMENT).tex
clean:
	rm -f *.pdf
	rm -f *.log
	rm -f *.lol
	rm -f *.lof
	rm -f *.toc
	rm -f *.out
	rm -f *.aux
	rm -f *.alg
	rm -f *.glg
	rm -f *.glo
	rm -f *.gls
	rm -f *.glsdefs
	rm -f *.idx
	rm -f *.ist
	rm -f *.acn
	rm -f *.acr



