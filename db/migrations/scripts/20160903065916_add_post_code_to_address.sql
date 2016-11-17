-- // add_post_code_to_address
-- Migration SQL that makes the change goes here.

ALTER TABLE address ADD COLUMN post_code CHARACTER VARYING(255) NULL;


-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE address DROP COLUMN post_code;

