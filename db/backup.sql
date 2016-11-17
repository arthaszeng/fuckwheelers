
--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'LATIN1';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO account VALUES (1, 'AdminCat', 'admin@example.com', 'Yellow bikes are just amazingly awesome, right? Says Robert, my good friend.', '', true);
INSERT INTO account VALUES (2, 'UserCat', 'user@example.com', 'Part 3: Tall zebra mobile responsive communication patterns.', '', true);
INSERT INTO account VALUES (6, 'Michael Stipe', 'mstipe@example.com', 'REMisAwesome', '1234567890', true);
INSERT INTO account VALUES (8, 'Peter Buck', 'peter-buck@example.com', 'P3t3r', '1234567890', true);
INSERT INTO account VALUES (7, 'Scott McCaughey', 'smccaughey@example.com', 'ScottScottScott', '1234567890', true);
INSERT INTO account VALUES (9, 'Bill Rieflin', 'billrieflin@example.com', 'YoudontKNOW!!!', '', true);
INSERT INTO account VALUES (10, 'SurveyUser', 'survey@example.com', 'survey, the quintessence of wisdom and learning. Donkey donkey fishtail!', '', true);


--
-- Name: account_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('account_account_id_seq', (SELECT MAX(account_id) FROM account), true);


--
-- Data for Name: account_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO account_role VALUES (1, 'AdminCat', 'ROLE_ADMIN');
INSERT INTO account_role VALUES (2, 'UserCat', 'ROLE_USER');
INSERT INTO account_role VALUES (4, 'Michael Stipe', 'ROLE_USER');
INSERT INTO account_role VALUES (5, 'Scott McCaughey', 'ROLE_USER');
INSERT INTO account_role VALUES (6, 'Peter Buck', 'ROLE_USER');
INSERT INTO account_role VALUES (7, 'Bill Rieflin', 'ROLE_USER');
INSERT INTO account_role VALUES (8, 'SurveyUser', 'ROLE_USER');


--
-- Name: account_role_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('account_role_role_id_seq', (SELECT MAX(role_id) FROM account_role), true);


--
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO item VALUES (9, 'If you''re looking to develop your in-line skills or simply want to roll over any obstacle with ease, then this economically priced, 29er frame will keep you riding with speed and control.', '2011 Access XCL 9er', 179.99, 'FRAME', 521);
INSERT INTO item VALUES (7, 'Get ready to be impressed. With a fit and finish that''s nearly flawless, this 7005 butted aluminum frameset is in a class of its own when it comes to high-end performance at a great value.', 'Scattante XRL Comp Road Bike', 289.99, 'FRAME', 787);
INSERT INTO item VALUES (6, 'Fuji''s Altamira CX 1.0 cyclocross frameset is the perfect platform for building up the cross bike of your dreams.', 'Fuji Altamira CX 1.0 Cyclocross', 2599.99, 'FRAME', 432);
INSERT INTO item VALUES (12, 'Replacement alloy derailleur hanger for Access XCL frames. ', 'Derailleur Hanger for Access XCL and XCL 29er Frames', 10.00, 'ACCESSORIES', 1010);
INSERT INTO item VALUES (11, 'Shimano''s successful introduction of Di2 digital electronic shifting in its Dura-Ace group heralded a new era of shifting performance for the early adopters who jumped in with both feet.', 'Shimano Ultegra Di2 Upgrade Kit', 1299.99, 'ACCESSORIES', 599);
INSERT INTO item VALUES (13, 'Frame protection at its best! Small: Standard Large: Jumbo ', 'Lizard Skins Chainstay Guard', 7.99, 'ACCESSORIES', 319);
INSERT INTO item VALUES (8, 'If you crave simplicity in your cycling life -- but like to express your individuality -- building up your own, customized single-speed road bike could well be the answer. And the Ascent single-speed road bike frame is a great place to start.', 'Ascent Single-Speed Road Bike', 89.99, 'FRAME', 1210);
INSERT INTO item VALUES (10, 'If fast and furious is how you like to ride, then this winning cyclocross frameset is exactly what you need.', 'Scattante XRL Cyclocross Frame', 279.99, 'FRAME', 537);


--
-- Name: item_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('item_item_id_seq', (SELECT MAX(item_id) FROM item), true);

--
-- Name: item_type_item_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('item_type_item_type_id_seq', (SELECT MAX(item_type_id) FROM item_type), true);


--
-- Data for Name: reserve_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO reserve_order VALUES (11, 8, 7, 'NEW', '', '2013-03-28 12:19:10.021');
INSERT INTO reserve_order VALUES (14, 6, 12, 'NEW', '', '2013-03-28 12:20:19.721');
INSERT INTO reserve_order VALUES (13, 6, 6, 'NEW', '', '2013-03-27 09:20:14.127');
INSERT INTO reserve_order VALUES (15, 6, 11, 'NEW', '', '2013-03-22 16:02:22.875');
INSERT INTO reserve_order VALUES (12, 8, 13, 'PAID', '', '2013-03-20 08:11:21.983');
INSERT INTO reserve_order VALUES (18, 7, 10, 'READY_FOR_SHIPMENT', '', '2013-03-19 22:21:28.171');
INSERT INTO reserve_order VALUES (17, 7, 8, 'IN_PROGRESS', '', '2013-03-28 12:21:24.155');
INSERT INTO reserve_order VALUES (16, 6, 13, 'NEW', '', '2013-03-26 20:14:25.336');


--
-- Name: reserve_order_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('reserve_order_order_id_seq', (SELECT MAX(order_id) FROM reserve_order), true);


--
-- Data for Name: survey_entry; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 2,'This site is really confusing. I wouldn''t recommend it. The price is right, but its all too hard.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 2,'Did I buy something? It''s not clear.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 0,'It seemed to be a lot of bother, really hard to use. You need to fix it.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 3,'This doesn''t feel safe. Are you encrypting my password? Why can I see it? Is it stored like that?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 3,'There are many sites which do this better.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 6,'Meh. Your site is ok.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 9,'I loved the sound of the products on offer.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 6,'Did I buy a bike? What happens next?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 7,'It''s ok.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 5,'Not great.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 2,'Am I getting a bike or not? I can''t tell. What does "reserved" mean?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 4,'I wish I could have seen pictures of the bikes.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 9,'Can''t wait for my new frame!'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 8,'Good.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 5,'OK'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 6,''
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 4,'That was terrible. Did I just buy something?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 5,''
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 1,'I would never recommend this site to anyone. Sorry.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 5,''
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 5,'Why don''t you have pictures?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 1,'I don''t understand what just happened.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 1,'Aren''t you going to email me or something? Thank goodness I didn''t pay for anything'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 2,''
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 1,'Why do I have to create an account to make a purchase?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
 SELECT account.account_id, 2,'It''s so unclear where I go, what''s an admin profile?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 1,'Too hard to use.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 2,'Difficult.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
 SELECT account.account_id, 1,'You need to make this less painful. I have no idea what just happened'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 2,''
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 1,'Hard to use.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 2,''
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 1,'I''m so confused.'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 3,''
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 4,'Should I be seeing images?'
FROM account WHERE account.account_name = 'SurveyUser';

INSERT INTO survey_entry (account_id, rating, comment)
SELECT account.account_id, 7,'It''s OK. Wish I didn''t have to log in after creating an account though'
FROM account WHERE account.account_name = 'SurveyUser';

--
-- PostgreSQL database dump complete
--
