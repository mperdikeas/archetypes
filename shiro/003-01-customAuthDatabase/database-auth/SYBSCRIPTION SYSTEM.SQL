SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--


--
-- Name: SS_MODULES; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_MODULES" (
    "MODU_ID" integer NOT NULL,
    "MODU_DESCRIPTION" character varying(120) NOT NULL
);


ALTER TABLE public."SS_MODULES" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_MODULES"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_MODULES" IS 'TABLE THAT HOLDS THE MODULES OF THE GAIA';


--
-- Name: COLUMN "SS_MODULES"."MODU_DESCRIPTION"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULES"."MODU_DESCRIPTION" IS 'busted UTF8';


--
-- Name: MODU_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "MODU_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."MODU_ID_seq" OWNER TO "gaia-user";

--
-- Name: MODU_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "MODU_ID_seq" OWNED BY "SS_MODULES"."MODU_ID";


--
-- Name: SS_GROUPING_MODULES; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_GROUPING_MODULES" (
    "GRMO_ID" integer DEFAULT nextval('"MODU_ID_seq"'::regclass) NOT NULL,
    "MOGR_ID" integer NOT NULL,
    "MODU_ID" integer NOT NULL
);


ALTER TABLE public."SS_GROUPING_MODULES" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_GROUPING_MODULES"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_GROUPING_MODULES" IS 'busted UTF8';


--
-- Name: GRMO_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "GRMO_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."GRMO_ID_seq" OWNER TO "gaia-user";

--
-- Name: GRMO_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "GRMO_ID_seq" OWNED BY "SS_GROUPING_MODULES"."GRMO_ID";


--
-- Name: SS_MODULE_GROUP_EXCLUSIONS; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_MODULE_GROUP_EXCLUSIONS" (
    "MOGE_ID" integer NOT NULL,
    "MOGR_ID" integer NOT NULL,
    "MOGR_ID_EXLUDED" integer NOT NULL
);


ALTER TABLE public."SS_MODULE_GROUP_EXCLUSIONS" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_MODULE_GROUP_EXCLUSIONS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_MODULE_GROUP_EXCLUSIONS" IS 'busted UTF-8';


--
-- Name: COLUMN "SS_MODULE_GROUP_EXCLUSIONS"."MOGR_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULE_GROUP_EXCLUSIONS"."MOGR_ID" IS 'busted UTF-8';


--
-- Name: MOGE_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "MOGE_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."MOGE_ID_seq" OWNER TO "gaia-user";

--
-- Name: MOGE_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "MOGE_ID_seq" OWNED BY "SS_MODULE_GROUP_EXCLUSIONS"."MOGE_ID";


--
-- Name: SS_MODULE_GROUPS; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_MODULE_GROUPS" (
    "MOGR_ID" integer NOT NULL,
    "MOGR_DESCRIPTION" character varying(120) NOT NULL
);


ALTER TABLE public."SS_MODULE_GROUPS" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_MODULE_GROUPS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_MODULE_GROUPS" IS 'busted-UTF8';


--
-- Name: COLUMN "SS_MODULE_GROUPS"."MOGR_DESCRIPTION"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULE_GROUPS"."MOGR_DESCRIPTION" IS 'busted-UTF8';


--
-- Name: MOGR_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "MOGR_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."MOGR_ID_seq" OWNER TO "gaia-user";

--
-- Name: MOGR_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "MOGR_ID_seq" OWNED BY "SS_MODULE_GROUPS"."MOGR_ID";


--
-- Name: SS_MODULES_MENU; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_MODULES_MENU" (
    "MOME_ID" integer NOT NULL,
    "MOME_PARENT_ID" integer,
    "MODU_ID" integer,
    "MOME_CAPTION" character varying(240) NOT NULL,
    "MOME_DISPLAY_ORDER" integer NOT NULL,
    "MEDE_ACTION" character varying(240)
);


ALTER TABLE public."SS_MODULES_MENU" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_MODULES_MENU"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_MODULES_MENU" IS 'busted-UTF8';


--
-- Name: COLUMN "SS_MODULES_MENU"."MOME_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULES_MENU"."MOME_ID" IS 'Id Menu Detail';


--
-- Name: COLUMN "SS_MODULES_MENU"."MOME_PARENT_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULES_MENU"."MOME_PARENT_ID" IS 'busted UTF-8';


