INSERT INTO categories(id, caption, name, parent)
VALUES (1, 'Books', 'books', null),
       (2, 'Fiction', 'fiction', 1),
       (3, 'Non-fiction', 'non_fiction', 1),
       (4, 'History', 'history', 3),
       (5, 'Science', 'science', 3),
       (6, 'Cooking', 'cooking', 3),
       (7, 'Vegan', 'vegan', 6),
       (8, 'Egypt', 'egypt', 4),
       (9, 'Second World War', 'second_ww', 4);

INSERT INTO book_categories(id, main_category, book_id, category_id)
VALUES (1, 'Y', 1, 2),
       (2, 'N', 1, 5);