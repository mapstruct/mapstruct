/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.util.Experimental;

/**
 * A contract to be implemented by other annotation processors which - against the design philosophy of JSR 269 - alter
 * the types under compilation.
 * <p>
 * This contract will be queried by MapStruct when examining types referenced by mappers to be generated, most notably
 * the source and target types of mapping methods. If at least one AST-modifying processor announces further changes to
 * such type, the generation of the affected mapper(s) will be deferred to a future round in the annotation processing
 * cycle.
 * <p>
 * Implementations are discovered via the service loader, i.e. a JAR providing an AST-modifying processor needs to
 * declare its implementation in a file {@code META-INF/services/org.mapstruct.ap.spi.AstModifyingAnnotationProcessor}.
 *
 * @author Gunnar Morling
 */
@Experimental( "This interface may change in future revisions" )
public interface AstModifyingAnnotationProcessor {

    /**
     * Whether the specified type has been fully processed by this processor or not (i.e. this processor will amend the
     * given type's structure after this invocation).
     *
     * @param type The type of interest
     * @return {@code true} if this processor has fully processed the given type (or has no interest in processing this
     * type altogether), {@code false} otherwise.
     */
    boolean isTypeComplete(TypeMirror type);
}
