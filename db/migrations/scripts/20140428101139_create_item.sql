-- // create item
-- Migration SQL that makes the change goes here.

CREATE TABLE item (
   item_id SERIAL PRIMARY KEY,
   description CHARACTER VARYING(255) NOT NULL,
   name CHARACTER VARYING(255) NOT NULL,
   price NUMERIC(19,2) NOT NULL,
   type CHARACTER VARYING(255) NOT NULL,
   quantity BIGINT NOT NULL
 );

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE item;
