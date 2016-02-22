/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures the mapping of source constant value to target constant value.
 *
 * Assume the following enumerations
 * <pre>
 * <code>
 * public enum OrderType {
 *
 *   RETAIL, B2B, EXTRA, STANDARD, NORMAL
 * }
 * </code>
 * </pre>
 *
 * <pre>
 * <code>
 * public enum ExternalOrderType {
 *
 *   RETAIL, B2B, SPECIAL, DEFAULT
 * }
 * </code>
 * </pre>
 *
 * MapStruct can complete mappings based on names. Just handle the exceptions and let MapStruct do the remainder.
 *
 * So:
 * <pre>
 * <code>
 * &#64;ValueMappings({
 *     &#64;ValueMapping(source = "EXTRA", target = "SPECIAL"),
 *     &#64;ValueMapping(source = "STANDARD", target = "DEFAULT"),
 *     &#64;ValueMapping(source = "NORMAL", target = "DEFAULT") })
 * ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * </pre>
 *
 * Will result in:
 *
 * <pre>
 * <code>
 *   &#64;Override
 *   public ExternalOrderType orderTypeToExternalOrderType(OrderType orderType) {
 *      if ( orderType == null ) {
 *          return null;
 *      }
 *
 *      ExternalOrderType externalOrderType_;
 *
 *      switch ( orderType ) {
 *          case EXTRA: externalOrderType_ = ExternalOrderType.SPECIAL;
 *           break;
 *           case STANDARD: externalOrderType_ = ExternalOrderType.DEFAULT;
 *           break;
 *           case NORMAL: externalOrderType_ = ExternalOrderType.DEFAULT;
 *           break;
 *           case RETAIL: externalOrderType_ = ExternalOrderType.RETAIL;
 *           break;
 *           case B2B: externalOrderType_ = ExternalOrderType.B2B;
 *           break;
 *           default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
 *      }
 *
 *      return externalOrderType_;
 *   }
 * </code>
 * </pre>
 *
 * You can specify how to handle null sources by specifying a mapping. And, you can specify how to handle defaults.
 *
 * <pre>
 * <code>
 * &#64;ValueMappings({
 *     &#64;ValueMapping( valueMappingType = ValueMappingType.NULL, target = "DEFAULT" ),
 *     &#64;ValueMapping( source = "STANDARD", targetIsNull = true ),
 *     &#64;ValueMapping( valueMappingType = ValueMappingType.DEFAULT, target = "SPECIAL" ) })
 * ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * </pre>
 *
 * That will result in:
 *
 * <pre>
 * <code>
 * &#64;Override
 * public ExternalOrderType orderTypeToExternalOrderType(OrderType orderType) {
 *      if ( orderType == null ) {
 *         return ExternalOrderType.DEFAULT;
 *      }
 *
 *      ExternalOrderType externalOrderType_;
 *
 *      switch ( orderType ) {
 *          case STANDARD: externalOrderType_ = null;
 *          break;
 *          default: externalOrderType_ = ExternalOrderType.SPECIAL;
 *      }
 *
 *      return externalOrderType_;
 *
 *  }
 * </code>
 * </pre>
 *
 * What about only specifying the default mapping, and let the remainder be done by name based mapping.
 *
 * <pre>
 * <code>
 *  &#64;ValueMapping( valueMappingType = ValueMappingType.DEFAULT_AFTER_APPLYING_NAME_BASED_MAPPINGS,
 *  target = "DEFAULT" )
 *  ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * </pre>
 *
 * Will result in:
 *
 * <pre>
 * <code>
 * &#64;Override
 * public ExternalOrderType orderTypeToExternalOrderType(OrderType orderType) {
 *       if ( orderType == null ) {
 *          return null;
 *       }
 *
 *       ExternalOrderType externalOrderType_;
 *
 *       switch ( orderType ) {
 *          case RETAIL: externalOrderType_ = ExternalOrderType.RETAIL;
 *          break;
 *          case B2B: externalOrderType_ = ExternalOrderType.B2B;
 *          break;
 *          default: externalOrderType_ = ExternalOrderType.DEFAULT;
 *        }
 *
 *        return externalOrderType_;
 *    }
 * </code>
 * </pre>
 *
 * So: MapStruct handles the same names and reverts to default for the ones it cannot map based on name.
 *
 * @author Sjaak Derksen
 */
@Repeatable(ValueMappings.class)
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ValueMapping {

    /**
     * Controls the type of mapping.
     *
     * @return The mapping type.
     */
    ValueMappingType valueMappingType() default ValueMappingType.SOURCE;


    /**
     * The source value constant to use for this mapping.
     *
     * If the value is not of type {@code String}, the value will be converted by applying a matching conversion method
     * or built-in conversion. Enumeration constants will be taken 'as is'.
     *
     * <p>
     * <b>NOTE:</b>
     * <ol>
     * <li> Mandatory for a mapping type {@link ValueMappingType#SOURCE} </li>
     * <li>May not be defined for {@link ValueMappingType#DEFAULT} or {@link ValueMappingType#NULL}</li>
     * </ol>
     *
     * @return The source value.
     */
    String source() default "";

    /**
     * The target value constant to use for this mapping.
     *
     * If the value is not of type {@code String}, the value will be converted by applying a matching
     * conversion method or built-in conversion. Enumeration constants will be taken 'as is'.
     *
     * This is a mandatory property unless {@code target=true}
     *
     * @return The target value.
     */
    String target() default "";

    /**
     * The {@link #target})should be considered {@code null}
     *
     * @return true if {@link #target} is to be considered {@code null}
     */
    boolean targetIsNull() default false;

}
