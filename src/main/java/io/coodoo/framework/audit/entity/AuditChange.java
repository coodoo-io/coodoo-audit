package io.coodoo.framework.audit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author coodoo GmbH (coodoo.io)
 */
@Entity
@Table(name = "audit_change")
public class AuditChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private AuditEvent event;

    @Column(name = "field", nullable = false)
    private String field;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_event_id")
    private AuditEvent subEvent;

    @Column(name = "sub_event_name")
    private String subEventName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public AuditEvent getEvent() {
        return event;
    }

    public void setEvent(AuditEvent event) {
        this.event = event;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public AuditEvent getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(AuditEvent subEvent) {
        this.subEvent = subEvent;
    }

    public String getSubEventName() {
        return subEventName;
    }

    public void setSubEventName(String subEventName) {
        this.subEventName = subEventName;
    }

    @Override
    public String toString() {
        return "AuditChange [id=" + id + ", event=" + event + ", field=" + field + ", oldValue=" + oldValue + ", newValue=" + newValue + ", subEvent="
                        + subEvent + ", subEventName=" + subEventName + "]";
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AuditChange)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (((AuditChange) obj).getId() == null) {
            return false;
        }
        return ((AuditChange) obj).getId().equals(getId());
    }

}
