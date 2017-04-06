package io.coodoo.framework.audit.boundary;

/**
 * To get the user informations of an auditable JPA event, this interface has to get implemented as a @Stateless EJB.
 * 
 * @author coodoo GmbH (coodoo.io)
 */
public interface AuditUserBean {

    /**
     * The technical reference to the user
     * 
     * @return a nummerical ID or <code>null</code>
     */
    public Long getUserId();

    /**
     * The name of the user.
     * 
     * @return users name or <code>null</code>
     */
    public String getUserName();

}
