CREATE TABLE audit_event (
  id NUMBER(19) NOT NULL AUTO_INCREMENT,
  created_at DATE NOT NULL,
  entity VARCHAR2(128) NOT NULL,
  entity_id NUMBER(19) NOT NULL,
  action VARCHAR2(8) NOT NULL,
  user_id NUMBER(19) DEFAULT NULL,
  user_name VARCHAR2(128) DEFAULT NULL
);

ALTER TABLE audit_event ADD (
  CONSTRAINT audit_event_pk PRIMARY KEY (id)
);

CREATE SEQUENCE audit_event_seq START WITH 1;

CREATE OR REPLACE TRIGGER audit_event_bir 
BEFORE INSERT ON audit_event 
FOR EACH ROW

BEGIN
  SELECT audit_event_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

CREATE TABLE audit_change (
  id NUMBER(19) NOT NULL AUTO_INCREMENT,
  event_id NUMBER(19) NOT NULL,
  field VARCHAR2(128) NOT NULL,
  old_value VARCHAR2(4096) DEFAULT NULL,
  new_value VARCHAR2(4096) DEFAULT NULL,
  sub_event_id NUMBER(19) DEFAULT NULL,
  sub_event_name VARCHAR2(4096) DEFAULT NULL,
  CONSTRAINT audit_change_pk PRIMARY KEY (id)
);

ALTER TABLE audit_change ADD (
  CONSTRAINT audit_change_pk PRIMARY KEY (id)
);

CREATE SEQUENCE audit_change_seq START WITH 1;

CREATE OR REPLACE TRIGGER audit_change_bir 
BEFORE INSERT ON audit_change 
FOR EACH ROW

BEGIN
  SELECT audit_change_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

