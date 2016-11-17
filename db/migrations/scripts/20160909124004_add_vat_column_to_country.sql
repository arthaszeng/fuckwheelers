-- // add_vat_column_to_country
-- Migration SQL that makes the change goes here.

ALTER TABLE country ADD vat FLOAT NULL;


-- //@UNDO
-- SQL to undo the change goes here.


ALTER TABLE country DROP COLUMN vat;

