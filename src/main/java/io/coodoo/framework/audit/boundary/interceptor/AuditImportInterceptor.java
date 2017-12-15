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
 * To mark a CREATE audit event as an IMPORT audit event, annotate the method with <code>@AuditImport</code>
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@SuppressWarnings("serial")
@AuditImport
@Interceptor
public class AuditImportInterceptor implements Serializable {

    private static Logger log = LoggerFactory.getLogger(AuditImportInterceptor.class);

    private static Map<Integer, LocalDateTime> transactions = new HashMap<>();

    public AuditImportInterceptor() {}

    @AroundInvoke
    public Object toggleActionImport(InvocationContext invocationContext) throws Exception {

        try {

            try {
                log.info("Audit events are now marked as 'IMPORT' for this transaction.");
                transactions.put(AuditUtil.getTransactionKey(), LocalDateTime.now());
            } catch (Exception e) {
                log.error("Mark as 'IMPORT' interception failed: {}", e.getMessage());
            }

            // do a persist and get audited as an import
            return invocationContext.proceed();

        } finally {

            // clean up old transaction keys
            AuditUtil.cleanUpTransactionKeyMap(transactions);
        }
    }

    public static boolean isImport() {
        try {
            return transactions.containsKey(AuditUtil.getTransactionKey());
        } catch (NamingException e) {
            return false;
        }
    }

}
