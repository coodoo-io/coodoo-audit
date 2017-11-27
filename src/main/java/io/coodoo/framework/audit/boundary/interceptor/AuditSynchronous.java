package io.coodoo.framework.audit.boundary.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Prevents asynchronous processing of audit events to avoid null-references in huge transactions.<br>
 * It needs to get declare in beans.xml
 * 
 * <pre>
 * &lt;interceptors&gt;
 *     &lt;class&gt;de.dbag.nap.access.framework.audit.boundary.interceptor.AuditSynchronousInterceptor&lt;/class&gt;
 * &lt;/interceptors&gt;
 * </pre>
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AuditSynchronous {
}
