package io.coodoo.framework.audit.boundary;

import java.util.Map;

/**
 * Needs to be implemented by an entity that wants to get audited
 * 
 * @author coodoo GmbH (coodoo.io)
 */
public interface AuditInitialValues {

    /**
     * @return the ID of the entity
     */
    public Long getEntiyId();

    /**
     * The entity needs a transient map that will store the current properties of a loaded entity
     * 
     * @return key value map representing the initial values of the entity
     */
    public Map<String, Object> getInitialValues();

    /**
     * Access to set the current properties of the entity
     * 
     * @param initialValues key value map representing the initial values of the entity
     */
    public void setInitialValues(Map<String, Object> initialValues);

}
