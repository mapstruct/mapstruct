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
 * <p>
 * Supported mappings are
 * <ol>
 * <li>Enumeration to Enumeration</li>
 * </ol>
 * <p>
 * <B>Example 1:</B>
 * <pre>
 * <code>
 * public enum OrderType { RETAIL, B2B, EXTRA, STANDARD, NORMAL }
 *
 * public enum ExternalOrderType { RETAIL, B2B, SPECIAL, DEFAULT }
 *
 * &#64;ValueMappings({
 *    &#64;ValueMapping(source = "EXTRA", target = "SPECIAL"),
 *    &#64;ValueMapping(source = "STANDARD", target = "DEFAULT"),
 *    &#64;ValueMapping(source = "NORMAL", target = "DEFAULT")
 * })
 * ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * Mapping result:
 * ╔═════════════════════╦════════════════════════════╗
 * ║ OrderType           ║ ExternalOrderType          ║
 * ╠═════════════════════╬════════════════════════════╣
 * ║ &lt;NULL&gt;              ║ &lt;NULL&gt;                     ║
 * ║ OrderType.EXTRA     ║ ExternalOrderType.SPECIAL  ║
 * ║ OrderType.STANDARD  ║ ExternalOrderType.DEFAULT  ║
 * ║ OrderType.NORMAL    ║ ExternalOrderType.DEFAULT  ║
 * ║ OrderType.RETAIL    ║ ExternalOrderType.RETAIL   ║
 * ║ OrderType.B2B       ║ ExternalOrderType.B2B      ║
 * ╚═════════════════════╩════════════════════════════╝
 * </pre>
 * MapStruct will <B>WARN</B> on incomplete mappings. However, if for some reason no match is found an
 * {@link java.lang.IllegalStateException} will be thrown.
 * <p>
 * <B>Example 2:</B>
 * <pre>
 * <code>
 * &#64;ValueMappings({
 *    &#64;ValueMapping( source = "&lt;NULL&gt;", target = "DEFAULT" ),
 *    &#64;ValueMapping( source = "STANDARD", target = "&lt;NULL&gt;" ),
 *    &#64;ValueMapping( source = "&lt;ANY&gt;", target = "SPECIAL" )
 * })
 * ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * Mapping result:
 * ╔═════════════════════╦════════════════════════════╗
 * ║ OrderType           ║ ExternalOrderType          ║
 * ╠═════════════════════╬════════════════════════════╣
 * ║ &lt;NULL&gt;              ║ ExternalOrderType.DEFAULT  ║
 * ║ OrderType.STANDARD  ║ &lt;NULL&gt;                     ║
 * ║ OrderType.RETAIL    ║ ExternalOrderType.RETAIL   ║
 * ║ OrderType.B2B       ║ ExternalOrderType.B2B      ║
 * ║ &lt;ANY&gt;               ║ ExternalOrderType.SPECIAL  ║
 * ╚═════════════════════╩════════════════════════════╝
 * </pre>
 *
 * @author Sjaak Derksen
 */
@Repeatable(ValueMappings.class)
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ValueMapping {

   /**
     * The source value constant to use for this mapping.
     *
     * <p>
     * <b>Valid values:</b>
     * <ol>
     * <li>ENUM IDENTIFIER</li>
     * <li>&lt;NULL&gt;</li>
     * <li>&lt;ANY&gt;</li>
     * <li>&lt;ANY_UNMAPPED&gt;</li>
     * </ol>
     * <p>
     * <b>NOTE:</b>When using &lt;ANY&gt;, MapStruct will perform the normal name based mapping, in which source is
     * mapped to target based on enum identifier equality. Using &lt;ANY_UNMAPPED&gt; will not apply name based mapping.
     *
     * @return The source value.
     */
    String source();

    /**
     * The target value constant to use for this mapping.
     *
     * <p>
     * <b>Valid values:</b>
     * <ol>
     * <li>ENUM IDENTIFIER</li>
     * <li>&lt;NULL&gt;</li>
     * </ol>
     *
     * @return The target value.
     */
    String target();

}