--
-- Name: COLUMN "SS_MODULES_MENU"."MOME_CAPTION"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULES_MENU"."MOME_CAPTION" IS 'busted UTF-8';


--
-- Name: COLUMN "SS_MODULES_MENU"."MOME_DISPLAY_ORDER"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULES_MENU"."MOME_DISPLAY_ORDER" IS 'busted UTF-8';


--
-- Name: COLUMN "SS_MODULES_MENU"."MEDE_ACTION"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_MODULES_MENU"."MEDE_ACTION" IS 'busted UTF-8';


--
-- Name: MOME_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "MOME_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."MOME_ID_seq" OWNER TO "gaia-user";

--
-- Name: MOME_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "MOME_ID_seq" OWNED BY "SS_MODULES_MENU"."MOME_ID";


--
-- Name: SS_ROLE_ACCESS_RIGHTS; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_ROLE_ACCESS_RIGHTS" (
    "ROAR_ID" integer NOT NULL,
    "ROLE_ID" integer NOT NULL,
    "SYEL_ID" integer NOT NULL,
    "ROAR_PERMISSION" character varying(240) DEFAULT '*'::character varying NOT NULL
);


ALTER TABLE public."SS_ROLE_ACCESS_RIGHTS" OWNER TO "gaia-user";

--
-- Name: ROAR_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "ROAR_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."ROAR_ID_seq" OWNER TO "gaia-user";

--
-- Name: ROAR_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "ROAR_ID_seq" OWNED BY "SS_ROLE_ACCESS_RIGHTS"."ROAR_ID";


--
-- Name: SS_ROLES; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_ROLES" (
    "ROLE_ID" integer NOT NULL,
    "SUBS_ID" integer NOT NULL,
    "ROLE_CAPTION" character varying(120) NOT NULL,
    "ROLE_STATUS" smallint DEFAULT 0 NOT NULL
);


ALTER TABLE public."SS_ROLES" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_ROLES"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_ROLES" IS 'busted UTF8';


--
-- Name: COLUMN "SS_ROLES"."ROLE_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_ROLES"."ROLE_ID" IS 'busted UTF8';


--
-- Name: COLUMN "SS_ROLES"."SUBS_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_ROLES"."SUBS_ID" IS 'busted UTF8';


--
-- Name: COLUMN "SS_ROLES"."ROLE_CAPTION"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_ROLES"."ROLE_CAPTION" IS 'busted UTF8';


--
-- Name: COLUMN "SS_ROLES"."ROLE_STATUS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_ROLES"."ROLE_STATUS" IS 'busted UTF8';


--
-- Name: ROLE_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "ROLE_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."ROLE_ID_seq" OWNER TO "gaia-user";

--
-- Name: ROLE_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "ROLE_ID_seq" OWNED BY "SS_ROLES"."ROLE_ID";


--
-- Name: SS_SUBSCRIPTIONS; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_SUBSCRIPTIONS" (
    "SUBS_ID" integer NOT NULL,
    "SUBS_NAME" character varying(60),
    "SUBS_DATABASE" character varying(120)
);


ALTER TABLE public."SS_SUBSCRIPTIONS" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_SUBSCRIPTIONS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_SUBSCRIPTIONS" IS 'busted UTF8';


--
-- Name: COLUMN "SS_SUBSCRIPTIONS"."SUBS_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_SUBSCRIPTIONS"."SUBS_ID" IS 'busted UTF8';


--
-- Name: COLUMN "SS_SUBSCRIPTIONS"."SUBS_NAME"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_SUBSCRIPTIONS"."SUBS_NAME" IS 'busted UTF8';


--
-- Name: COLUMN "SS_SUBSCRIPTIONS"."SUBS_DATABASE"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_SUBSCRIPTIONS"."SUBS_DATABASE" IS 'busted UTF8';


--
-- Name: SS_SYSTEM_ELEMENTS; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_SYSTEM_ELEMENTS" (
    "SYEL_ID" integer NOT NULL,
    "SYEL_TYPE" smallint DEFAULT 0 NOT NULL,
    "SYEL_DESCRIPTION" character varying(240)
);


ALTER TABLE public."SS_SYSTEM_ELEMENTS" OWNER TO "gaia-user";

--
-- Name: COLUMN "SS_SYSTEM_ELEMENTS"."SYEL_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_SYSTEM_ELEMENTS"."SYEL_ID" IS 'PRIMARY KEY OF THE TABLE';


