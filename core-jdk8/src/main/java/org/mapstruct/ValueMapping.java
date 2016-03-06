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
 * Configures the mapping of source constant value to target constant value. Supported mappings are
 * <p>
 * <ol>
 * <li>Enumeration to Enumeration</li>
 * </ol>
 * <p>
 * <H2>Enumeration to Enumeration</H2>
 * <p>
 * {@codesnippet OrderType}
 * {@codesnippet ExternalOrderType}
 * <p>
 * <H3>Completing mappings based on name</H3>
 * MapStruct can complete mappings based on names. Just handle the non matching names and let MapStruct do the
 * remainder.
 * <p>
 * <H4>Mapper Definition:</H4>
 * {@codesnippet OrderMapper}
 * <p>
 * <H4>Results in:</H4>
 * <table border="1" cellpadding="5">
 * <thead>
 *   <tr><th>Source</th><th>Target</th></tr>
 * </thead>
 * <tbody>
 *    <tr><td>&lt;null&gt;</td><td>&lt;null&gt;</td></tr>
 *    <tr><td>OrderType.EXTRA</td><td>ExternalOrderType.SPECIAL</td></tr>
 *    <tr><td>OrderType.STANDARD</td><td>ExternalOrderType.DEFAULT</td></tr>
 *    <tr><td>OrderType.NORMAL</td><td>ExternalOrderType.DEFAULT</td></tr>
 *    <tr><td>OrderType.RETAIL</td><td>ExternalOrderType.RETAIL</td></tr>
 *    <tr><td>OrderType.B2B</td><td>ExternalOrderType.B2B</td></tr>
 * </tbody>
 * </table>
 * <p>
 * Note how MapStruct completed the last two mappings based on a name match. If for some reason no match is found, for
 * instance because the source enumeration has been extended without compilation, an
 * {@link java.lang.IllegalStateException} will be thrown.
 * <H3>Mapping null sources and null targets</H3>
 * <p>
 * When desired, handling null sources can be handled by specifying a mapping. And, just like a case statement, also
 * the default can be specified.
 * <p>
 * <H4>Mapper Definition:</H4>
 * {@codesnippet SpecialOrderMapper}
 * <p>
 * <H4>Results in:</H4>
 * <table border="1" cellpadding="5">
 * <thead>
 *   <tr><th>Source</th><th>Target</th></tr>
 * </thead>
 * <tbody>
 *    <tr><td>&lt;null&gt;</td><td>ExternalOrderType.DEFAULT</td></tr>
 *    <tr><td>OrderType.STANDARD</td><td>&lt;null&gt;</td></tr>
 *    <tr><td>&lt;all other&gt;</td><td>ExternalOrderType.SPECIAL</td></tr>
 * </tbody>
 * </table>
 * <p>
 * <H3>Completing mappings based on name, then applying default</H3>
 * When MapStruct needs to complete mappings based on names and then map the remainder to a default, use the option
 * {@code ValueMappingType.DEFAULT_AFTER_APPLYING_NAME_BASED_MAPPINGS}
 * <p>
 * <H4>Mapper Definition:</H4>
 * {@codesnippet DefaultOrderMapper}
 * <p>
 * <H4>Results in:</H4>
 * <p>
 * <table border="1" cellpadding="5">
 * <thead>
 *   <tr><th>Source</th><th>Target</th></tr>
 * </thead>
 * <tbody>
 *    <tr><td>&lt;null&gt;</td><td>&lt;null&gt;</td></tr>
 *    <tr><td>OrderType.RETAIL</td><td>ExternalOrderType.RETAIL</td></tr>
 *    <tr><td>OrderType.B2B</td><td>ExternalOrderType.B2B</td></tr>
 *    <tr><td>&lt;all other&gt;</td><td>ExternalOrderType.DEFAULT</td></tr>
 * </tbody>
 * </table>
 *
 * <H3>Reverting</H3>
 * MapStruct can also revert all mappings. Use the {@link InheritInverseConfiguration} to do so.
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
     * <li>Enumeration Identifier</li>
     * <li>null</li>
     * <li>$</li>
     * <li>*</li>
     * </ol>
     * <B>NOTE:</B> '$' can only be used in when {@link #target() } == '$'. Its meant to signify automated mapping
     * source to target based on identical names <B>before</B> applying a default ('*') mapping.
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
     * <li>Enumeration Identifier</li>
     * <li>null</li>
     * <li>$</li>
     * </ol>
     *
     * <B>NOTE:</B> '$' can only be used in when {@link #source() } == '$'. Its meant to signify automated mapping
     * source to target based on identical names <B>before</B> applying a default ('*') mapping.
     * @return The target value.
     */
    String target();

}
