include ${CONSTS}
.PHONY: all foo
all: foo
foo: check-env
	@echo $(A)
	@echo $(B)
	@echo $(C)
	@echo $(D)

check-env:
ifndef CONSTS
	$(error CONSTS is undefined)
endif