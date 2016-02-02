-- queries copied from Chapter 9 of the IVOA Registry Relational Schema Version 1.0 Specification:
-- at the time of this writing a simple page to perform queries is made available on:
--     http://localhost:8080/regtap/tap/
-- (when the war is published)


-- [1]
-- the following:
SELECT ivoid, access_url FROM rr.capability NATURAL JOIN rr.interface WHERE standard_id='ivo://ivoa.net/std/tap' AND intf_role='std'
-- failed with: adql.db.exception.UnresolvedIdentifiersException: 1 unresolved identifiers: ivoid [l.1 c.8 - l.1 c.13] !
-- and had to be replaced with:
SELECT i.ivoid, access_url FROM rr.capability NATURAL JOIN rr.interface i WHERE standard_id='ivo://ivoa.net/std/tap' AND intf_role='std'
-- ... which executed OK (but did not produce any rows)

-- [2]
-- the following:
SELECT ivoid, access_url
FROM rr.capability
NATURAL JOIN rr.resource
NATURAL JOIN rr.interface
NATURAL JOIN rr.res_subject
WHERE standard_id='ivo://ivoa.net/std/sia'
AND intf_role='std'
AND (
1=ivo_nocasematch(res_subject, '%spiral%')
OR 1=ivo_hasword(res_description, 'spiral')
OR 1=ivo_hasword(res_title, 'spiral'))
-- failed with: adql.db.exception.UnresolvedIdentifiersException: 1 unresolved identifier: ivoid [l.1 c.8 - l.1 c.13]!
-- and had to be replaced with:
SELECT r.ivoid, access_url
FROM rr.capability
NATURAL JOIN rr.resource r
NATURAL JOIN rr.interface
NATURAL JOIN rr.res_subject
WHERE standard_id='ivo://ivoa.net/std/sia'
AND intf_role='std'
AND (
1=ivo_nocasematch(res_subject, '%spiral%')
OR 1=ivo_hasword(res_description, 'spiral')
OR 1=ivo_hasword(res_title, 'spiral'))
-- which executed OK (and produced many rows)


-- [3]
-- the following:
SELECT ivoid, access_url
FROM rr.capability
NATURAL JOIN rr.resource
NATURAL JOIN rr.interface
NATURAL JOIN rr.res_subject
WHERE standard_id='ivo://ivoa.net/std/sia'
AND intf_role='std'
AND (
1=ivo_nocasematch(res_subject, '%spiral%')
OR 1=ivo_hasword(res_description, 'spiral')
OR 1=ivo_hasword(res_title, 'spiral'))
-- failed with: adql.db.exception.UnresolvedIdentifiersException: 1 unresolved identifier: ivoid [l.1 c.8 - l.1 c.13] !
-- and had to be replaced with:

SELECT r.ivoid, access_url
FROM rr.capability
NATURAL JOIN rr.resource r
NATURAL JOIN rr.interface
NATURAL JOIN rr.res_subject
WHERE standard_id='ivo://ivoa.net/std/sia'
AND intf_role='std'
AND (
1=ivo_nocasematch(res_subject, '%spiral%')
OR 1=ivo_hasword(res_description, 'spiral')
OR 1=ivo_hasword(res_title, 'spiral'))
-- which executed OK (and produced many rows)

-- [4]
-- the following:
SELECT ivoid, access_url
FROM rr.capability
NATURAL JOIN rr.resource
NATURAL JOIN rr.interface
WHERE standard_id='ivo://ivoa.net/std/sia'
AND intf_role='std'
AND 1=ivo_hashlist_has('infrared', waveband)
-- failed with: adql.db.exception.UnresolvedIdentifiersException: 1 unresolved identifiers: ivoid [l.1 c.8 - l.1 c.13] !
-- and had to be changed to:
SELECT r.ivoid, access_url
FROM rr.capability
NATURAL JOIN rr.resource r
NATURAL JOIN rr.interface
WHERE standard_id='ivo://ivoa.net/std/sia'
AND intf_role='std'
AND 1=ivo_hashlist_has('infrared', waveband)
-- which executed OK (but did not produce any rows)

