package io.coodoo.framework.audit.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;

/**
 * @author coodoo GmbH (coodoo.io)
 */
@Entity
@Table(name = "audit_change")
@NamedQueries({@NamedQuery(name = "AuditChange.getByEvent", query = "SELECT ac FROM AuditChange ac WHERE ac.eventId = :eventId"), @NamedQuery(
                name = "AuditChange.getByEventAndField", query = "SELECT ac FROM AuditChange ac WHERE ac.eventId = :eventId AND ac.field = :field")})
public class AuditChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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
        return "AuditChange [id=" + id + ", eventId=" + eventId + ", field=" + field + ", oldValue=" + oldValue + ", newValue=" + newValue + ", subEvent="
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

    /**
     * Executes the query 'AuditChange.getByEvent' returning a list of result objects.
     *
     * @param entityManager the entityManager
     * @param eventId the eventId
     * @return List of result objects
     */
    @SuppressWarnings("unchecked")
    public static List<AuditChange> getByEvent(EntityManager entityManager, Long eventId) {
        Query query = entityManager.createNamedQuery("AuditChange.getByEvent");
        query = query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    /**
     * Executes the query 'AuditChange.getByEventAndField' returning one/the first object or null if nothing has been found.
     *
     * @param entityManager the entityManager
     * @param eventId the eventId
     * @param field the field
     * @return the result
     */
    public static AuditChange getByEventAndField(EntityManager entityManager, Long eventId, String field) {
        Query query = entityManager.createNamedQuery("AuditChange.getByEventAndField");
        query = query.setParameter("eventId", eventId);
        query = query.setParameter("field", field);
        query = query.setMaxResults(1);
        @SuppressWarnings("rawtypes")
        List results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return (AuditChange) results.get(0);
    }

}
