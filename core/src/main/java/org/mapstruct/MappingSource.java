/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a parameter with specific handling behavior during mapping.
 * This annotation controls how source parameters are handled in multi-source mapping scenarios.
 * <p>
 * By default,:
 * - all parameters have their properties automatically used for implicit mapping
 * - Map parameters in multi-source scenarios don't have their entries automatically used for implicit mapping
 * <p>
 * The implicitMapping attribute allows overriding these defaults:
 * - For Bean parameters: setting implicitMapping=false prevents automatic property expansion
 * - For Map parameters: setting implicitMapping=true enables using Map entries for implicit mapping
 * <p>
 * This annotation is primarily useful in multi-source parameter scenarios.
 *
 * <pre><code class='java'>
 * // Preventing a bean parameter's properties from being used in implicit mapping
 * {@literal @}Mapper
 * public interface MultiSourceMapper {
 *     {@literal @}Mapping(target = "name", source = "otherSource.name")
 *     TargetDto map(
 *         {@literal @}MappingSource(implicitMapping = false) UserEntity user,
 *          OtherSource otherSource);
 * }
 *
 * // Enabling Map entries for implicit mapping in multi-source scenarios
 * {@literal @}Mapper
 * public interface MultiSourceMapper {
 *     {@literal @}Mapping(target = "id", source = "entity.id")
 *     TargetDto map({@literal @}MappingSource(implicitMapping = true)
 *     Map&lt;String, Object&gt; sourceMap, Entity entity);
 * }
 * </code></pre>
 *
 * @since 1.6.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface MappingSource {

    /**
     * Controls whether this parameter participates in implicit mapping.
     * The effect depends on the parameter type:
     * <p>
     * - For Bean parameters:
     * true (default): Properties are automatically used for implicit mapping
     * false: Properties are not automatically used for implicit mapping
     * <p>
     * - For Map parameters (in multi-source scenarios):
     * true: Map entries are used for implicit mapping (similar to single-source behavior)
     * false (default): Map entries are not automatically used for implicit mapping
     * <p>
     * - For other types (Collection, Path, etc.):
     * This setting has no effect as these types are never automatically expanded
     * <p>
     * Note: This setting only affects implicit mapping. Explicit mappings defined with
     * {@literal @}Mapping annotations are always processed regardless of this setting.
     *
     * @return whether implicit mapping should be enabled for this parameter
     */
    boolean implicitMapping() default true;
}
