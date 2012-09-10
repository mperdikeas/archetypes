CREATE TABLE public.user (
    id             INTEGER NOT NULL,
    firstname      VARCHAR NOT NULL,
    surname        VARCHAR NOT NULL,
    age            INTEGER NOT NULL,
    CONSTRAINT id_pk PRIMARY KEY(id)
);

CREATE UNIQUE INDEX id_idx ON public.user (id);
