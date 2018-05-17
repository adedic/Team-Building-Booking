INSERT INTO reservation (id, date_last_edited, date_of_reservation, ends_at, starts_at, offer_id, user_id)
VALUES (1, CURRENT_DATE - 1, CURRENT_DATE - 2, CURRENT_DATE + 9, CURRENT_DATE + 2, 1, 3);