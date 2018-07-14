/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb.underscores;

public class UnderscoreType {
    protected String inheritedUnderscore;
    protected String declaredUnderscore;

    public String getInheritedUnderscore() {
        return inheritedUnderscore;
    }

    public void setInheritedUnderscore(String inheritedUnderscore) {
        this.inheritedUnderscore = inheritedUnderscore;
    }

    public String getDeclaredUnderscore() {
        return declaredUnderscore;
    }

    public void setDeclaredUnderscore(String declaredUnderscore) {
        this.declaredUnderscore = declaredUnderscore;
    }
}
