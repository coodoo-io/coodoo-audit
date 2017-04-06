package io.coodoo.framework.audit.boundary;

import java.util.Map;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author coodoo GmbH (coodoo.io)
 */
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class AbstractAuditValuesEntity implements AuditInitialValues {

    @Transient
    private transient Map<String, Object> initialValues;

    @Override
    public Map<String, Object> getInitialValues() {
        return initialValues;
    }

    @Override
    public void setInitialValues(Map<String, Object> initialValues) {
        this.initialValues = initialValues;
    }

}
