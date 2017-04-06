package io.coodoo.framework.audit.boundary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If annotated field gets set, the entity gets audited as 'DELETE'
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditDeleteMarker {

    public static final String DELETE_MARKER = "DELETE_MARKER";

}
