drop table if exists member CASCADE;

CREATE TABLE member
(
 member_id BIGINT NOT NULL AUTO_INCREMENT,
 member_name VARCHAR(255),
 PRIMARY KEY (member_id)
);

CREATE TABLE document (
  document_id BIGINT NOT NULL AUTO_INCREMENT,
  document_name VARCHAR(255) NOT NULL,
  member_id BIGINT NOT NULL,
  path VARCHAR(255) NOT NULL,
  PRIMARY KEY (document_id),
  FOREIGN KEY (member_id) REFERENCES member(member_id)
);

CREATE TABLE translation (
    page_id BIGINT AUTO_INCREMENT,
    document_id BIGINT,
    page_index INT,
    summary INT ARRAY,
    text TEXT ARRAY,
    picture TEXT ARRAY,
    PRIMARY KEY (page_id),
    FOREIGN KEY (document_id) REFERENCES document(document_id)
);
