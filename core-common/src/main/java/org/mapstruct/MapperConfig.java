/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.factory.Mappers;

/**
 * Marks a class or interface as configuration source for generated mappers. This allows to share common configurations
 * between several mapper classes.
 * <p>
 * Generally, any settings given via {@link Mapper} take precedence over the settings given via the referenced
 * {@code MapperConfig}. The lists of referenced mappers given via {@link Mapper#uses()} and
 * {@link MapperConfig#uses()} will be merged.
 * <p>
 * Additionally, mapper configuration classes may declare one more <em>prototype mapping methods</em>. These methods are
 * not meant to be invoked themselves (no implementation will generated for mapper config classes), but serve as
 * configuration template for mapping methods declared by actual mapper classes. Depending on the configured
 * {@link #mappingInheritanceStrategy()}, the configuration can be inherited either explicitly using
 * {@link InheritConfiguration} or {@link InheritInverseConfiguration}, or automatically in case all source and target
 * types are assignable.
 * </p>
 *
 * @author Sjaak Derksen
 * @see Mapper#config()
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface MapperConfig {

    /**
     * The mapper types used by this mapper.
     *
     * @return The mapper types used by this mapper.
     */
    Class<?>[] uses() default { };

    /**
     * How unmapped properties of the target type of a mapping should be
     * reported.
     *
     * @return The reporting policy for unmapped target properties.
     */
    ReportingPolicy unmappedTargetPolicy() default ReportingPolicy.WARN;

    /**
     * Specifies the component model to which the generated mapper should
     * adhere. Supported values are
     * <ul>
     * <li> {@code default}: the mapper uses no component model, instances are
     * typically retrieved via {@link Mappers#getMapper(Class)}</li>
     * <li>
     * {@code cdi}: the generated mapper is an application-scoped CDI bean and
     * can be retrieved via {@code @Inject}</li>
     * <li>
     * {@code spring}: the generated mapper is a Spring bean and
     * can be retrieved via {@code @Autowired}</li>
     * <li>
     * {@code jsr330}: the generated mapper is annotated with {@code @Named} and
     * can be retrieved via {@code @Inject}</li>
     * </ul>
     *
     * @return The component model for the generated mapper.
     */
    String componentModel() default "default";

    /**
     * Specifies the name of the implementation class. The {@code <CLASS_NAME>} will be replaced by the
     * interface/abstract class name.
     * <p>
     * Defaults to postfixing the name with {@code Impl}: {@code <CLASS_NAME>Impl}
     *
     * @return The implementation name.
     * @see #implementationPackage()
     */
    String implementationName() default "<CLASS_NAME>Impl";

    /**
     * Specifies the target package for the generated implementation. The {@code <PACKAGE_NAME>} will be replaced by the
     * interface's or abstract class' package.
     * <p>
     * Defaults to using the same package as the mapper interface/abstract class
     *
     * @return the implementation package.
     * @see #implementationName()
     */
    String implementationPackage() default "<PACKAGE_NAME>";

    /**
     * The strategy to be applied when propagating the value of collection-typed properties. By default, only JavaBeans
     * accessor methods (setters or getters) will be used, but it is also possible to invoke a corresponding adder
     * method for each element of the source collection (e.g. {@code orderDto.addOrderLine()}).
     *
     * @return The strategy applied when propagating the value of collection-typed properties.
     */
    CollectionMappingStrategy collectionMappingStrategy() default CollectionMappingStrategy.ACCESSOR_ONLY;

    /**
     * The strategy to be applied when {@code null} is passed as source value to mapping methods. If no strategy is
     * configured, {@link NullValueMappingStrategy#RETURN_NULL} will be used by default.
     *
     * @return The strategy to be applied when {@code null} is passed as source value to mapping methods.
     */
    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    /**
     * The strategy to use for applying method-level configuration annotations of prototype methods in the interface
     * annotated with this annotation. Annotations that can be inherited are for example {@link Mapping},
     * {@link IterableMapping}, {@link MapMapping}, or {@link BeanMapping}.
     * <p>
     * If no strategy is configured, {@link MappingInheritanceStrategy#EXPLICIT} will be used as default.
     *
     * @return The strategy to use for applying {@code @Mapping} configurations of prototype methods in the interface
     * annotated with this annotation.
     */
    MappingInheritanceStrategy mappingInheritanceStrategy()
        default MappingInheritanceStrategy.EXPLICIT;
}
