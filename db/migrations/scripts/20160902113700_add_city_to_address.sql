-- // add_city_to_address
-- Migration SQL that makes the change goes here.

ALTER TABLE address ADD COLUMN city CHARACTER VARYING(255) NULL;

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE address DROP COLUMN city;
