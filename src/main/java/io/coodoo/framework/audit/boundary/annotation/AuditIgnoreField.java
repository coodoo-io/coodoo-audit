package io.coodoo.framework.audit.boundary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field shall not be audited
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditIgnoreField {

}
