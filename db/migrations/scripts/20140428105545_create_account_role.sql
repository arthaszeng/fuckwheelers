-- // create account role
-- Migration SQL that makes the change goes here.
CREATE TABLE account_role
(
  role_id SERIAL PRIMARY KEY,
  account_name CHARACTER VARYING(255) NOT NULL,
  role CHARACTER VARYING(255) NOT NULL
);

-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE account_role;
