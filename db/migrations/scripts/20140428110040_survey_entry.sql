-- // survey entry
-- Migration SQL that makes the change goes here.
CREATE TABLE survey_entry
(
  survey_entry_id SERIAL PRIMARY KEY,
  account_id BIGINT NOT NULL,
  rating INT NOT NULL,
  comment CHARACTER VARYING(255)
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE survey_entry;
