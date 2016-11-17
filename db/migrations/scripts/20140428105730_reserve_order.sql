-- // reserve order
-- Migration SQL that makes the change goes here.
CREATE TABLE reserve_order
(
  order_id SERIAL PRIMARY KEY,
  account_id BIGINT NOT NULL,
  item_id BIGINT NOT NULL,
  status CHARACTER VARYING(255) NOT NULL,
  note CHARACTER VARYING(255) NOT NULL,
  reservation_timestamp TIMESTAMP without time zone NOT NULL
);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE reserve_order;
