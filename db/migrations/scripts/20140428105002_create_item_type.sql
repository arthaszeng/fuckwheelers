-- // create item type
-- Migration SQL that makes the change goes here.
CREATE TABLE item_type
(
   item_type_id SERIAL PRIMARY KEY,
   name CHARACTER VARYING(64) NOT NULL
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE item_type;
