CREATE DATABASE movie_pg ENCODING 'UTF8';
CREATE TABLE "movie"
(
    "id"           serial                  NOT NULL,
    "title"        character varying(100)  NOT NULL,
    "release_year" int                     NOT NULL,
    "director"     character varying(100)  NOT NULL,
    "description"  character varying(1000) NOT NULL,
    "country_id"   int                     NOT NULL,
    "created"      TIMESTAMP               NOT NULL,
    "updated"      TIMESTAMP               NOT NULL,
    CONSTRAINT "movie_pk" PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE "user_account"
(
    "id"        serial                 NOT NULL,
    "user_name" character varying(100) NOT NULL,
    "password"  character varying(255) NOT NULL,
    "created"   TIMESTAMP              NOT NULL,
    "updated"   TIMESTAMP              NOT NULL,
    "role_id"   int                    NOT NULL,
    CONSTRAINT "user_account_pk" PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE "genre"
(
    "id"         serial                 NOT NULL,
    "genre_name" character varying(100) NOT NULL,
    "created"    TIMESTAMP              NOT NULL,
    "updated"    TIMESTAMP              NOT NULL,
    CONSTRAINT "genre_pk" PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE "role"
(
    "id"        serial                 NOT NULL,
    "role_name" character varying(100) NOT NULL,
    "created"   TIMESTAMP              NOT NULL,
    "updated"   TIMESTAMP              NOT NULL,
    CONSTRAINT "role_pk" PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE "movie_genre"
(
    "movie_id" int NOT NULL,
    "genre_id" int NOT NULL,
    CONSTRAINT "movie_genre_pk" PRIMARY KEY ("movie_id", "genre_id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE "country"
(
    "id"           serial                 NOT NULL,
    "country_name" character varying(100) NOT NULL,
    "created"      TIMESTAMP              NOT NULL,
    "updated"      TIMESTAMP              NOT NULL,
    CONSTRAINT "country_pk" PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



ALTER TABLE "movie"
    ADD CONSTRAINT "movie_fk0" FOREIGN KEY ("country_id") REFERENCES "country" ("id");

ALTER TABLE "user_account"
    ADD CONSTRAINT "user_account_fk0" FOREIGN KEY ("role_id") REFERENCES "role" ("id");



ALTER TABLE "movie_genre"
    ADD CONSTRAINT "movie_genre_fk0" FOREIGN KEY ("movie_id") REFERENCES "movie" ("id");
ALTER TABLE "movie_genre"
    ADD CONSTRAINT "movie_genre_fk1" FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");
