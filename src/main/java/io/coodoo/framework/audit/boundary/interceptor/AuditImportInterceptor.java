package io.coodoo.framework.audit.boundary.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To mark a CREATE audit event as an IMPORT audit event, annotate the method with <code>@AuditImport</code>
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@SuppressWarnings("serial")
@AuditImport
@Interceptor
public class AuditImportInterceptor implements Serializable {

    private static Logger log = LoggerFactory.getLogger(AuditImportInterceptor.class);

    /**
     * This flag is set <code>true</code> if this interceptor is used at the start of the transaction. A CREATE audit event is now marked as an IMPORT.
     */
    public static boolean isImport = false;

    public AuditImportInterceptor() {}

    @AroundInvoke
    public Object toggleActionImport(InvocationContext ctx) throws Exception {

        isImport = true;
        log.info("Audit event 'CREATE' is now marked as 'IMPORT'");

        try {

            // do a persist and get audited as an import
            return ctx.proceed();

        } catch (Exception e) {
            log.error("Import failed: {}", e.getMessage());
            return null;
        } finally {

            isImport = false;
            log.info("Audit event 'CREATE' is back to normal");
        }
    }

}
