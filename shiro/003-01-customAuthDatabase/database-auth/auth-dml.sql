INSERT INTO "SS_SUBSCRIPTIONS"("SUBS_NAME", "SUBS_DATABASE") VALUES('foo', 'foo');
INSERT INTO "SS_USERS"("SUBS_ID", "USER_NAME", "USER_PASSWORD", "USER_FIRSTNAME", "USER_SURNAME") (SELECT max("SUBS_ID"), 'bill' , 'gatesfoo' , 'Bill' , 'Gates'    FROM "SS_SUBSCRIPTIONS");
INSERT INTO "SS_USERS"("SUBS_ID", "USER_NAME", "USER_PASSWORD", "USER_FIRSTNAME", "USER_SURNAME") (SELECT max("SUBS_ID"), 'james', 'the_king' , 'James', 'Stewart'  FROM "SS_SUBSCRIPTIONS");
INSERT INTO "SS_USERS"("SUBS_ID", "USER_NAME", "USER_PASSWORD", "USER_FIRSTNAME", "USER_SURNAME") (SELECT max("SUBS_ID"), 'mary' , 'bones'    , 'Mary' , 'Bones'    FROM "SS_SUBSCRIPTIONS");
INSERT INTO "SS_USERS"("SUBS_ID", "USER_NAME", "USER_PASSWORD", "USER_FIRSTNAME", "USER_SURNAME") (SELECT max("SUBS_ID"), 'jack' , 'paranoid' , 'Jack' , 'Paranoid' FROM "SS_SUBSCRIPTIONS");

INSERT INTO "SS_SYSTEM_ELEMENTS"("SYEL_DESCRIPTION") VALUES('secure');
INSERT INTO "SS_SYSTEM_ELEMENTS"("SYEL_DESCRIPTION") VALUES('can_cache');


INSERT INTO "SS_ROLES"("SUBS_ID", "ROLE_CAPTION") (SELECT max("SUBS_ID"), 'staff' FROM "SS_SUBSCRIPTIONS");
INSERT INTO "SS_ROLES"("SUBS_ID", "ROLE_CAPTION") (SELECT max("SUBS_ID"), 'cacher' FROM "SS_SUBSCRIPTIONS");
INSERT INTO "SS_ROLES"("SUBS_ID", "ROLE_CAPTION") (SELECT max("SUBS_ID"), 'admin' FROM "SS_SUBSCRIPTIONS");
INSERT INTO "SS_ROLES"("SUBS_ID", "ROLE_CAPTION") (SELECT max("SUBS_ID"), 'user' FROM "SS_SUBSCRIPTIONS");

INSERT INTO "SS_ROLE_ACCESS_RIGHTS"("ROLE_ID", "SYEL_ID") (SELECT a."ROLE_ID", b."SYEL_ID" FROM "SS_ROLES" a, "SS_SYSTEM_ELEMENTS" b WHERE a."ROLE_CAPTION"='staff' AND b."SYEL_DESCRIPTION"='secure');
INSERT INTO "SS_ROLE_ACCESS_RIGHTS"("ROLE_ID", "SYEL_ID") (SELECT a."ROLE_ID", b."SYEL_ID" FROM "SS_ROLES" a, "SS_SYSTEM_ELEMENTS" b WHERE a."ROLE_CAPTION"='cacher' AND b."SYEL_DESCRIPTION"='can_cache');
INSERT INTO "SS_ROLE_ACCESS_RIGHTS"("ROLE_ID", "SYEL_ID") (SELECT a."ROLE_ID", b."SYEL_ID" FROM "SS_ROLES" a, "SS_SYSTEM_ELEMENTS" b WHERE a."ROLE_CAPTION"='admin' AND b."SYEL_DESCRIPTION"='can_cache');

INSERT INTO "SS_USER_ROLES"("USER_ID", "ROLE_ID") (SELECT a."USER_ID", b."ROLE_ID" FROM "SS_USERS" a, "SS_ROLES" b WHERE a."USER_NAME"='bill'  AND  b."ROLE_CAPTION"='staff' );
INSERT INTO "SS_USER_ROLES"("USER_ID", "ROLE_ID") (SELECT a."USER_ID", b."ROLE_ID" FROM "SS_USERS" a, "SS_ROLES" b WHERE a."USER_NAME"='james' AND  b."ROLE_CAPTION"='admin' );
INSERT INTO "SS_USER_ROLES"("USER_ID", "ROLE_ID") (SELECT a."USER_ID", b."ROLE_ID" FROM "SS_USERS" a, "SS_ROLES" b WHERE a."USER_NAME"='mary'  AND  b."ROLE_CAPTION"='user'  );
INSERT INTO "SS_USER_ROLES"("USER_ID", "ROLE_ID") (SELECT a."USER_ID", b."ROLE_ID" FROM "SS_USERS" a, "SS_ROLES" b WHERE a."USER_NAME"='jack'  AND  b."ROLE_CAPTION"='cacher');
