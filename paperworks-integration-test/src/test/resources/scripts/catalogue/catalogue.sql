INSERT INTO authors (id, bio, display_name, first_name, last_name, middle_name)
VALUES (1, 'Sample Bio', 'Hubert Cumberdale', 'Hubert', 'Cumberdale', null),
       (2, null, 'Marjory Baxter', 'Marjory', 'Baxter', 'Steward');

INSERT INTO publishers (id, publisher, overview)
VALUES (1, 'Sample Publisher', 'Some Bio 1999');

INSERT INTO language (code, language)
VALUES ('en', 'english'),
       ('sk', 'slovak'),
       ('de', 'german');

INSERT INTO books (id, cat_id, date_created, isbn_10, isbn_13, overview, page_count, title, weight, year_published, language_code, publisher_id)
VALUES (1, 123456789, now(), null, null, null, null, 'Sample Book', null, 1999, 'en', 1);