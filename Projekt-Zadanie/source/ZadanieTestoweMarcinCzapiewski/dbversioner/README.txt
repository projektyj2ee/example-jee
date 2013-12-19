W razie problemów z liquidbase mo¿na wykonaæ poni¿sze polecenia:

Przed wykonaniem poleceñ nale¿y utworzyæ bazê o nazwie: sun-appserv-samples

CREATE TABLE COMMENT 
     (ID_COMMENT BIGINT NOT NULL, AUTHOR_NAME VARCHAR(10) NOT NULL, USER_COMMENT VARCHAR(1000), CREATE_DATE TIMESTAMP, PRIMARY KEY (ID_COMMENT));

--sekwecja uzywana do generacji wartosci ID_COMMENT -- Uwaga eclipslink moze nie radzic sobie z sekwencami na Derby:/
CREATE SEQUENCE COMMENTSSEQUENCE START WITH 500 INCREMENT BY 1;

--tabla - generator PK
CREATE TABLE is_generators
(
  name character varying(255),
  value integer
);