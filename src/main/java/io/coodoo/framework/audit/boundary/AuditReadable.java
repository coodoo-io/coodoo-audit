package io.coodoo.framework.audit.boundary;

import io.coodoo.framework.audit.boundary.annotation.AuditRelatedEntity;

/**
 * Interface needed for the use of {@link AuditRelatedEntity} to provide a String representation.
 * 
 * @author coodoo GmbH (coodoo.io)
 */
public interface AuditReadable {

    /**
     * @return String representation of current object for the usage in auditing
     */
    public String toAuditableString();

}
