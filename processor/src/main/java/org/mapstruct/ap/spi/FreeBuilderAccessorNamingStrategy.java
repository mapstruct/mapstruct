/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;

import org.mapstruct.util.Experimental;

/**
 * Accessor naming strategy for FreeBuilder.
 * FreeBuilder adds a lot of other methods that can be considered as fluent setters. Such as:
 * <ul>
 *     <li>{@code from(Target)}</li>
 *     <li>{@code mapXXX(UnaryOperator)}</li>
 *     <li>{@code mutateXXX(Consumer)}</li>
 *     <li>{@code mergeFrom(Target)}</li>
 *     <li>{@code mergeFrom(Target.Builder)}</li>
 * </ul>
 * <p>
 * When the JavaBean convention is not used with FreeBuilder then the getters are non standard and MapStruct
 * won't recognize them. Therefore one needs to use the JavaBean convention in which the fluent setters
 * start with {@code set}.
 *
 * @author Filip Hrisafov
 */
@Experimental("The FreeBuilder accessor naming strategy might change in a subsequent release")
public class FreeBuilderAccessorNamingStrategy extends DefaultAccessorNamingStrategy {

    @Override
    protected boolean isFluentSetter(ExecutableElement method) {
        // When using FreeBuilder one needs to use the JavaBean convention, which means that all setters will start
        // with set
        return false;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName();
    }

}
