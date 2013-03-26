drop table measureactions.a cascade ;
create table measureactions.a 
(a1 integer not null,
 a2 integer not null,
 a3 integer not null,
 v varchar,
 constraint a_pk_a1_a2 primary key (a1, a2),
 constraint a_self_ref_a1_a2 foreign key (a2, a3) references measureactions.a(a1,a2));
 
 
 drop table if exists measureactions.b;
 
 create table measureactions.b
 (b1 integer not null,
  ba1 integer not null,
  ba2 integer not null,
  constraint b_pk_b1 primary key (b1),
  constraint b_2_a_fk foreign key (ba1, ba2) references measureactions.a(a1,a2));