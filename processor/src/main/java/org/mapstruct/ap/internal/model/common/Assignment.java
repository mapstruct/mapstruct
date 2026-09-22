/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.util.accessor.Nullability;

/**
 * Assignment represents all kind of manners a source can be assigned to a target.
 *
 * @author Sjaak Derksen
 */
public interface Assignment {

    enum AssignmentType {
        /** assignment is direct */
        DIRECT( true ),
        /** assignment is type converted */
        TYPE_CONVERTED( false ),
        /** assignment is mapped (builtin/custom) */
        MAPPED( false ),
        /** 2 mapping methods (builtin/custom) are applied to get the target */
        MAPPED_TWICE( false ),
        /** assignment is first mapped (builtin/custom), then the result is type converted */
        MAPPED_TYPE_CONVERTED( false ),
        /** assignment is first type converted, and then mapped (builtin/custom) */
        TYPE_CONVERTED_MAPPED( false );

        private final boolean direct;

        AssignmentType( boolean isDirect ) {
            this.direct = isDirect;
        }

        public boolean isDirect() {
            return direct;
        }

    }

    /**
     * returns all types required as import by the assignment statement.
     *
     * @return imported types
     */
    Set<Type> getImportTypes();

    /**
     * returns all types exception types thrown by this assignment.
     *
     * @return exceptions thrown
     */
    List<Type> getThrownTypes();

    /**
     * An assignment in itself can wrap another assignment. E.g.:
     * <ul>
     * <li>a MethodReference can wrap a TypeConversion, another MethodReference and ultimately a Simple</li>
     * <li>a TypeConversion can wrap a MethodReference, and ultimately a Simple</li>
     * </ul>
     *
     * @param assignment the assignment to set
     */
    void setAssignment(Assignment assignment);

    /**
     * the source reference being a source-getter, a constant, nested method call, etc.
     *
     * @return source reference
     */
    String getSourceReference();

    /**
     *
     * @return true when the source reference is the source parameter (and not a property of the source parameter type)
     */
    boolean isSourceReferenceParameter();

    /**
     * the source presence checker reference
     *
     * @return source reference
     */
    PresenceCheck getSourcePresenceCheckerReference();

    /**
     * the source type used in the matching process
     *
     * @return source type (can be null)
     */
    Type getSourceType();

    /**
     * Creates an unique safe (local) variable name.
     *
     * @param desiredName the desired name
     *
     * @return the desired name, made unique in the scope of the bean mapping.
     */
    String createUniqueVarName(String desiredName );

    /**
     * See {@link #setSourceLocalVarName(java.lang.String) }
     *
     * @return local variable name (can be null if not set)
     */
    String getSourceLocalVarName();

    /**
     * Returns the source parameter name, to which this assignment applies. Note: the source parameter itself might
     * be mapped by this assignment, or one of its properties
     *
     * @return  the source parameter name
     */
    String getSourceParameterName();

    /**
     * Replaces the sourceReference at the call site in the assignment in the template with this sourceLocalVarName.
     * The sourceLocalVarName can subsequently be used for e.g. null checking.
     *
     * @param sourceLocalVarName source local variable name
     */
    void setSourceLocalVarName(String sourceLocalVarName);

    /**
     * See {@link #getSourceLoopVarName()} (java.lang.String) }
     *
     * @return loop variable (can be null if not set)
     */
    String getSourceLoopVarName();

    /**
     * Replaces the sourceLocalVar or sourceReference at the call site in the assignment in the template with this
     * sourceLoopVarName.
     * The sourceLocalVar can subsequently be used for e.g. null checking.
     *
     * @param sourceLoopVarName loop variable
     */
    void setSourceLoopVarName(String sourceLoopVarName);

    /**
     * Returns whether the type of assignment
     *
     * @return {@link  AssignmentType}
     */
    AssignmentType getType();

    boolean isCallingUpdateMethod();

    /**
     * Returns the nullability when this assignment is used as a source.
     *
     * @return the {@link Nullability} resulting from this Assignment.
     */
    Nullability getSourceNullability();

    /**
     * Returns whether the first input parameter needs a null-check. In other words, whether the source expression
     * could be {@code null} and the assignment would fail if it were.
     *
     * @return {@code true} if the first input parameter does not accept null and null must be checked before passing it
     * to this assignment; {@code false} otherwise
     */
    boolean needsParameterNullCheck();
}
