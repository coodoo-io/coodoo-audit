package io.coodoo.framework.audit.boundary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;

/**
 * Date/Time pattern for the value representation in audit entry
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditDateTimePattern {

    @Nonbinding
    String value() default "dd.MM.yyyy";

}
