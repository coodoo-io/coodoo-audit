package io.coodoo.framework.audit.control;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.coodoo.framework.audit.boundary.AuditAction;
import io.coodoo.framework.audit.boundary.AuditInitialValues;
import io.coodoo.framework.audit.boundary.AuditManagedReadable;
import io.coodoo.framework.audit.boundary.AuditReadable;
import io.coodoo.framework.audit.boundary.annotation.AuditDeleteMarker;
import io.coodoo.framework.audit.boundary.annotation.AuditEntityManager;
import io.coodoo.framework.audit.boundary.annotation.AuditGroupEvents;
import io.coodoo.framework.audit.boundary.annotation.AuditGroupEventsBreak;
import io.coodoo.framework.audit.boundary.annotation.AuditRelatedEntity;
import io.coodoo.framework.audit.entity.AuditChange;
import io.coodoo.framework.audit.entity.AuditEvent;

/**
 * @author coodoo GmbH (coodoo.io)
 */
@Stateless
public class AuditController {

    private static Logger log = LoggerFactory.getLogger(AuditController.class);

    @Inject
    @AuditEntityManager
    EntityManager entityManager;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createAuditEventAsynchronous(AuditInitialValues entity, AuditAction auditAction, AuditUser auditUser, LocalDateTime createdAt) {
        createAuditEvent(entity, auditAction, auditUser, createdAt);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createAuditEvent(AuditInitialValues entity, AuditAction auditAction, AuditUser auditUser, LocalDateTime createdAt) {

        if (auditUser.getUserName() == null || auditUser.getUserName().isEmpty()) {
            auditUser.setUserName(AuditConfig.DEFAULT_USER);
        }
        String entityName = AuditUtil.getEntityName(entity);
        boolean grouping = false;

        // get dem changes
        List<AuditChange> auditChanges;
        AuditAction action = auditAction;
        switch (action) {
            case CREATE:
            case IMPORT:
                auditChanges = getAuditChanges(entity, true);
                break;
            case UPDATE:
                auditChanges = getAuditChanges(entity, false);
                if (auditChanges.stream().map(AuditChange::getField).filter(AuditDeleteMarker.DELETE_MARKER::equals).findFirst().isPresent()) {
                    action = AuditAction.DELETE; // If delete marker is triggered, the update shall be audited as a deletion
                }
                grouping = AuditUtil.groupEvents(entity, action);
                break;
            default:
                auditChanges = new ArrayList<>();
                break;
        }

        if (grouping) {

            AuditGroupEvents groupSettings = entity.getClass().getAnnotation(AuditGroupEvents.class);
            // try grouping with older event to join changes
            grouping = groupChangesWithLatestEvent(entity.getClass(), entity.getEntiyId(), auditChanges, groupSettings.timeframe(),
                            groupSettings.overwriteChanges(), auditUser);
        }
        if (!grouping) {

            // audit it the normal way
            AuditEvent auditEvent = createAuditEvent(entityName, entity.getEntiyId(), action, auditChanges, auditUser, createdAt);

            if (!action.equals(AuditAction.EXPORT) && !action.equals(AuditAction.IMPORT)) {
                // updated parent audit events
                Map<Class<?>, Long> parentReferences = AuditUtil.getParentReferences(entity);
                if (parentReferences.size() > 0) {

                    String subEventName = toAuditableString(entity);

                    for (Map.Entry<Class<?>, Long> parentReference : parentReferences.entrySet()) {

                        Class<?> entityClass = parentReference.getKey();
                        Long entityId = parentReference.getValue();

                        AuditChange parentReferenceChange = new AuditChange();
                        parentReferenceChange.setField(entityName);
                        parentReferenceChange.setSubEvent(auditEvent);
                        parentReferenceChange.setSubEventName(subEventName);

                        if (!groupChangesWithLatestEvent(entityClass, entityId, Arrays.asList(parentReferenceChange), 10, false, auditUser)) {

                            createAuditEvent(AuditUtil.getEntityName(entityClass), entityId, AuditAction.UPDATE, Arrays.asList(parentReferenceChange),
                                            auditUser, createdAt);
                        }
                    }
                }
            }
            log.debug("Audit event written: {}", auditEvent.getId());
        }
    }

    private List<AuditChange> getAuditChanges(AuditInitialValues entity, boolean initial) {

        List<AuditChange> auditChanges = new ArrayList<>();
        if (entity == null) {
            return auditChanges;
        }

        Map<String, Object> newValues = AuditUtil.getValues(entity);
        Map<String, Object> oldValues = null;
        if (initial) {
            try {
                oldValues = AuditUtil.getValues(entity.getClass().getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                            | SecurityException e) {
                log.error("Could not instantiate {}", entity.getClass().getName(), e);
            }
        } else {
            oldValues = entity.getInitialValues();
        }

        Map<String, Field> nameFieldMapping = AuditUtil.getNameFieldMapping(entity);

        for (Change change : getChanges(oldValues, newValues)) {

            AuditChange auditChange = new AuditChange();
            auditChange.setField(change.getField());

            Field field = nameFieldMapping.get(change.getField());
            if (field != null && field.isAnnotationPresent(AuditRelatedEntity.class)) {
                auditChange.setOldValue(getRelatedEntityValue(field.getAnnotation(AuditRelatedEntity.class).value(), change.getOldValue()));
                auditChange.setNewValue(getRelatedEntityValue(field.getAnnotation(AuditRelatedEntity.class).value(), change.getNewValue()));
            } else {
                auditChange.setOldValue(change.getOldValueString());
                auditChange.setNewValue(change.getNewValueString());
            }
            auditChanges.add(auditChange);
        }
        return auditChanges;
    }

    private AuditEvent createAuditEvent(String entity, Long entiyId, AuditAction action, List<AuditChange> auditChanges, AuditUser auditUser,
                    LocalDateTime createdAt) {

        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setEntity(entity);
        auditEvent.setEntityId(entiyId);
        auditEvent.setAction(action);
        auditEvent.setUserName(auditUser.getUserName());
        auditEvent.setUserId(auditUser.getUserId());
        auditEvent.setCreatedAt(createdAt);

        entityManager.persist(auditEvent);

        auditChanges.forEach(auditChange -> {
            auditChange.setEventId(auditEvent.getId());
            entityManager.persist(auditChange);
        });

        return auditEvent;
    }

    private boolean groupChangesWithLatestEvent(Class<?> entityClass, Long entityId, List<AuditChange> auditChanges, long timeframe, boolean overwriteChanges,
                    AuditUser auditUser) {

        LocalDateTime groupDateLimit = LocalDateTime.now(ZoneId.of(AuditConfig.LOCAL_DATE_TIME_ZONE)).minusSeconds(timeframe);

        AuditEvent latestEvent = AuditEvent.getLatestEvent(entityManager, AuditUtil.getEntityName(entityClass), entityId, groupDateLimit);

        if (latestEvent != null && (latestEvent.getAction().equals(AuditAction.CREATE) || latestEvent.getAction().equals(AuditAction.UPDATE))
                        && (Objects.equals(latestEvent.getUserId(), auditUser.getUserId()) || latestEvent.getUserName().equals(auditUser.getUserName()))) {

            // check if field want's its own event and breaks this grouping with no survivors
            List<String> groupBreakignFields = new ArrayList<>();
            for (Field field : AuditUtil.getFields(entityClass)) {
                if (field.isAnnotationPresent(AuditGroupEventsBreak.class)) {
                    groupBreakignFields.add(AuditUtil.getFieldName(field));
                }
            }
            for (AuditChange auditChange : auditChanges) {
                if (groupBreakignFields.contains(auditChange.getField())) {
                    return false;
                }
            }

            if (overwriteChanges) {

                // set latest values
                for (AuditChange newChange : auditChanges) {

                    AuditChange oldChange = AuditChange.getByEventAndField(entityManager, latestEvent.getId(), newChange.getField());

                    if (oldChange != null) {
                        oldChange.setNewValue(newChange.getNewValue());
                    } else {
                        newChange.setEventId(latestEvent.getId());
                        entityManager.persist(newChange);
                    }
                }
            } else {
                // just add new changes
                auditChanges.forEach(auditChange -> {
                    auditChange.setEventId(latestEvent.getId());
                    entityManager.persist(auditChange);
                });
            }
            // reset time on event
            latestEvent.setCreatedAt(LocalDateTime.now(ZoneId.of(AuditConfig.LOCAL_DATE_TIME_ZONE)));

            log.debug("Audit changes grouped with event: {}", latestEvent.getId());
            return true;
        }
        return false;
    }

    private String toAuditableString(Object entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof AuditManagedReadable) {
            return ((AuditManagedReadable) entity).toAuditableString(entityManager);
        }
        if (entity instanceof AuditReadable) {
            return ((AuditReadable) entity).toAuditableString();
        }
        return entity.toString();
    }

    private String getRelatedEntityValue(Class<?> relatedEntityClass, Object value) {

        if (relatedEntityClass == null || value == null) {
            return null;
        }

        Field idField = AuditUtil.getIdField(relatedEntityClass);

        if (idField != null && idField.getType() == value.getClass()) {
            try {
                Object relatedEntity = entityManager.find(relatedEntityClass, value);
                return toAuditableString(relatedEntity);
            } catch (NoResultException e) {
                log.warn("Related Entity not found: {} ID {}", relatedEntityClass, value);
            }
        }
        return value.toString();
    }

    private Set<Change> getChanges(Map<String, Object> oldValues, Map<String, Object> newValues) {
        Set<Change> changeses = new HashSet<>();
        for (Entry<String, Object> entry : newValues.entrySet()) {
            Object oldForNew = oldValues.get(entry.getKey());
            if (oldForNew == null) {
                if (entry.getValue() != null) {
                    changeses.add(new Change(null, entry));
                }
            } else {
                if (!oldForNew.equals(entry.getValue())) {
                    changeses.add(new Change(oldForNew, entry));
                }
            }
        }
        return changeses;
    }

    private static class Change {

        private Object oldValue;
        private Object newValue;
        private String field;

        public Change(Object oldValue, Entry<String, Object> newValue) {
            this.oldValue = oldValue;
            this.newValue = newValue.getValue();
            this.field = newValue.getKey();
        }

        public String getField() {
            return field;
        }

        public Object getOldValue() {
            return oldValue;
        }

        public Object getNewValue() {
            return newValue;
        }

        public String getOldValueString() {
            return toString(oldValue);
        }

        public String getNewValueString() {
            return toString(newValue);
        }

        @Override
        public String toString() {
            return field + ": " + toString(oldValue) + " -> " + toString(newValue);
        }

        private String toString(Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof LocalDateTime) {
                return ((LocalDateTime) object).format(DateTimeFormatter.ofPattern(AuditConfig.TIMESTAMP_PATTERN));
            }
            if (object instanceof Date) {
                return new SimpleDateFormat(AuditConfig.TIMESTAMP_PATTERN).format(((Date) object));
            }

            String value = object.toString();

            if (value.length() > AuditConfig.MAX_CHARACTERS) {
                value = value.substring(0, AuditConfig.MAX_CHARACTERS - 5) + " ...";
            }
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((field == null) ? 0 : field.hashCode());
            result = prime * result + ((newValue == null) ? 0 : newValue.hashCode());
            result = prime * result + ((oldValue == null) ? 0 : oldValue.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Change other = (Change) obj;
            if (field == null) {
                if (other.field != null)
                    return false;
            } else if (!field.equals(other.field))
                return false;
            if (newValue == null) {
                if (other.newValue != null)
                    return false;
            } else if (!newValue.equals(other.newValue))
                return false;
            if (oldValue == null) {
                if (other.oldValue != null)
                    return false;
            } else if (!oldValue.equals(other.oldValue))
                return false;
            return true;
        }

    }

}
