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
 * Prevent asynchronous processing of audit events to avoid null-references in huge transactions by annotate the method with <code>@AuditSynchronous</code>
 * 
 * @author coodoo GmbH (coodoo.io)
 */
@SuppressWarnings("serial")
@AuditSynchronous
@Interceptor
public class AuditSynchronousInterceptor implements Serializable {

    private static Logger log = LoggerFactory.getLogger(AuditSynchronousInterceptor.class);

    private static Map<Integer, LocalDateTime> transactions = new HashMap<>();

    public AuditSynchronousInterceptor() {}

    @AroundInvoke
    public Object toggleActionSynchronous(InvocationContext invocationContext) throws Exception {

        try {

            log.info("Creating audit events synchronous only for this transaction.");
            transactions.put(AuditUtil.getTransactionKey(), LocalDateTime.now());

            // do audit events synchronous only
            return invocationContext.proceed();

        } catch (Exception e) {

            log.error("Synchronous only interception failed: {}", e.getMessage());
            return null;

        } finally {

            // clean up old transaction keys
            AuditUtil.cleanUpTransactionKeyMap(transactions);
        }
    }

    public static boolean isSynchonousOnly() {
        try {
            return transactions.containsKey(AuditUtil.getTransactionKey());
        } catch (NamingException e) {
            return false;
        }
    }

}
