package io.coodoo.framework.audit.boundary.interceptor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.coodoo.framework.audit.control.AuditUtil;

/**
 * Marks the reading of an entity as an EXPORT audit event, annotate the method with <code>@AuditExport</code>
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@SuppressWarnings("serial")
@AuditExport
@Interceptor
public class AuditExportInterceptor implements Serializable {

    private static Logger log = LoggerFactory.getLogger(AuditExportInterceptor.class);

    private static Map<Integer, LocalDateTime> transactions = new HashMap<>();

    public AuditExportInterceptor() {}

    @AroundInvoke
    public Object toggleActionExport(InvocationContext invocationContext) throws Exception {

        try {

            try {
                log.info("Audit events are now marked as 'EXPORT' for this transaction.");
                transactions.put(AuditUtil.getTransactionKey(), LocalDateTime.now());
            } catch (Exception e) {
                log.error("Mark as 'EXPORT' interception failed: {}", e.getMessage());
            }

            // do whatever you must
            return invocationContext.proceed();

        } finally {

            // clean up old transaction keys
            AuditUtil.cleanUpTransactionKeyMap(transactions);
        }
    }

    public static boolean isExport() {
        try {
            return transactions.containsKey(AuditUtil.getTransactionKey());
        } catch (NamingException e) {
            return false;
        }
    }
}
