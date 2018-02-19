package io.coodoo.framework.audit.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

public class AuditChangeTest {

    /**
     * Tests that query 'AuditChange.getByEvent' has not changed since this test had been created. If this test fails, you should consider re-generating ALL
     * methods created from that query as they may be out-dated.
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes", "null"})
    @org.junit.Test
    public void testGetByEventQueryUnchanged() {
        List annotations = new ArrayList();
        NamedQuery namedQueryAnnotation = io.coodoo.framework.audit.entity.AuditChange.class.getAnnotation(NamedQuery.class);
        if (namedQueryAnnotation == null) {
            NamedQueries namedQueriesAnnotation = io.coodoo.framework.audit.entity.AuditChange.class.getAnnotation(NamedQueries.class);
            if (namedQueriesAnnotation != null) {
                annotations.addAll(Arrays.asList(namedQueriesAnnotation.value()));
            }
        } else {
            annotations.add(namedQueryAnnotation);
        }
        NamedQuery queryUnderTest = null;
        for (Object obj : annotations) {
            NamedQuery query = (NamedQuery) obj;
            if (query.name().equals("AuditChange.getByEvent")) {
                queryUnderTest = query;
                break;
            }
        }
        if (queryUnderTest == null) {
            org.junit.Assert.fail("Query AuditChange.getByEvent does not exist anymore.");
        }
        String queryText = queryUnderTest.query();
        // Minor changes with whitespace are ignored
        queryText = queryText.trim().replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
        while (queryText.contains("  ")) {
            queryText = queryText.replace("  ", " ");
        }
        org.junit.Assert.assertEquals(
                        "There's a change in the query string. Generated methods may not fit to the query anymore. Change from 'SELECT ac FROM AuditChange ac WHERE ac.event = :event' to '"
                                        + queryText + "'",
                        "SELECT ac FROM AuditChange ac WHERE ac.event = :event", queryText);
    }

    /**
     * Tests that call and query are consistent for query 'AuditChange.getByEvent'.
     *
     */
    @org.junit.Test
    public void testGetByEvent() {
        Query query = org.mockito.Mockito.mock(Query.class);
        EntityManager entityManager = org.mockito.Mockito.mock(EntityManager.class);
        org.mockito.BDDMockito.given(entityManager.createNamedQuery("AuditChange.getByEvent")).willReturn(query);
        AuditEvent event = org.mockito.Mockito.mock(AuditEvent.class);
        org.mockito.BDDMockito.given(query.setParameter("event", event)).willReturn(query);
        // Call
        io.coodoo.framework.audit.entity.AuditChange.getByEvent(entityManager, event);
        // Verification
        org.mockito.BDDMockito.verify(entityManager, org.mockito.Mockito.times(1)).createNamedQuery("AuditChange.getByEvent");
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("event", event);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).getResultList();
    }

    /**
     * Tests that all classes and members/fields used in query 'AuditChange.getByEvent' still exist.
     *
     */
    @org.junit.Test
    public void testGetByEventVerifyFields() {
        String[][] classesFieldsAndTypes = new String[2][4];
        classesFieldsAndTypes[0][0] = "ac";
        classesFieldsAndTypes[0][1] = "io.coodoo.framework.audit.entity.AuditChange";
        classesFieldsAndTypes[1][0] = "ac.event";
        classesFieldsAndTypes[1][1] = "io.coodoo.framework.audit.entity.AuditChange";
        classesFieldsAndTypes[1][2] = "event";
        classesFieldsAndTypes[1][3] = "io.coodoo.framework.audit.entity.AuditEvent";
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
                                    org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditChange.getByEvent: The field " + clazz.getName()
                                                    + "." + field + " does not have the type " + fieldType + " (anymore)");
                                }
                                fieldFound = true;
                                break;
                            }
                        }
                        clazz = clazz.getSuperclass();
                    } while (!fieldFound && clazz != null);
                    if (!fieldFound) {
                        org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditChange.getByEvent: The field " + className + "." + fieldName
                                        + " does not exist (anymore)");
                    }
                }
            } catch (ClassNotFoundException e) {
                org.junit.Assert.fail(
                                "Error checking path " + fieldPath + " in query AuditChange.getByEvent: The class " + className + " does not exist (anymore)");
            }
        }
    }

    /**
     * Tests that query 'AuditChange.getByEventAndField' has not changed since this test had been created. If this test fails, you should consider re-generating
     * ALL methods created from that query as they may be out-dated.
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes", "null"})
    @org.junit.Test
    public void testGetByEventAndFieldQueryUnchanged() {
        List annotations = new ArrayList();
        NamedQuery namedQueryAnnotation = io.coodoo.framework.audit.entity.AuditChange.class.getAnnotation(NamedQuery.class);
        if (namedQueryAnnotation == null) {
            NamedQueries namedQueriesAnnotation = io.coodoo.framework.audit.entity.AuditChange.class.getAnnotation(NamedQueries.class);
            if (namedQueriesAnnotation != null) {
                annotations.addAll(Arrays.asList(namedQueriesAnnotation.value()));
            }
        } else {
            annotations.add(namedQueryAnnotation);
        }
        NamedQuery queryUnderTest = null;
        for (Object obj : annotations) {
            NamedQuery query = (NamedQuery) obj;
            if (query.name().equals("AuditChange.getByEventAndField")) {
                queryUnderTest = query;
                break;
            }
        }
        if (queryUnderTest == null) {
            org.junit.Assert.fail("Query AuditChange.getByEventAndField does not exist anymore.");
        }
        String queryText = queryUnderTest.query();
        // Minor changes with whitespace are ignored
        queryText = queryText.trim().replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
        while (queryText.contains("  ")) {
            queryText = queryText.replace("  ", " ");
        }
        org.junit.Assert.assertEquals(
                        "There's a change in the query string. Generated methods may not fit to the query anymore. Change from 'SELECT ac FROM AuditChange ac WHERE ac.event = :event AND ac.field = :field' to '"
                                        + queryText + "'",
                        "SELECT ac FROM AuditChange ac WHERE ac.event = :event AND ac.field = :field", queryText);
    }

    /**
     * Tests that call and query are consistent for query 'AuditChange.getByEventAndField' - no result.
     *
     */
    @org.junit.Test
    public void testGetByEventAndFieldEmptyResult() {
        Query query = org.mockito.Mockito.mock(Query.class);
        EntityManager entityManager = org.mockito.Mockito.mock(EntityManager.class);
        org.mockito.BDDMockito.given(entityManager.createNamedQuery("AuditChange.getByEventAndField")).willReturn(query);
        @SuppressWarnings("rawtypes")
        List results = new ArrayList();
        org.mockito.BDDMockito.given(query.getResultList()).willReturn(results);
        AuditEvent event = org.mockito.Mockito.mock(AuditEvent.class);
        org.mockito.BDDMockito.given(query.setParameter("event", event)).willReturn(query);
        String field = "1";
        org.mockito.BDDMockito.given(query.setParameter("field", field)).willReturn(query);
        org.mockito.BDDMockito.given(query.setMaxResults(1)).willReturn(query);
        // Call
        AuditChange result = io.coodoo.framework.audit.entity.AuditChange.getByEventAndField(entityManager, event, field);
        // Verification
        org.mockito.BDDMockito.verify(entityManager, org.mockito.Mockito.times(1)).createNamedQuery("AuditChange.getByEventAndField");
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("event", event);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("field", field);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).getResultList();
        org.junit.Assert.assertNull("Result should be null if list is empty", result);
    }

    /**
     * Tests that call and query are consistent for query 'AuditChange.getByEventAndField' - one result.
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @org.junit.Test
    public void testGetByEventAndFieldOneResult() {
        Query query = org.mockito.Mockito.mock(Query.class);
        EntityManager entityManager = org.mockito.Mockito.mock(EntityManager.class);
        org.mockito.BDDMockito.given(entityManager.createNamedQuery("AuditChange.getByEventAndField")).willReturn(query);
        List results = new java.util.ArrayList();
        AuditChange first = org.mockito.Mockito.mock(AuditChange.class);
        AuditChange second = org.mockito.Mockito.mock(AuditChange.class);
        results.add(first);
        results.add(second);
        org.mockito.BDDMockito.given(query.getResultList()).willReturn(results);
        AuditEvent event = org.mockito.Mockito.mock(AuditEvent.class);
        org.mockito.BDDMockito.given(query.setParameter("event", event)).willReturn(query);
        String field = "1";
        org.mockito.BDDMockito.given(query.setParameter("field", field)).willReturn(query);
        org.mockito.BDDMockito.given(query.setMaxResults(1)).willReturn(query);
        // Call
        AuditChange result = io.coodoo.framework.audit.entity.AuditChange.getByEventAndField(entityManager, event, field);
        // Verification
        org.mockito.BDDMockito.verify(entityManager, org.mockito.Mockito.times(1)).createNamedQuery("AuditChange.getByEventAndField");
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("event", event);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).setParameter("field", field);
        org.mockito.BDDMockito.verify(query, org.mockito.Mockito.times(1)).getResultList();
        org.junit.Assert.assertEquals("Result not the first of list.", first, result);
    }

    /**
     * Tests that all classes and members/fields used in query 'AuditChange.getByEventAndField' still exist.
     *
     */
    @org.junit.Test
    public void testGetByEventAndFieldVerifyFields() {
        String[][] classesFieldsAndTypes = new String[3][4];
        classesFieldsAndTypes[0][0] = "ac";
        classesFieldsAndTypes[0][1] = "io.coodoo.framework.audit.entity.AuditChange";
        classesFieldsAndTypes[1][0] = "ac.event";
        classesFieldsAndTypes[1][1] = "io.coodoo.framework.audit.entity.AuditChange";
        classesFieldsAndTypes[1][2] = "event";
        classesFieldsAndTypes[1][3] = "io.coodoo.framework.audit.entity.AuditEvent";
        classesFieldsAndTypes[2][0] = "ac.field";
        classesFieldsAndTypes[2][1] = "io.coodoo.framework.audit.entity.AuditChange";
        classesFieldsAndTypes[2][2] = "field";
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
                                    org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditChange.getByEventAndField: The field "
                                                    + clazz.getName() + "." + field + " does not have the type " + fieldType + " (anymore)");
                                }
                                fieldFound = true;
                                break;
                            }
                        }
                        clazz = clazz.getSuperclass();
                    } while (!fieldFound && clazz != null);
                    if (!fieldFound) {
                        org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditChange.getByEventAndField: The field " + className + "."
                                        + fieldName + " does not exist (anymore)");
                    }
                }
            } catch (ClassNotFoundException e) {
                org.junit.Assert.fail("Error checking path " + fieldPath + " in query AuditChange.getByEventAndField: The class " + className
                                + " does not exist (anymore)");
            }
        }
    }

}
