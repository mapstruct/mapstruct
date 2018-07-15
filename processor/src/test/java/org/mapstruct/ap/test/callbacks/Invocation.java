/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks;

import java.util.Arrays;

/**
 * @author Andreas Gudian
 */
public class Invocation {
    private final String methodName;
    private final String arguments;

    public Invocation(String methodName, Object... arguments) {
        this.methodName = methodName;
        this.arguments = Arrays.toString( arguments );
    }

    public String getMethodName() {
        return methodName;
    }

    public String getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Invocation [methodName=" + methodName + ", arguments=" + arguments + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( arguments == null ) ? 0 : arguments.hashCode() );
        result = prime * result + ( ( methodName == null ) ? 0 : methodName.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        Invocation other = (Invocation) obj;
        if ( arguments == null ) {
            if ( other.arguments != null ) {
                return false;
            }
        }
        else if ( !arguments.equals( other.arguments ) ) {
            return false;
        }
        if ( methodName == null ) {
            if ( other.methodName != null ) {
                return false;
            }
        }
        else if ( !methodName.equals( other.methodName ) ) {
            return false;
        }
        return true;
    }
}
