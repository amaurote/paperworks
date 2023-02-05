INSERT INTO users (id, active, email, enabled, login_by_email, password, username)
VALUES (1, true, 'simone.j@test.sk', true, true, null, 'simonej'),
       (2, true, 'maja.olsen@test.sk', true, true, null, 'majaolsen'),
       (3, true, 'barney.pitts@test.sk', true, true, null, 'barney45'),
       (4, true, 'sadia.cain@test.sk', true, true, null, 'cain12'),
       (5, true, 'tatiana.sokolova@test.sk', true, true, null, 'sokolova1'),
       (6, true, 'joyce.waller@test.sk', true, true, null, 'jw123'),
       (7, true, 'niki.manda@test.sk', true, true, null, 'nikim'),
       (8, true, 'ben.nolan@test.sk', true, true, null, 'benolan'),
       (99, true, 'admin@admin.sk', true, true, null, 'admin');

INSERT INTO roles (id, role_desc, role_name)
VALUES (1, '', 'ROLE_ADMIN'),
       (2, '', 'ROLE_USER');