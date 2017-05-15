package io.coodoo.framework.audit.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class AuditUtilTest {

    @Test
    public void testGetFields_GetFieldsClassA() throws Exception {

        List<Field> fields = AuditUtil.getFields(GetFieldsClassA.class);

        assertEquals(3, fields.size());

        List<String> fieldNames = fields.stream().map(field -> field.getName()).collect(Collectors.toList());

        assertTrue(fieldNames.contains("fieldA2Column"));
        assertTrue(fieldNames.contains("fieldA4"));
        assertTrue(fieldNames.contains("fieldA6Deprecated"));

        assertFalse(fieldNames.contains("fieldA1Id"));
        assertFalse(fieldNames.contains("fieldA3Ignore"));
        assertFalse(fieldNames.contains("fieldA5Transient"));

    }

    @Test
    public void testGetFields_GetFieldsClassB() throws Exception {

        List<Field> fields = AuditUtil.getFields(GetFieldsClassB.class);

        assertEquals(6, fields.size());

        List<String> fieldNames = fields.stream().map(field -> field.getName()).collect(Collectors.toList());

        assertTrue(fieldNames.contains("fieldB4Public"));
        assertTrue(fieldNames.contains("fieldB5Protected"));
        assertTrue(fieldNames.contains("fieldB6Package"));
        assertTrue(fieldNames.contains("fieldA2Column"));
        assertTrue(fieldNames.contains("fieldA4"));
        assertTrue(fieldNames.contains("fieldA6Deprecated"));

        assertFalse(fieldNames.contains("fieldB1Collection"));
        assertFalse(fieldNames.contains("fieldB2Static"));
        assertFalse(fieldNames.contains("fieldB3Final"));
        assertFalse(fieldNames.contains("fieldA1Id"));
        assertFalse(fieldNames.contains("fieldA3Ignore"));
        assertFalse(fieldNames.contains("fieldA5Transient"));
    }

    @Test
    public void testGetFields_GetFieldsClassC() throws Exception {

        List<Field> fields = AuditUtil.getFields(GetFieldsClassC.class);

        assertEquals(8, fields.size());

        List<String> fieldNames = fields.stream().map(field -> field.getName()).collect(Collectors.toList());

        assertTrue(fieldNames.contains("fieldC1"));
        assertTrue(fieldNames.contains("fieldC2"));
        assertTrue(fieldNames.contains("fieldC3"));
        assertTrue(fieldNames.contains("fieldC5"));
        assertTrue(fieldNames.contains("fieldB5Protected"));
        assertTrue(fieldNames.contains("fieldB6Package"));
        assertTrue(fieldNames.contains("fieldA2Column"));
        assertTrue(fieldNames.contains("fieldA6Deprecated"));

        assertFalse(fieldNames.contains("fieldC4"));
        assertFalse(fieldNames.contains("fieldB1Collection"));
        assertFalse(fieldNames.contains("fieldB2Static"));
        assertFalse(fieldNames.contains("fieldB3Final"));
        assertFalse(fieldNames.contains("fieldB4Public"));
        assertFalse(fieldNames.contains("fieldA1Id"));
        assertFalse(fieldNames.contains("fieldA3Ignore"));
        assertFalse(fieldNames.contains("fieldA4"));
        assertFalse(fieldNames.contains("fieldA5Transient"));
    }

}
