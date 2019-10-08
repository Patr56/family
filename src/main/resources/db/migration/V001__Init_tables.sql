CREATE TABLE person(
    person_id serial PRIMARY KEY,
    name_first varchar (255) NOT NULL,
    name_middle varchar (255),
    name_last varchar (255) NOT NULL,
    birthday date,
    death date,
    sex varchar (10) NOT NULL,
    description text
);

CREATE TABLE contact_information(
    contact_information_id serial PRIMARY KEY,
    person_id integer REFERENCES person,
    code varchar(255) NOT NULL,
    value varchar (255) NOT NULL
);

CREATE TABLE photo(
    photo_id serial PRIMARY KEY,
    url varchar (1024),
    type varchar (4) NOT NULL
);

CREATE TABLE photo_information(
    photo_information_id serial PRIMARY KEY,
    photo_id integer REFERENCES photo,
    person_id integer REFERENCES person,
    description text,
    area_top_left_x real NOT NULL,
    area_top_left_y real NOT NULL,
    area_bottom_right_x real NOT NULL,
    area_bottom_right_y real NOT NULL
);

CREATE TABLE relationship(
    relationship_id serial PRIMARY KEY,
    person_id integer REFERENCES person,
    related_id integer REFERENCES person,
    type varchar (10) NOT NULL
);