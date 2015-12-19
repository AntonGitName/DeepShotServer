DROP TABLE IF EXISTS MLOutputRecord;
DROP TABLE IF EXISTS UserInputRecord;
DROP TABLE IF EXISTS Style;

CREATE TABLE Style (
    id              INTEGER      PRIMARY KEY,
    name            TEXT        UNIQUE NOT NULL,
    imageUrl        TEXT UNIQUE NOT NULL
);

CREATE TABLE UserInputRecord (
    id              INTEGER      PRIMARY KEY,
    username TEXT,
    imageUrl        TEXT UNIQUE NOT NULL,
    style INT REFERENCES Style
);

CREATE TABLE MLOutputRecord (
    id              INTEGER      PRIMARY KEY,
    imageUrl        TEXT UNIQUE,
    status TEXT,
    userInputRecord INT REFERENCES UserInputRecord
);