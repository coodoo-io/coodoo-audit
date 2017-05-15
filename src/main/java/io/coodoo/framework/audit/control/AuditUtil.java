package io.coodoo.framework.audit.control;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.coodoo.framework.audit.boundary.AuditAction;
import io.coodoo.framework.audit.boundary.AuditInitialValues;
import io.coodoo.framework.audit.boundary.AuditReadable;
import io.coodoo.framework.audit.boundary.annotation.AuditBooleanLabels;
import io.coodoo.framework.audit.boundary.annotation.AuditCollectionParent;
import io.coodoo.framework.audit.boundary.annotation.AuditDateTimePattern;
import io.coodoo.framework.audit.boundary.annotation.AuditDeleteMarker;
import io.coodoo.framework.audit.boundary.annotation.AuditGroupEvents;
import io.coodoo.framework.audit.boundary.annotation.AuditIgnoreField;
import io.coodoo.framework.audit.boundary.annotation.AuditIgnoreFields;
import io.coodoo.framework.audit.boundary.annotation.AuditName;

/**
 * @author coodoo GmbH (coodoo.io)
 */
public final class AuditUtil {

    private static Logger log = LoggerFactory.getLogger(AuditUtil.class);

    private AuditUtil() {}

    public static String getEntityName(AuditInitialValues entity) {
        return getEntityName(entity.getClass());
    }

    public static String getEntityName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(AuditName.class)) {
            return entityClass.getAnnotation(AuditName.class).value();
        }
        return entityClass.getSimpleName();
    }

    public static List<Field> getFields(Class<?> targetClass) {

        List<Field> fields = new ArrayList<>();
        List<String> ignoreFields = new ArrayList<>();
        Class<?> inheritanceClass = targetClass;

        while (inheritanceClass != null) {
            if (inheritanceClass.isAnnotationPresent(AuditIgnoreFields.class)) {
                ignoreFields.addAll(Arrays.asList(inheritanceClass.getAnnotation(AuditIgnoreFields.class).value()));
            }
            for (Field field : inheritanceClass.getDeclaredFields()) {
                // There is no need to check the JPA identifier and transient fields are a irrelevant
                if (!field.isAnnotationPresent(Id.class) && !field.isAnnotationPresent(Transient.class)
                // Defined to ignore
                                && !field.isAnnotationPresent(AuditIgnoreField.class) && !ignoreFields.contains(field.getName())
                                // Ignore collections, final and static fields
                                && !Collection.class.isAssignableFrom(field.getType()) && !Modifier.isFinal(field.getModifiers())
                                && !Modifier.isStatic(field.getModifiers())) {
                    fields.add(field);
                }
            }
            inheritanceClass = inheritanceClass.getSuperclass();
        }
        return fields;
    }

    public static boolean groupEvents(AuditInitialValues entity, AuditAction action) {
        return (AuditAction.CREATE.equals(action) || AuditAction.UPDATE.equals(action)) && entity.getClass().isAnnotationPresent(AuditGroupEvents.class);
    }

    public static String getFieldName(Field field) {
        if (field.isAnnotationPresent(AuditName.class)) {
            return field.getAnnotation(AuditName.class).value();
        }
        return field.getName();
    }

    public static String getClassName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(AuditName.class)) {
            return clazz.getAnnotation(AuditName.class).value();
        }
        return clazz.getSimpleName();
    }

    public static Map<String, Field> getNameFieldMapping(AuditInitialValues entity) {

        return getFields(entity.getClass()).stream().collect(Collectors.toMap(f -> getFieldName(f), f -> f));
    }

    public static Map<String, Object> getValues(AuditInitialValues entity) {

        Map<String, Object> map = new HashMap<>();

        for (Field field : AuditUtil.getFields(entity.getClass())) {
            try {
                if (field.isAnnotationPresent(AuditCollectionParent.class)) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(entity);

                map.put(getFieldName(field), processFieldValue(value, field));

                if (field.isAnnotationPresent(AuditDeleteMarker.class) && value != null) {
                    map.put(AuditDeleteMarker.DELETE_MARKER, value);
                }

            } catch (IllegalArgumentException | IllegalAccessException e) {
                // Does nothing in this case
            }
        }
        return map;
    }

    private static Object processFieldValue(Object value, Field field) {

        if (field.getType().isEnum() && value instanceof AuditReadable) {
            return ((AuditReadable) value).toAuditableString();
        }
        if (value != null && field.isAnnotationPresent(AuditDateTimePattern.class)) {

            String datePattern = field.getAnnotation(AuditDateTimePattern.class).value();

            if (value instanceof LocalDateTime) {
                return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern(datePattern));
            }
            if (value instanceof Date) {
                return new SimpleDateFormat(datePattern).format(((Date) value));
            }
        }
        if (value != null && field.isAnnotationPresent(AuditBooleanLabels.class) && value instanceof Boolean) {

            if ((boolean) value) {
                return field.getAnnotation(AuditBooleanLabels.class).trueLabel();
            }
            return field.getAnnotation(AuditBooleanLabels.class).falseLabel();
        }
        return value;
    }

    public static Map<Class<?>, Long> getParentReferences(AuditInitialValues entity) {

        Map<Class<?>, Long> parents = new HashMap<>();

        try {
            for (Field field : getFields(entity.getClass())) {
                if (field.isAnnotationPresent(AuditCollectionParent.class)) {

                    field.setAccessible(true);
                    Object fieldValue = field.get(entity);

                    for (Field parentField : getFields(field.getType())) {

                        if (parentField.isAnnotationPresent(Id.class)) {
                            parentField.setAccessible(true);
                            Object id = parentField.get(fieldValue);

                            if (id != null && (parentField.getType().equals(Long.class) || parentField.getType().equals(long.class))) {
                                parents.put(field.getType(), (Long) id);
                            }
                        }
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            log.warn("Could not get parent references: {}", e);
        }
        return parents;
    }

}
