-- // create account
-- Migration SQL that makes the change goes here.
CREATE TABLE account
(
      account_id SERIAL PRIMARY KEY,
      account_name CHARACTER VARYING(255) NOT NULL,
      email_address CHARACTER VARYING(255) NOT NULL UNIQUE,
      password CHARACTER VARYING(255) NOT NULL,
      phone_number CHARACTER VARYING(32) NOT NULL,
      enabled BOOLEAN NOT NULL
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE account;