-- [5]
-- the following:
SELECT ivoid, access_url
FROM rr.capability
NATURAL JOIN rr.resource
NATURAL JOIN rr.interface
WHERE standard_id='ivo://ivoa.net/std/sia'
AND intf_role='std'
AND 1=ivo_hashlist_has('infrared', waveband)
-- failed with: adql.db.exception.UnresolvedIdentifiersException: 1 unresolved identifiers: ivoid [l.1 c.8 - l.1 c.13] !
-- and had to change to:
SELECT c.ivoid, access_url
FROM rr.capability c
NATURAL JOIN rr.table_column
NATURAL JOIN rr.interface
WHERE standard_id='ivo://ivoa.net/std/conesearch'
AND intf_role='std'
AND ucd='src.redshift'
-- which executed OK (but did not produce any rows)

-- [6]
-- the following:
SELECT ivoid
FROM rr.resource
WHERE ivoid LIKE 'ivo://org.gavo.dc%'
-- executed OK and produced many rows

-- [7]
-- the following:
SELECT ivoid
FROM rr.res_role
WHERE 1=ivo_nocasematch(role_name, '%gavo%')
AND base_role='publisher'
-- executed OK and produced many rows

-- [8]
-- the following:
SELECT ivoid
FROM rr.res_role
WHERE role_ivoid='ivo://ned.ipac/ned'
AND base_role='publisher'
-- executed OK and produced many rows

-- [9]
-- the following:
SELECT ivoid FROM rr.resource
RIGHT OUTER JOIN (
SELECT 'ivo://' || detail_value || '%' AS pat FROM rr.res_detail
WHERE detail_xpath='/managedAuthority'
AND ivoid='ivo://cds.vizier/registry')
AS authpatterns
ON (resource.ivoid LIKE authpatterns.pat)
-- executed OK (but did not produce any rows)

-- [10]
-- the following:
SELECT access_url
FROM rr.interface
NATURAL JOIN rr.capability
NATURAL JOIN rr.res_detail
WHERE standard_id='ivo://ivoa.net/std/tap'
AND intf_role='std'
AND detail_xpath='/capability/dataModel/@ivo-id'
AND 1=ivo_nocasematch(detail_value, 'ivo://ivoa.net/std/regtap/vor')
-- executed OK (but did not produce any rows)

-- [11]
-- the following:
SELECT ivoid, access_url, name, ucd, column_description
FROM rr.capability
NATURAL JOIN rr.interface
NATURAL JOIN rr.table_column
NATURAL JOIN rr.res_table
WHERE standard_id='ivo://ivoa.net/std/tap'
AND intf_role='std'
AND 1=ivo_hasword(table_description, 'quasar')
AND ucd='phot.mag;em.opt.v'
-- failed with: adql.db.exception.UnresolvedIdentifiersException: 1 unresolved identifiers: ivoid [l.1 c.8 - l.1 c.13] !
-- and had to be replaced with:
SELECT c.ivoid, access_url, name, ucd, column_description
FROM rr.capability c
NATURAL JOIN rr.interface
NATURAL JOIN rr.table_column
NATURAL JOIN rr.res_table
WHERE standard_id='ivo://ivoa.net/std/tap'
AND intf_role='std'
AND 1=ivo_hasword(table_description, 'quasar')
AND ucd='phot.mag;em.opt.v'
-- which executed OK (but did not produce any rows)

-- [12]
-- the following:
SELECT access_url
FROM rr.res_detail
NATURAL JOIN rr.capability
NATURAL JOIN rr.interface
WHERE detail_xpath='/capability/dataSource'
AND intf_role='std'
AND standard_id='ivo://ivoa.net/std/ssa'
AND detail_value='theory'
-- executed OK (but did not produce any rows)

-- [13]
-- the following:
SELECT DISTINCT base_role, role_name, email
FROM rr.res_role
NATURAL JOIN rr.interface
WHERE access_url='http://dc.zah.uni-heidelberg.de/__system__/tap/run/tap'
-- executed OK (and produced 3 rows)

-- [14]
-- the following:
SELECT *
FROM rr.relationship AS a
JOIN rr.capability AS b
ON (a.related_id=b.ivoid)
WHERE relationship_type='served-by'
-- executed OK (and produced many rows)

