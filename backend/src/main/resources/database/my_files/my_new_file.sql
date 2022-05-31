DROP TABLE new_table;
CREATE TABLE IF NOT EXISTS new_table(
   id serial PRIMARY KEY,
   name varchar,
   created date
) ;

INSERT INTO new_table(name, created) VALUES ('Gustaf josefsen', '2019-01-02');