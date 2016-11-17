-- // create_default_accounts_and_item_types
-- Migration SQL that makes the change goes here.
INSERT INTO account (email_address, account_name, password, phone_number, enabled)
	    VALUES ('admin@example.com', 'AdminCat','Yellow bikes are just amazingly awesome, right? Says Robert, my good friend.', '', true)
	    ;
INSERT INTO account (email_address, account_name, password, phone_number, enabled)
	    VALUES ('user@example.com', 'UserCat','Part 3: Tall zebra mobile responsive communication patterns.', '', true);

INSERT INTO account_role (account_name, role) VALUES ('AdminCat', 'ROLE_ADMIN');
INSERT INTO account_role (account_name, role) VALUES ('UserCat', 'ROLE_USER');

INSERT INTO item_type (name) VALUES ('Frames');
INSERT INTO item_type (name) VALUES ('Accessories');


-- //@UNDO
-- SQL to undo the change goes here.

DELETE FROM account WHERE email_address IN ('admin@example.com', 'user@example.com');
DELETE FROM item_type WHERE name IN ('Frames', 'Accessories');