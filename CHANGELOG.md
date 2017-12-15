

<!--
### Bug Fixes
### Features
### BREAKING CHANGES
-->

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