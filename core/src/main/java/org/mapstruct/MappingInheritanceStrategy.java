/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for inheriting configurations given for methods of prototype mapping methods (declared on mapper config
 * classes) to actual mapping methods declared on mappers referring to such config class via {@link Mapper#config()}.
 *
 * @author Andreas Gudian
 */
public enum MappingInheritanceStrategy {
    /**
     * Apply the method-level configuration annotations only if the prototype method is explicitly referenced using
     * {@link InheritConfiguration}.
     */
    EXPLICIT,

    /**
     * Inherit the method-level forward configuration annotations automatically if source and target types of the
     * prototype method are assignable from the types of a given mapping method.
     */
    AUTO_INHERIT_FROM_CONFIG,

    /**
     * Inherit the method-level reverse configuration annotations automatically if source and target types of the
     * prototype method are assignable from the target and source types of a given mapping method.
     */
    AUTO_INHERIT_REVERSE_FROM_CONFIG,

    /**
     * Inherit the method-level forward and reverse configuration annotations automatically if source and target types
     * of the prototype method are assignable from the types of a given mapping method.
     */
    AUTO_INHERIT_ALL_FROM_CONFIG;
}
