-- // update_password
-- Migration SQL that makes the change goes here.
UPDATE account
SET password = 'Yellow bikes are just amazingly awesome, right? Says Robert, my good friend.'
WHERE account_name = 'AdminCat';

UPDATE account
SET password = 'Part 3: Tall zebra mobile responsive communication patterns.'
WHERE account_name = 'UserCat';


-- //@UNDO
-- SQL to undo the change goes here.
UPDATE account
SET password = 'Yellow bikes are just amazingly awesome, right? Says Robert, my good friend!'
WHERE account_name = 'AdminCat';

UPDATE account
SET password = 'Part 3: Tall zebra mobile responsive communication patterns!'
WHERE account_name = 'UserCat';

