package io.coodoo.framework.audit.boundary.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import io.coodoo.framework.audit.entity.AuditAction;
import io.coodoo.framework.audit.entity.AuditEvent;

/**
 * @author coodoo GmbH (coodoo.io)
 */
public class AuditEventDTO {

    public Long id;

    public LocalDateTime createdAt;

    public String entity;

    public Long entityId;

    public AuditAction action;

    public Long userId;

    public String username;

    public List<AuditChangeDTO> changes;

    public AuditEventDTO() {
        super();
    }

    public AuditEventDTO(AuditEvent auditEvent) {
        super();
        this.id = auditEvent.getId();
        this.createdAt = auditEvent.getCreatedAt();
        this.entity = auditEvent.getEntity();
        this.entityId = auditEvent.getEntityId();
        this.action = auditEvent.getAction();
        this.userId = auditEvent.getUserId();
        this.username = auditEvent.getUserName();
        this.changes = auditEvent.getChanges().stream().map(AuditChangeDTO::new).collect(Collectors.toList());

    }

}
