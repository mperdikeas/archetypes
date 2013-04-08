SET client_min_messages TO WARNING;

CREATE TABLE KM_CASH_FLOWS (
    CAFL_ID integer NOT NULL,
    COMP_ID integer NOT NULL,
    CAFL_DATE_CREATED date NOT NULL,
    CAFL_DATE_END date NOT NULL,
    CAFL_NAME character varying(240) NOT NULL,
    CAFL_BUBGETED_AMOUNT Numeric(12,2),
    CAFL_REVIEW_AMOUNT Numeric(12,2)
);

CREATE SEQUENCE CAFL_ID_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE CAFL_ID_seq OWNED BY KM_CASH_FLOWS.CAFL_ID;

CREATE TABLE ER_COMPANIES (
    COMP_ID integer NOT NULL,
    SUBS_ID integer NOT NULL
);

CREATE TABLE KM_TRANSACTIONS (
    TRAN_ID integer NOT NULL,
    TRGR_CREDITED_ID integer NOT NULL,
    TRGR_DEBITED_ID integer NOT NULL,
    TRAN_DATE date NOT NULL,
    TRAN_VALUE Numeric(12,2) DEFAULT 0 NOT NULL,
    TRAN_NAME character varying(120) NOT NULL
);

CREATE TABLE KM_TRANSACTION_GROUPS (
    TRGR_ID integer NOT NULL,
    CAFL_ID integer NOT NULL,
    TRGR_PARENT_ID integer,
    TRGT_ID smallint NOT NULL,
    TRGR_NAME character varying(120) NOT NULL,
    TRGR_PRINT_ORDER smallint NOT NULL,
    TRGR_PRINT_TOTAL boolean DEFAULT true NOT NULL,
    TRGR_IMPORT_RULES character varying(1200)
);

CREATE TABLE KM_TRANSACTION_GROUPS_INIT (
    TRGI_ID integer NOT NULL,
    TRGR_ID integer,
    TRGI_VALUE Numeric(12,2) DEFAULT 0 NOT NULL
);

CREATE TABLE KM_TRANSACTION_GROUPS_TEMPLATE (
    TRGE_ID integer NOT NULL,
    COMP_ID integer NOT NULL,
    TRGR_PARENT_ID integer,
    TRGT_ID smallint NOT NULL,
    TRGE_NAME character varying(120) NOT NULL,
    TRGE_PRINT_ORDER smallint NOT NULL,
    TRGE_PRINT_TOTAL boolean DEFAULT true NOT NULL,
    TRGE_IMPORT_RULES character varying(1200)
);

CREATE TABLE KM_TRANSACTION_GROUP_TYPES (
    TRGT_ID smallint NOT NULL,
    TRGT_NAME character varying(60) NOT NULL
);

CREATE SEQUENCE TRAN_ID_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE TRAN_ID_seq OWNED BY KM_TRANSACTIONS.TRAN_ID;

CREATE SEQUENCE TRGI_ID_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE TRGI_ID_seq OWNED BY KM_TRANSACTION_GROUPS_INIT.TRGI_ID;

CREATE SEQUENCE TRGR_ID_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE TRGR_ID_seq OWNED BY KM_TRANSACTION_GROUPS.TRGR_ID;

CREATE SEQUENCE TRGE_ID_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE TRGE_ID_seq OWNED BY KM_TRANSACTION_GROUPS_TEMPLATE.TRGE_ID;

ALTER TABLE ONLY KM_CASH_FLOWS ALTER COLUMN CAFL_ID SET DEFAULT nextval('CAFL_ID_seq'::regclass);

ALTER TABLE ONLY KM_TRANSACTIONS ALTER COLUMN TRAN_ID SET DEFAULT nextval('TRAN_ID_seq'::regclass);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS ALTER COLUMN TRGR_ID SET DEFAULT nextval('TRGR_ID_seq'::regclass);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_INIT ALTER COLUMN TRGI_ID SET DEFAULT nextval('TRGI_ID_seq'::regclass);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_TEMPLATE ALTER COLUMN TRGE_ID SET DEFAULT nextval('TRGE_ID_seq'::regclass);

