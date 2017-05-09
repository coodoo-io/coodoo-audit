package io.coodoo.framework.audit.control;

import io.coodoo.framework.audit.boundary.annotation.AuditIgnoreFields;

@AuditIgnoreFields({"fieldA4", "fieldB4Public", "fieldC4"})
public class GetFieldsClassC extends GetFieldsClassB {

    private String fieldC1;

    private String fieldC2;

    private String fieldC3;

    private String fieldC4;

    private String fieldC5;

}