--
-- Name: COLUMN "SS_SYSTEM_ELEMENTS"."SYEL_TYPE"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_SYSTEM_ELEMENTS"."SYEL_TYPE" IS '0 = UNSPECIFIED
1 = WEB PAGE
2 = MENU LINK
3 = BUTTON
4 = TEXT BOX
5 = PICTURE
6 = LABEL
ADD WHAT EVER ELSE YOUI NEED.';


--
-- Name: COLUMN "SS_SYSTEM_ELEMENTS"."SYEL_DESCRIPTION"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_SYSTEM_ELEMENTS"."SYEL_DESCRIPTION" IS 'ELEMENT DESCRIPTION';


--
-- Name: SS_USERS; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_USERS" (
    "USER_ID" integer NOT NULL,
    "SUBS_ID" integer NOT NULL,
    "USER_NAME" character varying(20) NOT NULL,
    "USER_PASSWORD" character varying(120) NOT NULL,
    "USER_FIRSTNAME" character varying(60) NOT NULL,
    "USER_SURNAME" character varying(60) NOT NULL,
    "USER_EMAIL" character varying(120),
    "USER_ADDRESS" character varying(120),
    "USER_ADDRESS_NUMBER" character varying(10),
    "USER_POSTAL_CODE" character varying(10),
    "USER_CITY" character varying(60),
    "USER_PREFECTURE" character varying(60),
    "USER_COUNTRY" character varying(60),
    "USER_IS_ADMINISTRATOR" boolean DEFAULT false NOT NULL,
    "USER_STATUS" smallint DEFAULT 0 NOT NULL,
    "USER_COMMENTS" character varying(250),
    "USER_LAST_LOGIN_DATE" timestamp without time zone DEFAULT now()
);


ALTER TABLE public."SS_USERS" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_USERS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_USERS" IS 'TABLE THAT HOLDS USERS INFORMATION';


--
-- Name: COLUMN "SS_USERS"."USER_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_ID" IS 'PRIMARY KEY OF THE TABLE';


--
-- Name: COLUMN "SS_USERS"."SUBS_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."SUBS_ID" IS 'SUBSCRIPTION ID';


--
-- Name: COLUMN "SS_USERS"."USER_NAME"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_NAME" IS 'USERNAME';


--
-- Name: COLUMN "SS_USERS"."USER_PASSWORD"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_PASSWORD" IS 'PASSWORD MD5 ENCRYPTED';


--
-- Name: COLUMN "SS_USERS"."USER_FIRSTNAME"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_FIRSTNAME" IS 'FIRST NAME';


--
-- Name: COLUMN "SS_USERS"."USER_SURNAME"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_SURNAME" IS 'SURNAME';


--
-- Name: COLUMN "SS_USERS"."USER_EMAIL"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_EMAIL" IS 'EMAIL ADDRESS WE  NEED TO FIND A VALIDATION MECHANISM';


--
-- Name: COLUMN "SS_USERS"."USER_ADDRESS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_ADDRESS" IS 'ADDRESS';


--
-- Name: COLUMN "SS_USERS"."USER_ADDRESS_NUMBER"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_ADDRESS_NUMBER" IS 'ADDRESS NO';


--
-- Name: COLUMN "SS_USERS"."USER_POSTAL_CODE"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_POSTAL_CODE" IS 'POSTAL CODE';


--
-- Name: COLUMN "SS_USERS"."USER_CITY"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_CITY" IS 'CITY';


--
-- Name: COLUMN "SS_USERS"."USER_PREFECTURE"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_PREFECTURE" IS 'PREFECTURE OR STATE';


--
-- Name: COLUMN "SS_USERS"."USER_COUNTRY"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_COUNTRY" IS 'COUNTRY';


--
-- Name: COLUMN "SS_USERS"."USER_IS_ADMINISTRATOR"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_IS_ADMINISTRATOR" IS 'IF TRUE THIS IS THE MAIN SUBSCRIPTION PERSON';


--
-- Name: COLUMN "SS_USERS"."USER_STATUS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_STATUS" IS '0=ACTIVE, 1=BLOCKED, 2=DELETED';


--
-- Name: COLUMN "SS_USERS"."USER_COMMENTS"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_COMMENTS" IS 'COMMENTS';


