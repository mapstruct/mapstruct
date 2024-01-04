/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.presence;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.NegatePresenceCheck;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Filip Hrisafov
 */
public class JavaExpressionPresenceCheck extends ModelElement implements PresenceCheck {

    private final String javaExpression;

    public JavaExpressionPresenceCheck(String javaExpression) {
        this.javaExpression = javaExpression;
    }

    public String getJavaExpression() {
        return javaExpression;
    }

    @Override
    public PresenceCheck negate() {
        return new NegatePresenceCheck( this );
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        JavaExpressionPresenceCheck that = (JavaExpressionPresenceCheck) o;
        return Objects.equals( javaExpression, that.javaExpression );
    }

    @Override
    public int hashCode() {
        return Objects.hash( javaExpression );
    }
}
