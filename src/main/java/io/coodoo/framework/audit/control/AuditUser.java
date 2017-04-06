package io.coodoo.framework.audit.control;

import io.coodoo.framework.audit.boundary.AuditUserBean;

/**
 * @author coodoo GmbH (coodoo.io)
 */
public class AuditUser {

    private Long userId;

    private String userName;

    public AuditUser(AuditUserBean auditUser) {
        super();
        this.userId = auditUser.getUserId();
        this.userName = auditUser.getUserName();
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

    @Override
    public String toString() {
        return "AuditUser [userId=" + userId + ", userName=" + userName + "]";
    }

}
