# Audit #

*Audit framework to store data changes observing JPA events*

## Getting started

1. Prepare your project

   Add the [maven dependency](http://search.maven.org/#artifactdetails%7Cio.coodoo%7Ccoodoo-audit%7C1.1.0%7Cjar):

   ```xml
	<dependency>
	    <groupId>io.coodoo</groupId>
	    <artifactId>coodoo-audit</artifactId>
	    <version>1.0.0</version>
	</dependency>
   ```
   
   Create the audit tables `audit_event` and `audit_change`. *This is a MySQL example, see [here](https://github.com/coodoo-io/coodoo-audit/tree/master/src/main/resources) for more.*

   ```sql
   
   CREATE TABLE audit_event (
     id bigint(20) NOT NULL AUTO_INCREMENT,
     created_at datetime NOT NULL,
     entity varchar(128) NOT NULL,
     entity_id bigint(20) NOT NULL,
     action varchar(8) NOT NULL,
     user_id bigint(20) DEFAULT NULL,
     user_name varchar(128) DEFAULT NULL,
     PRIMARY KEY (id)
   );
   
   CREATE TABLE audit_change (
     id bigint(20) NOT NULL AUTO_INCREMENT,
     event_id bigint(20) NOT NULL,
     field varchar(128) NOT NULL,
     old_value varchar(4096) DEFAULT NULL,
     new_value varchar(4096) DEFAULT NULL,
     sub_event_id bigint(20) DEFAULT NULL,
     sub_event_name varchar(4096) DEFAULT NULL,
     PRIMARY KEY (id)
   );
   
   ```
    
   Add the audit entities to your persistence.xml:

   ```xml
	<class>io.coodoo.framework.audit.entity.AuditEvent</class> 
	<class>io.coodoo.framework.audit.entity.AuditChange</class> 
   ```
   To provide the EntityManager you have to implement a `@AuditEntityManager` CDI producer.
   
   ```java

	@Stateless
	public class AuditEntityManagerProducer {
	
	    @PersistenceContext(unitName = "my-fancy-persistence-unit")
	    private EntityManager entityManager;
	
	    @Produces
	    @AuditEntityManager
	    public EntityManager getEntityManager() {
	        return entityManager;
	    }
	}   
	```

## Custom settings

To provide own settings you need to add a property file named `coodoo.audit.properties` to your project. This file gets read on JavaEE server startup if available or manually by calling `AuditSettings.loadProperties()`;

These are the properties to be defined on the file:
```properties
## Default user name if no user is present while the audit is taken
coodoo.audit.default.user = Unknown

## Time zone
coodoo.audit.timezone = UTC

## Maximal possible characters to use as a value in a change
coodoo.audit.max.character = 4000

## Default pattern for Date or LocalDateTime values in a change
coodoo.audit.timestamp.pattern = dd.MM.yyyy HH:mm:ss

## Separator to join list elements
coodoo.audit.list.separator = ,
```
