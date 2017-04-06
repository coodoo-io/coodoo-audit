package io.coodoo.framework.audit.boundary.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mark the reading of an entity as an EXPORT audit event, annotate the method with <code>@AuditExport</code>
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@SuppressWarnings("serial")
@AuditExport
@Interceptor
public class AuditExportInterceptor implements Serializable {

    private static Logger log = LoggerFactory.getLogger(AuditExportInterceptor.class);

    /**
     * This flag is set <code>true</code> if this interceptor is used at the start of the transaction. A CREATE audit event is now marked as an IMPORT.
     */
    public static boolean isExport = false;

    public AuditExportInterceptor() {}

    @AroundInvoke
    public Object toggleActionExport(InvocationContext ctx) throws Exception {

        isExport = true;
        log.info("Loading entities is now marked as 'EXPORT'");

        try {

            // do whatever you must
            return ctx.proceed();

        } catch (Exception e) {
            log.error("Export failed: {}", e.getMessage());
            return null;
        } finally {

            isExport = false;
            log.info("Loading entities is back to normal");
        }
    }

}
