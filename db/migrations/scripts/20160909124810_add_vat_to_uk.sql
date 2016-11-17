-- // add_vat_to_uk
-- Migration SQL that makes the change goes here.

UPDATE country SET vat=0.2 WHERE name='UK';

-- //@UNDO
-- SQL to undo the change goes here.

UPDATE COUNTRY SET vat=null WHERE name='UK';