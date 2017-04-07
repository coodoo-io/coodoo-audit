package io.coodoo.framework.audit.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Query;
import javax.persistence.Table;

import io.coodoo.framework.audit.boundary.AuditAction;
import io.coodoo.framework.audit.control.AuditConfig;

/**
 * @author coodoo GmbH (coodoo.io)
 */
@Entity
@Table(name = "audit_event")
@NamedQueries({@NamedQuery(name = "AuditEvent.getAllEvents", query = "select av from AuditEvent av where av.entity = :entity order by av.createdAt desc"),
                @NamedQuery(name = "AuditEvent.getAllEventsForId",
                                query = "select av from AuditEvent av where av.entity = :entity and av.entityId = :entityId order by av.createdAt desc"),
                @NamedQuery(name = "AuditEvent.getLatestEvent",
                                query = "select av from AuditEvent av where av.entity = :entity and av.entityId = :entityId and av.createdAt >= :fromDate order by av.createdAt desc")})
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "entity", nullable = false)
    private String entity;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditAction action;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<AuditChange> changes;

    @PrePersist
    public void prePesist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now(ZoneId.of(AuditConfig.LOCAL_DATE_TIME_ZONE));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<AuditChange> getChanges() {
        return changes;
    }

    public void setChanges(List<AuditChange> changes) {
        this.changes = changes;
    }

    @Override
    public String toString() {
        return "AuditEvent [id=" + id + ", createdAt=" + createdAt + ", entity=" + entity + ", entityId=" + entityId + ", action=" + action + ", userId="
                        + userId + ", userName=" + userName + ", changes=" + changes + "]";
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
        if (obj == null || !(obj instanceof AuditEvent)) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        if (((AuditEvent) obj).getId() == null) {
            return false;
        }
        return ((AuditEvent) obj).getId().equals(getId());
    }

    /**
     * Executes the query 'AuditEvent.getAllEvents' returning a list of result objects.
     *
     * @param entityManager the entityManager
     * @param entity the entity
     * @return List of result objects
     */
    @SuppressWarnings("unchecked")
    public static List<AuditEvent> getAllEvents(EntityManager entityManager, String entity) {
        Query query = entityManager.createNamedQuery("AuditEvent.getAllEvents");
        query = query.setParameter("entity", entity);
        return query.getResultList();
    }

    /**
     * Executes the query 'AuditEvent.getAllEventsForId' returning a list of result objects.
     *
     * @param entityManager the entityManager
     * @param entity the entity
     * @param entityId the entityId
     * @return List of result objects
     */
    @SuppressWarnings("unchecked")
    public static List<AuditEvent> getAllEventsForId(EntityManager entityManager, String entity, Long entityId) {
        Query query = entityManager.createNamedQuery("AuditEvent.getAllEventsForId");
        query = query.setParameter("entity", entity);
        query = query.setParameter("entityId", entityId);
        return query.getResultList();
    }

    /**
     * Executes the query 'AuditEvent.getLatestEvent' returning one/the first object or null if nothing has been found.
     *
     * @param entityManager the entityManager
     * @param entity the entity
     * @param entityId the entityId
     * @param fromDate the fromDate
     * @return the result
     */
    public static AuditEvent getLatestEvent(EntityManager entityManager, String entity, Long entityId, LocalDateTime fromDate) {
        Query query = entityManager.createNamedQuery("AuditEvent.getLatestEvent");
        query = query.setParameter("entity", entity);
        query = query.setParameter("entityId", entityId);
        query = query.setParameter("fromDate", fromDate);
        query = query.setMaxResults(1);
        @SuppressWarnings("rawtypes")
        List results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return (AuditEvent) results.get(0);
    }

}
