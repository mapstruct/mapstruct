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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * String-based qualifier.
 *
 * <p>
 * For more info see:
 * <ul>
 * <li>{@link Mapping#qualifiedByName() }</li>
 * <li>{@link IterableMapping#qualifiedByName() }</li>
 * <li>{@link MapMapping#keyQualifiedByName() }</li>
 * <li>{@link MapMapping#valueQualifiedByName() }</li>
 * </ul>
 *
 * <p>
 * Example:
 *
 * <pre>
 * <code>
 * &#64;Named("TitleTranslator")
 * public class Titles {
 *
 *   &#64;Named("EnglishToGerman")
 *   public String translateTitleEG(String title) {
 *       // some mapping logic
 *   }
 *
 *   &#64;Named("GermanToEnglish")
 *   public String translateTitleGE(String title) {
 *       // some mapping logic
 *   }
 * }
 *
 * &#64;Mapper( uses = Titles.class )
 * public interface MovieMapper {
 *
 *    &#64;Mapping( target = "title", qualifiedByName = { "TitleTranslator", "EnglishToGerman" } )
 *    GermanRelease toGerman( OriginalRelease movies );
 *
 * }
 *
 * Will generate:
 *
 *  private final Titles titles = new Titles();
 *
 *  &#64;Override
 *  public GermanRelease toGerman(OriginalRelease movies) {
 *      if ( movies == null ) {
 *          return null;
 *      }
 *
 *      GermanRelease germanRelease = new GermanRelease();
 *
 *      germanRelease.setTitle( titles.translateTitleEG( movies.getTitle() ) );
 *
 *      return germanRelease;
 *  }
 * </code>
 * </pre>
 *
 *
 * @author Sjaak Derksen
 */
@Target( { ElementType.TYPE, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Qualifier
public @interface Named {
    /**
     *
     * @return the name
     */
    String value();
}
