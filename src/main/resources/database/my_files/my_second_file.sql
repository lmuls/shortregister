CREATE TABLE IF NOT EXISTS instrument(
    is_in varchar PRIMARY KEY,
    issuer_name varchar(255)
);

CREATE TABLE IF NOT EXISTS shorter(
    id integer PRIMARY KEY,
    company_name varchar(255)
);

INSERT INTO instrument VALUES ('jdasld2e19r1', 'Skippermakeren'), ('213sad212', 'Skomakeren');
INSERT INTO shorter VALUES (4, 'Shortest Capital Management'), (3, 'Mogo Capital');