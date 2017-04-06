package io.coodoo.framework.audit.boundary.dto;

import io.coodoo.framework.audit.entity.AuditChange;

/**
 * @author coodoo GmbH (coodoo.io)
 */
public class AuditChangeDTO {

    public Long id;

    public String field;

    public String oldValue;

    public String newValue;

    public AuditEventDTO subEvent;

    public String subEventName;

    public AuditChangeDTO() {
        super();
    }

    public AuditChangeDTO(AuditChange auditChange) {
        super();
        this.id = auditChange.getId();
        this.field = auditChange.getField();
        this.oldValue = auditChange.getOldValue();
        this.newValue = auditChange.getNewValue();
        if (auditChange.getSubEvent() != null) {
            this.subEvent = new AuditEventDTO(auditChange.getSubEvent());
            this.subEventName = auditChange.getSubEventName();
        }
    }

}
