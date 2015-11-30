DROP TABLE UserInputRecord;
DROP TABLE MLOutputRecord;
DROP TABLE Style;


CREATE TABLE Style (
    id              SERIAL      PRIMARY KEY,
    name            TEXT        UNIQUE NOT NULL,
    imageUrl        TEXT UNIQUE NOT NULL
);

CREATE TABLE UserInputRecord (
    id              SERIAL      PRIMARY KEY,
    username TEXT,
    imageUrl        TEXT UNIQUE NOT NULL,
    style INT REFERENCES Style
);

CREATE TABLE MLOutputRecord (
    id              SERIAL      PRIMARY KEY,
    imageUrl        TEXT UNIQUE,
    status TEXT,
    username TEXT,
    userInputRecord INT REFERENCES UserInputRecord
);