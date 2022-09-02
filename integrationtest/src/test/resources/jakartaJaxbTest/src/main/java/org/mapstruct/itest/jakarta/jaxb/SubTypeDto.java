/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jakarta.jaxb;

public class SubTypeDto extends SuperTypeDto {
    private String declaredCamelCase;
    private String declaredUnderscore;

    public String getDeclaredCamelCase() {
        return declaredCamelCase;
    }

    public void setDeclaredCamelCase(String declaredCamelCase) {
        this.declaredCamelCase = declaredCamelCase;
    }

    public String getDeclaredUnderscore() {
        return declaredUnderscore;
    }

    public void setDeclaredUnderscore(String declaredUnderscore) {
        this.declaredUnderscore = declaredUnderscore;
    }
}
