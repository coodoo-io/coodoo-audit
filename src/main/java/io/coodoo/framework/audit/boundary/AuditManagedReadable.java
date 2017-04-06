package io.coodoo.framework.audit.boundary;

import javax.persistence.EntityManager;

/**
 * Interface to provide a String representation using the EntityManager.
 * 
 * @author coodoo GmbH (coodoo.io)
 */
public interface AuditManagedReadable {

    /**
     * @param entityManager EntityManager
     * @return String representation of current object for the usage in auditing
     */
    public String toAuditableString(EntityManager entityManager);

}
