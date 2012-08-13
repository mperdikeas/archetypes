CREATE TABLE public.privateuserdata (
 employee             VARCHAR NOT NULL,
 employee_personal    VARCHAR NOT NULL,
 CONSTRAINT users_pk PRIMARY KEY(employee)
);

CREATE UNIQUE INDEX privateuserdata_idx ON public.privateuserdata (employee);
