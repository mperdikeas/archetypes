.PHONY: all clean import-ivy-dependencies main-build
all: clean import-ivy-dependencies mail-build
clean:
	rm -fr cache
	rm -fr lib
	ant clean
import-ivy-dependencies:
	ant -f build-ivy-install-local.xml
mail-build:
	ant


