package io.coodoo.framework.audit.boundary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;

/**
 * Full qualified name of the foreign key related entity class
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditRelatedEntity {

    @Nonbinding
    Class<?> value();

}
