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
 * Can be used in:
 * <ul>
 * <li>{@link Mapping#qualifiedBy() }</li>
 * <li>{@link BeanMapping#qualifiedBy() }</li>
 * <li>{@link IterableMapping#qualifiedBy() }</li>
 * <li>{@link MapMapping#keyQualifiedBy() }</li>
 * <li>{@link MapMapping#valueQualifiedBy() }</li>
 * <li>{@link SubclassMapping#qualifiedBy() }</li>
 * </ul>
 * <p><strong>Example:</strong></p>
 * <pre><code class='java'>
 * // create qualifiers
 * &#64;Qualifier
 * &#64;Target(ElementType.TYPE)
 * &#64;Retention(RetentionPolicy.CLASS)
 * public &#64;interface TitleTranslator {}
 *
 * &#64;Qualifier
 * &#64;Target(ElementType.METHOD)
 * &#64;Retention(RetentionPolicy.CLASS)
 * public @interface EnglishToGerman {}
 *
 * &#64;Qualifier
 * &#64;Target(ElementType.METHOD)
 * &#64;Retention(RetentionPolicy.CLASS)
 * public @interface GermanToEnglish {}
 * </code></pre>
 * <pre><code class='java'>
 * // we can create class with map methods
 * &#64;TitleTranslator
 * public class Titles {
 *     &#64;EnglishToGerman
 *     public String translateTitleEnglishToGerman(String title) {
 *         // some mapping logic
 *     }
 *     &#64;GermanToEnglish
 *     public String translateTitleGermanToEnglish(String title) {
 *         // some mapping logic
 *     }
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // usage
 * &#64;Mapper( uses = Titles.class )
 * public interface MovieMapper {
 *      &#64;Mapping( target = "title", qualifiedBy = { TitleTranslator.class, EnglishToGerman.class } )
 *      GermanRelease toGerman( OriginalRelease movies );
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * public class MovieMapperImpl implements MovieMapper {
 *      private final Titles titles = new Titles();
 *      &#64;Override
 *      public GermanRelease toGerman(OriginalRelease movies) {
 *          if ( movies == null ) {
 *              return null;
 *          }
 *          GermanRelease germanRelease = new GermanRelease();
 *          germanRelease.setTitle( titles.translateTitleEnglishToGerman( movies.getTitle() ) );
 *          return germanRelease;
 *     }
 * }
 * </code></pre>
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
