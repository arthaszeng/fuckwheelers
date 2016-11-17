-- // add_vat_to_uk
-- Migration SQL that makes the change goes here.

UPDATE country SET vat=0.2 WHERE name='France';
UPDATE country SET vat=0.22 WHERE name='Italy';
UPDATE country SET vat=0.19 WHERE name='Germany';


-- //@UNDO
-- SQL to undo the change goes here.

UPDATE country SET vat=null WHERE name='France';
UPDATE country SET vat=null WHERE name='Italy';
UPDATE country SET vat=null WHERE name='Germany';