package io.coodoo.framework.audit.boundary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Breaks the behavior that multiple updates gets grouped to a single audit event. To use with {@link AuditGroupEvents}
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditGroupEventsBreak {
}
