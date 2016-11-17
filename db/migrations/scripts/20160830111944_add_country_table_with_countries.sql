-- // add_country_table_with_countries
-- Migration SQL that makes the change goes here.

CREATE TABLE country
(
      country_id SERIAL PRIMARY KEY,
      name CHARACTER VARYING(255) NOT NULL
);

INSERT INTO country (name) VALUES ('USA');
INSERT INTO country (name) VALUES ('UK');
INSERT INTO country (name) VALUES ('Germany');
INSERT INTO country (name) VALUES ('Canada');
INSERT INTO country (name) VALUES ('Italy');
INSERT INTO country (name) VALUES ('France');


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE country;
