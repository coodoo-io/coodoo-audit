CREATE TABLE audit_event (
  id SERIAL PRIMARY KEY,
  created_at DATETIME NOT NULL,
  entity CHARACTER VARYING(128) NOT NULL,
  entity_id BIGINT(20) NOT NULL,
  action CHARACTER VARYING(8) NOT NULL,
  user_id BIGINT(20) DEFAULT NULL,
  user_name CHARACTER VARYING(128) DEFAULT NULL
);

CREATE TABLE audit_change (
  id SERIAL PRIMARY KEY,
  event_id BIGINT(20) NOT NULL,
  field CHARACTER VARYING(128) NOT NULL,
  old_value CHARACTER VARYING(4096) DEFAULT NULL,
  new_value CHARACTER VARYING(4096) DEFAULT NULL,
  sub_event_id BIGINT(20) DEFAULT NULL,
  sub_event_name CHARACTER VARYING(4096) DEFAULT NULL
);