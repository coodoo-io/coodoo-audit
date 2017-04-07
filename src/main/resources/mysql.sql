CREATE TABLE audit_event (
  id SERIAL,
  created_at DATETIME NOT NULL,
  entity VARCHAR(128) NOT NULL,
  entity_id BIGINT(20) NOT NULL,
  action VARCHAR(8) NOT NULL,
  user_id BIGINT(20) DEFAULT NULL,
  user_name VARCHAR(128) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE audit_change (
  id SERIAL,
  event_id BIGINT(20) NOT NULL,
  field VARCHAR(128) NOT NULL,
  old_value VARCHAR(4096) DEFAULT NULL,
  new_value VARCHAR(4096) DEFAULT NULL,
  sub_event_id BIGINT(20) DEFAULT NULL,
  sub_event_name VARCHAR(4096) DEFAULT NULL,
  PRIMARY KEY (id)
);