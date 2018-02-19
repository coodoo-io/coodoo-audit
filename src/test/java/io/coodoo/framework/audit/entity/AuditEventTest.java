package io.coodoo.framework.audit.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

public class AuditEventTest {

    /**
     * Tests that query 'AuditEvent.getAllEvents' has not changed since this test had been created. If this test fails, you should consider re-generating ALL
     * methods created from that query as they may be out-dated.
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes", "null"})
    @org.junit.Test
    public void testGetAllEventsQueryUnchanged() {
        List annotations = new ArrayList();
        NamedQuery namedQueryAnnotation = io.coodoo.framework.audit.entity.AuditEvent.class.getAnnotation(NamedQuery.class);
        if (namedQueryAnnotation == null) {
            NamedQueries namedQueriesAnnotation = io.coodoo.framework.audit.entity.AuditEvent.class.getAnnotation(NamedQueries.class);
            if (namedQueriesAnnotation != null) {
                annotations.addAll(Arrays.asList(namedQueriesAnnotation.value()));
            }
        } else {
            annotations.add(namedQueryAnnotation);
        }
        NamedQuery queryUnderTest = null;
        for (Object obj : annotations) {
            NamedQuery query = (NamedQuery) obj;
            if (query.name().equals("AuditEvent.getAllEvents")) {
                queryUnderTest = query;
                break;
            }
        }
        if (queryUnderTest == null) {
            org.junit.Assert.fail("Query AuditEvent.getAllEvents does not exist anymore.");
        }
        String queryText = queryUnderTest.query();
        // Minor changes with whitespace are ignored
        queryText = queryText.trim().replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
        while (queryText.contains("  ")) {
            queryText = queryText.replace("  ", " ");
        }
        org.junit.Assert.assertEquals(
                        "There's a change in the query string. Generated methods may not fit to the query anymore. Change from 'SELECT av FROM AuditEvent av WHERE av.entity = :entity ORDER BY av.createdAt DESC' to '"
                                        + queryText + "'",
                        "SELECT av FROM AuditEvent av WHERE av.entity = :entity ORDER BY av.createdAt DESC", queryText);
    }

    /**
     * Tests that call and query are consistent for query 'AuditEvent.getAllEvents'.
     *
     */
    @org.junit.Test
    public void testGetAllEvents() {
        Query query = org.mockito.Mockito.mock(Query.class);
        EntityManager entityManager = org.mockito.Mockito.mock(EntityManager.class);
        org.mockito.BDDMockito.given(entityManager.createNamedQuery("AuditEvent.getAllEvents")).willReturn(query);
        String entity = "0";
        org.mockito.BDDMockito.given(query.setParameter("entity", entity)).willReturn(query);
        // Call
        io.coodoo.framework.audit.entity.AuditEvent.getAllEvents(entityManager, entity);
        // Verification
        org.mockito.BDDMockito.verify(entityManager, org.mockito.Mockito.times(1)).createNamedQuery("AuditEvent.getAllEvents");
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("entity", entity);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).getResultList();
    }

    /**
     * Tests that all classes and members/fields used in query 'AuditEvent.getAllEvents' still exist.
     *
     */
    @org.junit.Test
    public void testGetAllEventsVerifyFields() {
        String[][] classesFieldsAndTypes = new String[3][4];
        classesFieldsAndTypes[0][0] = "av";
        classesFieldsAndTypes[0][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[1][0] = "av.createdAt";
        classesFieldsAndTypes[1][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[1][2] = "createdAt";
        classesFieldsAndTypes[1][3] = "java.time.LocalDateTime";
        classesFieldsAndTypes[2][0] = "av.entity";
        classesFieldsAndTypes[2][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[2][2] = "entity";
        classesFieldsAndTypes[2][3] = "java.lang.String";
        for (String[] testcase : classesFieldsAndTypes) {
            String fieldPath = testcase[0];
            String className = testcase[1];
            String fieldName = testcase[2];
            String fieldType = testcase[3];
            try {
                Class<?> clazz = Class.forName(className);
                if (fieldName != null) {
                    boolean fieldFound = false;
                    do {
                        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                            if (field.getName().equals(fieldName)) {
                                if (fieldType != null && !field.getType().getName().equals(fieldType)) {
                                    org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getAllEvents: The field " + clazz.getName()
                                                    + "." + field + " does not have the type " + fieldType + " (anymore)");
                                }
                                fieldFound = true;
                                break;
                            }
                        }
                        clazz = clazz.getSuperclass();
                    } while (!fieldFound && clazz != null);
                    if (!fieldFound) {
                        org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getAllEvents: The field " + className + "." + fieldName
                                        + " does not exist (anymore)");
                    }
                }
            } catch (ClassNotFoundException e) {
                org.junit.Assert.fail(
                                "Error checking path " + fieldPath + " in query AuditEvent.getAllEvents: The class " + className + " does not exist (anymore)");
            }
        }
    }

    /**
     * Tests that query 'AuditEvent.getAllEventsForId' has not changed since this test had been created. If this test fails, you should consider re-generating
     * ALL methods created from that query as they may be out-dated.
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes", "null"})
    @org.junit.Test
    public void testGetAllEventsForIdQueryUnchanged() {
        List annotations = new ArrayList();
        NamedQuery namedQueryAnnotation = io.coodoo.framework.audit.entity.AuditEvent.class.getAnnotation(NamedQuery.class);
        if (namedQueryAnnotation == null) {
            NamedQueries namedQueriesAnnotation = io.coodoo.framework.audit.entity.AuditEvent.class.getAnnotation(NamedQueries.class);
            if (namedQueriesAnnotation != null) {
                annotations.addAll(Arrays.asList(namedQueriesAnnotation.value()));
            }
        } else {
            annotations.add(namedQueryAnnotation);
        }
        NamedQuery queryUnderTest = null;
        for (Object obj : annotations) {
            NamedQuery query = (NamedQuery) obj;
            if (query.name().equals("AuditEvent.getAllEventsForId")) {
                queryUnderTest = query;
                break;
            }
        }
        if (queryUnderTest == null) {
            org.junit.Assert.fail("Query AuditEvent.getAllEventsForId does not exist anymore.");
        }
        String queryText = queryUnderTest.query();
        // Minor changes with whitespace are ignored
        queryText = queryText.trim().replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
        while (queryText.contains("  ")) {
            queryText = queryText.replace("  ", " ");
        }
        org.junit.Assert.assertEquals(
                        "There's a change in the query string. Generated methods may not fit to the query anymore. Change from 'SELECT av FROM AuditEvent av WHERE av.entity = :entity AND av.entityId = :entityId ORDER BY av.createdAt DESC' to '"
                                        + queryText + "'",
                        "SELECT av FROM AuditEvent av WHERE av.entity = :entity AND av.entityId = :entityId ORDER BY av.createdAt DESC", queryText);
    }

    /**
     * Tests that call and query are consistent for query 'AuditEvent.getAllEventsForId'.
     *
     */
    @org.junit.Test
    public void testGetAllEventsForId() {
        Query query = org.mockito.Mockito.mock(Query.class);
        EntityManager entityManager = org.mockito.Mockito.mock(EntityManager.class);
        org.mockito.BDDMockito.given(entityManager.createNamedQuery("AuditEvent.getAllEventsForId")).willReturn(query);
        String entity = "0";
        org.mockito.BDDMockito.given(query.setParameter("entity", entity)).willReturn(query);
        Long entityId = java.lang.Long.valueOf(1);
        org.mockito.BDDMockito.given(query.setParameter("entityId", entityId)).willReturn(query);
        // Call
        io.coodoo.framework.audit.entity.AuditEvent.getAllEventsForId(entityManager, entity, entityId);
        // Verification
        org.mockito.BDDMockito.verify(entityManager, org.mockito.Mockito.times(1)).createNamedQuery("AuditEvent.getAllEventsForId");
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("entity", entity);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("entityId", entityId);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).getResultList();
    }

    /**
     * Tests that all classes and members/fields used in query 'AuditEvent.getAllEventsForId' still exist.
     *
     */
    @org.junit.Test
    public void testGetAllEventsForIdVerifyFields() {
        String[][] classesFieldsAndTypes = new String[4][4];
        classesFieldsAndTypes[0][0] = "av";
        classesFieldsAndTypes[0][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[1][0] = "av.createdAt";
        classesFieldsAndTypes[1][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[1][2] = "createdAt";
        classesFieldsAndTypes[1][3] = "java.time.LocalDateTime";
        classesFieldsAndTypes[2][0] = "av.entity";
        classesFieldsAndTypes[2][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[2][2] = "entity";
        classesFieldsAndTypes[2][3] = "java.lang.String";
        classesFieldsAndTypes[3][0] = "av.entityId";
        classesFieldsAndTypes[3][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[3][2] = "entityId";
        classesFieldsAndTypes[3][3] = "java.lang.Long";
        for (String[] testcase : classesFieldsAndTypes) {
            String fieldPath = testcase[0];
            String className = testcase[1];
            String fieldName = testcase[2];
            String fieldType = testcase[3];
            try {
                Class<?> clazz = Class.forName(className);
                if (fieldName != null) {
                    boolean fieldFound = false;
                    do {
                        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                            if (field.getName().equals(fieldName)) {
                                if (fieldType != null && !field.getType().getName().equals(fieldType)) {
                                    org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getAllEventsForId: The field "
                                                    + clazz.getName() + "." + field + " does not have the type " + fieldType + " (anymore)");
                                }
                                fieldFound = true;
                                break;
                            }
                        }
                        clazz = clazz.getSuperclass();
                    } while (!fieldFound && clazz != null);
                    if (!fieldFound) {
                        org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getAllEventsForId: The field " + className + "."
                                        + fieldName + " does not exist (anymore)");
                    }
                }
            } catch (ClassNotFoundException e) {
                org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getAllEventsForId: The class " + className
                                + " does not exist (anymore)");
            }
        }
    }

    /**
     * Tests that query 'AuditEvent.getLatestEvent' has not changed since this test had been created. If this test fails, you should consider re-generating ALL
     * methods created from that query as they may be out-dated.
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes", "null"})
    @org.junit.Test
    public void testGetLatestEventQueryUnchanged() {
        List annotations = new ArrayList();
        NamedQuery namedQueryAnnotation = io.coodoo.framework.audit.entity.AuditEvent.class.getAnnotation(NamedQuery.class);
        if (namedQueryAnnotation == null) {
            NamedQueries namedQueriesAnnotation = io.coodoo.framework.audit.entity.AuditEvent.class.getAnnotation(NamedQueries.class);
            if (namedQueriesAnnotation != null) {
                annotations.addAll(Arrays.asList(namedQueriesAnnotation.value()));
            }
        } else {
            annotations.add(namedQueryAnnotation);
        }
        NamedQuery queryUnderTest = null;
        for (Object obj : annotations) {
            NamedQuery query = (NamedQuery) obj;
            if (query.name().equals("AuditEvent.getLatestEvent")) {
                queryUnderTest = query;
                break;
            }
        }
        if (queryUnderTest == null) {
            org.junit.Assert.fail("Query AuditEvent.getLatestEvent does not exist anymore.");
        }
        String queryText = queryUnderTest.query();
        // Minor changes with whitespace are ignored
        queryText = queryText.trim().replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
        while (queryText.contains("  ")) {
            queryText = queryText.replace("  ", " ");
        }
        org.junit.Assert.assertEquals(
                        "There's a change in the query string. Generated methods may not fit to the query anymore. Change from 'SELECT av FROM AuditEvent av WHERE av.entity = :entity AND av.entityId = :entityId AND av.createdAt >= :fromDate ORDER BY av.createdAt DESC' to '"
                                        + queryText + "'",
                        "SELECT av FROM AuditEvent av WHERE av.entity = :entity AND av.entityId = :entityId AND av.createdAt >= :fromDate ORDER BY av.createdAt DESC",
                        queryText);
    }

    /**
     * Tests that call and query are consistent for query 'AuditEvent.getLatestEvent' - no result.
     *
     */
    @org.junit.Test
    public void testGetLatestEventEmptyResult() {
        Query query = org.mockito.Mockito.mock(Query.class);
        EntityManager entityManager = org.mockito.Mockito.mock(EntityManager.class);
        org.mockito.BDDMockito.given(entityManager.createNamedQuery("AuditEvent.getLatestEvent")).willReturn(query);
        @SuppressWarnings("rawtypes")
        List results = new ArrayList();
        org.mockito.BDDMockito.given(query.getResultList()).willReturn(results);
        String entity = "0";
        org.mockito.BDDMockito.given(query.setParameter("entity", entity)).willReturn(query);
        Long entityId = java.lang.Long.valueOf(1);
        org.mockito.BDDMockito.given(query.setParameter("entityId", entityId)).willReturn(query);
        LocalDateTime fromDate = null;
        org.mockito.BDDMockito.given(query.setParameter("fromDate", fromDate)).willReturn(query);
        org.mockito.BDDMockito.given(query.setMaxResults(1)).willReturn(query);
        // Call
        AuditEvent result = io.coodoo.framework.audit.entity.AuditEvent.getLatestEvent(entityManager, entity, entityId, fromDate);
        // Verification
        org.mockito.BDDMockito.verify(entityManager, org.mockito.Mockito.times(1)).createNamedQuery("AuditEvent.getLatestEvent");
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("entity", entity);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("entityId", entityId);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("fromDate", fromDate);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).getResultList();
        org.junit.Assert.assertNull("Result should be null if list is empty", result);
    }

    /**
     * Tests that call and query are consistent for query 'AuditEvent.getLatestEvent' - one result.
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @org.junit.Test
    public void testGetLatestEventOneResult() {
        Query query = org.mockito.Mockito.mock(Query.class);
        EntityManager entityManager = org.mockito.Mockito.mock(EntityManager.class);
        org.mockito.BDDMockito.given(entityManager.createNamedQuery("AuditEvent.getLatestEvent")).willReturn(query);
        List results = new java.util.ArrayList();
        AuditEvent first = org.mockito.Mockito.mock(AuditEvent.class);
        AuditEvent second = org.mockito.Mockito.mock(AuditEvent.class);
        results.add(first);
        results.add(second);
        org.mockito.BDDMockito.given(query.getResultList()).willReturn(results);
        String entity = "0";
        org.mockito.BDDMockito.given(query.setParameter("entity", entity)).willReturn(query);
        Long entityId = java.lang.Long.valueOf(1);
        org.mockito.BDDMockito.given(query.setParameter("entityId", entityId)).willReturn(query);
        LocalDateTime fromDate = null;
        org.mockito.BDDMockito.given(query.setParameter("fromDate", fromDate)).willReturn(query);
        org.mockito.BDDMockito.given(query.setMaxResults(1)).willReturn(query);
        // Call
        AuditEvent result = io.coodoo.framework.audit.entity.AuditEvent.getLatestEvent(entityManager, entity, entityId, fromDate);
        // Verification
        org.mockito.BDDMockito.verify(entityManager, org.mockito.Mockito.times(1)).createNamedQuery("AuditEvent.getLatestEvent");
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("entity", entity);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("entityId", entityId);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("fromDate", fromDate);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).getResultList();
        org.junit.Assert.assertEquals("Result not the first of list.", first, result);
    }

    /**
     * Tests that all classes and members/fields used in query 'AuditEvent.getLatestEvent' still exist.
     *
     */
    @org.junit.Test
    public void testGetLatestEventVerifyFields() {
        String[][] classesFieldsAndTypes = new String[4][4];
        classesFieldsAndTypes[0][0] = "av";
        classesFieldsAndTypes[0][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[1][0] = "av.createdAt";
        classesFieldsAndTypes[1][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[1][2] = "createdAt";
        classesFieldsAndTypes[1][3] = "java.time.LocalDateTime";
        classesFieldsAndTypes[2][0] = "av.entity";
        classesFieldsAndTypes[2][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[2][2] = "entity";
        classesFieldsAndTypes[2][3] = "java.lang.String";
        classesFieldsAndTypes[3][0] = "av.entityId";
        classesFieldsAndTypes[3][1] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[3][2] = "entityId";
        classesFieldsAndTypes[3][3] = "java.lang.Long";
        for (String[] testcase : classesFieldsAndTypes) {
            String fieldPath = testcase[0];
            String className = testcase[1];
            String fieldName = testcase[2];
            String fieldType = testcase[3];
            try {
                Class<?> clazz = Class.forName(className);
                if (fieldName != null) {
                    boolean fieldFound = false;
                    do {
                        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                            if (field.getName().equals(fieldName)) {
                                if (fieldType != null && !field.getType().getName().equals(fieldType)) {
                                    org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getLatestEvent: The field "
                                                    + clazz.getName() + "." + field + " does not have the type " + fieldType + " (anymore)");
                                }
                                fieldFound = true;
                                break;
                            }
                        }
                        clazz = clazz.getSuperclass();
                    } while (!fieldFound && clazz != null);
                    if (!fieldFound) {
                        org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getLatestEvent: The field " + className + "."
                                        + fieldName + " does not exist (anymore)");
                    }
                }
            } catch (ClassNotFoundException e) {
                org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditEvent.getLatestEvent: The class " + className
                                + " does not exist (anymore)");
            }
        }
    }

}
