-- // create_address
-- Migration SQL that makes the change goes here.
CREATE TABLE address
(
      address_id SERIAL PRIMARY KEY,
      street_one CHARACTER VARYING(255) NOT NULL,
      street_two CHARACTER VARYING(255)
);


-- //@UNDO
-- SQL to undo the change goes here.


DROP TABLE address;