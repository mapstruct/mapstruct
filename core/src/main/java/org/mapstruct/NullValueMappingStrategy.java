/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for dealing with {@code null} values passed to mapping methods.
 *
 * @author Sjaak Derksen
 */
public enum NullValueMappingStrategy {

    /**
     * If {@code null} is passed to a mapping method, {@code null} will be returned, unless the return type is
     * {@link java.util.Optional Optional}, in which case an empty optional will be returned.
     * That's the default behavior if no alternative strategy is configured globally, for given mapper or method.
     */
    RETURN_NULL,

    /**
     * If {@code null} is passed to a mapping method, a default value will be returned. The value depends on the kind of
     * the annotated method:
     * <ul>
     * <li>For bean mapping methods the target type will be instantiated and returned. Any properties of the target type
     * which are mapped via {@link Mapping#expression()} or {@link Mapping#constant()} will be populated based on the
     * given expression or constant. Note that expressions must be prepared to deal with {@code null} values in this
     * case.</li>
     * <li>For iterable mapping methods an empty collection will be returned.</li>
     * <li>For map mapping methods an empty map will be returned.</li>
     * <li>For optional mapping methods an empty optional will be returned.</li>
     * </ul>
     */
    RETURN_DEFAULT;
}
