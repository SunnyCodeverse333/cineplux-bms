-- =========================
-- ROLES (ONLY REQUIRED DATA)
-- =========================
INSERT INTO roles (id, name, description) VALUES
                                              (1, 'ROLE_USER', 'Normal user'),
                                              (2, 'ROLE_ADMIN', 'Admin user');


-- =========================
-- OPTIONAL: SAMPLE DATA (NON-AUTH)
-- =========================

-- CITIES
INSERT INTO cities (id, name, state) VALUES
                                         (1, 'Chennai', 'Tamil Nadu'),
                                         (2, 'Bangalore', 'Karnataka');

-- THEATRES
INSERT INTO theatres (id, name, address, city_id) VALUES
                                                      (1, 'PVR Velachery', 'Phoenix Mall, Chennai', 1),
                                                      (2, 'INOX Marina Mall', 'OMR, Chennai', 1),
                                                      (3, 'SPI Palazzo', 'Forum Mall, Chennai', 1),
                                                      (4, 'PVR Orion', 'Rajajinagar, Bangalore', 2);

-- SCREENS
INSERT INTO screens (id, name, total_seats, theater_id) VALUES
                                                            (1, 'Audi 1', 20, 1),
                                                            (2, 'Audi 2', 20, 1),
                                                            (3, 'Audi 1', 20, 2),
                                                            (4, 'Audi 2', 20, 2),
                                                            (5, 'Audi 1', 20, 3),
                                                            (6, 'Audi 1', 20, 4);

-- MOVIES
INSERT INTO movies
(id, title, genre, language, duration_minutes, rating, release_date, poster_url)
VALUES
    (1, 'Inception', 'Sci-Fi', 'English', 148, 8.8, '2010-07-16',
     'https://upload.wikimedia.org/wikipedia/en/c/c1/Inception_ver3.jpg'),

    (2, 'Interstellar', 'Sci-Fi', 'English', 169, 9.0, '2014-11-07',
     'https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg'),

    (3, 'Leo', 'Action', 'Tamil', 160, 7.5, '2023-10-19',
     'https://upload.wikimedia.org/wikipedia/en/7/75/Leo_%282023_Indian_film%29.jpg'),

    (4, 'Avengers Endgame', 'Action', 'English', 181, 8.4, '2019-04-26',
     'https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg');

-- SHOWS
INSERT INTO shows
(id, screen_id, movie_id, show_date, start_time, end_time, ticket_price)
VALUES
    (1, 1, 1, '2026-04-10', '10:00:00', '12:30:00', 250),
    (2, 1, 2, '2026-04-10', '14:00:00', '17:00:00', 300),
    (3, 2, 3, '2026-04-10', '18:00:00', '21:00:00', 200),
    (4, 3, 4, '2026-04-10', '11:00:00', '14:00:00', 350);

-- SEATS (BASE SCREEN)
-- SEATS (BASE SCREEN) - Using numeric numbers to match JS logic
INSERT INTO seats (seat_number, seat_row, seat_col, seat_type, screen_id) VALUES
                                                                              ('1','A',1,'REGULAR',1),('2','A',2,'REGULAR',1),('3','A',3,'REGULAR',1),('4','A',4,'REGULAR',1),('5','A',5,'REGULAR',1),
                                                                              ('6','A',6,'REGULAR',1),('7','A',7,'REGULAR',1),('8','A',8,'REGULAR',1),('9','A',9,'REGULAR',1),('10','A',10,'REGULAR',1),
                                                                              ('11','B',1,'PREMIUM',1),('12','B',2,'PREMIUM',1),('13','B',3,'PREMIUM',1),('14','B',4,'PREMIUM',1),('15','B',5,'PREMIUM',1),
                                                                              ('16','B',6,'PREMIUM',1),('17','B',7,'PREMIUM',1),('18','B',8,'PREMIUM',1),('19','B',9,'PREMIUM',1),('20','B',10,'PREMIUM',1);

-- (The rest of your COPY SEATS logic will work fine as-is)
-- COPY SEATS TO OTHER SCREENS
INSERT INTO seats (seat_number, seat_row, seat_col, seat_type, screen_id)
SELECT seat_number, seat_row, seat_col, seat_type, 2 FROM seats WHERE screen_id = 1;

INSERT INTO seats (seat_number, seat_row, seat_col, seat_type, screen_id)
SELECT seat_number, seat_row, seat_col, seat_type, 3 FROM seats WHERE screen_id = 1;

INSERT INTO seats (seat_number, seat_row, seat_col, seat_type, screen_id)
SELECT seat_number, seat_row, seat_col, seat_type, 4 FROM seats WHERE screen_id = 1;

INSERT INTO seats (seat_number, seat_row, seat_col, seat_type, screen_id)
SELECT seat_number, seat_row, seat_col, seat_type, 5 FROM seats WHERE screen_id = 1;

INSERT INTO seats (seat_number, seat_row, seat_col, seat_type, screen_id)
SELECT seat_number, seat_row, seat_col, seat_type, 6 FROM seats WHERE screen_id = 1;