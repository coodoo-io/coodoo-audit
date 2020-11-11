# Audit #

*Audit framework to store data changes by observing JPA events*

## Getting started

1. Add the [maven dependency](http://search.maven.org/#artifactdetails%7Cio.coodoo%7Ccoodoo-audit%7C1.1.0%7Cjar):

   ```xml
    <dependency>
        <groupId>io.coodoo</groupId>
        <artifactId>coodoo-audit</artifactId>
        <version>1.1.0</version>
    </dependency>
   ```

2. Create the audit tables `audit_event` and `audit_change` in your database.
         
   ```sql
   CREATE TABLE audit_event (
     id BIGINT(20) NOT NULL AUTO_INCREMENT,
     created_at DATETIME NOT NULL,
     entity VARCHAR(128) NOT NULL,
     entity_id BIGINT(20) NOT NULL,
     action VARCHAR(8) NOT NULL,
     user_id BIGINT(20) DEFAULT NULL,
     user_name VARCHAR(128) DEFAULT NULL,
     PRIMARY KEY (id)
   );

   CREATE TABLE audit_change (
     id BIGINT(20) NOT NULL AUTO_INCREMENT,
     event_id BIGINT(20) NOT NULL,
     field VARCHAR(128) NOT NULL,
     old_value VARCHAR(4096) DEFAULT NULL,
     new_value VARCHAR(4096) DEFAULT NULL,
     sub_event_id BIGINT(20) DEFAULT NULL,
     sub_event_name VARCHAR(4096) DEFAULT NULL,
     PRIMARY KEY (id),
     KEY fk_audit_change__audit_event_idx (event_id),
     CONSTRAINT fk_audit_change__audit_event FOREIGN KEY (event_id) 
	  REFERENCES audit_event (id) 
	  ON DELETE NO ACTION ON UPDATE NO ACTION
   );

   ```
   *This is a MySQL example, see [here](https://github.com/coodoo-io/coodoo-audit/tree/master/src/main/resources/sql) for more.*
                 
3. Add the audit entities to your persistence.xml:

   ```xml
    <class>io.coodoo.framework.audit.entity.AuditEvent</class>
    <class>io.coodoo.framework.audit.entity.AuditChange</class>
   ```
4. To provide the EntityManager you have to implement a `@AuditEntityManager` CDI producer.

   ```java

    @Stateless
    public class AuditEntityManagerProducer {
         
        @PersistenceContext  
        private EntityManager entityManager;
         
        @Produces
        @AuditEntityManager
        public EntityManager getEntityManager() {
            return entityManager;
        }
    }
    ```
    *This is necessary to avoid trouble when it comes to different persistence contexts.*

5. Prepare your entities to get audited.
   Every desired entity needs to add the `AuditEntityListener` and implement the `AuditInitialValues` interface. The easiest way to do so is to extend the given class `AbstractAuditValuesEntity`.

   ```java
   @Entity
   public class Person extends AbstractAuditValuesEntity {

       @Id
       @GeneratedValue
       private Long id;
         
       private String name;
         
       private Date birthday;
         
       private Gender gender;
         
       @Override
       public Long getEntiyId() {
           return id;
       }
         
       // getters, setters, hashCode, equals, ...
   }
   ```
 From now on, every create, update and delete will be audited and stored in in the database. You can access the audit data using the `AuditService` by giving the simple class name of the audited entity. This will get you all the data concerning the entity. To see the audit of one entry you have to give the ID as well.

   ```java
   @Stateless
public class PersonService {

    Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Inject
    AuditService auditService;

    public void logAuditForPerson(Long personId) {

        logger.info("Audit for Person with the ID {}", personId);

        for (AuditEvent auditEvent : auditService.getEvents(Person.class.getSimpleName(), personId)) {

            logger.info("{}: {} ({})", auditEvent.getCreatedAt(),
                                       auditEvent.getAction(),
                                       auditEvent.getUserName());

            for (AuditChange auditChange : auditEvent.getChanges()) {

                logger.info("* Field '{}' changed from '{}' to '{}'", 
                        auditChange.getField(), 
                        auditChange.getOldValue(), 
                        auditChange.getNewValue());
            }
        }
    }
}
   ```

## Make it pretty

First of all you will need a name on an audit event, so you know who is to blame. Therefore you



## Import / Export events

`@AuditImport` and `@AuditExport` interceptor annotations.
  
```xml
<interceptors>
    <class>io.coodoo.framework.audit.boundary.interceptor.AuditImportInterceptor</class>
    <class>io.coodoo.framework.audit.boundary.interceptor.AuditExportInterceptor</class>
</interceptors> 
```

## Custom settings

To provide own settings you need to add a property file named `coodoo.audit.properties` to your project. This file gets read on JavaEE server startup if available or manually by calling `AuditSettings.loadProperties()`;

These are the properties to be defined on the file:
```properties
## Default user name if no user is present while the audit is taken
coodoo.audit.default.user = Unknown

## Maximal possible characters to use as a value in a change
coodoo.audit.max.character = 4000

## Default pattern for Date or LocalDateTime values in a change
coodoo.audit.timestamp.pattern = dd.MM.yyyy HH:mm:ss

## Time zone
coodoo.audit.timezone = UTC  
```
