-- // add_state_to_address
-- Migration SQL that makes the change goes here.
ALTER TABLE address ADD COLUMN state CHARACTER VARYING(255);

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE address DROP COLUMN state;