--
-- Name: COLUMN "SS_USERS"."USER_LAST_LOGIN_DATE"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USERS"."USER_LAST_LOGIN_DATE" IS 'LAST LOGIN DATE AND TIME IN GAIA';


--
-- Name: SS_USER_PHONES; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_USER_PHONES" (
    "USPH_ID" integer NOT NULL,
    "USER_ID" integer NOT NULL,
    "USPH_TYPE" character varying(120) NOT NULL,
    "USPH_NUMBER" character varying(20) NOT NULL,
    "USPH_IS_PREFERRED" boolean DEFAULT false
);


ALTER TABLE public."SS_USER_PHONES" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_USER_PHONES"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_USER_PHONES" IS 'TABLE THAT HOLDS USER PHONE NUMBERS';


--
-- Name: COLUMN "SS_USER_PHONES"."USPH_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_PHONES"."USPH_ID" IS 'PRIMARY KEY OF THE TABLE';


--
-- Name: COLUMN "SS_USER_PHONES"."USER_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_PHONES"."USER_ID" IS 'USER ID THAT OWNS THIS PHONE';


--
-- Name: COLUMN "SS_USER_PHONES"."USPH_TYPE"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_PHONES"."USPH_TYPE" IS 'DESCRIPTION OF PHONE LIKE CELL, HOME, WORK ETC';


--
-- Name: COLUMN "SS_USER_PHONES"."USPH_NUMBER"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_PHONES"."USPH_NUMBER" IS 'THE PHONE NUMBER';


--
-- Name: COLUMN "SS_USER_PHONES"."USPH_IS_PREFERRED"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_PHONES"."USPH_IS_PREFERRED" IS 'IF TRUE THEN THIS IS THE NUMBER WE USE FIRST TO CONTACT THIS PERSON';


--
-- Name: SS_USER_ROLES; Type: TABLE; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE TABLE "SS_USER_ROLES" (
    "USRO_ID" integer NOT NULL,
    "USER_ID" integer NOT NULL,
    "ROLE_ID" integer NOT NULL
);


ALTER TABLE public."SS_USER_ROLES" OWNER TO "gaia-user";

--
-- Name: TABLE "SS_USER_ROLES"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON TABLE "SS_USER_ROLES" IS 'TABLE THAT JOINS USERS AND ROLES';


--
-- Name: COLUMN "SS_USER_ROLES"."USRO_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_ROLES"."USRO_ID" IS 'PRIMARY KEY OF THE TABLE';


--
-- Name: COLUMN "SS_USER_ROLES"."USER_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_ROLES"."USER_ID" IS 'FOREIGN KEY TO THE USERS TABLE';


--
-- Name: COLUMN "SS_USER_ROLES"."ROLE_ID"; Type: COMMENT; Schema: public; Owner: gaia-user
--

COMMENT ON COLUMN "SS_USER_ROLES"."ROLE_ID" IS 'FOREIGN KEY TO THE ROLES TABLE';


--
-- Name: SUBS_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "SUBS_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."SUBS_ID_seq" OWNER TO "gaia-user";

--
-- Name: SUBS_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "SUBS_ID_seq" OWNED BY "SS_SUBSCRIPTIONS"."SUBS_ID";


--
-- Name: SYEL_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "SYEL_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."SYEL_ID_seq" OWNER TO "gaia-user";

--
-- Name: SYEL_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "SYEL_ID_seq" OWNED BY "SS_SYSTEM_ELEMENTS"."SYEL_ID";


--
-- Name: USER_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "USER_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."USER_ID_seq" OWNER TO "gaia-user";

--
-- Name: USER_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "USER_ID_seq" OWNED BY "SS_USERS"."USER_ID";


--
-- Name: USPH_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "USPH_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."USPH_ID_seq" OWNER TO "gaia-user";

--
-- Name: USPH_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "USPH_ID_seq" OWNED BY "SS_USER_PHONES"."USPH_ID";


--
-- Name: USRO_ID_seq; Type: SEQUENCE; Schema: public; Owner: gaia-user
--

CREATE SEQUENCE "USRO_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."USRO_ID_seq" OWNER TO "gaia-user";

--
-- Name: USRO_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gaia-user
--

ALTER SEQUENCE "USRO_ID_seq" OWNED BY "SS_USER_ROLES"."USRO_ID";


--
-- Name: MODU_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_MODULES" ALTER COLUMN "MODU_ID" SET DEFAULT nextval('"MODU_ID_seq"'::regclass);


