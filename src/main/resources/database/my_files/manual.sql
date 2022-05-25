INSERT INTO short_position(id, instrument, opened, closed, active) VALUES (1, 'NO0003053605', 2021-01-01, 2022-01-01, false);

SELECT closed FROM instrument INNER JOIN short_position ON instrument.isin = short_position.instrument;