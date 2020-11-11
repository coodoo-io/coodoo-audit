

<!--
### Bug Fixes
### Features
### BREAKING CHANGES
-->

<a name="1.1.0"></a>

## 1.1.0 (2020-11-11)

### Features

 * CDI replaces EJB in EntityListener
 

### BREAKING CHANGES 

 *  The `@Inject` in the EntityListener is not supported WildFly below version 11 see: [https://issues.jboss.org/browse/WFLY-2387](https://issues.jboss.org/browse/WFLY-2387)


<a name="1.0.4"></a>

## 1.0.4 (2020-01-18)

### Features

 * Performance gain by dissolving circular dependencies and using named queries


<a name="1.0.3"></a>

## 1.0.3 (2017-12-15)

### Bug Fixes

 * Exception handling in interceptors
 * Auditing of @OneToMany changes using @AuditCollectionParent


<a name="1.0.2"></a>

## 1.0.2 (2017-11-27)

### Features

 * @AuditSynchronous interceptor to prevent asynchronous processing of audit events to avoid null-references in huge transactions.
 * Interceptors are now based on JTA transactions

### Bug Fixes

 * Fixed getRelatedEntityValue() & toAuditableString()



<a name="1.0.1"></a>
 
## 1.0.1 (2017-11-17)

### Bug Fixes

* Null check at @AuditRelatedEntity



<a name="1.0.0"></a>

## 1.0.0 (2017-04-06)

### Features

Initial release:

* Settings via property file