--
-- Name: MOME_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_MODULES_MENU" ALTER COLUMN "MOME_ID" SET DEFAULT nextval('"MOME_ID_seq"'::regclass);


--
-- Name: MOGR_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_MODULE_GROUPS" ALTER COLUMN "MOGR_ID" SET DEFAULT nextval('"MOGR_ID_seq"'::regclass);


--
-- Name: MOGE_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_MODULE_GROUP_EXCLUSIONS" ALTER COLUMN "MOGE_ID" SET DEFAULT nextval('"MOGE_ID_seq"'::regclass);


--
-- Name: ROLE_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_ROLES" ALTER COLUMN "ROLE_ID" SET DEFAULT nextval('"ROLE_ID_seq"'::regclass);


--
-- Name: ROAR_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_ROLE_ACCESS_RIGHTS" ALTER COLUMN "ROAR_ID" SET DEFAULT nextval('"ROAR_ID_seq"'::regclass);


--
-- Name: SUBS_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_SUBSCRIPTIONS" ALTER COLUMN "SUBS_ID" SET DEFAULT nextval('"SUBS_ID_seq"'::regclass);


--
-- Name: SYEL_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_SYSTEM_ELEMENTS" ALTER COLUMN "SYEL_ID" SET DEFAULT nextval('"SYEL_ID_seq"'::regclass);


--
-- Name: USER_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_USERS" ALTER COLUMN "USER_ID" SET DEFAULT nextval('"USER_ID_seq"'::regclass);


--
-- Name: USPH_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_USER_PHONES" ALTER COLUMN "USPH_ID" SET DEFAULT nextval('"USPH_ID_seq"'::regclass);


--
-- Name: USRO_ID; Type: DEFAULT; Schema: public; Owner: gaia-user
--

ALTER TABLE "SS_USER_ROLES" ALTER COLUMN "USRO_ID" SET DEFAULT nextval('"USRO_ID_seq"'::regclass);


--
-- Name: GRMO_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_GROUPING_MODULES"
    ADD CONSTRAINT "GRMO_PK" PRIMARY KEY ("GRMO_ID");


--
-- Name: MODU_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_MODULES"
    ADD CONSTRAINT "MODU_PK" PRIMARY KEY ("MODU_ID");


--
-- Name: MOGE_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_MODULE_GROUP_EXCLUSIONS"
    ADD CONSTRAINT "MOGE_PK" PRIMARY KEY ("MOGE_ID");


--
-- Name: MOGR_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_MODULE_GROUPS"
    ADD CONSTRAINT "MOGR_PK" PRIMARY KEY ("MOGR_ID");


--
-- Name: MOME_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_MODULES_MENU"
    ADD CONSTRAINT "MOME_PK" PRIMARY KEY ("MOME_ID");


--
-- Name: ROAR_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_ROLE_ACCESS_RIGHTS"
    ADD CONSTRAINT "ROAR_PK" PRIMARY KEY ("ROAR_ID");


--
-- Name: ROLE_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_ROLES"
    ADD CONSTRAINT "ROLE_PK" PRIMARY KEY ("ROLE_ID");


--
-- Name: SUBS_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_SUBSCRIPTIONS"
    ADD CONSTRAINT "SUBS_PK" PRIMARY KEY ("SUBS_ID");


--
-- Name: SYEL_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_SYSTEM_ELEMENTS"
    ADD CONSTRAINT "SYEL_PK" PRIMARY KEY ("SYEL_ID");


--
-- Name: USER_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_USERS"
    ADD CONSTRAINT "USER_PK" PRIMARY KEY ("USER_ID");


--
-- Name: USPH_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_USER_PHONES"
    ADD CONSTRAINT "USPH_PK" PRIMARY KEY ("USPH_ID");


--
-- Name: USRO_PK; Type: CONSTRAINT; Schema: public; Owner: gaia-user; Tablespace: 
--

ALTER TABLE ONLY "SS_USER_ROLES"
    ADD CONSTRAINT "USRO_PK" PRIMARY KEY ("USRO_ID");


--
-- Name: ROAR_ROLE_ID_SYEL_ID_U; Type: INDEX; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE UNIQUE INDEX "ROAR_ROLE_ID_SYEL_ID_U" ON "SS_ROLE_ACCESS_RIGHTS" USING btree ("ROLE_ID", "SYEL_ID");


