CREATE TABLE public.users (
 user_name     VARCHAR NOT NULL,
 jdbcCoodrs    VARCHAR NOT NULL,
 CONSTRAINT users_pk PRIMARY KEY(user_name)
);

CREATE UNIQUE INDEX users_idx ON public.users (user_name);