INSERT INTO KM_TRANSACTION_GROUP_TYPES VALUES (-1, 'ΠΛΗΡΩΜΕΣ');
INSERT INTO KM_TRANSACTION_GROUP_TYPES VALUES (0, 'ΔΙΑΘΕΣΙΜΑ');
INSERT INTO KM_TRANSACTION_GROUP_TYPES VALUES (1, 'ΕΙΣΠΡΑΞΕΙΣ');

ALTER TABLE ONLY KM_CASH_FLOWS
    ADD CONSTRAINT CAFL_PK PRIMARY KEY (CAFL_ID);

ALTER TABLE ONLY ER_COMPANIES
    ADD CONSTRAINT COMP_PK PRIMARY KEY (COMP_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_INIT
    ADD CONSTRAINT Key1 PRIMARY KEY (TRGI_ID);

ALTER TABLE ONLY KM_TRANSACTIONS
    ADD CONSTRAINT TRAN_PK PRIMARY KEY (TRAN_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_TEMPLATE
    ADD CONSTRAINT TRGE_PK PRIMARY KEY (TRGE_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS
    ADD CONSTRAINT TRGR_PK PRIMARY KEY (TRGR_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUP_TYPES
    ADD CONSTRAINT TRGT_PK PRIMARY KEY (TRGT_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS
    ADD CONSTRAINT CAFL_TRGR_FK FOREIGN KEY (CAFL_ID) REFERENCES KM_CASH_FLOWS(CAFL_ID);

ALTER TABLE ONLY KM_CASH_FLOWS
    ADD CONSTRAINT COMP_CAFL_FK FOREIGN KEY (COMP_ID) REFERENCES ER_COMPANIES(COMP_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_TEMPLATE
    ADD CONSTRAINT COMP_TRGE_FK FOREIGN KEY (COMP_ID) REFERENCES ER_COMPANIES(COMP_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_TEMPLATE
    ADD CONSTRAINT TRGE_TRGE_FK FOREIGN KEY (TRGR_PARENT_ID) REFERENCES KM_TRANSACTION_GROUPS_TEMPLATE(TRGE_ID);

ALTER TABLE ONLY KM_TRANSACTIONS
    ADD CONSTRAINT TRGR_TRAN_FK FOREIGN KEY (TRGR_CREDITED_ID) REFERENCES KM_TRANSACTION_GROUPS(TRGR_ID);

ALTER TABLE ONLY KM_TRANSACTIONS
    ADD CONSTRAINT TRGR_TRAN_FK1 FOREIGN KEY (TRGR_DEBITED_ID) REFERENCES KM_TRANSACTION_GROUPS(TRGR_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_INIT
    ADD CONSTRAINT TRGR_TRGI_FK FOREIGN KEY (TRGR_ID) REFERENCES KM_TRANSACTION_GROUPS(TRGR_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS
    ADD CONSTRAINT TRGR_TRGR_FK FOREIGN KEY (TRGR_PARENT_ID) REFERENCES KM_TRANSACTION_GROUPS(TRGR_ID) DEFERRABLE;

ALTER TABLE ONLY KM_TRANSACTION_GROUPS_TEMPLATE
    ADD CONSTRAINT TRGT_TRGE_FK FOREIGN KEY (TRGT_ID) REFERENCES KM_TRANSACTION_GROUP_TYPES(TRGT_ID);

ALTER TABLE ONLY KM_TRANSACTION_GROUPS
    ADD CONSTRAINT TRGT_TRGR_FK FOREIGN KEY (TRGT_ID) REFERENCES KM_TRANSACTION_GROUP_TYPES(TRGT_ID);