--
-- Name: ROLE_SUBS_ID_DESCRIPTION_U; Type: INDEX; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE UNIQUE INDEX "ROLE_SUBS_ID_DESCRIPTION_U" ON "SS_ROLES" USING btree ("SUBS_ID", "ROLE_CAPTION");


--
-- Name: USER_SUBS_ID_NAME_U; Type: INDEX; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE UNIQUE INDEX "USER_SUBS_ID_NAME_U" ON "SS_USERS" USING btree ("SUBS_ID", "USER_NAME");


--
-- Name: USRO_USER_ID_ROLE_ID_U; Type: INDEX; Schema: public; Owner: gaia-user; Tablespace: 
--

CREATE UNIQUE INDEX "USRO_USER_ID_ROLE_ID_U" ON "SS_USER_ROLES" USING btree ("USER_ID", "ROLE_ID");


--
-- Name: MODU_GRMO_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_GROUPING_MODULES"
    ADD CONSTRAINT "MODU_GRMO_FK" FOREIGN KEY ("MODU_ID") REFERENCES "SS_MODULES"("MODU_ID");


--
-- Name: MODU_MOME_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_MODULES_MENU"
    ADD CONSTRAINT "MODU_MOME_FK" FOREIGN KEY ("MODU_ID") REFERENCES "SS_MODULES"("MODU_ID");


--
-- Name: MOGR_GRMO_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_GROUPING_MODULES"
    ADD CONSTRAINT "MOGR_GRMO_FK" FOREIGN KEY ("MOGR_ID") REFERENCES "SS_MODULE_GROUPS"("MOGR_ID");


--
-- Name: MOGR_MOGE_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_MODULE_GROUP_EXCLUSIONS"
    ADD CONSTRAINT "MOGR_MOGE_FK" FOREIGN KEY ("MOGR_ID") REFERENCES "SS_MODULE_GROUPS"("MOGR_ID");


--
-- Name: MOGR_MOGE_FK1; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_MODULE_GROUP_EXCLUSIONS"
    ADD CONSTRAINT "MOGR_MOGE_FK1" FOREIGN KEY ("MOGR_ID_EXLUDED") REFERENCES "SS_MODULE_GROUPS"("MOGR_ID");


--
-- Name: MOME_MOME_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_MODULES_MENU"
    ADD CONSTRAINT "MOME_MOME_FK" FOREIGN KEY ("MOME_PARENT_ID") REFERENCES "SS_MODULES_MENU"("MOME_ID");


--
-- Name: ROLE_ROAR_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_ROLE_ACCESS_RIGHTS"
    ADD CONSTRAINT "ROLE_ROAR_FK" FOREIGN KEY ("ROLE_ID") REFERENCES "SS_ROLES"("ROLE_ID");


--
-- Name: ROLE_USRO_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_USER_ROLES"
    ADD CONSTRAINT "ROLE_USRO_FK" FOREIGN KEY ("ROLE_ID") REFERENCES "SS_ROLES"("ROLE_ID");


--
-- Name: SUBS_ROLE_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_ROLES"
    ADD CONSTRAINT "SUBS_ROLE_FK" FOREIGN KEY ("SUBS_ID") REFERENCES "SS_SUBSCRIPTIONS"("SUBS_ID");


--
-- Name: SUBS_USERS_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_USERS"
    ADD CONSTRAINT "SUBS_USERS_FK" FOREIGN KEY ("SUBS_ID") REFERENCES "SS_SUBSCRIPTIONS"("SUBS_ID");


--
-- Name: SYEL_ROAR_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_ROLE_ACCESS_RIGHTS"
    ADD CONSTRAINT "SYEL_ROAR_FK" FOREIGN KEY ("SYEL_ID") REFERENCES "SS_SYSTEM_ELEMENTS"("SYEL_ID");


--
-- Name: USER_USPH_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_USER_PHONES"
    ADD CONSTRAINT "USER_USPH_FK" FOREIGN KEY ("USER_ID") REFERENCES "SS_USERS"("USER_ID");


--
-- Name: USER_USRO_FK; Type: FK CONSTRAINT; Schema: public; Owner: gaia-user
--

ALTER TABLE ONLY "SS_USER_ROLES"
    ADD CONSTRAINT "USER_USRO_FK" FOREIGN KEY ("USER_ID") REFERENCES "SS_USERS"("USER_ID");

