CREATE TABLE IF NOT EXISTS instrument(
    is_in varchar PRIMARY KEY,
    issuer_name varchar(255)
);

CREATE TABLE IF NOT EXISTS shorter(
    id integer PRIMARY KEY,
    company_name varchar(255)
);

INSERT INTO instrument VALUES ('123ksddnaw', 'XXL'), ('123sdnsadwal', 'ABG');
INSERT INTO shorter VALUES (2, 'Shorter Capital Management'), (1, 'Voleon Capital');