INSERT INTO user_book_ratings(id, score, book, reviewer)
VALUES (1, 5, 1, 1),
       (2, 3, 1, 2),
       (3, 4, 1, 3),
       (4, 1, 1, 4);

INSERT INTO user_book_reviews(id, text, book, reviewer, date_created, date_updated)
VALUES (1, 'This is a great book', 1, 1, '2023-01-01', null),
       (2, 'This is not a good book', 1, 4, now(), null),
       (3, 'Sample review text.', 1, 3, now(), null);