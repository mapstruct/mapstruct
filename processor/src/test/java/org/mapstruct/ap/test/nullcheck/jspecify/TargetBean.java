/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class TargetBean {

    private String nonNullTarget;
    private boolean nonNullTargetSet;

    private String nullableTarget;
    private boolean nullableTargetSet;

    private String nonNullTargetFromNullable;
    private boolean nonNullTargetFromNullableSet;

    private String unannotatedTarget;
    private boolean unannotatedTargetSet;

    public String getNonNullTarget() {
        return nonNullTarget;
    }

    public void setNonNullTarget(@NonNull String nonNullTarget) {
        this.nonNullTargetSet = true;
        this.nonNullTarget = nonNullTarget;
    }

    public boolean isNonNullTargetSet() {
        return nonNullTargetSet;
    }

    public String getNullableTarget() {
        return nullableTarget;
    }

    public void setNullableTarget(@Nullable String nullableTarget) {
        this.nullableTargetSet = true;
        this.nullableTarget = nullableTarget;
    }

    public boolean isNullableTargetSet() {
        return nullableTargetSet;
    }

    public String getNonNullTargetFromNullable() {
        return nonNullTargetFromNullable;
    }

    public void setNonNullTargetFromNullable(@NonNull String nonNullTargetFromNullable) {
        this.nonNullTargetFromNullableSet = true;
        this.nonNullTargetFromNullable = nonNullTargetFromNullable;
    }

    public boolean isNonNullTargetFromNullableSet() {
        return nonNullTargetFromNullableSet;
    }

    public String getUnannotatedTarget() {
        return unannotatedTarget;
    }

    public void setUnannotatedTarget(String unannotatedTarget) {
        this.unannotatedTargetSet = true;
        this.unannotatedTarget = unannotatedTarget;
    }

    public boolean isUnannotatedTargetSet() {
        return unannotatedTargetSet;
    }
}
