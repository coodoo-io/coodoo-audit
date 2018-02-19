package io.coodoo.framework.audit.boundary;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import io.coodoo.framework.audit.boundary.annotation.AuditEntityManager;
import io.coodoo.framework.audit.boundary.interceptor.AuditSynchronousInterceptor;
import io.coodoo.framework.audit.control.AuditConfig;
import io.coodoo.framework.audit.control.AuditController;
import io.coodoo.framework.audit.control.AuditUser;
import io.coodoo.framework.audit.control.AuditUtil;
import io.coodoo.framework.audit.entity.AuditChange;
import io.coodoo.framework.audit.entity.AuditEvent;

/**
 * @author coodoo GmbH (coodoo.io)
 */
@Stateless
public class AuditService {

    @Inject
    @AuditEntityManager
    EntityManager entityManager;

    @Inject
    AuditUserBean auditUser;

    @Inject
    AuditController auditController;

    public List<AuditEvent> getEvents(String entity) {
        return AuditEvent.getAllEvents(entityManager, entity);
    }

    public List<AuditEvent> getEvents(String entity, Long entityId) {
        return AuditEvent.getAllEventsForId(entityManager, entity, entityId);
    }

    public List<AuditChange> getChanges(AuditEvent event) {
        return AuditChange.getByEvent(entityManager, event);
    }

    public void createAuditEvent(AuditInitialValues entity, AuditAction action) {

        LocalDateTime createdAt = LocalDateTime.now(ZoneId.of(AuditConfig.LOCAL_DATE_TIME_ZONE));

        if (AuditUtil.groupEvents(entity, action) || AuditSynchronousInterceptor.isSynchonousOnly()) {
            auditController.createAuditEvent(entity, action, getAuditUser(), createdAt);
        } else {
            auditController.createAuditEventAsynchronous(entity, action, getAuditUser(), createdAt);
        }
    }

    private AuditUser getAuditUser() {
        return new AuditUser(auditUser);
    }
}
