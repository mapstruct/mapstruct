/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Controls which means of mapping are considered between the source and the target in mappings.
 *
 * <p>
 * There are several applications of <code>MappingControl</code> conceivable. One application, "deep cloning" is
 * explained below in the example.
 * </p>
 *
 * <p>
 * Another application is controlling so called "complex mappings", which are not always desirable and sometimes lead to
 * unexpected behaviour and prolonged compilation time.
 * </p>
 *
 * <p><strong>Example:</strong>Cloning of an object</p>
 * <p>
 * When all methods are allowed, MapStruct would make a shallow copy. It would take the <code>ShelveDTO</code> in
 * the <code>FridgeDTO</code> and directly enter that as target on the target <code>FridgeDTO</code>. By disabling all
 * other kinds of mappings apart from {@link MappingControl.Use#MAPPING_METHOD}, see {@link DeepClone} MapStruct is
 * forced to generate mapping methods all through the object graph `FridgeDTO` and hence create a deep clone.
 * </p>
 * <pre><code class='java'>
 * public class FridgeDTO {
 *
 *     private ShelveDTO shelve;
 *
 *     public ShelveDTO getShelve() {
 *         return shelve;
 *     }
 *
 *     public void setShelve(ShelveDTO shelve) {
 *         this.shelve = shelve;
 *     }
 * }
 * </code></pre>
 * <pre><code class='java'>
 * public class ShelveDTO {
 *
 *     private CoolBeerDTO coolBeer;
 *
 *     public CoolBeerDTO getCoolBeer() {
 *         return coolBeer;
 *     }
 *
 *     public void setCoolBeer(CoolBeerDTO coolBeer) {
 *         this.coolBeer = coolBeer;
 *     }
 * }
 * </code></pre>
 * <pre><code class='java'>
 * public class CoolBeerDTO {
 *
 *     private String beerCount;
 *
 *     public String getBeerCount() {
 *         return beerCount;
 *     }
 *
 *     public void setBeerCount(String beerCount) {
 *         this.beerCount = beerCount;
 *     }
 * }
 * </code></pre>
 *
 * <pre><code class='java'>
 * &#64;Mapper(mappingControl = DeepClone.class)
 * public interface CloningMapper {
 *
 *     CloningMapper INSTANCE = Mappers.getMapper( CloningMapper.class );
 *
 *     FridgeDTO clone(FridgeDTO in);
 *
 * }
 * </code></pre>
 *
 * @author Sjaak Derksen
 */
@Retention(RetentionPolicy.CLASS)
@Repeatable(MappingControls.class)
@Target( ElementType.ANNOTATION_TYPE )
@MappingControl( MappingControl.Use.DIRECT )
@MappingControl( MappingControl.Use.BUILT_IN_CONVERSION )
@MappingControl( MappingControl.Use.MAPPING_METHOD )
@MappingControl( MappingControl.Use.COMPLEX_MAPPING )
public @interface MappingControl {

    Use value();

    enum Use {

        /**
         * Controls the mapping, allows for type conversion from source type to target type
         * <p>
         * Type conversions are typically supported directly in Java. The "toString()" is such an example,
         * which allows for mapping for instance a  {@link java.lang.Number} type to a {@link java.lang.String}.
         * <p>
         * Please refer to the MapStruct guide for more info.
         *
         * @since 1.4
         */
        BUILT_IN_CONVERSION,

        /**
         * Controls the mapping from source to target type, allows mapping by calling:
         * <ol>
         * <li>A type conversion, passed into a mapping method</li>
         * <li>A mapping method, passed into a type conversion</li>
         * <li>A mapping method passed into another mapping method</li>
         * </ol>
         *
         * @since 1.4
         */
        COMPLEX_MAPPING,
        /**
         * Controls the mapping, allows for a direct mapping from source type to target type.
         * <p>
         * This means if source type and target type are of the same type, MapStruct will not perform
         * any mappings anymore and assign the target to the source direct.
         * <p>
         * An exception are types from the package {@link java}, which will be mapped always directly.
         *
         * @since 1.4
         */
        DIRECT,

        /**
         * Controls the mapping, allows for Direct Mapping from source type to target type.
         * <p>
         * The mapping method can be either a custom referred mapping method, or a MapStruct built in
         * mapping method.
         *
         * @since 1.4
         */
        MAPPING_METHOD
    }

}
