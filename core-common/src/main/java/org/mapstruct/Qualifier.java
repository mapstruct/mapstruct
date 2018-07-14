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
 * Declares an annotation type to be a qualifier. Qualifier annotations allow unambiguously identify a suitable mapping
 * method in case several methods qualify to map a bean property, iterable element etc.
 * <p>
 * For more info see:
 * <ul>
 * <li>{@link Mapping#qualifiedBy() }</li>
 * <li>{@link BeanMapping#qualifiedBy() }</li>
 * <li>{@link IterableMapping#qualifiedBy() }</li>
 * <li>{@link MapMapping#keyQualifiedBy() }</li>
 * <li>{@link MapMapping#valueQualifiedBy() }</li>
 * </ul>
 * Example:
 *
 * <pre>
 * &#64;Qualifier
 * &#64;Target(ElementType.METHOD)
 * &#64;Retention(RetentionPolicy.CLASS)
 * public &#64;interface EnglishToGerman {
 * }
 * </pre>
 *
 * <b>NOTE:</b> Qualifiers should have {@link RetentionPolicy#CLASS}.
 *
 * @author Sjaak Derksen
 * @see Named
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Qualifier {
}
